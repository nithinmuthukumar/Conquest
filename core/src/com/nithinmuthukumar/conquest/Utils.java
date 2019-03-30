package com.nithinmuthukumar.conquest;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.files.FileHandle;
import com.nithinmuthukumar.conquest.Components.*;

import java.util.Arrays;
import java.util.Comparator;


public class Utils {
    public static final ComponentMapper<TransformComponent> transformComp = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<BodyComponent> bodyComp=ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<StateComponent> stateComp=ComponentMapper.getFor(StateComponent.class);
    public static final ComponentMapper<VelocityComponent> velocityComp=ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<RenderableComponent> renderComp=ComponentMapper.getFor(RenderableComponent.class);
    public static final int NO_TILE=0;
    public static final int COLLIDE=1;
    public static final int ELEVATE_COLLIDE=4;
    public static final int INSIDE_HOUSE=3;
    public static final int ELEVATE =2;
    public static final int PLACEMENT_COLLIDE=5;
    public static final int VERTICAL_MOVEMENT=6;
    public static final int FLOOR_COLLIDE = 7;
    public static final int FOUR_DIRECTIONAL_MOVEMENT=8;

    public static final int PPM = 100;
    public static void print(String file,String... message){
        System.out.print(file+": ");
        for(String s:message){
            System.out.print(s+" ");

        }
        System.out.print("\n");

    }
    public static String joinArray(String[] strings){
        StringBuilder stringBuilder=new StringBuilder();
        for(String s: strings){

            stringBuilder.append(s);

        }

        return stringBuilder.toString();

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

}
