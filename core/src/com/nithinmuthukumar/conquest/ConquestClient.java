package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntMap;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Screens.PlayScreen;
import com.nithinmuthukumar.conquest.Server.*;

import java.io.IOException;

import static com.badlogic.gdx.Input.Keys.*;
import static com.nithinmuthukumar.conquest.Globals.*;
import static com.nithinmuthukumar.conquest.Helpers.Utils.setMeleeTransform;

//The client object
//everything that is inputted is sent to the server and is processed by the client
public class ConquestClient extends Listener {
    private Client client;
    //a map of PlayerControllers where the client id is the key
    private IntMap<PlayerController> controllers;
    private ClientInput clientInput;



    public ConquestClient() {
        //holds all player controllers which are sent input based on what this class receives
        controllers = new IntMap<>();
        clientInput = new ClientInput();


    }

    public ClientInput getInputHandler() {
        return clientInput;
    }

    public void start() {
        client = new Client() {
            @Override
            public int sendTCP(Object object) {
                //all objects that are sent from the client side need id to be the client so it is set right here instead of
                //passing into the Messages constructor everytime
                if (object instanceof Message) ((Message) object).id = getID();

                return super.sendTCP(object);

            }
        };
        Utils.registerClasses(client.getKryo());
        client.start();
        try {
            //looks for servers that are binded to this port and connects to them
            client.connect(5000, client.discoverHost(54777, 5000).getHostAddress(), 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.addListener(this);

    }

    public Client getClient() {
        return client;
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        //this allows the update to happen asynchronously
        Gdx.app.postRunnable(() -> update(object));


        super.received(connection, object);
    }

    //checks the instance of a message and processes it accordingly
    public void update(Object object) {

        if (object == null) {
            return;
        }

        if (object instanceof String) {
            //start the game
            if (object.equals("play")) {
                game.setScreen(new PlayScreen());
            }
        }
        //creates the player sent
        if (object instanceof PlayerMessage) {

            Entity p = Assets.recipes.get("player").make();
            BodyComponent body = bodyComp.get(p);
            body.body.setTransform(((PlayerMessage) object).x, ((PlayerMessage) object).y, body.body.getAngle());
            //assigns the alliance of the entity to be the id of the message
            p.add(engine.createComponent(AllianceComponent.class).create(((PlayerMessage) object).id));
            Utils.setUserData(p);
            engine.addEntity(p);
            //camera is only added if this entity is the entity controlled by the client
            if (((PlayerMessage) object).id == client.getID()) {

                p.add(engine.createComponent(CameraComponent.class));
                player = new Player(p);


            }
            //the player is assigned to a controller which changes his attributes whenever
            controllers.put(((PlayerMessage) object).id, new PlayerController(p));
        }
        //removes the player in question from the controllers so that it does not error
        if (object instanceof PlayerDeathMessage) {
            controllers.remove(((PlayerDeathMessage) object).id);
            for (Entity e : engine.getEntitiesFor(Family.all(AllianceComponent.class).get())) {
                if (allianceComp.get(e).side == ((PlayerDeathMessage) object).id) {
                    e.add(engine.createComponent(RemovalComponent.class).create(1));
                }
            }
        }
        //sends an item that should spawned
        if (object instanceof ItemMessage) {
            engine.addEntity(EntityFactory.createItem(Assets.itemDatas.get(((ItemMessage) object).name), ((ItemMessage) object).x, ((ItemMessage) object).y));

        }
        //sends the target that the other client chose in MapUI
        if (object instanceof MapTargetMessage) {
            MapTargetMessage message = (MapTargetMessage) object;
            //if it has the velocity component it means it can move
            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(AIComponent.class, VelocityComponent.class).get());
            float scaleW = message.mapDimensions.width / gameMap.getWidth() * gameMap.getTileWidth();
            float scaleH = message.mapDimensions.height / gameMap.getHeight() * gameMap.getTileHeight();

            //loops through all the entities and checks if they are within the rectangle that was created
            for (Entity e : entities) {

                //if they are and are also on the same side the entities overall goal is set
                if (aiComp.has(e) && allianceComp.get(e).side == message.id) {
                    if (Utils.rectContains(message.selection, transformComp.get(e).pos.cpy().scl(scaleW, scaleH).add(message.mapDimensions.x, message.mapDimensions.y))) {
                        aiComp.get(e).overallGoal = new Vector2(message.x / scaleW, message.y / scaleH);


                    }

                }
            }
        }
        //has the controller of the player to process input
        if (object instanceof InputMessage) {
            controllers.get(((InputMessage) object).id).process((InputMessage) object);
        }
        if (object instanceof BuildMessage) {
            EntityFactory.createBuilding(((BuildMessage) object).buildX, ((BuildMessage) object).buildY, Assets.buildingDatas.get(((BuildMessage) object).name)).add(engine.createComponent(AllianceComponent.class).create(((BuildMessage) object).id));
        }
        if (object instanceof SpawnMessage) {

            Utils.spawn(((SpawnMessage) object));
        }
        if (object instanceof WeaponSwitchMessage) {
            controllers.get(((WeaponSwitchMessage) object).id).process((WeaponSwitchMessage) object);
        }

    }
    //un implement inputProcessor

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }

