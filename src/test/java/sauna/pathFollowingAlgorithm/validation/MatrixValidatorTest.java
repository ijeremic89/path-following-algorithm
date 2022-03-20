package sauna.pathFollowingAlgorithm.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sauna.pathFollowingAlgorithm.constants.Indicators;
import sauna.pathFollowingAlgorithm.enums.Direction;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartingPathsException;
import sauna.pathFollowingAlgorithm.exceptions.MultipleTurnsException;
import sauna.pathFollowingAlgorithm.helpers.matrix.MatrixHelper;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PositionModel;

import java.util.ArrayList;
import java.util.List;

public class MatrixValidatorTest {

    public static final String MATRIX_TEST = "@-+  A  x";
    public static final String MATRIX_WITH_DUPLICATE_START = "@-@  A  x";

    MatrixValidator matrixValidator = new MatrixValidator();
    MatrixModel matrix;
    MatrixModel matrixWithDuplicateStart;
    PositionModel positionInMatrix;
    PositionModel positionOutOfMatrix;
    PositionModel positionModel1;
    PositionModel positionModel2;
    PositionModel startPosition;
    PositionModel cornerPosition;
    List<PositionModel> positionModels = new ArrayList<>();
    List<Direction> directions = new ArrayList<>();

    @Before
    public void init() {
        matrix = MatrixHelper.createMatrixModel(MATRIX_TEST, 3, 3);
        matrixWithDuplicateStart = MatrixHelper.createMatrixModel(MATRIX_WITH_DUPLICATE_START, 3, 3);

        positionInMatrix = new PositionModel(1, 1, "-", Direction.RIGHT);
        positionOutOfMatrix = new PositionModel(5, 5, " ", Direction.NO_DIRECTION);
        startPosition = new PositionModel(0, 0, "@", Direction.NO_DIRECTION);
        cornerPosition = new PositionModel(0, 0, "+", Direction.RIGHT);

        positionModel1 = new PositionModel(0, 0, "@", Direction.NO_DIRECTION);
        positionModel2 = new PositionModel(0, 1, "A", Direction.RIGHT);
        positionModels.add(positionModel1);
        positionModels.add(positionModel2);

        directions.add(Direction.DOWN);
        directions.add(Direction.UP);
    }

    @Test
    public void positionInMatrixTest() {
        boolean positionInMatrix = matrixValidator.isPositionInMatrix(matrix, this.positionInMatrix);
        Assert.assertEquals(positionInMatrix, Boolean.TRUE);
    }

    @Test
    public void positionIsOutOfMatrixTest() {
        boolean positionInMatrix = matrixValidator.isPositionInMatrix(matrix, positionOutOfMatrix);
        Assert.assertEquals(positionInMatrix, Boolean.FALSE);
    }

    @Test
    public void emptyPositionTest() {
        boolean isEmptyPosition = matrixValidator.isEmptyPosition(startPosition, matrix);
        Assert.assertEquals(isEmptyPosition, Boolean.FALSE);
    }

    @Test
    public void positionIsAlreadyUsedTest() {
        boolean positionUsed = matrixValidator.isPositionAlreadyUsed(startPosition, positionModels);
        Assert.assertEquals(positionUsed, Boolean.TRUE);
    }

    @Test
    public void duplicateStartIndicatorInMatrixTest() {
        boolean isDuplicateStart = matrixValidator.isDuplicateIndicatorInMatrix(matrixWithDuplicateStart, Indicators.START);
        Assert.assertEquals(isDuplicateStart, Boolean.TRUE);
    }

    @Test(expected = MultipleStartingPathsException.class)
    public void throwMultipleStartingPathsExceptionTest() throws MultipleStartingPathsException {
        matrixValidator.checkMultipleDirections(directions, startPosition);
    }

    @Test(expected = MultipleTurnsException.class)
    public void throwMultipleTurnsExceptionExceptionTest() throws MultipleTurnsException {
        matrixValidator.checkMultipleDirections(directions, cornerPosition);
    }
}
