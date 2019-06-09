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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.nithinmuthukumar.conquest.Assets;
import com.nithinmuthukumar.conquest.Components.*;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.TreeSet;

import static com.nithinmuthukumar.conquest.Globals.*;

public class MapUI extends Group {
    private Image map;
    private Table tools;
    private TreeSet<Entity> mapPics;
    private boolean small = true;
    private Rectangle selection = new Rectangle() {
        @Override
        public boolean contains(float x, float y) {
            float leftX = Math.min(this.x, this.x + width);
            float bottomY = Math.min(this.y, this.y + height);
            float rightX = Math.max(this.x, this.x + width);
            float topY = Math.max(this.y, this.y + width);
            return leftX <= x && rightX >= x && bottomY <= y && topY >= y;

        }
    };
    private Tools curTool;
    private Array<Vector2> spots = new Array<>();
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

            else if (builtComp.has(o1))
                return -1;
            else
                return 1;
        });
        map = new Image(new Drawable() {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {

                float scaleW = width / renderComp.get(mapPics.first()).region.getRegionWidth();
                float scaleH = height / renderComp.get(mapPics.first()).region.getRegionHeight();
                for (Entity e : mapPics) {
                    if (e.getComponents().size() == 0) {
                        continue;
                    }
                    TransformComponent transform = transformComp.get(e);
                    if (builtComp.has(e)) {
                        RenderableComponent renderable = renderComp.get(e);
                        batch.draw(renderable.region, x + transform.getRenderX() * scaleW,
                                y + transform.getRenderY() * scaleH, transform.width * scaleW, transform.height * scaleH);

                    } else if (!small || playerComp.has(e)) {
                        String color = Conquest.colors[allianceComp.get(e).side];

                        if (playerComp.has(e)) {
                            color += " Gem 4";

                        } else {

                            color += " Gem 1";
                        }
                        batch.draw(Assets.style.get(color, TextureRegion.class), x + transform.pos.x * scaleW, y + transform.pos.y * scaleH);
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

                if (small) {
                    map.addAction(new ParallelAction(Actions.sizeTo(500f, 500f, 0.1f),
                            Actions.moveTo(Gdx.graphics.getWidth() / 2 - map.getImageWidth(),
                                    Gdx.graphics.getHeight() / 2 - map.getImageHeight(), 0.1f)));
                    addActor(tools);

                    small = false;
                    Conquest.client.getInputHandler().off();
                }

                super.clicked(event, x, y);
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (!small && curTool != Tools.PIN) {
                    //the x is given relative to the position of the map as 0,0 so add the map's position to normalize it
                    x += map.getX();
                    y += map.getY();
                    selection.setSize(x - selection.x, y - selection.y);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (curTool == Tools.PIN) {

                    float scaleW = map.getWidth() / renderComp.get(mapPics.first()).region.getRegionWidth();
                    float scaleH = map.getHeight() / renderComp.get(mapPics.first()).region.getRegionWidth();


                    spots.add(selection.getPosition(new Vector2()));

                    for (Entity e : mapPics) {

                        if (aiComp.has(e) && allianceComp.get(e).side == Conquest.client.getClient().getID()) {


                            if (selection.contains(transformComp.get(e).pos.cpy().scl(scaleW, scaleH).add(map.getX(), map.getY()))) {
                                aiComp.get(e).overallGoal = new Vector2(x / scaleW, y / scaleH);


                            }

                        }
                    }


                } else if (curTool == Tools.SELECT) {
                    x += map.getX();
                    y += map.getY();
                    selection.setSize(0);
                    selection.setPosition(x, y);
                }


                return super.touchDown(event, x, y, pointer, button);
            }
        });
        Family f = Family.one(PlayerComponent.class, BuiltComponent.class, AIComponent.class).exclude(WeaponComponent.class).get();


        map.setSize(250, 250);
        for (Entity e : Conquest.engine.getEntitiesFor(f)) {
            mapPics.add(e);
        }
        Conquest.engine.addEntityListener(f, new EntityListener() {
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
        tools.add(pinButton);
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


    public void makeSmall() {
        Conquest.client.getInputHandler().on();
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
