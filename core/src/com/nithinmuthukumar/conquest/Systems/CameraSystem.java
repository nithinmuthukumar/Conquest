package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

import static com.nithinmuthukumar.conquest.Constants.batch;

public class CameraSystem extends EntitySystem {
    private OrthographicCamera camera;
    private ComponentMapper<TransformComponent> positionComp = ComponentMapper.getFor(TransformComponent.class);
    private ImmutableArray<Entity> controllers;

    public CameraSystem(OrthographicCamera camera) {
        super();
        this.camera=camera;
        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);

    }

    @Override
    public void update(float deltaTime) {
            float x=0;
            float y=0;

            for(Entity e:controllers) {
                TransformComponent position = positionComp.get(e);
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
        controllers = engine.getEntitiesFor(Family.all(TransformComponent.class, CameraComponent.class).get());
    }
}
