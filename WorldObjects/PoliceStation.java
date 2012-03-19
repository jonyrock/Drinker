package drinker.WorldObjects;

import drinker.ObjectEventHandler;
import drinker.WorldObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class PoliceStation extends WorldObject {
    
    public PoliceStation(int x, int y) {
        super(x,y);        
    }
    
    @Override
    public char draw() {
        return 'П';
    }
    
}
