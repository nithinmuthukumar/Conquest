package com.nithinmuthukumar.conquest.Server;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.io.IOException;

public class ConquestServer extends Listener {
    private Server server;
    private boolean start = false;

    public ConquestServer(Server server) {
        this.server = server;

    }


    public static void main(String[] args) {
        Server server = new Server();
        //registers the classes that can be sent by the server
        Utils.registerClasses(server.getKryo());
        server.start();
        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.addListener(new ConquestServer(server));


    }


    @Override
    public void connected(Connection connection) {
        if (!start && server.getConnections().length < 5)
            super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof String) {
            if (object.equals("one player")) {
                for (int i = 0; i < 35; i++) {

                    server.sendToAllTCP(new BuildMessage("tree", MathUtils.random(100, 3100), MathUtils.random(100, 3100)));
                }


                for (Connection c : server.getConnections()) {
                    PlayerMessage playerMessage = new PlayerMessage(MathUtils.random(0, 200), MathUtils.random(0, 200), c.getID());
                    server.sendToAllTCP(playerMessage);
                }
                server.sendToAllTCP("one player");
                server.sendToAllTCP("play");

            }

            if (object.equals("ready") && server.getConnections().length > 1 && !start) {
                start = true;


                for (int i = 0; i < 35; i++) {

                    server.sendToAllTCP(new BuildMessage("tree", MathUtils.random(100, 3100), MathUtils.random(100, 3100)));
                }


                for (Connection c : server.getConnections()) {
                    PlayerMessage playerMessage = new PlayerMessage(MathUtils.random(0, 200), MathUtils.random(0, 200), c.getID());
                    server.sendToAllTCP(playerMessage);
                }

                server.sendToAllTCP("play");
            }
        }


        if (object instanceof InputMessage || object instanceof SpawnMessage
                || object instanceof BuildMessage || object instanceof MapTargetMessage || object instanceof PlayerDeathMessage) {
            server.sendToAllTCP(object);
        }
        if (object instanceof WeaponSwitchMessage) {
            server.sendToAllTCP(object);
        }

        /*for (Connection c : server.getConnections()) {

                if (c.getID() != connection.getID()) {

                    c.sendTCP(object);
                }
            }

         */

        super.received(connection, object);
    }


    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
