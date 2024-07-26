package net.swordie.ms.world.event;

import net.swordie.ms.client.character.Char;

public interface InGameEvent {

    String getEventName();
    void doEvent();
    void endEvent();
    void clear();
    void joinEvent(Char c);
    void sendLobbyClock(Char c);
    boolean isActive();
    boolean isOpen();
    InGameEventType getEventType();
    int getEventEntryMap();
    boolean charInEvent(int charId);
    void onMigrateDeath(Char c);
}
