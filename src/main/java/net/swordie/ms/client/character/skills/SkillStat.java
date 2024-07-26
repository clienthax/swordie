package net.swordie.ms.client.character.skills;

import net.swordie.ms.enums.BaseStat;

/**
 * Created on 12/20/2017.
 */
public enum SkillStat {
    x,
    y,
    z,
    q,
    s,
    u,
    w,
    attackCount,
    rb,
    damage,
    lt,
    mpCon,
    mobCount,
    damAbsorbShieldR,
    pddX,
    mhpR,
    psdJump,
    speedMax,
    stanceProp,
    psdSpeed,
    lv2mhp,
    forceCon,
    cooltime,
    indieAsrR,
    indiePad,
    indieMad,
    indiePdd,
    indieMdd,
    indieTerR,
    indieEva,
    indieAcc,
    indieBooster,
    indieSpeed,
    indieJump,
    range,
    time,
    cooltimeMS,
    subTime,
    strX,
    padX,
    bdR,
    damR,
    ignoreMobpdpR,
    mobCountDamR,
    actionSpeed,
    mastery,
    rb2,
    lt2,
    v,
    terR,
    mddX,
    asrR,
    MDF,
    cr,
    prop,
    ballCount,
    t,
    dotInterval,
    dotTime,
    criticaldamageMin,
    criticaldamageMax,
    dot,
    ballDelay,
    ballDelay1,
    pdR,
    dexX,
    mmpR,
    madR,
    lukX,
    intX,
    hcProp,
    hcCooltime,
    hcTime,
    subProp,
    mp,
    hcHp,
    hp,
    indieCr,
    indieDamR,
    mhpX,
    targetPlus,
    indieMaxDamageOverR,
    indieMaxDamageOver,
    damPlus,
    ar,
    madX,
    selfDestruction,
    pddR,
    mddR,
    speed,
    evaX,
    accX,
    onActive,
    jump,
    summonCount,
    acc,
    eva,
    epdd,
    emdd,
    indieMmp,
    indieMhp,
    pdd,
    mdd,
    bulletCount,
    mdd2pdd,
    lv2mmp,
    indiePddR,
    epad,
    attackDelay,
    mdR,
    hcSubTime,
    mad,
    damageToBoss,
    coolTimeR,
    w2,
    u2,
    s2,
    q2,
    v2,
    mesoR,
    dropR,
    expR,
    indieExp,
    indiePadR,
    indieMadR,
    hcSummonHp,
    er,
    indieMhpR,
    indieBDR,
    ppRecovery,
    ballDelay0,
    ballDelay2,
    bulletConsume,
    ignoreMobDamR,
    indieStance,
    dotSuperpos,
    dotTickDamR,
    ppCon,
    ppReq,
    indiePMdR,
    bufftimeR,
    rb3,
    rb4,
    lt3,
    lt4,
    hpCon,
    areaDotCount,
    hcSubProp,
    costmpR,
    MDamageOver,
    variableRect, // null val
    attackPoint, // null val
    property, // null val
    emad,
    ballDelay3,
    emhp,
    mpConReduce,
    indieMmpR,
    indieIgnoreMobpdpR,
    gauge,
    fixdamage,
    hpRCon,
    padR,
    hcReflect,
    reduceForceR,
    timeRemainEffect,
    dex,
    killRecoveryR,
    accR,
    emmp,
    powerCon,
    mmpX,
    epCon,
    kp,
    a,
    ignoreCounter,
    action,
    evaR,
    damageTW3,
    damageTW2,
    damageTW4,
    pad,
    indieAllStat,
    bulletSpeed,
    morph,
    itemConsume,
    nbdR,
    psdIncMaxDam,
    strFX,
    dexFX,
    lukFX,
    intFX,
    pdd2mdd,
    acc2mp,
    eva2hp,
    str2dex,
    dex2str,
    int2luk,
    luk2int,
    luk2dex,
    dex2luk,
    lv2pad,
    lv2mad,
    tdR,
    minionDeathProp,
    abnormalDamR,
    acc2dam,
    pdd2dam,
    mdd2dam,
    pdd2mdx,
    mdd2pdx,
    nocoolProp,
    passivePlus,
    mpConEff,
    lv2damX,
    summonTimeR,
    expLossReduceR,
    onHitHpRecoveryR,
    onHitMpRecoveryR,
    pdr,
    mhp2damX,
    mmp2damX,
    finalAttackDamR,
    guardProp,
    mob, // null val
    extendPrice,
    priceUnit,
    period,
    price,
    reqGuildLevel,
    mileage,
    disCountR,
    pqPointR,
    mesoG,
    itemUpgradeBonusR,
    itemCursedProtectR,
    itemTUCProtectR,
    igpCon,
    gpCon,
    iceGageCon,
    PVPdamage,
    lv2str,
    lv2dex,
    lv2int,
    lv2luk,
    orbCount,
    dotHealHPPerSecondR,
    ballDelay6,
    ballDelay7,
    ballDelay4,
    ballDelay5,
    ballDamage,
    ballAttackCount,
    ballMobCount,
    delay,
    strR,
    dexR,
    intR,
    lukR,
    OnActive,
    PVPdamageX,
    indieMDF,
    soulmpCon,
    prob,
    indieMddR,
    indieDrainHP,
    trembling,
    incMobRateDummy,
    fixCoolTime,
    indieForceSpeed,
    indieForceJump,
    itemCon,
    itemConNo,
    ;

