package drinker.WorldObjects;

import drinker.WorldObject;

public class Pole extends WorldObject {

    public Pole(int x, int y) {
        super(x,y);
    }
    
    @Override
    public char draw() {
        return '#';
    }
    
    @Override
    public boolean isStopAble(){
        return true;
    }
    
}
