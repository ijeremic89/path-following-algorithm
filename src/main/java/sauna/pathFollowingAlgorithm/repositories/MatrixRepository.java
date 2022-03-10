package sauna.pathFollowingAlgorithm.repositories;

public class MatrixRepository {
    //    private static final String regex = "[A-Z|+\\sx@-]+";


    public String getMatrix(String matrixId) {
        String matrix = "";

        switch (matrixId) {
            case "1":
                matrix = matrix_1;
                break;
            case "2":
                matrix = matrix_2;
                break;
            default:
                //
        }
        return matrix;
    }


    String matrix_1 =
            "@---A---+\n" +
                    "        |\n" +
                    "x-B-+   C\n" +
                    "    |   |\n" +
                    "    +---+";

    String matrix_2 =
            "+-O-N-+       \n" +
                    "     |     |  \n" +
                    "     |   +-I-+\n" +
                    " @-G-O-+ | | |\n" +
                    "     | | +-+ E\n" +
                    "     +-+     S\n" +
                    "             |\n" +
                    "             x";
}
