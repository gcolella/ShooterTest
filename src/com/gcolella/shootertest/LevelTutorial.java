package com.gcolella.shootertest;


import android.content.Context;

public class LevelTutorial extends Level {

	LevelTutorial(Context ctx, ShooterTestActivity act) {
		super(ctx, act);
		// TODO Auto-generated constructor stub
	}


	void setup(){
		out = new Universe(x,y,myctx);
		myship = new TinyShip(Location.randomLocation(x, y),100, new Vector(0,0), out, Team.GREEN);
		new NeutralShip(Location.randomLocation(x,y),20,new Vector(2,2), out, Team.YELLOW);
	}
	@Override
	boolean levelComplete() {
		return out.shipNumber() == 1;
	}

	boolean levelFailed() {
		if(myactiv.surface.playership.IDnum != getPlayer().IDnum)
			return true;
		return false;
	}
	String levelText(){
		return "Learn to shoot by engaging a small cargo ship.";
	}
	boolean powerups(){
		return true;
	}

}
