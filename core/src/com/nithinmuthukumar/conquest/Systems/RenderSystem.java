package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.nithinmuthukumar.conquest.Components.RenderableComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Assets;
import com.nithinmuthukumar.conquest.Utils;

import static com.nithinmuthukumar.conquest.Helpers.Globals.*;

public class RenderSystem{

    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent position = transformComp.get(entity);
        RenderableComponent renderable = renderComp.get(entity);
        Color c=batch.getColor();
        batch.setColor(c.r,c.g,c.b,renderable.alpha);
        batch.draw(renderable.region, position.getRenderX(), position.getRenderY(),renderable.originX, renderable.originY,
                renderable.region.getRegionWidth(),renderable.region.getRegionHeight(), 1,1,position.rotation);



        //batch.draw(renderable.region,position.getRenderX(), position.getRenderY());
    }
}
