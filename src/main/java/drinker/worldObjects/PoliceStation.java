package main.java.drinker.worldObjects;

import main.java.drinker.WorldObject;

public class PoliceStation extends WorldObject {

    public PoliceStation(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return 'П';
    }

}
