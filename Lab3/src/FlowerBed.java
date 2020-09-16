import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class FlowerBed implements GardenComponent {
    private Rectangle rectangle;
    private ArrayList<Flower> flowersList;

    public FlowerBed(Rectangle rectangle) {
        this.rectangle = rectangle;
        flowersList = new ArrayList<>();
    }

    public void addFlower(Flower flower) {
        flowersList.add(flower);
    }

    public void removeFlower(Flower flower) {
        flowersList.remove(flower);
    }

    @Override
    public Shape getShape() {
        return rectangle;
    }

    @Override
    public void move(double dX, double dY) {
        rectangle.setX(rectangle.getX()+dX);
        rectangle.setY(rectangle.getY()+dY);
        for(Flower flower : flowersList)
            flower.move(dX,dY);
    }
}