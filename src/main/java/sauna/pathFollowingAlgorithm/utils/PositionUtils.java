package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

public class PositionUtils {
    public static PositionModel copyPosition(PositionModel currentPosition) {
        PositionModel positionModel = new PositionModel();
        positionModel.setX(currentPosition.getX());
        positionModel.setY(currentPosition.getY());
        positionModel.setValue(currentPosition.getValue());
        positionModel.setLastDirection(currentPosition.getLastDirection());
        return positionModel;
    }

    public static PositionModel findIndicatorPositionInMatrix(MatrixModel matrixModel, String indicator) {
        PositionModel position = new PositionModel();
        String[][] matrix = matrixModel.getMatrix();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals(indicator)) {
                    position.setX(j);
                    position.setY(i);
                    position.setValue(indicator);
                    position.setLastDirection(Direction.NO_DIRECTION);
                    return position;
                }
            }
        }
        return null;
    }
}
