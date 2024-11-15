import java.util.Scanner;
import java.util.Random;

public class game {
    public static Object[] random(int start, int end) {
        Random rd = new Random();
        int[][] arr1 = new int[start][end];
        int n = 0;

        for (int i = 0; i < start; i++) {
            for (int j = 0; j < end; j++) {
                arr1[i][j] = rd.nextInt(2);
                if (arr1[i][j] == 0) {
                    n++;
                }
            }
        }

        return new Object[] { arr1, n };
    }

    public static void battlement(Object[] randomResult, int chances) {
        int[][] arr = (int[][]) randomResult[0];
        int total = (int) randomResult[1];
        Scanner game = new Scanner(System.in);

        int hit = 0, row, col;
        while (chances > 0) {
            System.out.println("ENTER THE SPECIFIC ROW:");
            row = game.nextInt();
            System.out.println("ENTER THE SPECIFIC COLUMN:");
            col = game.nextInt();

            if ((row < 4 && row >= 0) && (col < 4 && col >= 0)) {
                if (arr[row][col] == 0) {
                    System.out.println("YOU HAVE ATTACKED A SHIP");
                    arr[row][col] = 1;
                    hit++;
                    total--;
                } else {
                    System.out.println("SORRY YOU HAVE NOT ATTACKED A SHIP");
                }
                System.out.println("BALANCE REMAINING " + total);
                System.out.println("TOTAL CHANCES LEFT " + (--chances));
            } else {
                System.out.println("WRONG INPUT, PLEASE ENTER AGAIN");
            }
        }

        System.out.println("GAME OVER. YOU HIT " + hit + " SHIPS.");
    }

    public static void main(String[] args) {
        System.out.println("\t\t\tBATTLEMENT OF SHIPS");
        System.out.println("YOU HAVE TO GUESS THE SHIPS PRESENT IN A 4X4 MATRIX IN 12 CHANCES");
        System.out.println("IN THIS YOU HAVE 8 SHIPS");

        Object[] randomResult = random(4, 4);
        battlement(randomResult, 12);
    }
}
