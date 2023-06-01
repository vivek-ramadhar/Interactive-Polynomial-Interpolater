import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CartesianPlot extends Application {

    Plot plot;
    Axes axes;
    double xMax;
    double yMax;
    List<Double> xDataList;
    List<Double> yDataList;
    double[] xData;
    double[] yData;
    double[] domain;
    PolynomialFit poly;

    int width;
    int height;

    Text eqText;

    @Override
    public void start(final Stage stage) {
        // Initializes values
        xMax = 8;
        width = 1200;
        height = 900;

        poly = null;
        xData = null;
        yData = null;
        xDataList = new ArrayList<Double>();
        yDataList = new ArrayList<Double>();

        domain = null;
        domain = linspace(-1*xMax, xMax, 200);

        yMax = 6; 
        
        axes = new Axes(
                width, height, 50,
                -1*xMax, xMax, 1,
                -1*yMax, yMax, 1
        );

        // Create and set button position
        Button b = new Button("Clear");
        b.setPrefWidth(100);
        b.setTranslateX(-width/2 + 50);
        b.setTranslateY(-height/2 + 10);

        // Create and set Text position, font, and color
        eqText = new Text();
        eqText.setTranslateX(0);
        eqText.setTranslateY(height/2 - 4);
        eqText.setFill(Color.WHITE);
        eqText.setFont(Font.font("Verdana", 12));

        // Create empty graph 
        plot = new Plot(poly, domain, xData, yData, -1*xMax, xMax, 0.1, axes);
        StackPane layout = new StackPane(plot, b, eqText);

        
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: rgb(35, 39, 50);");
        
        axes.setOnMousePressed(new MousPressEventHandler());
        b.setOnAction(new ButtonEventHandler());

        stage.setTitle("Test Test");
        stage.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        stage.show();
    }

    // Clears Plot and Axes objects of children on button click
    private class ButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            // Removes drawn curve from Plot object
            plot.drawCurve(null);
            // Removes drawn points from Axes object
            axes.getChildren().removeIf(Circle.class::isInstance);
            
        }
        
    }

    private class MousPressEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            // Draws a point at left click position
            if(e.getButton() == MouseButton.PRIMARY) {
                if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    // Gets mouse click coordinates
                    double x = e.getX();
                    double y = e.getY();
                    // Draws point on Axes object
                    Circle point = plot.drawPoint(x, y);
                    axes.getChildren().add(point);
                    // Adds point coordinates to observed points list
                    xDataList.add(axesToPlotX(x));
                    yDataList.add(axesToPlotY(y));
                }
            }
            
            // Draws polynomial fitted to observed points on right click
            if(e.getButton() == MouseButton.SECONDARY) {
                // Converts List object to Array object
                xData = xDataList.stream().mapToDouble(d -> d).toArray();
                yData = yDataList.stream().mapToDouble(d -> d).toArray();
                // Creates polynomial object and draws the curve
                poly = new PolynomialFit(xData.length - 1);
                poly.fit(xData, yData);
                plot.polynomialFit = poly;
                plot.drawCurve(domain);

                
                // resets observed points list
                xDataList.clear();
                yDataList.clear();

                double[] coefs = poly.getCoef();
                // int finVal = coefs.length - 1;
                String eq = "";
                String sign;

                for (int i = 0; i < coefs.length; i++) {
                    if (i == coefs.length - 1) {
                        eq += String.format("%.3f", coefs[i]) + String.format("x^%o", i);
                    } 
                    else {
                        sign = coefs[i+1] < 0 ? "" : "+";
                        if(i == 0) {
                            eq += String.format("%.3f",coefs[i]) + sign;
                        } 
                        else if (i == 1) {
                            eq += String.format("%.3f", coefs[i]) + "x" + sign;
                        } 
                        else {
                            eq += String.format("%.3f", coefs[i]) + String.format("x^%o", i) + sign;
                        }
                    }
                }
                // System.out.println(eq);
                eqText.setText("f(x) = " + eq);
            
        }
        
        }
    }

    // Converts Axes layout coordinates to cartesian plane axes coordinates
    private double axesToPlotX(double xAxes) {
        double tickPixelWidth = axes.getPrefWidth()/(2*xMax);
        double xPlot = (xAxes - axes.getPrefWidth()/2)/tickPixelWidth;
        return xPlot;
    }

    private double axesToPlotY(double yAxes) {
        double tickPixelWidth = axes.getPrefWidth()/(2*xMax);
        double yPlot = -(yAxes - axes.getPrefHeight()/2)/tickPixelWidth;
        return yPlot;
    }

    // interval starts at a and ends at b with c total points
    private double[] linspace(double start, double end, int numPoints) {
        List<Double> rangeList = new ArrayList<Double>();
        
        double delta = (end-start)/numPoints;

        for (double x = start; x < end; x+=delta) {
            rangeList.add((double)x);
        }

        return rangeList.stream().mapToDouble(d -> d).toArray();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}