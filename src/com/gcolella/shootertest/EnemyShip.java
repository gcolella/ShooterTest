package com.gcolella.shootertest;

import android.util.Log;

public class EnemyShip extends TrackingObject {
	int cooldown = 10;
	int countdown = 0;
	float fireangle = (float)Math.toRadians(15.0);

	public EnemyShip(Location newloc, float health,  //constructor w/o a target.
			Vector newvelo, Universe theverse, Team team) {
		super( newloc, health, newvelo, theverse,team);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean targetable(UniverseObject obj) {
	return (!(obj instanceof DamageObject)) && (!(teammate(obj)));
	}
	public int getBmpID() {
		// TODO Auto-generated method stub
		return R.drawable.enemy;
	}
	public void update(){
		super.update();
		if(hasTarget()){
		if(Math.abs(targetAngle()-getVelocity().getAngle())<(fireangle))
		{	
			if(countdown==0){
				fire();
				countdown=cooldown;
			}else{countdown--;}
		}
	}
	}



	@Override
	public float getHitRadius() {
		return 30;
	}

	@Override
	public int maxSecondary() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
