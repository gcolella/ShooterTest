package com.gcolella.shootertest;

public class Countdown {
int cooldown;
int countdown;
public Countdown(int c){
	countdown = c;
	cooldown = c;
}
public void cool(){
	if(countdown>0)
		countdown--;
}
public boolean timeUp(){
	if(countdown==0){
		countdown=cooldown;
		return true;
	}
	return false;
}
}
