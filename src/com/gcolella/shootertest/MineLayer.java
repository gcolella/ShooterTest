package com.gcolella.shootertest;

public class MineLayer extends TrackingObject {
	int cooldown = 200;
	int countdown = 0;
	public MineLayer(Location newloc, float health, Vector newvelo,
			Universe theverse,Team team) {
		super(newloc, health, newvelo, theverse, team);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean targetable(UniverseObject obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int maxSecondary() {
		return 0;
	}

	@Override
	public float getHitRadius() {
		// TODO Auto-generated method stub
		return 30;
	}
	public boolean isEnemy(){
		return true;
	}
	public void update(){
		super.update();
		if(countdown==0){
			fireMine();
			countdown=cooldown;
		}else{countdown--;}
		
	}
	@Override
	public int getBmpID() {
		// TODO Auto-generated method stub
		return R.drawable.miner;
	}

}
