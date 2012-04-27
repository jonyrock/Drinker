package drinker.worldObjects;

import drinker.WorldObject;

public class Beggar extends WorldObject {

    public Beggar(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return 'Z';
    }

}
