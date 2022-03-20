package sauna.pathFollowingAlgorithm.helpers.matrix;

import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

public class MatrixHelper {
    public static MatrixModel createMatrixModel(String text, int rows, int columns) {
        MatrixModel matrixModel = new MatrixModel();
        PositionModel startingPosition = new PositionModel(0, 0, "@", Direction.NO_DIRECTION);
        String[][] matrix = new String[rows][columns];
        int counter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = String.valueOf(text.charAt(counter));
                counter++;
            }
        }
        matrixModel.setMatrix(matrix);
        matrixModel.setStartingPosition(startingPosition);
        return matrixModel;
    }
}
