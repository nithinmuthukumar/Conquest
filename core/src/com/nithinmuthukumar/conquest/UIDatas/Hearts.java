package com.nithinmuthukumar.conquest.UIDatas;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Globals;

import static com.nithinmuthukumar.conquest.Globals.healthComp;

public class Hearts extends Actor {
    private String color;
    private TextureRegion[] hearts;

    public Hearts() {
        color = Globals.colors[Globals.conquestClient.getClient().getID()];
        hearts = new TextureRegion[]{
                Assets.style.getRegion("heart_73"),
                Assets.style.getRegion("heart_55"),
                Assets.style.getRegion("heart_37"),
                Assets.style.getRegion("heart_19"),
                Assets.style.getRegion("heart_01")
        };


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        HealthComponent hp = healthComp.get(Globals.player.getEntity());
        if (hp.health < 0) {
            return;
        }
        float x = getX();

        for (int i = 0; i < hp.health / 4; i++) {

            batch.draw(hearts[4], x, getY(), hearts[4].getRegionWidth() * 2, hearts[4].getRegionHeight() * 2);
            x += 32;
        }
        int leftOver = (int) hp.health % 4;
        if (leftOver != 0) {
            batch.draw(hearts[leftOver], x, getY(),
                    hearts[leftOver].getRegionWidth() * 2, hearts[leftOver].getRegionHeight() * 2);
            x += 32;

        }
        for (int i = 0; i < (hp.maxHealth - hp.health) / 4 - 2; i++) {

            batch.draw(hearts[0], x, getY(), hearts[4].getRegionWidth() * 2, hearts[4].getRegionHeight() * 2);
            x += 32;
        }


        super.draw(batch, parentAlpha);
    }
}
