package com.nithinmuthukumar.conquest.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectFloatMap;
import com.nithinmuthukumar.conquest.Components.AIComponent;
import com.nithinmuthukumar.conquest.Components.FollowComponent;
import com.nithinmuthukumar.conquest.Components.TargetComponent;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.HashMap;
import java.util.PriorityQueue;

import static com.nithinmuthukumar.conquest.Conquest.gameMap;
import static com.nithinmuthukumar.conquest.Globals.*;

public class PathFindingSystem extends IntervalIteratingSystem {
    public PathFindingSystem() {
        super(Family.all(AIComponent.class, FollowComponent.class, TransformComponent.class).get(), 0.5f);
    }


    @Override
    protected void processEntity(Entity entity) {

        AIComponent ai = aiComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        Vector2 goal;


        if (follow.target == null || follow.target.getComponents().size() == 0) {
            if (ai.overallGoal == null) {
                return;
            }
            goal = ai.overallGoal.cpy();
        } else {
            goal = transformComp.get(follow.target).pos.cpy();

        }





        TargetComponent target = targetComp.get(entity);

        Vector2 start = transformComp.get(entity).pos.cpy().add(0, 0);
        //get the positions on the grid
        start.x = MathUtils.round(start.x / gameMap.getTileWidth());
        start.y = MathUtils.round(start.y / gameMap.getTileHeight());

        goal.x = MathUtils.round(goal.x / gameMap.getTileWidth());
        goal.y = MathUtils.round(goal.y / gameMap.getTileHeight());

        //if either the target or the goal is offmap or on an unreachable spot we return
        if (gameMap.getTileInfo(start.x * 16, start.y * 16) == COLLIDE || gameMap.getTileInfo(goal.x * 16, goal.y * 16) == COLLIDE
                || !Utils.inBounds(-1, 201, start.x) || !Utils.inBounds(-1, 201, start.y)
                || !Utils.inBounds(-1, 201, goal.x) || !Utils.inBounds(-1, 201, goal.y)) {

            return;
        }
        ObjectFloatMap<Vector2> closed = new ObjectFloatMap<>();
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
        Vector2[] directions = new Vector2[]{new Vector2(1, 0), new Vector2(0, 1), new Vector2(-1, 0), new Vector2(0, -1)};


        while (!queue.isEmpty()) {


            Vector3 cur = queue.poll();


            Vector2 prev = new Vector2(cur.x, cur.y);
            if (prev.equals(goal)) {
                break;
            }

            for (Vector2 d : directions) {

                Vector2 next = prev.cpy().add(d);
                if (gameMap.getTileInfo(next.x * 16, next.y * 16) == COLLIDE || !Utils.inBounds(-1, 201, next.x) || !Utils.inBounds(-1, 201, next.y)) {

                    continue;
                }
                float cost = closed.get(prev, 0) + 0.5f;


                if (!closed.containsKey(next)) {

                    path.put(next, prev);
                    closed.put(next, cost);


                    queue.add(new Vector3(next.x, next.y, goal.dst(next) + cost));

                }

            }

        }
        if (queue.isEmpty()) {
            System.out.println(queue);
            return;

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
