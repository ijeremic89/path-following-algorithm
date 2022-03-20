package sauna.pathFollowingAlgorithm.validation;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartingPathsException;
import sauna.pathFollowingAlgorithm.exceptions.MultipleTurnsException;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

import java.util.List;

public class MatrixValidator {
    public boolean isPositionInMatrix(MatrixModel matrix, PositionModel position) {
        try {
            String value = matrix.getMatrix()[position.getY()][position.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public boolean isEmptyPosition(PositionModel position, MatrixModel matrix) {
        return matrix.getMatrix()[position.getY()][position.getX()].equals(" ");
    }

    public boolean isPositionAlreadyUsed(PositionModel positionModel, List<PositionModel> positionHistory) {
        return positionHistory.stream()
                              .anyMatch(position -> positionModel.getX() == position.getX() && positionModel.getY() == position.getY());
    }

    public boolean isDuplicateIndicatorInMatrix(MatrixModel matrixModel, String indicator) {
        int counter = 0;
        for (String[] strings : matrixModel.getMatrix()) {
            for (String string : strings) {
                if (string.equals(indicator)) {
                    counter++;
                }
            }
        }
        return counter > 1;
    }

    public void checkMultipleDirections(List<Direction> possibleDirections, PositionModel currentPosition) {
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
