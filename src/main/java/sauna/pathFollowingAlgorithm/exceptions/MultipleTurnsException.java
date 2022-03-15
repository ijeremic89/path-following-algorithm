package sauna.pathFollowingAlgorithm.exceptions;

public class MultipleTurnsException extends RuntimeException {
    public MultipleTurnsException() {
        super("Multiple turns!");
    }
}
