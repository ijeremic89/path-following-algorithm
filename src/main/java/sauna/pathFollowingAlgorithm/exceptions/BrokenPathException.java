package sauna.pathFollowingAlgorithm.exceptions;

public class BrokenPathException extends RuntimeException {

    public BrokenPathException() {
        super("Path is broken! ");
    }
}
