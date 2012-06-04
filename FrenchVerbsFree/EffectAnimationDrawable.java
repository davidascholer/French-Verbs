package com.AbleApps.FrenchVerbsFree;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

public class EffectAnimationDrawable extends AnimationDrawable {

	private Runnable callback = null;
	private int current_frame = -1;
	private int repeats = 0;
	private Handler handler;
	
	
	public void setCallback(Runnable r, Handler h)	{
		this.callback = r;
		this.handler = h;
	}
	
	public void setRepeatNumber(int repeat)
	{
		this.repeats = repeat;
		this.setOneShot(false);
	}
	
	@Override
	public void run(){
		
		super.run();
		
		this.current_frame++;
		
		if(this.current_frame == this.getNumberOfFrames() -1 && this.repeats > 0 && !this.isOneShot())
		{
			this.repeats --;
			if(this.repeats == 0)
			{
				this.setOneShot(true);
			}
		}
		
		if(this.current_frame == this.getNumberOfFrames() -1 && this.callback != null && this.handler != null && this.isOneShot())
		{
			
			final int d = this.getDuration(this.current_frame);
			this.handler.postDelayed(this.callback, d);
		}
		
		
	}
	
	@Override
	public void unscheduleSelf(Runnable r){
		super.unscheduleSelf(r);
		
		this.current_frame = -1;
	}
	
	public int getDurration()
	{
		int durration = 0;
		
		if(this.getNumberOfFrames() > 0)
		{
			for(int i = 0;i<this.getNumberOfFrames();i++)
			{
				durration += this.getDuration(i);
			}
		}
		
		return durration;
	}
}
