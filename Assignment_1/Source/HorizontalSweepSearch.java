import java.util.Arrays;

public class HorizontalSweepSearch implements SearchStrategy {
    private int[][] board;
    private int[][] carrierLocation;
    private int[][] submarineLocation;
    private int count;
    private double time;
    public HorizontalSweepSearch(int[][] board){
        this.board = board;
        carrierLocation = new int[5][2];
        for (int i = 0; i < carrierLocation.length; i++)
            Arrays.fill(carrierLocation[i], -1);
        submarineLocation = new int[3][2];
        for (int i = 0; i < submarineLocation.length; i++)
            Arrays.fill(submarineLocation[i], -1);
        count = 0;
    }
    @Override
    public void search() {
        int j=0 ;
        long startTime = System.currentTimeMillis();
        for (int column = 0; column < board.length && j<8; column++) {
            for (int row = 0; row < board[column].length && j<8; row++) {
                switch (board[column][row]) {
                    case BattleShip.CARRIER:
                        for (int i = 0; i < carrierLocation.length; i++)
                            if (carrierLocation[i][0] == -1) {
                                carrierLocation[i][0] = column;
                                carrierLocation[i][1] = row;
                                j++ ;
                                break;
                            }
                        break;
                    case BattleShip.SUBMARINE:
                        for (int i = 0; i < submarineLocation.length; i++)
                            if (submarineLocation[i][0] == -1) {
                                submarineLocation[i][0] = column;
                                submarineLocation[i][1] = row;
                                j++ ;
                                break;
                            }
                        break;
                }
                count++ ;
            }
        }
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
        sortShipsLocations();
    }

    @Override
    public void sortShipsLocations() {
        if (carrierLocation[0][0] == carrierLocation[1][0]) {
            for (int j = 0; j < carrierLocation.length - 1; j++) {
                int min = carrierLocation[j][1];
                int index = j;
                for (int i = j + 1; i < carrierLocation.length; i++)
                    if (min > carrierLocation[i][1]) {
                        min = carrierLocation[i][1];
                        index = i;
                    }
                carrierLocation[index][1] = carrierLocation[j][1];
                carrierLocation[j][1] = min;
            }
        } else {
            for (int j = 0; j < carrierLocation.length - 1; j++) {
                int min = carrierLocation[j][0];
                int index = j;
                for (int i = j + 1; i < carrierLocation.length; i++)
                    if (min > carrierLocation[i][0]) {
                        min = carrierLocation[i][0];
                        index = i;
                    }
                carrierLocation[index][0] = carrierLocation[j][0];
                carrierLocation[j][0] = min;
            }
        }
        if (submarineLocation[0][0] == submarineLocation[1][0]) {
            for (int j = 0; j < submarineLocation.length - 1; j++) {
                int min = submarineLocation[j][1];
                int index = j;
                for (int i = j + 1; i < submarineLocation.length; i++)
                    if (min > submarineLocation[i][1]) {
                        min = submarineLocation[i][1];
                        index = i;
                    }
                submarineLocation[index][1] = submarineLocation[j][1];
                submarineLocation[j][1] = min;
            }
        } else {
            for (int j = 0; j < submarineLocation.length - 1; j++) {
                int min = submarineLocation[j][0];
                int index = j;
                for (int i = j + 1; i < submarineLocation.length; i++)
                    if (min > submarineLocation[i][0]) {
                        min = submarineLocation[i][0];
                        index = i;
                    }
                submarineLocation[index][0] = submarineLocation[j][0];
                submarineLocation[j][0] = min;
            }
        }
    }

    @Override
    public String toString() {
        return  "Strategy : Horizontal Sweep Search\nNumber of cells searched : " + count +
                "\nCarrier found : ("+carrierLocation[0][0]+","+carrierLocation[0][1]+") to ("
                +carrierLocation[4][0]+","+carrierLocation[4][1]+") Submarine found : ("+
                submarineLocation[0][0]+","+submarineLocation[0][1]+") to ("
                +submarineLocation[2][0]+","+submarineLocation[2][1]+")\n"+
                "Search finished in "+time+" milli seconds.";
    }
}
