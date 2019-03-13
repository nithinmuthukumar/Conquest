package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Systems.*;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class Conquest extends Game {
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public FitViewport viewport;
	public Engine engine;
	public OrthogonalTiledMapRenderer mapRenderer;
	private TiledMap tiledMap;
	private TmxMapLoader mapLoader=new TmxMapLoader();




	
	@Override
	public void create () {






		batch = new SpriteBatch();




		OrthographicCamera camera =new OrthographicCamera(960,720);






		CameraSystem cameraSystem=new CameraSystem(camera,batch);
		engine=new Engine();
		createMap(0,0,"village_map");
		Signal signal=new Signal<Entity>();

		PlayerInputSystem pis=new PlayerInputSystem(signal);
		Gdx.input.setInputProcessor(pis);
		createPlayer();

		engine.addSystem(new AnimationSystem());
		engine.addSystem(new RenderSystem(batch));
		engine.addSystem(new MapCollisionSystem());
		engine.addSystem(pis);
		engine.addSystem(new MovementSystem());
		engine.addSystem(cameraSystem);
		engine.addSystem(new AnimationSystem());
		SocketSystem socketSystem=new SocketSystem();

		engine.addSystem(socketSystem);








	}
	public void createMap(int x,int y,String file){
		PositionComponent mapPos=new PositionComponent(0,0);
		ArrayList<Entity> entities=new ArrayList<>();
		TiledMap map=mapLoader.load(file+"/map.tmx");
		int z=-1;
		for(MapLayer layer:map.getLayers()){
			if(!layer.getName().equals("tileinfo")&&!layer.getName().equals("renderinfo")) {
				Entity l=new Entity();
				l.add(new TextureComponent(new Texture(file + "/"+layer.getName() + ".png")));
				l.add(new PositionComponent(0,0,z));
				engine.addEntity(l);
				z+=1;
			}
		}MapCollisionSystem.addCollisionLayer((TiledMapTileLayer)map.getLayers().get("tileinfo"),x,y);
		//still have to finish this
		//going to get another tmx file from this and recursively add it to the map
		for(MapObject object: map.getLayers().get("renderinfo").getObjects()){
			object.getProperties();

		}


	}


	public void createPlayer(){
		Entity e=new Entity();
		e.add(new PositionComponent(500,500));
		e.add(new AnimationComponent("Character/"));
		e.add(new PlayerComponent());
		e.add(new StateComponent());
		e.add(new VelocityComponent(1.2f));
		e.add(new TextureComponent());
		engine.addEntity(e);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(Gdx.graphics.getDeltaTime());
		//mapRenderer.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
