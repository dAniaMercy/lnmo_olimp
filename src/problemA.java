import java.util.*;

/**
 *       LL             OOOOOOOO      RRRRRRRRR       EEEEEEEEEE     NNNN     NN
 *       LL            OO      OO     RR      RR      EE             NN NN    NN
 *       LL           OO        OO    RR       RR     EE             NN  NN   NN
 *       LL          OO          OO   RR      RR      EEEEEEE        NN   NN  NN
 *       LL           OO        OO    RRRRRRR         EEEEEEE        NN    NN NN
 *       LL            OO      OO     RR    RR        EE             NN     NNNN
 *       LLLLLLLLL      OOOOOOOO      RR     RR       EEEEEEEEEE     NN      NNN
 */

public class problemA {
    public static List<Integer> generateResults(int n) {
        int[][] results = new int[n][n];
        List<Integer> output = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((j - i) * 2 == n) {
                    results[i][j] = results[j][i] = 0;
                } else if ((j - i) * 2 < n) {
                    results[i][j] = 1;
                    results[j][i] = -1;
                } else {
                    results[i][j] = -1;
                    results[j][i] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                output.add(results[i][j]);
            }
        }

        return output;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            List<Integer> results = generateResults(n);
            for (int res : results) {
                System.out.print(res + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
