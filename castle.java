import java.io.*;
import java.util.*;

public class castle {
    static int[][][] floorplan = new int[51][51][4];

    static boolean[][] isVisited = new boolean[51][51];
    static int num;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("castle.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
        StringTokenizer line = new StringTokenizer(br.readLine());
        int width = Integer.parseInt(line.nextToken());
        int height = Integer.parseInt(line.nextToken());
        for (int i = 0; i < height; i++) {
            StringTokenizer l = new StringTokenizer(br.readLine());
            for (int j = 0; j < width; j++) {
                int temp = Integer.parseInt(l.nextToken());
                if (temp >= 8) {
                    temp -= 8;
                    floorplan[i][j][3] = 1;
                }
                if (temp >= 4) {
                    temp -= 4;
                    floorplan[i][j][2] = 1;
                }
                if (temp >= 2) {
                    temp -= 2;
                    floorplan[i][j][1] = 1;
                }
                if (temp >= 1) {
                    temp -= 1;
                    floorplan[i][j][0] = 1;
                }

            }
        }
        int roomNum = 0;
        int largest = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!isVisited[i][j]) {
                    roomNum++;
                    num = 0;
                    int n = dfs(floorplan, i, j, height, width);
                    if (n > largest) {
                        largest = n;
                    }
                }
            }
        }
        out.println(roomNum);
        out.println(largest);
        ArrayList<int[]> largestSizePos = new ArrayList<>();
        int[] optimal = new int[3];
        for (int i = 0; i < height; i++) {
            for (int j = width - 1; j >= 0; j--) {
                for (int k = 2; k > 0; k--) {
                    if (i == 0 && k == 1 || j == width - 1 && k == 2) {
                        continue;
                    } else if (floorplan[i][j][k] == 1) {
                        int testLargest = testing(i, j, k, height, width);
                        int[] tempReocrd = {i, j, k};
                        if (testLargest > largest) {
                            largest = testLargest;
                            largestSizePos.clear();
                            largestSizePos.add(tempReocrd);
                        } else if (testLargest == largest) {
                            if (i == largestSizePos.get(largestSizePos.size() - 1)[0] && j == largestSizePos.get(largestSizePos.size() - 1)[1]) {
                                if (k == 1 && largestSizePos.get(largestSizePos.size() - 1)[2] == 2) {
                                    largestSizePos.set(largestSizePos.size() - 1, tempReocrd);
                                }
                            }
                            else {
                                largestSizePos.add(tempReocrd);
                            }
                        }


                    }
                }
            }
        }
        out.println(largest);

        int p = 51;
        int[] result = new int[3];
        for (int i = 0; i < largestSizePos.size(); i++) {
            if (largestSizePos.get(i)[1] <= p) {
                p = largestSizePos.get(i)[1];
                result = largestSizePos.get(i);
            }
        }
        String direction = null;
        int dir = result[2];
        if (dir == 1) {
            direction = "N";
        } else if (dir == 2) {
            direction = "E";
        }
        int x = result[0]+1;
        int y = result[1]+1;
        out.println(x + " " + y + " " + direction);
        out.close();

    }


    public static int testing(int i, int j, int k, int height, int width) {
        int[][][] testing = floorplan.clone();
        isVisited = new boolean[51][51];
        num = 0;
        testing[i][j][k] = 0;
        int largestSize = dfs(testing, i, j, height, width);
        testing[i][j][k] = 1;

        return largestSize;
    }

    public static int dfs(int[][][] floorplan, int i, int j, int height, int width) {
        if (i >= 0 && i < height && j >= 0 && j < width && !isVisited[i][j]) {
            num++;
            isVisited[i][j] = true;
            for (int k = 0; k < 4; k++) {
                if (floorplan[i][j][k] == 0) {
                    if (k == 0 && j - 1 >= 0 && !isVisited[i][j - 1]) {
                        dfs(floorplan, i, j - 1, height, width);
                    } else if (k == 1 && i - 1 >= 0 && !isVisited[i - 1][j]) {
                        dfs(floorplan, i - 1, j, height, width);
                    } else if (k == 2 && j + 1 < width && !isVisited[i][j + 1]) {
                        dfs(floorplan, i, j + 1, height, width);
                    } else if (k == 3 && i + 1 < width && !isVisited[i + 1][j]) {
                        dfs(floorplan, i + 1, j, height, width);
                    }
                }
            }
        }
        return num;
    }

}
