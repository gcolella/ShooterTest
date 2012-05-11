package com.gcolella.shootertest;

import android.content.Context;
import android.util.Log;

public class FreeFlight extends Level {

	public FreeFlight(Context ctx, ShooterTestActivity home) {
		super(ctx, home);
		// TODO Auto-generated constructor stub
	}
	boolean powerups(){
		return true;
	}
	@Override
	boolean levelComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean levelFailed() {
		// TODO Auto-generated method stub
		return false;
	}
	void updateLevel(){
		if(out.onTeam(Team.RED) <= 1){
			out.spawnShip(Team.RED);
			out.spawnShip(Team.RED);}
	}
	@Override
	String levelText(){
		return "Fly against two enemies at a time. They contain mine laying and normal firing craft. You start with two missiles.. use them wisely.";
		
	}
	void setup() {
		out = new Universe(x,y,myctx);
		myship = new BasicPlayer( new Location(0,0), 100, new Vector(0,2), out,Team.GREEN);
		new EnemyShip( Location.randomLocation(x,y), 20, new Vector(2,4), out,Team.RED);
		new EnemyShip( Location.randomLocation(x,y), 20, new Vector(2,4), out,Team.RED);
		myship.addAmmo(0,10);
	}

}
