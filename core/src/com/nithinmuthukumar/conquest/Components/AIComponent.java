package com.nithinmuthukumar.conquest.Components;

import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.nithinmuthukumar.conquest.Helpers.Utils;

//all entities that need decision making have this component
public class AIComponent implements BaseComponent {
    private static final int ALL = 0;
    private static final int ONE = 1;
    private static final int EXCLUDE = 2;
    //holds the order of target preference
    public Family[] targetOrder;
    //a position that the entity moves toward when there is no immediate target
    public Vector2 overallGoal = null;

    public Family currentTarget;
    //how far the entity can see
    public int sightDistance;
    public boolean isTargetChanger;
    //families are placed in order of importance
    private String[][][] families;






    @Override
    public BaseComponent create() {
        if (families == null) {
            return this;
        }

        targetOrder = new Family[families.length];
        //the preferences are each a family with something that has to be included-ALL, than something that is interchangeable -ONE
        //and something that has to be excluded which is the the third array within the nested array

        for (int i = 0; i < families.length; i++) {
            targetOrder[i] = Family.all(getComponents(i, ALL)).one(getComponents(i, ONE)).exclude(getComponents(i, EXCLUDE)).get();
        }


        return this;

    }
    //creates the components that are found in the families[][][] and get the index of that family

    public Class<BaseComponent>[] getComponents(int i, int t) {
        Class<BaseComponent>[] components = new Class[families[i][t].length];
        for (int j = 0; j < components.length; j++) {
            components[j] = Utils.getComponentClass(families[i][t][j]);
        }
        return components;

    }


    @Override
    public void reset() {
        targetOrder = null;
        overallGoal = null;
        currentTarget = null;
        sightDistance = 0;
        families = null;



    }
}
