package drinker.worldObjects;

import drinker.utils.ObjectEventHandler;
import drinker.Point2D;
import drinker.WorldObject;
import drinker.utils.Pair;

import java.util.ArrayDeque;
import java.util.Collection;

public class Policeman extends WorldObject {

    protected ArrayDeque<WorldObject> targets;
    protected WorldObject currentTarget;
    boolean busy;
    PoliceStation policeStation;
    private final Lamp lamp;

    public Policeman(Lamp lamp, PoliceStation policeStation, int x, int y) {

        super(x, y);

        this.targets = new ArrayDeque<WorldObject>();

        this.busy = false;
        this.policeStation = policeStation;
        this.currentTarget = null;
        this.lamp = lamp;


    }

    public void bindToLamp() {
        ObjectEventHandler ev = new ObjectEventHandler() {

            public void onEvent(WorldObject o) {
                if (o.isNeedPoliceHelp()) {
                    addTarget(o);
                }
            }

        };

        Collection<WorldObject> list = lamp.getLightedPlaces();
        for (WorldObject pl : list) {
            pl.addEnterHandler(ev);
        }
    }

    @Override
    public void onTick() {

        if (!busy && targets.isEmpty()) {
            return;
        }

        if (busy) {
            stepToHome();
        } else {
            stepToHelp();
        }


    }

    private void stepToHome() {

        Pair<Point2D, Integer> pair = world.findDirectionOnClosestPath(this, policeStation);

        if (pair == null) {
            return;
        }

        Point2D direction = pair.first;
        
        if (direction.isZero()) {
            busy = false;
            currentTarget = null;
            return;
        }

        super.x += direction.x;
        super.y += direction.y;

    }

    private void stepToHelp() {

        if (currentTarget == null) {
            currentTarget = targets.poll();
        }

        Pair<Point2D, Integer> pair = world.findDirectionOnClosestPath(this, currentTarget);

        if (pair == null) {
            return;
        }
        
        Point2D direction = pair.first;
        
        if (direction.isZero()) {
            busy = true;
            world.removeObject(currentTarget);
            return;
        }

        super.x += direction.x;
        super.y += direction.y;

    }

    @Override
    public char draw() {
        return '!';
    }

    @Override
    public boolean isSuspendAble() {
        return true;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

    public void addTarget(WorldObject o) {
        targets.add(o);
    }

}
