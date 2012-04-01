package drinker.tests;

import drinker.ObjectEventHandler;
import drinker.World;
import drinker.WorldObject;
import drinker.WorldObjects.Bottle;
import drinker.WorldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {

    private World world;
    private boolean pos03visited;
    private int testPositionsAttempts = 0;


    @Before
    public void initWorld() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        });

        world = new World(16, 16, printStream);

    }

    void worldStart() {
        for (int i = 1; i < 1000; i++) {
            world.tick();
        }
    }


    @Test
    public void testPositions() {
        // on bottom is ground
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                final WorldObject ground = world.getObjectAtXY(i, j).iterator().next();
                ground.addEnterHandler(new ObjectEventHandler() {
                    public void onEvent(WorldObject o) {
                        testPositions(ground);
                    }
                });
            }
        }
        worldStart();
        if (!pos03visited && testPositionsAttempts++ < 10) {
            initWorld();
            testPositions();
        }
        Assert.assertTrue("Position (0,3) wasn't visited.", pos03visited);
    }

    public void testPositions(WorldObject ground) {
        boolean badPos = ground.getX() == 16 && ground.getY() == 0;
        Assert.assertFalse("Illegal position (16, 0)", badPos);
        if (ground.getX() == 0 && ground.getY() == 3) {
            pos03visited = true;
        }
    }

    @Test
    public void testToperSleep() {

        world.pole.addMutuallyCollisionHandler(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                if (o.getClass().equals(Toper.class)) {
                    Assert.assertFalse("Toper must be stopped after meeting pole", o.isMovable());
                }
            }
        });

        worldStart();

    }

    @Test
    public void testToperWithBottleAndToper() {
        world.addAddObjectHandler(new ObjectEventHandler() {
            public void onEvent(final WorldObject t) {
                if (t.getClass().equals(Toper.class)) {
                    t.addMutuallyCollisionHandler(new ObjectEventHandler() {
                        public void onEvent(WorldObject b) {

                            // meet with bottle
                            if (b.getClass().equals(Bottle.class)) {
                                Assert.assertFalse("Toper can move after meeting bottle",
                                        t.isMovable());
                            }

                            // meet with other toper
                            if (b.getClass().equals(Toper.class)) {
                                Toper anotherToper = (Toper) b;
                                // check if he in sleep
                                if (anotherToper.isStopAble() || anotherToper.isSuspendAble()) {
                                    Assert.assertTrue("Toper can move after meeting sleeping toper",
                                            t.isSuspended());
                                } else {
                                    if (world.getObjectAtXY(b.getX(), b.getY()).size() == 2) {                                        
                                        Assert.assertTrue("Toper can't move after meeting not sleeping toper",
                                                t.isMovable() && !t.isSuspended());
                                    }
                                }
                            }

                        }
                    });
                }
            }
        });

        worldStart();

    }


}
