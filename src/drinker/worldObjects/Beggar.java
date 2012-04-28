package drinker.worldObjects;

import drinker.WorldObject;

import java.util.ArrayList;
import java.util.Random;

public class Beggar extends WorldObject {

    private final BottleHouse bottleHouse;
    private boolean isWithBottle;

    public Beggar(BottleHouse bottleHouse, int x, int y) {
        super(x, y);
        this.bottleHouse = bottleHouse;
        isWithBottle = false;

    }

    @Override
    public char draw() {
        return 'Z';
    }

    @Override
    public void onTick() {

        if (isWithBottle) {
            homeCase();
        } else {
            newBottleCase();
        }

    }

    void homeCase() {

        if (this.x == bottleHouse.getX() && this.y == bottleHouse.getY()) {
            isWithBottle = false;
            return;
        }


    }

    void newBottleCase() {

        ArrayList<Bottle> list = world.getBottles();

        if (list.isEmpty()) {
            return;
        }

        Bottle nearestBottle = null;
        int minDist = Integer.MIN_VALUE;


    }

    @Override
    public boolean isMovable() {
        return true;
    }

}
