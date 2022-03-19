package sauna.pathFollowingAlgorithm.repositories;

import sauna.pathFollowingAlgorithm.constants.Matrixes;

public class MatrixRepository {
    public String getMatrixByID(int matrixId) {
        String matrix = "";

        switch (matrixId) {
            case 1:
                matrix = Matrixes.MATRIX_1;
                break;
            case 2:
                matrix = Matrixes.MATRIX_2;
                break;
            case 3:
                matrix = Matrixes.MATRIX_3;
                break;
            case 4:
                matrix = Matrixes.MATRIX_4;
                break;
            case 5:
                matrix = Matrixes.MATRIX_5;
                break;
            case 6:
                matrix = Matrixes.MATRIX_6;
                break;
            case 7:
                matrix = Matrixes.MATRIX_7;
                break;
            case 8:
                matrix = Matrixes.MATRIX_8;
                break;
            case 9:
                matrix = Matrixes.MATRIX_9;
                break;
            case 10:
                matrix = Matrixes.MATRIX_10;
                break;
            case 11:
                matrix = Matrixes.MATRIX_11;
                break;
            case 12:
                matrix = Matrixes.MATRIX_12;
                break;
            case 13:
                matrix = Matrixes.MATRIX_13;
                break;
            case 14:
                matrix = Matrixes.MATRIX_14;
                break;
            default:
                //
        }
        return matrix;
    }



}
