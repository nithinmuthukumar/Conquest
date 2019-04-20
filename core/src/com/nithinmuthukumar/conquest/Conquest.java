package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;
import com.nithinmuthukumar.conquest.Screens.SelectionScreen;

import static com.nithinmuthukumar.conquest.Helpers.Globals.batch;


public class Conquest extends Game {

	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public SelectionScreen selectionScreen;


	@Override
	public void create () {
		Assets.loadAllFiles();
		selectionScreen=new SelectionScreen(this);



		playScreen = new PlayScreen();
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
