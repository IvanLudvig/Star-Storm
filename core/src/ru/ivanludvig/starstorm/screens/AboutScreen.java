package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.starstorm.Gam;
import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class AboutScreen extends ScreenAdapter{
	
	Gam game;
	Stage stage;
	Table table;
	Table all;
	TextButton back;
	Label dev;
	Label thanks;
	Sprite bg;
	SpriteBatch batch;
	
	public AboutScreen(Gam gam){
		game = gam;
		
        bg = new Sprite((Texture) game.manager.get(("bg/7.png")));
        batch = new SpriteBatch();
        
		stage = new Stage(game.uiviewport);
		table = new Table();
		all = new Table();
        back = new TextButton(" < ", game.skin);
        dev = new Label("Developer: Ivan Ludvig", game.skin);
        thanks = new Label("Build your solar system, upgrade your planets and protect them from asteroids. "
        		+ " There are 10 ships with different strength, speed, agility, power and weaponry with unique effects and sounds."
        		+"Your goal is to create your own solar system from a choice of 20 planets, protect them from cosmic threats and upgrade them."+
        		" The arcade mode doesn't involve building a solar system, you have to try to beat your high score.", game.skin);
        thanks.setWrap(true);
        thanks.setAlignment(Align.left);
        table.align(Align.top);
        all.align(Align.center);
        table.add(dev).padTop(20).row();
        table.add(thanks).padLeft(10).fill().expand();
        table.pack();
        all.add(back).left().fill();
        all.add(table).fill().expand();
        all.setFillParent(true);
        all.pack();
        
        stage.addActor(all);
		back.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
	        }
	    });
		
		bg.setPosition(0, -200);
		bg.setOrigin(300, 300);
	
		Tween.to(bg, SpriteTween.OPACITY, 10f)
		.target(0.65f)
		.ease(TweenEquations.easeInOutElastic)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.tweenManager);

		
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
	}
	
	@Override 
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(game.uicamera.combined);
		batch.begin();
		bg.draw(batch);
		batch.end();
		stage.act();
		stage.draw();
		game.tweenManager.update(delta);
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
