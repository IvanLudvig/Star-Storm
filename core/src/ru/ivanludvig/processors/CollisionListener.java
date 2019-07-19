package ru.ivanludvig.processors;


import ru.ivanludvig.planets.Ammo;
import ru.ivanludvig.starstorm.Dust;
import ru.ivanludvig.starstorm.Gam;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class CollisionListener implements ContactListener {
	
	float PTM = 32f;
	Gam game;
	
	public CollisionListener(Gam game){
		this.game = game;
	}

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    //	if((contact.getFixtureA().getBody().getUserData()==game.defense.sputnik)||
    //			(contact.getFixtureB().getBody().getUserData()==game.defense.sputnik)){

        		//System.out.println("A: "+contact.getFixtureA().getBody().getUserData().getClass().toString());
        		//System.out.println("B: "+contact.getFixtureB().getBody().getUserData().getClass().toString());
        		if(((contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.starstorm.Dust"))||
    	    			(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.starstorm.Dust")))){
            		if(((contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Planet"))||
        	    			(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Planet")))){
            			for(int i =0; i<game.defense.dustmanager.dust.size; i++){
            				if((contact.getFixtureA().getBody().getUserData()==game.defense.dustmanager.dust.get(i))){
            					System.out.println(game.defense.dustmanager.dust.get(i).getPos());
                        		game.defense.planet.gamehp-=game.defense.dustmanager.dust.get(i).radius;
                        		game.defense.shooter.dustPlanet(i, ((Dust)contact.getFixtureA().getBody().getUserData()).radius);
            					
            				}else if(contact.getFixtureB().getBody().getUserData()==game.defense.dustmanager.dust.get(i)){
            					System.out.println(game.defense.dustmanager.dust.get(i).getPos());
                    			game.defense.planet.gamehp-=game.defense.dustmanager.dust.get(i).radius;
                    			game.defense.shooter.dustPlanet(i, ((Dust)contact.getFixtureB().getBody().getUserData()).radius);
            				}
            			}
            		}
            		if(((contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.shoot.Shot"))||
        	    		(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.shoot.Shot")))){
            			for(int i =0; i<game.defense.dustmanager.dust.size; i++){
            				if((contact.getFixtureA().getBody().getUserData()==game.defense.dustmanager.dust.get(i))||
            						(contact.getFixtureB().getBody().getUserData()==game.defense.dustmanager.dust.get(i))){
            					game.defense.score += 1;
            					game.defense.dustmanager.remove(i);
            				}
            			}
            			for(int i =0; i<game.defense.shooter.shots.size; i++){
            				if(game.defense.shooter.shots.get(i)!=null){
            					if((contact.getFixtureA().getBody().getUserData()==game.defense.shooter.shots.get(i))){
            						game.defense.shooter.destroy(i, ((Dust)contact.getFixtureB().getBody().getUserData()).radius);
            						break;
            					}
            					if(contact.getFixtureB().getBody().getUserData()==game.defense.shooter.shots.get(i)){
            						game.defense.shooter.destroy(i, ((Dust)contact.getFixtureA().getBody().getUserData()).radius);
            						break;
            					}
            					}
            				}
            			}
            		}
        	if(((contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.shoot.Shot"))||
    	    		(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.shoot.Shot")))){
        		if(((contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Planet"))||
    	    			(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Planet")))){
        			game.defense.planet.gamehp-=3*game.defense.sputnik.weaponry;
        			for(int i =0; i<game.defense.shooter.shots.size; i++){
        				if((contact.getFixtureA().getBody().getUserData()==game.defense.shooter.shots.get(i))||
        						(contact.getFixtureB().getBody().getUserData()==game.defense.shooter.shots.get(i))){
        						game.defense.shooter.destroy(i, 0f);
        				}
        			}
        		}
            	if((contact.getFixtureB().getBody().getUserData()==game.defense.sputnik)||
            			(contact.getFixtureA().getBody().getUserData()==game.defense.sputnik)){
        			game.defense.sputnik.hp-=3*game.defense.sputnik.weaponry;
        			System.out.println("weap "+game.defense.sputnik.weaponry);
        			for(int i =0; i<game.defense.shooter.shots.size; i++){
        				if((contact.getFixtureA().getBody().getUserData()==game.defense.shooter.shots.get(i))||
        						(contact.getFixtureB().getBody().getUserData()==game.defense.shooter.shots.get(i))){
        						game.defense.shooter.destroy(i, 0f);
        				}
        			}
            	}
            		
        	}
        	
        	if(contact.getFixtureA().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Ammo")){
            	if(contact.getFixtureB().getBody().getUserData()==game.defense.sputnik){
            		game.defense.resmanager.delRes((Ammo)contact.getFixtureA().getBody().getUserData());
            	}
        	}if(contact.getFixtureB().getBody().getUserData().getClass().toString().equals("class ru.ivanludvig.planets.Ammo")){
            	if(contact.getFixtureA().getBody().getUserData()==game.defense.sputnik){
            		game.defense.resmanager.delRes((Ammo)contact.getFixtureB().getBody().getUserData());
            	}
        	}
    //	}
    }


    @Override
    public void beginContact(Contact contact) {

    }

};