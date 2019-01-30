package rosettacode;

import java.util.Arrays;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
        String level = "########     ##     ##. #  ##. $$ ##.$$  ##.#  @########";
        String[] ary = level.split("");
        final int M = 8;
        final int N = 7;
        String[][] matrix = new String[M][N];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                matrix[r][c] = ary[r*N+c];
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    test a =	new test();
    }

}
