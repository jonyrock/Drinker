package drinker.tests;

import drinker.ObjectEventHandler;
import drinker.World;
import drinker.WorldObject;
import drinker.WorldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {

    private World world;


    @Before
    public void initWorld() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        });

        world = new World(16, 16, printStream);

    }

    private boolean pos03visited;
    private int testPositionsAttempts = 0;

    @Test
    public void TestPositions() {
        // on bottom is ground
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                final WorldObject ground = world.getObjectAtXY(i, j).iterator().next();
                ground.addEnterHandler(new ObjectEventHandler() {
                    public void onEvent(WorldObject o) {
                        TestPositions(ground);
                    }
                });
            }
        }
        worldStart();
        if (!pos03visited && testPositionsAttempts++ < 10) {
            initWorld();
            TestPositions();
        }
        Assert.assertTrue("Position (0,3) wasn't visited.", pos03visited);
    }

    public void TestPositions(WorldObject ground) {
        boolean badPos = ground.getX() == 16 && ground.getY() == 0;
        Assert.assertFalse("Illegal position (16, 0)", badPos);
        if (ground.getX() == 0 && ground.getY() == 3) {
            pos03visited = true;
        }
    }

    @Test
    public void TestToperSleep() {
                
        world.lamp.addMutuallyCollisionHandler(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                if (o.getClass().equals(Toper.class)) {
                    Assert.assertTrue(o.isMovable());
                }
            }
        });
        
        worldStart();
        
    }

    void worldStart() {
        for (int i = 1; i < 1000; i++) {
            world.tick();
        }
    }

}
