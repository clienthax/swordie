# [Theme Dungeon] Ellinel Fairy Academy
# This version appears for Wind Archer

IRENA = 1101005 # NPC ID
THEME_DUNGEON_ELLINEL_FAIRY_ACADEMY = 32151 # QUEST ID
NORTH_FOREST_GIANT_TREE = 101030000 # MAP ID
status = -1

def init():
    sm.setSpeakerID(IRENA)
    sm.sendAskAccept("#h #, could you spare me a moment? I received a request for help, and i can't think of anyone better than you.")

def action(response, answer):
    global status
    status += 1

    if status == 0:
        if response == 1:
            sm.sendNext("There has been an incident at the #b Ellinel Fairy Academy#k. A human magician has trespassed in the sacred halls of the fairy school.")
        else:
            sm.dispose()

    elif status == 1:
        sm.sendNext("I don't know all the details, but I know our relationship with the fairies is strained enough as it is. Will you go to the North Forest near Elinia and meet with #p1040002#.")

    elif status == 2:
        sm.sendAskYesNo("Fanzy will take you into the land of the fairies. I can send you to him directly, if you'd like.")

    elif status == 3:
        if response == 1:
            sm.startQuestNoCheck(THEME_DUNGEON_ELLINEL_FAIRY_ACADEMY)
            sm.completeQuestNoRewards(parentID)
            sm.warp(NORTH_FOREST_GIANT_TREE)
        sm.dispose()