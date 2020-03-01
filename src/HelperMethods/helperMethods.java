package HelperMethods;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import processing.core.PApplet;
import processing.core.PVector;

public class helperMethods extends PApplet {

    public static PVector fingerToVector(Finger finger, int height) {
        return new PVector(finger.tipPosition().getX() * 2.0f + height,
                height - 2 * finger.tipPosition().getY(),
                (finger.tipPosition().getZ() - (height / 2.0f))/600);

    }

    public static PVector handToVector(Hand hand) {

        return new PVector(hand.palmPosition().getX(),
                hand.palmPosition().getY(),
                hand.palmPosition().getZ());
    }

}
