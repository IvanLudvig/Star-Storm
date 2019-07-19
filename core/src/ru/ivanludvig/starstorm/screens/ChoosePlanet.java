package ru.ivanludvig.starstorm.screens;

import ru.ivanludvig.planets.Planet;
import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.Gam;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ChoosePlanet extends ScreenAdapter{
	
	Gam game;
	Stage stage;
	Texture bg;
	TextButton back;
	TextButton ok;
	SpriteBatch batch;
	Image[] images = new Image[21];
	Table[] tables = new Table[21];
	Label[] label = new Label[21];
	final Actor[] actors = new Actor[21];
	Planet[] p = new Planet[21];
	ScrollPane scroller;
	int g;
	
	
	public ChoosePlanet(Gam gam, int n){
		this.game = gam;
		create();
	}
	
	public void create(){
		stage = new Stage(game.uiviewport);
		batch = new SpriteBatch();
        bg = game.manager.get("solar/6.png");
        back = new TextButton("<", game.skin);
		ok = new TextButton("ok", game.skin);
		ok.setVisible(false);
        final Table scrollTable = new Table();
        loop:
        	for(int i = 1;i<=20; i++){
                	images[i] = new Image((Texture) game.manager.get(("planets/"+i+".png")));
                	actors[i] = new Actor();
                	actors[i].setSize(Stats.prad[i], Stats.prad[i]);
                	actors[i].setOrigin(Stats.prad[i]/2, Stats.prad[i]/2);
                    label[i] = new Label("hp:"+(int)Stats.php[i]+ "\ngravity:"+(int)Stats.pgr[i]+ "\nresources:"+
                    (int)Stats.pres[i], game.skin);
                    label[i].setAlignment(Align.right);
                    tables[i] = new Table();
                    tables[i].align(Align.right);
                    tables[i].add(actors[i]).width(100).height(100).padRight(20).padTop(20).row();
                    tables[i].add(label[i]).row();
                    //tables[i].add(buttons[i]);
                    p[i]=new Planet(game, i, -1);
                    scrollTable.add(tables[i]).padLeft(20).left();
                    scrollTable.align(Align.center);
                    if(i%5==0){
                    	scrollTable.row();
                    }
                	for(int j=0; j<game.solarSystem.planets.size; j++){
                		if (game.solarSystem.planets.get(j).v==i){
                			actors[i].setColor(actors[i].getColor().r, actors[i].getColor().g,
                					actors[i].getColor().b, 0.2f);
                			continue loop;
                		}
                	}
                    final int m = i;
                    actors[i].addListener(new ClickListener() {
            	        @Override
            	        public void clicked (InputEvent event, float x, float y) {
            	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
            	        	//buttons[game.shipnum].addAction(Actions.color(new Color(0f, 1f, 1f, 1f), 0.5f));
            	        	actors[m].addAction(Actions.forever(Actions.parallel(
            	        			Actions.sequence(Actions.moveBy(0, 5, 0.5f), Actions.moveBy(0, -5, 0.5f)), 
            	        			Actions.sequence(Actions.alpha(0.7f, 0.5f), Actions.alpha(1f, 0.5f)))));
            	        	if(g!=0){
            	        		actors[g].clearActions();
            	        	}
        					ok.setVisible(true);
        					stage.addActor(ok);
        					g=m;
            	       		//game.shipnum = m;
            	        }
            	    });
        	}

        scrollTable.row();
        scrollTable.align(Align.left);

        scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.align(Align.left);
        final Table t = new Table();
        t.setFillParent(true);
        t.align(Align.left);
        t.add(back).fill().row();
        t.add(ok);
        table.add(t);
        table.add(scroller).fill().expand();
        

        stage.addActor(table);
        
		back.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
	        	game.setScreen(game.solarMenu);
	        }
	    });
		ok.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
	    		((Sound) game.manager.get("audio/sounds/click.mp3")).play();
				game.solarSystem.addPlanet(g);	
				actors[g].clearActions();
	        	game.setScreen(game.solarMenu);
			}
		});
	}
	
	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(game.uicamera.combined);
		batch.begin();
		batch.draw(bg, -100, -400);
		//System.out.println(scroller.get);
		for(int o=1; o<=20 ;o++){
			p[o].halfdraw(batch, actors[o].localToStageCoordinates(new Vector2(0, 0)), 0, actors[o].getColor().a);
		}
		batch.end();
        stage.act();
        stage.draw();
	}
	
	@Override
	public void show(){
		create();
        game.inputMultiplexer.addProcessor(stage);
		ok.setVisible(false);
		for(Actor actor : stage.getActors()){
			actor.clearActions();
		}
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
	}

}
