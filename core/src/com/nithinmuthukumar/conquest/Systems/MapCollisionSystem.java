package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Utils.*;

//fixup map stuff
//make it a switch statement
//fix magic numbers
//how am i going to work with z value to place order of layers
//think
public class MapCollisionSystem extends IteratingSystem {
    private static final int NO_TILE=0;
    private static final int COLLIDE=1;
    private static final int UP_COLLIDE=4;
    private static final int INSIDE_HOUSE=3;
    private static final int LADDER=2;
    public static final int PLACEMENT_COLLIDE=5;
    private static TiledMapTileLayer collisionLayer=new TiledMapTileLayer(200,200,16,16);


    public static void addCollisionLayer(TiledMapTileLayer layer,int posX,int posY){
        System.out.println(layer.getWidth());
        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){

                collisionLayer.setCell((int)(x+posX/layer.getTileWidth()), (int) (y+posY/layer.getTileHeight()),layer.getCell(x,y));

            }
        }
    }

    public MapCollisionSystem(){
        super(Family.all(
                PositionComponent.class,
                VelocityComponent.class,
                BodyComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position=positionComp.get(entity);
        VelocityComponent velocity=velocityComp.get(entity);
        BodyComponent body=bodyComp.get(entity);

        int futureX=(int)(position.x+velocity.x);
        int futureY=(int)(position.y+velocity.y);


        if(!Utils.inBounds(-1, (int) (collisionLayer.getWidth()*collisionLayer.getTileWidth()),futureX)
                ||!Utils.inBounds(-1, (int) (collisionLayer.getHeight()*collisionLayer.getTileHeight()),futureY)){
            body.collided=true;
            return;

        }

        Integer val=getTileInfo(futureX,futureY);
        switch(val){
            case INSIDE_HOUSE:
                if(val==INSIDE_HOUSE&&(getTileInfo((int)position.x,(int)position.y)!=NO_TILE&&getTileInfo((int)position.x,(int)position.y)!=INSIDE_HOUSE)){
                    body.collided=true;
                }
                break;
            case UP_COLLIDE:
                if(velocity.y>0){
                    body.collided=true;
                }
                break;
            case COLLIDE:
                body.collided=true;
                break;
            case LADDER:
                position.z=5;
                break;
            default:
                body.collided=false;
                position.z=0;
        }

        if(getTileInfo((int)position.x, (int) position.y)==INSIDE_HOUSE||val==INSIDE_HOUSE) {
            position.z=4;
        }

    }
    private Integer getTileInfo(int x,int y){

        if(collisionLayer.getCell(x/16,y/16)==null){
            return NO_TILE;
        }

        else{
            return collisionLayer.getCell(x/16,y/16).getTile().getProperties().get("info",Integer.class);
        }

    }
}
