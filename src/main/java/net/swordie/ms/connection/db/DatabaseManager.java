package net.swordie.ms.connection.db;

import net.swordie.ms.client.Account;
import net.swordie.ms.client.LinkSkill;
import net.swordie.ms.client.User;
import net.swordie.ms.client.alliance.Alliance;
import net.swordie.ms.client.anticheat.Offense;
import net.swordie.ms.client.anticheat.OffenseManager;
import net.swordie.ms.client.character.*;
import net.swordie.ms.client.character.avatar.AvatarData;
import net.swordie.ms.client.character.avatar.AvatarLook;
import net.swordie.ms.client.character.cards.CharacterCard;
import net.swordie.ms.client.character.cards.MonsterBookInfo;
import net.swordie.ms.client.character.damage.DamageSkinSaveData;
import net.swordie.ms.client.character.items.Equip;
import net.swordie.ms.client.character.items.Inventory;
import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.client.character.items.PetItem;
import net.swordie.ms.client.character.keys.FuncKeyMap;
import net.swordie.ms.client.character.keys.Keymapping;
import net.swordie.ms.client.character.potential.CharacterPotential;
import net.swordie.ms.client.character.quest.Quest;
import net.swordie.ms.client.character.quest.QuestManager;
import net.swordie.ms.client.character.quest.progress.*;
import net.swordie.ms.client.character.skills.ChosenSkill;
import net.swordie.ms.client.character.skills.Skill;
import net.swordie.ms.client.character.skills.StolenSkill;
import net.swordie.ms.client.friend.Friend;
import net.swordie.ms.client.guild.*;
import net.swordie.ms.client.guild.bbs.BBSRecord;
import net.swordie.ms.client.guild.bbs.BBSReply;
import net.swordie.ms.client.trunk.Trunk;
import net.swordie.ms.handlers.EventManager;
import net.swordie.ms.life.Familiar;
import net.swordie.ms.life.Merchant.EmployeeTrunk;
import net.swordie.ms.life.Merchant.MerchantItem;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.loaders.containerclasses.EquipDrop;
import net.swordie.ms.loaders.containerclasses.MonsterCollectionGroupRewardInfo;
import net.swordie.ms.loaders.containerclasses.MonsterCollectionMobInfo;
import net.swordie.ms.loaders.containerclasses.MonsterCollectionSessionRewardInfo;
import net.swordie.ms.world.shop.NpcShopItem;
import net.swordie.ms.world.shop.cashshop.CashItemInfo;
import net.swordie.ms.world.shop.cashshop.CashShopCategory;
import net.swordie.ms.world.shop.cashshop.CashShopItem;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import net.swordie.ms.util.FileTime;
import net.swordie.ms.util.SystemTime;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 12/12/2017.
 */
public class DatabaseManager {
    private static final Logger log = Logger.getLogger(DatabaseManager.class);
    private static final int KEEP_ALIVE_MS = 10 * 60 * 1000; // 10 minutes

    private static SessionFactory sessionFactory;
    private static List<Session> sessions;

