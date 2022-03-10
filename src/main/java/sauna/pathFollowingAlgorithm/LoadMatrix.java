package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.exceptions.MatrixIsEmptyOrNullException;
import sauna.pathFollowingAlgorithm.exceptions.IndicatorPositionNotFoundException;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.repositories.MatrixRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LoadMatrix {
    MatrixRepository matrixRepository = new MatrixRepository();

    public String[][] loadMatrix(int matrixId) {
        String matrixAsString = matrixRepository.getMatrix("matrix_" + matrixId);

        if (StringUtils.isEmpty(matrixAsString)) {
            throw new MatrixIsEmptyOrNullException();
        }

        String[][] matrix = createMatrixFromString(matrixAsString);

        if (!isValidMatrix(matrix)) {
            return null;
        }

        return matrix;
    }

    public String[][] createMatrixFromString(String text) {
        int rowNumber = text.split(System.lineSeparator()).length;
        int columnNumber = text.split(System.lineSeparator())[0].length();
        String[][] matrix = new String[rowNumber][columnNumber];

        int counter = 0;
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {

                String x = (!String.valueOf(text.charAt(counter)).equals(System.lineSeparator()))
                        ? String.valueOf(text.charAt(counter))
                        : String.valueOf(text.charAt(counter++));

                matrix[i][j] = x;
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
                    return position;
                }
            }
        }
        return null;
    }

    public boolean isValidMatrix(String[][] matrix) {
        PositionModel startPosition = findIndicatorPositionInMatrix(matrix, Indicators.START);
        PositionModel endPosition = findIndicatorPositionInMatrix(matrix, Indicators.END);

        if (Objects.isNull(startPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.START);
        }

        if (Objects.isNull(endPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.END);
        }

        return true;
    }
}
