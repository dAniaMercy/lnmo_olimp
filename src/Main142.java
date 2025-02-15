import java.io.*;
import java.util.*;

public class Main142 {
    static int[] parent, rank;

    static int find(int v) {
        if (parent[v] == v) return v;
        return parent[v] = find(parent[v]); // Сжатие пути
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
        BufferedReader br = new BufferedReader(new FileReader("inputs/INPUT142.txt"));
        PrintWriter pw = new PrintWriter(new FileWriter("outputs/OUTPUT142.txt"));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());
            edges.add(new int[]{u, v, w});
        }

        // Сортировка рёбер по весу
        edges.sort(Comparator.comparingInt(e -> e[2]));

        // Инициализация DSU
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) parent[i] = i;

        int mstWeight = 0;
        int edgesUsed = 0;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (find(u) != find(v)) { // Если вершины в разных компонентах
                union(u, v);
                mstWeight += w;
                edgesUsed++;
                if (edgesUsed == N - 1) break; // Достаточно N-1 рёбер
            }
        }

        pw.println(mstWeight);
        br.close();
        pw.close();
    }
}
