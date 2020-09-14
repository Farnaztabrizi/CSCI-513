//****************************
// author: Farnaz Tabrizi
// Course: Software Engineering-Csci 513
//****************************

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Ship extends Observable {

    private int columnIndex;
    private int rowIndex;
    private Image image ;
    public List<Observer> observers ;

    public Ship(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        try {
            image=new Image(new FileInputStream("./images/ship.png")) ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        observers=new ArrayList<>() ;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void changeLocation(int columnIndex, int rowIndex) {
        if (OceanMap.fullLocations[columnIndex][rowIndex]) {
            if(OceanMap.piratesShipLocation[columnIndex][rowIndex]){
                OceanExplorer.piratesWins();
            }
            return;
        }
        OceanMap.fullLocations[columnIndex][rowIndex] = true;
        OceanMap.fullLocations[this.columnIndex][this.rowIndex] = false;
        OceanMap.allImageViews[columnIndex][rowIndex].setImage(image);
        OceanMap.allImageViews[this.columnIndex][this.rowIndex].setImage(null);
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        for (Observer ob : observers)
            ob.update(null,this);
    }
}