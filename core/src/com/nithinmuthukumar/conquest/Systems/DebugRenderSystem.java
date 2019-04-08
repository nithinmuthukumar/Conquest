package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import static com.nithinmuthukumar.conquest.Constants.world;

public class DebugRenderSystem extends EntitySystem {
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();

    public DebugRenderSystem(OrthographicCamera camera) {
        super(5);
        this.camera=camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world,camera.combined);
    }


}