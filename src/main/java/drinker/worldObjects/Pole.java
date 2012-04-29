package drinker.worldObjects;

import drinker.WorldObject;

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
