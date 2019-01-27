package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private ScrollPane openPane;
    @FXML
    private Button buttonOpen;
    @FXML
    private Label labelColor;

    @FXML
    private Label labelFigure;
    @FXML
    private Button buttonSave;
    @FXML
    private Label labelFont;

    @FXML
    private TextField textFont;
    @FXML
    private TextField textText;
    @FXML
    private Label labelText;
    @FXML
    private Label labelH;

    @FXML
    private Label labelRad;

    @FXML
    private Label labelW;

    @FXML
    private Label labelCont;

    @FXML
    private ColorPicker cColor;

    @FXML
    private ColorPicker cContour;

    @FXML
    private ComboBox<String> cShape;

    @FXML
    private TextField textHeight;

    @FXML
    private TextField textRadius;

    @FXML
    private TextField textWidth;
    public Button getButtonOpen() {
        return buttonOpen;
    }
    public ScrollPane getOpenPane() {
        return openPane;
    }

    public int getTextFont() {
        return Integer.parseInt(textFont.getText());    }


    public String getTextText() {
        return textText.getText();
    }

    public int getTextRadius() {
        return Integer.parseInt(textRadius.getText());
    }

    public void setTextRadius(int textRadius) {
            this.textRadius.setText(String.valueOf(textRadius));    }

    public void setTextHeight(int textHeight) {
        this.textHeight.setText(String.valueOf(textHeight));     }

    public void setTextWidth(int textWidth) {
        this.textWidth.setText(String.valueOf(textWidth));     }
    public void setTextText(String textText) {
        this.textText.setText(textText);
    }

    public void setTextFont(int textFont) {
        this.textFont.setText(String.valueOf(textFont));
    }

    public int getTextHeight() {
        return Integer.parseInt(textHeight.getText());    }

    public int getTextWidth() {
        return Integer.parseInt(textWidth.getText());    }
    public String getColorToSend() {
        return cColor.getValue().toString();
    }

    public String getStrokeToSend() {
        return cContour.getValue().toString();
    }

    public String getShapeToSend() {
        return cShape.getValue();
    }


    public Paint getcColor() {
        return cColor.getValue();
    }

    public void setcColor(String cColor) {
        this.cColor.setValue(Color.valueOf(String.valueOf(cColor)));
    }
    public void setcStroke(String cStroke){
        this.cContour.setValue(Color.valueOf(String.valueOf(cStroke)));
    }
    public void setcShape(String cShape){
        this.cShape.setValue(String.valueOf(cShape));
    }

    public Paint getcContour(){ return cContour.getValue();}

    public String getcShape() {
        return cShape.getValue();
    }
    public Button getButtonSave() {
        return buttonSave;
    }
    public void hideEvrExCircle(){
        if(cShape.getValue()=="Круг"){
            textHeight.setVisible(false);
            textWidth.setVisible(false);
            labelH.setVisible(false);
            labelW.setVisible(false);
            labelRad.setVisible(true);
            textRadius.setVisible(true);
            cContour.setVisible(true);
            textText.setVisible(false);
            labelText.setVisible(false);
            labelFont.setVisible(false);
            textFont.setVisible(false);
        }else if(cShape.getValue()=="Линия"){
            textHeight.setVisible(false);
            textWidth.setVisible(false);
            labelH.setVisible(false);
            labelW.setVisible(false);
            labelRad.setVisible(false);
            textRadius.setVisible(false);
            textText.setVisible(false);
            labelText.setVisible(false);
            labelFont.setVisible(false);
            textFont.setVisible(false);
        }
        else if(cShape.getValue()=="Прямоугольник"){
            textHeight.setVisible(true);
            textWidth.setVisible(true);
            labelH.setVisible(true);
            labelW.setVisible(true);
            labelRad.setVisible(false);
            textRadius.setVisible(false);
            textText.setVisible(false);
            labelText.setVisible(false);
            labelFont.setVisible(false);
            textFont.setVisible(false);
        }else if(cShape.getValue()=="Текст"){
            textHeight.setVisible(false);
            textWidth.setVisible(false);
            labelH.setVisible(false);
            labelW.setVisible(false);
            labelRad.setVisible(false);
            textRadius.setVisible(false);
            textText.setVisible(true);
            labelText.setVisible(true);
            labelFont.setVisible(true);
            textFont.setVisible(true);

        }
    }
    public void hideAll(){
        labelFont.setVisible(false);
        labelText.setVisible(false);
        labelRad.setVisible(false);
        labelW.setVisible(false);
        labelH.setVisible(false);
        labelColor.setVisible(false);
        labelCont.setVisible(false);
        labelFigure.setVisible(false);
        cShape.setVisible(false);
        cContour.setVisible(false);
        cColor.setVisible(false);
        textText.setVisible(false);
        textWidth.setVisible(false);
        textHeight.setVisible(false);
        textRadius.setVisible(false);
        textFont.setVisible(false);
        buttonSave.setVisible(false);
    }
    public void unHideAll(){
        labelFont.setVisible(true);
        labelText.setVisible(true);
        labelRad.setVisible(true);
        labelW.setVisible(true);
        labelH.setVisible(true);
        labelColor.setVisible(true);
        labelCont.setVisible(true);
        labelFigure.setVisible(true);
        cShape.setVisible(true);
        cContour.setVisible(true);
        cColor.setVisible(true);
        textText.setVisible(true);
        textWidth.setVisible(true);
        textHeight.setVisible(true);
        textRadius.setVisible(true);
        textFont.setVisible(true);
        buttonSave.setVisible(true);

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textHeight.setVisible(false);
        textWidth.setVisible(false);
        textRadius.setVisible(false);
        labelH.setVisible(false);
        labelW.setVisible(false);
        labelRad.setVisible(false);
        textText.setVisible(false);
        labelText.setVisible(false);
        labelFont.setVisible(false);
        textFont.setVisible(false);
        openPane.setVisible(false);
        cShape.getItems().addAll("Прямоугольник","Круг","Текст","Линия");
        textRadius.setText("10");
        textWidth.setText("10");
        textHeight.setText("10");


    }
}
