package com.AbleApps.FrenchVerbsFree;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class FlipAnimation extends Animation{

@Override
public void initialize(int width, int height, int parentWidth, int parentHeight){
	super.initialize(width, height, parentWidth, parentHeight);
	setDuration(2500);
	setFillAfter(false);
	setInterpolator(new LinearInterpolator());
}
@Override
protected void applyTransformation(float interpolatedTime, Transformation t){
	final Matrix matrix = t.getMatrix();
	matrix.setRotate(90);
}

}