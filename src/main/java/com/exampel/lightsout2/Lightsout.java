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
        System.out.println(piecesArr.length);
        System.out.println(piecesArr[0] + "  " + piecesArr[1] + " " + piecesArr[2]);
        for (int i = 0; i < piecesArr.length; i++) {
            String[] temp = piecesArr[i].split("Y");
            for (int j = 0; j < temp.length; j++) {

                System.out.println(Character.toString(temp[j].charAt(0)) + Character.toString(temp[j].charAt(1)) + "  " + i);

//            for (int j = 0; j < piecesArr[i].length(); j++) {
//                for (int k = 0; k < piecesArr[0].length(); k++) {
//                    Character.toString(piecesArrNew.get(j))
//
//                }
//
            }        }
            return null;
        }

}
