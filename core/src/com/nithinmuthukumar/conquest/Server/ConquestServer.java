package com.nithinmuthukumar.conquest.Server;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntIntMap;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.io.IOException;

public class ConquestServer extends Listener {
    IntIntMap teams = new IntIntMap();
    private Server server;

    public ConquestServer(Server server) {
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

        teams.put(connection.getID(), server.getConnections().length);
        if (server.getConnections().length > 2) {
            for (int i = 0; i < server.getConnections().length; i++)
                server.sendToAllTCP(new PlayerMessage(MathUtils.random(0, 3200), MathUtils.random(0, 3200), teams.get(i, 0)));
        }

        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object object) {

        for (Connection c : server.getConnections()) {

            if (c.getID() != connection.getID()) {

                c.sendTCP(object);
            }
        }

        super.received(connection, object);
    }


    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
