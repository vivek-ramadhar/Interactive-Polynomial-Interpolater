import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;

class Axes extends Pane {
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    public Axes(int width, int height, int offset, double xLow, double xHi, double xTickUnit, double yLow, double yHi, double yTickUnit) {
        // initialize Pane object display size properties
        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(width, height);
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        // create new variables for axes width, independent of Pane size
        int newWidth = width - offset;
        int newHeight = height - offset;

        // Initialize x-Axis NumberAxis object
        xAxis = new NumberAxis(xLow, xHi, xTickUnit);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(newWidth);

        // centers x-axis's y position
        xAxis.setLayoutY(height/2);
        // centers x-axis's x position
        xAxis.setLayoutX((width - newWidth)/2);

        // Initialize y-Axis NumberAxis object
        yAxis = new NumberAxis(yLow, yHi, yTickUnit);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);
        yAxis.setPrefHeight(newHeight);
        
        // centers y-axis x position
        yAxis.layoutXProperty().bind(
            Bindings.subtract(
                (width / 2) + 1,
                yAxis.widthProperty()
            )
        );
        //centers y-axis's y position
        yAxis.setLayoutY((height - newHeight)/2);

        getChildren().setAll(xAxis, yAxis);
    }

    public NumberAxis getXAxis() {
        return xAxis;
    }

    public NumberAxis getYAxis() {
        return yAxis;
    }
}
