package sauna.pathFollowingAlgorithm.exceptions;

public class MultipleStartingPathsException extends RuntimeException {

    public MultipleStartingPathsException() {
        super("Multiple starting paths! ");
    }
}
