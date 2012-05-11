package com.gcolella.shootertest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class ShooterSurface extends SurfaceView implements Runnable, OnTouchListener
{
	boolean shouldquit;
	int score;
	Thread theThread = null;
	SurfaceHolder holder;
	boolean isOkay = false;
	Universe universe;
	ControlledShip playership;
	Bitmap bigBack;
	Bitmap newback;
	//-----------
	int numframes=0;
	int totframes = 0;
	int frames=0;
	long lasttick=0;
	//-----------
	
	
	
	public void drawUniverseObject(UniverseObject obj,Canvas cvs){
		if(obj==null)
			return;
		float x = (hasControlShip())?(universe.getBoundX()/2)+obj.getLocation().getX()-playership.getLocation().getX():obj.getLocation().getX();
		float y = (hasControlShip())?(universe.getBoundY()/2)+obj.getLocation().getY()-playership.getLocation().getY():obj.getLocation().getY();
		if((x<0 || x>universe.getBoundX()) || (y<0 || y>universe.getBoundY()))
			return;
		Matrix matrix = new Matrix();
		Bitmap theBMP = obj.getBMP();
		float offCenterX = (float) (x-(theBMP.getWidth()/2.0));
		float offCenterY = (float) (y-(theBMP.getHeight()/2.0));
		matrix.setRotate((float)Math.toDegrees(obj.getVelocity().getAngle()),(float)(theBMP.getWidth()/2.0),(float)(theBMP.getHeight()/2.0));
		matrix.postTranslate(offCenterX, offCenterY);
		Paint mypaint = new Paint();
		mypaint.setColor(Color.GREEN);
		cvs.drawBitmap(theBMP,matrix, new Paint());
		//if(obj instanceof TrackingObject){
		//cvs.drawText(""+((TrackingObject)obj).getCurrentTarget(), offCenterX, offCenterY, mypaint);

		//}
		//cvs.drawCircle((float)(obj.getLocation().getX()),(float)(obj.getLocation().getY()),obj.getHitRadius(),new Paint()); //Debugging hitradius.
		
	}
	public Universe getUniverse(){
		return universe;
	}



	public ShooterSurface(Context context,int x, int y ) {
		super(context);
		universe = new Universe(x,y,context);
		holder = getHolder();
		setOnTouchListener(this);
		bigBack = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ocean), universe.getBoundX(), universe.getBoundY(), false);
		newback = Bitmap.createBitmap(universe.getBoundX(),universe.getBoundY(),Bitmap.Config.ARGB_8888);
		shouldquit = false;
	}
	/*
	public void init() //This init should be used when the universe ALREADY exists.
	{
		new EnemyShip(Location.randomLocation(universe.getBoundX()-50, universe.getBoundY()-50),100, new Vector(0,1),universe);
		new EnemyShip(new Location(100,100),100,new Vector((float)Math.toRadians(20),(float) 1),universe);
		BasicPlayer myship = new BasicPlayer(new Location(250,250), 200, new Vector(0,3),universe);
		setControlShip(myship);
		new MineLayer(Location.randomLocation(universe.getBoundX()-50, universe.getBoundY()-50),100,new Vector(0,1),universe);
		
	}*/
	public boolean hasControlShip(){
		return playership!=null;
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(hasControlShip()){
		if(!(universe.isPresent(playership)))
				playership = null;
		if(hasControlShip())
			return playership.onTouch(event);

		}
		return false;
		
	}

	public void setControlShip(ControlledShip ship){
		playership = ship;
	}
	public boolean beenSecond(){
		if(SystemClock.uptimeMillis()-lasttick>1000)
		{
			lasttick = SystemClock.uptimeMillis();
			return true;
		}
		return false;
	}
	public void calcFPS(){
		if(beenSecond()){
			if(frames>0)
				Log.e("OUTPUTSHOOTER","avg: "+avg(frames)+" now: "+frames);
			frames = 0;}
	}
	public float avg(int in){
		totframes+=in;
		numframes++;
		return totframes/numframes;
	}
	
	
	
	@Override
	public void run() {
		while(isOkay){
			calcFPS();
			if(!holder.getSurface().isValid())
				continue;
			frames++;
			Canvas cvs = holder.lockCanvas();
			//cvs.drawARGB(255, 0, 0, 0);
			drawField(cvs);
			
			if(hasControlShip()){
				playership.drawHUD(cvs);
			}
			
			for(UniverseObject thing:universe.getStuff())
			{
				drawUniverseObject(thing,cvs);
				universe.updateOne(thing);
			}
			universe.updateLevel();
			
			if(universe.mylevel.levelComplete() || universe.mylevel.levelFailed())
				shouldquit = true;
				
			if(!hasControlShip()){
				setControlShip(universe.spawnPlayer());
			}
			holder.unlockCanvasAndPost(cvs);
		}
		
	} 
	public void drawField(Canvas cvs){
		if(playership==null)
			return;
		makeField((int)(playership.getLocation().getX()),(int)(playership.getLocation().getY()),cvs);
	}
	public void makeField(int x, int y, Canvas cvs)
	{	
		x = x%bigBack.getWidth();
		y = y%bigBack.getHeight();
			cvs.drawARGB(255, 0, 0, 0);
			for(int i=((x<0)?-1:0);i<=((x<0)?0:1);i++){
				for(int j=((y<0)?-1:0);j<=((y<0)?0:1);j++){
					cvs.drawBitmap(bigBack, (bigBack.getWidth()*i)-x, (bigBack.getHeight()*j)-y, new Paint());
				}
		}
	}
	public void pause(){
		isOkay = false;
		boolean open = true;
		while(open){
			try{
				theThread.join();
				//backroundThread.join();
				open = false;
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	public void resume(){
		isOkay = true;
		theThread = new Thread(this);
		theThread.start();
		//backroundThread = new Thread(backrounder);
		//backroundThread.start();
	}

	public void setUniverse(Universe universe2) {
		universe = universe2;
		
	}
}
