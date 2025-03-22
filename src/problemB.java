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

public class problemB {
    public static int countEvictions(int n, int m, int[][] edges) {
        List<Set<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjList.add(new HashSet<>());
        }

        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }

        int groups = 0;
        boolean found;

        do {
            found = false;
            List<Integer> toRemove = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                if (adjList.get(i).size() == 1) {
                    toRemove.add(i);
                    found = true;
                }
            }

            if (!toRemove.isEmpty()) {
                groups++;
                for (int node : toRemove) {
                    for (int neighbor : adjList.get(node)) {
                        adjList.get(neighbor).remove(node);
                    }
                    adjList.get(node).clear();
                }
            }
        } while (found);

        return groups;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] edges = new int[m][2];

        for (int i = 0; i < m; i++) {
            edges[i][0] = scanner.nextInt();
            edges[i][1] = scanner.nextInt();
        }

        System.out.println(countEvictions(n, m, edges));
        scanner.close();
    }
}
