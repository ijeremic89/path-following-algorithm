package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartingPathsException;
import sauna.pathFollowingAlgorithm.exceptions.MultipleTurnsException;
import sauna.pathFollowingAlgorithm.models.PositionModel;

import java.util.List;

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

    public static void checkMultipleDirections(List<Direction> possibleDirections, PositionModel currentPosition) {
        if (possibleDirections.size() > 1) {
            if (currentPosition.getValue().equals(Indicators.START)) {
                throw new MultipleStartingPathsException();
            }
            if (currentPosition.getValue().equals(Indicators.CORNER) || currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
                throw new MultipleTurnsException();
            }
        }
    }
}
