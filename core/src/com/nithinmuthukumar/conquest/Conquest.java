package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;
import com.nithinmuthukumar.conquest.Screens.MultiplayerScreen;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;
import com.nithinmuthukumar.conquest.Screens.SelectionScreen;


public class Conquest extends Game {
	/*
	System priorities

	0-FollowAI
	1-TowerAI,ShooterAI,MeleeAI,SpawnerAI
	2-Pathfinding
	3-Target
	4-Direction
	5-Animation,StateParticle
	6-Collision,
	7-Movement
	8-Physics
	9-Death, Decay
	10-Removal,Camera
	11-RenderManager
	12-UI
	13-ShapeRender
	14-Spawn




	 */


	public static final World world = new World(new Vector2(), false);
	public static final PooledEngine engine = new PooledEngine();
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static GameMap gameMap;
	public static Player player;
	public static ConquestClient client;
	public static Conquest game;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public SelectionScreen selectionScreen;
    public MultiplayerScreen multiplayerScreen;
    public static String[] colors = new String[]{"White", "Red", "Blue", "Green", "Purple"};


	@Override
	public void create () {
		game = this;
		client = new ConquestClient();
		batch = new SpriteBatch();
        gameMap = new GameMap(3200, 3200, 16, 16);
		camera = new OrthographicCamera(960, 720);


		Assets.loadAllFiles();
		multiplayerScreen = new MultiplayerScreen();
		selectionScreen = new SelectionScreen();
		playScreen = new PlayScreen();
		menuScreen = new MenuScreen();
		setScreen(menuScreen);



	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("Conquest " + Gdx.graphics.getFramesPerSecond());

		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
