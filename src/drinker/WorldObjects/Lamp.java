package drinker.WorldObjects;

import drinker.WorldObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Lamp extends WorldObject{
    
    private ArrayList<WorldObject> lightedPlaces = new ArrayList<WorldObject>();
    public Lamp(int x, int y) {
        super(x, y);        
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
                
                // Based on fact that world guarantee that the lowest 
                // WorldObject is ground and it always exist
                lightedPlaces.add(
                        world.getObjectAtXY(this.x + i, this.y + j)
                                .iterator().next());
                
            }
        }


    }
    
    public Collection<WorldObject> getLightedPlaces(){
        return Collections.unmodifiableCollection(lightedPlaces);
    }
    
}
