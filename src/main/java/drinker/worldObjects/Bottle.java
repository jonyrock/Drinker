package main.java.drinker.worldObjects;

import main.java.drinker.WorldObject;

public class Bottle extends WorldObject {

    public Bottle(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return 'B';
    }

    @Override
    public boolean isLatencySuspendAble() {
        return true;
    }
}