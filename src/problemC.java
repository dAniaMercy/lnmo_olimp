import java.io.*;
import java.util.*;

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

            int answer = solveTest(n, L, obstacles, boostMap);
            result.append(answer).append("\n");
        }

        System.out.print(result);
    }

    private static int solveTest(int n, int L, int[][] obstacles, Map<Integer, List<Integer>> boostMap) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        pq.add(new int[]{1, 1, 0});

        Map<Long, Integer> visited = new HashMap<>();
        visited.put(encodeState(1, 1), 0);

        if (boostMap.containsKey(1)) {
            List<Integer> boosts = boostMap.get(1);
            for (int mask = 1; mask < (1 << boosts.size()); mask++) {
                int additionalPower = 0;
                int additionalBoosts = 0;

                for (int i = 0; i < boosts.size(); i++) {
                    if ((mask & (1 << i)) != 0) {
                        additionalPower += boosts.get(i);
                        additionalBoosts++;
                    }
                }

                int newJumpPower = 1 + additionalPower;
                int newBoostsUsed = additionalBoosts;

                long stateKey = encodeState(1, newJumpPower);
                if (!visited.containsKey(stateKey) || visited.get(stateKey) > newBoostsUsed) {
                    visited.put(stateKey, newBoostsUsed);
                    pq.add(new int[]{1, newJumpPower, newBoostsUsed});
                }
            }
        }

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int pos = current[0];
            int jumpPower = current[1];
            int boostsUsed = current[2];

            if (pos == L) {
                return boostsUsed;
            }

            long stateKey = encodeState(pos, jumpPower);

            if (visited.get(stateKey) < boostsUsed) {
                continue;
            }

            for (int newPos = pos + 1; newPos <= Math.min(pos + jumpPower, L); newPos++) {
                if (isObstacle(newPos, obstacles)) {
                    continue;
                }

                long newStateKey = encodeState(newPos, jumpPower);
                if (!visited.containsKey(newStateKey) || visited.get(newStateKey) > boostsUsed) {
                    visited.put(newStateKey, boostsUsed);
                    pq.add(new int[]{newPos, jumpPower, boostsUsed});
                }

                if (boostMap.containsKey(newPos)) {
                    List<Integer> boosts = boostMap.get(newPos);
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

                        long boostStateKey = encodeState(newPos, newJumpPower);
                        if (!visited.containsKey(boostStateKey) || visited.get(boostStateKey) > newBoostsUsed) {
                            visited.put(boostStateKey, newBoostsUsed);
                            pq.add(new int[]{newPos, newJumpPower, newBoostsUsed});
                        }
                    }
                }
            }
        }

        return -1;
    }

    private static long encodeState(int pos, int jumpPower) {
        return (long)pos * 10000L + jumpPower;
    }

    private static boolean isObstacle(int pos, int[][] obstacles) {
        for (int[] obstacle : obstacles) {
            if (pos >= obstacle[0] && pos <= obstacle[1]) {
                return true;
            }
        }
        return false;
    }
}