/**
 * Created by Febrian on 3/17/2017.
 */
import java.util.Scanner;
import java.util.Random;

public class Minesweeper {

    public static int bombPlanted = 0;
    public static void main(String[] args){
        System.out.println("Selamat Datang di Permainan Minesweeper Sederhana");
        System.out.println("Dibuat oleh : Febrian Nur Ramadhan");
        System.out.print("Masukkan Ukuran Field : ");
        Scanner input = new Scanner(System.in);
        int size = Integer.parseInt(input.nextLine());
        boolean isSafe = true;
        // create a minesweeper board
        String[][] mineBoard = drawBoard(size);
        // print minesweeper board
        printBoard(mineBoard);
        System.out.println("Jumlah Ranjau : " + countBomb(mineBoard));

        // variable to count the field that has been choosen
        int fieldChoosen = 0;
        while (isSafe){
            System.out.print("Pilih posisi : ");
            String position = input.nextLine();
            // jika posisi telah diinput
            if(!position.isEmpty()){
                int a = Integer.parseInt(position.substring(0,1)) - 1;
                int b = Integer.parseInt(position.substring(1,2)) - 1;
                boolean isLose = isBomb(a,b,mineBoard);
                /* jika tebakan benar dan semua field yang aman
                    telah ditebak, maka pemain menang
                 */
                if(!isLose && (fieldChoosen == (mineBoard.length - bombPlanted))){
                    revealField(mineBoard,position,true);
                    System.out.println("Selamat Anda Memenangkan Permainan Ini!");
                }
                /* jika tebakan benar dan belum semua field yang aman
                    ditebak, maka field yang dipilih dimunculkan nilai aslinya
                */
                else if(!isLose){
                    revealField(mineBoard,position,false);
                }
                // else, pemain kalah dan semua field dimunculkan nilai aslinya
                else{
                    revealField(mineBoard,position,true);
                    System.out.print("Anda Gagal!");
                    isSafe = false;
                }
                fieldChoosen++;
            }
        }
    }

    /**
     * method ini untuk membuat board minesweeper
     * @param size ukuran papan minesweeper yang diinginkan (size*size)
     * @return String[][] yang berisi nilai dari masing masing kolom
     * terdapat kolom yang berisi ranjau dan tidak berisi ranjau
     */
    static String[][] drawBoard(int size) {
        String[][] board = new String[size][size];
        Random random = new Random();
        int bomb = 0;
        //minimum bomb adalah size + 1
        int minRange = size + 1;
        //membuat random range untuk jumlah bomb
        while(bomb < minRange)
        {
            //maksimum bomb adalah ukuran * (ukuran-1)
            int maxRange = (size * (size - 1));
            bomb = random.nextInt(maxRange);
        }
        //mengiterasi penempatan bomb sesuai range
        while(bombPlanted < bomb)
        {
            /* meletakan bomb pada array[a][b]
               secara acak
            */
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            if(board[b][a] != null){
                board[b][a] = "x";
                bombPlanted ++;
            }
            else
                board[b][a] = Integer.toString(random.nextInt(4));
        }
        return board;
    }
    /**
     * method ini digunakan untuk menghitung jumlah bom
     * @param board board yang akan dihitung jumlah bomnya
     * @return jumlah bom
     */
    static int countBomb(String[][] board){
        int counter = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j].equals("x"))
                    counter++;
            }
        }
        return counter;
    }
    /**
     * method ini untuk mencetak papan minesweeper
     * @param board String[][] yang ingin dicetak
     */
    static void printBoard(String[][] board){
        Random random = new Random();
        for(int i = 0; i < board.length; i ++) {
            for(int j = 0; j < board.length; j ++) {
                if(board[i][j] == null)
                    board[i][j] = Integer.toString(random.nextInt(board[0].length));
                System.out.print(""+(i+1)+(j+1));
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    /**
     * method ini untuk mencetak board minesweeper dengan nilai aslinya (bom atau bukan)
     * @param board papan minesweeper yang akan dicetak kembali
     * @param val nilai board yang ingin dimunculkan nilai aslinya
     * @param isRevealAll flag untuk penanda apakah akan dimunculkan semua nilai aslinya atau tidak
     */
    static void revealField(String[][] board, String val, boolean isRevealAll){
        Random random = new Random();
        if(isRevealAll == true){
            for(int i = 0; i < board.length; i ++) {
                for(int j = 0; j < board.length; j ++) {
                    if(board[i][j].equals("x"))
                        System.out.print("x");
                    else if(board[i][j].equals("o"))
                        System.out.print("o");
                    else
                        System.out.print(""+(i+1)+(j+1));
                    System.out.print(" ");
                }
                System.out.print("\n");
            }
        }
        else{
            for(int i = 0; i < board.length; i ++) {
                for(int j = 0; j < board.length; j ++) {
                    String tempVal = ""+(i+1)+(j+1);
                    if(tempVal.equals(val)){
                        board[i][j] = "o";
                        System.out.print("o");
                    }
                    else if(board[i][j].equals("o"))
                        System.out.print("o");
                    else{
                        System.out.print(""+(i+1)+(j+1));
                    }
                    System.out.print(" ");
                }
                System.out.print("\n");
            }
        }
    }

    /**
     *
     * @param a sebagai index ke i pada dimensi array pertama
     * @param b sebagai index ke i pada dimensi array kedua
     * @param board board minesweeper yang akan dicek
     * @return
     */
    static boolean isBomb(int a, int b, String[][] board){
        if((a >= 0 && a < board.length) && (b >= 0 && b <= board.length) && board[a][b].equals("x"))
            return true;
        else
            return false;
    }
}
