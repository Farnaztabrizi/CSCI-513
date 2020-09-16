import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Flower implements GardenComponent {
    private Circle circle;

    public Flower(Circle circle) {
        this.circle = circle;
    }

    @Override
    public Shape getShape() {
        return circle;
    }

    @Override
    public void move(double dX, double dY) {
        circle.setCenterX(circle.getCenterX()+dX);
        circle.setCenterY(circle.getCenterY()+dY);
    }
}