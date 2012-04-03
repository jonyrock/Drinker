package drinker.utils;

import drinker.WorldObject;

import java.util.ArrayDeque;

/**
 * WorldObject is null sometimes 
 */
public class WorldEvent {

    private ArrayDeque<ObjectEventHandler> handlers = new ArrayDeque<ObjectEventHandler>();
    
    public void emit(WorldObject o) {
        for (ObjectEventHandler e : handlers) {
            e.onEvent(o);
        }
    }
    
    public void add(ObjectEventHandler e) {
        handlers.add(e);
    }

}
