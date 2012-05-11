package com.gcolella.shootertest;

public class BasicPlayer extends ControlledShip {
	Countdown gun = new Countdown(5);
	Countdown sec = new Countdown(20);
	public BasicPlayer(Location newloc, float health, Vector newvelo,
			Universe theverse,Team team) {
		super(newloc, health, newvelo, theverse,team);
		// TODO Auto-generated constructor stub
	}
	public void controlStick(float x,float y)
	{
		float angle = new Vector(x,y,true).getAngle();
		getVelocity().setAngle(angle);
	}
	public void fireStick(float x,float y){
		float angle = new Vector(x,y,true).getAngle();
		if(x>10 && getVelocity().getMagnitude()<4)
			getVelocity().setMagnitude((float) (getVelocity().getMagnitude()+.1));
		if(x<10 && getVelocity().getMagnitude()>.2)
			getVelocity().setMagnitude((float) (getVelocity().getMagnitude()-.1));
		if(gun.timeUp()){
			//fire(angle);
			fire();
		}
	}

	public void update(){
		super.update();
		gun.cool();
		sec.cool();
	}

	@Override
	public float getHitRadius() {
		// TODO Auto-generated method stub
		return 45;
	}

	@Override
	public int getBmpID() {
		// TODO Auto-generated method stub
		return R.drawable.player;
	}
	public int maxSecondary(){
		return 5;
	}
	@Override
	public void secondButtonOne() {
		switchSecondary();
		
	}
	@Override
	public void secondButtonTwo() {
		// TODO Auto-generated method stub
		if(sec.timeUp()){
			fireSecondary();
		}
	}

}
