package com.gcolella.shootertest;

public class Vector {
	float angle,magnitude;
	public Vector(float nwAngle, float nwMagnitude){
		angle = nwAngle; //ANGLE IS STORED IN RADIANS.
		magnitude = nwMagnitude;
	}
	static Vector randomVector(float mag){
		return new Vector((float) Math.toRadians((Math.random()*360)),mag);
	}
	public Vector(float icomp, float jcomp, boolean b){
		angle = (float) Math.atan2((double)jcomp,(double)icomp);
		magnitude = (float)(Math.sqrt((icomp*icomp)+(jcomp*jcomp)));
	}
	float getAngle(){
		return angle;
	}
	float getMagnitude(){
		return magnitude;
	}
	void setMagnitude(float m){
		magnitude = m;
	}
	void setAngle(float a){
		angle = a;
	}
	float getiComponent(){
		return (float)(magnitude*Math.cos(angle));
	}
	float getjComponent(){
		return (float)(magnitude*Math.sin(angle));
	}
	Vector getUnitVector(){
		return new Vector(angle,1);
	}
	
}
