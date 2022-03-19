package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.enums.Direction;

public class DirectionUtils {
    public static Direction getOppositeDirection(Direction direction) {
        switch (direction) {
            case RIGHT:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.RIGHT;
                break;
            case UP:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.UP;
                break;
        }
        return direction;
    }
}
