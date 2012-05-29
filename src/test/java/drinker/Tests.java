package drinker;

import drinker.utils.CollisionSubject;
import drinker.worldObjects.*;
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
        Point2D p1 = t1.getPosition();
        Toper t2 = new Toper(world, 6, 5);
        Point2D p2 = t2.getPosition();

        WorldObject.mutuallyCollision(t1, t2);

        Assert.assertTrue(t1.isMovable());
        Assert.assertTrue(t2.isMovable());

        Assert.assertTrue(t1.getPosition().equals(p1));
        Assert.assertTrue(t2.getPosition().equals(p2));

    }

    @Test
    public void testToperBottleMeeting() {
        Toper t1 = new Toper(world, 5, 5);
        Point2D p1 = t1.getPosition();
        Bottle t2 = new Bottle(5, 6);
        Point2D p2 = t2.getPosition();
        WorldObject.mutuallyCollision(t1, t2);
        t1.onTick();
        Assert.assertFalse(t1.getPosition().equals(p1));
        Assert.assertTrue(t2.getPosition().equals(p2));
    }

    @Test
    public void testToperPoleMeeting() {
        Toper t1 = new Toper(world, 5, 5);
        Point2D p1 = t1.getPosition();
        WorldObject.mutuallyCollision(t1, world.pole);
        t1.onTick();
        Assert.assertTrue(t1.getPosition().equals(p1));
        Assert.assertFalse(t1.isMovable());
    }

    /**
     * toper meets toper who had met pole before
     */
    @Test
    public void testToperToperPoleMeeting() {
        Toper t1 = new Toper(world, 5, 5);
        Toper t2 = new Toper(world, 5, 5);
        Point2D p1 = t1.getPosition();
        WorldObject.mutuallyCollision(t1, world.pole);
        t1.onTick();
        WorldObject.mutuallyCollision(t1, t2);
        Assert.assertTrue(t2.getPosition().equals(p1));
        Assert.assertFalse(t2.isMovable());
    }

    /**
     * Test that on mutual collision each objects
     * notified about that once.
     */
    @Test
    public void testMutualCollisionMethod() {

        Toper t1 = mock(Toper.class);
        Toper t2 = mock(Toper.class);
        t1.onMutuallyCollisionEvent = new CollisionSubject();
        t2.onMutuallyCollisionEvent = new CollisionSubject();

        WorldObject.mutuallyCollision(t1, t2);

        verify(t1, times(1)).onCollision(t2);
        verify(t2, times(1)).onCollision(t1);

    }

    /**
     * Test that policeman surrounded by topers can't move.
     * After that one toper disappears and policeman has to move
     */
    @Test
    public void testPolicemanToTopper() {

        Toper exitToper = new Toper(world, 5, 5);
        world.addObject(new Toper(world, 7, 5));
        world.addObject(new Toper(world, 6, 4));
        world.addObject(new Toper(world, 6, 6));
        Toper targetToper = new Toper(world, 12, 13);
        world.addObject(targetToper);
        world.addObject(exitToper);

        Policeman policeman = new Policeman(world.lamp, world.policeStation, 5, 6);
        policeman.setWorld(world);
        policeman.addTarget(targetToper);
        policeman.onTick();
        
        Point2D p1 = policeman.getPosition();
        
        Assert.assertTrue(policeman.getPosition().equals(p1));

        world.removeObject(exitToper);
        policeman.onTick();
        Assert.assertFalse(policeman.getPosition().equals(p1));

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
