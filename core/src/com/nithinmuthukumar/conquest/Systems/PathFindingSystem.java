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

import static com.nithinmuthukumar.conquest.GameMap.COLLIDE;
import static com.nithinmuthukumar.conquest.Globals.*;

public class PathFindingSystem extends IntervalIteratingSystem {
    public PathFindingSystem() {
        super(Family.all(AIComponent.class, FollowComponent.class, TransformComponent.class).get(), 0.5f, 2);
    }


    @Override
    protected void processEntity(Entity entity) {

        AIComponent ai = aiComp.get(entity);
        FollowComponent follow = followComp.get(entity);
        Vector2 goal;

        //if there is not entity target to follow, the overall goal becomes the goal
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
        //this holds all the values that have already been visited
        ObjectFloatMap<Vector2> closed = new ObjectFloatMap<>();
        //the path where the value for each key is where it came from
        HashMap<Vector2, Vector2> path = new HashMap<>();

        //puts the starting point where came from nowhere
        path.put(start, null);
        //the priority queue sorts based on the z which in this case would be the cost to move to that position
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
        //holds all the directions
        //the directions are done 4 ways and not eight because an Euclidean distance heuristic does not work properly for eight directions
        Vector2[] directions = new Vector2[]{new Vector2(1, 0), new Vector2(0, 1), new Vector2(-1, 0), new Vector2(0, -1)};

        //exits if there is nothing left meaning no path was found
        while (!queue.isEmpty()) {

            //gets the highest priority value from the queue
            Vector3 cur = queue.poll();


            Vector2 prev = new Vector2(cur.x, cur.y);
            //if the goal is reached we exit the loop
            if (prev.equals(goal)) {
                break;
            }

            for (Vector2 d : directions) {
                //add the direction value to the previous value
                Vector2 next = prev.cpy().add(d);
                //if the value on the map is invalid due to it being off the map or on a collide position we scrap that position
                if (gameMap.getTileInfo(next.x * 16, next.y * 16) == COLLIDE || !Utils.inBounds(-1, 201, next.x) || !Utils.inBounds(-1, 201, next.y)) {

                    continue;
                }
                //the cost of movement to this point is only incremented by 0.5 so that
                //it does not devolve into a bfs by valuing number of steps over distance from goal
                float cost = closed.get(prev, 0) + 0.5f;

                //if the path was not visited
                //there is no check to see whether there is a more efficient path by checking the cost in closed becaused
                //the obstacles are not complicated and shortest path doesn't matter that much
                if (!closed.containsKey(next)) {

                    path.put(next, prev);
                    closed.put(next, cost);

                    //add is to the queue with its distance also being a factor in its priority
                    queue.add(new Vector3(next.x, next.y, goal.dst(next) + cost));

                }

            }

        }
        //if there is no path we return
        if (queue.isEmpty()) {
            System.out.println(queue);
            return;

        }
        //loop through all nodes from the target to the start and check if we can go in a straight line
        // from the current position to that position which would be the shortest path
        //this is necessary so that the entities don't move in a 4 directional manner
        Vector2 trace = goal;
        while (!start.equals(path.get(trace))) {
            if (gameMap.isPassable(start, trace)) {
                break;
            }
            trace = path.get(trace);

        }


        //scales the target back from the grid units
        target.target = trace.scl(16);






    }

}
