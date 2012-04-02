package drinker;

import java.util.ArrayDeque;

public abstract class WorldObject {

    protected World world;
    protected int x;
    protected int y;

    protected ArrayDeque<ObjectEventHandler> onEnterEvent;
    protected ArrayDeque<ObjectEventHandler> onMutuallyCollisionEvent;
        

    public WorldObject(int x, int y) {
        this.x = x;
        this.y = y;

        this.onEnterEvent = new ArrayDeque<ObjectEventHandler>();
        this.onMutuallyCollisionEvent = new ArrayDeque<ObjectEventHandler>();
    }

    public void addEnterHandler(ObjectEventHandler handler) {
        this.onEnterEvent.add(handler);
    }
    

    /**
     * Event triggering after collision processing
     * @param handler ... 
     */
    public void addMutuallyCollisionHandler(ObjectEventHandler handler) {
        this.onMutuallyCollisionEvent.add(handler);
    }
  
    
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char draw();

    public void onTick() {

    }

    public void onCollision(WorldObject o) {

    }

    public void onEnter(WorldObject o) {

        if (this.onEnterEvent.isEmpty())
            return;

        for (ObjectEventHandler h : this.onEnterEvent) {
            h.onEvent(o);
        }

    }

    public void setWorld(World w) {
        this.world = w;
    }
    
    public boolean isSuspended(){
        return false;
    }
    
    public boolean isMovable() {
        return false;
    }

    public boolean isStopAble() {
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

        for (ObjectEventHandler h : a.onMutuallyCollisionEvent) {
            h.onEvent(b);
        }

        for (ObjectEventHandler h : b.onMutuallyCollisionEvent) {
            h.onEvent(a);
        }
        
    }

}
