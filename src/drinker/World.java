package drinker;

import drinker.WorldObjects.*;
import drinker.utils.ObjectEventHandler;
import drinker.utils.WorldEvent;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.*;

public class World {

    public final int width;
    public final int height;
    public final PrintStream stream;

    // defaults objects
    public final Pole pole;
    public final Tavern tavern;
    public final Lamp lamp;
    public final PoliceStation policeStation;
    public final Policeman policeman;

    private ArrayList<WorldObject> movableObjects;
    private ArrayList<WorldObject> worldObjects[][];

    protected WorldEvent onAddObjectEvent = new WorldEvent();
    protected WorldEvent onPreTickEvent = new WorldEvent();
    protected WorldEvent onPostTickEvent = new WorldEvent();

    @SuppressWarnings({"unchecked"})
    public World(int width, int height, PrintStream stream) {

        this.width = width;
        this.height = height;
        this.stream = stream;
        this.movableObjects = new ArrayList<WorldObject>();


        this.worldObjects = (ArrayList<WorldObject>[][])
                Array.newInstance(ArrayList.class, width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                worldObjects[i][j] = new ArrayList<WorldObject>();
            }
        }

        pole = new Pole(7, 7);
        tavern = new Tavern(9, 0);
        lamp = new Lamp(7, 3);
        policeStation = new PoliceStation(15, 3);
        policeman = new Policeman(lamp, policeStation, policeStation.getX(), policeStation.getY());

