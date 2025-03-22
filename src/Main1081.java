import java.io.*;
import java.util.*;

public class Main1081 {
    static int[] tree;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("inputs/INPUT1081.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("outputs/OUTPUT1081.txt"));

        N = Integer.parseInt(br.readLine());
        int[] A = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        tree = new int[4 * N];
        buildTree(A, 0, 0, N - 1);

        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken()) - 1;
            int R = Integer.parseInt(st.nextToken()) - 1;
            sb.append(query(0, 0, N - 1, L, R)).append(" ");
        }

        pw.println(sb.toString().trim());
        br.close();
        pw.close();
    }

    static void buildTree(int[] A, int node, int start, int end) {
        if (start == end) {
            tree[node] = A[start];
        } else {
            int mid = (start + end) / 2;
            buildTree(A, 2 * node + 1, start, mid);
            buildTree(A, 2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    static int query(int node, int start, int end, int L, int R) {
        if (L > end || R < start) return 0;
        if (L <= start && end <= R) return tree[node];
        int mid = (start + end) / 2;
        return query(2 * node + 1, start, mid, L, R) + query(2 * node + 2, mid + 1, end, L, R);
    }
}
