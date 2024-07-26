package net.swordie.ms.constants;

import net.swordie.ms.client.character.skills.info.SkillInfo;
import net.swordie.ms.client.jobs.Zero;
import net.swordie.ms.client.jobs.adventurer.*;
import net.swordie.ms.client.jobs.cygnus.BlazeWizard;
import net.swordie.ms.client.jobs.cygnus.DawnWarrior;
import net.swordie.ms.client.jobs.cygnus.NightWalker;
import net.swordie.ms.client.jobs.cygnus.ThunderBreaker;
import net.swordie.ms.client.jobs.legend.Aran;
import net.swordie.ms.client.jobs.legend.Evan;
import net.swordie.ms.client.jobs.legend.Phantom;
import net.swordie.ms.client.jobs.legend.Shade;
import net.swordie.ms.client.jobs.nova.AngelicBuster;
import net.swordie.ms.client.jobs.resistance.Demon;
import net.swordie.ms.client.jobs.resistance.Mechanic;
import net.swordie.ms.enums.BeastTamerBeasts;
import net.swordie.ms.loaders.SkillData;
import org.apache.log4j.Logger;

import java.util.*;

import static net.swordie.ms.client.jobs.legend.Aran.*;
import static net.swordie.ms.client.jobs.legend.Mercedes.STAGGERING_STRIKES;
import static net.swordie.ms.client.jobs.legend.Mercedes.STUNNING_STRIKES;
import static net.swordie.ms.client.jobs.nova.AngelicBuster.*;
import static net.swordie.ms.client.jobs.nova.Kaiser.*;
import static net.swordie.ms.client.jobs.resistance.Blaster.*;

/**
 * Created on 12/18/2017.
 */
public class SkillConstants {
    public static final short PASSIVE_HYPER_MIN_LEVEL = 140;
    public static final List<Short> ACTIVE_HYPER_LEVELS = Arrays.asList((short) 150, (short) 170, (short) 200);

    private static final Logger log = Logger.getLogger(SkillConstants.class);

    public static final short LINK_SKILL_1_LEVEL = 70;
    public static final short LINK_SKILL_2_LEVEL = 120;
    public static final short LINK_SKILL_3_LEVEL = 210;

    public static final byte PASSIVE_HYPER_JOB_LEVEL = 6;
    public static final byte ACTIVE_HYPER_JOB_LEVEL = 7;

    public static final int MAKING_SKILL_EXPERT_LEVEL = 10;
    public static final int MAKING_SKILL_MASTER_LEVEL = 11;
    public static final int MAKING_SKILL_MEISTER_LEVEL = 12;

    public static final int MINING_SKILL = 92010000;
    public static final int HERBALISM_SKILL = 92000000;

    public static final HashSet<Integer> KEYDOWN_SKILLS = new HashSet<>(
            Arrays.asList(
                    Shade.SPIRIT_INCARNATION,
                    Shade.SPIRIT_FRENZY,
                    BeastTamer.FISHY_SLAP,
                    BeastTamer.TORNADO_FLIGHT,
                    PinkBean.LETS_ROLL,
                    Demon.VITALITY_VEIL,
                    AngelicBuster.SOUL_RESONANCE,
                    BlazeWizard.DRAGON_BLAZE_FIRST,
                    Phantom.TEMPEST,
                    Magician.LIGHTNING_ORB,
                    Pirate.FALLING_STARS,
                    Pirate.BACKUP_BEATDOWN,
                    AngelicBuster.SUPREME_SUPERNOVA)
    );


    //custom skill cd's
    public static int getSkillCooldown(int skillId) {

        return switch (skillId) { //Sweeping Staff - BM Hyper
            //Sweeping Staff - BM Hyper
            case 32120052, 32120055, 32121052 -> //Sweeping Staff - BM Hyper
                    0;
            default -> -1;
        };

    }

    public static boolean isSkillNeedMasterLevel(int skillId) {
        if (isIgnoreMasterLevel(skillId)
                || (skillId / 1000000 == 92 && (skillId % 10000 == 0))
                || isMakingSkillRecipe(skillId)
                || isCommonSkill(skillId)
                || isNoviceSkill(skillId)
                || isFieldAttackObjSkill(skillId)) {
            return false;
        }
        int job = getSkillRootFromSkill(skillId);
        return skillId != 42120024 && !JobConstants.isBeastTamer((short) job)
                && (isAddedSpDualAndZeroSkill(skillId) || (JobConstants.getJobLevel((short) job) == 4 && !JobConstants.isZero((short) job)));
    }

    public static boolean isAddedSpDualAndZeroSkill(int skillId) {
        if (skillId > 101100101) {
            if (skillId > 101110203) {
                if (skillId == 101120104)
                    return true;
                return skillId == 101120204;
            } else {
                if (skillId == 101110203 || skillId == 101100201 || skillId == 101110102)
                    return true;
                return skillId == 101110200;
            }
        } else {
            if (skillId == 101100101)
                return true;
            if (skillId > 4331002) {
                if (skillId == 4340007 || skillId == 4341004)
                    return true;
                return skillId == 101000101;
            } else {
                if (skillId == 4331002 || skillId == 4311003 || skillId == 4321006)
                    return true;
                return skillId == 4330009;
            }
        }
    }

    public static int getSkillRootFromSkill(int skillId) {
        int prefix = skillId / 10000;
        if (prefix == 8000) {
            prefix = skillId / 100;
        }
        return prefix;
    }

