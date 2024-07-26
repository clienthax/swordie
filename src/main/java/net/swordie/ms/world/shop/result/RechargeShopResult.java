package net.swordie.ms.world.shop.result;

import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.world.shop.NpcShopDlg;

/**
 * Created on 3/29/2018.
 */
public class RechargeShopResult implements ShopResult {

    private final NpcShopDlg shop;

    public RechargeShopResult(NpcShopDlg shop) {
        this.shop = shop;
    }

    @Override
    public ShopResultType type() {
        return ShopResultType.RechargeSuccess;
    }

    @Override
    public void encode(OutPacket outPacket) {
        shop.encode(outPacket);
    }
}
