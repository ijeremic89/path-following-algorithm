package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.BrokenPathException;
import sauna.pathFollowingAlgorithm.exceptions.FakeTurnException;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PathResultModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.utils.DirectionUtils;
import sauna.pathFollowingAlgorithm.utils.PositionUtils;
import sauna.pathFollowingAlgorithm.validation.MatrixValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathFollower {
    MatrixValidator matrixValidator = new MatrixValidator();

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

        if (currentPosition.getValue().equals(Indicators.HORIZONTAL) ||
                currentPosition.getValue().equals(Indicators.VERTICAL)) {
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
        matrixValidator.checkMultipleDirections(possibleDirections, currentPosition);
        return possibleDirections.stream().findFirst().get();
    }

    private List<Direction> findPossibleDirections(MatrixModel matrix, PositionModel currentPosition) {
        PositionModel right = new PositionModel(currentPosition.getX() + 1, currentPosition.getY());
        PositionModel left = new PositionModel(currentPosition.getX() - 1, currentPosition.getY());
        PositionModel up = new PositionModel(currentPosition.getX(), currentPosition.getY() - 1);
        PositionModel down = new PositionModel(currentPosition.getX(), currentPosition.getY() + 1);

        List<Direction> possibleDirections = new ArrayList<>();
        possibleDirections.add(getValidDirection(matrix, right, Direction.RIGHT, currentPosition));
        possibleDirections.add(getValidDirection(matrix, left, Direction.LEFT, currentPosition));
        possibleDirections.add(getValidDirection(matrix, up, Direction.UP, currentPosition));
        possibleDirections.add(getValidDirection(matrix, down, Direction.DOWN, currentPosition));

        return possibleDirections.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Direction getValidDirection(MatrixModel matrix, PositionModel nextPosition, Direction nextDirection, PositionModel currentPosition) {
        if (matrixValidator.isPositionInMatrix(matrix, nextPosition) &&
                !matrixValidator.isEmptyPosition(nextPosition, matrix) &&
                !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(nextDirection)) {
            return nextDirection;
        }
        return null;
    }

    private PositionModel nextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection, List<PositionModel> positionHistory) {
        moveToNextPosition(matrix, currentPosition, nextDirection);

        if (!matrixValidator.isPositionAlreadyUsed(currentPosition, positionHistory)) {
            return currentPosition;
        }

        if (matrixValidator.isPositionAlreadyUsed(currentPosition, positionHistory)) {
            return moveToNextPosition(matrix, currentPosition, nextDirection);
        }
        return currentPosition;
    }

    private PositionModel moveToNextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection) {
        switch (nextDirection) {
            case RIGHT:
                currentPosition.setX(currentPosition.getX() + 1);
                break;
            case LEFT:
                currentPosition.setX(currentPosition.getX() - 1);
                break;
            case DOWN:
                currentPosition.setY(currentPosition.getY() + 1);
                break;
            case UP:
                currentPosition.setY(currentPosition.getY() - 1);
                break;
        }
        currentPosition.setValue(matrix.getMatrix()[currentPosition.getY()][currentPosition.getX()]);
        currentPosition.setLastDirection(nextDirection);
        return currentPosition;
    }
}
