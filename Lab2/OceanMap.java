//****************************
// author: Farnaz Tabrizi
// Course: Software Engineering-Csci 513
//****************************
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OceanMap {
    public static boolean[][] fullLocations = new boolean[10][10];
    public static ImageView allImageViews[][];
    public Image shipImage;
    private int numberOfIslands;
    private Ship ccs;
    private GridPane grid;

    public OceanMap() {
        int maxNumIsland = 16;
        int minNumIsland = 10;
        numberOfIslands = (int) ((Math.random() * (maxNumIsland - minNumIsland)) + minNumIsland);
        int maxNumPrIsland = 6;
        int minNumPrIsland = 2;
        numberOfPiratesIslands = (int) ((Math.random() * (maxNumPrIsland - minNumPrIsland)) + minNumPrIsland);
        initImages();
        setGrid();
        placeShip();
        placeIslands();
    }
    
// place the random islands in the ocean 

    private void placeIslands() {
        for (int i = 0; i < numberOfIslands; ) {
            int column = (int) (Math.random() * 10), row = (int) (Math.random() * 10);
            if (!fullLocations[column][row]) {
                allImageViews[column][row].setImage(islandImage);
                i++;
                fullLocations[column][row] = true;
            }
        }
    }
// place the christopher columbus ship in the island

    private void placeShip() {
        int column = (int) (Math.random() * 10), row = (int) (Math.random() * 10);
        if (!fullLocations[column][row]) {
            ccs = new Ship(column, row);
            allImageViews[column][row].setImage(shipImage);
            fullLocations[column][row] = true;
        } else {
            placeShip();
        }
    }

    public Ship getCcs() {
        return ccs;
    }

    private void initImages() {
        try {
            shipImage = new Image(new FileInputStream("./images/ship.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setGrid() {
        grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setStyle("-fx-background-color: aqua");
        for (int i = 0; i < 10; i++) {
            ColumnConstraints c = new ColumnConstraints();
            RowConstraints r = new RowConstraints();
            c.setPrefWidth(90);
            r.setPrefHeight(90);
            grid.getColumnConstraints().add(c);
            grid.getRowConstraints().add(r);
        }
        allImageViews = new ImageView[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                allImageViews[i][j] = new ImageView();
                allImageViews[i][j].setFitWidth(90);
                allImageViews[i][j].setFitHeight(90);
                grid.add(allImageViews[i][j], i, j);
            }
        }
        grid.setGridLinesVisible(true);
    }

    public GridPane getGrid() {
        return grid;
    }
}