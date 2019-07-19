package ru.ivanludvig.planets;

import ru.ivanludvig.ship.Sputnik;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Ammo {
	
	Gam game;
	
	public int amount;
	float PTM = 32f;
	Vector2 pos;
	Sprite sprite;
	ParticleEffect effect;
	Body body;
	
	public Ammo(Gam gam){
		game = gam;
		amount = genAmount();
		pos = genPos();
		effect = new ParticleEffect((ParticleEffect) game.manager.get("data/res.p"));
		effect.reset();
		effect.scaleEffect(0.2f+(amount/50f));
		body = createCircle(5, pos);
	}
	
	
	public void render(float delta, SpriteBatch batch){
		effect.setPosition(body.getWorldCenter().x*PTM, body.getWorldCenter().y*PTM);
		effect.draw(batch, delta);
	}
	
	
	public int genAmount(){
		int gen=0;
		gen = (int) ((int) 10f*(Math.random()+1));
		System.out.println(gen);
		return gen;
	}
	public Vector2 genPos(){
		Vector2 gen = new Vector2(game.defense.planet.pos);
		float angle = (float) (Math.random()*360);
		gen.add((float)Math.cos(angle*Sputnik.DEGREES_TO_RADIANS)*game.defense.planet.radius, 
				(float)Math.sin(angle*Sputnik.DEGREES_TO_RADIANS)*game.defense.planet.radius);
		return gen;
	}
	
	
	public void dispose(){
		effect.dispose();
	}
	
	private Body createCircle(int radius, Vector2 pos) {
        BodyDef bodyDef   = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.scl(1/PTM));

        Body body = game.defense.world.createBody(bodyDef);
        body.setUserData(this);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius/PTM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 5f; 
        fixtureDef.friction = 0.04f;
        fixtureDef.restitution = 0.06f; 

        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }

}
