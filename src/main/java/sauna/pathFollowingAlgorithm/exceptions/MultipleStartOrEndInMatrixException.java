package sauna.pathFollowingAlgorithm.exceptions;

public class MultipleStartOrEndInMatrixException extends RuntimeException {
    public MultipleStartOrEndInMatrixException(String indicator) {
        super("Multiple " + indicator + " indicator in matrix!");
    }
}
