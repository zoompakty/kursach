package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
    ServerSocket s1;
    Socket ss;
    ServerSocket s2;
    Socket ss2;

    InputStream is;
    InputStream isp;
    DataInputStream dis;
    Thread thread;
    Thread thread1;

    OutputStream os;
    DataOutputStream dos;

    ImageView myImageView = new ImageView();

    double locX, locY;
    String shapeValue;
    String colorValue;
    String strokeValue;
    double endX, endY;

    double checkSquareX = 0.11111;
    double checkSquareY = 0.11111;


    {
        try {
            s1 = new ServerSocket(3000);
            ss = s1.accept();
            is = ss.getInputStream();
            isp = ss.getInputStream();

            dis = new DataInputStream(is);
            os = ss.getOutputStream();
            dos = new DataOutputStream(os);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml")
        );
        Parent root = loader.load();
        Controller controller = (Controller) loader.getController();
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    if ((controller.getcShape() == "Линия")) {
                        System.out.println(event.getSceneX());
                        System.out.println(event.getSceneY());
                        endX = event.getSceneX();
                        endY = event.getSceneY();
                        Line line = new Line(locX, locY, endX, endY);
                        line.setStroke(controller.getcColor());
                        ((Pane) root).getChildren().add(line);
                        try {
                            dos.writeDouble(locX);
                            dos.writeDouble(locY);
                            dos.writeUTF(controller.getShapeToSend());
                            dos.writeUTF(controller.getColorToSend());
                            dos.writeUTF(controller.getStrokeToSend());
                            dos.writeDouble(endX);
                            dos.writeDouble(endY);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    System.out.println(event.getSceneX());
                    System.out.println(event.getSceneY());
                    locX = event.getSceneX();
                    locY = event.getSceneY();

                    if (controller.getcShape() == "Круг") {
                        Circle circle = new Circle(locX, locY, controller.getTextRadius());
                        circle.setFill(controller.getcColor());
                        circle.setStrokeWidth(controller.getTextRadius() / 3);
                        circle.setStroke(controller.getcContour());
                        ((Pane) root).getChildren().add(circle);
                        try {
                            dos.writeDouble(locX);
                            dos.writeDouble(locY);
                            dos.writeUTF(controller.getShapeToSend());
                            dos.writeUTF(controller.getColorToSend());
                            dos.writeUTF(controller.getStrokeToSend());
                            dos.writeInt(controller.getTextRadius());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (controller.getcShape() == "Прямоугольник") {
                        Rectangle rectangle = new Rectangle(locX, locY, controller.getTextWidth(), controller.getTextHeight());
                        rectangle.setFill(controller.getcColor());
                        rectangle.setStrokeWidth(controller.getTextRadius() / 5);
                        rectangle.setStroke(controller.getcContour());
                        ((Pane) root).getChildren().add(rectangle);
                        try {
                            dos.writeDouble(locX);
                            dos.writeDouble(locY);
                            dos.writeUTF(controller.getShapeToSend());
                            dos.writeUTF(controller.getColorToSend());
                            dos.writeUTF(controller.getStrokeToSend());
                            dos.writeInt(controller.getTextHeight());
                            dos.writeInt(controller.getTextWidth());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (controller.getcShape() == "Текст") {
                        Text text = new Text(locX, locY, controller.getTextText());
                        text.setFont(new Font(controller.getTextFont()));
                        text.setFill(controller.getcColor());
                        ((Pane) root).getChildren().add(text);
                        try {
                            dos.writeDouble(locX);
                            dos.writeDouble(locY);
                            dos.writeUTF(controller.getShapeToSend());
                            dos.writeUTF(controller.getColorToSend());
                            dos.writeUTF(controller.getStrokeToSend());
                            dos.writeUTF(controller.getTextText());
                            dos.writeInt(controller.getTextFont());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        });
        ScrollPane scrollPane = controller.getOpenPane();
        Button openButton = controller.getButtonOpen();
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        Socket s2 = null;
                        OutputStream outputStream=null;
                        try {
                            s2 = new Socket("localhost", 4000);
                            outputStream = s2.getOutputStream();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        ImageView myImageView = new ImageView();
                        FileChooser fileChooser = new FileChooser();
                        //Set extension filter
                        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                        FileChooser.ExtensionFilter extFilterJPG =
                                new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg",
                                        "*.JPEG", "*.jpeg");
                        fileChooser.getExtensionFilters().addAll(extFilterPNG,extFilterJPG);
                        //Show open file dialog
                        File file = fileChooser.showOpenDialog(null);

                        try {
                            BufferedImage bufferedImage = ImageIO.read(file);
                            ImageIO.write(bufferedImage,"png",s2.getOutputStream());
                            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                            myImageView.setImage(image);
                            s2.close();
                        } catch (IOException ex) {
                        }
                        scrollPane.setContent(myImageView);
                        scrollPane.setVisible(true);
                    }
                });
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                while (true) {
                    try {
                        s2 = new ServerSocket(2000);
                        System.out.println("openServerSocket is created");
                        ss2 = s2.accept();
                        System.out.println("openServerSocket is accepted");

                        BufferedImage bufferedImage = ImageIO.read(ImageIO.createImageInputStream(ss2.getInputStream()));
                        /*ImageIO.write(bufferedImage, "png", new File("C:\\Users\\danil\\Desktop\\test2.png"));//Удалить строчку на другом ПК*/
                        s2.close();
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        myImageView.setImage(image);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                scrollPane.setContent(myImageView);
                                scrollPane.setVisible(true);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread1.start();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        locX = dis.readDouble();
                        locY = dis.readDouble();
                        shapeValue = dis.readUTF();
                        System.out.println("Figure: " + shapeValue);
                        colorValue = dis.readUTF();
                        System.out.println("Color: " + colorValue);
                        strokeValue = dis.readUTF();
                        System.out.println("Color of stroke : " + strokeValue);

                        System.out.println(locX);
                        System.out.println(locY);
                        switch (shapeValue) {
                            case "Круг":
                                controller.setTextRadius(dis.readInt());
                                break;
                            case "Прямоугольник":
                                controller.setTextHeight(dis.readInt());
                                controller.setTextWidth(dis.readInt());
                                break;
                            case "Текст":
                                controller.setTextText(dis.readUTF());
                                controller.setTextFont(dis.readInt());
                                break;
                            case "Линия":
                                endX = dis.readDouble();
                                endY = dis.readDouble();
                                break;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.setcShape(shapeValue);
                            controller.setcColor(colorValue);
                            controller.setcStroke(strokeValue);
                            if (shapeValue.equals("Круг")) {
                                Circle circle = new Circle(locX, locY, controller.getTextRadius());
                                circle.setFill(controller.getcColor());
                                circle.setStrokeWidth(controller.getTextRadius() / 3);
                                circle.setStroke(controller.getcContour());
                                ((Pane) root).getChildren().add(circle);

                            } else if (shapeValue.equals("Прямоугольник")) {
                                if (checkSquareX != locX && checkSquareY != locY) {
                                    Rectangle rectangle = new Rectangle(locX, locY, controller.getTextWidth(), controller.getTextHeight());
                                    rectangle.setFill(controller.getcColor());
                                    rectangle.setStrokeWidth(controller.getTextRadius() / 5);
                                    rectangle.setStroke(controller.getcContour());
                                    ((Pane) root).getChildren().add(rectangle);
                                    checkSquareX = locX;
                                    checkSquareY = locY;
                                }
                            } else if (shapeValue.equals("Текст")) {
                                Text text = new Text(locX, locY, controller.getTextText());
                                text.setFont(new Font(controller.getTextFont()));
                                text.setFill(controller.getcColor());
                                ((Pane) root).getChildren().add(text);
                            } else if (shapeValue.equals("Линия")) {
                                Line line = new Line(locX, locY, endX, endY);
                                line.setStroke(controller.getcColor());
                                ((Pane) root).getChildren().add(line);
                            }
                        }
                    });
                }
            }
        });
        thread.start();

        Button saveButton = controller.getButtonSave();
        saveButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        controller.hideAll();
                        WritableImage image = root.snapshot(new SnapshotParameters(), null);

                        // TODO: probably use a file chooser here
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save Image");
                        File file = fileChooser.showSaveDialog(primaryStage);
                        try {
                            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        } catch (IOException r) {
                            // TODO: handle exception here
                        }
                        controller.unHideAll();
                    }
                });
    }


    public static void main(String[] args) {
        launch(args);

    }
}
