package net.swordie.ms.connection.netty;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.swordie.ms.client.Client;
import net.swordie.ms.client.User;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.connection.InPacket;
import net.swordie.ms.handlers.Handler;
import net.swordie.ms.handlers.header.InHeader;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static net.swordie.ms.connection.netty.NettyClient.CLIENT_KEY;


/**
 * Created by Tim on 2/28/2017.
 */
public class ChannelHandler extends SimpleChannelInboundHandler<InPacket> {

    private static final org.apache.logging.log4j.Logger log = LogManager.getRootLogger();
    private static final Map<InHeader, Method> handlers = new HashMap<>();

    public static void initHandlers(boolean mayOverride) {
        String handlersPackage = Handler.class.getPackage().getName();

        long start = System.currentTimeMillis();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(handlersPackage)
                .scan()) {
            List<Class<?>> classes = scanResult.getClassesWithMethodAnnotation(Handler.class.getName()).loadClasses();

            for (Class<?> clazz : classes) {
                for (Method method : clazz.getMethods()) {
                    Handler handler = method.getAnnotation(Handler.class);
                    if (handler == null) {
                        continue;
                    }

                    InHeader header = handler.op();
                    if (header != InHeader.NO) {
                        if (handlers.containsKey(header) && !mayOverride) {
                            throw new IllegalArgumentException(String.format("Multiple handlers found for header %s! Had method %s, but also found %s.", header, handlers.get(header).getName(), method.getName()));
                        }
                        handlers.put(header, method);
                        log.debug("Added handler for header {} with method {}", header, method.getName());
                    }

                    InHeader[] headers = handler.ops();
                    for (InHeader h : headers) {
                        handlers.put(h, method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Initialized {} handlers in {}ms.", handlers.size(), System.currentTimeMillis() - start);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.debug("[ChannelHandler] | Channel inactive.");
        Client c = (Client) ctx.channel().attr(CLIENT_KEY).get();
        User user = c.getUser();
        Char chr = c.getChr();
        if (chr != null && !chr.isChangingChannel()) {
            chr.logout();
        } else if (chr != null && chr.isChangingChannel()) {
            chr.setChangingChannel(false);
        } else if (user != null) {
            user.unstuck();
        } else {
            log.warn("[ChannelHandler] | Was not able to save character, data inconsistency may have occurred.");
        }
        NettyClient o = ctx.channel().attr(CLIENT_KEY).get();
        if (o != null) {
            o.close();
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, InPacket inPacket) {
        Client c = (Client) ctx.channel().attr(CLIENT_KEY).get();
        Char chr = c.getChr();
        short op = inPacket.decodeShort();
        InHeader inHeader = InHeader.getInHeaderByOp(op);
        if (inHeader == null) {
            handleUnknown(inPacket, op);
            return;
        }
        if (!InHeader.isSpamHeader(InHeader.getInHeaderByOp(op))) {
            log.debug(String.format("[In]\t| %s, %d/0x%s\t| %s", InHeader.getInHeaderByOp(op), op, Integer.toHexString(op).toUpperCase(), inPacket));
        }
        Method method = handlers.get(inHeader);
        try {
            if (method == null) {
                handleUnknown(inPacket, op);
            } else {
                Class<?> clazz = method.getParameterTypes()[0];
                try {
                    if (method.getParameterTypes().length == 3) {
                        method.invoke(this, chr, inPacket, inHeader);
                    } else if (clazz == Client.class) {
                        method.invoke(this, c, inPacket);
                    } else if (clazz == Char.class) {
                        method.invoke(this, chr, inPacket);
                    } else {
                        log.error("Unhandled first param type of handler {}, type = {}", method.getName(), clazz);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            inPacket.release();
        }
    }


    private void handleUnknown(InPacket inPacket, short opCode) {
        if (!InHeader.isSpamHeader(InHeader.getInHeaderByOp(opCode))) {
            log.warn(String.format("Unhandled opcode %s/0x%s, packet %s", opCode, Integer.toHexString(opCode).toUpperCase(), inPacket));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            log.info("Client forcibly closed the game.");
        } else {
            cause.printStackTrace();
        }
    }
}
