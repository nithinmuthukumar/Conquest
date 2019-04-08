package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Constants;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Utils;

public class MapCollisionSystem extends IteratingSystem {
    //the layer used to decide whether the entity has collided with the tiles
    private GameMap gameMap;


    public MapCollisionSystem(GameMap gameMap) {
        super(Family.all(
                TransformComponent.class,
                VelocityComponent.class,
                BodyComponent.class, RenderableComponent.class).get(), 1);
        this.gameMap = gameMap;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = Constants.transformComp.get(entity);
        VelocityComponent velocity = Constants.velocityComp.get(entity);
        RenderableComponent renderable = Constants.renderComp.get(entity);


        int futureX = (int) (position.getRenderX() + velocity.x);
        int futureY = (int) (position.getRenderY() + velocity.y);

        //checking if the position is within the bounds of the gameMap
        if (!Utils.inBounds(-1, (int) (gameMap.getWidth() * gameMap.getTileWidth()), futureX)
                || !Utils.inBounds(-1, (int) (gameMap.getHeight() * gameMap.getTileHeight()), futureY)) {
            velocity.setCollide(true);
            return;

        }
        //getting the tile value for the current spot
        Integer val = gameMap.getTileInfo(futureX, futureY);

        //getting the tile value where entity only moves horizontally or vertically
        Integer xCollide = gameMap.getTileInfo(futureX, position.getRenderY());
        Integer yCollide = gameMap.getTileInfo(position.getRenderX(), futureY);
        switch (val) {

            case Constants.ELEVATE_COLLIDE:
                if (position.z == 1) {
                    collideComponents(xCollide, yCollide, velocity, Constants.ELEVATE_COLLIDE);
                } else {
                    velocity.setCollide(false);
                }
                break;
            case Constants.FLOOR_COLLIDE:
                if (position.z == 0) {
                    collideComponents(xCollide, yCollide, velocity, Constants.FLOOR_COLLIDE);
                } else {
                    velocity.setCollide(false);

                }
                break;
            case Constants.COLLIDE:
                collideComponents(xCollide, yCollide, velocity, Constants.COLLIDE);
                break;
            case Constants.ELEVATE:
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



