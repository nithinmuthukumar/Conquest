package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.nithinmuthukumar.conquest.Globals.bodyComp;

public class B2DContactListener implements ContactListener {
    //sets the collided entity of each entity
    @Override
    public void beginContact(Contact contact) {
        Entity a = (Entity) contact.getFixtureA().getUserData();
        Entity b = (Entity) contact.getFixtureB().getUserData();
        bodyComp.get(a).collidedEntities.addLast(b);
        bodyComp.get(b).collidedEntities.addLast(a);
    }

    @Override
    public void endContact(Contact contact) {
        Entity a = (Entity) contact.getFixtureA().getUserData();
        Entity b = (Entity) contact.getFixtureB().getUserData();
        if (bodyComp.has(a)) bodyComp.get(a).collidedEntities.removeValue(b, true);
        if (bodyComp.has(b)) bodyComp.get(b).collidedEntities.removeValue(a, true);



    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
