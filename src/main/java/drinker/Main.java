package main.java.drinker;

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
     * @param args input (and output) file paths
     */
    public static void main(String[] args) {
        
        boolean isHex= false;
        if(args.length > 1){
            if(args[0].toLowerCase().equals("h")){
                isHex = true;
            }
        }
        new Game(true, 1000);
//        Game g2 = new Game(300);
//        Game g3 = new Game(500);


    }
    
}
