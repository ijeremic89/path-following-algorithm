package sauna.pathFollowingAlgorithm.utils;

import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

public class IndicatorUtils {
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

    public static boolean isDuplicateIndicatorInMatrix(MatrixModel matrixModel, String indicator) {
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
}
