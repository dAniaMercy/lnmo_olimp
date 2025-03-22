import java.io.*;
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

public class Main1385 {
    static int[] parent, rank;

    static int find(int v) {
        if (parent[v] == v) return v;
        return parent[v] = find(parent[v]);
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                parent[a] = b;
            } else if (rank[a] > rank[b]) {
                parent[b] = a;
            } else {
                parent[b] = a;
                rank[a]++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("inputs/INPUT1385.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("outputs/OUTPUT1385.txt"));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            edges.add(new int[]{u, v});
        }

        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) parent[i] = i;

        List<int[]> mst = new ArrayList<>();
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (find(u) != find(v)) {
                union(u, v);
                mst.add(new int[]{u + 1, v + 1});
                if (mst.size() == N - 1) break;
            }
        }

        for (int[] edge : mst) {
            pw.println(edge[0] + " " + edge[1]);
        }

        br.close();
        pw.close();
    }
}