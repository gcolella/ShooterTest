package com.gcolella.shootertest;

import android.util.Log;

@SuppressWarnings("unused")
public abstract class TrackingObject extends ArmedObject {

	protected float turnspeed = (float)Math.toRadians(1);
	private Location dest;
	
	public TrackingObject( Location newloc, float health, Vector newvelo, Universe theverse, Team team) {
		super(newloc, health, newvelo,theverse, team);
		getNewDest();
		// TODO Auto-generated constructor stub
	}
	public  void acquireTarget(){
		//Log.e("OUTPUTSHOOTER", "RETARGETING");
		for(UniverseObject obj:getUniverse().getStuff())
			if(targetable(obj)){
				setTarget(obj);
				return;
			}
		setTarget(null);
		//Log.e("OUTPUTSHOOTER",currentTarget+"");
	}

	public abstract boolean targetable(UniverseObject obj);
	public void setTurnSpeed(float tspd){
		turnspeed = tspd;
	}
	public float targetAngle(){
		Location targetloc = getCurrentTarget().getLocation();
		Location tracktargetloc = new Location(targetloc.getX()+(getCurrentTarget().getVelocity().getiComponent()*2),targetloc.getY()+(getCurrentTarget().getVelocity().getjComponent()*2));
		return getLocation().angleTo(tracktargetloc); 
	}

	public void turnTowards(Location loc){
		float angle = getLocation().angleTo(loc);
		//getVelocity().setAngle(angle);
		float myAngle = getVelocity().getAngle();
		if(Math.abs(angle-myAngle)>turnspeed){
		if(angle>myAngle)
			turn(turnspeed);
		if(angle<myAngle)
			turn(-1*turnspeed);
		}else{
			getVelocity().setAngle(angle);
		}
	}
	public void update(){
		if(getCurrentTarget() != null){
			turnTowards(getCurrentTarget().getLocation());
		}else{acquireTarget();}
		if(!hasTarget()){
			turnTowards(dest);
			if(haveArrived())
				getNewDest();
		}
		super.update();
	}
	public void getNewDest(){
		dest = Location.randomLocation(getUniverse().getBoundX()-40,getUniverse().getBoundY()-40);
		
	}
	public boolean haveArrived(){
		return getLocation().distanceTo(dest) < 20;
	}


}
