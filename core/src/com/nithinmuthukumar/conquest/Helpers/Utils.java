package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.nithinmuthukumar.conquest.Components.TransformComponent;
import com.nithinmuthukumar.conquest.GameMap;
import com.nithinmuthukumar.conquest.Globals;

import java.util.Arrays;
import java.util.Comparator;

import static com.nithinmuthukumar.conquest.Globals.bodyComp;
import static com.nithinmuthukumar.conquest.Globals.camera;


public class Utils {
    public static ZYComparator zyComparator=new ZYComparator();

    public static void print(String file, String... message){
        System.out.print(file+": ");
        for(String s:message){
            System.out.print(s+" ");

        }
        System.out.print("\n");

    }

    public static void print(String file, float... message) {
        for (float i : message) {
            System.out.print(i + " ");


        }
        System.out.println();

    }
    public static String joinArray(String[] strings){
        StringBuilder stringBuilder=new StringBuilder();
        for(String s: strings){

            stringBuilder.append(s);

        }

        return stringBuilder.toString();

    }
    public static void setUserData(Entity e){
        for(Fixture f:bodyComp.get(e).body.getFixtureList()){
            f.setUserData(e);
        }

    }
    public static float getTargetAngle(Vector2 p,Vector2 e){
        return MathUtils.radiansToDegrees * MathUtils.atan2(e.y - p.y, e.x - p.x);
    }
    public static FileHandle[] listFiles(FileHandle f) {
        //List all files in a directory that arent .DS_Store
        //mac problem
        //also sorts it that pictures always come out the proper order
        FileHandle[] fileHandles = f.list(n -> !new FileHandle(n).extension().equals("DS_Store"));
        Arrays.sort(fileHandles, Comparator.comparing(FileHandle::name));
        return fileHandles;
    }
    public static boolean inBounds(int lowerBound,int upperBound,int val){
        return lowerBound<val&&val<upperBound;

    }

    public static Texture resizeTexture(Texture texture, float newWidth, float newHeight) {
        Pixmap og = texture.getTextureData().consumePixmap();
        Pixmap new_ = new Pixmap(MathUtils.round(newWidth), MathUtils.round(newHeight), og.getFormat());
        new_.drawPixmap(og,
                0, 0, og.getWidth(), og.getHeight(),
                0, 0, new_.getWidth(), new_.getHeight()
        );


        return new Texture(new_);

    }

    public static int snapToGrid(GameMap gameMap, float x) {

        return MathUtils.round(gameMap.getTileWidth() * (MathUtils.ceil(x / gameMap.getTileWidth())));
    }
    public static float screenToCameraX(float x){

        return Globals.camera.position.x-Gdx.graphics.getWidth()/2+x;
    }
    public static float screenToCameraY(float y){

        return Globals.camera.position.y+ Gdx.graphics.getHeight()/2-y;
    }

    //need to update logic later
    public static class ZYComparator implements Comparator<Entity> {
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
}
