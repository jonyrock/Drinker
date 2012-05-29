package drinker.worldObjects;

import drinker.World;
import drinker.WorldObject;
import drinker.utils.CollisionObserver;

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
        CollisionObserver ev = new CollisionObserver() {

            public void notify(WorldObject o) {
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

        if (!busy && targets.isEmpty() && currentTarget == null) {
            return;
        }

        if (busy) {
            stepToHome();
        } else {
            stepToHelp();
        }


    }

    private void stepToHome() {

        World.DirectionStep pair = world.findDirectionOnClosestPath(this, policeStation);

        if (pair == null) {
            return;
        }

        if (pair.dist == 0) {
            busy = false;
            currentTarget = null;
            return;
        }

        super.x += pair.direction.x;
        super.y += pair.direction.y;

    }

    private void stepToHelp() {

        if (currentTarget == null) {
            currentTarget = targets.poll();
        }

        World.DirectionStep pair = world.findDirectionOnClosestPath(this, currentTarget);

        if (pair == null) {
            return;
        }
        
        if (pair.dist == 0) {
            busy = true;
            world.removeObject(currentTarget);
            return;
        }
        
        super.x += pair.direction.x;
        super.y += pair.direction.y;

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
