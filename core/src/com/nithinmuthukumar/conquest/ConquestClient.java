package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.nithinmuthukumar.conquest.Components.BodyComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.AllianceComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.PlayerMessage;

import java.io.IOException;

import static com.nithinmuthukumar.conquest.Conquest.engine;
import static com.nithinmuthukumar.conquest.Conquest.player;
import static com.nithinmuthukumar.conquest.Globals.bodyComp;

public class ConquestClient extends Listener {
    public Client client;
    private String ip = "localhost";

    public ConquestClient() {
        client = new Client();
        Utils.registerClasses(client.getKryo());
        client.start();
        try {
            client.connect(5000, ip, 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                super.received(connection, object);
            }
        });

    }

    public Client getClient() {
        return client;
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("connected");
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof PlayerMessage) {

            player = new Player(Assets.recipes.get("player").make());
            BodyComponent body = bodyComp.get(player.getEntity());
            body.body.setTransform(MathUtils.random(((PlayerMessage) object).x), MathUtils.random(((PlayerMessage) object).y), body.body.getAngle());
            player.getEntity().add(engine.createComponent(AllianceComponent.class).create(((PlayerMessage) object).side));
            Utils.setUserData(player.getEntity());
            engine.addEntity(player.getEntity());
        }


        super.received(connection, object);
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
