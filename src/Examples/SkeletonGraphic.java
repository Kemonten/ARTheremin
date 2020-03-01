package Examples;

import HelperMethods.helperMethods;
import com.leapmotion.leap.*;
import processing.core.PApplet;
import processing.core.PVector;

public class SkeletonGraphic extends PApplet {
    Controller leap;
    frameListener listener;

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        fullScreen(P3D);
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
        //frameRate(120);
        fill(255);
        stroke(255);
        strokeWeight(12);
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
            Hand hand = leap.frame().hands().get(0);
            Bone meta = finger.bone(Bone.Type.TYPE_METACARPAL);
            Bone prox = finger.bone(Bone.Type.TYPE_PROXIMAL);
            Bone inter = finger.bone(Bone.Type.TYPE_INTERMEDIATE);
            Bone dist = finger.bone(Bone.Type.TYPE_DISTAL);
            PVector fingerPos = helperMethods.fingerToVector(finger,height);

            line(meta.prevJoint().getX() * 2 + height, height - 2 * meta.prevJoint().getY(), (meta.prevJoint().getZ() - height / 2)/600,
                    prox.prevJoint().getX() * 2 + height, height - 2 * prox.prevJoint().getY(), (prox.prevJoint().getZ() - height / 2)/600);
            line(prox.prevJoint().getX() * 2 + height, height - 2 * prox.prevJoint().getY(), (prox.prevJoint().getZ() - height / 2)/600,
                    inter.prevJoint().getX() * 2 + height, height - 2 * inter.prevJoint().getY(), (inter.prevJoint().getZ() - height / 2)/600);
            line(inter.prevJoint().getX() * 2 + height, height - 2 * inter.prevJoint().getY(), (inter.prevJoint().getZ() - height / 2)/600,
                    dist.prevJoint().getX() * 2 + height, height - 2 * dist.prevJoint().getY(), (dist.prevJoint().getZ() - height / 2)/600);

        }

    }

    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"skeletonGraphic"};
        SkeletonGraphic mySketch = new SkeletonGraphic();
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
