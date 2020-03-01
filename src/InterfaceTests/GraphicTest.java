package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class GraphicTest extends PApplet {
    //Global variables to be used between functions
    Controller leap;
    ArrayList<PVector> lines;
    final int LINE_LENGTH = 20;
    frameListener listener;

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        size(500, 500, P3D);
    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    @SuppressWarnings("Duplicates")
    public void setup() {

        frameRate(60);
        leap = new Controller();
        listener = new frameListener();
        leap.addListener(listener);
        background(100);
        specular(10, 120, 200);
        ambient(0, 100, 120);
        emissive(40);
        lines = new ArrayList<PVector>();
    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        // Map vertical mouse position to volume.
        background(100);


        // Instead of setting the volume for every oscillator individually, we can just
        // control the overall output volume of the whole Sound library.

        if (lines.size() >= LINE_LENGTH) {
            lines.remove(lines.size() - 1);
        }
        for (Finger finger : leap.frame().fingers()) {
            PVector fingerPos = helperMethods.fingerToVector(finger, height);
            if (lines.size() >= LINE_LENGTH) {
                lines.remove(lines.size() - 1);
            }
            lines.add(0, fingerPos);

        }
        //fill(255);
        for (int i = lines.size() - 1, j = i - 1, k = j - 1, l = k - 1;
             i >= 3;
             i--, j--, k--, l--) {
            //PVector point = lines.get(i);
            PVector point1 = lines.get(i);
            PVector point2 = lines.get(j);
            PVector point3 = lines.get(k);
            PVector point4 = lines.get(l);
            if (i < 12) {
                fill(255, 50, 50);
                stroke(255, 50, 50);
                //specular(255,50,50);
                //ambient(150,100,100);
                //emissive(150,40,40);
            } else {
                fill(255);
                stroke(255);
                //specular(10,120,200);
                //ambient(0,100,120);
                //emissive(40);
            }
            //translate(point.x,point.y, point.z);
            //curve(point1.x, point1.y, point1.z,
            //        point2.x, point2.y, point2.z,
            //        point3.x, point3.y, point3.z,
            //        point4.x, point4.y, point4.z);

            strokeWeight(10 * point2.z);
            line(point1.x, point1.y, point2.x, point2.y);
        }

    }

    // basic structure for every main method
    public static void main(String[] args) {
        String[] processingArgs = {"GraphicTest"};
        GraphicTest mySketch = new GraphicTest();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            try {
                super.onFrame(controller);
            } catch (Exception e) {
            }
        }
    }

}
