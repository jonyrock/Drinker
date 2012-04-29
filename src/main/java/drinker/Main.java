package drinker;

/**
 * Simple directories list
 *
 * @author Alexey Velikiy. APTU.
 * @version %I%, %G%
 */
public class Main {

    /**
     * Start point
     *
     * @param args keys
     */
    public static void main(String[] args) {

        boolean isHex = false;
        if (args.length > 0) {
            if (args[0].toLowerCase().equals("-h")) {
                isHex = true;
            }
        }
        new Game(isHex, 1000);

    }

}
