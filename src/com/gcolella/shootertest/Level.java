package com.gcolella.shootertest;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public abstract class Level {
	Universe out = null;
	ControlledShip myship = null;
	Context myctx;
	static int x,y;
	ShooterTestActivity myactiv;
	static void setXY(int nx,int ny){
		x = nx;
		y = ny;
	}
	static int getX(){
		return x;
	}
	static int getY(){
		return y;
	}
	Level(Context ctx, ShooterTestActivity home){
		myctx = ctx;

		myactiv = home;
	}
	Universe getUniverse() {
		return out;
	}
	ControlledShip getPlayer(){
		return myship;
	}
	ControlledShip spawnPlayer(){
		return new BasicPlayer(new Location(250,250), 200, new Vector(0,4),out,Team.GREEN);
	}
	void updateLevel(){}
	abstract boolean levelComplete();
	abstract boolean levelFailed();
	abstract void setup();
	abstract String levelText();
	abstract boolean powerups();
	void goHome(){
		myactiv.wanthome = true;
	}
	LinearLayout levelScreen(){
		LinearLayout out = new LinearLayout(myctx);
		out.setOrientation(LinearLayout.VERTICAL);
		TextView stuff = new TextView(myctx);
		stuff.setText(levelText());
		Button cont= new Button(myctx);
		cont.setText("Continue");
		cont.setOnClickListener(new ButtonListener(this));
		out.addView(stuff);
		out.addView(cont);
		return out;
	}
	
	public class ButtonListener implements OnClickListener{
		Level l;
		public ButtonListener(Level nl){
			l=nl;
		}
		public void onClick(View arg0) {
			myactiv.loadLevel(l);
		}
	}

}
