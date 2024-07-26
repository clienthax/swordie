package net.swordie.ms.enums;

import java.util.Arrays;

/**
 * Created on 1/26/2018.
 */
public enum ItemGrade {
    LegendaryBonusHidden(-12),
    UniqueBonusHidden(-13),
    EpicBonusHidden(-14),
    RareBonusHidden(-15),

    HiddenLegendary(4),
    HiddenUnique(3),
    HiddenEpic(2),
    HiddenRare(1),
    Hidden(-1), // buggy

    None(0),

    RareSecondary(16), //we need this defined for the lowest tier to exist
    Rare(17),
    Epic(18),
    Unique(19),
    Legendary(20),
    ;

    private final int val;

    ItemGrade(int val) {
        this.val = val;
    }

    public static ItemGrade getHiddenBonusGradeByBaseGrade(ItemGrade gradeByVal) {
        return switch (gradeByVal) {
            case HiddenRare, Rare -> RareBonusHidden;
            case HiddenEpic, Epic -> EpicBonusHidden;
            case HiddenUnique, Unique -> UniqueBonusHidden;
            case HiddenLegendary, Legendary -> LegendaryBonusHidden;
            default -> None;
        };
    }

    public short getVal() {
        return (short) val;
    }

    public static ItemGrade getGradeByVal(int grade) {
        return Arrays.stream(values()).filter(ig -> ig.getVal() == grade).findFirst().orElse(null);
    }

    public static ItemGrade getGradeByOption(int option) {
        ItemGrade itemGrade = None;
        if(option < 0) {
            itemGrade =  Arrays.stream(values()).filter(is -> is.getVal() == Math.abs(option)).findFirst().orElse(None);
        }
        if((option > 0 && option < 10000)) {
            itemGrade = RareSecondary;
        }
        if(option > 10000 && option < 20000) {
            itemGrade = Rare;
        }
        if(option > 20000 && option < 30000) {
            itemGrade = Epic;
        }
        if(option > 30000 && option < 40000) {
            itemGrade = Unique;
        }
        if(option > 40000 && option < 50000) {
            itemGrade = Legendary;
        }
        return itemGrade;
    }
    
    public static boolean isMatching(short first, short second) {
        ItemGrade firstGrade = getGradeByVal(first);
        ItemGrade other = getGradeByVal(second);
        return switch (firstGrade) {
            case None -> other == None;
            case RareSecondary -> other == RareSecondary;
            case HiddenRare, Rare -> other == HiddenRare || other == Rare;
            case HiddenEpic, Epic -> other == HiddenEpic || other == Epic;
            case HiddenUnique, Unique -> other == HiddenUnique || other == Unique;
            case HiddenLegendary, Legendary -> other == HiddenLegendary || other == Legendary;
            default -> false;
        };
    }

    public static ItemGrade getHiddenGradeByVal(short val) {
        ItemGrade ig = None;
        ItemGrade arg = getGradeByVal(val);
        ig = switch (arg) {
            case Rare, HiddenRare -> HiddenRare;
            case Epic, HiddenEpic -> HiddenEpic;
            case Unique, HiddenUnique -> HiddenUnique;
            case Legendary, HiddenLegendary -> HiddenLegendary;
            default -> ig;
        };
        return ig;
    }

    public static ItemGrade getOneTierLower(short val) {
        ItemGrade ig = None;
        ItemGrade arg = getGradeByVal(val);
        ig = switch (arg) {
            case Rare -> RareSecondary;
            case Epic -> Rare;
            case HiddenRare, HiddenEpic -> HiddenRare;
            case Unique -> Epic;
            case HiddenUnique -> HiddenEpic;
            case Legendary -> Unique;
            case HiddenLegendary -> HiddenUnique;
            default -> ig;
        };
        return ig;
    }

    public boolean isHidden() {
        return switch (this) {
            case Hidden, HiddenRare, HiddenEpic, HiddenUnique, HiddenLegendary -> true;
            default -> false;
        };
    }
}