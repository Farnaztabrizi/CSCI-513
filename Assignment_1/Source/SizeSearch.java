import java.util.Arrays;

public class SizeSearch implements SearchStrategy {
    private int[][] board;
    private int[][] carrierLocation;
    private int[][] submarineLocation;
    private boolean[][] isSearchedCell;
    private int count;
    private double time;

    public SizeSearch(int[][] board) {
        this.board = board;
        isSearchedCell = new boolean[25][25];
        for (int i = 0; i < isSearchedCell.length; i++)
            Arrays.fill(isSearchedCell[i], false);
        carrierLocation = new int[5][2];
        for (int i = 0; i < carrierLocation.length; i++)
            Arrays.fill(carrierLocation[i], -1);
        submarineLocation = new int[3][2];
        for (int i = 0; i < submarineLocation.length; i++)
            Arrays.fill(submarineLocation[i], -1);
        count = 0;
    }

    private void findCompletely(int xGuid, int yGuid, int shipType) {
        count++ ;
        switch (shipType) {
            case BattleShip.CARRIER:
                if (yGuid + 1 < board.length && !isSearchedCell[xGuid][yGuid+1] && board[xGuid][yGuid + 1] == shipType) {
                    for (int i = 0; i < carrierLocation.length; i++)
                        if (carrierLocation[i][0] == -1) {
                            carrierLocation[i][0] = xGuid;
                            carrierLocation[i][1] = yGuid + 1;
                            isSearchedCell[xGuid][yGuid + 1] = true;
                            findCompletely(xGuid, yGuid + 1, shipType);
                            break;
                        }
                }
                if (xGuid + 1 < board.length && !isSearchedCell[xGuid+1][yGuid] && board[xGuid + 1][yGuid] == shipType) {
                    for (int i = 0; i < carrierLocation.length; i++)
                        if (carrierLocation[i][0] == -1) {
                            carrierLocation[i][0] = xGuid+1;
                            carrierLocation[i][1] = yGuid;
                            isSearchedCell[xGuid+1][yGuid] = true;
                            findCompletely(xGuid+1, yGuid, shipType);
                            break;
                        }
                }
                if(yGuid-1>=0 && !isSearchedCell[xGuid][yGuid-1] && board[xGuid][yGuid - 1] == shipType){
                    for (int i = 0; i < carrierLocation.length; i++)
                        if (carrierLocation[i][0] == -1) {
                            carrierLocation[i][0] = xGuid;
                            carrierLocation[i][1] = yGuid-1;
                            isSearchedCell[xGuid][yGuid-1] = true;
                            findCompletely(xGuid, yGuid-1, shipType);
                            break;
                        }
                }
                if(xGuid-1>=0 && !isSearchedCell[xGuid-1][yGuid] && board[xGuid-1][yGuid] == shipType){
                    for (int i = 0; i < carrierLocation.length; i++)
                        if (carrierLocation[i][0] == -1) {
                            carrierLocation[i][0] = xGuid-1;
                            carrierLocation[i][1] = yGuid;
                            isSearchedCell[xGuid-1][yGuid] = true;
                            findCompletely(xGuid-1, yGuid, shipType);
                            break;
                        }
                }
                break;
            case BattleShip.SUBMARINE:
                if (yGuid + 1 < board.length && !isSearchedCell[xGuid][yGuid+1] && board[xGuid][yGuid + 1] == shipType) {
                    for (int i = 0; i < submarineLocation.length; i++)
                        if (submarineLocation[i][0] == -1) {
                            submarineLocation[i][0] = xGuid;
                            submarineLocation[i][1] = yGuid + 1;
                            isSearchedCell[xGuid][yGuid + 1] = true;
                            findCompletely(xGuid, yGuid+1, shipType);
                            break;
                        }
                }
                if (xGuid + 1 < board.length && !isSearchedCell[xGuid+1][yGuid] && board[xGuid + 1][yGuid] == shipType) {
                    for (int i = 0; i < submarineLocation.length; i++)
                        if (submarineLocation[i][0] == -1) {
                            submarineLocation[i][0] = xGuid+1;
                            submarineLocation[i][1] = yGuid;
                            isSearchedCell[xGuid+1][yGuid] = true;
                            findCompletely(xGuid+1, yGuid, shipType);
                            break;
                        }
                }
                if(yGuid-1>=0 && !isSearchedCell[xGuid][yGuid-1] && board[xGuid][yGuid - 1] == shipType){
                    for (int i = 0; i < submarineLocation.length; i++)
                        if (submarineLocation[i][0] == -1) {
                            submarineLocation[i][0] = xGuid;
                            submarineLocation[i][1] = yGuid-1;
                            isSearchedCell[xGuid][yGuid-1] = true;
                            findCompletely(xGuid, yGuid-1, shipType);
                            break;
                        }
                }
                if(xGuid-1>=0 && !isSearchedCell[xGuid-1][yGuid] && board[xGuid-1][yGuid] == shipType){
                    for (int i = 0; i < submarineLocation.length; i++)
                        if (submarineLocation[i][0] == -1) {
                            submarineLocation[i][0] = xGuid-1;
                            submarineLocation[i][1] = yGuid;
                            isSearchedCell[xGuid-1][yGuid] = true;
                            findCompletely(xGuid-1, yGuid, shipType);
                            break;
                        }
                }
                break;
        }
    }

    @Override
    public void search() {
        int j = 0;
        int n = 3;
        long startTime = System.currentTimeMillis();
        for (int column = 0; column < board.length && j < 2; column++) {
            for (int row = column%n ; row < board[column].length && j < 2; row += n) {
                if (isSearchedCell[column][row])
                    continue;
                switch (board[column][row]) {
                    case BattleShip.CARRIER:
                        for (int i = 0; i < carrierLocation.length; i++)
                            if (carrierLocation[i][0] == -1) {
                                carrierLocation[i][0] = column;
                                carrierLocation[i][1] = row;
                                findCompletely(column, row, BattleShip.CARRIER);
                                j++;
                                break;
                            }
                        break;
                    case BattleShip.SUBMARINE:
                        for (int i = 0; i < submarineLocation.length; i++)
                            if (submarineLocation[i][0] == -1) {
                                submarineLocation[i][0] = column;
                                submarineLocation[i][1] = row;
                                findCompletely(column, row, BattleShip.SUBMARINE);
                                n = 5;
                                j++;
                                break;
                            }
                        break;
                }
                isSearchedCell[column][row]=true ;
                count++;
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
        return "Strategy : Search By Size Of Ships\nNumber of cells searched : " + count +
                "\nCarrier found : (" + carrierLocation[0][0] + "," + carrierLocation[0][1] + ") to ("
                + carrierLocation[4][0] + "," + carrierLocation[4][1] + ") Submarine found : (" +
                submarineLocation[0][0] + "," + submarineLocation[0][1] + ") to ("
                + submarineLocation[2][0] + "," + submarineLocation[2][1] + ")\n" +
                "Search finished in " + time + " milli seconds.";
    }
}