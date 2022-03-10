package sauna.pathFollowingAlgorithm.models;

import sauna.pathFollowingAlgorithm.enums.Direction;

public class PositionModel {
    private int x;
    private int y;
    private String value;
    private Direction lastDirection;

    public PositionModel() {
    }

    public PositionModel(int x, int y, String value, Direction lastDirection) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.lastDirection = lastDirection;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Direction getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(Direction lastDirection) {
        this.lastDirection = lastDirection;
    }
}
