package com.exampel.lightsout2;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lightsout {
    final static String Path = "./samples/";

    public static void main(String[] args) throws IOException {
        File file = new File(Path + "00.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int depth = Integer.valueOf(reader.readLine());
        String boardString = reader.readLine();
        String[] boardArray = boardString.split(",");//[100, 101, 011]
        Arrays.stream(boardArray).forEach(System.out::println);
        String piecesString = reader.readLine();
        String[] piecesArr = piecesString.replace(",", "Y").split(" ");//[..X|XXX|X.., X|X|X, .X|XX, XX.|.X.|.XX, XX|X., XX, .XX|XX.]
        reader.close();

        int[][] board = toMatrix(boardArray); // create board
        List<String[][]> listMatrix = toPiecesListMatrix(piecesArr); // create list of pieces

    }

    public static int[][] toMatrix(String[] inputString) {
        int matrixDepth = inputString.length;
        int[][] board = new int[inputString.length][inputString[0].length() + 1];
        for (int i = 0; i < inputString.length; i++) {
            String row = inputString[i];
            for (int j = 0; j < row.length(); j++) {
                board[i][j] = Integer.parseInt(Character.toString(row.charAt(j)));
            }
        }
        return board;
    }

    public static List<String[][]> toPiecesListMatrix(String[] piecesArr) {
        List<String[]> piecesArrNew = new ArrayList<>();
        List<String[][]> arrayList = new ArrayList<>();
        List<List<String[][]>> arrayListFinal = new ArrayList<>();
        for (int item = 0; item < piecesArr.length; item++) {
            String[] temp = piecesArr[item].split("Y");
            String[][] array = new String[temp.length][temp[0].length()];
            for (int row = 0; row < temp.length; row++) {
                for (int column = 0; column < temp[0].length(); column++) {
                    array[row][column] = Character.toString(temp[row].charAt(column));
                    arrayList.add(item, array);
                    System.out.println(arrayList.get(item)[row][column]);
                }
                arrayListFinal.add(arrayList);
            }
        }
        return null;

    }
}
