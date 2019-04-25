package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.Helpers.Utils;

import java.util.HashMap;

public class AnimationComponent implements BaseComponent {
    private String aniPath;
    private float speed;
    public int frames;
    private HashMap<Action,HashMap<Direction,Animation<TextureRegion>>> animations;

    public BaseComponent create() {
        animations = new HashMap<>();

        FileHandle[] stateFiles = Utils.listFiles(new FileHandle(aniPath));
        for (FileHandle f : stateFiles) {

            Action action = Action.valueOf(f.name());
            FileHandle[] dirFiles = Utils.listFiles(f);
            for (FileHandle d : dirFiles) {

                Direction direction = Direction.valueOf(d.nameWithoutExtension());
                Texture t = new Texture(d.path());
                //have to do this because frames is not recognized in TextureRegion.split
                int numFrames=frames;
                TextureRegion[] frames = TextureRegion.split(t, t.getWidth() / numFrames, t.getHeight())[0];
                put(action, direction, new Animation<>(speed, frames));
            }
        }
        return this;
    }


    public Animation<TextureRegion> get(Action action, Direction direction) {
        return animations.get(action).get(direction);

    }
    private void put(Action action, Direction direction, Animation<TextureRegion> sprites) {
        if (!animations.containsKey(action)) {
            animations.put(action, new HashMap<>());
        }
        animations.get(action).put(direction, sprites);
    }

    @Override
    public void reset() {
        animations=null;

    }
}