    public static boolean isFieldAttackObjSkill(int skillId) {
        if (skillId <= 0) {
            return false;
        }
        int prefix = skillId / 10000;
        if (skillId / 10000 == 8000) {
            prefix = skillId / 100;
        }
        return prefix == 9500;
    }

    private static boolean isNoviceSkill(int skillId) {
        int prefix  = skillId / 10000;
        if (skillId / 10000 == 8000) {
            prefix = skillId / 100;
        }
        return JobConstants.isBeginnerJob((short) prefix);
    }

    private static boolean isCommonSkill(int skillId) {
        int prefix = skillId / 10000;
        if (skillId / 10000 == 8000) {
            prefix = skillId / 100;
        }
        return (prefix >= 800000 && prefix <= 800099) || prefix == 8001;
    }

    public static boolean isMakingSkillRecipe(int recipeId) {
        boolean result = false;
        if (recipeId / 1000000 != 92 || recipeId % 10000 == 1) {
            int v1 = 10000 * (recipeId / 10000);
            if (v1 / 1000000 == 92 && (v1 % 10000 == 0))
                result = true;
        }
        return result;
    }

    public static boolean isIgnoreMasterLevel(int skillId) {
        return switch (skillId) {
            case 1120012, 1320011, 2121009, 2221009, 2321010, 3210015, 4110012, 4210012, 4340009, 5120011, 5120012,
                 5220012, 5220014, 5320007, 5321004, 5321006, 21120011, 21120014, 21120020, 21120021, 22171069,
                 23120011, 23120012, 23120013, 23121008, 33120010, 35120014, 80001913 -> true;
            default -> false;
        };
    }

    public static boolean isKeyDownSkill(int skillId) {
        return skillId == 2321001 || skillId == 80001836 || skillId == 37121052 || skillId == 36121000 ||
                skillId == 37121003 || skillId == 36101001 || skillId == 33121114 || skillId == 33121214 ||
                skillId == 35121015 || skillId == 33121009 || skillId == 32121003 || skillId == 31211001 ||
                skillId == 31111005 || skillId == 30021238 || skillId == 31001000 || skillId == 31101000 ||
                skillId == 80001887 || skillId == 80001880 || skillId == 80001629 || skillId == 20041226 ||
                skillId == 60011216 || skillId == 65121003 || skillId == 80001587 || skillId == 131001008 ||
                skillId == 142111010 || skillId == 131001004 || skillId == 95001001 || skillId == 101110100 ||
                skillId == 101110101 || skillId == 101110102 || skillId == 27111100 || skillId == 12121054 ||
                skillId == 11121052 || skillId == 11121055 || skillId == 5311002 || skillId == 4341002 ||
                skillId == 5221004 || skillId == 5221022 || skillId == 3121020 || skillId == 3101008 ||
                skillId == 3111013 || skillId == 1311011 || skillId == 2221011 || skillId == 2221052 ||
                skillId == 25121030 || skillId == 27101202 || skillId == 25111005 || skillId == 23121000 ||
                skillId == 22171083 || skillId == 14121004 || skillId == 13111020 || skillId == 13121001 ||
                skillId == 14111006 || (skillId >= 80001389 && skillId <= 80001392) || skillId == 42121000 ||
                skillId == 42120003 || skillId == 5700010 || skillId == 5711021 || skillId == 5721001 ||
                skillId == 5721061 || skillId == 21120018 || skillId == 21120019 || skillId == 24121000 || skillId == 24121005;

    }

    public static boolean isEvanForceSkill(int skillId) {
        return switch (skillId) {
            case 22140022, 22111011, 22111012, 22110022, 22110023, 22111017, 80001894, 22171062, 22171063, 22141011,
                 22141012 -> true;
            default -> false;
        };
    }

    public static boolean isSuperNovaSkill(int skillID) {
        return skillID == 4221052 || skillID == 65121052;
    }

    public static boolean isRushBombSkill(int skillID) {
        return switch (skillID) {
            case 101120205, 101120203, 80011386, 101120200, 80011380, 61111113, 61111218, 61111111, 61111100, 40021186,
                 31201001, 27121201, 22140015, 22140024, 14111022, 5101014, 5301001, 12121001, 2221012, 5101012 -> true;
            default -> false;
        };
    }

