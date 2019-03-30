package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.nithinmuthukumar.conquest.Components.TransformComponent;

import java.util.Comparator;
//need to update logic later
public class ZYComparator implements Comparator<Entity> {
    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

    @Override
    public int compare(Entity e1, Entity e2) {
        if(pm.get(e1).z==pm.get(e2).z){
            return (int) Math.signum(pm.get(e2).getRenderY() - pm.get(e1).getRenderY());
        }else{
            return (int)Math.signum(pm.get(e1).z - pm.get(e2).z);
        }

    }
}
