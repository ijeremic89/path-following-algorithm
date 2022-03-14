package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.MatrixIsEmptyOrNullException;
import sauna.pathFollowingAlgorithm.exceptions.IndicatorPositionNotFoundException;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.repositories.MatrixRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LoadMatrix {
    MatrixRepository matrixRepository = new MatrixRepository();

    public MatrixModel loadMatrix(int matrixId) {
        String matrixAsString = matrixRepository.getMatrix(matrixId);
        MatrixModel matrixModel = new MatrixModel();
        matrixModel.setMatrix(createMatrixFromString(matrixAsString));


        if (StringUtils.isEmpty(matrixAsString)) {
            throw new MatrixIsEmptyOrNullException();
        }

        PositionModel startPosition = findIndicatorPositionInMatrix(matrixModel.getMatrix(), Indicators.START);
        PositionModel endPosition = findIndicatorPositionInMatrix(matrixModel.getMatrix(), Indicators.END);

        if (Objects.isNull(startPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.START);
        }

        if (Objects.isNull(endPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.END);
        }

        matrixModel.setStartingPosition(startPosition);

        return matrixModel;
    }

    public String[][] createMatrixFromString(String text) {
        int rowNumber = text.split(System.lineSeparator()).length;
        int columnNumber = text.split(System.lineSeparator())[0].length();
        String adjusted = text.replaceAll(Regex.NEW_LINE, StringUtils.EMPTY);

        String[][] matrix = new String[rowNumber][columnNumber];
        int counter = 0;

        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                matrix[i][j] = String.valueOf(adjusted.charAt(counter));
                counter++;
            }
        }
        return matrix;
    }

    public PositionModel findIndicatorPositionInMatrix(String[][] matrix, String indicator) {
        PositionModel position = new PositionModel();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals(indicator)) {
                    position.setX(j);
                    position.setY(i);
                    position.setValue(indicator);
                    position.setLastDirection(Direction.NoDirection);
                    return position;
                }
            }
        }
        return null;
    }
}
