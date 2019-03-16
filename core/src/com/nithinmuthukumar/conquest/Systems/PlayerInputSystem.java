package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Direction;
import com.nithinmuthukumar.conquest.Utilities;


//player
public class PlayerInputSystem extends EntitySystem implements InputProcessor{



    private Entity player;
    private ComponentMapper<StateComponent> stateComp = ComponentMapper.getFor(StateComponent.class);
    private ComponentMapper<PositionComponent> positionComp = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velocityComp = ComponentMapper.getFor(VelocityComponent.class);



    private String[] dir=new String[]{"",""};
    public PlayerInputSystem(){



    }
    @Override
    public void addedToEngine(Engine engine) {
        player = engine.getEntitiesFor(Family.all(PlayerComponent.class,StateComponent.class,PositionComponent.class,VelocityComponent.class).exclude(EnemyComponent.class).get()).first();
    }
    @Override
    public void update(float deltaTime){





    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean keyDown(int keycode) {
        StateComponent state=stateComp.get(player);


        if (keycode == Input.Keys.R){
            state.action=Action.WALK;
            player.add(new MovingComponent());


        }if (keycode==Input.Keys.SPACE){
            state.action=Action.ATTACK;

        }

        return false;


    }


    @Override
    public boolean keyUp(int keycode) {
        StateComponent state=stateComp.get(player);
        if(keycode==Input.Keys.R){
            state.action=Action.IDLE;
            player.remove(MovingComponent.class);
        }
        return false;

    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        PositionComponent position= positionComp.get(player);
        StateComponent state= stateComp.get(player);
        VelocityComponent velocity=velocityComp.get(player);


        float angle=(float)Math.toDegrees(Math.atan2(720/2-screenY,screenX-960/2));

        if(angle<0){
            angle=360+angle;
        }
        if(angle<67.5||angle>292.5) {
            dir[1]="RIGHT";
        }else if(angle>112.5&&angle<247.5){
            dir[1]="LEFT";
        }else{
            dir[1]="";
        }
        if(angle>90-67.5&&angle<90+67.5){
            dir[0]="UP";
        }else if(angle>270-67.5&&angle<270+45*3/2){
            dir[0]="DOWN";
        }else{
            dir[0]="";
        }
        state.direction=Direction.valueOf(Utilities.joinArray(dir));
        velocity.angle=angle;



        return false;
    }

    @Override
    public boolean scrolled(int amount) { return false; }


}
