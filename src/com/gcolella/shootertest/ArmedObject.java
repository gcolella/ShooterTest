package com.gcolella.shootertest;

import android.util.Log;

public abstract class ArmedObject extends UniverseObject {
	int secondary=0;
	int[] ammo = {0,0,0,0,0};
	private UniverseObject currentTarget;
	
	public UniverseObject getCurrentTarget(){
		return currentTarget;
	}
	public void setTarget(UniverseObject obj){
		if(obj==null && this instanceof ControlledShip)
			Log.e("OUTPUTSHOOTER","NULLIN");
		currentTarget = obj;
	}
	public ArmedObject(Location newloc, float health, Vector newvelo,
			Universe theverse, Team team) {
		super(newloc, health, newvelo, theverse,team);
	}
	public void setAmmo(int[] i)
	{
		ammo = i;
	}
	public void addAmmo(int i,int n)
	{
		ammo[i]+=n;
	}
	public boolean hasTarget()
	{
		return currentTarget != null;
	}
	public String getSecondaryName(){
		switch(secondary){
		case 0: return "Off";
		case 1: return "Missile";
		case 2: return "Jammer";
		case 3: return "Mine";
		case 4: return "Drone";
		case 5: return "Forcefield";
		}
		return "ERR";
	}
	public void fireSecondary(){
		if(secondary==0)
			return;
		if(ammo[secondary-1]<1){
			return;
		}
		ammo[secondary-1]--;
		switch(secondary){
		case 1:fireMissile();
			   break;
		case 2:fireJammer();
			   break;
		case 3:fireMine();
			   break;
		case 4:releaseDrone();
			   break;
		case 5:deployForceField();
			   break;
		}
	}
	public void switchSecondary(){
	do{
		secondary++;
		if(secondary>maxSecondary()){
			secondary = 0;
			return;
		}
	}while(ammo[secondary-1]<=0);
	}
	public abstract int maxSecondary();
	public void fireMissile()
	{
		if(hasTarget())
			new Missile(getLocation(),40,new Vector(getVelocity().getAngle(),5),getUniverse(),50,IDnum, getTeam(),getCurrentTarget());
		else
			ammo[0]++;
	}
	public void fire(float angle)
	{
		Vector fireVelo = new Vector(angle,10);
		new Bullet( getLocation(),10, fireVelo, getUniverse(),Team.AGGRO, 10,IDnum);
	}
	public void fire()
	{
		fire(getVelocity().getAngle());
	}
	public void fireJammer()
	{
		new Jammer(getLocation(),10,new Vector((float)(Math.random()*2*Math.PI),.5f),getUniverse(), getTeam(),IDnum);
	}
	public void fireMine()
	{
		new Mine(getLocation(),40,new Vector(0,0),getUniverse(),getTeam(),100, IDnum);
	}
	public void releaseDrone()
	{
		new Drone(getLocation().offset((float)(Math.random()*50)-25, (float)(Math.random()*50)-25),100,new Vector(getVelocity().getAngle(),3),getUniverse(),getTeam(),IDnum);
	}
	public void deployForceField()
	{
		new ForceField(getLocation(),200, new Vector(0,0),getUniverse(),getTeam(),IDnum);
	}


}
