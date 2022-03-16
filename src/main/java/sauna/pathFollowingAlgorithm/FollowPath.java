package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.*;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PathResultModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.utils.DirectionUtils;
import sauna.pathFollowingAlgorithm.utils.PositionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FollowPath {

    public PathResultModel followPath(MatrixModel matrixModel) {
        StringBuilder letters = new StringBuilder();
        StringBuilder path = new StringBuilder();
        List<PositionModel> positionHistory = new ArrayList<>();

        PositionModel currentPosition = matrixModel.getStartingPosition();
        path.append(currentPosition.getValue());

        while (!currentPosition.getValue().equals(Indicators.END)) {
            positionHistory.add(PositionUtils.copyPosition(currentPosition));
            List<Direction> possibleDirections = findPossibleDirections(matrixModel, currentPosition);
            Direction direction = findDirection(currentPosition, possibleDirections);
            currentPosition = nextPosition(matrixModel, currentPosition, direction, positionHistory);
            path.append(currentPosition.getValue());

            if (currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
                letters.append(currentPosition.getValue());
            }
        }

        return new PathResultModel(letters.toString(), path.toString());
    }

    private Direction findDirection(PositionModel currentPosition, List<Direction> possibleDirections) {
        Direction lastDirection = currentPosition.getLastDirection();
        boolean lastDirectionIsPossibleDirection =
                possibleDirections.stream()
                        .anyMatch(direction -> direction.equals(currentPosition.getLastDirection()));

        if (possibleDirections.stream().findFirst().isEmpty()) {
            throw new BrokenPathException();
        }

        if (currentPosition.getValue().equals(Indicators.HORIZONTAL) || currentPosition.getValue().equals(Indicators.VERTICAL)) {
            return lastDirection;
        }

        if (currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
            if (lastDirectionIsPossibleDirection) {
                return currentPosition.getLastDirection();
            }
        }

        if (currentPosition.getValue().equals(Indicators.CORNER)) {

            if (lastDirectionIsPossibleDirection) {
                if (possibleDirections.size() > 1) {
                    possibleDirections.remove(currentPosition.getLastDirection());
                } else {
                    throw new FakeTurnException();
                }
            }
        }

        DirectionUtils.checkMultipleDirections(possibleDirections, currentPosition);
        return possibleDirections.stream().findFirst().get();
    }

    private List<Direction> findPossibleDirections(MatrixModel matrix, PositionModel currentPosition) {
        PositionModel right = new PositionModel(currentPosition.getX() + 1, currentPosition.getY());
        PositionModel left = new PositionModel(currentPosition.getX() - 1, currentPosition.getY());
        PositionModel up = new PositionModel(currentPosition.getX(), currentPosition.getY() - 1);
        PositionModel down = new PositionModel(currentPosition.getX(), currentPosition.getY() + 1);

        List<Direction> possibleDirections = new ArrayList<>();
        possibleDirections.add(getValidDirection(matrix, right, Direction.Right, currentPosition));
        possibleDirections.add(getValidDirection(matrix, left, Direction.Left, currentPosition));
        possibleDirections.add(getValidDirection(matrix, up, Direction.Up, currentPosition));
        possibleDirections.add(getValidDirection(matrix, down, Direction.Down, currentPosition));

        return possibleDirections.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Direction getValidDirection(MatrixModel matrix, PositionModel nextPosition, Direction nextDirection, PositionModel currentPosition) {
        if (PositionUtils.isPositionInMatrix(matrix, nextPosition)
                && PositionUtils.positionNotEmpty(nextPosition, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(nextDirection)) {
            return nextDirection;
        }
        return null;
    }

    private PositionModel nextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection, List<PositionModel> positionHistory) {
        currentPosition = moveToNextPosition(matrix, currentPosition, nextDirection);

        if (!PositionUtils.isPositionAlreadyUsed(currentPosition, positionHistory)) {
            return currentPosition;
        }

        if (PositionUtils.isPositionAlreadyUsed(currentPosition, positionHistory)) {
            return moveToNextPosition(matrix, currentPosition, nextDirection);
        }

        return currentPosition;
    }

    private PositionModel moveToNextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection) {
        switch (nextDirection) {
            case Right:
                currentPosition.setX(currentPosition.getX() + 1);
                break;
            case Left:
                currentPosition.setX(currentPosition.getX() - 1);
                break;
            case Down:
                currentPosition.setY(currentPosition.getY() + 1);
                break;
            case Up:
                currentPosition.setY(currentPosition.getY() - 1);
                break;
        }

        currentPosition.setValue(matrix.getMatrix()[currentPosition.getY()][currentPosition.getX()]);
        currentPosition.setLastDirection(nextDirection);

        return currentPosition;
    }
}
