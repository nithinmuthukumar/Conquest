package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Components.*;

public class CameraSystem extends EntitySystem {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ComponentMapper<PlayerComponent> playerComp = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> players;
    public CameraSystem(OrthographicCamera camera,SpriteBatch batch){
        super();
        this.camera=camera;
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
        this.batch=batch;

    }

    @Override
    public void update(float deltaTime) {

        PositionComponent playerPos=positionComp.get(players.get(0));
        camera.position.x=playerPos.x;
        camera.position.y=playerPos.y;

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        super.update(deltaTime);
    }
    @Override
    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PositionComponent.class,PlayerComponent.class).get());
    }
}
