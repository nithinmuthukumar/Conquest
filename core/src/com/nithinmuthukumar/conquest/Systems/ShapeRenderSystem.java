package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.GameMap;

public class ShapeRenderSystem extends EntitySystem {
    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();
    private static Queue<Rectangle> requests = new Queue<>();
    private boolean debug;
    Array<Color> colors;
    private GameMap map;

    public ShapeRenderSystem(GameMap map) {

        super(10);
        colors=new Array<>();
        colors.add(new Color(0,0,0,0));
        colors.add(Color.BLACK.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.3f));
        colors.add(Color.BLUE.add(0,0,0,-0.5f));

        this.map=map;
    }

    public static void addRectangle(Rectangle r) {
        requests.addLast(r);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if (debug)
            debugRenderer.render(Conquest.world, Conquest.camera.combined);

        Conquest.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if (debug) {
            Conquest.shapeRenderer.setProjectionMatrix(Conquest.camera.combined);


            for (int y = 0; y < map.getHeight() * 16; y += map.getTileHeight()) {
                for (int x = 0; x < map.getWidth() * 16; x += map.getTileWidth()) {
                    Conquest.shapeRenderer.setColor(colors.get(map.getTileInfo(x, y)));
                    Conquest.shapeRenderer.rect(x, y, 16, 16);
                }

            }
        }
        while (!requests.isEmpty()) {
            Rectangle r = requests.removeFirst();
            Conquest.shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }

        Conquest.shapeRenderer.end();



    }




}