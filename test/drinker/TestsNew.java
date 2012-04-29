package drinker;

import drinker.utils.ObjectEventHandler;
import drinker.worldObjects.Bottle;
import drinker.worldObjects.Toper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.mock;

public class TestsNew {

    private World world;

    @Before
    public void initWorld() {
        PrintStream printStream = mock(PrintStream.class);
        world = new World(false, printStream);
    }
    
}
