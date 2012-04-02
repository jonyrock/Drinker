package drinker;

public class Game {

    public Game(int count) {

        World w = new World(16, 16, System.out);
        

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
