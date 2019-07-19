package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class MainMenu extends ScreenAdapter{
	
	Gam game;
	Stage stage;
	SpriteBatch batch;
	Sprite bg;
	Sprite name;
	final ImageButton ship;
	final ImageButton solar;
	final ImageButton about;
	ParticleEffect effect;
	
	public MainMenu(Gam gam) {
		game = gam;
		stage = new Stage(game.uiviewport);
        Skin skin = game.skin;
        batch = new SpriteBatch();
        bg = new Sprite((Texture) game.manager.get(("bg/7.png")));
		name = new Sprite ((Texture) game.manager.get(("menu/name.png")));

        final Label text = new Label("Star Storm", skin);
        text.setAlignment(Align.center);
        text.setWrap(true);
        
        Label mode = new Label("...   ...   ... \n Solar Sytem mode", skin);
        mode.setAlignment(Align.center);
        mode.setWrap(true);
        
        TextButton play = new TextButton("Arcade mode", skin);
        ship = new ImageButton(new TextureRegionDrawable(
        		new TextureRegion((Texture) game.manager.get("menu/shipicon.png"))));
        solar = new ImageButton(new TextureRegionDrawable(
        		new TextureRegion((Texture) game.manager.get("menu/solar-system.png"))));
        about = new ImageButton(new TextureRegionDrawable(
        		new TextureRegion((Texture) game.manager.get("menu/info.png"))));
        ship.setColor(ship.getColor().r, ship.getColor().g, ship.getColor().b, 0.6f);
        solar.setColor(solar.getColor().r, solar.getColor().g, solar.getColor().b, 0.6f);
        about.setColor(about.getColor().r, about.getColor().g, about.getColor().b, 0.6f);
        final Table table = new Table();
        table.setFillParent(true);
        table.align(Align.top);
        //table.add(text).padTop(50);
        table.row();
        table.add(play).padTop(150).width(200).height(70);
        table.row();
        table.add(mode).padTop(10);
        table.row();
        table.add(ship).padTop(20).width(70).height(70).left();
        table.add(solar).padTop(20).padLeft(-70).width(70).height(70);

        about.setSize(30, 30);
        about.setPosition(10, 10);
        stage.addActor(table);
        stage.addActor(about);
        
        //stage.setDebugAll(true);
		effect = new ParticleEffect((ParticleEffect) game.manager.get("data/stars-slow.p"));
		effect.reset();
        
		name.setBounds(150, 320, 500, 187);
		bg.setPosition(0, -200);
		bg.setOrigin(300, 300);
		name.setOrigin(250, 90);
		//name.setRotation(-2);
		
		Tween.to(name, SpriteTween.POS_XY, 3f)
		.target(150, 330)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		Tween.to(bg, SpriteTween.OPACITY, 10f)
		.target(0.65f)
		.ease(TweenEquations.easeInOutElastic)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		//Tween.to(name, SpriteTween.ROTATION, 2f)
		//.target(2)
		//.ease(Linear.INOUT)
		//.repeatYoyo(Tween.INFINITY, 0)
		//.start(game.tweenManager);
		
		Tween.to(bg, SpriteTween.POS_XY, 20f)
		.target(-150, -200)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		
		Tween.to(bg, SpriteTween.ROTATION, 6f)
		.target(10)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);
		
		play.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	    		game.defense.setLevel(null);
	        	game.setScreen(game.defense);
	        }
	    });
		ship.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.shipMenu);
	        }
	    });
		solar.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.solarMenu);
	        }
	    });
		about.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.aboutScreen);
	        }
	    });
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(game.uicamera.combined);
		batch.begin();
		bg.draw(batch);
		name.draw(batch);
		effect.setPosition(300, 0);
		effect.draw(batch, delta);
		batch.end();
        stage.act();
        stage.draw();
		game.tweenManager.update(delta);
		
		
        if(ship.isOver()){
        	ship.setColor(ship.getColor().r, ship.getColor().g, ship.getColor().b, 1f);
        }else{
        	ship.setColor(ship.getColor().r, ship.getColor().g, ship.getColor().b, 0.6f);
        }
        if(solar.isOver()){
        	solar.setColor(solar.getColor().r, solar.getColor().g, solar.getColor().b, 1f);
        }else{
        	solar.setColor(solar.getColor().r, solar.getColor().g, solar.getColor().b, 0.6f);
        }
	}
	
	@Override
	public void show(){
        game.inputMultiplexer.addProcessor(stage);
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
