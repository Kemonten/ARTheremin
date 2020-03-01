package Examples;

import HelperMethods.helperMethods;
import com.leapmotion.leap.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class ParticleSystemTest extends PApplet{
    Controller leap;
    frameListener listener;
    ArrayList<Particle> plist = new ArrayList<Particle>();
    //ParticleSystem ps = new ParticleSystem(new PVector(width/2,250));


    class Particle {

        PVector location;
        PVector velocity;
        PVector acceleration;
        float lifespan;

        Particle(PVector l) {
            location = l;
            acceleration = new PVector(0,0.0f);
            velocity = new PVector(random(-.5f,.5f), random(-.5f,.5f));
            lifespan = 220;
        }

        void run() {
            update();
            display();
        }

        void update() {
            velocity.add(acceleration);
            location.add(velocity);
            lifespan -= 4;
        }

        void display() {
            stroke(255,0,0, lifespan);

            strokeWeight(1);
            fill(255, lifespan);
            ellipse(location.x,location.y,8,8);

        }

        boolean isDead(){
            return lifespan <= 0;
        }
    }



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
        for(int i = 0; i < plist.size(); i++){
            plist.get(i).run();
            if (plist.get(i).isDead()) {
                plist.remove(i--);

            }
        }
        for (Finger finger : leap.frame().fingers()) {
            PVector fingerPos = helperMethods.fingerToVector(finger, height);
            stroke(220,50,40);
            fill(255,50,40,255);
            ellipse(fingerPos.x, fingerPos.y, 30 * fingerPos.z, 30* fingerPos.z);
            Particle p = new Particle(fingerPos);
            plist.add(p);
        }

        //BONES
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

            strokeWeight(10);
            stroke(255);
            line(meta.prevJoint().getX() * 2 + height, height - 2 * meta.prevJoint().getY(), meta.prevJoint().getZ() - height / 2,
                    prox.prevJoint().getX() * 2 + height, height - 2 * prox.prevJoint().getY(), prox.prevJoint().getZ() - height / 2);
            line(prox.prevJoint().getX() * 2 + height, height - 2 * prox.prevJoint().getY(), prox.prevJoint().getZ() - height / 2,
                    inter.prevJoint().getX() * 2 + height, height - 2 * inter.prevJoint().getY(), inter.prevJoint().getZ() - height / 2);
            line(inter.prevJoint().getX() * 2 + height, height - 2 * inter.prevJoint().getY(), inter.prevJoint().getZ() - height / 2,
                    fingerPos.x, fingerPos.y,fingerPos.z);
        }

    }

    public void stop() {
        leap.setPaused(true);
    }

    public static void main(String[] args) {
        String[] processingArgs = {"ParticleSystemTest"};
        ParticleSystemTest mySketch = new ParticleSystemTest();
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
