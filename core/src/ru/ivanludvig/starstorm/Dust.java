package ru.ivanludvig.starstorm;

import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Dust {
	Gam game;
	
	Vector2 pos;
	public Body body;
	
	float PTM = 32f;
	public float radius;
	public Sprite sprite;

	public Dust(Gam game, Vector2 pos, float radius) {
		this.game = game;
		this.radius = radius;
		this.pos = pos;
		sprite = new Sprite((Texture) game.manager.get("asteroids/"+((int)(Math.random()*20)+1)+".png"));
		sprite.setOrigin(radius, radius);
		sprite.setAlpha(0.9f);
		body = createCircle(radius, pos);
		
		Tween.to(sprite, SpriteTween.ROTATION, (float) Math.random()*(radius)*5f)
		.target(360)
		.ease(Linear.INOUT)
		.repeat(Tween.INFINITY, 0)
		.start(game.defense.tweenManager);
	}
		
	
	public void render(){
		sprite.setBounds(getPos().x-radius, getPos().y-radius, radius*2, radius*2);
		sprite.draw(game.defense.batch);
	    if(game.defense.end==1){
			sprite.setAlpha(0.2f);
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

}