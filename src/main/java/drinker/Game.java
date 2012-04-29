package main.java.drinker;

public class Game {

    public Game(boolean isHex, int count) {

        World w = new World(isHex, System.out);


        for (int i = 0; i < count; i++) {

//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//
//            }            
//            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
//           w.drawScene();
//            
            w.tick();

        }

        w.drawScene();


    }

}
