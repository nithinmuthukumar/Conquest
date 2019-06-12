package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Server.WeaponSwitchMessage;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Conquest.player;
import static com.nithinmuthukumar.conquest.Globals.equipComp;

public class InventoryTable extends Actor {
    private Table table;


    private boolean full;
    private DataButton meleeSlot;
    private DataButton shootSlot;
    private DataButton throwSlot;
    private DataButton shieldSlot;
    private DataButton[][] inventory;


    public InventoryTable() {
        meleeSlot = new DataButton("melee");
        shootSlot = new DataButton("shoot");
        throwSlot = new DataButton("throw");
        shieldSlot = new DataButton("shield");
        meleeSlot.setPosition(200, 100);
        shootSlot.setPosition(300, 100);
        throwSlot.setPosition(400, 100);
        shieldSlot.setPosition(500, 100);


        table = new Table();
        table.setPosition(200, 200);
        inventory = new DataButton[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                inventory[y][x] = new DataButton("inventory");
                table.add(inventory[y][x]).size(32, 32);
                inventory[y][x].addListener(new CClickListener<>(new Vector2(x, y)) {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        DataButton button = inventory[(int) object.y][(int) object.x];
                        if (button.getData() == null) {
                            return;
                        }

                        ItemData data = (ItemData) button.getData();
                        if (data.getType().equals("shoot")) {
                            button.setData(shootSlot.getData());
                            shootSlot.setData(data);
                        } else if (data.getType().equals("throw")) {
                            button.setData(throwSlot.getData());
                            throwSlot.setData(data);
                        } else if (data.getType().equals("melee")) {
                            button.setData(meleeSlot.getData());
                            meleeSlot.setData(data);
                        } else if (data.getType().equals("shield")) {
                            button.setData(shieldSlot.getData());
                            shieldSlot.setData(data);
                        }
                        Conquest.client.getClient().sendTCP(new WeaponSwitchMessage(data.name, data.getType()));
                        if (inventory[(int) object.y][(int) object.x].getData() == null) {
                            equipComp.get(player.getEntity()).inventory.removeIndex((int) (object.y * 5 + object.x));

                            updateInventory();
                        }


                        super.clicked(event, x, y);
                    }
                });
            }
            table.row();

        }


    }

    public void updateInventory() {
        for (int y = 0; y < inventory.length; y++) {
            for (int x = 0; x < inventory[y].length; x++) {
                inventory[y][x].setData(null);
            }
        }
        Array<ItemData> pInventory = equipComp.get(player.getEntity()).inventory;


        for (int i = 0; i < pInventory.size; i++) {
            int y = i / 5;
            int x = i % 5;


            inventory[y][x].setData(pInventory.get(i));


        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void setStage(Stage stage) {
        updateInventory();
        if (stage != null) {
            stage.addActor(table);
            stage.addActor(meleeSlot);
            stage.addActor(throwSlot);
            stage.addActor(shootSlot);
            stage.addActor(shieldSlot);

        } else {
            table.remove();
            meleeSlot.remove();
            throwSlot.remove();
            shootSlot.remove();
            shieldSlot.remove();
        }
        super.setStage(stage);
    }

}
