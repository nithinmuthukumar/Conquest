package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class AIComponent implements BaseComponent {
    public Family targetType;
    public String[] components;






    @Override
    public BaseComponent create() {
        Family.Builder builder = Family.all();
        for (String s : components) {
            try {
                builder.all(ClassReflection.forName(s));
            } catch (ReflectionException e) {
                e.printStackTrace();
            }
        }
        targetType = builder.get();

        return this;

    }

    @Override
    public void reset() {


    }
}
