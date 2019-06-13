package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntMap;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.*;

import java.io.IOException;

import static com.nithinmuthukumar.conquest.Globals.*;

public class ConquestClient extends Listener {
    private Client client;
    private String ip = "localhost";
    private IntMap<PlayerController> controllers;
    private ClientInput clientInput;
    private int numPlayers = 0;


    public ConquestClient() {
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
                if (object instanceof Message) ((Message) object).id = getID();

                return super.sendTCP(object);

            }
        };
        Utils.registerClasses(client.getKryo());
        client.start();
        try {
            client.connect(5000, ip, 54555, 54777);
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
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                update(object);
            }
        });


        super.received(connection, object);
    }

    public void update(Object object) {
        if (object == null) {
            return;
        }

        if (object.equals("one player")) {
            EntityFactory.createBuilding(32, 32, Assets.buildingDatas.get("barracks")).add(engine.createComponent(AllianceComponent.class).create(2)).add(engine.createComponent(AIComponent.class).create());
        }
        if (object instanceof PlayerMessage) {

            Entity p = Assets.recipes.get("player").make();
            BodyComponent body = bodyComp.get(p);
            body.body.setTransform(((PlayerMessage) object).x, ((PlayerMessage) object).y, body.body.getAngle());
            p.add(engine.createComponent(AllianceComponent.class).create(((PlayerMessage) object).id));
            Utils.setUserData(p);
            engine.addEntity(p);
            numPlayers += 1;

            if (((PlayerMessage) object).id == client.getID()) {

                p.add(engine.createComponent(CameraComponent.class));
                player = new Player(p);


            }
            if (numPlayers == 1) {
                game.setScreen(game.playScreen);

            }
            controllers.put(((PlayerMessage) object).id, new PlayerController(p));
        }
        if (object instanceof PlayerDeathMessage) {
            controllers.remove(((PlayerDeathMessage) object).id);
            for (Entity e : engine.getEntitiesFor(Family.all(AllianceComponent.class).get())) {
                if (allianceComp.get(e).side == ((PlayerDeathMessage) object).id) {
                    e.add(engine.createComponent(RemovalComponent.class).create(1));
                }
            }
        }
        if (object instanceof ItemMessage) {
            engine.addEntity(EntityFactory.createItem(Assets.itemDatas.get(((ItemMessage) object).name), ((ItemMessage) object).x, ((ItemMessage) object).y));

        }
        if (object instanceof MapTargetMessage) {
            MapTargetMessage message = (MapTargetMessage) object;
            System.out.println(((MapTargetMessage) object).mapDimensions);
            //if it has the velocity component it means it can move
            ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(AIComponent.class, VelocityComponent.class).get());
            float scaleW = message.mapDimensions.width / 3200;
            float scaleH = message.mapDimensions.height / 3200;


            for (Entity e : entities) {


                if (aiComp.has(e) && allianceComp.get(e).side == message.id) {


                    if (Utils.rectContains(message.selection, transformComp.get(e).pos.cpy().scl(scaleW, scaleH).add(message.mapDimensions.x, message.mapDimensions.y))) {
                        System.out.println(e);
                        aiComp.get(e).overallGoal = new Vector2(message.x / scaleW, message.y / scaleH);


                    }

                }
            }
        }
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


    public class ClientInput implements InputProcessor {
        private boolean on = true;

        public void flip() {
            if (on) off();
            else on();
        }

        public void off() {

            on = false;

        }

        public void on() {
            on = true;
        }

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


}

