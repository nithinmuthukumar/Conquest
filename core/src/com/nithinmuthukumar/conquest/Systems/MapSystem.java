package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Helpers.Globals;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Helpers.Globals.ELEVATE;

public class MapSystem extends IteratingSystem {
    //the layer used to decide whether the entity has collided with the tiles
    private GameMap gameMap;
    private Entity emptyEntity=new Entity();


    public MapSystem(GameMap gameMap) {
        super(Family.all(
                TransformComponent.class,
                VelocityComponent.class,
                BodyComponent.class, RenderableComponent.class).get(), 1);
        this.gameMap = gameMap;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = Globals.transformComp.get(entity);
        VelocityComponent velocity = Globals.velocityComp.get(entity);
        BodyComponent body=Globals.bodyComp.get(entity);


        int futureX = (int) (position.getRenderX() + velocity.x);
        int futureY = (int) (position.getRenderY() + velocity.y);

        //checking if the position is within the bounds of the gameMap
        if (!Utils.inBounds(-1, (int) (gameMap.getWidth() * gameMap.getTileWidth()), futureX)
                || !Utils.inBounds(-1, (int) (gameMap.getHeight() * gameMap.getTileHeight()), futureY)) {
            body.collidedEntity=emptyEntity;
        }
        //getting the tile value for the current spot
        Integer val = gameMap.getTileInfo(futureX, futureY);

        //getting the tile value where entity only moves horizontally or vertically
        switch (val) {

            case ELEVATE:
                position.z = 1;
                for(Fixture f:body.body.getFixtureList()){
                    Filter elevated=f.getFilterData();


                    f.setFilterData(elevated);
                }

                break;

            default:
                position.z = 0;
                break;
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




}


