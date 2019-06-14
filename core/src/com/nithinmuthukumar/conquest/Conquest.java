//Nithin Muthukumar
//ICS4U
//This is a pvp top down game where players fight to the death with a variety of weapons, towers and troops
//they can also direct troops with the map and networking is done over the LAN
//there is also a sandbox mode where players can just play against enemy ai
package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nithinmuthukumar.conquest.Screens.MenuScreen;


public class Conquest extends Game {
	/*
	System priorities

	0-FollowAI
	1-TowerAI,ShooterAI,MeleeAI,SpawnerAI -this is done right after follow so that all target are already chosen
	2-Pathfinding -Pathfinding happens right away to determine the next positional target
	3-Target -sets the velocity to move towards the target
	4-Direction -determines the direction that an entity will face
	5-Animation,StateParticle -animation is done after all states and direction is set
	6-Collision, -collision checks happen to determine if there is any knockback which would override any movements mad by an entity
	7-Movement -sets the velocity of the box2d in preparation for the world step
	8-Physics
	9-Death, Decay -add removal stuff
	10-Removal,Camera -remove entities that are supposed to be removed
	11-RenderManager -these three draw things with the game being drawn first then the UI then shaperendering
	12-UI
	13-ShapeRender
	14-Spawn
	 */


    @Override
    public void create () {

        Globals.game = this;
        Globals.conquestClient = new ConquestClient();

        Globals.gameMap = new GameMap(3200, 3200, 16, 16);
        Globals.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        Assets.loadAllFiles();

        setScreen(new MenuScreen());


    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setTitle("Conquest " + Gdx.graphics.getFramesPerSecond());

        super.render();

    }

    @Override
    public void dispose () {
        Assets.dispose();
        Globals.batch.dispose();
    }
}
