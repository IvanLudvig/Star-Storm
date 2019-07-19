package ru.ivanludvig.tween;

import ru.ivanludvig.ship.Sputnik;
import aurelienribon.tweenengine.TweenAccessor;

public class SputnikTween implements TweenAccessor<Sputnik>{

	public static final int speed = 1;

	@Override
	public int getValues(Sputnik target, int tweenType, float[] returnValues) {
		switch (tweenType){
		case speed:
			returnValues[0] = target.cspeed; 
			return 1;
        default: 
        	assert false; 
        	return -1;
		}
	}

	@Override
	public void setValues(Sputnik target, int tweenType, float[] newValues) {
		switch (tweenType){
		case speed:
			target.cspeed = newValues[0];
			break;
        default: 
        	assert false; 
		}
	}
}
