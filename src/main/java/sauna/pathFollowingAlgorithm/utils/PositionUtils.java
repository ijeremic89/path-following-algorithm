package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

import java.util.List;

public class PositionUtils {

    public static boolean isPositionInMatrix(MatrixModel matrix, PositionModel position) {
        try {
            String value = matrix.getMatrix()[position.getY()][position.getX()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public static boolean positionNotEmpty(PositionModel position, MatrixModel matrix) {
        return !matrix.getMatrix()[position.getY()][position.getX()].equals(" ");
    }

    public static boolean isPositionAlreadyUsed(PositionModel positionModel, List<PositionModel> positionHistory) {
        return positionHistory.stream()
                              .anyMatch(position -> positionModel.getX() == position.getX() && positionModel.getY() == position.getY());
    }

    public static PositionModel copyPosition(PositionModel currentPosition) {
        PositionModel positionModel = new PositionModel();
        positionModel.setX(currentPosition.getX());
        positionModel.setY(currentPosition.getY());
        positionModel.setValue(currentPosition.getValue());
        positionModel.setLastDirection(currentPosition.getLastDirection());
        return positionModel;
    }
}
