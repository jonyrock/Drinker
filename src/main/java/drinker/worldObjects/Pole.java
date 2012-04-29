package main.java.drinker.worldObjects;

import main.java.drinker.WorldObject;

public class Pole extends WorldObject {

    public Pole(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return '#';
    }

    @Override
    public boolean isReasonToStopToper() {
        return true;
    }

}
