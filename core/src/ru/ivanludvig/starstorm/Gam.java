package ru.ivanludvig.starstorm;

import ru.ivanludvig.planets.Planet;
import ru.ivanludvig.planets.SolarSystem;
import ru.ivanludvig.ship.Sputnik;
import ru.ivanludvig.ship.Stats;
import ru.ivanludvig.starstorm.screens.AboutScreen;
import ru.ivanludvig.starstorm.screens.ChoosePlanet;
import ru.ivanludvig.starstorm.screens.Defense;
import ru.ivanludvig.starstorm.screens.MainMenu;
import ru.ivanludvig.starstorm.screens.ShipMenu;
import ru.ivanludvig.starstorm.screens.SolarMenu;
import ru.ivanludvig.tween.ButtonTweeen;
import ru.ivanludvig.tween.PlanetTween;
import ru.ivanludvig.tween.SpriteTween;
import ru.ivanludvig.tween.SputnikTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Gam extends Game {

	public Defense defense;
	public OrthographicCamera uicamera;
	public Viewport uiviewport; 
	public Viewport suiviewport; 
	
	public InputMultiplexer inputMultiplexer;
	public TweenManager tweenManager;
	public AssetManager manager;
	public Preferences prefs;
	
	public Skin skin;
	public Skin pinkskin;
	public Skin greenskin;
	public BitmapFont greenfont;
	public BitmapFont pinkfont;
	
	public MainMenu mainMenu;
	public ShipMenu shipMenu;
	public SolarMenu solarMenu;
	public SolarSystem solarSystem;
	public ChoosePlanet choosePlanet;
	public AboutScreen aboutScreen;
	
	public int shipnum = 1;
	public Music music;
	
	@Override
	public void create () {
		uicamera = new OrthographicCamera();
		uiviewport = new StretchViewport(800, 480, uicamera);
		suiviewport = new StretchViewport(1200, 720, uicamera);
		inputMultiplexer = new InputMultiplexer();
		manager = new AssetManager();
		prefs = Gdx.app.getPreferences("preferences");
		load();
		
		skin = manager.get("nskin/neon-ui-alpha.json");
		pinkskin = manager.get("nskin/pink-ui.json");
		greenskin = manager.get("nskin/green-ui.json");
		greenfont = greenskin.getFont("font");
		pinkfont = pinkskin.getFont("font");
		greenfont.setColor(0.47f, 1f, 0.45f, 1f);
		pinkfont.setColor(1f, 0.52f, 0.73f, 1f);
		//greenfont.getData().setScale(1.25f);
		//pinkfont.getData().setScale(1.25f);
		
		Tween.registerAccessor(Sputnik.class, new SputnikTween());
		Tween.registerAccessor(Planet.class, new PlanetTween());
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		Tween.registerAccessor(Actor.class, new ButtonTweeen());
		tweenManager = new TweenManager();
		
		//prefs.putString("solar-system", "");
		solarSystem = new SolarSystem(this);
		defense = new Defense(this);
		defense.ui = new Ui(this);
		mainMenu = new MainMenu(this);
		shipMenu = new ShipMenu(this);
		solarMenu = new SolarMenu(this);
		aboutScreen = new AboutScreen(this);
		choosePlanet = new ChoosePlanet(this, 1);


		music = manager.get("audio/menu.ogg");
		music.setLooping(true);
		music.setVolume(1f);
		music.play();
		
		solarSystem.create();
		this.setScreen(mainMenu);
		
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void render () {
		super.render();
		uicamera.update();
		solarSystem.render();
		//defense.render();
		
		/*
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		 
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		*/
	}
	
	public void load(){
		for(int i = 1; i<=20; i++){
			manager.load("asteroids/"+i+".png", Texture.class);
			if(i<=10){
				manager.load("ships/"+i+".png", Texture.class);
				manager.load("data/shots/"+Stats.names[i].toLowerCase()+"-shot.p", ParticleEffect.class);
				manager.load("data/explosions/"+Stats.names[i].toLowerCase()+"-explosion.p", ParticleEffect.class);
				manager.load("data/fire/"+Stats.names[i].toLowerCase()+".p", ParticleEffect.class);
				if(i>=2){
					manager.load("audio/sounds/shots/"+(i-1)+".ogg", Sound.class);
				}
			}
			if(i<=20){
				manager.load("planets/"+i+".png", Texture.class);
			}
			if((i<=8)&&(i!=6)){
				manager.load("bg/"+i+".png", Texture.class);
			}
			if(i<=5){
				manager.load("mbg/"+i+".jpg", Texture.class);
			}
		}
		manager.load("data/explosions/dust.p", ParticleEffect.class);
        manager.load("nskin/neon-ui.atlas", TextureAtlas.class);
        manager.load("nskin/neon-ui.json", Skin.class, new SkinLoader.SkinParameter("nskin/neon-ui.atlas"));
        manager.load("nskin/neon-ui-alpha.atlas", TextureAtlas.class);
        manager.load("nskin/neon-ui-alpha.json", Skin.class, new SkinLoader.SkinParameter("nskin/neon-ui-alpha.atlas"));
        manager.load("nskin/neon-ui-alpha.atlas", TextureAtlas.class);
        manager.load("nskin/pink-ui.json", Skin.class, new SkinLoader.SkinParameter("nskin/neon-ui-alpha.atlas"));
        manager.load("nskin/green-ui.json", Skin.class, new SkinLoader.SkinParameter("nskin/neon-ui-alpha.atlas"));
		manager.load("menu/name.png", Texture.class);
		manager.load("menu/failed.png", Texture.class);
		manager.load("menu/complete.png", Texture.class);
		manager.load("menu/shipicon.png", Texture.class);
		manager.load("menu/info.png", Texture.class);
		manager.load("menu/gameover.png", Texture.class);
		manager.load("menu/solar-system.png", Texture.class);
		manager.load("planets/stars/1.png", Texture.class);
		manager.load("solar/6.png", Texture.class);
		manager.load("audio/menu.ogg", Music.class);
		manager.load("audio/game.mp3", Music.class);
		
		manager.load("data/star.p", ParticleEffect.class);
		manager.load("data/star2.p", ParticleEffect.class);
		manager.load("data/explosion.p", ParticleEffect.class);
		//manager.load("data/some-effect.p", ParticleEffect.class);
		manager.load("data/stars.p", ParticleEffect.class);
		manager.load("data/stars-slow.p", ParticleEffect.class);
		manager.load("data/res.p", ParticleEffect.class);
		manager.load("asteroids/effect-up.p", ParticleEffect.class);
		manager.load("data/effect-new.p", ParticleEffect.class);
		
		manager.load("audio/sounds/shot1.ogg", Sound.class);
		manager.load("audio/engine.mp3", Music.class);
		manager.load("audio/sounds/expl1.ogg", Sound.class);
		manager.load("audio/sounds/far.ogg", Sound.class);
		manager.load("audio/sounds/close.ogg", Sound.class);
		manager.load("audio/sounds/distant.ogg", Sound.class);
		manager.load("audio/sounds/click.mp3", Sound.class);
		manager.load("audio/sounds/buy.mp3", Sound.class);
		manager.load("audio/sounds/click.ogg", Sound.class);
		manager.finishLoading();
	}
	
	@Override
	public void dispose () {
		skin.dispose();
		greenfont.dispose();
		pinkfont.dispose();
		manager.dispose();
		solarSystem.dispose();
	}
}
