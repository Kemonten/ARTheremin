package InterfaceTests;

import processing.core.PApplet;
import processing.sound.SinOsc;
import processing.sound.Sound;

// testing how to play sounds on the app, can use input( mouse ) onto app to change volume
public class SoundTest extends PApplet {
    //Global variables to be used between functions
    Sound s;

    /*
     * Called before setup, for pre-window necessities
     */
    public void settings() {
        size(200, 200);

    }

    /*
     * Called Before Draw
     * Called only once, sets up window
     */
    public void setup() {

        // Play two sine oscillators with slightly different frequencies for a nice "beat".
        SinOsc sin = new SinOsc(this);
        sin.play(400, 0.2f);
        sin = new SinOsc(this);
        sin.play(405, 0.2f);

        // Create a Sound object for globally controlling the output volume.
        s = new Sound(this);

    }

    /*
     * Called every frame
     * Where measurements/main calculations are made
     */
    public void draw() {
        // Map vertical mouse position to volume.
        float amplitude = map(mouseY, 0, height, 0.4f, 0.0f);

        // Instead of setting the volume for every oscillator individually, we can just
        // control the overall output volume of the whole Sound library.
        s.volume(amplitude);


    }

    // basic structure for every main method
    public static void main(String[] args) {
        String[] processingArgs = {"SoundTest"};
        SoundTest mySketch = new SoundTest();
        PApplet.runSketch(processingArgs, mySketch);

    }
}
