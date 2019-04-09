package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import static com.nithinmuthukumar.conquest.Helpers.Globals.camera;
import static com.nithinmuthukumar.conquest.Helpers.Globals.world;

public class DebugRenderSystem extends EntitySystem {
    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();

    public DebugRenderSystem() {
        super(5);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world,camera.combined);
    }


}