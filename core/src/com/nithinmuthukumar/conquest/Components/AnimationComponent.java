package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.Utils;

import java.util.HashMap;

public class AnimationComponent implements Component,Cloneable {
    private HashMap<Action,HashMap<Direction,Animation<TextureRegion>>> animations;

    public AnimationComponent(String path, float speed, int numFrames) {
        animations = new HashMap<>();

        FileHandle[] stateFiles = Utils.listFiles(new FileHandle(path));
        for (FileHandle f : stateFiles) {
            Action action = Action.valueOf(f.name());
            FileHandle[] dirFiles = Utils.listFiles(f);
            for (FileHandle d : dirFiles) {
                Direction direction = Direction.valueOf(d.nameWithoutExtension());
                Texture t = new Texture(d.path());
                TextureRegion[] frames = TextureRegion.split(t, t.getWidth() / numFrames, t.getHeight())[0];
                put(action, direction, new Animation<>(speed, frames));
            }
        }
    }


    public Animation<TextureRegion> get(Action action, Direction direction) {
        System.out.println(action);

        return animations.get(action).get(direction);
    }
    private void put(Action action, Direction direction, Animation<TextureRegion> sprites) {
        if (!animations.containsKey(action)) {
            animations.put(action, new HashMap<>());
        }
        animations.get(action).put(direction, sprites);
    }
    @Override
    protected AnimationComponent clone() throws CloneNotSupportedException{
        return (AnimationComponent)super.clone();

    }

}
