package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Direction;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SocketSystem extends IteratingSystem {
    private final float UPDATE_TIME=1/60f;
    float timer=0;
    private Socket socket;
    private String removePlayerId=null;
    private LinkedList<JSONObject> newPlayers=new LinkedList<>();
    private ArrayList<JSONObject> updatePlayers=new ArrayList<>();
    private ComponentMapper<NetworkComponent> networkComp = ComponentMapper.getFor(NetworkComponent.class);
    private ComponentMapper<VelocityComponent> vm=ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<PositionComponent> positionComp=ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<StateComponent> stateComp=ComponentMapper.getFor(StateComponent.class);
    private Entity player;
    public SocketSystem(){

        super(Family.all(PlayerComponent.class,NetworkComponent.class,EnemyComponent.class).get());
        connectSocket();
        configSocketEvents();


    }


    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        player=getEngine().getEntitiesFor(Family.all(PlayerComponent.class).exclude(EnemyComponent.class).get()).first();
    }
    public void connectSocket(){
        try{
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, args -> Gdx.app.log("SocketIO","Connected"))
                .on("socketID", args -> {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String id=data.getString("id");
                        player.add(new NetworkComponent(id));


                        Gdx.app.log("SocketIO","My ID "+id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }).on("newPlayer", args -> {
                    JSONObject data = (JSONObject) args[0];
                    newPlayers.add(data);
                    Gdx.app.log("SocketIO","New Player Connect: ");
                }).on("playerDisconnected", args -> {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        removePlayerId=data.getString("id");
                        System.out.println(removePlayerId);
                        Gdx.app.log("SocketIO","removePlayer"+removePlayerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }).on("playerUpdate", args -> {
                    JSONObject data = (JSONObject) args[0];
                    System.out.println("updatePlayer");
                    updatePlayers.add(data);




                }).on("getPlayers",args->{
                    JSONArray objects= (JSONArray)args[0];

                    try {
                        for (int i = 0; i < objects.length(); i++) {
                            newPlayers.add(objects.getJSONObject(i));
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }

                });
    }
    //almost a clone of create player
    public void createEnemyPlayer(JSONObject object){
        try {
            Entity e = new Entity();
            e.add(new EnemyComponent());
            e.add(new NetworkComponent(object.getString("id")));
            e.add(new PositionComponent(0, 0));
            e.add(new VelocityComponent(1.2f));
            e.add(new RenderableComponent());


            e.add(new AnimationComponent("Character/"));


            e.add(new PlayerComponent());
            e.add(new StateComponent());
            getEngine().addEntity(e);
        }catch (JSONException e){
            e.printStackTrace();

        }




    }

    @Override
    public void update(float deltaTime) {
        PositionComponent position=positionComp.get(player);
        StateComponent state=stateComp.get(player);
        VelocityComponent velocity=vm.get(player);
        while(!newPlayers.isEmpty()){
            createEnemyPlayer(newPlayers.pop());
        }
        timer+=deltaTime;


        if(timer>= UPDATE_TIME) {
            JSONObject data = new JSONObject();
            try {
                data.put("x", position.x);
                data.put("y", position.y);
                data.put("angle", velocity.angle);
                data.put("z", position.z);
                data.put("action", state.action);
                data.put("direction", state.direction);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("playerUpdate", data);
            timer=0;

        }

        super.update(deltaTime);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
            //update everything about the player here
        PositionComponent position=positionComp.get(entity);
        NetworkComponent network=networkComp.get(entity);
        StateComponent state=stateComp.get(entity);
        VelocityComponent velocity=vm.get(entity);


        if(network.id.equals(removePlayerId)){
            getEngine().removeEntity(entity);
            removePlayerId=null;

        }

        try {
            JSONObject update=null;
            for(JSONObject u:updatePlayers){
                if(network.id.equals(u.getString("id"))){
                    update=u;

                }
            }
            if (update!=null) {
                updatePlayers.remove(update);


                position.x = (float)update.getDouble("x");
                position.y=(float)update.getDouble("y");
                velocity.angle=(float)update.getDouble("angle");
                position.z=(float)update.getDouble("z");
                state.action= Action.valueOf(update.getString("action"));
                state.direction=Direction.valueOf(update.getString("direction"));

            }
        }catch(JSONException e){
            e.printStackTrace();

        }

    }

}
