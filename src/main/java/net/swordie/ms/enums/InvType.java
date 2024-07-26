package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 11/23/2017.
 */
public enum InvType {
    EQUIPPED(-1),
    EQUIP(1),
    CONSUME(2),
    ETC(4),
    INSTALL(3),
    CASH(5)
    ;

    private final byte val;

    InvType(int val) {
        this((byte) val);
    }

    InvType(byte val) {
        this.val = val;
    }

    public byte getVal() {
        return val;
    }

    public static InvType getInvTypeByVal(int val) {
        return Arrays.stream(InvType.values()).filter(t -> t.getVal() == val).findFirst().orElse(null);
    }

    public static InvType getInvTypeByString(String subMap) {
        subMap = subMap.toLowerCase();
        InvType res = switch (subMap) {
            case "cash", "pet" -> CASH;
            case "consume", "special", "use" -> CONSUME;
            case "etc" -> ETC;
            case "install", "setup" -> INSTALL;
            case "eqp", "equip" -> EQUIP;
            default -> null;
        };
        return res;
    }

    public boolean isStackable() {
        return this != EQUIP && this != EQUIPPED && this != CASH;
    }
}
