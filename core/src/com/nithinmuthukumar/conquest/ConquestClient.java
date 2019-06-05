package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntMap;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.CameraComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Helpers.EntityFactory;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.*;
import com.nithinmuthukumar.conquest.Systems.PlayerController;

import java.io.IOException;

import static com.nithinmuthukumar.conquest.Conquest.engine;
import static com.nithinmuthukumar.conquest.Conquest.player;
import static com.nithinmuthukumar.conquest.Globals.bodyComp;

public class ConquestClient extends Listener {
    private Client client;
    private String ip = "localhost";
    private Conquest game;
    private IntMap<PlayerController> controllers;
    private ClientInput clientInput;


    public ConquestClient(Conquest game) {
        this.game = game;
        controllers = new IntMap<>();
        clientInput = new ClientInput();


    }

    public ClientInput getInputHandler() {
        return clientInput;
    }

    public void start() {
        client = new Client();
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

        if (object.equals("play")) {
            game.setScreen(game.playScreen);
        }
        if (object.equals("one player")) {
            EntityFactory.createBuilding(32, 32, Assets.buildingDatas.get("barracks")).add(engine.createComponent(AllianceComponent.class).create(5)).add(engine.createComponent(AIComponent.class).create());
        }
        if (object instanceof PlayerMessage) {
            Entity p = Assets.recipes.get("player").make();
            BodyComponent body = bodyComp.get(p);
            body.body.setTransform(((PlayerMessage) object).x, ((PlayerMessage) object).y, body.body.getAngle());
            p.add(engine.createComponent(AllianceComponent.class).create(((PlayerMessage) object).id));
            Utils.setUserData(p);
            engine.addEntity(p);
            if (((PlayerMessage) object).id == client.getID()) {

                p.add(engine.createComponent(CameraComponent.class));
                player = new Player(p);

            }
            controllers.put(((PlayerMessage) object).id, new PlayerController(p));
        }
        if (object instanceof ItemMessage) {
            engine.addEntity(EntityFactory.createItem(Assets.itemDatas.get(((ItemMessage) object).name), ((ItemMessage) object).x, ((ItemMessage) object).y));

        }
        if (object instanceof InputMessage) {
            controllers.get(((InputMessage) object).id).process((InputMessage) object);
        }
        if (object instanceof BuildMessage) {
            EntityFactory.createBuilding(((BuildMessage) object).buildX, ((BuildMessage) object).buildY, Assets.buildingDatas.get(((BuildMessage) object).name)).add(engine.createComponent(AllianceComponent.class).create(((BuildMessage) object).id));
        }
        if (object instanceof SpawnMessage) {

            Entity e = Assets.recipes.get(((SpawnMessage) object).name).make();
            e.add(Conquest.engine.createComponent(AllianceComponent.class).create(((SpawnMessage) object).id));


            BodyComponent body = bodyComp.get(e);

            body.body.setTransform(((SpawnMessage) object).x, ((SpawnMessage) object).y - 40, body.body.getAngle());


            Utils.setUserData(e);
            Conquest.engine.addEntity(e);
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
                client.sendTCP(new InputMessage(client.getID(), "keyDown", keycode));

            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "keyUp", keycode));
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "touchDown", screenX, screenY, pointer, button));
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "touchUp", screenX, screenY, pointer, button));
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "touchDragged", screenX, screenY, pointer));
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "mouseMoved", Utils.screenToCameraX(screenX), Utils.screenToCameraY(screenY)));
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            if (on)
                client.sendTCP(new InputMessage(client.getID(), "scrolled", amount));
            return false;
        }
    }


}

