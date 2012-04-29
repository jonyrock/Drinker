package main.java.drinker.worldObjects;

import main.java.drinker.WorldObject;

public class Ground extends WorldObject {

    public Ground(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return '0';
    }
}
