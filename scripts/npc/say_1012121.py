# Clothes Collector (1012121) | Henesys Market

from net.swordie.ms.enums import InvType

nxItems = []

equipInv = chr.getInventoryByType(InvType.EQUIP)
invItems = equipInv.getItems()
# Sort by physical inventory slots, not item ID
invItems.sort(key=lambda item: item.getBagIndex())

# Filter the player's inventory to just Cash Equips
# Make sure to add the item's ID to nxItems, *not* the item entity itself
for equip in invItems:
    if equip.isCash():
        equipId = equip.getItemId()
        nxItems.append(equipId)

# No Cash Equips found
if len(nxItems) == 0:
    sm.sendSayOkay("You don't have anything for the Clothes Collector.")
else:
    disposeString = "Select the clothes you want to throw away. #b\r\n"
    # Construct disposal list
    for index, nxItem in enumerate(nxItems):
        disposeString += "#L"+ str(index) + "##i" + str(nxItem) + "# #z" + str(nxItem) +"##l\r\n"
    selection = sm.sendNext(disposeString)

    # Pull out item id from nxItems
    toDispose = nxItems[selection]

    response = sm.sendAskYesNo("Are you sure you want to throw away #b#i" + str(toDispose) + "# #z" + str(toDispose) + "##k? "
    "Once they're gone, they're gone for good!")
    if response:
        sm.consumeItem(toDispose)
        sm.sendSayOkay("Thank you for your garbage.")