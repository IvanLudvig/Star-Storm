package ru.ivanludvig.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonTweeen implements TweenAccessor<Actor> {
	
    public static final int OPACITY = 5;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
		    case OPACITY: returnValues[0] = target.getColor().a; return 1;
		
		    default: assert false; return -1;
        }
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
		    case OPACITY:
                Color c = target.getColor();
                c.set(c.r, c.g, c.b, newValues[0]);
                target.setColor(c);
		    default: assert false;
        }
		
	}

}
