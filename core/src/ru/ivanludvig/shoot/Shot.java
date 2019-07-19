package ru.ivanludvig.shoot;

import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Shot {
	
	Body body;
	float PTM = 32f;
	Gam game;
	ParticleEffect effect;
	float angle;
	
	public Shot(Gam game, Vector2 pos, Vector2 vel, Vector2 off){
		this.game = game;

		switch(game.shipnum){
		default:
			//sound = Gdx.audio.newSound((FileHandle) game.manager.get("audio/sounds/shot1.ogg"));
			break;
		}
		if(game.shipnum==1){
			((Sound) game.manager.get("audio/sounds/shots/1.ogg")).play(0.3f*game.defense.sputnik.weaponry);
		}else{
			((Sound) game.manager.get("audio/sounds/shots/"+(game.shipnum-1)+".ogg")).play(0.3f*game.defense.sputnik.weaponry);
		}
		//((Sound) game.manager.get("audio/sounds/shot1.ogg")).play(0.2f*game.defense.sputnik.weaponry);
		/*switch(game.shipnum){
		case 1:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/oberon-shot.p"));
			break;
		case 2:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/hydra-shot.p"));
			break;
		case 3:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/nightfall-shot.p"));
			break;
		case 4:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/nightfall-shot.p"));
			break;
		case 5:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/wildcat-shot.p"));
			break;
		default:
			effect = new ParticleEffect((ParticleEffect) game.manager.get("data/oberon-shot.p"));
			break;
		}*/
		effect = new ParticleEffect((ParticleEffect) 
				game.manager.get("data/shots/"+Stats.names[game.shipnum].toLowerCase()+"-shot.p"));
		effect.reset();
		//effect.load(Gdx.files.internal("data/shot4.p"), 
		//          Gdx.files.internal("data"));
		effect.scaleEffect(0.3f*game.defense.sputnik.weaponry);
		
		if((game.defense.ui.tp.getKnobPercentX()==0) && (game.defense.ui.tp.getKnobPercentY()==0 )){
			angle = (float) ((new Vector2(pos.x-vel.x-off.x, pos.y-vel.y-off.y).angle()-180)*game.defense.sputnik.DEGREES_TO_RADIANS);
		}else{
			angle = (float)(game.defense.sputnik.angle*game.defense.sputnik.DEGREES_TO_RADIANS);
		}


		Vector2 dir = new Vector2(vel.x-pos.x-off.x, vel.y-pos.y-off.y);
		dir.nor();
		if(game.defense.sputnik.cspeed>3f){
			body = createCircle(1f*game.defense.sputnik.weaponry, 
					new Vector2(pos.x+(float)(Math.cos(angle)*(Stats.range[game.shipnum-1]*1.2f)), 
					pos.y+(float)(Math.sin(angle)*(Stats.range[game.shipnum-1]*1.2f))));
			body.setLinearVelocity((float)(Math.cos(angle))*game.defense.sputnik.power*1.2f, 
					(float)(Math.sin(angle))*game.defense.sputnik.power*1.2f);
		}else{
			body = createCircle(1f*game.defense.sputnik.weaponry, 
					new Vector2(pos.x+(float)(Math.cos(angle)*(Stats.range[game.shipnum-1]/1.5f)), 
					pos.y+(float)(Math.sin(angle)*(Stats.range[game.shipnum-1]/1.5f))));
			body.setLinearVelocity((float)(Math.cos(angle))*game.defense.sputnik.power, 
					(float)(Math.sin(angle))*game.defense.sputnik.power);
		}
	}
	
	
	public void destroy(){
		
	}
	
	public void render(float delta){
		if((getPos().x<1100)&&(getPos().x>-300)&&(getPos().y<700)&&(getPos().y>-220)){
			effect.setPosition(body.getWorldCenter().x*PTM, body.getWorldCenter().y*PTM);
			effect.draw(game.defense.batch, delta);
		}
	}
	
	public Vector2 getPos(){
		return body.getWorldCenter().scl(PTM);
	}
	
	private Body createCircle(float radius, Vector2 pos) {
        BodyDef bodyDef   = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x/PTM, pos.y/PTM);


        Body body = game.defense.world.createBody(bodyDef);
        body.setUserData(this);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius/PTM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = radius * 0.5f; 
        fixtureDef.friction = 4f;
        fixtureDef.restitution = 0.06f; 

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }
	
	public void dispose(){
		effect.dispose();
	}

}
