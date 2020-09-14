//****************************
// author: Farnaz Tabrizi
// Course: Software Engineering-Csci 513
//****************************

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Ship extends Observable {

    private int columnIndex;
    private int rowIndex;
    public List<Observer> observers ;

    public Ship(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        observers=new ArrayList<>() ;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public boolean changeLocation(int columnIndex, int rowIndex) {
        if (OceanMap.fullLocations[columnIndex][rowIndex]) {
            if(OceanMap.piratesShipLocation[columnIndex][rowIndex]){
                OceanExplorer.piratesWins();
            }
            return false;
        }
        OceanMap.fullLocations[columnIndex][rowIndex] = true;
        OceanMap.fullLocations[this.columnIndex][this.rowIndex] = false;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        for (Observer ob : observers)
            ob.update(this,this);
        return true;
    }
}