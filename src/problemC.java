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

public class problemC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder result = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            int[][] obstacles = new int[n][2];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                obstacles[i][0] = Integer.parseInt(st.nextToken());
                obstacles[i][1] = Integer.parseInt(st.nextToken());
            }

            Map<Integer, List<Integer>> boostMap = new HashMap<>();
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int pos = Integer.parseInt(st.nextToken());
                int val = Integer.parseInt(st.nextToken());
                if (!boostMap.containsKey(pos)) {
                    boostMap.put(pos, new ArrayList<>());
                }
                boostMap.get(pos).add(val);
            }

            int answer = solveTest(n, m, L, obstacles, boostMap);
            result.append(answer).append("\n");
        }

        System.out.print(result);
    }

    private static int solveTest(int n, int m, int L, int[][] obstacles, Map<Integer, List<Integer>> boostMap) {
        boolean[] isObstacle = new boolean[L + 1];
        for (int[] obstacle : obstacles) {
            for (int pos = obstacle[0]; pos <= Math.min(obstacle[1], L); pos++) {
                isObstacle[pos] = true;
            }
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        pq.add(new int[]{1, 1, 0});

        if (boostMap.containsKey(1)) {
            processBoosts(1, 1, 0, boostMap.get(1), pq);
        }

        Map<String, Integer> visited = new HashMap<>();
        visited.put("1,1", 0);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int pos = current[0];
            int jumpPower = current[1];
            int boostsUsed = current[2];

            if (pos == L) {
                return boostsUsed;
            }

            String stateKey = pos + "," + jumpPower;

            if (visited.containsKey(stateKey) && visited.get(stateKey) < boostsUsed) {
                continue;
            }

            for (int newPos = pos + 1; newPos <= Math.min(pos + jumpPower, L); newPos++) {
                // Пропускаем, если это препятствие
                if (newPos < isObstacle.length && isObstacle[newPos]) {
                    continue;
                }

                String newStateKey = newPos + "," + jumpPower;
                if (!visited.containsKey(newStateKey) || visited.get(newStateKey) > boostsUsed) {
                    visited.put(newStateKey, boostsUsed);
                    pq.add(new int[]{newPos, jumpPower, boostsUsed});

                    if (boostMap.containsKey(newPos)) {
                        processBoosts(newPos, jumpPower, boostsUsed, boostMap.get(newPos), pq);
                    }
                }
            }
        }

        return -1;
    }

    private static void processBoosts(int pos, int jumpPower, int boostsUsed, List<Integer> boosts, PriorityQueue<int[]> pq) {
        for (int mask = 1; mask < (1 << boosts.size()); mask++) {
            int additionalPower = 0;
            int additionalBoosts = 0;

            for (int i = 0; i < boosts.size(); i++) {
                if ((mask & (1 << i)) != 0) {
                    additionalPower += boosts.get(i);
                    additionalBoosts++;
                }
            }

            int newJumpPower = jumpPower + additionalPower;
            int newBoostsUsed = boostsUsed + additionalBoosts;

            pq.add(new int[]{pos, newJumpPower, newBoostsUsed});
        }
    }
}