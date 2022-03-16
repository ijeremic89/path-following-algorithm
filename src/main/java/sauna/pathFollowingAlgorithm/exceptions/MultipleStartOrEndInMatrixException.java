package sauna.pathFollowingAlgorithm.exceptions;

public class MultipleStartOrEndInMatrixException extends RuntimeException {
    public MultipleStartOrEndInMatrixException() {
        super("Multiple start or end indicator in matrix!");
    }
}
