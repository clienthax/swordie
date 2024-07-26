package net.swordie.ms.enums;

import net.swordie.ms.client.character.skills.temp.CharacterTemporaryStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2/3/2018.
 * @author Eric
 */
public enum TSIndex {
    EnergyCharged(0),
    DashSpeed(1),
    DashJump(2),
    RideVehicle(3),
    PartyBooster(4),
    GuidedBullet(5),
    Undead(6);
    private final int index;

    TSIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static TSIndex getTSEByIndex(int index) {
        return Arrays.stream(TSIndex.values()).filter(tse -> tse.getIndex() == index).findFirst().orElse(null);
    }

    public static CharacterTemporaryStat getCTSFromTwoStatIndex(int index) {
        return switch (index) {
            case 0 -> CharacterTemporaryStat.EnergyCharged;
            case 1 -> CharacterTemporaryStat.DashSpeed;
            case 2 -> CharacterTemporaryStat.DashJump;
            case 3 -> CharacterTemporaryStat.RideVehicle;
            case 4 -> CharacterTemporaryStat.PartyBooster;
            case 5 -> CharacterTemporaryStat.GuidedBullet;
            case 6 -> CharacterTemporaryStat.Undead;
            default -> null;
        };
    }

    public static TSIndex getTSEFromCTS(CharacterTemporaryStat cts) {
        return switch (cts) {
            case EnergyCharged -> EnergyCharged;
            case DashJump -> DashJump;
            case DashSpeed -> DashSpeed;
            case RideVehicle -> RideVehicle;
            case PartyBooster -> PartyBooster;
            case GuidedBullet -> GuidedBullet;
            case Undead -> Undead;
            default -> null;
        };
    }

    public static boolean isTwoStat(CharacterTemporaryStat cts) {
        return getTSEFromCTS(cts) != null;
    }

    /**
     * Creates a list of all {@link CharacterTemporaryStat CharacterTemporaryStats} that are a TwoState.
     * Is guaranteed to be sorted by their index.
     * @return
     */
    public static List<CharacterTemporaryStat> getAllCTS() {
        List<CharacterTemporaryStat> characterTemporaryStats = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            characterTemporaryStats.add(getCTSFromTwoStatIndex(i));
        }
        return characterTemporaryStats;
    }
}