    //an inner class that processes input which is needed because it needs access to the client easily
    // and doesn't use anything outside this class
    public class ClientInput implements InputProcessor {
        private boolean on = true;

        public void flip() {
            if (on) off();
            else on();
        }

        public void off() {
            //this makes sure that the player stops when the input is inaccessible
            client.sendTCP(new InputMessage("keyUp", Input.Keys.R));
            on = false;

        }

        public void on() {
            on = true;
        }

        //processes an inputs the user makes and sends it to all other clients
        @Override
        public boolean keyDown(int keycode) {
            if (on)
                client.sendTCP(new InputMessage("keyDown", keycode));

            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (on)
                client.sendTCP(new InputMessage("keyUp", keycode));
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (on)
                client.sendTCP(new InputMessage("touchDown", screenX, screenY, pointer, button));
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (on)
                client.sendTCP(new InputMessage("touchUp", screenX, screenY, pointer, button));
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (on)
                client.sendTCP(new InputMessage("touchDragged", screenX, screenY, pointer));
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            //mouse moved is sent in terms of the world so that the variable camera position doesn't affect anything
            if (on)
                client.sendTCP(new InputMessage("mouseMoved", Utils.screenToCameraX(screenX), Utils.screenToCameraY(screenY)));
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            if (on)
                client.sendTCP(new InputMessage("scrolled", amount));
            return false;
        }
    }

    //this class is only used by ConquestClient so it is encapsulated within it
    private class PlayerController {
        private Entity weapon;
        private Entity player;
        private int x;
        private int y;


        public PlayerController(Entity player) {
            this.player = player;

        }


