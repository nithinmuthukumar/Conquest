package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.nithinmuthukumar.conquest.Action;
import com.nithinmuthukumar.conquest.Direction;
import com.nithinmuthukumar.conquest.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimationComponent implements Component {
    private HashMap<Action,HashMap<Direction,Animation<Texture>>> animations;
    public AnimationComponent(String path){
        animations = new HashMap<>();
        FileHandle[] stateFiles = Utilities.listFiles(new FileHandle(path));
        for (FileHandle f : stateFiles) {
            Action action = Action.valueOf(f.name());
            FileHandle[] dirFiles = Utilities.listFiles(f);
            for (FileHandle d : dirFiles) {
                Direction direction = Direction.valueOf(d.name());
                ArrayList<String> picList = new ArrayList<>();
                for (FileHandle c : Utilities.listFiles(d)) {
                    picList.add(c.path());
                }
                Texture[] frames = new Texture[picList.size()];
                for (int i = 0; i < picList.size(); i++) {
                    frames[i] = new Texture(picList.get(i));

                }
                put(action, direction, new Animation<>(0.1f, frames));
            }
        }
    }


    public Animation<Texture> get(Action action,Direction direction) {
        return animations.get(action).get(direction);
    }
    private void put(Action action, Direction direction, Animation<Texture> sprites) {
        if (!animations.containsKey(action)) {
            animations.put(action, new HashMap<>());
        }
        animations.get(action).put(direction, sprites);
    }

}
