package net.swordie.ms.loaders;

public class Loaders {

    private static Loaders INSTANCE;
    private final DropData dropData = new DropData();
    private final EffectData effectData = new EffectData();
    private final EtcData etcData = new EtcData();
    private final FieldData fieldData = new FieldData();
    private final ItemData itemData = new ItemData();
    private final MobData mobData = new MobData();
    private final MonsterCollectionData monsterCollectionData = new MonsterCollectionData();
    private final NpcData npcData = new NpcData();
    private final QuestData questData = new QuestData();
    private final ReactorData reactorData = new ReactorData();
    private final SkillData skillData = new SkillData();
    private final StringData stringData = new StringData();

    public static Loaders getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Loaders();
        }

        return INSTANCE;
    }

    public DropData getDropData() {
        return dropData;
    }

    public EffectData getEffectData() {
        return effectData;
    }

    public EtcData getEtcData() {
        return etcData;
    }

    public FieldData getFieldData() {
        return fieldData;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public MobData getMobData() {
        return mobData;
    }

    public MonsterCollectionData getMonsterCollectionData() {
        return monsterCollectionData;
    }

    public NpcData getNpcData() {
        return npcData;
    }

    public QuestData getQuestData() {
        return questData;
    }

    public ReactorData getReactorData() {
        return reactorData;
    }

    public SkillData getSkillData() {
        return skillData;
    }

    public StringData getStringData() {
        return stringData;
    }

}
