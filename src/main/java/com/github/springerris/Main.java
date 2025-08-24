package com.github.springerris;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final int[]  powers = {0,2,4,8,16,32,64,128,256,512,1024,2048};
    private static int WIDTH = 4;
    private static int HEIGHT = 4;

    public static String intToPaddedStr(int i) {
        if (i != 0) {
            String str = String.valueOf(i);

            switch (str.length()) {
                case 1:
                    return "  " + str + " ";
                case 2:
                    return " " + str + " ";
                case 3:
                    return " " + str;
                case 4:
                    return str;
            }
        }
        return "  - ";
    }

    public static void drawGrid(int[][] grid) {
        for (int[] row : grid) {
            String boxrow = "┌────┐┌────┐┌────┐┌────┐\n" +
              String.format("│%s││%s││%s││%s│\n",intToPaddedStr(row[0]),intToPaddedStr(row[1]),intToPaddedStr(row[2]),intToPaddedStr(row[3])) +
                            "└────┘└────┘└────┘└────┘\n";

            System.out.print(boxrow);
        }


    }

    public static void countEmpty(int[][] grid, List<IndexPair> emptyList) {
        emptyList.clear();
        int testSum = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (grid[i][j]==0) {
                    emptyList.add(new IndexPair(j,i));
                    testSum++;
                }
            }
        }
        System.out.println(testSum);
    }

    public static void main(String[] args) throws IOException {
        Random rd = new Random();

        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[WIDTH][HEIGHT];
        List<IndexPair> empty = new ArrayList<>();

        System.out.println("Hello and welcome!");
        char in = ' ';
        while (in != 'q') {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            countEmpty(grid,empty);
            //System.out.println(empty);
            Collections.shuffle(empty);
            //System.out.println(empty);
            if (!empty.isEmpty()) {
                grid[empty.get(0).y()][empty.get(0).x()] = 2;
            }

            int shiftX = 0;
            int shiftY = 0;
            int jConst = 0;
            int iConst = 0;
            int multX = 0;
            int multY = 0;
            int endX = 0;
            int endY = 0;

            switch (in) {

                case 'd':
                    jConst = 0;
                    iConst = 0;
                    shiftX = 1;
                    shiftY = 1;
                    multX = 1;
                    multY = 0;
                    endX = 3;
                    endY = 4;
                    break;

                case 'a':
                    jConst = 3;
                    iConst = 0;
                    shiftX = -1;
                    shiftY = 1;
                    multX = 1;
                    multY = 0;
                    endX = 3;
                    endY = 4;
                    break;

                case 's':
                    jConst = 0;
                    iConst = 0;
                    shiftX = 1;
                    shiftY = 1;
                    multX = 0;
                    multY = 1;
                    endX = 3;
                    endY = 4;
                    break;

                case 'w':
                    jConst = 3;
                    iConst = 3;
                    shiftX = -1;
                    shiftY = -1;
                    multX = 0;
                    multY = 1;
                    endX = 3;
                    endY = 4;
                    break;

            }

            for (int i = 0; i < endY; i++) {
                for (int j = 0; j < endX; j++) {
                    //System.out.println();
                    //System.out.println((iConst+i*shiftY)*multX + " + " + (jConst+j*shiftX)*multY);
                    //System.out.println((iConst+i*shiftY)*multY + " + " + (jConst+j*shiftX)*multX);
                    int finalI = (iConst+i*shiftY)*multX + (jConst+j*shiftX)*multY;
                    int finalJ = (iConst+i*shiftY)*multY + (jConst+j*shiftX)*multX;
                    //System.out.println(finalI);
                    //System.out.println(finalJ);
                    int finalShiftX = shiftX*multX;
                    int finalShiftY = shiftY*multY;
                    if (IntStream.of(powers).anyMatch(x -> x == grid[finalI][finalJ]
                            + grid[finalI + finalShiftY][finalJ + finalShiftX])) {
                        //if (Arrays.asList(powers).contains((grid[finalI][finalJ] + grid[finalI][j+1]))) {
                        grid[finalI+finalShiftY][finalJ+finalShiftX] = grid[finalI+finalShiftY][finalJ+finalShiftX] + grid[finalI][finalJ];
                        grid[finalI][finalJ] = 0;
                    }
                    else {
                        if ( grid[finalI+finalShiftY][finalJ+finalShiftX] == 0) {
                            grid[finalI+finalShiftY][finalJ+finalShiftX] = grid[finalI][finalJ];
                            grid[finalI][finalJ] = 0;
                        }
                    }
                }

            }

            for (int i = 0; i < endY; i++) {
                for (int j = 0; j < endX; j++) {
                    //System.out.println();
                    //System.out.println((iConst+i*shiftY)*multX + " + " + (jConst+j*shiftX)*multY);
                    //System.out.println((iConst+i*shiftY)*multY + " + " + (jConst+j*shiftX)*multX);
                    int finalI = (iConst+i*shiftY)*multX + (jConst+j*shiftX)*multY;
                    int finalJ = (iConst+i*shiftY)*multY + (jConst+j*shiftX)*multX;
                    //System.out.println(finalI);
                    //System.out.println(finalJ);
                    int finalShiftX = shiftX*multX;
                    int finalShiftY = shiftY*multY;
                        if ( grid[finalI+finalShiftY][finalJ+finalShiftX] == 0) {
                            grid[finalI+finalShiftY][finalJ+finalShiftX] = grid[finalI][finalJ];
                            grid[finalI][finalJ] = 0;
                        }

                }

            }




            drawGrid(grid);
            in = sc.next().charAt(0);







        }
    }
}