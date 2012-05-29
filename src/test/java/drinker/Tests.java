package drinker;

import drinker.utils.WorldEvent;
import drinker.worldObjects.Beggar;
import drinker.worldObjects.Bottle;
import drinker.worldObjects.BottleHouse;
import drinker.worldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class Tests {
        
    
    private World world;

    @Before
    public void initWorld() {
        PrintStream printStream = mock(PrintStream.class);
        world = new World(false, printStream);
    }

    /**
     * Tests worldObject movement to impossible
     * position (16, 0)
     */
    @Test
    public void test_16_0_Position() {
        Assert.assertFalse(world.isPossibleForStep(World.fromYXtoWorldFieldCoordinates(16, 0)));
    }

    /**
     * Tests worldObject movement to possible
     * position (0, 7)
     */
    @Test
    public void test_0_7_Position() {
        Assert.assertTrue(world.isPossibleForStep(World.fromYXtoWorldFieldCoordinates(0, 7)));
    }

    /**
     * Tests than after meeting toper in normal state
     * they stay normal
     */
    @Test
    public void testTopersMeeting() {

        Toper t1 = new Toper(world, 5, 5);
        Toper t2 = new Toper(world, 6, 5);
        WorldObject.mutuallyCollision(t1, t2);

        Assert.assertTrue(t1.isMovable());
        Assert.assertTrue(t2.isMovable());

    }

    /**
     * Test that on mutual collision each objects
     * notified about that once.
     */
    @Test
    public void testMutualCollisionMethod() {

        Toper t1 = mock(Toper.class);
        Toper t2 = mock(Toper.class);
        t1.onMutuallyCollisionEvent = new WorldEvent();
        t2.onMutuallyCollisionEvent = new WorldEvent();

        WorldObject.mutuallyCollision(t1, t2);

        verify(t1, times(1)).onCollision(t2);
        verify(t2, times(1)).onCollision(t1);

    }


    /**
     * Tests that beggar finds right direction
     */
    @Test
    public void testNormalFieldBeggarPathToBottle1() {

        // beggar with unreachable BottleHouse. Tests only path to bottle 
        Beggar beggar = new Beggar(new BottleHouse(20, 20), 10, 12);
        
        // create example world  
        World w = mock(World.class);
        ArrayList<Bottle> bottles = new ArrayList<Bottle>();
        bottles.add(new Bottle(10, 13));
        stub(w.getBottles()).toReturn(bottles);
        
        // and bind to real world the method. 
        stub(w.findDirectionOnClosestPath(beggar, bottles.get(0)))
                .toReturn(world.findDirectionOnClosestPath(beggar, bottles.get(0)));

        // is needed by contract
        beggar.setWorld(w);
        
        // 40 times wait and only move
        for (int i = 1; i <= 41; i++) {
            beggar.onTick();
        }
        
        // check that beggar tried find way 
        verify(w).getBottles();
        verify(w).findDirectionOnClosestPath(beggar, bottles.get(0));
        
        
        Assert.assertTrue(beggar.isSameLocation(bottles.get(0)));
        

    }

}
