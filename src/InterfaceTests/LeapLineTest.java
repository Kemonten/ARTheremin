package InterfaceTests;

import HelperMethods.helperMethods;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Listener;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class LeapLineTest extends PApplet {


    Controller leap;
    ArrayList<PVector> lines;
    final int LINE_LENGTH = 200;
    frameListener listener;
    int [] rgb;
    int counter;
    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        fullScreen();
    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    public void setup() {
        frameRate(90);
        leap = new Controller();
        listener = new frameListener();
        leap.addListener(listener);
        background(100);
        fill(255);
        stroke(255);
        rgb = new int[]{0,0,0};
        lines = new ArrayList<PVector>();
        //strokeWeight(12);
        counter = 0;
    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        background(100);
        rgbCounter(rgb);

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
        fill(255);
        for (int i = lines.size() - 1, j = i - 1; i >= 1; i--, j--) {
            PVector point = lines.get(i);
            PVector prePoint = lines.get(i);
            PVector postPoint = lines.get(j);
            if (i < 12) {
                fill(rgb[0], rgb[1], rgb[2]);
                stroke(0);
                ellipse(point.x, point.y, 50 * point.z , 50 * point.z);
            } else {
                fill(255);
                stroke(255);
                ellipse(point.x, point.y, 30 * point.z * (lines.size() - i) / lines.size(),
                                        30 * point.z*(lines.size() - i) / lines.size());
            }
            //ellipse(point.x, point.y, 200 * point.z/width,200 * point.z/width);

            //line(prePoint.x, prePoint.y, postPoint.x, postPoint.y);
        }
    }


    public void stop() {
        leap.setPaused(true);
    }

    private void rgbCounter(int[] rgb){

        counter += 0;

        if(rgb[2] < rgb[1] + 100){
            rgb[2] += 1;
        }
        else if(rgb[1] +100 < rgb[0] ){
            rgb[1]+= 1;
        }
        else{
            rgb[0] += 1;
        }
        for(int i = 0; i< rgb.length; i++){
            if(rgb[i] > 255){
                rgb[i] = 0;
            }
        }
    }

    public static void main(String[] args) {
        String[] processingArgs = {"LeapLineTest"};
        LeapLineTest mySketch = new LeapLineTest();
        PApplet.runSketch(processingArgs, mySketch);

    }

    // event listener subclass
    public class frameListener extends Listener {
        public void onFrame(Controller controller) {
            super.onFrame(controller);
        }
    }
}
