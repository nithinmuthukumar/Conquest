package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

import static com.nithinmuthukumar.conquest.Globals.batch;
import static com.nithinmuthukumar.conquest.Globals.camera;

public class CameraSystem extends EntitySystem {
    private ComponentMapper<TransformComponent> positionComp = ComponentMapper.getFor(TransformComponent.class);
    private ImmutableArray<Entity> controllers;

    public CameraSystem() {
        super(10);

        camera.translate(camera.viewportWidth/2,camera.viewportHeight/2);

    }

    @Override
    public void update(float deltaTime) {
            float x=0;
            float y=0;

            for(Entity e:controllers) {
                TransformComponent transform = positionComp.get(e);
                x += transform.pos.x;
                y += transform.pos.y;



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
