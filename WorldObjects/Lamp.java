package drinker.WorldObjects;

import drinker.WorldObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Lamp extends WorldObject{
    
    private ArrayList<PlaceWithLight> lightedPlaces;
    public Lamp(int x, int y) {
        super(x, y);
        lightedPlaces = new ArrayList<PlaceWithLight>();
    }

    @Override
    public boolean isSuspendAble() {
        return true;
    }

    @Override
    public char draw() {
        return 'Ф';
    }
    
    
    public void switchOn() {
        
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                
                if(i == 0 && j == 0)
                    continue;
                
                PlaceWithLight pl = new PlaceWithLight(this.x + i, this.y + j);
                world.addObject(pl);
                lightedPlaces.add(pl);
                
            }
        }


    }
    
    public Collection<PlaceWithLight> getLightedPlaces(){
        return Collections.unmodifiableCollection(lightedPlaces);
    }
    
}
