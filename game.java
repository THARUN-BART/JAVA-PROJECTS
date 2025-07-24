import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class game {
    // Generate grid and randomly place ships
    public static Object[] generateGrid(int rows, int cols) {
        Random rd = new Random();
        int[][] grid = new int[rows][cols];
        int shipCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = rd.nextInt(2); 
                if (grid[i][j] == 0) shipCount++;
            }
        }
        return new Object[]{grid, shipCount};
    }

    // Display the grid after game ends
    public static void displayGrid(int[][] grid) {
        System.out.println("\n🔍 Final Grid:");
        for (int[] row : grid) {
            for (int cell : row) {
                System.out.print((cell == 1 ? "🟦" : "🚢") + " ");
            }
            System.out.println();
        }
    }

    // Main game loop
    public static void playGame(Object[] gameData, int chances, int rows, int cols) {
        int[][] grid = (int[][]) gameData[0];
        int totalShips = (int) gameData[1];
        int hits = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🚀 WELCOME TO BATTLESHIP!");
        System.out.println("There are " + totalShips + " hidden ships.");
        System.out.println("You have " + chances + " chances to attack!\n");

        while (chances > 0 && totalShips > 0) {
            try {
                System.out.print("Enter row (0-" + (rows - 1) + "): ");
                int row = scanner.nextInt();
                System.out.print("Enter column (0-" + (cols - 1) + "): ");
                int col = scanner.nextInt();

                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    if (grid[row][col] == 0) {
                        System.out.println("🎯 HIT! Ship Destroyed!");
                        grid[row][col] = 1; // Mark as attacked
                        hits++;
                        totalShips--;
                    } else {
                        System.out.println("❌ MISS! No ship there.");
                    }
                    chances--;
                    System.out.println("Remaining ships: " + totalShips + " | Chances left: " + chances + "\n");
                } else {
                    System.out.println("⚠️ Invalid Input! Choose a valid row and column.");
                }
            } catch (InputMismatchException e) {
                System.out.println("🚫 Error! Please enter numeric values.");
                scanner.next();
            }
        }

        System.out.println("\n🛑 GAME OVER! You hit " + hits + " ships.");
        displayGrid(grid);
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rd = new Random();

        System.out.print("Enter grid size (e.g., 4 for a 4x4 grid): ");
        int size = scanner.nextInt();

        // Randomize chances between size and size*4
        int chances = rd.nextInt(size * 3) + size;

        Object[] gameData = generateGrid(size, size);
        playGame(gameData, chances, size, size);
    }
}
