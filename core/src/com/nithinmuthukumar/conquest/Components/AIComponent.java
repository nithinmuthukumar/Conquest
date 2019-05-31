package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Family;
import com.nithinmuthukumar.conquest.Helpers.Utils;

public class AIComponent implements BaseComponent {
    private static final int ALL = 0;
    private static final int ONE = 1;
    private static final int EXCLUDE = 2;
    public Family[] targetOrder;
    //families are placed in order of importance
    public String[][][] families;
    public Family currentTarget;
    public int sightDistance;






    @Override
    public BaseComponent create() {
        if (families == null) {
            return this;
        }

        targetOrder = new Family[families.length];

        for (int i = 0; i < families.length; i++) {
            targetOrder[i] = Family.all(getComponents(i, ALL)).one(getComponents(i, ONE)).exclude(getComponents(i, EXCLUDE)).get();
        }


        return this;

    }

    public Class<BaseComponent>[] getComponents(int i, int t) {
        Class<BaseComponent>[] components = new Class[families[i][t].length];
        for (int j = 0; j < components.length; j++) {
            components[j] = Utils.getComponentClass(families[i][t][j]);
        }
        return components;

    }


    @Override
    public void reset() {


    }
}
