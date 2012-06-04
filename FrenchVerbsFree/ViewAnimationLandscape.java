package com.AbleApps.FrenchVerbsFree;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class ViewAnimationLandscape extends Animation{

@Override
public void initialize(int width, int height, int parentWidth, int parentHeight){
	super.initialize(width, height, parentWidth, parentHeight);
	setDuration(5000);
	setFillAfter(true);
	setInterpolator(new LinearInterpolator());
}
@Override
protected void applyTransformation(float interpolatedTime, Transformation t){
	final Matrix matrix = t.getMatrix();
	matrix.setTranslate(-340 * interpolatedTime, 0);
	matrix.preTranslate(100, -28);
}

}

