package sauna.pathFollowingAlgorithm;

import sauna.pathFollowingAlgorithm.models.MatrixModel;
import sauna.pathFollowingAlgorithm.models.PathResultModel;

import java.util.Scanner;

public class PathFollowingAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter matrix ID: ");
        int matrixId = sc.nextInt();

        LoadMatrix loadMatrix = new LoadMatrix();
        MatrixModel matrix = loadMatrix.loadMatrix(matrixId);

        FollowPath followPath = new FollowPath();
        PathResultModel pathResult = followPath.followPath(matrix);

        System.out.print("Path: " + pathResult.getPath());
        System.out.println(System.lineSeparator());
        System.out.print("Letters: " + pathResult.getLetters());
    }
}
