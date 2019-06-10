package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.nithinmuthukumar.conquest.Components.AttackComponent;
import com.nithinmuthukumar.conquest.Components.TowerComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

import static com.nithinmuthukumar.conquest.Conquest.*;
import static com.nithinmuthukumar.conquest.Globals.attackComp;
import static com.nithinmuthukumar.conquest.Globals.transformComp;

public class ShapeRenderSystem extends IteratingSystem {
    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();
    private Queue<Rectangle> requests = new Queue<>();
    private boolean debug;
    public final Listener<Rectangle> drawRequestListener = (signal, object) -> requests.addLast(object);
    private Matrix4 screenView;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Array<Color> colors;

    public ShapeRenderSystem() {

        super(Family.all(TowerComponent.class, AttackComponent.class).get(), 200);
        colors=new Array<>();
        colors.add(new Color(0,0,0,0));
        colors.add(Color.BLACK.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.3f));
        colors.add(Color.BLUE.add(0,0,0,-0.5f));

        debug = false;
        screenView = shapeRenderer.getProjectionMatrix().cpy();
        shapeRenderer.setAutoShapeType(true);

    }


    @Override
    public void update(float deltaTime) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(camera.combined);
        if (debug)
            debugRenderer.render(world, camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (debug) {
            for (int y = 0; y < gameMap.getHeight() * 16; y += gameMap.getTileHeight()) {
                for (int x = 0; x < gameMap.getWidth() * 16; x += gameMap.getTileWidth()) {
                    shapeRenderer.setColor(colors.get(gameMap.getTileInfo(x, y)));
                    shapeRenderer.rect(x, y, 16, 16);
                }

            }
        }
        shapeRenderer.setColor(0, 0, 0, 0.08f);

        super.update(deltaTime);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.setProjectionMatrix(screenView);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);


        while (!requests.isEmpty()) {

            Rectangle r = requests.removeFirst();
            shapeRenderer.rect(r.x, r.y, r.width, r.height);
        }


        shapeRenderer.end();



    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackComponent attack = attackComp.get(entity);
        TransformComponent transform = transformComp.get(entity);
        shapeRenderer.circle(transform.pos.x, transform.pos.y, attack.range);

    }
}