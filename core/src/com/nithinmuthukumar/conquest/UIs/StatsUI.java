package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Helpers.Assets;

import static com.nithinmuthukumar.conquest.Globals.engine;
import static com.nithinmuthukumar.conquest.Globals.healthComp;

public class StatsUI extends Table {
    private int money;
    private int health=0;
    private Texture[] hearts=new Texture[]{Assets.manager.get("hearts/heart_73.png",Texture.class),
            Assets.manager.get("hearts/heart_55.png",Texture.class),
            Assets.manager.get("hearts/heart_37.png",Texture.class),
            Assets.manager.get("hearts/heart_19.png",Texture.class),
            Assets.manager.get("hearts/heart_01.png",Texture.class),
    };



    private Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get()).first();
    private HorizontalGroup heartGroup = new HorizontalGroup();

    private Label moneyLabel;

    public StatsUI() {

        heartGroup.scaleBy(1.5f);
        ImageButton money = new ImageButton(new TextureRegionDrawable(Assets.manager.get("ui stuff/money_icon.png", Texture.class)));
        money.addListener(new ClickListener() {
            //take them to store for more money
        });

        add(money).row();

        add(heartGroup).padTop(10);
        setPosition(25, Gdx.graphics.getHeight() - 25);
        debug();


    }

    @Override
    public void act(float delta) {

        updateHealth(healthComp.get(player));


        super.act(delta);

    }

    public void updateHealth(HealthComponent healthComponent) {
        int diff=healthComponent.health-health;
        if(diff!=0){
            if(diff>0){
                int numFull=diff/4;
                for(int i=0;i<numFull;i++){

                    heartGroup.addActor(new Image(hearts[4]));
                }if(diff%4!=0){

                    heartGroup.addActor(new Image(hearts[diff%4]));
                }
            }if(diff<0){
                int numRemove=Math.abs(diff/4);
                heartGroup.getChildren().removeRange(heartGroup.getChildren().size-numRemove,heartGroup.getChildren().size-1);

            }


            this.health=healthComponent.health;

        }





        }
}
