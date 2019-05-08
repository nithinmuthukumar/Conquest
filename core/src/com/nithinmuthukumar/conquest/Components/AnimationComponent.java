package com.nithinmuthukumar.conquest.Components;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.nithinmuthukumar.conquest.Enums.Action;
import com.nithinmuthukumar.conquest.Enums.Direction;
import com.nithinmuthukumar.conquest.Helpers.Utils;

public class AnimationComponent implements BaseComponent {
    public float aniTime=0;
    private String aniPath;
    private float speed;
    private String[] states;
    private int[] numFrames;
    ObjectIntMap<Action> stateByNumFrames;
    private ObjectMap<Action, ObjectMap<Direction, Animation<TextureRegion>>> animations;

    public BaseComponent create() {
        stateByNumFrames=new ObjectIntMap<>();
        for(int i=0;i<states.length;i++){
            stateByNumFrames.put(Action.valueOf(states[i]),numFrames[i]);

        }

        animations = new ObjectMap<>();

        FileHandle[] stateFiles = Utils.listFiles(new FileHandle(aniPath));
        for (FileHandle f : stateFiles) {

            Action action = Action.valueOf(f.name());
            //zero is the default value so that when it is not found it throws an error at the division of width
            int numFrames=stateByNumFrames.get(action,0);
            FileHandle[] dirFiles = Utils.listFiles(f);
            for (FileHandle d : dirFiles) {

                Direction direction = Direction.valueOf(d.nameWithoutExtension());
                Texture t = new Texture(d.path());
                //have to do this because frames is not recognized in TextureRegion.split
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
            animations.put(action, new ObjectMap<>());
        }
        animations.get(action).put(direction, sprites);
    }

    @Override
    public void reset() {
        animations=null;

    }

    public boolean isAnimationFinished(Action action, Direction direction) {
        return animations.get(action).get(direction).isAnimationFinished(aniTime);

    }
}
