package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.FollowComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.HashMap;
import java.util.PriorityQueue;

import static com.nithinmuthukumar.conquest.Conquest.gameMap;
import static com.nithinmuthukumar.conquest.Globals.*;

public class PathFindingSystem extends IteratingSystem {
    public PathFindingSystem() {
        super(Family.all(AIComponent.class, FollowComponent.class, TransformComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (followComp.get(entity).target == null) {
            return;

        }


        AIComponent ai = aiComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        TargetComponent target = targetComp.get(entity);
        Vector2 start = transformComp.get(entity).pos.cpy().add(8, 8);
        start.x = MathUtils.round(start.x / 16);
        start.y = MathUtils.round(start.y / 16);
        Vector2 goal = transformComp.get(follow.target).pos.cpy().add(8, 8);
        goal.x = MathUtils.round(goal.x / 16);
        goal.y = MathUtils.round(goal.y / 16);
        ObjectIntMap<Vector2> closed = new ObjectIntMap<>();
        HashMap<Vector2, Vector2> path = new HashMap<>();

        path.put(start, null);

        PriorityQueue<Vector3> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1.z < o2.z) {
                return -1;
            }
            if (o2.z < o1.z) {
                return 1;
            }
            return 0;
        });
        queue.add(new Vector3(start.x, start.y, 0));
        closed.put(start, 0);
        while (!queue.isEmpty()) {



            Vector3 cur = queue.poll();



            Vector2 prev = new Vector2(cur.x, cur.y);
            if (prev.equals(goal)) {
                break;
            }

            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {

                    Vector2 next = prev.cpy().add(x, y);
                    int cost = MathUtils.round(closed.get(prev, 0)) + 1;
                    if (gameMap.getTileInfo(next.x * 16, next.y * 16) == COLLIDE || (x == 0 && y == 0) || !Utils.inBounds(-1, 201, x) || !Utils.inBounds(-1, 201, y)) {
                        continue;
                    }


                    if (!closed.containsKey(next) || closed.get(next, 0) > cost) {

                        path.put(next, prev);

                        closed.put(next, cost);
                        queue.add(new Vector3(next.x, next.y, MathUtils.round(cost + goal.dst(next))));

                    }
                }
            }


        }

        Vector2 trace = goal;
        while (!start.equals(path.get(trace))) {
            if (gameMap.isPassable(start, trace)) {
                break;
            }
            trace = path.get(trace);

        }


        target.target = trace.scl(16);






    }

}
