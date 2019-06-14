package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Globals;
import com.nithinmuthukumar.conquest.Helpers.CClickListener;
import com.nithinmuthukumar.conquest.Server.WeaponSwitchMessage;
import com.nithinmuthukumar.conquest.UIDatas.DataButton;
import com.nithinmuthukumar.conquest.UIDatas.ItemData;

import static com.nithinmuthukumar.conquest.Globals.*;

public class InventoryTable extends Group {
    private Table table;


    private DataButton meleeSlot;
    private DataButton shootSlot;
    private DataButton throwSlot;
    private DataButton shieldSlot;
    private DataButton[][] inventory;
    private final int inventorySize = 10;


    public InventoryTable() {
        //hold the weapons and displays the current weapon in the slot
        meleeSlot = new DataButton("melee", false);
        shootSlot = new DataButton("shoot", false);
        throwSlot = new DataButton("throw", false);
        shieldSlot = new DataButton("shield", false);
        meleeSlot.setPosition(200, 100);
        shootSlot.setPosition(300, 100);
        throwSlot.setPosition(400, 100);
        shieldSlot.setPosition(500, 100);


        table = new Table();
        table.setPosition(200, 200);
        //takes all the items in the players inventory and creates a table of the buttons
        inventory = new DataButton[inventorySize][inventorySize];
        for (int y = 0; y < inventorySize; y++) {
            for (int x = 0; x < inventorySize; x++) {
                inventory[y][x] = new DataButton("inventory", false);
                table.add(inventory[y][x]).size(32, 32);

                inventory[y][x].addListener(new CClickListener<>(new Vector2(x, y)) {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        //gets the button that has been pressed
                        DataButton button = inventory[(int) object.y][(int) object.x];
                        //if there is nothing in the button there is nothing to be done
                        if (button.getData() == null) {
                            return;
                        }
                        //based on what the item is, it is placed in different slots
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
                        //sends the message to all players that a weapon has been switched
                        Globals.conquestClient.getClient().sendTCP(new WeaponSwitchMessage(data.name, data.getType()));
                        //this switches the weapon in the slot with the item in the spot that was clicked
                        if (inventory[(int) object.y][(int) object.x].getData() == null) {
                            equipComp.get(player.getEntity()).inventory.removeIndex((int) (object.y * inventorySize + object.x));
                            //updating inventory will close the gap made by the weapon that is in the slot
                            updateInventory();
                        } else {
                            //we switch the item in the slot with the item in the inventory of the player
                            equipComp.get(player.getEntity()).inventory.set((int) (object.y * inventorySize + object.x), (ItemData) inventory[(int) object.y][(int) object.x].getData());
                        }


                        super.clicked(event, x, y);
                    }
                });
            }
            table.row();

        }
        addActor(table);
        addActor(meleeSlot);
        addActor(shootSlot);
        addActor(throwSlot);
        addActor(shieldSlot);


    }
    //updates the inventory to match what the player has picked up
    //is only called once when the inventory is opened because the player can't pick up anything while using the inventory

    public void updateInventory() {

        //reset the data of the gui
        for (int y = 0; y < inventory.length; y++) {
            for (int x = 0; x < inventory[y].length; x++) {
                inventory[y][x].setData(null);
            }
        }
        Array<ItemData> pInventory = equipComp.get(player.getEntity()).inventory;

        //set the data based on what the entity is holding
        for (int i = 0; i < pInventory.size; i++) {
            int y = i / inventorySize;
            int x = i % inventorySize;


            inventory[y][x].setData(pInventory.get(i));


        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void setStage(Stage stage) {
        //checks if the item is all used up and gets rid of it if it is
        if (shootSlot.getData() != null && playerComp.get(player.getEntity()).shootUses <= 0) {
            shootSlot.setData(null);

        }
        if (meleeSlot.getData() != null && playerComp.get(player.getEntity()).meleeUses <= 0) {
            meleeSlot.setData(null);

        }
        if (shieldSlot.getData() != null && playerComp.get(player.getEntity()).shieldUses <= 0) {
            shieldSlot.setData(null);

        }
        if (throwSlot.getData() != null && playerComp.get(player.getEntity()).throwUses <= 0) {
            throwSlot.setData(null);

        }
        updateInventory();


        super.setStage(stage);
    }

}
