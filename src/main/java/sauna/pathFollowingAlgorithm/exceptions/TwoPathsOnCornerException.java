package sauna.pathFollowingAlgorithm.exceptions;

public class TwoPathsOnCornerException extends RuntimeException {

    public TwoPathsOnCornerException() {
        super("There are 2 paths on corner!");
    }
}
