package drinker.WorldObjects;

import drinker.ObjectEventHandler;
import drinker.WorldObject;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlaceWithLight extends WorldObject{
    
    public PlaceWithLight(int x, int y){
        
        super(x,y);
        
    }

    @Override
    public char draw() {
        return WorldObject.drawDefault();
    }
    
    @Override
    public boolean isTakePlace() {
        return false;
    }

    
}