    public static void init() {
        try {
            // Create a BootstrapServiceRegistryBuilder
            BootstrapServiceRegistryBuilder bootstrapRegistryBuilder = new BootstrapServiceRegistryBuilder();
            BootstrapServiceRegistry bootstrapRegistry = bootstrapRegistryBuilder.build();

            // Create a StandardServiceRegistryBuilder
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder(bootstrapRegistry);

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/mydatabase?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "cheese");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            settings.put(Environment.USE_SQL_COMMENTS, "true");
            settings.put(Environment.HBM2DDL_AUTO, "update");
            settings.put("hibernate.enable_lazy_load_no_trans", "true");

            registryBuilder.applySettings(settings);

            // Create a StandardServiceRegistry
            StandardServiceRegistry registry = registryBuilder.build();

            // Create MetadataSources and add annotated classes
            MetadataSources sources = new MetadataSources(registry)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(FileTime.class)
                    .addAnnotatedClass(SystemTime.class)
                    .addAnnotatedClass(NonCombatStatDayLimit.class)
                    .addAnnotatedClass(CharacterCard.class)
                    .addAnnotatedClass(Item.class)
                    .addAnnotatedClass(Equip.class)
                    .addAnnotatedClass(Inventory.class)
                    .addAnnotatedClass(Skill.class)
                    .addAnnotatedClass(FuncKeyMap.class)
                    .addAnnotatedClass(Keymapping.class)
                    .addAnnotatedClass(SPSet.class)
                    .addAnnotatedClass(ExtendSP.class)
                    .addAnnotatedClass(CharacterStat.class)
                    .addAnnotatedClass(AvatarLook.class)
                    .addAnnotatedClass(AvatarData.class)
                    .addAnnotatedClass(Char.class)
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(QuestManager.class)
                    .addAnnotatedClass(Quest.class)
                    .addAnnotatedClass(QuestProgressRequirement.class)
                    .addAnnotatedClass(QuestProgressLevelRequirement.class)
                    .addAnnotatedClass(QuestProgressItemRequirement.class)
                    .addAnnotatedClass(QuestProgressMobRequirement.class)
                    .addAnnotatedClass(QuestProgressMoneyRequirement.class)
                    .addAnnotatedClass(Guild.class)
                    .addAnnotatedClass(GuildMember.class)
                    .addAnnotatedClass(GuildRequestor.class)
                    .addAnnotatedClass(GuildSkill.class)
                    .addAnnotatedClass(BBSRecord.class)
                    .addAnnotatedClass(BBSReply.class)
                    .addAnnotatedClass(Friend.class)
                    .addAnnotatedClass(Macro.class)
                    .addAnnotatedClass(DamageSkinSaveData.class)
                    .addAnnotatedClass(Trunk.class)
                    .addAnnotatedClass(PetItem.class)
                    .addAnnotatedClass(MonsterBookInfo.class)
                    .addAnnotatedClass(CharacterPotential.class)
                    .addAnnotatedClass(LinkSkill.class)
                    .addAnnotatedClass(Familiar.class)
                    .addAnnotatedClass(StolenSkill.class)
                    .addAnnotatedClass(ChosenSkill.class)
                    .addAnnotatedClass(CashItemInfo.class)
                    .addAnnotatedClass(CashShopItem.class)
                    .addAnnotatedClass(CashShopCategory.class)
                    .addAnnotatedClass(MonsterCollectionSessionRewardInfo.class)
                    .addAnnotatedClass(MonsterCollectionGroupRewardInfo.class)
                    .addAnnotatedClass(MonsterCollectionMobInfo.class)
                    .addAnnotatedClass(MonsterCollection.class)
                    .addAnnotatedClass(MonsterCollectionReward.class)
                    .addAnnotatedClass(MonsterCollectionExploration.class)
                    .addAnnotatedClass(Alliance.class)
                    .addAnnotatedClass(DropInfo.class)
                    .addAnnotatedClass(Offense.class)
                    .addAnnotatedClass(OffenseManager.class)
                    .addAnnotatedClass(NpcShopItem.class)
                    .addAnnotatedClass(EquipDrop.class)
                    .addAnnotatedClass(EmployeeTrunk.class)
                    .addAnnotatedClass(MerchantItem.class);

            // Create Metadata
            Metadata metadata = sources.getMetadataBuilder().build();

            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();

            // Initialize session list
            sessions = new ArrayList<>();

            // Start heart beat
            sendHeartBeat();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Sends a simple query to the DB to ensure that the connection stays alive.
     */
    private static void sendHeartBeat() {
        Session session = getSession();
        Transaction t = session.beginTransaction();
        Query<Char> q = session.createQuery("from Char where id = 1", Char.class);
        q.list();
        t.commit();
        session.close();
        EventManager.addEvent(DatabaseManager::sendHeartBeat, KEEP_ALIVE_MS);
    }

    public static Session getSession() {
        Session session = sessionFactory.openSession();
        sessions.add(session);
        return session;
    }

    public static void cleanUpSessions() {
        sessions.removeAll(sessions.stream().filter(s -> !s.isOpen()).toList());
    }

    public static void saveToDB(Object obj) {
        log.info(String.format("%s: Trying to save obj %s.", LocalDateTime.now(), obj));
        synchronized (obj) {
            try (Session session = getSession()) {
                Transaction t = session.beginTransaction();
                session.saveOrUpdate(obj);
                t.commit();
            }
        }
        cleanUpSessions();
    }

    public static void deleteFromDB(Object obj) {
        log.info(String.format("%s: Trying to delete obj %s.", LocalDateTime.now(), obj));
        synchronized (obj) {
            try (Session session = getSession()) {
                Transaction t = session.beginTransaction();
                session.delete(obj);
                t.commit();
            }
        }
        cleanUpSessions();
    }

    public static Object getObjFromDB(Class clazz, int id) {
        log.info(String.format("%s: Trying to get obj %s with id %d.", LocalDateTime.now(), clazz, id));
        Object o = null;
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            // String.format for query, just to fill in the class
            // Can't set the FROM clause with a parameter it seems
            // session.get doesn't work for Chars for whatever reason
            Query query = session.createQuery(String.format("FROM %s WHERE id = :val", clazz.getName()));
            query.setParameter("val", id);
            List l = ((org.hibernate.query.Query) query).list();
            if (l != null && !l.isEmpty()) {
                o = l.get(0);
            }
            transaction.commit();
        }
        return o;
    }

    public static Object getObjFromDB(Class clazz, String name) {
        return getObjFromDB(clazz, "name", name);
    }

    public static Object getObjFromDB(Class clazz, String columnName, Object value) {
        log.info(String.format("%s: Trying to get obj %s with value %s.", LocalDateTime.now(), clazz, value));
        Object o = null;
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            // String.format for query, just to fill in the class
            // Can't set the FROM clause with a parameter it seems
            Query query = session.createQuery(String.format("FROM %s WHERE %s = :val", clazz.getName(), columnName));
            query.setParameter("val", value);
            List l = ((org.hibernate.query.Query) query).list();
            if (l != null && !l.isEmpty()) {
                o = l.get(0);
            }
            transaction.commit();
            session.close();
        }
        return o;
    }

    public static Object getObjListFromDB(Class clazz) {
        List list;
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            // String.format for query, just to fill in the class
            // Can't set the FROM clause with a parameter it seems
            Query query = session.createQuery(String.format("FROM %s", clazz.getName()));
            list = ((org.hibernate.query.Query) query).list();
            transaction.commit();
            session.close();
        }
        return list;
    }

    public static Object getObjListFromDB(Class clazz, String columnName, Object value) {
        List list;
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            // String.format for query, just to fill in the class
            // Can't set the FROM clause with a parameter it seems
            Query query = session.createQuery(String.format("FROM %s WHERE %s = :val", clazz.getName(), columnName));
            query.setParameter("val", value);
            list = ((org.hibernate.query.Query) query).list();
            transaction.commit();
            session.close();
        }
        return list;
    }
}
