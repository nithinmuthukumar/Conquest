package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    private DataButton[][] inventory;


    public InventoryTable() {
        meleeSlot = new DataButton();
        shootSlot = new DataButton();
        throwSlot = new DataButton();
        meleeSlot.setPosition(200, 100);
        shootSlot.setPosition(300, 100);
        throwSlot.setPosition(400, 100);


        table = new Table();
        table.setPosition(200, 200);
        inventory = new DataButton[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                inventory[y][x] = new DataButton();
                table.add(inventory[y][x]).size(32, 32);
                inventory[y][x].addListener(new CClickListener<>(inventory[y][x]) {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (object.getData() == null) {
                            return;
                        }

                        ItemData data = (ItemData) object.getData();
                        if (data.type.equals("shoot")) {
                            object.setData(shootSlot.getData());
                            shootSlot.setData(data);
                        } else if (data.type.equals("throw")) {
                            object.setData(throwSlot.getData());
                            throwSlot.setData(data);
                        } else if (data.type.equals("melee")) {
                            object.setData(meleeSlot.getData());
                            meleeSlot.setData(data);
                        }
                        Conquest.client.getClient().sendTCP(new WeaponSwitchMessage(Conquest.client.getClient().getID(), data.name, data.type));


                        super.clicked(event, x, y);
                    }
                });
            }
            table.row();

        }


    }

    public void updateInventory() {
        ItemData[][] pInventory = equipComp.get(player.getEntity()).inventory;


        for (int y = 0; y < inventory.length; y++) {

            for (int x = 0; x < inventory[y].length; x++) {

                if (pInventory[y][x] != null)
                    inventory[y][x].setData(pInventory[y][x]);



            }


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

        } else {
            table.remove();
            meleeSlot.remove();
            throwSlot.remove();
            shootSlot.remove();
        }
        super.setStage(stage);
    }

}
