package sauna.pathFollowingAlgorithm;

import org.junit.Assert;
import org.junit.Test;
import sauna.pathFollowingAlgorithm.exceptions.IndicatorPositionNotFoundException;
import sauna.pathFollowingAlgorithm.exceptions.MatrixHasWrongCharactersException;
import sauna.pathFollowingAlgorithm.exceptions.MatrixIsEmptyOrNullException;
import sauna.pathFollowingAlgorithm.exceptions.MultipleStartOrEndInMatrixException;
import sauna.pathFollowingAlgorithm.repositories.MatrixRepository;


public class LoadMatrixTest {

    public static final String MATRIX =
            "@         \n" +
                    "| +-C--+  \n" +
                    "A |    |  \n" +
                    "+---B--+  \n" +
                    "  |      x\n" +
                    "  |      |\n" +
                    "  +---D--+\n";

    MatrixRepository matrixRepository = new MatrixRepository();
    LoadMatrix loadMatrix = new LoadMatrix();

    @Test
    public void getMatrixByIdTest() {
        String expectedMatrix = matrixRepository.getMatrixByID(2);
        Assert.assertEquals(expectedMatrix, MATRIX);
    }

    @Test(expected = MatrixIsEmptyOrNullException.class)
    public void throwMatrixIsEmptyOrNullExceptionTest() throws MatrixIsEmptyOrNullException {
        loadMatrix.loadMatrix(55);
    }

    @Test(expected = MatrixHasWrongCharactersException.class)
    public void throwMatrixHasWrongCharactersExceptionTest() throws MatrixHasWrongCharactersException {
        loadMatrix.loadMatrix(14);
    }

    @Test(expected = MultipleStartOrEndInMatrixException.class)
    public void throwMultipleStartExceptionTest() throws MultipleStartOrEndInMatrixException {
        loadMatrix.loadMatrix(8);
    }

    @Test(expected = IndicatorPositionNotFoundException.class)
    public void throwStartPositionNotFoundExceptionTest()  throws IndicatorPositionNotFoundException {
        loadMatrix.loadMatrix(6);
    }

    @Test(expected = IndicatorPositionNotFoundException.class)
    public void throwEndPositionNotFoundExceptionTest()  throws IndicatorPositionNotFoundException {
        loadMatrix.loadMatrix(7);
    }
}
