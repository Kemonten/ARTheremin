package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;

//drawing finger positions on the board, because image is in 2d, restricted to writing fingers in 2d
// REMEMBER: you are still receiving and can use data from Z axis
public class LeapTest extends PApplet {
    Controller leap;
    frameListener listener;

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        size(500, 500);

    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    public void setup() {
        leap = new Controller();
        listener = new frameListener();
        leap.addListener(listener);
        background(0);
        fill(255);
    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(0);
        if (leap.frame().fingers().count() == 0) {
            return;
        }
        for (Finger finger : leap.frame().fingers()) {
            PVector fingerPos = helperMethods.fingerToVector(finger, height);
            ellipse(fingerPos.x, fingerPos.y, 10, 10);
        }

    }

    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"LeapTest"};
        LeapTest mySketch = new LeapTest();
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
