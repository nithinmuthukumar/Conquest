package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.*;

public class CameraSystem extends EntitySystem {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ImmutableArray<Entity> controllers;
    public CameraSystem(OrthographicCamera camera,SpriteBatch batch){
        super();
        this.camera=camera;
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);
        this.batch=batch;

    }

    @Override
    public void update(float deltaTime) {
            float x=0;
            float y=0;

            for(Entity e:controllers) {
                PositionComponent position=positionComp.get(e);
                x+=position.x;
                y+=position.y;



            }

            if(controllers.size()>0){
                x/=controllers.size();
                y/=controllers.size();

                camera.position.x=MathUtils.lerp(camera.position.x,x,.1f);
                camera.position.y=MathUtils.lerp(camera.position.y,y,.1f);

                camera.update();


                batch.setProjectionMatrix(camera.combined);

            }







        super.update(deltaTime);

    }
    @Override
    public void addedToEngine(Engine engine) {
        controllers = engine.getEntitiesFor(Family.all(PositionComponent.class,CameraComponent.class).get());
    }
}
