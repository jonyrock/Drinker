package drinker;

import drinker.utils.ObjectEventHandler;
import drinker.worldObjects.Bottle;
import drinker.worldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

public class TestsPositions {

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
    public void Test_16_0_Position() {

        Assert.assertFalse(world.isPossibleForStep(World.fromYXtoWorldFieldCoordinates(16, 0)));
    }

    /**
     * Tests worldObject movement to possible
     * position (0, 7)
     */
    @Test
    public void Test_0_7_Position() {
        Assert.assertTrue(world.isPossibleForStep(World.fromYXtoWorldFieldCoordinates(0, 7)));
    }


}
