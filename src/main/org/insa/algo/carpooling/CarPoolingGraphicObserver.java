package org.insa.algo.carpooling;

import java.awt.Color;

import org.insa.graph.Node;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.overlays.PointSetOverlay;

public class CarPoolingGraphicObserver implements CarPoolingObserver {

    // Drawing and Graph drawing
    protected Drawing drawing;
    protected PointSetOverlay psOverlay1, psOverlay2, psOverlay3, psOverlay4;

    public CarPoolingGraphicObserver(Drawing drawing) {
        this.drawing = drawing;
        psOverlay1 = drawing.createPointSetOverlay(1, Color.CYAN);
        psOverlay2 = drawing.createPointSetOverlay(1, Color.BLUE);
        psOverlay3 = drawing.createPointSetOverlay(1, Color.YELLOW);
        psOverlay4 = drawing.createPointSetOverlay(1, Color.RED);
    }

    @Override
    public void notifyOriginCarProcessed(Node node) {
        // drawing.drawMarker(node.getPoint(), Color.RED);
    }
    
    @Override
    public void notifyOriginPedestrianProcessed(Node node) {
        // drawing.drawMarker(node.getPoint(), Color.RED);
    }

    @Override
    public void notifyNodeReached(Node node) {
        psOverlay1.addPoint(node.getPoint());
    }
    
    @Override
    public void notifyNodeMarked(Node node) {
        psOverlay2.addPoint(node.getPoint());
    }
    
    @Override
    public void notifyNodeMarked2(Node node) {
        psOverlay3.addPoint(node.getPoint());
    }
    
    @Override
    public void notifyNodeMarked3(Node node) {
        psOverlay4.addPoint(node.getPoint());
    }

    @Override
    public void notifyDestinationReached(Node node) {
        // drawing.drawMarker(node.getPoint(), Color.RED);
    }

}
