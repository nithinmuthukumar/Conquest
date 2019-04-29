package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Recipe;

import static com.nithinmuthukumar.conquest.Globals.*;

public class SpawnSystem {
    /*
    private Table table;
    private HorizontalGroup inLine;
    private HorizontalGroup troops;
    private Entity current;
    private Listener<int[]> touchUpListener= (signal, object) -> {
        float x=Utils.screenToCameraX(Gdx.input.getX());
        float y=Utils.screenToCameraY(Gdx.input.getY());
        for(Entity e:getEngine().getEntitiesFor(Family.all(SpawnComponent.class).get())){
            TransformComponent pos=transformComp.get(e);
            if(x>=pos.x-pos.width/2&&x<=pos.x+pos.width/2){
                if(y>=pos.y-pos.height/2&&y<=pos.y+pos.height/2){
                    current=e;
                    table.setVisible(true);
                    for(Recipe r: spawnerComp.get(e).spawnable){
                        troops.addActor(new SpawnButton(r.make()));

                    }
                    break;
                }
            }


        }



    };
    public SpawnSystem() {
        //.exclude towerComponent.class
        super(Family.all(SpawnComponent.class).get());
        inputHandler.addListener("touchUp",touchUpListener);
        table=new Table();
        inLine=new HorizontalGroup();
        troops=new HorizontalGroup();



    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(table.isVisible()){
        }

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public Table getTable() {
        return table;
    }

     */
}
