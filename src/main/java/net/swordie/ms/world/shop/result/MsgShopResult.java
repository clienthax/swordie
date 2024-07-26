package net.swordie.ms.world.shop.result;

import net.swordie.ms.connection.OutPacket;

/**
 * Created on 3/29/2018.
 */
public record MsgShopResult(ShopResultType type) implements ShopResult {

    @Override
    public void encode(OutPacket outPacket) {
    }
}