        InitObjects();


    }

    private void InitObjects() {

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.addObject(new Ground(i, j));
            }
        }


        this.addObject(lamp);
        lamp.switchOn();

        for (int i = 0; i < 16; i++) {
            if (i == 9)
                continue;
            this.addObject(new WhiteSpace(i, 0));
        }

        for (int i = 0; i < 16; i++) {
            if (i == 3)
                continue;
            this.addObject(new WhiteSpace(15, i));
        }


        this.addObject(pole);
        this.addObject(tavern);
        this.addObject(policeStation);
        this.addObject(policeman);
        policeman.bindToLamp();


    }

    private int tickCursor = 0;
    private int tickCount = 0;

    public void tick() {
        
        tickCount++;
        tickCursor--;

        onPreTickEvent.emit(null);

        if (tickCount % 20 == 0) {
            addToper();
            return;
        }

        if (movableObjects.isEmpty()) {
            return;
        }

        if (tickCursor < 0) {
            tickCursor = movableObjects.size() - 1;
        }

        tickObject(movableObjects.get(tickCursor));

        onPostTickEvent.emit(null);

    }

    public void drawScene() {

        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {

                stream.print(worldObjects[i][j].get(
                        worldObjects[i][j].size() - 1).draw());

            }
            stream.println();
        }

    }


    public void addObject(WorldObject o) {

        o.setWorld(this);
        if (o.isMovable())
            movableObjects.add(o);

        worldObjects[o.getX()][o.getY()].add(o);

        onAddObjectEvent.emit(o);

    }

    public void addAddObjectHandler(ObjectEventHandler handler) {
        this.onAddObjectEvent.add(handler);
    }

    public void addPreTickEvent(ObjectEventHandler handler) {
        onPreTickEvent.add(handler);
    }

    public void addPostTickEvent(ObjectEventHandler handler) {
        onPostTickEvent.add(handler);
    }

    public void removeObject(WorldObject o) {
        if (movableObjects.contains(o)) {
            movableObjects.remove(o);
        }
        if (worldObjects[o.getX()][o.getY()].contains(o)) {
            worldObjects[o.getX()][o.getY()].remove(o);
        }
    }

    public Collection<WorldObject> getObjectAtXY(int x, int y) {

        if (x < 0 || x >= this.width || y < 0 || y >= this.height)
            return null;

        if (worldObjects[x][y] == null)
            return null;

        return Collections.unmodifiableCollection(worldObjects[x][y]);

    }


    private void addToper() {
        addObject(new Toper(9, 1));
    }

    private void tickObject(WorldObject o) {
                    
        int x = o.getX();
        int y = o.getY();

        o.onWorldTick();

        int newX = o.getX();
        int newY = o.getY();

        if (newX != x || newY != y) {
            worldObjects[x][y].remove(o);
            for (WorldObject wo : worldObjects[newX][newY]) {
                wo.onEnter(o);
            }
            worldObjects[newX][newY].add(o);
        }

        if (!o.isMovable()) {
            movableObjects.remove(o);
        }
                
        
    }

    /**
     * Is place free to move for policeman?
     * It reacts almost the same as toper
     *
     * @param x position in the world
     * @param y position in the world
     * @return is position free to move
     */
    public boolean isTakePlace(int x, int y) {

        Collection<WorldObject> collection = this.getObjectAtXY(x, y);

        if (collection == null)
            return true;

        for (WorldObject w : collection) {
            if (w.isStopAble() || w.isSuspendAble())
                return true;
        }


        return false;

    }

    public Point2D findDirectionOnClosestPath(
            WorldObject start, WorldObject finish) {

        if (start.isSameLocation(finish))
            return new Point2D(0, 0);


        Queue<Point2D> queue = new
                ArrayDeque<Point2D>();

        queue.add(new Point2D(start.getX(), start.getY()));

        Point2D[][] prevMatrix = (Point2D[][])
                Array.newInstance(
                        (new Point2D(0, 0)).getClass(),
                        width, height);

        prevMatrix[start.getX()][start.getY()] = new Point2D(0, 0);

        while (!queue.isEmpty()) {

            Point2D next = queue.poll();

            Point2D nextStep = new Point2D(0, 0);

            nextStep.x = -1;
            nextStep.y = 0;
            if (tryAddToQueue(next, nextStep, finish, queue, prevMatrix)) {
                break;
            }

            nextStep.x = 1;
            nextStep.y = 0;
            if (tryAddToQueue(next, nextStep, finish, queue, prevMatrix)) {
                break;
            }

            nextStep.x = 0;
            nextStep.y = 1;
            if (tryAddToQueue(next, nextStep, finish, queue, prevMatrix)) {
                break;
            }

            nextStep.x = 0;
            nextStep.y = -1;
            if (tryAddToQueue(next, nextStep, finish, queue, prevMatrix)) {
                break;
            }
        }

        int nextPrevX = finish.getX();
        int nextPrevY = finish.getY();

        Point2D prevPoint = new Point2D();

        while (!(nextPrevX == start.getX() && nextPrevY == start.getY())) {

            if (prevMatrix[nextPrevX][nextPrevY] == null)
                return null;

            prevPoint.x = nextPrevX;
            prevPoint.y = nextPrevY;

            nextPrevX = prevMatrix[prevPoint.x][prevPoint.y].x;
            nextPrevY = prevMatrix[prevPoint.x][prevPoint.y].y;


        }

        return new Point2D(prevPoint.x - start.getX(),
                prevPoint.y - start.getY());


    }

    private boolean tryAddToQueue(Point2D current,
                                  Point2D step,
                                  WorldObject finish,
                                  Queue<Point2D> queue,
                                  Point2D[][] prevMatrix) {

        int newX = current.x + step.x;
        int newY = current.y + step.y;

        if (newX == finish.getX() && newY == finish.getY()) {
            prevMatrix[newX][newY] = current.copy();
            return true;
        }


        if (!isTakePlace(newX, newY) && prevMatrix[newX][newY] == null) {
            queue.add(new Point2D(newX, newY));
            prevMatrix[newX][newY] = current.copy();
        }


        return false;

    }

    public boolean isPossibleForStep(int x, int y) {
        return !(x >= width - 1 || x < 0 || y >= height || y < 1);
    }

}
