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
    public static boolean[][] piratesShipLocation = new boolean[10][10];
    public static ImageView allImageViews[][];
    private Image islandImage;
    private Image pirateIslandImage;
    public Image shipImage;
    public Image pirateShipImage;
    private int numberOfIslands;
    private int numberOfPiratesIslands;
    private Ship ccs;
    private PirateShip[] pirateShips;
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
        pirateShips = new PirateShip[numberOfPiratesIslands];
        placePirateIslands();
        placeIslands();
    }
    // place the random pirate islands in the occean
    private void placePirateIslands() {
        for (int i = 0; i < numberOfPiratesIslands; ) {
            int column = (int) (Math.random() * 10), row = (int) (Math.random() * 10);
            if (!fullLocations[column][row]) {
                try {
                    if (!fullLocations[column][row + 1]) {
                        placePirateShips(i, column, row + 1);
                    } else if (!fullLocations[column][row - 1]) {
                        placePirateShips(i, column, row - 1);
                    } else if (!fullLocations[column + 1][row]) {
                        placePirateShips(i, column + 1, row);
                    } else if (!fullLocations[column - 1][row]) {
                        placePirateShips(i, column - 1, row);
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    continue;
                }
                allImageViews[column][row].setImage(pirateIslandImage);
                i++;
                fullLocations[column][row] = true;
            }
        }
    }
// place the pirate ship in the ocean

    private void placePirateShips(int i, int column, int row) {
        pirateShips[i] = new PirateShip(column, row);
        allImageViews[column][row].setImage(pirateShipImage);
        fullLocations[column][row] = true;
        piratesShipLocation[column][row] = true;
        ccs.observers.add(pirateShips[i]) ;
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
            islandImage = new Image(new FileInputStream("./images/island.jpg"));
            pirateIslandImage = new Image(new FileInputStream("./images/pirateIsland.png"));
            shipImage = new Image(new FileInputStream("./images/ship.png"));
            pirateShipImage = new Image(new FileInputStream("./images/pirateShip.png"));
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