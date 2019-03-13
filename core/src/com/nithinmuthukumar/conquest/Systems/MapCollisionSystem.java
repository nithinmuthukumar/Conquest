package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Components.MovingComponent;
import com.nithinmuthukumar.conquest.Components.PositionComponent;
import com.nithinmuthukumar.conquest.Components.StateComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.Direction;
import com.nithinmuthukumar.conquest.Utilities;

public class MapCollisionSystem extends IteratingSystem {
    private static final int NO_TILE=0;
    private static final int COLLIDE=1;
    private static final int UP_COLLIDE=4;
    private static final int INSIDE_HOUSE=3;
    private static final int LADDER=2;
    public static final int PLACEMENT_COLLIDE=5;
    private static TiledMapTileLayer collisionLayer=new TiledMapTileLayer(200,200,16,16);




    private ComponentMapper<PositionComponent> pm=ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm=ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<MovingComponent> mm=ComponentMapper.getFor(MovingComponent.class);
    private ComponentMapper<StateComponent> stateComp=ComponentMapper.getFor(StateComponent.class);
    public static void addCollisionLayer(TiledMapTileLayer layer,int posX,int posY){
        System.out.println(layer.getWidth());
        for(int x=0;x<layer.getWidth();x++) {
            for (int y = 0; y < layer.getHeight(); y++){
                System.out.print(x+" ");
                System.out.println(y);

                collisionLayer.setCell((int)(x+posX/layer.getTileWidth()), (int) (y+posY/layer.getTileHeight()),layer.getCell(x,y));

            }
        }
    }

    public MapCollisionSystem(){
        super(Family.all(
                PositionComponent.class,
                VelocityComponent.class, MovingComponent.class, StateComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position=pm.get(entity);
        VelocityComponent velocity=vm.get(entity);
        MovingComponent movingComponent=mm.get(entity);
        StateComponent state=stateComp.get(entity);
        int futureX=(int)(position.x+velocity.moveDistX());
        int futureY=(int)(position.y+velocity.moveDistY());

        if(!Utilities.inBounds(0, (int) (collisionLayer.getWidth()*collisionLayer.getTileWidth()),futureX)
                ||!Utilities.inBounds(0, (int) (collisionLayer.getHeight()*collisionLayer.getTileHeight()),futureY)){
            movingComponent.collide=true;
            return;

        }
        Integer val=getTileInfo(futureX,futureY);
        if(val==COLLIDE||(val==UP_COLLIDE&&state.direction== Direction.UP)){
            movingComponent.collide=true;

        }else if(val==INSIDE_HOUSE&&(getTileInfo((int)position.x,(int)position.y)!=NO_TILE||getTileInfo((int)position.x,(int)position.y)!=INSIDE_HOUSE)){
            movingComponent.collide=true;
        } else if(val==LADDER) {
            position.z = 5;
        }else{
            movingComponent.collide=false;
            position.z=0;
        }
        System.out.println(getTileInfo((int)position.x, (int) position.y));
        if(getTileInfo((int)position.x, (int) position.y)==INSIDE_HOUSE) {
            position.z=4;
        }








    }
    private Integer getTileInfo(int x,int y){

        if(collisionLayer.getCell(x/16,y/16)==null){

            return NO_TILE;

        }

        else{
            return (Integer)collisionLayer.getCell(x/16,y/16).getTile().getProperties().get("info");
        }

    }
}