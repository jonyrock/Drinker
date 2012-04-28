package drinker.worldObjects;

import drinker.Point2D;
import drinker.World;
import drinker.WorldObject;

import java.util.Collection;
import java.util.Random;

public class Toper extends WorldObject {

    private boolean fellAsleep;
    private boolean fellAsleepDown;

    // true means that toper miss   
    // tick after facing with bottle
    private boolean suspended;
    private boolean hasBottle;

    private final World world;
    private final Point2D[] dirs;

    public Toper(World world, int x, int y) {

        super(x, y);

        this.world = world;
        this.dirs = world.getDirections().toArray(new Point2D[world.getDirections().size()]);

        fellAsleep = false;
        suspended = false;
        hasBottle = true;

    }

    @Override
    public char draw() {

        if (fellAsleep)
            return '1';

        if (fellAsleepDown)
            return '&';

        return '@';
    }

    @Override
    public void onTick() {

        if (suspended) {
            suspended = false;
            return;
        }

        if (hasBottle) {
            tryDropBottle();
        }

        Point2D direction = getNewDirection();

        int newX = x + direction.x;
        int newY = y + direction.y;

        Collection<WorldObject> objs = world.getObjectAtXY(newX, newY);
        for (WorldObject w : objs) {
            WorldObject.mutuallyCollision(this, w);
        }

        if (fellAsleep || suspended) {
            return;
        }

        x = newX;
        y = newY;

    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public boolean isMovable() {
        return (!fellAsleep && !fellAsleepDown);
    }

    @Override
    public boolean isReasonToStop() {
        return fellAsleep;
    }

    @Override
    public boolean isSuspendAble() {
        return true;
    }

    @Override
    public boolean isNeedPoliceHelp() {
        return fellAsleepDown;
    }

    @Override
    public void onCollision(WorldObject o) {

        if (o.isReasonToStop())
            this.fellAsleep = true;

        if (o.isSuspendAble())
            this.suspended = true;

        if (o.isLatencySuspendAble())
            this.fellAsleepDown = true;

    }


    private void tryDropBottle() {

        int r = ((int) (Math.random() * 100)) % 30;

        // it is not matter that is 13 or something else 
        // because each variant has the same probability 
        if (r != 13)
            return;
        world.addObject(new Bottle(this.x, this.y));
        hasBottle = false;

    }

    private Point2D getNewDirection() {


        int r = new Random().nextInt(dirs.length);

        int xt = dirs[r].x;
        int yt = dirs[r].y;


        if (!world.isPossibleForStep(x + xt, y)) {
            xt *= (-1);
        }

        if (!world.isPossibleForStep(x, y + yt)) {
            yt *= (-1);
        }

        return new Point2D(xt, yt);

    }

}
