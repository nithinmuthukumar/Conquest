package com.nithinmuthukumar.conquest.Systems.UI;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.nithinmuthukumar.conquest.Components.Identifiers.BuiltComponent;
import com.nithinmuthukumar.conquest.Components.Identifiers.PlayerComponent;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Components.WeaponComponent;
import com.nithinmuthukumar.conquest.Conquest;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.TreeSet;

import static com.nithinmuthukumar.conquest.Globals.*;

public class MapTable {
    private Image map;
    private TreeSet<Entity> mapPics;
    private boolean small = true;

    public MapTable() {
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
                float scaleH = height / renderComp.get(mapPics.first()).region.getRegionWidth();
                for (Entity e : mapPics) {
                    TransformComponent transform = transformComp.get(e);
                    if (builtComp.has(e)) {
                        RenderableComponent renderable = renderComp.get(e);
                        batch.draw(renderable.region, x + transform.getRenderX() * scaleW, y + transform.getRenderY() * scaleH, transform.width * scaleW, transform.height * scaleH);
                    } else if (!small) {
                        if (playerComp.has(e)) {


                        }
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
                    map.addAction(new ParallelAction(Actions.scaleTo(2f, 2f, 2), Actions.moveTo(100, 200, 2)));

                    small = false;
                }
                super.clicked(event, x, y);
            }
        });
        map.setSize(250, 250);
        for (Entity e : Conquest.engine.getEntitiesFor(Family.one(PlayerComponent.class, BuiltComponent.class).exclude(WeaponComponent.class).get())) {
            mapPics.add(e);
        }
        Conquest.engine.addEntityListener(Family.one(PlayerComponent.class, BuiltComponent.class).all(TransformComponent.class).exclude(WeaponComponent.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                mapPics.add(entity);
            }

            @Override
            public void entityRemoved(Entity entity) {
                mapPics.remove(entity);
            }
        });
    }

    public Image getMap() {
        return map;
    }
}
