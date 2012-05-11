package com.gcolella.shootertest;

import android.content.Context;

public class LevelOne extends Level {

	public LevelOne(Context ctx,ShooterTestActivity act) {
		super(ctx,act);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean levelComplete() {
		// TODO Auto-generated method stub
		return false;
	}
	boolean powerups(){
		return true;
	}
	@Override
	boolean levelFailed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	void setup() {
		out = new Universe(x,y,myctx);
		myship = new BasicPlayer(Location.randomLocation(x, y),100, new Vector(0,2), out,Team.GREEN);
		new EnemyCarrier(Location.randomLocation(x,y),800,new Vector(2,2), out, Team.RED);
	}
	String levelText(){
		return "Fight against a huge enemy spacecraft capable of launching smaller drones to assist it.";
	}
	
	
	@Override
	ControlledShip getPlayer() {
		// TODO Auto-generated method stub
		return myship;
	}

}
