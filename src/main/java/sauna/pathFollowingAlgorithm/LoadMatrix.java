package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.constants.Regex;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartOrEndInMatrixException;
import sauna.pathFollowingAlgorithm.exceptions.MatrixHasWrongCharactersException;
import sauna.pathFollowingAlgorithm.exceptions.MatrixIsEmptyOrNullException;
import sauna.pathFollowingAlgorithm.exceptions.IndicatorPositionNotFoundException;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;
import sauna.pathFollowingAlgorithm.repositories.MatrixRepository;
import org.apache.commons.lang3.StringUtils;
import sauna.pathFollowingAlgorithm.utils.IndicatorUtils;

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

        if (!matrixAsString.matches(Regex.ACCEPTED_CHARACTERS)) {
            throw new MatrixHasWrongCharactersException();
        }

        if (IndicatorUtils.isDuplicateIndicatorInMatrix(matrixModel, Indicators.START) || IndicatorUtils.isDuplicateIndicatorInMatrix(matrixModel, Indicators.END)) {
            throw new MultipleStartOrEndInMatrixException();
        }

        PositionModel startPosition = IndicatorUtils.findIndicatorPositionInMatrix(matrixModel, Indicators.START);
        PositionModel endPosition = IndicatorUtils.findIndicatorPositionInMatrix(matrixModel, Indicators.END);

        if (Objects.isNull(startPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.START);
        }

        if (Objects.isNull(endPosition)) {
            throw new IndicatorPositionNotFoundException(Indicators.END);
        }

        matrixModel.setStartingPosition(startPosition);
        return matrixModel;
    }

    private String[][] createMatrixFromString(String text) {
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
}
