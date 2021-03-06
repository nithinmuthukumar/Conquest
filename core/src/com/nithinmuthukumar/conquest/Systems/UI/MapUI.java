package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Helpers.Utils;
import com.nithinmuthukumar.conquest.Server.MapTargetMessage;

import java.util.TreeSet;

import static com.nithinmuthukumar.conquest.Globals.*;

public class MapUI extends Group {
    //the picture of the map
    private Image map;
    //holds the tools used to mark up the map
    private Table tools;
    //holds the sorted entities that are placed on the map based on their z ordering
    private TreeSet<Entity> mapPics;
    private boolean small = true;
    //the selection made by the selection tool
    private Rectangle selection = new Rectangle();


    private Tools curTool;
    private Array<Vector2> spots = new Array<>();
    //signal which signals that it needs a rectangle drawn
    private Signal signal;

    //icons will be color coded and the larger the gem the more dangerous the enemy

    public MapUI() {

        signal = new Signal();

        curTool = Tools.SELECT;



        mapPics = new TreeSet<>((o1, o2) -> {
            if (o1.getComponents().size() == 0) {
                return -1;

            }
            if (o2.getComponents().size() == 0) {
                mapPics.remove(o2);
                return 1;
            }

            if (builtComp.has(o1) == builtComp.has(o2))

                return Utils.zyComparator.compare(o1, o2);
                //only buildings are sorted by z order
            else if (builtComp.has(o1))
                return -1;
            else
                return 1;
        });
        map = new Image(new Drawable() {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {

                //the scale of the pictures to the world is determined by the size of the map
                float scaleW = width / (gameMap.getWidth() * gameMap.getTileWidth());
                float scaleH = height / (gameMap.getHeight() * gameMap.getTileHeight());
                for (Entity e : mapPics) {
                    //if the entity has been deleted but remains it is skipped over
                    if (e.getComponents().size() == 0) {
                        continue;
                    }
                    TransformComponent transform = transformComp.get(e);
                    //if it is a building draw a scaled down version of it
                    if (builtComp.has(e)) {
                        RenderableComponent renderable = renderComp.get(e);
                        batch.draw(renderable.region, x + transform.getRenderX() * scaleW,
                                y + transform.getRenderY() * scaleH, transform.width * scaleW, transform.height * scaleH);


                    }
                    //only draws enemies if the map is large
                    else if (!small) {
                        String color = colors[allianceComp.get(e).side];


                        color += " Gem 1";

                        batch.draw(Assets.style.get(color, TextureRegion.class), x + transform.getRenderX() * scaleW, y + transform.getRenderY() * scaleH);
                    }

                }
                //loops through all players and draws them with the gem of their color
                for (Entity player : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())) {
                    TransformComponent transform = transformComp.get(player);
                    String color = colors[allianceComp.get(player).side];
                    if (playerComp.has(player)) {
                        color += " Gem 4";
                        batch.draw(Assets.style.get(color, TextureRegion.class), x + transform.getRenderX() * scaleW, y + transform.getRenderY() * scaleH);


                    }

                }


            }

            @Override
            public float getLeftWidth() {
                return 0;
            }

            @Override
            public void setLeftWidth(float leftWidth) {
            }

            @Override
            public float getRightWidth() {
                return 0;
            }

            @Override
            public void setRightWidth(float rightWidth) {
            }

            @Override
            public float getTopHeight() {
                return 0;
            }

            @Override
            public void setTopHeight(float topHeight) {
            }

            @Override
            public float getBottomHeight() {
                return 0;
            }

            @Override
            public void setBottomHeight(float bottomHeight) {
            }

            @Override
            public float getMinWidth() {
                return 0;
            }

            @Override
            public void setMinWidth(float minWidth) {
            }

            @Override
            public float getMinHeight() {
                return 0;
            }

            @Override
            public void setMinHeight(float minHeight) {
            }
        });
        map.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //if the map is small make it larger when clicked on
                if (small) {
                    map.addAction(new ParallelAction(Actions.sizeTo(500f, 500f, 0.1f),
                            Actions.moveTo(Gdx.graphics.getWidth() / 2 - map.getImageWidth(),
                                    Gdx.graphics.getHeight() / 2 - map.getImageHeight(), 0.1f)));
                    addActor(tools);

                    small = false;
                    //the player input is turned off when the map is large
                    conquestClient.getInputHandler().off();
                }

                super.clicked(event, x, y);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                super.touchDragged(event, x, y, pointer);
                //changes the dimensions of the selection based on how the mouse is dragged
                if (!small && curTool == Tools.SELECT) {
                    //the x is given relative to the position of the map as 0,0 so add the map's position to normalize it
                    x += map.getX();
                    y += map.getY();
                    //size is set relative to where the rectangle started
                    selection.setSize(x - selection.x, y - selection.y);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (curTool == Tools.PIN) {
                    //new Rectangle is created for selection because map has an overrided function so kryo throws an error
                    conquestClient.getClient().sendTCP(new MapTargetMessage(selection, new Rectangle(map.getX(), map.getY(), map.getWidth(), map.getHeight()), x, y));


                }
                //resets the rectangle and its starting position
                else if (curTool == Tools.SELECT) {
                    x += map.getX();
                    y += map.getY();
                    selection.setSize(0);
                    selection.setPosition(x, y);
                }


                return super.touchDown(event, x, y, pointer, button);
            }
        });
        //gets the entities from the engine that are drawn in the map
        Family f = Family.one(BuiltComponent.class, AIComponent.class, AllianceComponent.class).exclude(WeaponComponent.class).get();


        map.setSize(250, 250);
        for (Entity e : engine.getEntitiesFor(f)) {
            mapPics.add(e);
        }
        //listens to changes in the entities within this family so that when something is added or removed it the treeset can be updated
        engine.addEntityListener(f, new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                mapPics.add(entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                mapPics.remove(entity);
            }
        });
        small = true;
        tools = new Table();

        //the buttons for the tools
        ImageButton selectButton = new ImageButton(Assets.style.getDrawable("Berserk"));

        selectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                curTool = Tools.SELECT;

                super.clicked(event, x, y);
            }
        });
        tools.add(selectButton);
        ImageButton pinButton = new ImageButton(Assets.style.getDrawable("Left Pointer"));
        pinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                curTool = Tools.PIN;
            }
        });

        tools.add(pinButton).row();
        tools.add(new Label("Select", Assets.style)).padRight(20);
        tools.add(new Label("Pin", Assets.style));
        tools.setPosition(500, 100);

        addActor(map);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (curTool == Tools.PIN) {
            //change cursor
        }

        super.draw(batch, parentAlpha);


        signal.dispatch(selection);


    }

    //changes the size of the map back to small
    public void makeSmall() {
        conquestClient.getInputHandler().on();
        small = true;
        tools.remove();
        selection.set(0, 0, 0, 0);
        map.addAction(new ParallelAction(Actions.sizeTo(250, 250, 0.1f), Actions.moveTo(0, 0, 0.1f)));
        //this is to make sure that it only becomes small after the sizeto and moveto are done so that the tool can't be used during that 0.1f
        map.addAction(
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        small = true;
                        return true;
                    }
                });
    }

    public boolean isSmall() {
        return small;
    }

    public Signal getSignal() {
        return signal;
    }

    private enum Tools {
        SELECT, PIN, MARK
    }
}
