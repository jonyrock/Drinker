package drinker.WorldObjects;

import drinker.WorldObject;

public class WhiteSpace extends WorldObject {

        
    public WhiteSpace(int x, int y) {
        super(x, y);
    }
    
    @Override
    public char draw() {
        return ' ';
    }
    
    
}
