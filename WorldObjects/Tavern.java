package drinker.WorldObjects;

import drinker.WorldObject;

public class Tavern extends WorldObject {

    public Tavern(int x, int y) {
        super(x, y);
    }
    
    @Override
    public char draw() {
        return 'T';
    }
    
}
