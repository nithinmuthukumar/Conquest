package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nithinmuthukumar.conquest.Components.*;

public class CameraSystem extends EntitySystem {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> controller;
    public CameraSystem(OrthographicCamera camera,SpriteBatch batch){
        super();
        this.camera=camera;
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
        this.batch=batch;

    }

    @Override
    public void update(float deltaTime) {
        if(controller.size()!=0){
            PositionComponent controllerPos=positionComp.get(controller.get(0));
            camera.position.x=controllerPos.x;
            camera.position.y=controllerPos.y;

            camera.update();

            batch.setProjectionMatrix(camera.combined);



        }
        super.update(deltaTime);

    }
    @Override
    public void addedToEngine(Engine engine) {
        controller = engine.getEntitiesFor(Family.all(PositionComponent.class,CameraComponent.class).get());
    }
}
