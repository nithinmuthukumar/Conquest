package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nithinmuthukumar.conquest.Components.BodyComponent;

import static com.nithinmuthukumar.conquest.Utils.bodyComp;

public class DebugRenderSystem extends EntitySystem {
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();
    public DebugRenderSystem(World world, OrthographicCamera camera){
        super(5);
        this.world=world;
        this.camera=camera;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        debugRenderer.render(world,camera.combined);
    }


}