import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;

class Plot extends Pane {
    Axes plotAxes;
    Path path;
    public PolynomialFit polynomialFit;

    public Plot(PolynomialFit poly, double[] domain, double[] xs, double[] ys, double xMin, double xMax, double xInc, Axes axes) {
        // Initializes values
        plotAxes = axes;
        polynomialFit = poly;
    
        path = new Path();
        path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
        path.setStrokeWidth(2);

        path.setClip(
                new Rectangle(
                        0, 0, 
                        axes.getPrefWidth(), 
                        axes.getPrefHeight()
                )
        );

        setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
        setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        getChildren().setAll(axes, path);
    }

    // Convert Axes object coordinates to cartesian axes coordinates
    public double mapX(double x, Axes axes) {
        double tx = axes.getPrefWidth() / 2;
        double sx = axes.getPrefWidth() / 
           (axes.getXAxis().getUpperBound() - 
            axes.getXAxis().getLowerBound());

        return x * sx + tx;
    }

    public double mapY(double y, Axes axes) {
        double ty = axes.getPrefHeight() / 2;
        double sy = axes.getPrefHeight() / 
            (axes.getYAxis().getUpperBound() - 
             axes.getYAxis().getLowerBound());

        return -y * sy + ty;
    }

    // Creates a circle object at specified x and y cartesian axes coordinate
    public Circle drawPoint(double x, double y) {
        Circle point = new Circle(x, y, 2);
        point.setFill(Color.RED);
        point.setStroke(Color.RED);
        return point;
    }

    private Circle[] drawObsPoints(double[] xs, double[] ys, Axes axes) {
        Circle[] obsPoints  = new Circle[xs.length];
        for(int i = 0; i < xs.length; i++) {
            double x = xs[i];
            double y = ys[i];
            obsPoints[i] = drawPoint(mapX(x, axes), mapY(y, axes));
        }

        return obsPoints;
    }

    // This method handles drawing polynomial on cartesian axes
    public void drawCurve(double[] domain) {
        // If domain is passed in it draws the curve for each point in the domain 
        if (domain != null) {
            path.getElements().add(
                    new MoveTo(
                            mapX(domain[0], plotAxes), mapY(polynomialFit.compute(domain[0]), plotAxes)
                    )
            );
    
            for(double xDomain : domain) {
                // y = polynomialFit.compute(x);
    
                path.getElements().add(
                        new LineTo(
                                mapX(xDomain, plotAxes), mapY(polynomialFit.compute(xDomain), plotAxes)
                                )
                );
            }
        // If domain is null it resets the drawn path object
        } else {
            path.getElements().clear();

        }
    }

}