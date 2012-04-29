package main.java.drinker;

import main.java.drinker.utils.ObjectEventHandler;
import main.java.drinker.utils.WorldEvent;

public abstract class WorldObject {

    protected World world;
    protected int x;
    protected int y;

    protected WorldEvent onEnterEvent = new WorldEvent();
    public WorldEvent onMutuallyCollisionEvent = new WorldEvent();
    protected WorldEvent onPreTickEvent = new WorldEvent();
    protected WorldEvent onPostTickEvent = new WorldEvent();


    public WorldObject(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void addEnterHandler(ObjectEventHandler handler) {
        this.onEnterEvent.add(handler);
    }


    /**
     * Event triggering after collision processing
     *
     * @param handler ...
     */
    public void addMutuallyCollisionHandler(ObjectEventHandler handler) {
        this.onMutuallyCollisionEvent.add(handler);
    }


    /**
     * object will be null
     *
     * @param e will
     */
    public void addPreTickEvent(ObjectEventHandler e) {
        onPreTickEvent.add(e);
    }

    public void addPostTickEvent(ObjectEventHandler e) {
        onPostTickEvent.add(e);
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }
    
    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public abstract char draw();

    /**
     * proxy method for onTickEvent()
     * need for trigger event
     */
    public final void onWorldTick() {
        onPreTickEvent.emit(this);
        onTick();
        onPostTickEvent.emit(this);
    }

    public void onTick() {
    }

    public void onCollision(WorldObject o) {
    }

    public void onEnter(WorldObject o) {
        onEnterEvent.emit(o);
    }

    public void setWorld(World w) {
        this.world = w;
    }

    public boolean isSuspended() {
        return false;
    }

    public boolean isMovable() {
        return false;
    }

    public boolean isReasonToStop() {
        return false;
    }

    public boolean isSuspendAble() {
        return false;
    }

    /**
     * True means that toper will not fully stop.
     * Only stumble.
     *
     * @return true or false
     */
    public boolean isLatencySuspendAble() {
        return false;
    }

    public boolean isNeedPoliceHelp() {
        return false;
    }

    public boolean isSameLocation(WorldObject o) {
        return (this.x == o.x) && (this.y == o.y);
    }

    public static void mutuallyCollision(WorldObject a, WorldObject b) {

        a.onCollision(b);
        b.onCollision(a);

        a.onMutuallyCollisionEvent.emit(b);
        b.onMutuallyCollisionEvent.emit(a);

    }

}
