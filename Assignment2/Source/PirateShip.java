//****************************
// author: Farnaz Tabrizi
// Course: Software Engineering-Csci 513
//purpose: Christopher Columbus sails the ocean blue 

//****************************

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

public class PirateShip implements Observer {

    private int column;
    private int row;
    private Image image;

    public PirateShip(int column, int row) {
        this.column = column;
        this.row = row;
        try {
            image = new Image(new FileInputStream("./images/pirateShip.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Ship ccs = (Ship) arg;
        if (ccs.getRowIndex() == row && ccs.getColumnIndex() == column)
            OceanExplorer.piratesWins();
        else
            changeLocation(ccs.getColumnIndex(), ccs.getRowIndex());
    }

    private void changeLocation(int targetColumn, int targetRow) {
        boolean isMoved = false;
        if (Math.abs(column - targetColumn) >= Math.abs(row - targetRow)) {
            if (column - targetColumn > 0) {
                if (!OceanMap.fullLocations[column - 1][row]) {
                    move(column - 1, row);
                    isMoved = true;
                } else if (column - 1 == targetColumn && row == targetRow)
                    OceanExplorer.piratesWins();
            } else {
                if (!OceanMap.fullLocations[column + 1][row]) {
                    move(column + 1, row);
                    isMoved = true;
                } else if (column + 1 == targetColumn && row == targetRow)
                    OceanExplorer.piratesWins();
            }
            if (!isMoved) {
                if(row-targetRow>0){
                    if (!OceanMap.fullLocations[column][row-1]) {
                        move(column, row-1);
                        isMoved = true;
                    } else if (column == targetColumn && row -1 == targetRow)
                        OceanExplorer.piratesWins();
                } else {
                    if (!OceanMap.fullLocations[column][row+1]) {
                        move(column, row+1);
                        isMoved = true;
                    } else if (column == targetColumn && row +1 == targetRow)
                        OceanExplorer.piratesWins();
                }
            }
        } else {
            if (row-targetRow>0) {
                if (!OceanMap.fullLocations[column][row-1]) {
                    move(column, row-1);
                    isMoved = true;
                } else if (column == targetColumn && row-1 == targetRow)
                    OceanExplorer.piratesWins();
            } else {
                if (!OceanMap.fullLocations[column][row+1]) {
                    move(column, row+1);
                    isMoved = true;
                } else if (column == targetColumn && row+1 == targetRow)
                    OceanExplorer.piratesWins();
            }
            if (!isMoved) {
                if (column - targetColumn > 0) {
                    if (!OceanMap.fullLocations[column - 1][row]) {
                        move(column - 1, row);
                    } else if (column - 1 == targetColumn && row == targetRow)
                        OceanExplorer.piratesWins();
                } else {
                    if (!OceanMap.fullLocations[column + 1][row]) {
                        move(column + 1, row);
                    } else if (column + 1 == targetColumn && row == targetRow)
                        OceanExplorer.piratesWins();
                }
            }
        }
    }
// pirate ship movement method in the ocean
    private void move(int column, int row) {
        OceanMap.fullLocations[this.column][this.row] = false;
        OceanMap.fullLocations[column][row] = true;
        OceanMap.piratesShipLocation[this.column][this.row] = false;
        OceanMap.piratesShipLocation[column][row] = true;
        OceanMap.allImageViews[this.column][this.row].setImage(null);
        OceanMap.allImageViews[column][row].setImage(image);
        this.column = column;
        this.row = row;
    }
}