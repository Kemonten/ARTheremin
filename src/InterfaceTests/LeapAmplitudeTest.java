package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SinOsc;
import processing.sound.Sound;


// Using hand instead of finger
// lets user manipulate the volume of a pre-existing sound
public class LeapAmplitudeTest extends PApplet {
    //Global variables to be used between functions
    Controller leap;
    Sound s;
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

        // Play two sine oscillators with slightly different frequencies for a nice "beat".
        SinOsc sin = new SinOsc(this);
        sin.play(200, 0.2f);
        sin = new SinOsc(this);
        sin.play(205, 0.2f);

        // Create a Sound object for globally controlling the output volume.
        s = new Sound(this);
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
            // Map vertical mouse position to volume.
            float amplitude = map(handPos.y, 0, height, 0.8f, 0.0f);

            // Instead of setting the volume for every oscillator individually, we can just
            // control the overall output volume of the whole Sound library.
            s.volume(amplitude);

        }
    }

    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"LeapAmplitudeTest"};
        LeapAmplitudeTest mySketch = new LeapAmplitudeTest();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }
}
