package drinker.tests;

import drinker.World;
import drinker.WorldObjects.Toper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Tests {

    private World world;    

    @Before
    public void InitWorld() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                
            }
        });
        world = new World(16, 16, printStream);
    }

    @Test
    public void TestS() {
        world.addObject(new Toper(1,1));
        System.out.println("asd");

    }

}
