package com.nithinmuthukumar.conquest;

import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.Comparator;

public class Utilities {
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
