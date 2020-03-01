package Examples;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SinOsc;

//OscP5 oscP5;//osc
//NetAddress myRemoteLocation;//osc

public class HarmonyMelody extends PApplet {

    // declare we have a leap plugged in
    Controller leap;
    final int numFingers = 5; // 5 fingers for 1 hand, 10 fingers for 2
    final int numHands = 2;
    // create an array of sinWaves, 1 for each finger
    SinOsc[] leftFingers = new SinOsc[numFingers];
    SinOsc[] rightFingers = new SinOsc[numFingers];
    frameListener listener;

    OscP5 oscP5 = new OscP5(this, 11000);//osc
    NetAddress myRemoteLocation = new NetAddress("100.90.226.138", 11000);//osc

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
            leftFingers[i] = new SinOsc(this);
            rightFingers[i] = new SinOsc(this);
            leftFingers[i].amp(.2f); // dear god don't make this too loud
            rightFingers[i].amp(.2f); // dear god don't make this too loud
        }

    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(0); // potentially, make this color change with frequencies, same with fill

        // check if either hand lost fingers
        for (Hand hand : leap.frame().hands()) {
            // if it was left, turn of the left missing fingers
            if (hand.isLeft()) {
                for (int i = 0; i < numFingers; i++) {
                    if (i >= hand.fingers().count()) {
                        leftFingers[i].stop();
                    }
                }
            } else {
                for (int i = 0; i < numFingers; i++) {
                    if (i >= hand.fingers().count()) {
                        rightFingers[i].stop();
                    }
                }
            }
        }

        //for each hand in this frame,
        for (Hand hand : leap.frame().hands()) {
            // if it was left,
            if (hand.isLeft()) {
                for (int i = 0; i < hand.fingers().count(); i++) {
                    PVector fingerPos = helperMethods.fingerToVector(hand.fingers().get(i), height);
                    ellipse(fingerPos.x, fingerPos.y, 10, 10);
                    fingerToSound(leftFingers[i], fingerPos, true);

                }
            } else {
                for (int i = 0; i < hand.fingers().count(); i++) {
                    PVector fingerPos = helperMethods.fingerToVector(hand.fingers().get(i), height);
                    ellipse(fingerPos.x, fingerPos.y, 10, 10);
                    fingerToSound(rightFingers[i], fingerPos, false);
                }
            }
        }
    }

    public void fingerToSound(SinOsc leftFinger, PVector fingerPos, boolean isLeft) {
        // convert position of the finger into frequency and pan
        float freq;
        if (isLeft) {
            freq = map(fingerPos.y, 0, height, 440, 110);
        } else {
            freq = map(fingerPos.y, 0, height, 3520, 880);
        }

        float pan = map(fingerPos.x, 0, width, -.5f, .5f);

        leftFinger.freq(freq);
        leftFinger.pan(pan);
        leftFinger.play();
    }

    public void stop() {
        leap.setPaused(true);

    }

    public static void main(String[] args) {
        String[] processingArgs = {"HarmonyMelody"};
        HarmonyMelody mySketch = new HarmonyMelody();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }

}
