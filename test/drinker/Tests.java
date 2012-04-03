package drinker;

import drinker.utils.ObjectEventHandler;
import drinker.worldObjects.Bottle;
import drinker.worldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {

    private World world;
    private boolean pos03visited;
    private int testPositionsAttempts;
    private boolean wasMoveOnTick;
    private boolean policeMetToper;
    private int tick;
    private int testPolicemanToperMeetAttempts;


    @Before
    public void initWorld() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {

            }
        });

        world = new World(16, 16, printStream);
        wasMoveOnTick = false;
        pos03visited = false;
        testPositionsAttempts = 0;
        tick = 0;
        policeMetToper = false;
        testPolicemanToperMeetAttempts = 0;
    }

    void worldStart() {
        for (int i = 1; i < 1000; i++) {
            world.tick();
        }
    }


    /**
     * Test checks:
     * - illegal positions
     * - was visiting position (0, 3) at least one time
     * - no one was at lamp position 
     */
    @Test
    public void testPositions() {
        // ground on bottom 
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
        boolean isLampPosition = world.lamp.getX() == ground.getX() && world.lamp.getY() == ground.getY();
        Assert.assertFalse("Somebody enter to lamp position", isLampPosition);
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

    @Test
    public void testOneTickOneMove() {

        world.addPostTickEvent(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                wasMoveOnTick = false;
            }
        });

        world.addAddObjectHandler(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                o.addPreTickEvent(new ObjectEventHandler() {
                    public void onEvent(WorldObject o) {
                        Assert.assertFalse("Two objects were moved", wasMoveOnTick);
                        wasMoveOnTick = true;
                    }
                });
            }
        });

        worldStart();

    }
    
    @Test
    public void testAdded20Toper(){
        
        world.addPostTickEvent(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                tick++;
                Assert.assertTrue("20 ticks without new toper.", tick < 20);
            }
        });

        world.addAddObjectHandler(new ObjectEventHandler() {
            public void onEvent(final WorldObject t) {
                if (t.getClass().equals(Toper.class)) {
                    tick = 0;
                }
            }
        });
        
        worldStart();
        
    }
    
    @Test
    public void testPolicemanToperMeet(){
        
        world.policeman.addMutuallyCollisionHandler(new ObjectEventHandler() {
            public void onEvent(WorldObject o) {
                if(o.getClass().equals(Toper.class)){
                    policeMetToper = true;
                }
            }
        });

        worldStart();

        if (!policeMetToper && testPolicemanToperMeetAttempts++ < 10) {
            initWorld();
            testPolicemanToperMeet();
        }
        
    }
    
}
