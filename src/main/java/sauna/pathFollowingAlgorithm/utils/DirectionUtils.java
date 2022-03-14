package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.enums.Direction;

public class DirectionUtils {
    public static Direction getOppositeDirection(Direction direction) {
        Direction oppositeDirection = Direction.NoDirection;

        switch (direction) {
            case Right:
                oppositeDirection = Direction.Left;
                break;
            case Left:
                oppositeDirection = Direction.Right;
                break;
            case Up:
                oppositeDirection = Direction.Down;
                break;
            case Down:
                oppositeDirection = Direction.Up;
                break;
        }
        return oppositeDirection;
    }
}
