package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.LowPass;
import processing.sound.WhiteNoise;


/*
 * Good for easy implementation of filters on a pre-existing/ loaded sound file, not practical
 * if we want the user to "make" their own sounds
 */
public class LeapFilterTest extends PApplet {

    Controller leap;
    LowPass lowpass;
    frameListener listener;

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        size(800, 800);
    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    public void setup() {
        background(0);
        fill(255);

        leap = new Controller();
        listener = new frameListener();
        leap.addListener(listener);
        //makes some white noise
        WhiteNoise noise = new WhiteNoise(this);
        noise.play();

        // Create a Filter object for hand to filter with.
        lowpass = new LowPass(this);
        lowpass.process(noise, 8000);
    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(0);
        for (Hand hand : leap.frame().hands()) {
            PVector handPos = helperMethods.handToVector(hand);
            ellipse(handPos.x, handPos.y, 20, 20);
            float filter = map(handPos.y, 0, height, 8000, 100);
            lowpass.freq(filter);
        }

    }


    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"LeapFilterTest"};
        LeapFilterTest mySketch = new LeapFilterTest();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }
}
