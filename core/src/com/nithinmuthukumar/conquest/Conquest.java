package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;


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




	@Override
	public void create () {
        Globals.game = this;
        Globals.conquestClient = new ConquestClient();
        Globals.batch = new SpriteBatch();
        Globals.gameMap = new GameMap(3200, 3200, 16, 16);
        Globals.camera = new OrthographicCamera(960, 720);


		Assets.loadAllFiles();

        setScreen(new MenuScreen());



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
        Globals.batch.dispose();
	}
}
