package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.CollisionLayer;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Utils.*;

public class MapCollisionSystem extends IteratingSystem {
    //the layer used to decide whether the entity has collided with the tiles
    private CollisionLayer collisionLayer;


    public MapCollisionSystem(CollisionLayer collisionLayer) {
        super(Family.all(
                PositionComponent.class,
                VelocityComponent.class,
                BodyComponent.class, RenderableComponent.class).get(), 1);
        this.collisionLayer = collisionLayer;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionComp.get(entity);
        VelocityComponent velocity = velocityComp.get(entity);
        RenderableComponent renderable = renderComp.get(entity);


        int futureX = (int) (position.x + velocity.x) + renderable.texture.getRegionWidth() / 2;
        int futureY = (int) (position.y + velocity.y) + renderable.texture.getRegionHeight() / 2;

        //checking if the position is within the bounds of the map
        if (!Utils.inBounds(-1, (int) (collisionLayer.getWidth() * collisionLayer.getTileWidth()), futureX)
                || !Utils.inBounds(-1, (int) (collisionLayer.getHeight() * collisionLayer.getTileHeight()), futureY)) {
            velocity.setCollide(true);
            return;

        }
        //getting the tile value for the current spot
        Integer val = collisionLayer.getTileInfo(futureX, futureY);

        //getting the tile value where entity only moves horizontally or vertically
        Integer xCollide = collisionLayer.getTileInfo(futureX, position.y + renderable.texture.getRegionHeight() / 2);
        Integer yCollide = collisionLayer.getTileInfo(position.x + renderable.texture.getRegionWidth() / 2, futureY);

        switch (val) {

            case ELEVATE_COLLIDE:
                if (position.z == 1) {
                    collideComponents(xCollide, yCollide, velocity, ELEVATE_COLLIDE);
                } else {
                    velocity.setCollide(false);
                }
                break;
            case COLLIDE:
                collideComponents(xCollide, yCollide, velocity, COLLIDE);
                break;
            case ELEVATE:
                position.z = 1;
                velocity.setCollide(false);
                break;

            default:
                velocity.setCollide(false);
                position.z = 0;
        }


    }


    //returns if the adjustment happened

        /*
        if(val==INSIDE_HOUSE&&(getTileInfo(position.x,position.y)!=NO_TILE&&getTileInfo((int)position.x,(int)position.y)!=INSIDE_HOUSE)){
            body.collided=true;
        }


        if(getTileInfo((int)position.x, (int) position.y)==INSIDE_HOUSE||val==INSIDE_HOUSE) {
            position.z=1;
        }
        */


    private static void collideComponents(int xCollide, int yCollide, VelocityComponent velocity, int n) {

        if (yCollide == n && xCollide != n) {
            velocity.yCollide = true;
        } else if (xCollide == n && yCollide != n) {
            velocity.xCollide = true;
        } else {
            velocity.setCollide(true);
        }


    }


}



