package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.Arrays;
import java.util.Comparator;


public class Utils {

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

}
