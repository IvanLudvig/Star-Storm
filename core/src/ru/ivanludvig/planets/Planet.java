package ru.ivanludvig.planets;

import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.PlanetTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Planet {
	
	Gam game;
	
	Vector2 pos;
	public Body body;
	public Sprite sprite;
	
	float PTM = 32f;
	public float hp = 100;
	public float gamehp;
	public float radius = 30;
	public float gravity = 1f;
	public int v;
	public int order;
	public float angle;
	public float dir;
	public float res;
	public float speed;
	public float space;
	
	public float addHp = 0;
	public float addGrv = 0;
	public float addRes = 0;
	public Vector2 stagePos = new Vector2(0,0);

	public Planet(Gam game, int i, int order) {
		this.game = game;
		v=i;
		this.order = order;
		if(i==-1){
			sprite = new Sprite((Texture)game.manager.get(("planets/"+((int)(Math.random()*20)+1)+".png")));
		}else{
			hp = Stats.php[i];
			gravity = Stats.pgr[i];
			radius = Stats.prad[i];
			dir = Stats.pdir[i];
			speed = Stats.pspeed[i];
			res = Stats.pres[i];
			space = Stats.psp[i];
			
			addHp = game.prefs.getInteger(i+"hp");
			addGrv = game.prefs.getInteger(i+"grv");
			addRes = game.prefs.getInteger(i+"res");

			sprite = new Sprite((Texture)game.manager.get("planets/"+i+".png"));
			if(order!=-1){
				angle=-56f*dir/order;
				tween();
			}
		}
		pos = new Vector2(400, 240);
		sprite.setOrigin(0, 0);
	}
	
	public void create(){
		body = createCircle();
		gamehp = getHp();
	}
	
	
	public void render(){
		sprite.setBounds(getPos().x-radius, getPos().y-radius, radius*2, radius*2);
		if(game.defense.end==1){
			sprite.setAlpha(0.2f);
		}
		sprite.draw(game.defense.batch);
		//body.setTransform(pos.x/PTM, pos.y/PTM, 0);
	}
	
	public void draw(SpriteBatch batch, Vector2 pos, float r){
		stagePos = pos;
		sprite.setRotation(r);
		sprite.setBounds(pos.x, pos.y, radius*2, radius*2);
		sprite.draw(batch);
	}
	public void halfdraw(SpriteBatch batch, Vector2 pos, float r, float a){
		stagePos = pos;
		sprite.setRotation(r);
		sprite.setAlpha(a);
		sprite.setBounds(pos.x, pos.y, radius, radius);
		sprite.draw(batch);
	}
	
	public void tween(){
		Tween.to(this, PlanetTween.angle, 1/speed*512f)
		.target(56f*dir/order)
		.ease(TweenEquations.easeInOutQuad)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
	}
	
	public String stats(){
		String string = order + ": hp"+(int)getHp()+" grv:"+(int)getGrv()+" res"+(int)getRes()+"\n";
		
		return string;
	}
	
	public Vector2 getPos(){
		return body.getWorldCenter().scl(PTM);
	}
	
	public void plusHp(float plus){
		System.out.println(plus+ " plus");
		addHp+=plus;
		game.prefs.putInteger(v+"hp", (int)addHp);
		game.prefs.flush();
	}
	
	public void plusGrv(float plus){
		addGrv+=plus;
		game.prefs.putInteger(v+"grv", (int)addGrv);
		game.prefs.flush();
	}
	
	public void minusGrv(float minus){
		addGrv-=minus;
		game.prefs.putInteger(v+"grv", (int)addGrv);
		game.prefs.flush();
	}
	
	public void plusRes(float plus){
		addRes+=plus;
		game.prefs.putInteger(v+"res", (int)addRes);
		game.prefs.flush();
	}
	
	public float getHp(){
		return hp+addHp;
	}
	
	public float getGrv(){
		return gravity+addGrv;
	}
	
	public float getRes(){
		return res+addRes;
	}
	
	public int hpCost(int points){
		return (int) (Math.sqrt(addHp*addHp*addHp)+20)+points;
	}
	public int grvCost(int points){
		return (int) (Math.sqrt(addGrv*addGrv*points)+20)+points;
		
	}
	public int resCost(int points){
		return (int) (Math.sqrt(addRes*addRes*points)+20)+points;
		
	}
    private Body createCircle() {
        BodyDef bodyDef   = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((400+15)/PTM, (240+15)/PTM);


        Body body = game.defense.world.createBody(bodyDef);
        body.setUserData(this);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius/PTM);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 50f; 
        fixtureDef.friction = 0.04f;
        fixtureDef.restitution = 0.1f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        return body;
    }
    
	public void dispose () {
	}

}
