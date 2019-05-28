package com.nithinmuthukumar.conquest.Server;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.io.IOException;

public class ConquestServer extends Listener {
    private Server server;
    private int readies;
    private boolean start = false;

    public ConquestServer(Server server) {
        readies = 0;
        this.server = server;

    }


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Utils.registerClasses(server.getKryo());
        server.start();
        server.bind(54555, 54777);
        server.addListener(new ConquestServer(server));


    }


    @Override
    public void connected(Connection connection) {
        if (!start)
            super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof String) {
            if (object.equals("ready")) {
                readies += 1;
            }
        }
        if (readies == server.getConnections().length && !start) {

            server.sendToAllTCP("play");

            for (int i = 0; i < 20; i++) {
                server.sendToAllTCP(new ItemMessage("villager sword", MathUtils.random(0, 3200), MathUtils.random(0, 3200)));
                server.sendToAllTCP(new ItemMessage("crossbow", MathUtils.random(0, 3200), MathUtils.random(0, 3200)));
            }




            for (Connection c : server.getConnections()) {
                PlayerMessage playerMessage = new PlayerMessage(MathUtils.random(0, 3200), MathUtils.random(0, 3200), c.getID());
                server.sendToAllTCP(playerMessage);


            }


            start = true;
        }
        if (object instanceof InputMessage || object instanceof SpawnMessage || object instanceof BuildMessage) {
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
