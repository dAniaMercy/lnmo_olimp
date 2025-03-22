import java.util.*;

public class problemC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();

        while (t-- > 0) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int L = scanner.nextInt();

            List<int[]> obstacles = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int l = scanner.nextInt();
                int r = scanner.nextInt();
                obstacles.add(new int[]{l, r});
            }

            List<int[]> powerUps = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int x = scanner.nextInt();
                int v = scanner.nextInt();
                powerUps.add(new int[]{x, v});
            }

            System.out.println(minPowerUps(n, m, L, obstacles, powerUps));
        }

        scanner.close();
    }

    private static int minPowerUps(int n, int m, int L, List<int[]> obstacles, List<int[]> powerUps) {
        int pos = 1, jump = 1, powerUpCount = 0, index = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (int[] obstacle : obstacles) {
            int l = obstacle[0], r = obstacle[1];

            while (pos < l) {
                while (index < m && powerUps.get(index)[0] <= pos) {
                    pq.offer(powerUps.get(index)[1]);
                    index++;
                }

                if (pos + jump >= l) break;

                if (pq.isEmpty()) return -1;

                jump += pq.poll();
                powerUpCount++;
            }

            pos = r + 1;
        }

        while (pos < L) {
            while (index < m && powerUps.get(index)[0] <= pos) {
                pq.offer(powerUps.get(index)[1]);
                index++;
            }

            if (pos + jump >= L) return powerUpCount;

            if (pq.isEmpty()) return -1;

            jump += pq.poll();
            powerUpCount++;
        }

        return powerUpCount;
    }
}
