package net.swordie.ms.handlers.social;

import net.swordie.ms.client.character.Char;
import net.swordie.ms.client.character.commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandHandler {

    private static final Logger log = LogManager.getLogger(CommandHandler.class);
    final HashMap<ICommand, Command> commands = new HashMap<>();

    public void loadCommands() {
        Class<?>[] adminCommandClasses = AdminCommands.class.getClasses();
        Class<?>[] playerCommandClasses = PlayerCommands.class.getClasses();

        List<Class<?>> classesList = new ArrayList<>(adminCommandClasses.length + playerCommandClasses.length);
        classesList.addAll(Arrays.asList(adminCommandClasses));
        classesList.addAll(Arrays.asList(playerCommandClasses));

        for (Class<?> clazz : classesList) {
            Command cmdInfo = clazz.getAnnotation(Command.class);
            if (cmdInfo == null) {
                continue;
            }

            try {
                ICommand cmdRunner = (ICommand) clazz.getDeclaredConstructor().newInstance();
                commands.put(cmdRunner, cmdInfo);
                log.info("Loaded command: {} (Permission: {})", cmdInfo.names(), cmdInfo.requiredType());
            } catch (Exception e) {
                log.info("Error loading command: {} (Permission: {})", cmdInfo.names(), cmdInfo.requiredType());
            }
        }
    }

    public boolean handleCommand(Char chr, String msg) {
        String command = msg.split(" ")[0].replace(String.valueOf(msg.charAt(0)), "");

        for (Map.Entry<ICommand, Command> commandEntry : commands.entrySet()) {
            Command cmdInfo = commandEntry.getValue();
            if (!Arrays.asList(cmdInfo.names()).contains(command)) {
                // Doesn't match command name
                continue;
            }

            // Check if the player has the required permission
            if (chr.getUser().getAccountType().ordinal() < cmdInfo.requiredType().ordinal()) {
                // Doesn't have permission
                continue;
            }

            // Execute the command
            commandEntry.getKey().execute(chr, msg.split(" "));
            return true;
        }

        return false;
    }

}
