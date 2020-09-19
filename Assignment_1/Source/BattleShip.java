import java.io.*;
import java.util.Scanner;

public class BattleShip {
    public static final int CARRIER = 1;
    public static final int SUBMARINE = 2;
    private static int[][] board;

    public static void main(String[] args) {
        try {
            SearchStrategy[] search = new SearchStrategy[3];
            Scanner input = new Scanner(new FileInputStream(new File("input.txt")));
            FileWriter writer = new FileWriter(new File("output.txt"));
            for (int i = 1; input.hasNextLine(); i++) {
                writer.write("Game " + i + " :\n");
                setBoard(input.nextLine());
                search[0] = new RandomSearch(board);
                search[1] = new HorizontalSweepSearch(board);
                search[2] = new SizeSearch(board);
                for (int j = 0; j < 3; j++) {
                    search[j].search();
                    writer.write(search[j].toString() + "\n********************************\n");
                }
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setBoard(String locations) {
        board = new int[25][25];
        String[] a = locations.split("[()]");
        for (int i = 0; i < a.length; i++) {
            if (i % 2 != 0) {
                int column = Integer.parseInt(a[i].split(",")[0]);
                int row = Integer.parseInt(a[i].split(",")[1]);
                board[column][row] = ((i <= 10) ? CARRIER : SUBMARINE);
            }
        }
    }
}