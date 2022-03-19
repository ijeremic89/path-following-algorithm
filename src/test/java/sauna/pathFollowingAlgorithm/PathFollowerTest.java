package sauna.pathFollowingAlgorithm;

import org.junit.Before;
import sauna.pathFollowingAlgorithm.models.MatrixModel;

public class PathFollowerTest {

    MatrixLoader matrixLoader = new MatrixLoader();
    PathFollower pathFollower = new PathFollower();
    MatrixModel matrix_2 = new MatrixModel();

    @Before
    public void init() {
        matrix_2 = matrixLoader.createMatrix(2);
    }
}
