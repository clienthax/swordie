package net.swordie.ms.loaders;

import net.swordie.ms.connection.db.DatabaseManager;
import net.swordie.ms.life.npc.Npc;
import net.swordie.ms.world.shop.NpcShopDlg;
import net.swordie.ms.world.shop.NpcShopItem;
import net.swordie.ms.ServerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import net.swordie.ms.util.Util;
import net.swordie.ms.util.XMLApi;

import java.io.*;
import java.util.*;

/**
 * Created on 2/19/2018.
 */
public class NpcData implements DataCreator {
	private final Logger log = LogManager.getLogger(NpcData.class);
	private final boolean LOG_UNKS = false;

	private final Set<Npc> npcs = new HashSet<>();
	private final Map<Integer, NpcShopDlg> shops = new HashMap<>();

	private Map<Integer, NpcShopDlg> getShops() {
		return shops;
	}

	private void addShop(int id, NpcShopDlg nsd) {
		getShops().put(id, nsd);
	}

	private void loadNpcsFromWz() {
		String wzDir = String.format("%s/Npc.wz", ServerConstants.WZ_DIR);
		File dir = new File(wzDir);
		if (!dir.exists()) {
            log.error("{} does not exist.", wzDir);
			return;
		}

		for (File file : dir.listFiles()) {
			Npc npc = new Npc(0);
			Node node = XMLApi.getRoot(file);
			Node mainNode = XMLApi.getAllChildren(node).get(0);
			int id = Integer.parseInt(XMLApi.getNamedAttribute(mainNode, "name")
					.replace(".xml", "").replace(".img", ""));
			npc.setTemplateId(id);
			npc.setMove(XMLApi.getFirstChildByNameBF(mainNode, "move") != null);
			Node scriptNode = XMLApi.getFirstChildByNameBF(mainNode, "script");
			if (scriptNode != null) {
				for (Node idNode : XMLApi.getAllChildren(scriptNode)) {
					String scriptIDString = XMLApi.getNamedAttribute(idNode, "name");
					if (!Util.isNumber(scriptIDString)) {
						continue;
					}
					int scriptID = Integer.parseInt(XMLApi.getNamedAttribute(idNode, "name"));
					Node scriptValueNode = XMLApi.getFirstChildByNameDF(idNode, "script");
					if (scriptValueNode != null) {
						String scriptName = XMLApi.getNamedAttribute(scriptValueNode, "value");
						npc.getScripts().put(scriptID, scriptName);
					}
				}
			}
			Node infoNode = XMLApi.getFirstChildByNameBF(mainNode, "info");
			for (Node infoChildNode : XMLApi.getAllChildren(infoNode)) {
				String name = XMLApi.getNamedAttribute(infoChildNode, "name");
				String value = XMLApi.getNamedAttribute(infoChildNode, "value");
				switch (name) {
					case "trunkGet":
						npc.setTrunkGet(Integer.parseInt(value));
						break;
					case "trunkPut":
						npc.setTrunkPut(Integer.parseInt(value));
						break;
				}
			}
			getBaseNpcs().add(npc);
		}
	}

	public void saveNpcsToDat(String dir) {
		Util.makeDirIfAbsent(dir);
		for (Npc npc : getBaseNpcs()) {
			File file = new File(String.format("%s/%d.dat", dir, npc.getTemplateId()));
			try(DataOutputStream das = new DataOutputStream(new FileOutputStream(file))) {
				das.writeInt(npc.getTemplateId());
				das.writeBoolean(npc.isMove());
				das.writeInt(npc.getTrunkGet());
				das.writeInt(npc.getTrunkPut());
				das.writeShort(npc.getScripts().size());
				npc.getScripts().forEach((key, val) -> {
					try {
						das.writeInt(key);
						das.writeUTF(val);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Npc getNpc(int id) {
		return getBaseNpcs().stream().filter(npc -> npc.getTemplateId() == id).findFirst().orElse(null);
	}

	public Npc getNpcDeepCopyById(int id) {
		Npc res = getNpc(id);
		if(res != null) {
			res = res.deepCopy();
		} else {
			File file = new File(String.format("%s/npc/%d.dat", ServerConstants.DAT_DIR, id));
			if (file.exists()) {
				res = loadNpcFromDat(file).deepCopy();
				getBaseNpcs().add(res);
			}
		}
		return res;
	}

	private Npc loadNpcFromDat(File file) {
		try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
			Npc npc = new Npc(dis.readInt());
			npc.setMove(dis.readBoolean());
			npc.setTrunkGet(dis.readInt());
			npc.setTrunkPut(dis.readInt());
			short size = dis.readShort();
			for (int i = 0; i < size; i++) {
				int id = dis.readInt();
				String val = dis.readUTF();
				npc.getScripts().put(id, val);
			}
			getBaseNpcs().add(npc);
			return npc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private NpcShopDlg loadNpcShopDlgFromDB(int id) {
		List<NpcShopItem> items = DatabaseManager.getObjListFromDB(NpcShopItem.class, "shopid", id);
		if (items == null || items.isEmpty()) {
			return null;
		}
		NpcShopDlg nsd = new NpcShopDlg();
		nsd.setNpcTemplateID(id);
		nsd.setShopID(id);
		items.sort(Comparator.comparingInt(NpcShopItem::getItemID));
		nsd.setItems(items);
		addShop(id, nsd);
		return nsd;
	}

	public NpcShopDlg getShopById(int id) {
		return getShops().getOrDefault(id, loadNpcShopDlgFromDB(id));
	}

	public void generateDatFiles() {
		log.info("Started generating npc data.");
		long start = System.currentTimeMillis();
		loadNpcsFromWz();
		saveNpcsToDat(ServerConstants.DAT_DIR + "/npc");
		log.info(String.format("Completed generating npc data in %dms.", System.currentTimeMillis() - start));
	}

	public Set<Npc> getBaseNpcs() {
		return npcs;
	}

	public void clear() {
		getBaseNpcs().clear();
		getShops().clear();
	}
}
