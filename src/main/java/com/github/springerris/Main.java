package com.github.springerris;

import org.jline.terminal.Terminal;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final int[]  powers = {4,8,16,32,64,128,256,512,1024,2048};
    private static final int WIDTH = 4;
    private static final int HEIGHT = 4;
    private static int score = 0;
    private static int moves = 0;

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
        return "    ";
    }

    public static void drawGrid(int[][] grid) {
        for (int[] row : grid) {
            String boxrow = "┌────┐┌────┐┌────┐┌────┐\n" +
              String.format("│%s││%s││%s││%s│\n",intToPaddedStr(row[0]),intToPaddedStr(row[1]),intToPaddedStr(row[2]),intToPaddedStr(row[3])) +
                            "└────┘└────┘└────┘└────┘\n";

            System.out.print(boxrow);
        }


    }

    private static boolean checkMoves(int[][] grid) {
        if (moveGrid('w',grid,false)) return true;
        if (moveGrid('a',grid,false)) return true;
        if (moveGrid('s',grid,false)) return true;
        if (moveGrid('d',grid,false)) return true;
        return false;
    }

    private static boolean checkWinCon(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 2048) return true;
            }
        }
        return false;
    }

    private static boolean moveGrid(char in, int[][] grid, boolean countScore) {
        boolean somethingHapened = false;
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
                endX = WIDTH-1;
                endY = HEIGHT;
                break;

            case 'a':
                jConst = 3;
                iConst = 0;
                shiftX = -1;
                shiftY = 1;
                multX = 1;
                multY = 0;
                endX = WIDTH-1;
                endY = HEIGHT;
                break;

            case 's':
                jConst = 0;
                iConst = 0;
                shiftX = 1;
                shiftY = 1;
                multX = 0;
                multY = 1;
                endX = WIDTH-1;
                endY = HEIGHT;
                break;

            case 'w':
                jConst = 3;
                iConst = 3;
                shiftX = -1;
                shiftY = -1;
                multX = 0;
                multY = 1;
                endX = WIDTH-1;
                endY = HEIGHT;
                break;

        }

        // leftover from previous attempt to keep score
        //int prev1 = 0;
        //int prev2 = 0;


        for (int i = 0; i < endY; i++) {
            boolean setFirstPrevs = false;
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
                // leftover from previous attempt to keep score
//                if (!setFirstPrevs) {
//                prev1 = grid[finalI][finalJ];
//                prev2 = grid[finalI+finalShiftY][finalJ+finalShiftX];
//                setFirstPrevs = true;
//                }
                if (IntStream.of(powers).anyMatch(x -> x == grid[finalI][finalJ]
                        + grid[finalI + finalShiftY][finalJ + finalShiftX])) {
                    //if (Arrays.asList(powers).contains((grid[finalI][finalJ] + grid[finalI][j+1]))) {
                    int prev1 = grid[finalI][finalJ];

                    grid[finalI+finalShiftY][finalJ+finalShiftX] = grid[finalI+finalShiftY][finalJ+finalShiftX] + grid[finalI][finalJ];
                    if (countScore && prev1 !=0 )
                        score = score + grid[finalI+finalShiftY][finalJ+finalShiftX];
                    grid[finalI][finalJ] = 0;

                    somethingHapened = true;
                }
                else {
                    if ( grid[finalI+finalShiftY][finalJ+finalShiftX] == 0) {
                        grid[finalI+finalShiftY][finalJ+finalShiftX] = grid[finalI][finalJ];
                        grid[finalI][finalJ] = 0;
                        somethingHapened = true;
                    }
                }
                // leftover from previous attempt to keep score
//                prev1 = grid[finalI][finalJ];
//                prev2 = grid[finalI+finalShiftY][finalJ+finalShiftX];
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
                    somethingHapened = true;
                }

            }

        }
        return somethingHapened;
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
        String OSNameLower = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        System.out.println(OSNameLower);
        Random rd = new Random();
        boolean itsOver = false;
        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[WIDTH][HEIGHT];
        List<IndexPair> empty = new ArrayList<>();

        System.out.println("Hello and welcome!");
        char in = ' ';
        while (in != 'q') {


            System.out.println(new char[] { (char) 0x1B, '[', '2', 'J' });
            System.out.println(new char[] { (char) 0x1B, '[', '9', '9','9', 'C' });
            System.out.println(new char[] { (char) 0x1B, '[', '9', '9','9', 'A' });
            countEmpty(grid,empty);
            //System.out.println(empty);
            Collections.shuffle(empty,rd);
            //System.out.println(empty);
            if (!empty.isEmpty()) {
                grid[empty.get(0).y()][empty.get(0).x()] = 2;
            } else {
                int [][] testGrid =  new int[WIDTH][HEIGHT];
                for (int i = 0; i < WIDTH; i++) {
                    System.arraycopy(grid[i],0,testGrid[i],0,grid.length);
                }
                System.out.println(Arrays.deepToString(testGrid));
                if (!checkMoves(testGrid)) {
                    itsOver = true;
                };
            }

            if (moveGrid(in, grid,true)) moves++;

            // for testing win condition
            //grid[0][0] = 1024;
            //grid[0][1] = 1024;

            // for testing lose condition
//            for (int i = 0; i < HEIGHT; i++) {
//                for (int j = 0; j < WIDTH; j++) {
//                    grid[i][j]= 12;
//
//                }
//            }

            System.out.println("Score: " + score);
            System.out.println("Moves: " + moves);
            drawGrid(grid);
            if (checkWinCon(grid)) {
                System.out.println("Congratulations! You won in " + moves + " moves!");
                break;
            }
            if (itsOver) {
                System.out.println("No more moves, you've lost!");
                break;
            }

            in = sc.next().charAt(0);

        }
    }


}