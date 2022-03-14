package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.BrokenPathException;
import sauna.pathFollowingAlgorithm.exceptions.InvalidOrEmptyPositionInMatrix;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartingPathsException;
import sauna.pathFollowingAlgorithm.exceptions.TwoPathsOnCornerException;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PathResultModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.utils.DirectionUtils;
import sauna.pathFollowingAlgorithm.utils.PositionUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

            Direction direction = findDirection(matrixModel, currentPosition, positionHistory);
            currentPosition = nextPosition(matrixModel, currentPosition, direction, positionHistory);
            path.append(currentPosition.getValue());

            if (currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
                letters.append(currentPosition.getValue());
            }
        }

        return new PathResultModel(letters.toString(), path.toString());
    }

    public Direction findDirection(MatrixModel matrix, PositionModel currentPosition, List<PositionModel> positionHistory) {


        // START
        if (currentPosition.getValue().equals(Indicators.START)) {
            List<Direction> possibleDirections = findPossibleDirections(matrix, currentPosition);

            if (possibleDirections.isEmpty()) {
                throw new BrokenPathException();
            }
            if (possibleDirections.size() > 1) {
                throw new MultipleStartingPathsException();
            }
            return possibleDirections.stream().findFirst().orElse(Direction.NoDirection);
        }

        //HORIZONTAL OR VERTICAL
        if (currentPosition.getValue().equals(Indicators.HORIZONTAL) || currentPosition.getValue().equals(Indicators.VERTICAL)) {
            return currentPosition.getLastDirection();
        }

        //CORNER
        if (currentPosition.getValue().equals(Indicators.CORNER)) {
            List<Direction> possibleDirections = findPossibleDirections(matrix, currentPosition);


            if (currentPosition.getLastDirection().equals(Direction.Up) || currentPosition.getLastDirection().equals(Direction.Down)) {

                if (possibleDirections.contains(Direction.Right) && possibleDirections.contains(Direction.Left)) {
                    throw new TwoPathsOnCornerException();
                }

                List<Direction> goodDir = new ArrayList<>();
                goodDir.add(Direction.Right);
                goodDir.add(Direction.Left);

                return possibleDirections.stream().filter(x -> x.equals(Direction.Left) || x.equals(Direction.Right)).findFirst().get();
            }

            if (currentPosition.getLastDirection().equals(Direction.Right) || currentPosition.getLastDirection().equals(Direction.Left)) {

                if (possibleDirections.contains(Direction.Up) && possibleDirections.contains(Direction.Down)) {
                    throw new TwoPathsOnCornerException();
                }

                List<Direction> goodDir = new ArrayList<>();
                goodDir.add(Direction.Up);
                goodDir.add(Direction.Down);

                return possibleDirections.stream().filter(x -> x.equals(Direction.Up) || x.equals(Direction.Down)).findFirst().get();
            }
        }

        //LETTERS
        if (currentPosition.getValue().matches(Regex.CAPITAL_LETTERS)) {
            List<Direction> possibleDirections = findPossibleDirections(matrix, currentPosition);
            return possibleDirections.stream().findFirst().get();
        }


        return Direction.NoDirection;
    }

    public List<Direction> findPossibleDirections(MatrixModel matrix, PositionModel currentPosition) {
        List<Direction> possibleDirections = new ArrayList<>();

        PositionModel right = new PositionModel(currentPosition.getX() + 1, currentPosition.getY());
        PositionModel left = new PositionModel(currentPosition.getX() - 1, currentPosition.getY());
        PositionModel up = new PositionModel(currentPosition.getX(), currentPosition.getY() - 1);
        PositionModel down = new PositionModel(currentPosition.getX(), currentPosition.getY() + 1);

        List<PositionModel> positions = Arrays.asList(right, left, up, down);


//        possibleDirections.add(validDirection(matrix, right, Direction.Right, currentPosition));
//        possibleDirections.add(validDirection(matrix, left, Direction.Left, currentPosition));
//        possibleDirections.add(validDirection(matrix, up, Direction.Up, currentPosition));
//        possibleDirections.add(validDirection(matrix, down, Direction.Down, currentPosition));
//
//        return possibleDirections.stream().filter(Objects::nonNull).collect(Collectors.toList());


        //Right
        if (PositionUtils.isValidMatrixPosition(matrix, right)
                && !PositionUtils.isPositionWithEmptyIndicator(right, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(Direction.Right)) {
            possibleDirections.add(Direction.Right);
        }

        //Left
        if (PositionUtils.isValidMatrixPosition(matrix, left)
                && !PositionUtils.isPositionWithEmptyIndicator(left, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(Direction.Left)) {
            possibleDirections.add(Direction.Left);
        }

        //Up
        if (PositionUtils.isValidMatrixPosition(matrix, up)
                && !PositionUtils.isPositionWithEmptyIndicator(up, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(Direction.Up)) {
            possibleDirections.add(Direction.Up);
        }

        //Down
        if (PositionUtils.isValidMatrixPosition(matrix, down)
                && !PositionUtils.isPositionWithEmptyIndicator(down, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(Direction.Down)) {
            possibleDirections.add(Direction.Down);
        }

        return possibleDirections;
    }

    public Direction validDirection(MatrixModel matrix, PositionModel positionModel, Direction direction, PositionModel currentPosition) {
        if (PositionUtils.isValidMatrixPosition(matrix, positionModel)
                && !PositionUtils.isPositionWithEmptyIndicator(positionModel, matrix)
                && !DirectionUtils.getOppositeDirection(currentPosition.getLastDirection()).equals(Direction.Down)) {
            return direction;
        }
        return null;
    }

    public PositionModel nextPosition(MatrixModel matrix, PositionModel currentPosition, Direction nextDirection, List<PositionModel> positionHistory) {

        currentPosition = move(matrix, currentPosition, nextDirection);

        if (PositionUtils.isValidMatrixPosition(matrix, currentPosition) && !PositionUtils.isPositionWithEmptyIndicator(currentPosition, matrix)) {

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
