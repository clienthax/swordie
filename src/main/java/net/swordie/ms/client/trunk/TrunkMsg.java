package net.swordie.ms.client.trunk;

import net.swordie.ms.connection.OutPacket;

/**
 * Created on 4/7/2018.
 */
public record TrunkMsg(TrunkType type) implements TrunkDlg {

    @Override
    public void encode(OutPacket outPacket) {

    }
}
