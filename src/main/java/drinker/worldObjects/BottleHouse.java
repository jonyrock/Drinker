package main.java.drinker.worldObjects;

import main.java.drinker.WorldObject;

public class BottleHouse extends WorldObject {

    public BottleHouse(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return 'B';
    }

}
