package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.ship.Stats;
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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ShipMenu extends ScreenAdapter{
	
	Gam game;
	Stage stage;
	Image[] images = new Image[12];
	Label[] label = new Label[12];
	Label[] stats = new Label[12];
	Table[] tables = new Table[12];
	final TextButton[] buttons = new TextButton[12];
	TextButton back;
	
	SpriteBatch batch;
	Sprite bg;
	ScrollPane scroller;
	
	public ShipMenu(Gam gam) {
		game = gam;
		stage = new Stage(game.uiviewport);
        Skin skin = game.skin;
        batch = new SpriteBatch();
        bg = new Sprite((Texture)game.manager.get("bg/8.png"));

        back = new TextButton(" < ", skin);
        final Table scrollTable = new Table();
        scrollTable.add(back).fill();
        
        for(int i = 1;i<=10; i++){
        	images[i] = new Image((Texture) game.manager.get(("ships/"+i+".png")));
            label[i] = new Label(Stats.names[i], skin);
            label[i].setAlignment(Align.center);
            label[i].setWrap(true);
            stats[i] = new Label("Strength:"+ Stats.strength[i]+"\nSpeed:" +Stats.speed[i]
            		+ "\nAgility:" +Stats.agility[i]+ "\nPower:" +Stats.power[i]+ "\nWeaponry:"+Stats.weaponry[i], skin);
            stats[i].setAlignment(Align.left);
            if(game.solarSystem.hasShip(i)){
            	buttons[i] = new TextButton("Select", game.skin);
            }else{
            	buttons[i] = new TextButton("Buy for "+Stats.price[i-1], game.skin);
        		//buttons[i].setColor(new Color(1f, 0.1f, 1f, 1f));
            }
            tables[i] = new Table();
            tables[i].align(Align.left);
            tables[i].add(label[i]).row();
            tables[i].add(images[i]).width(200).height(200).padLeft(10).padRight(10).row();
            tables[i].add(stats[i]).row();
            tables[i].add(buttons[i]);
            scrollTable.add(tables[i]).left();
            
            final int m = i;
            buttons[m].addListener(new ClickListener() {
    	        @Override
    	        public void clicked (InputEvent event, float x, float y) {
    	        	if(game.solarSystem.hasShip(m)){
    		    		((Sound) game.manager.get("audio/sounds/click.ogg")).play();
						buttons[game.shipnum].setText("Select");
	        			buttons[m].setText("Selected");
    	        		buttons[game.shipnum].addAction(Actions.color(new Color(0f, 1f, 1f, 1f), 0.5f));
    	        		buttons[m].addAction(Actions.color(new Color(0.14f,1f,0.09f,1f), 0.5f));
    	        		System.out.println("my ship is "+m);
    	        		game.shipnum = m;
    	        		game.prefs.putInteger("shipnum", game.shipnum);
    	        		game.prefs.flush();
    	        	}else{
    	        		if(game.solarSystem.money>=Stats.price[m-1]){
    	        			final Window win = new Window("", game.skin);
    	        			win.setSize(200, 100);
    	        			Label price = new Label("Price: "+Stats.price[m-1], game.skin);
    	        			TextButton buy = new TextButton("buy", game.greenskin);
    	        			TextButton back = new TextButton("back", game.pinkskin);
    	        			win.align(Align.center);
    	        			win.add(price).row();
    	        			win.add(buy).left();
    	        			win.add(back).padLeft(20);
    	        			win.setPosition((800-200)/2, (480-100)/2);
    	        			
    	        			stage.addActor(win);
    	        			back.addListener(new ChangeListener() {
    	        		        @Override
    	        		        public void changed (ChangeEvent event, Actor actor) {
    	        		    		((Sound) game.manager.get("audio/sounds/click.ogg")).play();
    	        		        	win.remove();
    	        		        }
    	        		    });
    	        			buy.addListener(new ChangeListener() {
    	        		        @Override
    	        		        public void changed (ChangeEvent event, Actor actor) {
    	        		    		((Sound) game.manager.get("audio/sounds/buy.mp3")).play();
    	    	        			game.solarSystem.addShip(m);
    	    	        			buttons[m].setText("Select");
    	        		        	win.remove();
    	        		        }
    	        		    });
        	        		//buttons[m].setSkin(game.skin);
    	        		}
    	        	}
    	        }
    	    });
        }
        
      
        //scrollTable.add(l2).width(200).left();
        //scrollTable.add(l3).width(200).left();
        //scrollTable.add(l4).width(200).left();
        scrollTable.row();

        scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();

        stage.addActor(table);
        
/*
        buttons[1].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 1;
	        }
	    });
        buttons[2].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 2;
	        }
	    });
        buttons[3].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 3;
	        }
	    });
        buttons[4].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 4;
	        }
	    });
        buttons[5].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 5;
	        }
	    });
        buttons[6].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 6;
	        }
	    });
        buttons[7].addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.shipnum = 7;
	        }
	    });
	    */
		buttons[game.shipnum].setText("Selected");
		buttons[game.shipnum].addAction(Actions.color(new Color(0f, 1f, 1f, 1f), 0.5f));
		back.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.mainMenu);
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
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(game.uicamera.combined);
		game.tweenManager.update(delta);
		batch.begin();
		bg.draw(batch);
		//System.out.println(scroller.get);
		batch.end();
        stage.act();
        stage.draw();
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
