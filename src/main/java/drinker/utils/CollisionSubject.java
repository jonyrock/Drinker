package drinker.utils;

import drinker.WorldObject;

import java.util.ArrayDeque;

/**
 * WorldObject is null sometimes
 */
public class CollisionSubject {

    private ArrayDeque<CollisionObserver> observerCollection = new ArrayDeque<CollisionObserver>();

    public void notifyObservers(WorldObject o) {
        for (CollisionObserver e : observerCollection) {
            e.notify(o);
        }
    }

    public void registerObserver(CollisionObserver e) {
        observerCollection.add(e);
    }

}
