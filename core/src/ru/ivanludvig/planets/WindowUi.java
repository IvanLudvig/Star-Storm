package ru.ivanludvig.planets;

import ru.ivanludvig.starstorm.Gam;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class WindowUi {

	Gam game;
	public Window window;
	public Window win[] = new Window[20];
	public Window stats;
	
	TextButton xs;
	Label s;
	
	public WindowUi(Gam gam){
		game = gam;
		
		newStats();
		stats.setPosition(250, 200);
    	stats.setVisible(false);
		/*
		xs = new TextButton("x", game.pinkskin);
		s = new Label("Stats:\n ", game.pinkskin);
		stats = new Window("", game.pinkskin);
		s = new Label("Stats:\n ", game.pinkskin);
		for(int i=0;i<game.solarSystem.planets.size; i++){
			s.setText(s.getText()+"\n"+game.solarSystem.planets.get(i).stats());
		}
		stats.add(s);
		stats.add(xs).row();
		stats.setPosition(250, 200);
    	stats.setVisible(false);
		stats.pack();
		*/
		
		for(int i = 0; i<game.solarSystem.planets.size; i++){
			final int m = i;
			win[i] = new Window("", game.skin);
			win[i].setSize(200, 200);
			win[i].align(Align.left);
			win[i].setPosition(game.solarMenu.actor[i].localToStageCoordinates(new Vector2(0,0)).x, 
					game.solarMenu.actor[i].localToStageCoordinates(new Vector2(0,0)).y);
			game.solarMenu.actor[i].addListener(new ClickListener() {
    	        @Override
    	        public void clicked (InputEvent event, float x, float y) {
    	        	win[m].remove();
    				win[m] = new Window("", game.skin);
    	        	win[m].setSize(250, 200);
    	        	Label label = new Label("Upgrade   +points   cost", game.skin);
    	        
    	        	final Label p1 = new Label((int)game.solarSystem.upPoints(m, 1)+"", game.skin);
    	        	final Label p2 = new Label((int)game.solarSystem.upPoints(m, 2)+"", game.skin);
    	        	final Label p3 = new Label((int)game.solarSystem.upPoints(m, 3)+"", game.skin);
    	        	final Label c1 = new Label((int)game.solarSystem.planets.get(m).hpCost(game.solarSystem.upPoints(m, 1))+"", 
        			game.skin);
    	        	final Label c2 = new Label((int)game.solarSystem.planets.get(m).grvCost(game.solarSystem.upPoints(m, 2))+"", 
        			game.skin);
    	        	final Label c3 = new Label((int)game.solarSystem.planets.get(m).resCost(game.solarSystem.upPoints(m, 3))+"", 
        			game.skin);
    	        	TextButton uph = new TextButton("hp", game.skin);
    	        	TextButton upgr = new TextButton("grv", game.skin);
    	        	TextButton upr = new TextButton("res", game.skin);
    	        	TextButton no = new TextButton("x", game.skin);
    	        	win[m].align(Align.left);
    	        	win[m].add(label);
    	        	win[m].add(no).width(40).height(40).right().row();
    	        	win[m].add(uph).left().padTop(-10);
    	        	win[m].add(p1).left().padLeft(-100).padTop(0);
    	        	win[m].add(c1).left().padLeft(-100).row();
    	        	win[m].add(upgr).left().padTop(-10);
    	        	win[m].add(p2).left().padLeft(-100).padTop(0);
    	        	win[m].add(c2).left().padLeft(-100).row();
    	        	win[m].add(upr).left().padTop(-10);
    	        	win[m].add(p3).left().padLeft(-100).padTop(0);
    	        	win[m].add(c3).left().padLeft(-100);
    	        	win[m].setPosition(game.solarMenu.actor[m].localToStageCoordinates(new Vector2(0,0)).x, 
    	        			game.solarMenu.actor[m].localToStageCoordinates(new Vector2(0,0)).y);
    	        	win[m].setVisible(true);
    	        	win[m].pack();
    	        	game.solarMenu.stage.addActor(win[m]);
    	        	
    				no.addListener(new ChangeListener() {
    			        @Override
    			        public void changed (ChangeEvent event, Actor actor) {
    			        	win[m].setVisible(false);
    			        }
    			    });
    				uph.addListener(new ChangeListener() {
    			        @Override
    			        public void changed (ChangeEvent event, Actor actor) {
    			        	game.solarSystem.upgrade(m, 1);
    			        	p1.setText((int)game.solarSystem.upPoints(m, 1)+"");
    			        	c1.setText((int)game.solarSystem.planets.get(m).hpCost(game.solarSystem.upPoints(m, 1))+"");
    			        }
    			    });
    				upgr.addListener(new ChangeListener() {
    			        @Override
    			        public void changed (ChangeEvent event, Actor actor) {
    			        	final Window grvwin = new Window("", game.skin);
    			        	TextButton add = new TextButton("+"+game.solarSystem.upPoints(m, 2), game.greenskin);
    			        	TextButton sub = new TextButton("-"+game.solarSystem.upPoints(m, 2), game.greenskin);
    			        	TextButton out = new TextButton("cancel", game.pinkskin);
    			        	grvwin.add(add);
    			        	grvwin.add(sub);
    			        	grvwin.add(out);
    	    	        	grvwin.pack();
    	    	        	grvwin.setPosition((800-grvwin.getWidth())/2,  (480-grvwin.getHeight())/2);
    	    	        	game.solarMenu.stage.addActor(grvwin);
    	    				add.addListener(new ChangeListener() {
    	    			        @Override
    	    			        public void changed (ChangeEvent event, Actor actor) {
    	    			        	game.solarSystem.upgrade(m, 2);
    	    			        	grvwin.remove();
    	    			        }
    	    			    });
    	    				sub.addListener(new ChangeListener() {
    	    			        @Override
    	    			        public void changed (ChangeEvent event, Actor actor) {
    	    			        	game.solarSystem.upgrade(m, -2);
    	    			        	grvwin.remove();
    	    			        }
    	    			    });
    	    				out.addListener(new ChangeListener() {
    	    			        @Override
    	    			        public void changed (ChangeEvent event, Actor actor) {
    	    			        	grvwin.remove();
    	    			        }
    	    			    });
    			        	p2.setText((int)game.solarSystem.upPoints(m, 2)+"");
    			        	c2.setText((int)game.solarSystem.planets.get(m).grvCost(game.solarSystem.upPoints(m, 2))+"");
    			        }
    			    });
    				upr.addListener(new ChangeListener() {
    			        @Override
    			        public void changed (ChangeEvent event, Actor actor) {
    			        	game.solarSystem.upgrade(m, 3);
    			        	p3.setText((int)game.solarSystem.upPoints(m, 3)+"");
    			        	c3.setText((int)game.solarSystem.planets.get(m).resCost(game.solarSystem.upPoints(m, 3))+"");
    			        }
    			    });
	        }
	    });
			win[i].setVisible(false);
			game.solarMenu.stage.addActor(win[i]);
		}
		
		game.solarMenu.stage.addActor(stats);
	}
	
	public void render(float delta){
		if((game.solarSystem.planets.size==0)&&(game.solarMenu.j==0)){
			System.out.println("hey there");
			window = new Window("", game.skin);
			window.setSize(300, 150);
			TextButton add = new TextButton("add planets", game.skin);
			TextButton no = new TextButton("back", game.skin);
			Label dont = new Label("Hey there\nYou don't have any planets", game.skin);
			window.align(Align.center);
			window.add(dont).row();
			window.add(add).left();
			window.add(no).padLeft(-60);
			window.setPosition(250, 200);
			
			game.solarMenu.stage.addActor(window);
			game.solarMenu.j = 1;
			add.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		        	game.setScreen(game.choosePlanet);
		        }
		    });
			no.addListener(new ChangeListener() {
		        @Override
		        public void changed (ChangeEvent event, Actor actor) {
		        	game.setScreen(game.mainMenu);
		        }
		    });
		}
	}
	
	public void update(){
		if(stats.isVisible()){
			Vector2 pos = new Vector2(stats.getX(), stats.getY());
			stats.remove();
			newStats();
			stats.setVisible(true);
			stats.setPosition(pos.x, pos.y);
		}else{
			stats.remove();
			newStats();
			stats.setVisible(false);
		}
	}
	
	public void max(){
    	final Window winj = new Window("", game.skin);
    	Label leb = new Label("Currently you can have only "+game.solarSystem.maxplanet+" planets", game.skin);
    	TextButton x = new TextButton("X", game.pinkskin);
    	winj.add(leb).row();
    	winj.add(x);
    	winj.pack();
    	winj.setPosition((800-winj.getWidth())/2,  (480-winj.getHeight())/2);
    	game.solarMenu.stage.addActor(winj);
		x.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	winj.remove();
	        }
	    });
	}
	private void newStats(){
		stats = new Window("", game.pinkskin);
		xs = new TextButton("x", game.pinkskin);
		s = new Label("Stats:\n ", game.pinkskin);
		for(int i=0;i<game.solarSystem.planets.size; i++){
			s.setText(s.getText()+"\n"+game.solarSystem.planets.get(i).stats());
		}
		stats.add(s);
		stats.add(xs).row();
		stats.pack();
		game.solarMenu.stage.addActor(stats);
		
		xs.addListener(new ChangeListener() {
	        @Override
	        public void changed (ChangeEvent event, Actor actor) {
	        	stats.setVisible(false);
	        }
	    });
	}
}
