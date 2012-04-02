package drinker.WorldObjects;

import drinker.WorldObject;

public class Ground extends WorldObject {

    public Ground(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return '0';
    }
}
