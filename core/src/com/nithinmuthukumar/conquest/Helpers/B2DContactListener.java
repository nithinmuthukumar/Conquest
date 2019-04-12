package com.nithinmuthukumar.conquest.Helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import static com.nithinmuthukumar.conquest.Helpers.Globals.bodyComp;

public class B2DContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Entity a = (Entity) contact.getFixtureA().getUserData();
        Entity b = (Entity) contact.getFixtureB().getUserData();
        bodyComp.get(a).collidedEntity = b;
        bodyComp.get(b).collidedEntity = a;


    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
