package com.example.katestirovanie;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.rgb;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public Canvas mainCanvas;
    public Slider sliderA;
    public Slider sliderB;
    public Slider sliderZ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw();

        sliderB.valueProperty().addListener((observable, oldValue, newValue) ->{
            draw();
        });
        sliderA.valueProperty().addListener((observable, oldValue, newValue) ->{
            draw();
        });
        sliderZ.valueProperty().addListener((observable, oldValue, newValue) ->{
            draw();
        });
    }

    void draw(){
        double zoom = sliderZ.getValue();

        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();

        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        ctx.save(); //Сохранение состояния матрицы преобразований
        Affine transform = ctx.getTransform();
        transform.appendTranslation(mainCanvas.getWidth() / 2, mainCanvas.getHeight() / 2);
        transform.appendScale(zoom, zoom);
        ctx.setTransform(transform);

        double rB = sliderB.getValue();

        double rA = sliderA.getValue();

        double r3 = (sliderB.getValue() - sliderA.getValue());
        double fi = 360 / 7;

        ctx.setFill(rgb(179, 0, 0));
        ctx.fillOval(0 - rB, 0 - rB, rB * 2, rB * 2);

        ctx.setFill(Color.BLACK);
        ctx.fillOval(0 - rA, 0 - rA, rA * 2, rA * 2);

        int i = 1;
        do {
            ctx.fillOval((knowX(((rB - rA)), fi * i) - r3 / 2), knowY(((rB - rA )),fi * i)  - r3 / 2, r3, r3);
            i++;
        }while (i < 8);

        if (rA > rB || rA == rB){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Вы вышли за пределы диапазона!");
            alert.showAndWait();
            sliderA.setValue(3.25);
            sliderB.setValue(10);
        }

        ctx.restore(); //Восстановление матрцицы преобразований
    }

    public double knowX(double acr, double fi){
        return acr * Math.cos(Math.toRadians(fi));
    }
    public double knowY(double acr, double fi){
        return acr * Math.sin(Math.toRadians(fi));
    }
}