    public static boolean isZeroSkill(int skillID) {
        int prefix = skillID / 10000;
        if(prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix == 10000 || prefix == 10100 || prefix == 10110 || prefix == 10111 || prefix == 10112;
    }

    public static boolean isUsercloneSummonedAbleSkill(int skillID) {
        return switch (skillID) {
            case 14001020, 14101020, 14101021, 14111020, 14111021, 14111022, 14111023, 14121001, 14121002, 14120045 ->
                    true;
            default -> false;
        };
    }

    public static boolean isNoconsumeUsebulletMeleeAttack(int skillID) {
        return skillID == 14121052 || skillID == 14121003 || skillID == 14000028 || skillID == 14000029;
    }

    public static boolean isScreenCenterAttackSkill(int skillID) {
        return skillID == 80001431 || skillID == 100001283 || skillID == 21121057 || skillID == 13121052 ||
                skillID == 14121052 || skillID == 15121052 || skillID == 80001429;
    }

    public static boolean isAranFallingStopSkill(int skillID) {
        return switch (skillID) {
            case 21110028, 21120025, 21110026, 21001010, 21000006, 21000007, 21110022, 21110023, 80001925, 80001926,
                 80001927, 80001936, 80001937, 80001938 -> true;
            default -> false;
        };
    }

    public static boolean isFlipAffectAreaSkill(int skillID) {
        return skillID == 33111013 || skillID == 33121016 || skillID == 33121012 || skillID == 131001207 ||
                skillID == 131001107 || skillID == 4121015 || skillID == 51120057 || skillID == Mechanic.DISTORTION_BOMB;
    }

    public static boolean isShootSkillNotConsumingBullets(int skillID) {
        int job = skillID / 10000;
        if (skillID / 10000 == 8000) {
            job = skillID / 100;
        }
        return switch (skillID) {
            case 80001279, 80001914, 80001915, 80001880, 80001629, 33121052, 33101002, 14101006, 13101020, 1078 -> true;
            default -> getDummyBulletItemIDForJob(job, 0, 0) > 0
                    || isShootSkillNotUsingShootingWeapon(skillID, false)
                    || isFieldAttackObjSkill(skillID);
        };
    }

    private static boolean isShootSkillNotUsingShootingWeapon(int skillID, boolean bySteal) {
        if(bySteal || (skillID >= 80001848 && skillID <= 80001850)) {
            return true;
        }
        return switch (skillID) { // ? was 26803624, guessing it's just a +1
            case 80001863, 80001880, 80001914, 80001915, 80001939, 101110204, 101110201, 101000202, 101100202, 80001858,
                 80001629, 80001829, 80001838, 80001856, 80001587, 80001418, 80001387, 61111215, 80001279, 61001101,
                 51121008, 51111007, 51001004, 36111010, 36101009, 31111005, 31111006, 31101000, 22110024, 22110014,
                 21120006, 21100007, 21110027, 21001009, 21000004, 5121013, 1078, 1079 -> true;
            default -> false;
        };
    }

    private static int getDummyBulletItemIDForJob(int job, int subJob, int skillID) {
        if ( job / 100 == 35 )
            return 2333000;
        if ( job / 10 == 53 || job == 501 || (job / 1000) == 0 && subJob == 2 )
            return 2333001;
        if ( JobConstants.isMercedes((short) job) )
            return 2061010;
        if ( JobConstants.isAngelicBuster((short) job) )
            return 2333001;
        // TODO:
//        if ( !JobConstants.isPhantom((short) job)
//                || !is_useable_stealedskill(skillID)
//                || (result = get_vari_dummy_bullet_by_cane(skillID), result <= 0) )
//        {
//            result = 0;
//        }
        return 0;
    }

    public static boolean isKeydownSkillRectMoveXY(int skillID) {
        return skillID == 13111020;
    }

    public static int getOriginalOfLinkedSkill(int skillID) {
        int result = 0;
        switch(skillID) {
            case 80010006:
                result = 110000800;
                break;
            case 80001040:
                result = 20021110;
                break;
            case 80001140:
                result = 50001214;
                break;
            case 80001155:
                result = 60011219;
                break;
            case 80000378:
                result = 30000077;
                break;
            case 80000334:
                result = 30000075;
                break;
            case 80000335:
                result = 30000076;
                break;
            case 80000369:
                result = 20010294;
                break;
            case 80000370:
                result = 20000297;
                break;
            case 80000333:
                result = 30000074;
                break;
            case 80000000:
                result = 110;
                break;
            case 80000001:
                result = 30010112;
                break;
            case 80000002:
                result = 20030204;
                break;
            case 80000005:
                result = 20040218;
                break;
            case 80000006:
                result = 60000222;
                break;
            case 80000047:
                result = 30020233;
                break;
            case 80000050:
                result = 30010241;
                break;
            case 80000066:
                result = 10000255;
                break;
            case 80000067:
                result = 10000256;
                break;
            case 80000068:
                result = 10000257;
                break;
            case 80000069:
                result = 10000258;
                break;
            case 80000070:
                result = 10000259;
                break;
            case 80000110:
                result = 100000271;
                break;
            case 80000169:
                result = 20050286;
                break;
            case 80000188:
                result = 140000292;
                break;
            case 80000004:
                result = 40020002;
                break;
            case 0:
                result = 0;
                break;
            default:
                log.error("Unknown corresponding link skill for link skill id " + skillID);
        }
        return result;
    }

    public static boolean isZeroAlphaSkill(int skillID) {
        return isZeroSkill(skillID) && skillID % 1000 / 100 == 2;
    }

    public static boolean isZeroBetaSkill(int skillID) {
        return isZeroSkill(skillID) && skillID % 1000 / 100 == 1;
    }

    public static boolean isLightmageSkill(int skillID) {
        int prefix = skillID / 10000;
        if(prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix / 100 == 27 || prefix == 2004;
    }

    public static boolean isLarknessDarkSkill(int skillID) {
        return skillID != 20041222 && isLightmageSkill(skillID) && skillID / 100 % 10 == 2;
    }

    public static boolean isLarknessLightSkill(int skillID) {
        return skillID != 20041222 && isLightmageSkill(skillID) && skillID / 100 % 10 == 1;
    }

    public static boolean isEquilibriumSkill(int skillID) {
        return skillID >= 20040219 && skillID <= 20040220;
    }

    public static int getAdvancedCountHyperSkill(int skillId) {
        return switch (skillId) {
            case 4121013 -> 4120051;
            case 5321012 -> 5320051;
            default -> 0;
        };
    }

    public static int getAdvancedAttackCountHyperSkill(int skillId) {
        return switch (skillId) {
            case 25121005 -> 25120148;
            case 31121001 -> 31120050;
            case 31111005 -> 31120044;
            case 22140023 -> 22170086;
            case 21120022, 21121015, 21121016, 21121017 -> 21120066;
            case 21120006 -> 21120049;
            case 21110020, 21111021 -> 21120047;
            case 15121002 -> 15120048;
            case 14121002 -> 14120045;
            case 15111022, 15120003 -> 15120045;
            case 51121008 -> 51120048;
            case 32111003 -> 32120047;
            case 35121016 -> 35120051;
            case 37110002 -> 37120045;
            case 51120057 -> 51120058;
            case 51121007 -> 51120051;
            case 65121007, 65121008, 65121101 -> 65120051;
            case 61121201, 61121100 -> 61120045;
            case 51121009 -> 51120058;
            case 13121002 -> 13120048;
            case 5121016, 5121017 -> 5120051;
            case 3121015 -> 3120048;
            case 2121006 -> 2120048;
            case 2221006 -> 2220048;
            case 1221011 -> 1220050;
            case 1120017, 1121008 -> 1120051;
            case 1221009 -> 1220048;
            case 4331000 -> 4340045;
            case 3121020 -> 3120051;
            case 3221017 -> 3220048;
            case 4221007 -> 4220048;
            case 4341009 -> 4340048;
            case 5121007 -> 5120048;
            case 5321004 -> 5320043;
            // if ( nSkillID != &loc_A9B1CF ) nothing done with line 172?
            case 12110028, 12000026, 12100028 -> 12120045;
            case 12120010 -> 12120045;
            case 12120011 -> 12120046;
            default -> 0;
        };
    }

    public static boolean isKinesisPsychicLockSkill(int skillId) {
        return switch (skillId) {
            case 142120000, 142120001, 142120002, 142120014, 142111002, 142100010, 142110003, 142110015 -> true;
            default -> false;
        };
    }

    public static int getActualSkillIDfromSkillID(int skillID) {
        return switch (skillID) {
            case 101120206 -> //Zero - Severe Storm Break (Tile)
                    101120204; //Zero - Adv Storm Break

            case 4221016 -> //Shadower - Assassinate 2
                    4221014; //Shadower - Assassinate 1

            case 41121020 -> //Hayato - Tornado Blade-Battoujutsu Link
                    41121017; //Tornado Blade

            case 41121021 -> //Hayato - Sudden Strike-Battoujutsu Link
                    41121018; //Sudden Strike

            case 5121017 -> //Bucc - Double Blast
                    5121016; //Bucc - Buccaneer Blast

            case 5101014 -> //Bucc - Energy Vortex
                    5101012; //Bucc - Tornado Uppercut

            case 5121020 -> //Bucc - Octopunch (Max Charge)
                    5121007; //Bucc - Octopunch

            case 5111013 -> //Bucc - Hedgehog Buster
                    5111002; //Bucc - Energy Burst

            case 5111015 -> //Bucc - Static Thumper
                    5111012; //Bucc - Static Thumper

            //DA - Exceed Double Slash 2
            //DA - Exceed Double Slash 3
            //DA - Exceed Double Slash 4
            case 31011004, 31011005, 31011006, 31011007 -> //DA - Exceed Double Slash Purple
                    31011000; //DA - Exceed Double Slash 1

            //DA - Exceed Demon Strike 2
            //DA - Exceed Demon Strike 3
            //DA - Exceed Demon Strike 4
            case 31201007, 31201008, 31201009, 31201010 -> //DA - Exceed Demon Strike Purple
                    31201000; //DA - Exceed Demon Strike 1

            //DA - Exceed Lunar Slash 2
            //DA - Exceed Lunar Slash 3
            //DA - Exceed Lunar Slash 4
            case 31211007, 31211008, 31211009, 31211010 -> //DA - Exceed Lunar Slash Purple
                    31211000; //DA - Exceed Lunar Slash 1

            //DA - Exceed Execution 2
            //DA - Exceed Execution 3
            //DA - Exceed Execution 4
            case 31221009, 31221010, 31221011, 31221012 -> //DA - Exceed Execution Purple
                    31221000; //DA - Exceed Execution 1

            case 31211002 -> //DA - Shield Charge (Spikes)
                    31211011; //DA - Shield Charge (Rush)

            case 61120219 -> //Kaiser - Dragon Slash (Final Form)
                    61001000; //Kaiser - Dragon Slash 1

            case 61111215 -> //Kaiser - Flame Surge (Final Form)
                    61001101; //Kaiser - Flame Surge

            case 61111216 -> //Kaiser - Impact Wave (Final Form)
                    61101100; //Kaiser - Impact Wave

            case 61111217 -> //Kaiser - Piercing Blaze (Final Form)
                    61101101; //Kaiser - Piercing Blaze

            case 61111111 -> //Kaiser - Wing Beat (Final Form)
                    61111100; //Kaiser - Wing Beat

            case 61111219 -> //Kaiser - Pressure Chain (Final Form)
                    61111101; //Kaiser - Pressure Chain

            case 61121201 -> //Kaiser - Gigas Wave (Final Form)
                    61121100; //Kaiser - Gigas Wave

            case 61121222 -> //Kaiser - Inferno Breath (Final Form)
                    61121105; //Kaiser - Inferno Breath

            case 61121203 -> //Kaiser - Dragon Barrage (Final Form)
                    61121102; //Kaiser - Dragon Barrage

            case 61121221 -> //Kaiser - Blade Burst (Final Form)
                    61121104; //Kaiser - Blade Burst

            case 14101021 -> //NW - Quint. Throw Finisher
                    14101020; //NW - Quint. Throw

            case 14111021 -> //NW - Quad Throw Finisher
                    14111020; //NW - Quad Throw

            case 14121002 -> //NW - Triple Throw Finisher
                    14121001; //NW - Triple Throw

            case STAGGERING_STRIKES -> STUNNING_STRIKES;
            case SMASH_WAVE_COMBO -> SMASH_WAVE;
            case FINAL_BLOW_COMBO, FINAL_BLOW_SMASH_SWING_COMBO -> FINAL_BLOW;
            case SOUL_SEEKER_ATOM -> SOUL_SEEKER;
            case 65101006 -> //AB - Lovely Sting Explosion
                    LOVELY_STING;
            case 65121007, 65121008 -> TRINITY;
            case REVOLVING_CANNON_2, REVOLVING_CANNON_3 -> REVOLVING_CANNON;
            default -> skillID;
        };
    }

    public static int getKaiserGaugeIncrementBySkill(int skillID) {
        HashMap<Integer, Integer> hashMapIncrement = new HashMap<>();
        hashMapIncrement.put(DRAGON_SLASH_1, 1);
        hashMapIncrement.put(DRAGON_SLASH_2, 3);
        hashMapIncrement.put(DRAGON_SLASH_3, 4);
        hashMapIncrement.put(DRAGON_SLASH_1_FINAL_FORM, 1);

        hashMapIncrement.put(FLAME_SURGE, 2);
        hashMapIncrement.put(FLAME_SURGE_FINAL_FORM, 2);

        hashMapIncrement.put(IMPACT_WAVE, 5);
        hashMapIncrement.put(IMPACT_WAVE_FINAL_FORM, 0);

        hashMapIncrement.put(PIERCING_BLAZE, 5);
        hashMapIncrement.put(PIERCING_BLAZE_FINAL_FORM, 0);

        hashMapIncrement.put(WING_BEAT, 2);
        hashMapIncrement.put(WING_BEAT_FINAL_FORM, 1);

        hashMapIncrement.put(PRESSURE_CHAIN, 8);
        hashMapIncrement.put(PRESSURE_CHAIN_FINAL_FORM, 0);

        hashMapIncrement.put(GIGA_WAVE, 8);
        hashMapIncrement.put(GIGA_WAVE_FINAL_FORM, 0);

        hashMapIncrement.put(INFERNO_BREATH, 14);
        hashMapIncrement.put(INFERNO_BREATH_FINAL_FORM, 0);

        hashMapIncrement.put(DRAGON_BARRAGE, 6);
        hashMapIncrement.put(DRAGON_BARRAGE_FINAL_FORM, 0);

        hashMapIncrement.put(BLADE_BURST, 6);
        hashMapIncrement.put(BLADE_BURST_FINAL_FORM, 0);

        hashMapIncrement.put(TEMPEST_BLADES_FIVE, 15);
        hashMapIncrement.put(TEMPEST_BLADES_FIVE_FF, 0);

        hashMapIncrement.put(TEMPEST_BLADES_THREE, 15);
        hashMapIncrement.put(TEMPEST_BLADES_THREE_FF, 0);

        return hashMapIncrement.getOrDefault(skillID, 0);
    }

    public static boolean isEvanFusionSkill(int skillID) {
        return switch (skillID) {
            case 22110014, 22110025, 22140014, 22140015, 22140024, 22140023, 22170065, 22170066, 22170067, 22170094 ->
                    true;
            default -> false;
        };
    }

    public static boolean isShikigamiHauntingSkill(int skillID) {
        return switch (skillID) {
            case 80001850, 42001000, 42001005, 42001006, 40021185, 80011067 -> true;
            default -> false;
        };
    }

    public static boolean isStealableSkill(int skillID) {
        // TODO
        return false;
    }

    public static int getStealSkillManagerTabFromSkill(int skillID) {
        int smJobID;

        //Hyper Skills
        if(skillID % 100 == 54) {
            return 5;
        }
        return switch (skillID / 10000) {

            // 1st Job Tab
            case 100, 200, 300, 400, 430, 500, 501 -> 1;

            // 2nd Job Tab
            case 110, 120, 130, 210, 220, 230, 310, 320, 410, 420, 431, 432, 510, 520, 530 -> 2;

            // 3rd Job Tab
            case 111, 121, 131, 211, 221, 231, 311, 321, 411, 421, 433, 511, 521, 531 -> 3;

            // 4th job Tab
            case 112, 122, 132, 212, 222, 232, 312, 322, 412, 422, 434, 512, 522, 532 -> 4;
            default -> -1;
        };
    }

    public static int getMaxPosBysmJobID(int smJobID) {
        int maxPos = switch (smJobID) {
            case 1, 2 -> 3;
            case 3 -> 2;
            case 4, 5 -> 1;
            default -> 0;
        };
        return maxPos;
    }

    public static int getStartPosBysmJobID(int smJobID) {
        int startPos = switch (smJobID) {
            case 1 -> 0;
            case 2 -> 4;
            case 3 -> 8;
            case 4 -> 11;
            case 5 -> 13;
            default -> 0;
        };
        return startPos;
    }

    public static int getImpecSkillIDBysmJobID(int smJobID) {
        int impecSkillID = switch (smJobID) {
            case 1 -> 24001001;
            case 2 -> 24101001;
            case 3 -> 24111001;
            case 4 -> 24121001;
            case 5 -> 24121054;
            default -> 0;
        };
        return impecSkillID;
    }

    public static int getSMJobIdByImpecSkillId(int impecSkillId) {
        return switch (impecSkillId) {
            case 24001001 ->  // 1st Job
                    1;
            case 24101001 ->  // 2nd Job
                    2;
            case 24111001 ->  // 3rd job
                    3;
            case 24121001 ->  // 4th Job
                    4;
            case 24121054 ->  // Hyper Skill
                    5;
            default -> -1;
        };
    }

    public static boolean isIceSkill(int skillID) {
        return switch (skillID) {
            case Magician.CHILLING_STEP, Magician.COLD_BEAM, Magician.ICE_STRIKE, Magician.GLACIER_CHAIN,
                 Magician.FREEZING_BREATH, Magician.BLIZZARD, Magician.FROZEN_ORB, Magician.ELQUINES -> true;
            default -> false;
        };
    }

    public static int getLinkSkillByJob(short job) {
        if (JobConstants.isCannonShooter(job)) { // Pirate Blessing
            return 80000000;
        } else if (JobConstants.isCygnusKnight(job)) { // Cygnus Blessing
            return 80000070;
        }  else if (JobConstants.isAran(job)) { // Combo Kill Blessing
            return 80000370;
        } else if (JobConstants.isEvan(job)) { // Rune Persistence
            return 80000369;
        } else if (JobConstants.isMercedes(job)) { // Elven Blessing
            return 80001040;
        } else if (JobConstants.isDemonSlayer(job)) { // Fury Unleashed
            return 80000001;
        } else if (JobConstants.isDemonAvenger(job)) { // Wild Rage
            return 80000050;
        } else if (JobConstants.isJett(job)) { // Core Aura
            return 80001151;
        } else if (JobConstants.isPhantom(job)) { // Phantom Instinct
            return 80000002;
        } else if (JobConstants.isMihile(job)) { // Knight's Watch
            return 80001140;
        } else if (JobConstants.isLuminous(job)) { // Light Wash
            return 80000005;
        } else if (JobConstants.isAngelicBuster(job)) { // Terms and Conditions
            return 80001155;
        } else if (JobConstants.isHayato(job)) { // Keen Edge
            return 80000003;
        } else if (JobConstants.isKanna(job)) { // Elementalism
            return 80000004;
        } else if (JobConstants.isKaiser(job)) { // Iron Will
            return 80000006;
        } else if (JobConstants.isXenon(job)) { // Hybrid Logic
            return 80000047;
        } else if (JobConstants.isZero(job)) { // Rhinne's Blessing
            return 80000110;
        } else if (JobConstants.isKinesis(job)) { // Judgment
            return 80000188;
        } else if (JobConstants.isBeastTamer(job)) { // Focus Spirit
            return 80010006;
        }
        return 0;
    }

    public static int getLinkSkillLevelByCharLevel(short level) {
        int res = 0;
        if (level >= LINK_SKILL_3_LEVEL) {
            res = 3;
        } else if (level >= LINK_SKILL_2_LEVEL) {
            res = 2;
        } else if (level >= LINK_SKILL_1_LEVEL) {
            res = 1;
        }
        return res;
    }

    public static int getLinkedSkill(int skillID) {
        return switch (skillID) {
            case Zero.STORM_BREAK_INIT -> Zero.STORM_BREAK;
            case Zero.ADV_STORM_BREAK_SHOCK_INIT -> Zero.ADV_STORM_BREAK;
            default -> skillID;
        };
    }

    public static boolean isPassiveSkill_NoPsdSkillsCheck(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si != null && si.isPsd();
    }

    public static boolean isPassiveSkill(int skillId) {
        SkillInfo si = SkillData.getSkillInfoById(skillId);
        return si != null && si.isPsd() && si.getPsdSkills().isEmpty();
    }

    public static boolean isHyperstatSkill(int skillID) {
        return skillID >= 80000400 && skillID <= 80000418;
    }

    public static int getTotalHyperStatSpByLevel(short currentlevel) {
        int sp = 0;
        for (short level = 140; level <= currentlevel; level++) {
            sp += getHyperStatSpByLv(level);
        }
        return sp;
    }

    public static int getHyperStatSpByLv(short level) {
        return 3 + ((level - 140) / 10);
    }

    public static int getNeededSpForHyperStatSkill(int lv) {
        return switch (lv) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 4;
            case 4 -> 8;
            case 5 -> 10;
            case 6 -> 15;
            case 7 -> 20;
            case 8 -> 25;
            case 9 -> 30;
            case 10 -> 35;
            default -> 0;
        };
    }

    public static int getTotalNeededSpForHyperStatSkill(int lv) {
        return switch (lv) {
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 7;
            case 4 -> 15;
            case 5 -> 25;
            case 6 -> 40;
            case 7 -> 60;
            case 8 -> 85;
            case 9 -> 115;
            case 10 -> 150;
            default -> 0;
        };
    }

    public static boolean isUnregisteredSkill(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix != 9500 && skillID / 10000000 == 9;
    }

    public static boolean isHomeTeleportSkill(int skillId) {
        return switch (skillId) { // All Adventurers
            // All KoC
            // Mercedes
            // Luminous
            // Shade
            // Kaiser
            // All Resistance
            // Hayato
            // Kanna
            case Warrior.MAPLE_RETURN, BeastTamer.HOMEWARD_BOUND, Kinesis.RETURN_KINESIS, DawnWarrior.IMPERIAL_RECALL,
                 Aran.RETURN_TO_RIEN, Evan.BACK_TO_NATURE, Phantom.TO_THE_SKIES, AngelicBuster.DAY_DREAMER,
                 Demon.SECRET_ASSEMBLY, Zero.TEMPLE_RECALL -> true;
            default -> false;
        };
    }

    public static boolean isArmorPiercingSkill(int skillId) {
        return switch (skillId) {
            case 3120017, 95001000, 3120008, 3100001, 3100010 -> false;
            default -> true;
        };
    }

    public static int getBaseSpByLevel(short level) {
        return level > 140 ? 0
                : level > 130 ? 6
                : level > 120 ? 5
                : level > 110 ? 4
                : 3;
    }

    public static int getTotalPassiveHyperSpByLevel(short level) {
        return level < 140 ? 0 : (level - 130) / 10;
    }

    public static int getTotalActiveHyperSpByLevel(short level) {
        return level < 140 ? 0 : level < 170 ? 1 : level < 200 ? 2 : 3;
    }

    public static boolean isGuildSkill(int skillID) {
        int prefix = skillID / 10000;
        if (prefix == 8000) {
            prefix = skillID / 100;
        }
        return prefix == 9100;
    }

    public static boolean isGuildContentSkill(int skillID) {
        return (skillID >= 91000007 && skillID <= 91000015) || (skillID >= 91001016 && skillID <= 91001021);
    }

    public static boolean isGuildNoblesseSkill(int skillID) {
        return skillID >= 91001022 && skillID <= 91001024;
    }

    public static boolean isMultiAttackCooldownSkill(int skillID) {
        return switch (skillID) {
            case 5311010, 5711021, 22171063, 22141012, ThunderBreaker.GALE, ThunderBreaker.TYPHOON, Demon.DEMON_CRY,
                 Mechanic.DISTORTION_BOMB, BALLISTIC_HURRICANE, BALLISTIC_HURRICANE_1 -> true;
            default -> false;
        };
    }

    public static boolean isMatching(int rootId, int job) {
        boolean matchingStart = job / 100 == rootId / 100;
        boolean matching = matchingStart;
        if (matchingStart && rootId % 100 != 0) {
            // job path must match
            matching = (rootId % 100) / 10 == (job % 100) / 10;
        }
        return matching;
    }

    // is_skill_from_item(signed int nSkillID)
    public static boolean isSkillFromItem(int skillID) {
        return switch (skillID) { // New Destiny
            // Dawn Shield
            // Dawn Shield
            // Divine Guardian
            // Divine Shield
            // Divine Brilliance
            // Monolith
            // Scouter
            // Ribbit Ring
            // Krrr Ring
            // Rawr Ring
            // Pew Pew Ring
            // Elunarium Power (ATT & M. ATT)
            // Elunarium Power (Skill EXP)
            // Elunarium Power (Boss Damage)
            // Elunarium Power (Ignore Enemy DEF)
            // Elunarium Power (Crit Rate)
            // Elunarium Power (Crit Damage)
            // Elunarium Power (Status Resistance)
            // Elunarium Power (All Stats)
            // Firestarter Ring
            // Rope Lift
            // Rope Lift
            // Scouter
            case 80011123, 80011247, 80011248, 80011249, 80011250, 80011251, 80011261, 80011295, 80011346, 80011347,
                 80011348, 80011349, 80011475, 80011476, 80011477, 80011478, 80011479, 80011480, 80011481, 80011482,
                 80011492, 80001768, 80001705, 80001941, 80010040 -> // Altered Fate
                    true;
            default ->
                // Tower of Oz skill rings
                    (skillID >= 80001455 && skillID <= 80001479);
        };
    }

    public static int getHyperPassiveSkillSpByLv(int level) {
        // 1 sp per 10 levels, starting at 140, ending at 220
        return level >= 140 && level <= 220 && level % 10 == 0 ? 1 : 0;
    }

    public static int getHyperActiveSkillSpByLv(int level) {
        return level == 150 || level == 170 || level == 200 ? 1 : 0;
    }

    public static int getNoviceSkillRoot(short job) {
        if (job / 100 == 22 || job == 2001) {
            return JobConstants.JobEnum.EVAN_NOOB.getJobId();
        }
        if (job / 100 == 23 || job == 2002) {
            return JobConstants.JobEnum.MERCEDES.getJobId();
        }
        if (job / 100 == 24 || job == 2003) {
            return JobConstants.JobEnum.PHANTOM.getJobId();
        }
        if (JobConstants.isDemon(job)) {
            return JobConstants.JobEnum.DEMON_SLAYER.getJobId();
        }
        if (JobConstants.isMihile(job)) {
            return JobConstants.JobEnum.NAMELESS_WARDEN.getJobId();
        }
        if (JobConstants.isLuminous(job)) {
            return JobConstants.JobEnum.LUMINOUS.getJobId();
        }
        if (JobConstants.isAngelicBuster(job)) {
            return JobConstants.JobEnum.ANGELIC_BUSTER.getJobId();
        }
        if (JobConstants.isXenon(job)) {
            return JobConstants.JobEnum.XENON.getJobId();
        }
        if (JobConstants.isShade(job)) {
            return JobConstants.JobEnum.SHADE.getJobId();
        }
        if (JobConstants.isKinesis(job)) {
            return JobConstants.JobEnum.KINESIS_0.getJobId();
        }
        if (JobConstants.isBlaster(job)) {
            return JobConstants.JobEnum.CITIZEN.getJobId();
        }
        if (JobConstants.isHayato(job)) {
            return JobConstants.JobEnum.HAYATO.getJobId();
        }
        if (JobConstants.isKanna(job)) {
            return JobConstants.JobEnum.KANNA.getJobId();
        }
        return 1000 * (job / 1000);
    }

    public static int getNoviceSkillFromRace(int skillID) {
        if (skillID == 50001215 || skillID == 10001215) {
            return 1005;
        }
        if (isCommonSkill(skillID) || (skillID >= 110001500 && skillID <= 110001504)) {
            return skillID;
        }
        if (isNoviceSkill(skillID)) {
            return skillID % 10000;
        }
        return 0;
    }

    public static int getBuffSkillItem(int buffSkillID) {
        int novice = getNoviceSkillFromRace(buffSkillID);
        return switch (novice) {
            // Angelic Blessing
            case 86 -> 2022746;
            // Dark Angelic Blessing
            case 88 -> 2022747;
            // Angelic Blessing
            case 91 -> 2022764;
            // White Angelic Blessing
            case 180 -> 2022823;
            // Lightning God's Blessing
            case 80000086 -> 2023189;
            // White Angelic Blessing
            case 80000155 -> 2022823;
            // Lightning God's Blessing
            case 80010065 -> 2023189;
            // Goddess' Guard
            case 80011150 -> 1112932;
            default -> 0;
        };
    }

    public static String getMakingSkillName(int skillID) {
        return switch (skillID) {
            case 92000000 -> "Herbalism";
            case 92010000 -> "Mining";
            case 92020000 -> "Smithing";
            case 92030000 -> "Accessory Crafting";
            case 92040000 -> "Alchemy";
            default -> null;
        };
    }

    public static int recipeCodeToMakingSkillCode(int skillID) {
        return 10000 * (skillID / 10000);
    }

    public static int getNeededProficiency(int level) {
        if (level <= 0 || level >= MAKING_SKILL_EXPERT_LEVEL) {
            return 0;
        }
        return ((100 * level * level) + (level * 400)) / 2;
    }

    public static boolean isSynthesizeRecipe(int recipeID) {
        return isMakingSkillRecipe(recipeID) && recipeID % 10000 == 9001;
    }

    public static boolean isDecompositionRecipeScroll(int recipeID) {
        return isMakingSkillRecipe(recipeID)
                && recipeCodeToMakingSkillCode(recipeID) == 92040000
                && recipeID - 92040000 >= 9003
                && recipeID - 92040000 <= 9006;
    }

    public static boolean isDecompositionRecipeCube(int recipeID) {
        return isMakingSkillRecipe(recipeID) && recipeCodeToMakingSkillCode(recipeID) == 92040000 && recipeID == 92049002;
    }

    public static boolean isDecompositionRecipe(int recipeID) {
        if (isMakingSkillRecipe(recipeID) && recipeCodeToMakingSkillCode(recipeID) == 92040000 && recipeID == 92049000
         || isDecompositionRecipeScroll(recipeID)
         || isDecompositionRecipeCube(recipeID)) {
            return true;
        }
        return false;
    }

    public static int getFairyBlessingByJob(short job) {
        short beginJob = JobConstants.JobEnum.getJobById(job).getBeginnerJobId();
        // xxxx0012, where xxxx is the "0th" job
        return beginJob * 10000 + 12;
    }

    public static int getEmpressBlessingByJob(short job) {
        short beginJob = JobConstants.JobEnum.getJobById(job).getBeginnerJobId();
        // xxxx0073, where xxxx is the "0th" job
        return beginJob * 10000 + 73;
    }

    public static int getSoaringByJob(short job) {
        short beginJob = JobConstants.JobEnum.getJobById(job).getBeginnerJobId();
        // xxxx1026, where xxxx is the "0th" job
        return beginJob * 10000 + 1026;
    }

    public static boolean isBeginnerSpAddableSkill(int skillID) {
        return skillID == 1000 || skillID == 1001 || skillID == 1002 || skillID == 140000291 || skillID == 30001000
                || skillID == 30001001 || skillID == 30001002;
    }

    public static boolean isKinesisPsychicAreaSkill(int skillId){
        return switch (skillId) {
            case 142001002, 142120003, 142101009, 142111006, 142111007, 142121005, 142121030 -> true;
            default -> false;
        };
    }

    public static boolean isNoConsumeBullet(int skillID) {
        return switch (skillID) {
            case 14111023, 14111022, 14121052, NightWalker.SHADOW_BAT_ATOM, NightWalker.SHADOW_STITCH -> true;
            default ->
                // Tower of Oz skill rings
                    (skillID >= 80001455 && skillID <= 80001479);
        };
    }

    public static BeastTamerBeasts getBeastFromSkill(int skillId) {
        return switch (skillId / 10000) {
            case 11200 -> BeastTamerBeasts.Bear;
            case 11210 -> BeastTamerBeasts.Leopard;
            case 11211 -> BeastTamerBeasts.Bird;
            case 11212 -> BeastTamerBeasts.Cat;
            default -> BeastTamerBeasts.None;
        };
    }

    public static boolean isKeydownCDSkill(int nSkillID)
    {
        return KEYDOWN_SKILLS.contains(nSkillID);
    }
}
