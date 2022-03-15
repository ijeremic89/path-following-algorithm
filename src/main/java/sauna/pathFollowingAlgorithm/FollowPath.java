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

            Direction direction = findDirection(matrixModel, currentPosition);
            currentPosition = nextPosition(matrixModel, currentPosition, direction, positionHistory);
            path.append(currentPosition.getValue());

            if (currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
                letters.append(currentPosition.getValue());
            }
        }

        return new PathResultModel(letters.toString(), path.toString());
    }

    public Direction findDirection(MatrixModel matrix, PositionModel currentPosition) {
        Direction direction = findPossibleDirections(matrix, currentPosition);

        if (currentPosition.getValue().equals(Indicators.HORIZONTAL) || currentPosition.getValue().equals(Indicators.VERTICAL)) {
            return currentPosition.getLastDirection();
        }
        return direction;
    }

    public Direction findPossibleDirections(MatrixModel matrix, PositionModel currentPosition) {
        List<Direction> possibleDirections = new ArrayList<>();

        PositionModel right = new PositionModel(currentPosition.getX() + 1, currentPosition.getY());
        PositionModel left = new PositionModel(currentPosition.getX() - 1, currentPosition.getY());
        PositionModel up = new PositionModel(currentPosition.getX(), currentPosition.getY() - 1);
        PositionModel down = new PositionModel(currentPosition.getX(), currentPosition.getY() + 1);

        possibleDirections.add(validDirection(matrix, right, Direction.Right, currentPosition));
        possibleDirections.add(validDirection(matrix, left, Direction.Left, currentPosition));
        possibleDirections.add(validDirection(matrix, up, Direction.Up, currentPosition));
        possibleDirections.add(validDirection(matrix, down, Direction.Down, currentPosition));

        possibleDirections = possibleDirections.stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (possibleDirections.isEmpty()) {
            throw new BrokenPathException();
        }

        if (possibleDirections.size() > 1) {
            if (currentPosition.getValue().equals(Indicators.START)) {
                throw new MultipleStartingPathsException();
            }
            if (currentPosition.getValue().equals(Indicators.CORNER)) {
                throw new FakeTurnException();
            }
        }

        return possibleDirections.stream().findFirst().get();
    }

    public Direction validDirection(MatrixModel matrix, PositionModel nextPosition, Direction nextDirection, PositionModel currentPosition) {
        if (PositionUtils.isPositionInMatrix(matrix, nextPosition)
                && PositionUtils.positionNotEmpty(nextPosition, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(nextDirection)) {
            return nextDirection;
        }
        return null;
    }

    public PositionModel nextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection, List<PositionModel> positionHistory) {
        currentPosition = move(matrix, currentPosition, nextDirection);

        if (PositionUtils.isPositionInMatrix(matrix, currentPosition) && PositionUtils.positionNotEmpty(currentPosition, matrix)) {
            if (!PositionUtils.isPositionAlreadyUsed(currentPosition, positionHistory)) {
                return currentPosition;
            }

            if (PositionUtils.isPositionAlreadyUsed(currentPosition, positionHistory)) {
                return move(matrix, currentPosition, nextDirection);
            }
        } else {
            throw new InvalidOrEmptyPositionInMatrix();
        }

        return currentPosition;
    }

    public PositionModel move(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection) {
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
