package sauna.pathFollowingAlgorithm.exceptions;

public class IndicatorPositionNotFoundException extends RuntimeException{

    public IndicatorPositionNotFoundException(String indicator) {
        super("Position for " + indicator + " indicator not found in matrix!");
    }
}
