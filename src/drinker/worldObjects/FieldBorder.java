package drinker.worldObjects;

import drinker.WorldObject;

/**
 * FieldBorder is ground around filed.
 * It's used to understand where units can't be
 * With this becomes possible to make no-rectangle field
 */
public class FieldBorder extends WorldObject {


    public FieldBorder(int x, int y) {
        super(x, y);
    }

    @Override
    public char draw() {
        return ' ';
    }


}
