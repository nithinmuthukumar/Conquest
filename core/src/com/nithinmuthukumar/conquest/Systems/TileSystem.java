package com.nithinmuthukumar.conquest.Systems;

public class TileSystem {
    /*
    //the layer used to decide whether the entity has collided with the tiles
    private GameMap gameMap;
    private Entity emptyEntity=new Entity();


    public TileSystem(GameMap gameMap) {
        super(Family.all(
                TransformComponent.class,
                VelocityComponent.class,
                BodyComponent.class, RenderableComponent.class).get(), 1);
        this.gameMap = gameMap;
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent position = Globals.transformComp.get(entity);
        BodyComponent body=Globals.bodyComp.get(entity);

        Integer val = gameMap.getTileInfo(position.getRenderX(),position.getRenderY());

        switch (val) {

            case ELEVATE:
                position.z = 1;
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



