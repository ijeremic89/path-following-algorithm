package sauna.pathFollowingAlgorithm.repositories;

public class MatrixRepository {
    public String getMatrixByID(int matrixId) {
        String matrix = "";

        switch (matrixId) {
            case 1:
                matrix = matrix_1;
                break;
            case 2:
                matrix = matrix_2;
                break;
            case 3:
                matrix = matrix_3;
                break;
            case 4:
                matrix = matrix_4;
                break;
            case 5:
                matrix = matrix_5;
                break;
            case 6:
                matrix = matrix_6;
                break;
            case 7:
                matrix = matrix_7;
                break;
            case 8:
                matrix = matrix_8;
                break;
            case 9:
                matrix = matrix_9;
                break;
            case 10:
                matrix = matrix_10;
                break;
            case 11:
                matrix = matrix_11;
                break;
            case 12:
                matrix = matrix_12;
                break;
            case 13:
                matrix = matrix_13;
                break;
            case 14:
                matrix = matrix_14;
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
            "@         \n" +
                    "| +-C--+  \n" +
                    "A |    |  \n" +
                    "+---B--+  \n" +
                    "  |      x\n" +
                    "  |      |\n" +
                    "  +---D--+\n";

    String matrix_3 =
            "@---A---+\n" +
                    "        |\n" +
                    "x-B-+   |\n" +
                    "    |   |\n" +
                    "    +---C";

    String matrix_4 =
            "     +-O-N-+  \n" +
                    "     |     |  \n" +
                    "     |   +-I-+\n" +
                    " @-G-O-+ | | |\n" +
                    "     | | +-+ E\n" +
                    "     +-+     S\n" +
                    "             |\n" +
                    "             x";

    String matrix_5 =
            " +-L-+  \n" +
                    " |  +A-+\n" +
                    "@B+ ++ H\n" +
                    " ++    x";

    String matrix_6 =
            "     -A---+\n" +
                    "          |\n" +
                    "  x-B-+   C\n" +
                    "      |   |\n" +
                    "      +---+";

    String matrix_7 =
            "@--A---+\n" +
                    "       |\n" +
                    " B-+   C\n" +
                    "   |   |\n" +
                    "   +---+";

    String matrix_8 =
            " @--A-@-+\n" +
                    "        |\n" +
                    "x-B-+   C\n" +
                    "    |   |\n" +
                    "    +---+";

    String matrix_9 =
            " @--A---+\n" +
                    "        |\n" +
                    "x-Bx+   C\n" +
                    "    |   |\n" +
                    "    +---+";

    String matrix_10 =
            "     x-B\n" +
                    "       |\n" +
                    "@--A---+\n" +
                    "       |\n" +
                    "  x+   C\n" +
                    "   |   |\n" +
                    "   +---+";

    String matrix_11 =
            "@--A-+\n" +
                    "     |\n" +
                    "      \n" +
                    "   B-x";

    String matrix_12 =
            "-B-@-A-x";

    String matrix_13 =
            "@-A-+-B-x";

    String matrix_14 =
            "@         \n" +
                    "| +-C--+  \n" +
                    "a x    |  \n" +
                    "+---B--+  \n";
}
