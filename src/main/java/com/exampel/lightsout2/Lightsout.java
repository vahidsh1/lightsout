package com.exampel.lightsout2;

import java.io.*;
import java.util.*;


public class Lightsout {
    final static String Path = "./samples/";

    public static void main(String[] args) throws IOException {
        List<String[]> boardArrayList = new ArrayList<>();
        List<String[]> piecesArrList = new ArrayList<>();
        List<Integer> depth = new ArrayList<>();
        List<Integer> lenghtOfInput = new ArrayList<>();
        List<int[][]> listMatrixBoard=new ArrayList<>();
        Map<Integer,int[][]> mapOfInputs = new HashMap<>();
        HashMap<Integer,List<String[][]>> mapOfPatterns =new HashMap<>();
        for(int i = 0; i < 10 ; i++) {
            File file = new File(Path + "0" + i + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            depth.add(Integer.valueOf(reader.readLine()));
            String boardString = reader.readLine();
            boardArrayList.add(boardString.split(","));
            String piecesString = reader.readLine();
            piecesArrList.add(piecesString.replace(",", "Y").split(" "));//[..X|XXX|X.., X|X|X, .X|XX, XX.|.X.|.XX, XX|X., XX, .XX|XX.]
            reader.close();
            lenghtOfInput.add(boardArrayList.get(i).length);
            listMatrixBoard.add(toMatrix(boardArrayList.get(i)));

            mapOfInputs.put(i,toMatrix(boardArrayList.get(i)));
            mapOfPatterns.put(i,toPiecesListMatrix(piecesArrList.get(i)));

        }
        System.out.println(listMatrixBoard.stream().toArray().length);
        System.out.println(Arrays.stream(piecesArrList.get(0)).count() + " " + Arrays.stream(piecesArrList.get(5)).count());
        System.out.println(Arrays.stream(piecesArrList.get(9)).count() + " " + Arrays.stream(piecesArrList.get(8)).count());
        System.out.println(mapOfInputs.keySet().stream().toString());

        long startTime = System.currentTimeMillis() / 1000;

//        int[][] board = toMatrix(boardArray); // create board
//        List<String[][]> listMatrix = toPiecesListMatrix(piecesArr); // create list of pieces
//        System.out.println(solution(board,listMatrix,depth));
//
//
//        System.out.println("# FINISHED AT " + new Date() + "; Elapsed Time: " + (System.currentTimeMillis() / 1000 - startTime) + " Second");


    }

//    public static int[][] toMatrix(String[] inputString) {
//        int matrixDepth = inputString.length;
//        int[][] board = new int[inputString.length][inputString[0].length() + 1];
//        for (int i = 0; i < inputString.length; i++) {
//            String row = inputString[i];
//            for (int j = 0; j < row.length(); j++) {
//                board[i][j] = Integer.parseInt(Character.toString(row.charAt(j)));
//            }
//        }
//        return board;
//    }


    public static int[][] toMatrix(String[] boardArray) {
        int[][] board = new int[boardArray.length][boardArray[0].length()];
        for (int i = 0; i < boardArray.length; i++) {
            String row = boardArray[i];
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
//                    System.out.println(arrayList.get(item)[row][column]);
                }
            }
            arrayList.add(item, array);
        }
        return arrayList;

    }


    private static void placePieceOnBoard(int[][] board, String[][] piece, final int startRow, final int startCol, final int depth) {//OK
        if (startRow + piece.length > board.length || startCol + piece[0].length > board[0].length) return;
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[0].length; j++) {
                if (piece[i][j].charAt(0) == 'X') {
                    board[startRow + i][startCol + j] = ((board[startRow + i][startCol + j] + 1) % depth);
                }
            }
        }
    }

    public static String  solution(int[][] board, List<String[][]> listMatrix, int depth){
        StringBuilder result= new StringBuilder();
        backtrack(board, listMatrix, -1, depth, result);// go to solve
        return result.reverse().toString().trim();
    }

    private static boolean backtrack(int[][] board, List<String[][]> listMatrix, int indexListMatrix, int depth, StringBuilder result) {

        if (indexListMatrix >= listMatrix.size() - 1) {//If our pieces are finished. Then return false.
            return false;
        }
        indexListMatrix++;// Select the next piece
        for (int row = 0; row < board.length; row++) { //traverse the matrix
            for (int col = 0; col < board[0].length; col++) {
                if (row + listMatrix.get(indexListMatrix).length > board.length || col + listMatrix.get(indexListMatrix)[0].length > board[0].length)
                    break;//If piece violated the boarder of the board, go for the next item of board.
                int[][] bk = copyBoard(board);//for backtrack. Save the current board.
                placePieceOnBoard(board, listMatrix.get(indexListMatrix), row, col, depth);//Place a piece on the board

                Arrays.stream(board)
                        .flatMapToInt(x -> Arrays.stream(x))
                        .forEach(element -> System.out.println(element));
                System.out.println("new : ");


                if (sumBoardMatrix(board) == 0) {//if we found a solution then return true.
                    if ((indexListMatrix) == (listMatrix.size() - 1)) {//if all pieces are finished and found a solution then return true.
                        result.append(row).append(",").append(col).append(" ");
                        return true;
                    } else return false;
                }
                if (!backtrack(board, listMatrix, indexListMatrix, depth, result)) {//call the solution with the next piece.
                    board = copyBoard(bk);//Revert-> backtrack if the above condition is false
                } else {
                    result.append(row).append(",").append(col).append(" ");
                    return true;
                }
            }
        }
        return false;
    }

    private static int[][] copyBoard(int[][] board) {
        int[][] bk = new int[board.length][];
        for (int i = 0; i < board.length; i++)
            bk[i] = board[i].clone();
        return bk;
    }

    private static int sumBoardMatrix(final int[][] board) {
        int result = 0;
        for (int[] ints : board) {
            for (int anInt : ints) {
                result += anInt;
            }
        }
        return result;
    }

}


