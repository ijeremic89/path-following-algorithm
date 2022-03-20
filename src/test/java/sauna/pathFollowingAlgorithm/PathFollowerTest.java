package sauna.pathFollowingAlgorithm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sauna.pathFollowingAlgorithm.exceptions.BrokenPathException;
import sauna.pathFollowingAlgorithm.exceptions.FakeTurnException;
import sauna.pathFollowingAlgorithm.helpers.matrix.MatrixHelper;
import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PathResultModel;

public class PathFollowerTest {

    public static final String MATRIX_WITH_BROKEN_PATH = "@-+     x";
    public static final String MATRIX_WITH_FAKE_TURN = "@-+-Ax";
    public static final String MATRIX_STRAIGHT_THROUGH_INTERSECTIONS = "@ +B+| | |+-A-+  |    x  ";
    public static final String MATRIX_WITH_LETTERS_AS_CORNERS = "@ x| |A-B";
    public static final String MATRIX_WITH_COMPACT_SPACE = "@  ++| xC+A  | +-B-+   ++";

    PathFollower pathFollower = new PathFollower();

    MatrixModel matrixWithBrokenPath;
    MatrixModel matrixWithFakeTurn;
    MatrixModel matrixWithIntersections;
    MatrixModel matrixWithLettersAsCorners;
    MatrixModel matrixWithCompactSpace;

    @Before
    public void init() {
        matrixWithBrokenPath = MatrixHelper.createMatrixModel(MATRIX_WITH_BROKEN_PATH, 3, 3);
        matrixWithFakeTurn = MatrixHelper.createMatrixModel(MATRIX_WITH_FAKE_TURN, 1, 6);
        matrixWithIntersections = MatrixHelper.createMatrixModel(MATRIX_STRAIGHT_THROUGH_INTERSECTIONS, 5, 5);
        matrixWithLettersAsCorners = MatrixHelper.createMatrixModel(MATRIX_WITH_LETTERS_AS_CORNERS, 3, 3);
        matrixWithCompactSpace = MatrixHelper.createMatrixModel(MATRIX_WITH_COMPACT_SPACE, 5, 5);
    }

    @Test(expected = BrokenPathException.class)
    public void throwBrokenPathExceptionTest() throws BrokenPathException {
        pathFollower.followPath(matrixWithBrokenPath);
    }

    @Test(expected = FakeTurnException.class)
    public void throwFakeTurnExceptionTest() throws FakeTurnException {
        pathFollower.followPath(matrixWithFakeTurn);
    }

    @Test
    public void pathStraightThroughIntersectionsTest() {
        String expectedLetters = "AB";
        String expectedPath = "@|+-A-+|+B+||x";
        PathResultModel pathResult = pathFollower.followPath(matrixWithIntersections);

        Assert.assertEquals(pathResult.getPath(), expectedPath);
        Assert.assertEquals(pathResult.getLetters(), expectedLetters);
    }

    @Test
    public void lettersAsCornersTest() {
        String expectedLetters = "AB";
        String expectedPath = "@|A-B|x";
        PathResultModel pathResult = pathFollower.followPath(matrixWithLettersAsCorners);

        Assert.assertEquals(pathResult.getPath(), expectedPath);
        Assert.assertEquals(pathResult.getLetters(), expectedLetters);
    }

    @Test
    public void keepDirectionInCompactSpaceTest() {
        String expectedLetters = "ABC";
        String expectedPath = "@|A+-B-+++|C+++x";
        PathResultModel pathResult = pathFollower.followPath(matrixWithCompactSpace);

        Assert.assertEquals(pathResult.getPath(), expectedPath);
        Assert.assertEquals(pathResult.getLetters(), expectedLetters);
    }
}
