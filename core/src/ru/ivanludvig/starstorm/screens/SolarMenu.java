package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.planets.MyButton;
import ru.ivanludvig.planets.Planet;
import ru.ivanludvig.planets.WindowUi;
import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class SolarMenu extends ScreenAdapter{
	
	Gam game;
	public Stage stage;
	public WindowUi windowui;
	SpriteBatch batch;
	
	TextButton back;
	TextButton addPlanet;
	TextButton showStats;
	Table table;
	ScrollPane scroller;
	public Actor actor[] = new Actor[20];
	MyButton buttons[] = new MyButton[20];
	Actor sun;
	Vector2 pos[] = new Vector2[20];
	Sprite bg;
	public int j = 0;
	public int k = 0;
	
	public SolarMenu(Gam gam){
		this.game = gam;
		stage = new Stage(game.uiviewport);
		batch = new SpriteBatch();
        bg = new Sprite((Texture)game.manager.get("bg/8.png"));
		table = new Table();
        back = new TextButton("  <  ", game.skin);
    	if(game.solarSystem.planets.size<game.solarSystem.maxplanet){
    		addPlanet = new TextButton("add a\n planet", game.greenskin);
    	}else{
    		addPlanet = new TextButton("add a\n planet", game.skin);
    	}
        showStats = new TextButton("show\n stats", game.skin);
		scroller = new ScrollPane(table);
		
		back.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
	        }
	    });
		addPlanet.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	if(game.solarSystem.planets.size<game.solarSystem.maxplanet){
		        	game.setScreen(game.choosePlanet);
	        	}else{
	        		windowui.max();
	        	}
	        }
	    });
		showStats.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	windowui.stats.setVisible(true);
	        }
	    });
		bg.setAlpha(0.85f);
		bg.setPosition(-100, -100);
		Tween.to(bg, SpriteTween.POS_XY, 150f)
		.target(-500, -500)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		
		Tween.to(bg, SpriteTween.ROTATION, 50f)
		.target(10)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		
		Tween.to(bg, SpriteTween.OPACITY, 10f)
		.target(0.65f)
		.ease(TweenEquations.easeInOutElastic)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
	}
	
	float angle;
	
	@Override
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(game.uicamera.combined);
		game.tweenManager.update(delta);
		batch.begin();
		bg.draw(batch);
		angle+=delta;

		draw(delta);
		game.solarSystem.renderSun(delta, batch, sun.localToStageCoordinates(new Vector2(0, 0)));
		batch.end();
		
		stage.act();
		stage.draw();
		handleInput();
		windowui.render(delta);
	}
	
	Table pTable;
	public void update(){
		int l=0;
		if(game.solarSystem.planets.size!=0){
			for(int i = 0; i<game.solarSystem.planets.size; i++){
				l++;
			}
		}
		if(stage!=null){
			if(l>k){
				newMenu();
				k=l;
			}
		}
	}
	
	public void draw(float delta){
		for(int i=0;i<game.solarSystem.planets.size; i++){
			buttons[i].render(delta);
			game.solarSystem.planets.get(i).halfdraw(batch, actor[i].localToStageCoordinates(new Vector2(0, 0))
					, actor[i].getRotation(), 1f);
			actor[i].addAction(Actions.sequence(Actions.moveTo(
        	(float) ((Math.cos(game.solarSystem.planets.get(i).angle*game.defense.sputnik.DEGREES_TO_RADIANS)*250
        			*game.solarSystem.planets.get(i).order)+pos[i].x), 
        	(float) (Math.sin(game.solarSystem.planets.get(i).angle*game.defense.sputnik.DEGREES_TO_RADIANS)*250
        			*game.solarSystem.planets.get(i).order)+pos[i].y)));
		}
	}
	
	public void newMenu(){
		stage.clear();
		table = new Table();
        table.setFillParent(true);
        table.align(Align.topLeft);
		table.add(back).row();
		table.add(addPlanet).row();
		table.add(showStats);
		scroller.clear();
		pTable = new Table();
		pTable.clear();
		sun = new Actor();
		if(game.solarSystem.count()>=2){
			pTable.add(sun).padLeft(-240);
		}else{
			pTable.add(sun).padLeft(-240*4);
		}
		pTable.row();
		scroller = new ScrollPane(pTable);
		scroller.setFillParent(true);
		Table ok = new Table();
		ok.clear();
		ok.setFillParent(true);
		ok.add(table).fill();
		ok.add(scroller).padTop(200).fill().expand();
		
		
		stage.addActor(ok);
		buildStage();
		for(Planet p : game.solarSystem.planets){
			int i = game.solarSystem.planets.indexOf(p, true);
			stage.act();
			stage.draw();
			pos[i] = actor[i].localToStageCoordinates(new Vector2(0, 0));
		}
	}
	
	public void buildStage(){
		for(int i = 0; i<game.solarSystem.planets.size; i++){
			buttons[i] = new MyButton(game, i);
			actor[i]=new Actor();
			pos[i] = (actor[i].localToStageCoordinates(new Vector2(0, 0)));
			actor[i].clearActions();
			actor[i].setOrigin(game.solarSystem.planets.get(i).radius/2, game.solarSystem.planets.get(i).radius/2);
			actor[i].addAction(Actions.forever(
        	Actions.rotateBy((float) ((Math.random()*2-1)*360), 50f)));

			if(i!=game.solarSystem.planets.size-1){
				pTable.add(actor[i]).width(game.solarSystem.planets.get(i).radius).
				height(game.solarSystem.planets.get(i).radius)
				.padRight(50*game.solarSystem.planets.get(i+1).space).left(); 
			}else{
				pTable.add(actor[i]).width(game.solarSystem.planets.get(i).radius).
				height(game.solarSystem.planets.get(i).radius)
				.padRight(800+(100*game.solarSystem.planets.get(i).order)).left(); 
			}

		}
        windowui = new WindowUi(game);
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			game.uicamera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			game.uicamera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			game.uicamera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			game.uicamera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			game.uicamera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			game.uicamera.translate(0, 3, 0);
		}
	}
	
	@Override
	public void show(){
        game.inputMultiplexer.addProcessor(stage);
        if(windowui!=null){
        	if(windowui.window!=null){
        		windowui.window.remove();
        	}
        }
        newMenu();
        j=0;
	}
	
	@Override
	public void hide(){
        game.inputMultiplexer.removeProcessor(stage);
	}
	@Override
	public void resize(int screenX, int screenY){
        game.uiviewport.update(screenX, screenY);
	}
	
	@Override
	public void dispose(){
		stage.dispose();
		batch.dispose();
	}

}
