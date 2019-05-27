package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;
import com.nithinmuthukumar.conquest.Screens.MultiplayerScreen;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;
import com.nithinmuthukumar.conquest.Screens.SelectionScreen;


public class Conquest extends Game {


	public static final World world = new World(new Vector2(), false);
	public static final PooledEngine engine = new PooledEngine();
	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static ShapeRenderer shapeRenderer;
	public static GameMap gameMap;
	public static Player player;
	public static ConquestClient client;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public SelectionScreen selectionScreen;
    public MultiplayerScreen multiplayerScreen;


	@Override
	public void create () {
        client = new ConquestClient(this);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
        gameMap = new GameMap(3200, 3200, 16, 16);
		camera = new OrthographicCamera(960, 720);


		Assets.loadAllFiles();
        multiplayerScreen = new MultiplayerScreen(this);
		selectionScreen=new SelectionScreen(this);
		playScreen = new PlayScreen(this);
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);



	}


	@Override
	public void render () {
        client.update();
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
