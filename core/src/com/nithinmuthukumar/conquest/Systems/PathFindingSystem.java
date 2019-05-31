package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.FollowComponent;
import com.nithinmuthukumar.conquest.Components.VelocityComponent;

import java.util.PriorityQueue;

import static com.nithinmuthukumar.conquest.Globals.*;

public class PathFindingSystem extends IntervalIteratingSystem {
    public PathFindingSystem() {
        super(Family.all(AIComponent.class, FollowComponent.class).get(), 1);
    }

    @Override
    protected void processEntity(Entity entity) {
        Vector2 start = new Vector2(transformComp.get(entity).pos);
        AIComponent ai = aiComp.get(entity);
        VelocityComponent velocity = velocityComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        Vector2 target = transformComp.get(follow.target).pos;

        PriorityQueue<Vector3> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.z < o2.z) return 1;
            else if (o2.z < o1.z) return -1;
            else return 0;
        });
        queue.add(new Vector3(start, 0));
        while (queue.peek().x != target.x && queue.peek().y != target.y) {
            Vector3 cur = queue.poll();
            for (int i = 0; i < 8; i++) {

            }

        }


    }
}
