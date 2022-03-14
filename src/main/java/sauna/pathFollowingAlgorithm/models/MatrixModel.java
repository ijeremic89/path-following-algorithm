package sauna.pathFollowingAlgorithm.models;

public class MatrixModel {
    String[][] matrix;
    PositionModel startingPosition;

    public MatrixModel() {
    }

    public MatrixModel(String[][] matrix, PositionModel startingPosition) {
        this.matrix = matrix;
        this.startingPosition = startingPosition;
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public PositionModel getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(PositionModel startingPosition) {
        this.startingPosition = startingPosition;
    }
}
