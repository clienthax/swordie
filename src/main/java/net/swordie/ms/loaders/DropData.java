package net.swordie.ms.loaders;

import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.life.drop.DropInfo;
import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import net.swordie.ms.util.Util;
import org.hibernate.query.Query;

import java.io.*;
import java.util.*;

/**
 * Created on 2/21/2018.
 */
public class DropData {
    private static final Logger log = LogManager.getLogger(DropData.class);

    private final Map<Integer, Set<DropInfo>> drops = new HashMap<>();

    public void loadCompleteDropsFromTxt(File file) {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            int mobID = 0;
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("//")) {
                    continue;
                }
                String[] split = line.split(" ");
                /*
                Format:
                mobID (int)
                or:
                itemID (int) chance (int out of 10000) minCount (int) maxCount (int)
                itemID (int) chance (int out of 10000)
                So, single line = mobID, else 2/4 ints
                 */
                if(split.length == 1 && split[0].equalsIgnoreCase("global")) {
                    mobID = -1;
                } else if(split.length == 1 && !split[0].isEmpty()) {
                    mobID = Integer.parseInt(split[0]);
                } else if(split.length >= 2) {
                    int itemID = Integer.parseInt(split[0]);
                    int chance = Integer.parseInt(split[1]);
                    int minQuant = 1;
                    int maxQuant = 1;
                    if (split.length >= 4 && Util.isNumber(split[3])) { // hack to make you able to comment stuff
                        // min/max count
                        minQuant = Integer.parseInt(split[2]);
                        maxQuant = Integer.parseInt(split[3]);
                    }
                    DropInfo dropInfo = new DropInfo(itemID, chance, minQuant, maxQuant);
                    addDrop(mobID, dropInfo);
                }
            }
            for (Map.Entry<Integer, Set<DropInfo>> drop : getDrops().entrySet()) {
                mobID = drop.getKey();
                for (DropInfo di : drop.getValue()) {
                    log.debug(String.format("(%d, %d, %d, %d, %d),", mobID, di.getItemID(), di.getChance(), di.getMinQuant(), di.getMaxQuant()));
                }
            }
//            for (DropInfo globalDrop : getDropInfoByID(-1)) {
//                for (int key : getDrops().keySet()) {
//                    if (key != -1) {
//                        addDrop(key, globalDrop);
//                    }
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<DropInfo> getDropInfoByID(int mobID) {
        Set<DropInfo> drops = getCachedDropInfoById(mobID);
        if (drops == null) {
            drops = getDropInfoByIdFromDB(mobID);
            if (mobID != -1) {
                drops.addAll(getDropInfoByIdFromDB(-1)); // global drops
            }
            getDrops().put(mobID, drops);
        }
        return drops;
    }

    private Set<DropInfo> getDropInfoByIdFromDB(int mobID) {
        List<DropInfo> l;
        try(Session session = DatabaseManager.getSession()) {
            Transaction transaction = session.beginTransaction();
            // String.format for query, just to fill in the class
            // Can't set the FROM clause with a parameter it seems
            Query<DropInfo> query = session.createQuery("FROM DropInfo WHERE mobid = :mobid", DropInfo.class);
            query.setParameter("mobid", mobID);
            l = query.list();
            transaction.commit();
            session.close();
        }
        return l == null ? null : new HashSet<>(l);
    }

    private Set<DropInfo> getCachedDropInfoById(int mobID) {
        return getDrops().getOrDefault(mobID, null);
    }

    private void addDrop(int mobID, DropInfo dropInfo) {
        if(!getDrops().containsKey(mobID)) {
            getDrops().put(mobID, new HashSet<>());
        }
        getDrops().get(mobID).add(dropInfo);
    }

    private Map<Integer, Set<DropInfo>> getDrops() {
        return drops;
    }

    public void main(String[] args) {
        loadCompleteDropsFromTxt(new File(ServerConstants.RESOURCES_DIR + "/mobDrops.txt"));
    }

    public void clear() {
        getDrops().clear();
    }
}
