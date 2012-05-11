package com.gcolella.shootertest;
import java.util.*;


import android.content.Context;
import android.util.Log;
@SuppressWarnings("unused")
public class Universe {
private int xbound,ybound;
Context mycontxt;
Level mylevel=null;
ArrayList<UniverseObject> stuffInUniverse;
boolean isCopying = false;
public Universe(int xbound, int ybound,Context ctxt)
{
	stuffInUniverse = new ArrayList<UniverseObject>();
	this.xbound = xbound;
	this.ybound = ybound;
	mycontxt = ctxt;
}
public Context getContext(){
	return mycontxt;
}
public boolean hasLevel(){
	return !(mylevel==null);
}
public void setLevel(Level l){
	mylevel = l;
}
public int shipNumber(){
	int count = 0;
	List<UniverseObject> stuff = getStuff();
	for(UniverseObject ship:stuff){
		if(!(ship instanceof DamageObject))
			count++;
	}
	return count;
}
public int getBoundX(){
	return xbound;
}
public int getBoundY(){
	return ybound;
}
public void setYbound(int ybnd){
	ybound = ybnd;
}
public void add(UniverseObject newobj)
{
	stuffInUniverse.add(newobj);
}
public void updateOne(UniverseObject thing){
	thing.update();
	doHits(thing);
	clearDead(thing);
	
}
public void updateLevel(){
	if(hasLevel())
		mylevel.updateLevel();
}

@Deprecated
public void updateAll(){
	if(hasLevel())
		mylevel.updateLevel();
	for(UniverseObject thing:getStuff()){
		thing.update();
		doHits(thing);
		clearDead(thing);
		
	}
}
public void initAll(){
	for(UniverseObject thing:getStuff())
		thing.init();
}

public int onTeam(Team t){
int ct=0;
for(UniverseObject thing:getStuff()){
	if(thing == null)
		continue;
	if(thing.getTeam().same(t) && !(thing instanceof DamageObject))
		ct++;
}
return ct;
}



@SuppressWarnings("unchecked")
public ArrayList<UniverseObject> getStuff(){
	return (ArrayList<UniverseObject>)stuffInUniverse.clone();
}
public  void doHits(UniverseObject ship){
	List<UniverseObject> stuff = getStuff();
		for(UniverseObject thing:stuff){
			
			if(thing instanceof Powerup){
				if(ship instanceof ControlledShip){
					if(colliding(ship,thing)){
						((Powerup)thing).applyPowerup((ControlledShip)ship);
						thing.removeMe();
					}
				}
			}
			
			
			if(!(thing instanceof DamageObject))
				continue;
			DamageObject damager = (DamageObject)thing;
			if(ship instanceof DamageObject){
				DamageObject shipdmg = (DamageObject)ship;
				if(shipdmg.getCreatorID() == damager.getCreatorID())
					continue;
			}
			if(!damager.targetable(ship))
				continue;
			if(colliding(ship,thing) && damager.getCreatorID()!=ship.IDnum){
				if(damager.getExplosionRadius()==-1){
				ship.damage(damager.getDamage());}
				else{
					new Explosion(thing.getLocation(),damager.getExplosionRadius(),damager.getDamage(),this,Team.AGGRO,damager.getCreatorID());
				}
				//Log.e("OUTPUTSHOOTER","HITTINGGG: "+thing.IDnum);
				if(!damager.penetration())
					thing.removeMe();
			if(ship.getHealth()<=0 && !(ship instanceof DamageObject)){
				UniverseObject killer = findID(damager.getCreatorID());
				if(killer != null){
					if(!(killer.teammate(ship)))
						killer.addScore(1);	
				}
			}
		}
	}
		
}
public ControlledShip spawnPlayer(){
	if(!hasLevel())
		return new BasicPlayer(new Location(250,250), 200, new Vector(0,4),this,Team.GREEN);
	return mylevel.spawnPlayer();
}
private boolean colliding(UniverseObject thgone, UniverseObject thgtwo) {
	float between = thgone.getLocation().distanceTo(thgtwo.getLocation());
	return (between < (thgone.getHitRadius()+thgtwo.getHitRadius()));
}
public void clearDead(UniverseObject ship){
		if(ship.getHealth()<=0){
			if(ship instanceof DamageObject){
				DamageObject damager = (DamageObject)ship;
				if(damager.getExplosionRadius()!=-1)
					new Explosion(ship.getLocation(),damager.getExplosionRadius(),damager.getDamage(),this,Team.AGGRO,0);
			}
			else{
			spawnPowerup(ship.getLocation());
			new Explosion(ship.getLocation(),ship.getHitRadius(),50,this,Team.AGGRO,0);
			}
			ship.removeMe();}
}
public void deTarget(int ID){
	List<UniverseObject> stuff = getStuff();
	for(UniverseObject ship:stuff){
		if(ship instanceof ArmedObject){
			ship = (ArmedObject)ship;
			if(((((ArmedObject) ship).hasTarget()) && ((ArmedObject) ship).getCurrentTarget().IDnum == ID))
				((ArmedObject) ship).setTarget(null);
		}
	}
}
public void spawnShip(Team t){
	int type = (int)(Math.random()*5);
	if(type!=3)
		new EnemyShip(Location.randomLocation(this.getBoundX()-50, this.getBoundY()-50),100, new Vector(0,1),this,t);
	else
		new MineLayer(Location.randomLocation(this.getBoundX()-50, this.getBoundY()-50),100,new Vector(0,1),this,t);
	
}
public void spawnPowerup(Location l){
	int type = (int)(Math.random()*100);
	if(type>50){
		new HealthPowerup(l,150, new Vector((float) (Math.random()*Math.PI*2),0),this,Team.FRIEND);
		return;
	}
	if(type>25){
		new MysteryPowerup(l,150,new Vector((float) (Math.random()*Math.PI*2),0),this,Team.FRIEND);
		return;
	}
	if(type>10){
		new MissilePowerup(l,150,new Vector((float) (Math.random()*Math.PI*2),0),this,Team.FRIEND);
	}
	
}
public UniverseObject objAt(Location l){
	for(UniverseObject thing:getStuff()){
		if(thing.getLocation().distanceTo(l)<thing.getHitRadius()){
			return thing;
		}
	}
	return null;
}
public  UniverseObject findID(int searchID){
	List<UniverseObject> stuff = getStuff();
	for(UniverseObject thing:stuff)
		if(thing.IDnum == searchID)
			return thing;
	return null;
}

public void remove(UniverseObject obj){
	deTarget(obj.IDnum);
	stuffInUniverse.remove(obj);
}

public boolean isPresent(UniverseObject obj){
	List<UniverseObject> stuff = getStuff();
	return stuff.contains(obj);
}

	
}
