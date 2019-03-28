package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nithinmuthukumar.conquest.CollisionLayer;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Utils.*;

public class MapCollisionSystem extends IteratingSystem {

    private CollisionLayer collisionLayer;


    public MapCollisionSystem(CollisionLayer collisionLayer){
        super(Family.all(
                PositionComponent.class,
                VelocityComponent.class,
                BodyComponent.class,RenderableComponent.class).get(),-1);
        this.collisionLayer=collisionLayer;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position=positionComp.get(entity);
        VelocityComponent velocity=velocityComp.get(entity);
        RenderableComponent renderable=renderComp.get(entity);
        BodyComponent body=bodyComp.get(entity);
        int futureX = (int) (position.x + velocity.x);
        int futureY = (int) (position.y + velocity.y);
        if(renderable.texture!=null) {
            futureX = (int) (position.x + velocity.x) + renderable.texture.getRegionWidth() / 2;
            futureY = (int) (position.y + velocity.y) + renderable.texture.getRegionHeight() / 2;
        }


        if(!Utils.inBounds(-1, (int) (collisionLayer.getWidth()*collisionLayer.getTileWidth()),futureX)
                ||!Utils.inBounds(-1, (int) (collisionLayer.getHeight()*collisionLayer.getTileHeight()),futureY)){
            body.collided=true;
            return;

        }

        Integer val=getTileInfo(futureX,futureY);
        print("MapCollisionSystem",Integer.toString(val));
        switch(val){
            case INSIDE_HOUSE:
                if(val==INSIDE_HOUSE&&(getTileInfo(position.x,position.y)!=NO_TILE&&getTileInfo((int)position.x,(int)position.y)!=INSIDE_HOUSE)){
                    body.collided=true;
                }
                break;
            case ELEVATE_COLLIDE:
                if(position.z==1){
                    body.collided=true;
                }
                break;
            case COLLIDE:
                body.collided=true;
                break;
            case ELEVATE:
                position.z=1;
                body.collided=false;
                break;
            case VERTICAL_MOVEMENT:
                if(Utils.inBounds(-1,180,(int)velocity.angle()))
                    velocity.setAngle(90);
                else velocity.setAngle(270);
                body.collided=false;
                break;
            case HORIZONTAL_MOVEMENT:
                if(Utils.inBounds(270,360,(int)velocity.angle())||Utils.inBounds(-1,90,(int)velocity.angle()))
                    velocity.setAngle(0);
                else velocity.setAngle(180);
                body.collided=false;
                break;
            case FOUR_DIRECTIONAL_MOVEMENT:
                body.collided=false;
                break;
            default:
                body.collided=false;
                position.z=0;
        }

        if(getTileInfo((int)position.x, (int) position.y)==INSIDE_HOUSE||val==INSIDE_HOUSE) {
            position.z=4;
        }

    }
    private Integer getTileInfo(float x,float y){

        if(collisionLayer.getCell(x/16,y/16)==null){
            return NO_TILE;
        }

        else{
            return collisionLayer.getCell(x/16,y/16).getTile().getProperties().get("info",Integer.class);
        }

    }
}
