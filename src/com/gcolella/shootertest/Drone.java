package com.gcolella.shootertest;

public class Drone extends TrackingObject {
	int life = 2000;
	int cooldown = 5;
	int countdown = 0;
	int carrierID;
	float fireangle = (float)Math.toRadians(15.0);
	public Drone(Location newloc, float health, Vector newvelo,
			Universe theverse, Team team, int carrierID) {
		super(newloc, health, newvelo, theverse, team);
		this.carrierID = carrierID;
		// TODO Auto-generated constructor stub
	}
	public boolean targetable(UniverseObject obj) {
	return (!(obj instanceof DamageObject)) && !(teammate(obj));
	}
	@Override
	public int maxSecondary() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void fire(){
		Vector fireVelo = new Vector(getVelocity().getAngle(),10);
		new DroneBullet( getLocation(),10, fireVelo, getUniverse(),getTeam(), 10,IDnum,carrierID);
	}
	@Override
	public float getHitRadius() {
		// TODO Auto-generated method stub
		return 20;
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
		life--;
		if(life<=0)
			removeMe();
	}
	@Override
	public int getBmpID() {
		// TODO Auto-generated method stub
		return R.drawable.drone;
	}

}
