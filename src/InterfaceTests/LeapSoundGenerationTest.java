package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SinOsc;

// Sound generation, using Y axis to manipulate frequencies of multiple sin waves
// X axis for pan/UX, can be used for something else (filter, volume, reverb)
// Z Axis can still be used to apply one of these, not added since we can't see it on screen therfore harder to use
public class LeapSoundGenerationTest extends PApplet {

    // declare we have a leap plugged in
    Controller leap;
    frameListener listener;
    final int numFingers = 5; // 5 fingers for 1 hand, 10 fingers for 2

    // create an array of sinWaves, 1 for each finger
    SinOsc[] sounds = new SinOsc[numFingers];

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
        // initialize each sinOsc so we can mess with the lines in Draw
        for (int i = 0; i < numFingers; i++) {
            sounds[i] = new SinOsc(this);
            sounds[i].amp(.2f); // dear god don't make this too loud
        }
    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(0); // potentially, make this color change with frequencies, same with fill
        FingerList fingerlist = leap.frame().fingers();
        // if in this frame one of the fingers left, stop playing that sound
        for (int i = 0; i < numFingers; i++) {
            if (i >= fingerlist.count()) ;
            sounds[i].stop();
        }

        //for each finger in this frame, first draw it into the picture
        for (int i = 0; i < fingerlist.count() && i < numFingers; i++) {
            Finger finger = fingerlist.get(i);
            PVector fingerPos = helperMethods.fingerToVector(finger, height);
            ellipse(fingerPos.x, fingerPos.y, 10, 10);

            // convert position of the finger into frequency and pan
            float freq = map(fingerPos.y, 0, height, 880, 220);
            float pan = map(fingerPos.x, 0, width, -.5f, .5f);

            // for that finger in the frame, set the freq/pan and play it
            sounds[i].freq(freq);
            sounds[i].pan(pan);
            sounds[i].play();
        }

        leap.removeListener(listener);
    }

    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"LeapFilterTest"};
        LeapSoundGenerationTest mySketch = new LeapSoundGenerationTest();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }

}
