package ru.ivanludvig.starstorm;

import ru.ivanludvig.tween.ButtonTweeen;
import ru.ivanludvig.tween.SpriteTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.equations.Linear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;

public class Ui {
	
	Gam game;
	public Touchpad tp;
	Window win;
	Label scoreLabel;
	Label hpLabel;
	Label ammoLabel;
	TextButton shotscount;
	TextButton back;
	TextButton greenback;
	Table table;

	public Ui(Gam gam){
		game = gam;
		tp = new Touchpad(20, game.skin);
		tp.setPosition(25, 25);
		
        TextButton pause = new TextButton("Pause", game.skin);
        shotscount = new TextButton("I", game.skin);
        if(game.defense.sputnik.ccount==1){
        	shotscount.setText("I");
        }else if(game.defense.sputnik.ccount==2){
        	shotscount.setText("II");
        }else{        
        	shotscount.setText("III");
        }
        shotscount.setPosition(700, 100);
        
        scoreLabel = new Label("score:"+game.defense.score, game.skin);
        hpLabel = new Label("hp:"+game.defense.sputnik.hp, game.skin);
        ammoLabel = new Label("ammo:"+game.defense.sputnik.gameammo, game.skin);
		greenback = new TextButton("Back", game.greenskin);
		back = new TextButton("Back", game.pinkskin);
    	Tween.to(shotscount, ButtonTweeen.OPACITY, 5f)
    	.target(0.5f)
    	.ease(TweenEquations.easeInQuad)
    	.start(game.defense.tweenManager);
    	
		final TextButton sure = new TextButton("Back.", game.pinkskin);
    	sure.setColor(sure.getColor().r, sure.getColor().g, sure.getColor().b, 0f);
        final Table tab = new Table();
        tab.setFillParent(true);
        tab.align(Align.topLeft);
        tab.add(pause).pad(1).padLeft(-5);
        tab.add(sure).pad(1);
        tab.row();
        tab.add(scoreLabel).pad(1).left().padLeft(15);
        tab.row();
        tab.add(hpLabel).pad(1).left().padLeft(15);
        tab.row();
        tab.add(ammoLabel).pad(1).left().padLeft(15);
        tab.pack();
        
		win = new Window("", game.skin);
		win.setSize(200, 100);
		TextButton resume = new TextButton("resume", game.greenskin);
		TextButton exit = new TextButton("exit", game.pinkskin);
		Label dont = new Label("Pause", game.skin);
		win.align(Align.center);
		win.add(dont).row();
		win.add(resume).left();
		win.add(exit).padLeft(20);
		win.setPosition((800-200)/2, (480-100)/2);
    	win.setVisible(false);
		
		game.defense.stage.addActor(win);
		game.defense.stage.addActor(tp);
		
		resume.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.defense.pause=0;
	        	win.setVisible(false);
	        }
	    });
		exit.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
	        }
	    });
		pause.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.defense.pause=1;
	    		((Sound) game.manager.get("audio/sounds/click.ogg")).play();
	        	win.setVisible(true);
	        }
	    });
		greenback.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
	        }
	    });
		back.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
	        }
	    });
		shotscount.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.ogg")).play();
	        	shotscount.setColor(shotscount.getColor().r, shotscount.getColor().g, shotscount.getColor().b, 0.8f);
	        	Tween.to(shotscount, ButtonTweeen.OPACITY, 5f)
	        	.target(0.5f)
	        	.ease(TweenEquations.easeInQuad)
	        	.start(game.defense.tweenManager);
	        	if(game.defense.sputnik.ccount<game.defense.sputnik.count){
	        		game.defense.sputnik.ccount++;
	        	}else{
	        		game.defense.sputnik.ccount = 1;
	        	}
	            if(game.defense.sputnik.ccount==1){
	            	shotscount.setText("I");
	            }else if(game.defense.sputnik.ccount==2){
	            	shotscount.setText("II");
	            }else{        
	            	shotscount.setText("III");
	            }
	        	System.out.println(game.defense.sputnik.ccount);
	        }
	    });
		
		game.defense.stage.addActor(tab);
		game.defense.stage.addActor(shotscount);
	}
	
	public void render(float delta){
		scoreLabel.setText("score:"+game.defense.score);
		hpLabel.setText("hp:"+game.defense.sputnik.hp);
		ammoLabel.setText("ammo:"+game.defense.sputnik.gameammo);
	}
	
	public void newGame(){
        if(game.defense.sputnik.ccount==1){
        	shotscount.setText("I");
        }else if(game.defense.sputnik.ccount==2){
        	shotscount.setText("II");
        }else{        
        	shotscount.setText("III");
        }
		if(table!=null){
			table.remove();
		}
		for(Actor actor : game.defense.stage.getActors()){
			actor.setColor(actor.getColor().r, actor.getColor().g, actor.getColor().b, 0f);
		}
		for(Actor actor : game.defense.stage.getActors()){
			actor.clearActions();
			actor.addAction(Actions.fadeIn(1f));
		}
    	win.setVisible(false);
    	game.defense.pause = 0;
	}
	
	public void lost(){
		for(Actor actor : game.defense.stage.getActors()){
			actor.addAction(Actions.fadeOut(3f));
		}
		game.defense.sputnik.sprite.setAlpha(0.2f);
		Label gScore = new Label("Score:"+game.defense.score, game.pinkskin);
		if(game.prefs.getInteger("highscore")<game.defense.score){
			game.prefs.putInteger("highscore", game.defense.score);
			game.prefs.flush();
		}
		Label highScore = new Label("High Score:"+game.prefs.getInteger("highscore"), game.pinkskin);
		//back.setSize(100, 50);
		table = new Table();
        table.setFillParent(true);
        table.align(Align.top);
		table.add(gScore).padTop(200).row();
		table.add(highScore).row();
		table.add(back);
		//table.setPosition(200, 200);
		game.defense.stage.addActor(table);
		
		Tween.to(game.defense.gameover, SpriteTween.OPACITY, 2f)
		.target(0.6f)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.defense.tweenManager);
	}
	
	public void comUI(){
		for(Actor actor : game.defense.stage.getActors()){
			actor.addAction(Actions.fadeOut(3f));
		}
		game.defense.sputnik.sprite.setAlpha(0.2f);
		Label rewardlab = new Label("reward:"+game.defense.level.fullReward(), game.greenskin);
		//Label highScore = new Label("High Score:"+game.prefs.getInteger("highscore"), game.pinkskin);
		//back.setSize(100, 50);
		table = new Table();
		table.clear();
        table.setFillParent(true);
        table.align(Align.top);
		table.add(rewardlab).padTop(200).row();
		//table.add(highScore).row();
		table.add(greenback);
		//table.setPosition(200, 200);
		game.defense.stage.addActor(table);
		
		Tween.to(game.defense.complete, SpriteTween.OPACITY, 2f)
		.target(0.6f)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.defense.tweenManager);
	}
	
	public void lostui(){
		for(Actor actor : game.defense.stage.getActors()){
			actor.addAction(Actions.fadeOut(3f));
		}
		game.defense.sputnik.sprite.setAlpha(0.2f);
		Label rewardlab = new Label("reward:"+game.defense.level.fullReward(), game.greenskin);
		//Label highScore = new Label("High Score:"+game.prefs.getInteger("highscore"), game.pinkskin);
		//back.setSize(100, 50);
		table = new Table();
		table.clear();
        table.setFillParent(true);
        table.align(Align.top);
		table.add(rewardlab).padTop(200).row();
		//table.add(highScore).row();
		table.add(greenback);
		//table.setPosition(200, 200);
		game.defense.stage.addActor(table);
		
		Tween.to(game.defense.complete, SpriteTween.OPACITY, 2f)
		.target(0.6f)
		.ease(Linear.INOUT)
		.repeatYoyo(Tween.INFINITY, 0)
		.start(game.defense.tweenManager);
	}
	
	public static Touchpad createPad(Vector2 pos){
		TouchpadStyle touchpadStyle;
		Skin touchpadSkin;
		Drawable touchBackground;
		Drawable touchKnob;
		Texture blockTexture;
		Sprite blockSprite;
		float blockSpeed;
		//Create a touchpad skin	
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", new Texture("pad/touchBackground.png"));
		//Set knob image
		touchpadSkin.add("touchKnob", new Texture("pad/touchKnob.png"));
		//Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		//Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		touchKnob.setMinHeight(60);
		touchKnob.setMinWidth(60);
		
		Touchpad touchpad;
		//Create new TouchPad with the created style
		touchpad = new Touchpad(10, touchpadStyle);
		//setBounds(x,y,width,height)
		touchpad.setColor(touchpad.getColor().r, touchpad.getColor().g,
				touchpad.getColor().b, 0.65f);
        touchpad.setBounds(pos.x-375, pos.y-225, 150, 150);

		//Create a Stage and add TouchPad
		//Create block sprite
		blockTexture = new Texture(Gdx.files.internal("pad/block.png"));
		blockSprite = new Sprite(blockTexture);
		//Set position to centre of the screen
		blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);

		blockSpeed = 5;
		
		return touchpad;
		
		
	}
}
