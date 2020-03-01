package Examples;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import netP5.NetAddress;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

public class DataTransfer extends PApplet {

    // declare we have a leap plugged in
    Controller leap;
    final int numFingers = 5; // 5 fingers for 1 hand, 10 fingers for 2
    final int numHands = 2;
    // create an array of sinWaves, 1 for each finger
    DataTransfer.frameListener listener;

    OscP5 oscP5 = new OscP5(this, 11000);//osc
    NetAddress myRemoteLocation = new NetAddress("100.90.226.138", 11000);//osc

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        size(0, 0);
    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    public void setup() {
        background(0);
        frameRate(120);
        fill(255);
        leap = new Controller();
        listener = new DataTransfer.frameListener();
        leap.addListener(listener);

    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(0); // potentially, make this color change with frequencies, same with fill

        //for each hand in this frame,

        HandList hands = leap.frame().hands();

        if (hands.count() == 0) {
            handSendNull("/left");
            handSendNull("right");
        } else if (leap.frame().hands().count() == 1) {
            if (hands.get(0).isLeft()) {
                handSendMessage(hands.get(0), "/left");
                handSendNull("/right");
            } else {
                handSendMessage(hands.get(0), "/right");
                handSendNull("/left");
            }
        } else {
            for (Hand hand : leap.frame().hands()) {
                // if it was left,
                if (hand.isLeft()) {
                    handSendMessage(hand, "/left");
                } else {
                    handSendMessage(hand, "/right");
                }
            }
        }
    }


    public void handSendMessage(Hand hand, String s) {
        OscMessage message = new OscMessage(s);
        message.add(hand.palmPosition().toFloatArray());
        //rotation
        hand.rotationAxis(leap.frame(1));
        message.add(hand.palmNormal().toFloatArray());
        //fingers
        float[][] fingers = new float[numFingers][3];
        for (int i = 0; i < numFingers; i++) {
            fingers[i] = helperMethods.fingerToVector(hand.fingers().get(i), height).array();
        }
        message.add(fingers);
        oscP5.send(message, myRemoteLocation);
    }

    public void handSendNull(String s) {
        OscMessage message = new OscMessage(s);
        float[] empty = {0, 0, 0};
        for (int i = 0; i < 7; i++) {
            message.add(empty);
        }
        oscP5.send(message, myRemoteLocation);
    }


    public void stop() {
        leap.setPaused(true);

    }

    public static void main(String[] args) {
        String[] processingArgs = {"DataTransfer"};
        DataTransfer mySketch = new DataTransfer();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }

}
