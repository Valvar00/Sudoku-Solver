package pl.valvar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Sudoku_Solver {
    private static String nameofCSV = "Sudoku.csv";
    private static int[][][] sudoku = new int[47][9][9];
    private static String[][] csv_data = new String[47][4];
    private static Node[][] our_sudoku = new Node[9][9];
    private static Node[][] correct_sudoku = new Node[9][9];

    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        ReadData();                                         //get sudokus from the file
        sudokuTransformer();                                //get solution to  sudoku if exist needed for first checks
        currentSudoku(43);                               //insert into special array sudoku with some id
        sudokuCorTransformer(1);                         //was needed to check in first versions of program
        printNode(our_sudoku);                              //prints the sudoku
        System.out.println();
        long startTime = System.nanoTime();
        if (checkNode()){
            printNode(our_sudoku);
            long endTime = System.nanoTime();
            System.out.println("Time(Milliseconds) that have been spent to solve this problem = " +(endTime - startTime)/1000000); //dividing by 1000000 to get milliseconds
        }
        else{
            System.out.println("Unsolvable");
            long endTime = System.nanoTime();
            System.out.println("Time(Milliseconds) that have been spent to solve this problem = " +(endTime - startTime)/1000000); //dividing by 1000000 to get milliseconds
        }
    }

    private static boolean checkNode(){                                                 //backtracking algorithms
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (our_sudoku[i][j].getValue() == 0) {                                 //check if sudoku has a free space in node
                    for (int num = 1; num <= 9; num++) {                                //insert numbers from 1 to 9
                        our_sudoku[i][j].setValue(num);
                        if (our_sudoku[i][j].corrCheckNode(our_sudoku)) {               //check if number applies to all constraints
                            if (checkNode()){                                           //check correctness for full sudoku
                                return true;
                            } else {
                                our_sudoku[i][j].setValue(0);                           //if the sudoku is incorrect make this node back to free
                            }
                        }else{
                            our_sudoku[i][j].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void sudokuTransformer(){                                                             //get all sudokus from file
        String[] splitted;
        for (int i = 1; i < 47; i++) {
            splitted = csv_data[i][2].split("(?!^)");
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (splitted[j*9+k].equals("."))
                        sudoku[i][j][k] = 0;
                    else
                        sudoku[i][j][k] = Integer.parseInt(splitted[j*9+k]);
                }
            }
        }
    }


    public static void sudokuCorTransformer(int id){                                    //get solution to  sudoku from file
        String[] splitted;
        splitted = csv_data[id][3].split("(?!^)");
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                if (splitted[j*9+k].equals(".")){
                    correct_sudoku[j][k] = new Node(0,j,k);;
                }
                else{
                    correct_sudoku[j][k] = new Node(Integer.parseInt(splitted[j*9+k]),j,k);
                }
            }
        }
    }

    public static void currentSudoku(int id){                                   //set sudoku with id to be our current sudoku
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                our_sudoku[j][i] = new Node(sudoku[id][j][i],j,i);
            }
        }
    }

    public static void printNode(Node[][] n){                                   //printing sudoku provided to function
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                System.out.print(n[j][i].getValue() + ",");
            }
            System.out.println();
        }
    }

    public static void ReadData() throws IOException {                                          //transform file to array
        BufferedReader br = null;
        String line = "";
        int i=0;
        String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(nameofCSV));
            while ((line = br.readLine()) != null) {
                String[] csv_data_str = line.split(cvsSplitBy);
                csv_data[i][0] = csv_data_str[0];//id
                csv_data[i][1] = csv_data_str[1];//difficulty
                csv_data[i][2] = csv_data_str[2];//sudoku
                csv_data[i][3] = csv_data_str[3];//solution
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    System.out.println("Loading Done!");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
