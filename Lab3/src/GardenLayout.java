import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GardenLayout extends Application {

    private ArrayList<GardenComponent> allObjects = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ArrayList<Object> list = placeObjects();
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #abff8a");
        for (Object ob : list)
            root.getChildren().add((Node) ob);
        Scene scene = new Scene(root, 700, 800);
        final Point2D[] lastPoint = {new Point2D(0, 0)};
        final GardenComponent[] object = {null};
        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Point2D point = new Point2D(event.getX(), event.getY());
                String eventName = event.getEventType().getName();
                int selectedObjectType = 10;
                System.out.println(eventName);
                System.out.println(point.getX() + " " + point.getY());
                try {
                    switch (eventName) {
                        case "MOUSE_PRESSED":
                            if (point.getX() >= 20 && point.getX() <= 155 && point.getY() >= 20 && point.getY() <= 780) {
                                // create new object
                                if (point.getY() >= 70 && point.getY() <= 90) {
                                    // create new flower
                                    if (point.getX() >= 35 && point.getX() <= 55) {
                                        // create new red flower
                                        Circle circle = new Circle(point.getX(),point.getY(),15,Color.RED) ;
                                        circle.setStroke(Color.BLACK);
                                        circle.setStrokeWidth(1);
                                        Flower flower=new Flower(circle) ;
                                        object[0]=flower ;
                                        allObjects.add(flower) ;
                                        root.getChildren().add(circle) ;
                                    } else if (point.getX() >= 75 && point.getX() <= 95) {
                                        // create new yellow flower
                                        Circle circle = new Circle(point.getX(),point.getY(),15,Color.YELLOW) ;
                                        circle.setStroke(Color.BLACK);
                                        circle.setStrokeWidth(1);
                                        Flower flower=new Flower(circle) ;
                                        object[0]=flower ;
                                        allObjects.add(flower) ;
                                        root.getChildren().add(circle) ;
                                    } else if (point.getX() >= 115 && point.getX() <= 135) {
                                        // create new green flower
                                        Circle circle = new Circle(point.getX(),point.getY(),15,Color.YELLOWGREEN) ;
                                        circle.setStroke(Color.BLACK);
                                        circle.setStrokeWidth(1);
                                        Flower flower=new Flower(circle) ;
                                        object[0]=flower ;
                                        allObjects.add(flower) ;
                                        root.getChildren().add(circle) ;
                                    }
                                } else if (point.getY() >= 150 && point.getY() <= 330 && point.getX() >= 35 && point.getX() <= 135) {
                                    // create new flower bed
                                    Rectangle rectangle = new Rectangle(point.getX(),point.getY(), 120, 200);
                                    rectangle.setFill(Color.ROSYBROWN);
                                    rectangle.setStrokeWidth(1);
                                    rectangle.setStroke(Color.BLACK);
                                    FlowerBed flowerBed = new FlowerBed(rectangle) ;
                                    object[0]=flowerBed ;
                                    allObjects.add(flowerBed) ;
                                    root.getChildren().add(rectangle);
                                } else if (point.getY() >= 400 && point.getY() <= 480 && point.getX() >= 45 && point.getX() <= 125) {
                                    // create new tree
                                    Circle circle = new Circle(point.getX(),point.getY(),50,Color.DARKGREEN) ;
                                    circle.setStroke(Color.BLACK);
                                    circle.setStrokeWidth(1);
                                    Tree tree=new Tree(circle) ;
                                    object[0]=tree ;
                                    allObjects.add(tree) ;
                                    root.getChildren().add(circle) ;
                                }
                            } else if (point.getX() >= 157 || point.getY() >= 782) {
                                // moving the created object
                                object[0] = selectedObject(point);
                            }
                            break;
                        case "MOUSE_DRAGGED":
                            double dX = point.getX() - lastPoint[0].getX();
                            double dY = point.getY() - lastPoint[0].getY();
                            object[0].move(dX, dY);
                            break;
                        case "MOUSE_RELEASED":
                            boolean isInDrawMenu = false ;
                            Shape shape = object[0].getShape() ;
                            if(shape instanceof Circle){
                                Circle circle = (Circle) shape;
                                if(point.getX()-circle.getRadius()<155 && point.getY()-circle.getRadius()<785)
                                    isInDrawMenu=true ;
                            }else {
                                if(point.getX()<155 && point.getY()<785)
                                    isInDrawMenu=true ;
                            }
                            if(isInDrawMenu){
                                root.getChildren().remove(object[0].getShape());
                                allObjects.remove(object[0]);
                            }else {
                                if (object[0] instanceof Flower) {
                                    for(GardenComponent obj : allObjects)
                                        if(obj instanceof FlowerBed)
                                            ((FlowerBed) obj).removeFlower((Flower) object[0]);
                                    if (interferenceObject(object[0]) instanceof FlowerBed) {
                                        ((FlowerBed) interferenceObject(object[0])).addFlower((Flower) object[0]);
                                    } else {
                                        root.getChildren().remove(object[0].getShape());
                                        allObjects.remove(object[0]);
                                    }
                                }
                            }
                            object[0]=null ;
                            break;
                    }
                    lastPoint[0]=point ;
                } catch (NullPointerException ex) {}
            }
        };
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMousePressed(mouseHandler);
        scene.setOnMouseReleased(mouseHandler);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Garden Layout");
        primaryStage.show();
    }

    private GardenComponent interferenceObject(GardenComponent object) {
        // This method returns an object that interferes with the input object
        if (object instanceof Flower) {
            Circle circleObject = (Circle) object.getShape();
            for (GardenComponent obj : allObjects) {
                if (obj instanceof Flower) {
                    Circle circleObj = (Circle) obj.getShape();
                    if ((circleObject.getCenterX() - circleObject.getRadius() > circleObj.getCenterX() + circleObj.getRadius() &&
                            circleObject.getCenterX() + circleObject.getRadius() < circleObj.getCenterX() - circleObj.getRadius() &&
                            circleObject.getCenterY() - circleObject.getRadius() > circleObj.getCenterY() + circleObj.getRadius() &&
                            circleObject.getCenterY() + circleObject.getRadius() < circleObj.getCenterY() - circleObj.getRadius())) {
                        return obj;
                    }
                }
            }
            for (GardenComponent obj : allObjects) {
                if (obj instanceof FlowerBed) {
                    Rectangle rectangleObj = (Rectangle) obj.getShape();
                    if (circleObject.getCenterX() + circleObject.getRadius() < rectangleObj.getX() + rectangleObj.getWidth() &&
                            circleObject.getCenterX() - circleObject.getRadius() > rectangleObj.getX() &&
                            circleObject.getCenterY() + circleObject.getRadius() < rectangleObj.getY() + rectangleObj.getHeight() &&
                            circleObject.getCenterY() - circleObject.getRadius() > rectangleObj.getY()) {
                        return obj;
                    }
                }
            }
        }
        return null;
    }

    private GardenComponent selectedObject(Point2D point) {
        // This method find selected object out of draw menu
        // I write three for loop to set the priority
        for (GardenComponent object : allObjects)
            if (point.getX() >= 157 || point.getY() >= 782) {
                if (object instanceof Flower) {
                    Circle circle = (Circle) object.getShape();
                    if (point.getX() <= circle.getCenterX() + 10 && point.getX() >= circle.getCenterX() - 10 && point.getY() <= circle.getCenterY() + 10 && point.getY() >= circle.getCenterY() - 10)
                        return object;
                }
            }
        for (GardenComponent object : allObjects)
            if (object instanceof FlowerBed) {
                Rectangle rectangle = (Rectangle) object.getShape();
                if (point.getX() >= rectangle.getX() + 10 && point.getX() <= rectangle.getX() + rectangle.getWidth() - 10 && point.getY() >= rectangle.getY() + 10 && point.getY() <= rectangle.getY() + rectangle.getHeight() - 10)
                    return object;
            }
        for (GardenComponent object : allObjects)
            if (object instanceof Tree) {
                Circle circle = (Circle) object.getShape();
                if (point.getX() <= circle.getCenterX() + 45 && point.getX() >= circle.getCenterX() - 45 && point.getY() <= circle.getCenterY() + 45 && point.getY() >= circle.getCenterY() - 45)
                    return object;
            }
        return null;
    }

    private ArrayList<Object> placeObjects() {
        ArrayList<Object> list = new ArrayList<>();
        Rectangle drawMenu = new Rectangle(20, 20, 130, 760);
        drawMenu.setFill(Color.LIGHTGRAY);
        drawMenu.setStroke(Color.BLACK);
        drawMenu.setStrokeWidth(0.5);
        Label flowerLabel = new Label("Flowers");
        flowerLabel.setTranslateX(45);
        flowerLabel.setTranslateY(30);
        flowerLabel.setFont(Font.font(20));
        Circle flower1 = new Circle(45, 80, 15, Color.RED);
        flower1.setStroke(Color.BLACK);
        flower1.setStrokeWidth(1);
        Circle flower2 = new Circle(85, 80, 15, Color.YELLOW);
        flower2.setStroke(Color.BLACK);
        flower2.setStrokeWidth(1);
        Circle flower3 = new Circle(125, 80, 15, Color.YELLOWGREEN);
        flower3.setStrokeWidth(1);
        flower3.setStroke(Color.BLACK);
        Label flowerBedLabel = new Label("Flower Bed");
        flowerBedLabel.setTranslateX(35);
        flowerBedLabel.setTranslateY(110);
        flowerBedLabel.setFont(Font.font(20));
        Rectangle flowerBed = new Rectangle(25, 140, 120, 200);
        flowerBed.setFill(Color.ROSYBROWN);
        flowerBed.setStrokeWidth(1);
        flowerBed.setStroke(Color.BLACK);
        Label treeLabel = new Label("Tree");
        treeLabel.setTranslateX(60);
        treeLabel.setTranslateY(355);
        treeLabel.setFont(Font.font(20));
        Circle tree = new Circle(85, 440, 50, Color.DARKGREEN);
        tree.setStroke(Color.BLACK);
        tree.setStrokeWidth(1);
        list.add(drawMenu);
        list.add(flowerBedLabel);
        list.add(flowerLabel);
        list.add(treeLabel);
        list.add(flower1);
        list.add(flower2);
        list.add(flower3);
        list.add(flowerBed);
        list.add(tree);
        return list;
    }
}