        public void keyDown(int keycode) {
            PlayerComponent p = playerComp.get(player);
            StateComponent state = stateComp.get(player);
            //once an object is out of use it is removed
            switch (keycode) {
                case R:
                    state.action = Action.WALK;
                    break;
                case NUM_1:

                    state.action = Action.IDLE;
                    //checks to make sure that the slot has something in it and that there are uses left
                    if (p.meleeSlot == null || p.meleeUses <= 0) {
                        p.meleeSlot = null;
                        return;
                    }
                    p.meleeUses -= 1;
                    weapon = Assets.recipes.get(p.meleeSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));


                    setMeleeTransform(player, weapon);

                    engine.addEntity(weapon);
                    break;


                case NUM_2:


                    if (p.shootSlot == null || p.shootUses <= 0) {
                        p.shootSlot = null;
                        return;
                    }
                    p.shootUses -= 1;
                    //sets the target of the shooter if the weapon has a target and
                    // if the weapon doesn't, it simply sets the angle of the velocity to face that direction
                    Entity shooter = Assets.recipes.get(p.shootSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                    if (targetComp.has(shooter)) {
                        targetComp.get(shooter).target = new Vector2(x, y);
                    }
                    if (velocityComp.has(shooter)) {
                        velocityComp.get(shooter).setAngle(velocityComp.get(player).angle());
                    }
                    //setting the angle of the picture and the position
                    transformComp.get(shooter).rotation = velocityComp.get(player).angle();
                    bodyComp.get(shooter).body.setTransform(bodyComp.get(player).body.getPosition(), velocityComp.get(player).angle());

                    engine.addEntity(shooter);
                    break;
                case NUM_3:
                    if (p.throwSlot == null) break;
                    if (p.throwUses <= 0) {
                        p.throwSlot = null;
                        return;
                    }
                    p.throwUses -= 1;

                    Entity w = Assets.recipes.get(p.throwSlot).make();
                    setMeleeTransform(player, w);
                    //reset rotation that happens in setMeleeTransform because these are not rotated
                    transformComp.get(w).rotation = 0;
                    engine.addEntity(w);
                    break;
                case NUM_4:


                    if (p.shieldSlot == null) break;
                    if (p.shieldUses <= 0) {
                        p.shieldSlot = null;

                        return;
                    }

                    p.shieldUses -= 1;
                    //stops the entity from moving when using the shield
                    state.action = Action.IDLE;

                    if (engine.getEntities().contains(weapon, true)) {
                        engine.removeEntity(weapon);
                    }

                    weapon = Assets.recipes.get(playerComp.get(player).shieldSlot).make().add(engine.createComponent(AllianceComponent.class).create(allianceComp.get(player).side));
                    transformComp.get(weapon).pos.set(transformComp.get(player).pos);
                    setMeleeTransform(player, weapon);
                    transformComp.get(weapon).rotation = 0;
                    stateComp.get(weapon).direction = state.direction;

                    engine.addEntity(weapon);
                    break;


                case A:
                    equipComp.get(player).equipping = true;
                    break;
            }
        }


        public void keyUp(int keycode) {
            switch (keycode) {
                //if r is no longer pressed the player is not moving
                case R:
                    stateComp.get(player).action = Action.IDLE;
                    break;
                //if the player is no longer pressing a he is no longer trying to pic things up
                case A:
                    equipComp.get(player).equipping = false;
                    break;
                case NUM_1:
                case NUM_4:
                    if (weapon == null) {
                        return;
                    }
                    if (shieldComp.has(weapon)) {
                        weapon.add(engine.createComponent(RemovalComponent.class));
                    }
                    weapon = null;
                    break;
            }

        }

        public void mouseMoved(int mScreenX, int mScreenY) {
            if (weapon != null && shieldComp.has(weapon)) {

                return;

            }
            x = mScreenX;
            y = mScreenY;

            VelocityComponent velocity = velocityComp.get(player);

            float angle = (float) Math.toDegrees(MathUtils.atan2(mScreenY - transformComp.get(player).pos.y, mScreenX - transformComp.get(player).pos.x));
            velocity.setAngle(angle);


        }


        public void process(InputMessage inputMessage) {
            int[] args = inputMessage.args;
            switch (inputMessage.type) {
                case "mouseMoved":
                    mouseMoved(args[0], args[1]);
                    break;
                case "keyUp":
                    keyUp(args[0]);
                    break;
                case "keyDown":
                    keyDown(args[0]);
            }
        }

        public void process(WeaponSwitchMessage object) {
            //receives the weapon that was switched and places it in the appropriate slot
            //uses are calculated based on the objects rarity

            PlayerComponent weapons = playerComp.get(player);
            if (object.slot.equals("shoot")) {
                weapons.shootSlot = object.weapon;
                weapons.shootUses = 10 + Assets.itemDatas.get(object.weapon).getRarity() * 10;
            } else if (object.slot.equals("throw")) {
                weapons.throwSlot = object.weapon;
                weapons.throwUses = 1 + Assets.itemDatas.get(object.weapon).getRarity();
            } else if (object.slot.equals("melee")) {
                weapons.meleeSlot = object.weapon;
                weapons.meleeUses = 10 + Assets.itemDatas.get(object.weapon).getRarity() * 10;

            } else if (object.slot.equals("shield")) {
                weapons.shieldSlot = object.weapon;
                weapons.shieldUses = 5 + Assets.itemDatas.get(object.weapon).getRarity() * 10;
            }
        }
    }


}

