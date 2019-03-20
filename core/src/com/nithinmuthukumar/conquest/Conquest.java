package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;
import com.nithinmuthukumar.conquest.Systems.*;

import java.util.ArrayList;


public class Conquest extends Game {
	public SpriteBatch batch;
	public AssetManager manager;

	public TmxMapLoader mapLoader=new TmxMapLoader();
	public Skin style;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;
	public World world=new World(new Vector2(0,0),true);



	@Override
	public void create () {
		manager=new AssetManager();
		batch = new SpriteBatch();
		manager.load("shade/skin/uiskin.json",Skin.class);
		manager.finishLoading();
		style=manager.get("shade/skin/uiskin.json",Skin.class);
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
