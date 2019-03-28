package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;



public class Conquest extends Game {
	public static final InputHandler inputHandler=new InputHandler();
	public SpriteBatch batch;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;

	public World world=new World(new Vector2(0,0),true);



	@Override
	public void create () {
		Assets.loadAllFiles();
		batch = new SpriteBatch();



		playScreen=new PlayScreen(this);
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);














	}





	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
