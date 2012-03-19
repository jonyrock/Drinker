package drinker.WorldObjects;

import drinker.ObjectEventHandler;
import drinker.Point2D;
import drinker.WorldObject;

import java.util.ArrayDeque;
import java.util.Collection;

public class Policeman extends WorldObject {

    protected ArrayDeque<WorldObject> targets;
    protected WorldObject currentTarget;
    boolean busy;
    PoliceStation policeStation;

    public Policeman(Lamp lamp, PoliceStation policeStation, int x, int y) {

        super(x, y);

        this.targets = new ArrayDeque<WorldObject>();

        this.busy = false;
        this.policeStation = policeStation;
        this.currentTarget = null;

        ObjectEventHandler ev = new ObjectEventHandler() {

            public void onEvent(WorldObject o) {
                if (o.isNeedPoliceHelp()) {
                    addTarget(o);
                }
            }

        };

        Collection<PlaceWithLight> list = lamp.getLightedPlaces();
        for (PlaceWithLight pl : list) {
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

        Point2D direction = world.findDirectionOnClosestPath(this, policeStation);

        if (direction == null) {
            return;
        }

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

        Point2D direction = world.findDirectionOnClosestPath(this, currentTarget);

        if (direction == null) {
            return;
        }

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
