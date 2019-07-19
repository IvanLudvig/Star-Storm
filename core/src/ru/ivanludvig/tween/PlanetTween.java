package ru.ivanludvig.tween;

import ru.ivanludvig.planets.Planet;
import aurelienribon.tweenengine.TweenAccessor;

public class PlanetTween implements TweenAccessor<Planet>{

	public static final int angle = 1;

	@Override
	public int getValues(Planet target, int tweenType, float[] returnValues) {
		switch (tweenType){
		case angle:
			returnValues[0] = target.angle; 
			return 1;
        default: 
        	assert false; 
        	return -1;
		}
	}

	@Override
	public void setValues(Planet target, int tweenType, float[] newValues) {
		switch (tweenType){
		case angle:
			target.angle = newValues[0];
			break;
        default: 
        	assert false; 
		}
	}
}