    public static SkillStat getSkillStatByString(String s) {
        for(SkillStat skillStat : SkillStat.values()) {
            if(skillStat.toString().equals(s)) {
                return skillStat;
            }
        }
        return null;
    }

    public BaseStat getBaseStat() {
        return switch (this) {
            case pddX, mdd2pdd, pdd, epdd, indiePdd -> BaseStat.pdd;
            case pddR, indiePddR -> BaseStat.pddR;
            case mddX, pdd2mdd, emdd, mdd, indieMdd -> BaseStat.mdd;
            case mddR, indieMddR -> BaseStat.mddR;
            case lv2mhp -> BaseStat.mhpLv;
            case mhpX, emhp, indieMhp, hcHp, eva2hp -> BaseStat.mhp;
            case mhpR, indieMhpR -> BaseStat.mhpR;
            case lv2mmp -> BaseStat.mmpLv;
            case mmpX, indieMmp, emmp -> BaseStat.mmp;
            case mmpR, indieMmpR -> BaseStat.mmpR;
            case psdSpeed, speed, speedMax, indieForceSpeed, indieSpeed -> BaseStat.speed;
            case psdJump, indieForceJump, indieJump, jump -> BaseStat.jump;
            case stanceProp, indieStance -> BaseStat.stance;
            case asrR, indieAsrR -> BaseStat.asr;
            case pad, padX, indiePad, epad -> BaseStat.pad;
            case lv2pad -> BaseStat.padLv;
            case padR, indiePadR -> BaseStat.padR;
            case indieMad, mad, madX, emad -> BaseStat.mad;
            case lv2mad -> BaseStat.madLv;
            case madR, indieMadR -> BaseStat.madR;
            case indieTerR, terR -> BaseStat.ter;
            case indieEva, eva -> BaseStat.eva;
            case indieBooster -> BaseStat.booster;
            case mastery -> BaseStat.mastery;
            case strFX, strX, dex2str -> BaseStat.str;
            case lv2str -> BaseStat.strLv;
            case strR -> BaseStat.strR;
            case dex, dexFX, dexX, luk2dex, str2dex -> BaseStat.dex;
            case lv2dex -> BaseStat.dexLv;
            case dexR -> BaseStat.dexR;
            case intFX, intX, luk2int -> BaseStat.inte;
            case lv2int -> BaseStat.intLv;
            case intR -> BaseStat.intR;
            case lukFX, lukX, dex2luk, int2luk -> BaseStat.luk;
            case lv2luk -> BaseStat.lukLv;
            case lukR -> BaseStat.lukR;
            case bdR, indieBDR, nbdR -> BaseStat.bd;
            case ignoreMobpdpR, indieIgnoreMobpdpR -> BaseStat.ied;
            case indieAllStat -> BaseStat.allStat;
            case criticaldamageMin -> BaseStat.minCd;
            case criticaldamageMax -> BaseStat.maxCd;
            case cr, indieCr -> BaseStat.cr;
            case expR, indieExp -> BaseStat.expR;
            case dropR -> BaseStat.dropR;
            case mesoR, mesoG -> BaseStat.mesoR;
            case hp -> BaseStat.hpRecovery;
            case mp -> BaseStat.mpRecovery;
            case bufftimeR -> BaseStat.buffTimeR;
            default -> null;
        };
    }
}
