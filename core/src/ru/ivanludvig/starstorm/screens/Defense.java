package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.level.Level;
import ru.ivanludvig.planets.Planet;
import ru.ivanludvig.planets.ResManager;
import ru.ivanludvig.processors.CollisionListener;
import ru.ivanludvig.processors.Ip;
import ru.ivanludvig.ship.Blossom;
import ru.ivanludvig.ship.Cygnus;
import ru.ivanludvig.ship.Hydra;
import ru.ivanludvig.ship.Lynx;
import ru.ivanludvig.ship.NightFall;
import ru.ivanludvig.ship.Oberon;
import ru.ivanludvig.ship.Oblivion;
import ru.ivanludvig.ship.Pleadis;
import ru.ivanludvig.ship.Proximo;
import ru.ivanludvig.ship.Sirius;
import ru.ivanludvig.ship.Sputnik;
import ru.ivanludvig.shoot.Shooter;
import ru.ivanludvig.starstorm.DustManager;
import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.starstorm.Ui;
import aurelienribon.tweenengine.TweenManager;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Defense extends ScreenAdapter{
	
	Gam game;
	
	public OrthographicCamera camera;
	public Viewport viewport; 
	
	public static SpriteBatch batch;
	public static World world;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	public TweenManager tweenManager;
	
	public Planet planet;
	public Sputnik sputnik;
	public DustManager dustmanager;
	public ResManager resmanager;
	public Level level;
	
	Texture img;
	float PTM = 32f;
	public final Vector2 center = new Vector2((400+15)/PTM, (240+15)/PTM);
	ParticleEffect stars;
	
	Array<Body> bodies = new Array<Body>();
	
	CollisionListener cl;
	
	public Stage stage;
	public Ui ui;
	public Shooter shooter;
	public Skin skin;
	
	public Ip ip;

	public int score = 0;
	public int end = 0;
	public Texture bg;
	public Sprite gameover;
	public Sprite complete;
	public Sprite failed;
	RayHandler rayHandler;
	PointLight light;
	public int pause = 0;
	
	public Defense(Gam gam) {
		this.game = gam;
		batch = new SpriteBatch();
		tweenManager = game.tweenManager;
		camera = new OrthographicCamera(800, 480);
		camera.setToOrtho(false);
		camera.position.set(400, 240, 0);
		viewport = new StretchViewport(800, 480, camera);
		viewport.apply(true);
		
		world = new World(new Vector2(0, 0), false);
		debugRenderer = new Box2DDebugRenderer();
		score = 0;
		
		bg = game.manager.get("bg/"+(int)(Math.random()*5+1)+".png");
		gameover = new Sprite((Texture)game.manager.get("menu/gameover.png"));
		gameover.setBounds(150, 320, 500, 187);
		complete = new Sprite((Texture)game.manager.get("menu/complete.png"));
		complete.setBounds(150, 320, 500, 187);
		failed = new Sprite((Texture)game.manager.get("menu/failed.png"));
		failed.setBounds(150, 320, 500, 187);
		stars = new ParticleEffect((ParticleEffect) game.manager.get("data/stars-slow.p"));
		stars.reset();
		
		skin = game.skin;	
		stage = new Stage(game.uiviewport);
		
	    debugMatrix=new Matrix4(camera.combined);
	    debugMatrix.scale(PTM, PTM, 1f);
		
		//img = new Texture("badlogic.jpg");
		planet = new Planet(game, (int)(Math.random()*20)+1, -1);
		sputnik = new Oberon(game);
		
		dustmanager = new DustManager(game);
		shooter = new Shooter(game);
		resmanager = new ResManager(game);

		cl = new CollisionListener(game);
		world.setContactListener(cl);
		ip = new Ip(game);
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(0.3f);
		rayHandler.setShadows(true);		
		rayHandler.setCombinedMatrix(camera.combined.scl(PTM, PTM, 0));
		light = new PointLight(rayHandler, 32, Color.GOLD, 20, 100/PTM, 240/PTM);
		light.setSoftnessLength(0);
		camera.zoom += 0.5f;
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glBlendEquationSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		tweenManager.update(delta);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
	    debugMatrix=new Matrix4(camera.combined);
	    debugMatrix.scale(PTM, PTM, 1f);
	    handleInput();
	    
	    batch.begin();
		batch.setProjectionMatrix(game.uicamera.combined);
	    if(lost()==1){
			batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.1f);
	    }
	    batch.draw(bg, -100-(sputnik.getPos().x/10), -100-(sputnik.getPos().y/10));

		batch.setProjectionMatrix(camera.combined);
		planet.render();
		sputnik.render(delta);
		dustmanager.render();
		shooter.render(delta);
		resmanager.render(delta, batch);
		stars.setPosition(400, 200);
		stars.draw(batch, delta);

		//batch.setProjectionMatrix(game.uicamera.combined);
		//font.draw(batch,sputnik.hp+"", 10, 470);
		batch.end();
		game.tweenManager.update(delta);
		ui.render(delta);
		//debugRenderer.render(world, debugMatrix);
		gravity();
		if(lost()==0){
			if(level!=null){
				level.render();
				if(level.don){
					completed();
				}else{
					normal();
				}
			}else{
				normal();
			}
		}else{
			if(level!=null){
				failed();
			}else{
				gameOver();
			}
		}
	}
	
	public void normal(){
		if(pause==0){
			world.step(1f/60f, 6, 2);
			world.clearForces();
		}
		batch.begin();
		if(planet.gamehp>20){
			game.greenfont.draw(batch,(int)planet.gamehp+"", 
					planet.getPos().x-10, planet.getPos().y+(planet.radius)+10);
		}else{
			game.pinkfont.draw(batch,(int)planet.gamehp+"", 
					planet.getPos().x-10, planet.getPos().y+(planet.radius)+10);
		}
		batch.end();
		stage.draw();
		stage.act();
	}
	
	int once = 0;
	public void failed(){
		if(once==0){
			light = new PointLight(rayHandler, 1024, Color.MAGENTA, 70, -600/PTM, 240/PTM);
			light.setSoftnessLength(30f);
			once=1;
		}
		end = 1;
		stage.draw();
		stage.act();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		failed.draw(batch);
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.2f);
		batch.end();
		rayHandler.setCombinedMatrix(camera.combined.scl(PTM, PTM, 0));
		rayHandler.updateAndRender();
	}
	
	public void completed(){
		if(once==0){
			light = new PointLight(rayHandler, 1024, Color.GREEN, 70, -600/PTM, 240/PTM);
			light.setSoftnessLength(30f);
			once=1;
		}
		end = 1;
		stage.draw();
		stage.act();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		complete.draw(batch);
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.2f);
		batch.end();
		rayHandler.setCombinedMatrix(camera.combined.scl(PTM, PTM, 0));
		rayHandler.updateAndRender();
	}
	
	public void gameOver(){
		if(once==0){
			light = new PointLight(rayHandler, 1024, Color.MAGENTA, 70, -600/PTM, 240/PTM);
			light.setSoftnessLength(30f);
			once=1;
		}
		end = 1;
		stage.draw();
		stage.act();
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		gameover.draw(batch);
		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.2f);
		batch.end();
		rayHandler.setCombinedMatrix(camera.combined.scl(PTM, PTM, 0));
		rayHandler.updateAndRender();
	}
	
	
	public void gravity(){
		world.getBodies(bodies);

		for (Body body : bodies){
			Vector2 debrisPosition=body.getWorldCenter();
					float planetRadius=15/PTM;
					Vector2 planetPosition = new Vector2((400+15)/PTM, (240+15)/PTM);
					Vector2 planetDistance=new Vector2(0,0);
					planetDistance.add(debrisPosition);
					planetDistance.sub(planetPosition);

					float finalDistance=planetDistance.len();
					//if (finalDistance<=planetRadius*300) {
					planetDistance.x=-planetDistance.x;
					planetDistance.y=-planetDistance.y;
					float vecSum=Math.abs(planetDistance.x)+Math.abs(planetDistance.y);
					planetDistance.scl((1/vecSum)*planetRadius/finalDistance);
					//body.applyForce(planetDistance.scl(finalDistance*0.5f),body.getWorldCenter(), false);
					if((body.getUserData()!=planet)){
						float sputnikRadius=10/PTM;
						Vector2 sputnikPosition = new Vector2(sputnik.body.getWorldCenter());
						Vector2 sputnikDistance=new Vector2(0,0);
						sputnikDistance.add(debrisPosition);
						sputnikDistance.sub(sputnikPosition);

						float finalDistance2=sputnikDistance.len();
						//if (finalDistance<=planetRadius*300) {
						sputnikDistance.x=-sputnikDistance.x;
						sputnikDistance.y=-sputnikDistance.y;
						float vecSum2=Math.abs(sputnikDistance.x)+Math.abs(sputnikDistance.y);
						sputnikDistance.scl((1/vecSum2)*sputnikRadius/finalDistance2);
						//body.applyForce(sputnikDistance.scl(0.005f),body.getWorldCenter(), false);
						if(body.getUserData().getClass().toString().equals("class ru.ivanludvig.starstorm.Dust")){
							body.applyForce(planetDistance.scl(0.3f*body.getMass()*(planet.getGrv()/10)*((float)Math.sqrt(score+100))/12f),body.getWorldCenter(), false);
						}else{
							body.applyForce(planetDistance.scl(135f*body.getMass()*sputnik.weaponry*(planet.getGrv()/10)),body.getWorldCenter(), false);
						}
					}
			}
		}
	
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			score+=50;
		}
	}
	
	int k;
	
	public int lost(){
		if(sputnik.hp<=0 || planet.gamehp<=0){
			if(k==0){
				ui.lost();
				k=1;
			}
			return 1;
		}else{
			return 0;
		}
	}
	
	public void newGame(){
		score = 0;
		end = 0;
		once = 0;
		world = new World(new Vector2(0, 0), false);
		camera.position.set(400, 240, 0);
		if(level==null){
			bg = new Texture("bg/"+(int)(Math.random()*5+1)+".png");
			planet = new Planet(game, (int)(Math.random()*20)+1, -1);
			planet.create();
		}else{
			bg = new Texture("bg/"+(int)(Math.random()*5+1)+".png");
			planet = new Planet(game, level.planetNum(), -1);
			planet.create();
		}
		k=0;
		switch(game.shipnum){
		case 1:
			sputnik = new Oberon(game);
			break;
		case 2:
			sputnik = new Hydra(game);
			break;
		case 3:
			sputnik = new NightFall(game);
			break;
		case 4:
			sputnik = new Cygnus(game);
			break;
		case 5:
			sputnik = new Lynx(game);
			break;
		case 6:
			sputnik = new Proximo(game);
			break;
		case 7:
			sputnik = new Oblivion(game);
			break;
		case 8:
			sputnik = new Sirius(game);
			break;
		case 9:
			sputnik = new Pleadis(game);
			break;
		case 10:
			sputnik = new Blossom(game);
			break;
		default:
			sputnik = new Oberon(game);
			break;
		}
		
		ui.newGame();
		game.music.stop();
		game.music = game.manager.get("audio/game.mp3");
		game.music.setLooping(true);
		game.music.setVolume(0.3f);
		game.music.play();
		
		dustmanager = new DustManager(game);
		shooter = new Shooter(game);
		resmanager = new ResManager(game);

		batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f);
		
		cl = new CollisionListener(game);
		world.setContactListener(cl);
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(1f);
		rayHandler.setShadows(true);		
		rayHandler.setCombinedMatrix(camera.combined.scl(PTM, PTM, 0));
		rayHandler.setBlur(true);
	}
	
	public void setLevel(Level lvl){
		level = lvl;
		newGame();
	}
	
	@Override
	public void show(){
        game.inputMultiplexer.addProcessor(stage);
        game.inputMultiplexer.addProcessor(ip);
        //newGame();
        System.out.println("show" );
	}
	
	@Override
	public void hide(){
        game.inputMultiplexer.removeProcessor(stage);
        game.inputMultiplexer.removeProcessor(ip);
		game.music.stop();
		game.music = game.manager.get("audio/menu.ogg");
		game.music.setLooping(true);
		game.music.setVolume(0.5f);
		game.music.play();
        System.out.println("hide" );
	}
	@Override
	public void resize(int screenX, int screenY){
        game.uiviewport.update(screenX, screenY);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		world.dispose();
		skin.dispose();
		bg.dispose();
		stage.dispose();
		debugRenderer.dispose();
		sputnik.dispose();
		planet.dispose();
		shooter.dispose();
		resmanager.dispose();
	}
}
