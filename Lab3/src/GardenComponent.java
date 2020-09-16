import javafx.scene.shape.Shape;

public interface GardenComponent {
    Shape getShape() ;
    void move(double dX , double dY);
}