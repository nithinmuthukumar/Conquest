package com.nithinmuthukumar.conquest.UIs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nithinmuthukumar.conquest.Components.HealthComponent;
import com.nithinmuthukumar.conquest.Components.PlayerComponent;
import com.nithinmuthukumar.conquest.Helpers.Assets;

import static com.nithinmuthukumar.conquest.Helpers.Globals.engine;
import static com.nithinmuthukumar.conquest.Helpers.Globals.healthComp;

public class StatsUI extends Table {
    private int money;
    private int health;
    private HorizontalGroup hearts = new HorizontalGroup() {
        public void update() {
        }
    };
    private Label moneyLabel;

    public StatsUI() {
        ImageButton money = new ImageButton(new TextureRegionDrawable(Assets.manager.get("ui stuff/money_icon.png", Texture.class)));
        money.addListener(new ClickListener() {
            //take them to store for more money
        });
        add(money);
        setPosition(25, Gdx.graphics.getHeight() - 25);


    }

    @Override
    public void act(float delta) {
        ImmutableArray<Entity> playerHealth = engine.getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get());
        if (playerHealth.size() == 1) {
            updateHealth(healthComp.get(playerHealth.first()));
        }

        super.act(delta);

    }

    public void updateHealth(HealthComponent health) {

    }
}
