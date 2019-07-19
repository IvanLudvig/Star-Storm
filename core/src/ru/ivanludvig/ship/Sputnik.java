package ru.ivanludvig.ship;

import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.SpriteTween;
import ru.ivanludvig.tween.SputnikTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Sputnik {

	Gam game;
	
	public Sprite sprite;
	Vector2 pos;
	public Body body;
	
	float PTM = 32f;
	
	public int ammo;
	public int gameammo;
	public float speed;
	public float cspeed;
	public float maxspeed;
	public int hp;
	public float angle = 0f;
	public float power;
	public float agility;
	public float weaponry;
	public int count = 1;
	public int ccount;
	public Vector2 size;
	int num;
	
	public ParticleEffect effect;
	public Music engine;

	public Sputnik(Gam game) {
		this.game = game;
		ammo = 20;
		gameammo = ammo;
		engine = (Music)game.manager.get("audio/engine.mp3", Music.class);
		engine.setLooping(true);
		cspeed = 0f;
		/*
		this.hp = (int) Stats.str[g];
		this.speed = Stats.sp[g];
		this.maxspeed = Stats.maxsp[g];
		this.power = Stats.pow[g];
		this.agility = Stats.ag[g];
		this.weaponry = Stats.weap[g];
		*/
		//body.setLinearVelocity(1f, 1f);]
		
	}
	
 	public static final double DEGREES_TO_RADIANS = (double)(Math.PI/180);
 	
 	
	public void render(float delta){
		effect.setPosition((float) (getPos().x-(Math.cos(sprite.getRotation()*DEGREES_TO_RADIANS)*size.x/2)), 
			(float) (getPos().y-(Math.sin(sprite.getRotation()*DEGREES_TO_RADIANS)*size.y/2)));
		effect.draw(game.defense.batch, delta);
		sprite.setBounds(getPos().x-32, getPos().y-32, 64, 64);
		sprite.draw(game.defense.batch);

     	 if((game.defense.ui.tp.getKnobPercentX()!=0) || (game.defense.ui.tp.getKnobPercentY()!=0 )){
         	 body.setLinearVelocity(new Vector2(game.defense.ui.tp.getKnobPercentX()*cspeed, 
         			 game.defense.ui.tp.getKnobPercentY()*cspeed));
         	 addAngle((float) (new Vector2(game.defense.ui.tp.getKnobPercentX(), 
     				 game.defense.ui.tp.getKnobPercentY()).angle()));
          	tweening();
        	stween();
     	 }else{
     		body.setLinearVelocity(cspeed*((float)Math.cos(angle*DEGREES_TO_RADIANS)), 
     				(cspeed*((float)Math.sin(angle*DEGREES_TO_RADIANS))));
     		slowtween();
     	 }
       	engine.setVolume(power*cspeed/100f*1.1f);
 		body.setTransform(body.getWorldCenter(), (float) ((float) sprite.getRotation()*DEGREES_TO_RADIANS));
 		if(getPos().x>990){
 			body.setTransform(990/PTM, body.getWorldCenter().y, (float) ((float) sprite.getRotation()*DEGREES_TO_RADIANS));
 			
 		}else if(getPos().x<-190){
 			body.setTransform(-190/PTM, body.getWorldCenter().y, (float) ((float) sprite.getRotation()*DEGREES_TO_RADIANS));
 		}
 		if(getPos().y>580){
 			body.setTransform(body.getWorldCenter().x, 580/PTM, (float) ((float) sprite.getRotation()*DEGREES_TO_RADIANS));
 			
 		}else if(getPos().y<-100){
 			body.setTransform(body.getWorldCenter().x, -100/PTM, (float) ((float) sprite.getRotation()*DEGREES_TO_RADIANS));
 		}
     	 if(Math.abs(angle - sprite.getRotation())>1){

     	 }else{
     		 body.setFixedRotation(true);
     		 //cspeed = speed;
     	 }
     	 if(j==1){
	     	shoot(vec2);
     	 }
     	 //cspeed=body.getLinearVelocity().len();
	}
	
	public void tweening(){
		if(angle-sprite.getRotation()>180){
			angle-=360;
		}
		else if(sprite.getRotation()-angle>180){
			angle+=360;
		}else{
			//angle = acute(angle);
			//sprite.setRotation(acute(sprite.getRotation()));
		}
		if(Math.abs(sprite.getRotation()-angle)>0.1){
			game.defense.tweenManager.killTarget(sprite);
			Tween.to(sprite, SpriteTween.ROTATION, (Math.abs(sprite.getRotation()-angle))*(1/this.agility)*0.03f)
			.target(angle)
			.ease(Linear.INOUT)
			.start(game.defense.tweenManager);
		}else{
		}
	}
	
	public void stween(){
		if(body.getLinearVelocity().len()>1f){
			Tween.to(this, SputnikTween.speed, 3f)
			.target(maxspeed)
			.ease(Linear.INOUT)
			.start(game.defense.tweenManager);
		}else{
			game.defense.tweenManager.killTarget(this);
			cspeed = speed;
		}
	}
	
	public void slowtween(){
		if(body.getLinearVelocity().len()>0f){
			Tween.to(this, SputnikTween.speed, 3f)
			.target(0f)
			.ease(Linear.INOUT)
			.start(game.defense.tweenManager);
		}else{
			//game.defense.tweenManager.killTarget(this);
			//cspeed = 0f;
		}
	}
	
	public void shoottween(){
		Tween.to(this, SputnikTween.speed, 1f/(float)Math.sqrt(ccount))
		.target(-1f*power/agility)
		.ease(TweenEquations.easeOutCirc)
		.start(game.defense.tweenManager);
	}
	
	public float acute(float y){
		float g = y;
		return g;
	}
	
	int j = 0;
	Vector2 vec2;
	float mangle;
	
	public void shoot(Vector2 vec){
		System.out.println(ccount);
		 if(gameammo>=ccount){
	    	 if((game.defense.ui.tp.getKnobPercentX()==0) && (game.defense.ui.tp.getKnobPercentY()==0 )){
	 			shoottween();
	    		if(j==0){
	    			 addAngle((float) (((new Vector2(vec)).sub(game.defense.sputnik.getPos())).angle()));
	    		     tweening();
				}
				j=1;
				vec2 = vec;
				if(Math.abs(sprite.getRotation() - (float) (angle))<5){
		     			 switch(ccount){
		     			 case 1:
			     			 game.defense.shooter.shoot(vec, new Vector2(0,0));
			     			 gameammo--;
			     			 break;
		     			 case 2:
			     			 doubleShoot(vec);
			     			 gameammo-=2;
			     			 break;
		     			 case 3:
			     			 tripleShoot(vec);
			     			 gameammo-=3;
			     			 break;
		     			 }
		     			 j=0;
				}
	    	 }else{
	     		 if(gameammo>=ccount){
	     			 switch(ccount){
	     			 case 1:
		     			 game.defense.shooter.shoot(vec, new Vector2(0,0));
		     			 gameammo--;
		     			 break;
	     			 case 2:
		     			 doubleShoot(vec);
		     			 gameammo-=2;
		     			 break;
	     			 case 3:
		     			 tripleShoot(vec);
		     			 gameammo-=3;
		     			 break;
	     			 }
					j=0;
	     		 }
	    	 }
		 }
	}
	
	public Vector2 getPos(){
		return body.getWorldCenter().scl(PTM);
	}
	
	public void addAngle(float r){
		if((r - sprite.getRotation())>=360){
			while((r - sprite.getRotation())>=360){
				r-=360;
			}
		}else if((sprite.getRotation() - r)>=360){
			while((sprite.getRotation() - r)>=360){
				r+=360;
			}
		}
		angle = r;
	}
	
	public void create(){
		this.sprite = new Sprite((Texture) game.manager.get(("ships/"+num+".png")));
		pos = new Vector2((float)Math.random()*800, (float)Math.random()*480);
		size = new Vector2(Stats.height[num-1], Stats.width[num-1]);
		body = createCircle(7);
		this.hp = Stats.hpFunc(num);
		this.maxspeed = Stats.speedFunc(num);
		this.agility = Stats.agilityFunc(num);
		this.speed = Stats.minSpeedFunc(maxspeed, agility);
		System.out.println(size);
		this.power = Stats.powerFunc(num);
		this.weaponry = Stats.weapFunc(num, power);
		this.sprite.setOrigin(32, 32);
		effect = new ParticleEffect((ParticleEffect) 
				game.manager.get("data/fire/"+Stats.names[game.shipnum].toLowerCase()+".p"));
		this.effect.reset();
		this.effect.scaleEffect(0.9f);
		if(num<=4){
			count = 1;
		}else if(num>=8){
			count = 3;
		}else{
			count = 2;
		}
		ccount = 1;
		engine.setVolume(power/7f);
		cspeed=0f;
		angle=(float)Math.random()*360;
		sprite.setRotation(angle);
      	engine.play();
       	engine.setVolume(power*cspeed/100f*1.5f);
	}
	
	public void addAmmo(int amount){
		gameammo+=amount;
	}
	
	private Body createCircle(int radius) {
	        BodyDef bodyDef   = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        bodyDef.position.set(pos.x/PTM, pos.y/PTM);

	        Body body = game.defense.world.createBody(bodyDef);
	        body.setUserData(this);
	        PolygonShape shape = new PolygonShape();
	        shape.setAsBox(size.x/2/PTM, size.y/2/PTM);

	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = 5f; 
	        fixtureDef.friction = 0.04f;
	        fixtureDef.restitution = 0.06f; 

	        Fixture fixture = body.createFixture(fixtureDef);

	        return body;
	    }
	
	public void tripleShoot(Vector2 vec){
		if((Math.cos(this.angle*this.DEGREES_TO_RADIANS)>0)&&(Math.sin(this.angle*this.DEGREES_TO_RADIANS)>0)
				|| (Math.cos(this.angle*this.DEGREES_TO_RADIANS)<0)&&(Math.sin(this.angle*this.DEGREES_TO_RADIANS)<0)){
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*-10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(this.weaponry*20*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS)))));
			 game.defense.shooter.shoot(vec, new Vector2(0,0));
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(this.weaponry*-20*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS))))); 
			System.out.println("ok this first ");
			
		}else{
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(this.weaponry*20*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS)))));
			 game.defense.shooter.shoot(vec, new Vector2(0,0));
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*-10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(this.weaponry*-20*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS))))); 
		}
	}
	public void doubleShoot(Vector2 vec){
		if((Math.cos(this.angle*this.DEGREES_TO_RADIANS)>0)&&(Math.sin(this.angle*this.DEGREES_TO_RADIANS)>0)
				|| (Math.cos(this.angle*this.DEGREES_TO_RADIANS)<0)&&(Math.sin(this.angle*this.DEGREES_TO_RADIANS)<0)){
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(-10*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS)))));
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*-10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(10*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS))))); 
			
		}else{
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(10*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS)))));
			game.defense.shooter.shoot(vec, new Vector2((float)(this.weaponry*-10*Math.abs(Math.sin(this.angle*this.DEGREES_TO_RADIANS))), 
					(float)(-10*Math.abs(Math.cos(this.angle*this.DEGREES_TO_RADIANS))))); 
		}
	}
	
	public void dispose () {
		effect.dispose();
	}

}
