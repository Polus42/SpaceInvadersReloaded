package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by figiel-paul on 04/06/15.
 */
public class GestionContact implements ContactListener {
    private World world;

    /**
     * Initialisation de la gestion des contacts avec un monde physique
     * @param w World
     */
    public void initialize(World w)
    {
        this.world = w;
    }

    /**
     * Methode utilisée à chaque debut de contact
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {
        Object dataA = contact.getFixtureA().getBody().getUserData();
        Object dataB = contact.getFixtureB().getBody().getUserData();
        if ((dataA!=null)&&(dataB!=null))
        {
            if ((((BodyUserData) dataA).getState()== BodyUserData.State.NORMAL)&&(((BodyUserData) dataB).getState()== BodyUserData.State.IS_BOULET))
            {
                // FixtureUserData = 1 : destruction des joints
                ((BodyUserData) dataA).setState(BodyUserData.State.DESTROY_JOINTS);
            }
            if ((((BodyUserData) dataA).getState()== BodyUserData.State.IS_CENTER)&&(((BodyUserData) dataB).getState()== BodyUserData.State.IS_BOULET))
            {
                // FixtureUserData = 1 : destruction des joints
                ((BodyUserData) contact.getFixtureA().getBody().getUserData()).setState(BodyUserData.State.IS_CENTER_TOUCHED);
            }
            if ((((BodyUserData) dataA).getState()== BodyUserData.State.IS_ALIEN)&&(((BodyUserData) dataB).getState()== BodyUserData.State.IS_BOULET))
            {
                ((BodyUserData) dataA).setState(BodyUserData.State.IS_ALIEN_DEAD);
            }
            if ((((BodyUserData) dataA).getState()== BodyUserData.State.IS_ALIEN)&&(((BodyUserData) dataB).getState()== BodyUserData.State.DESTROY_JOINTS))
            {
                ((BodyUserData) dataA).setState(BodyUserData.State.IS_ALIEN_DEAD);
            }
            if ((((BodyUserData) dataA).getState()== BodyUserData.State.IS_DOOR)&&(((BodyUserData) dataB).getState()== BodyUserData.State.IS_PLAYER))
            {
                ((BodyUserData) dataA).setState(BodyUserData.State.TO_DESTROY);
            }


        }
    }

    /**
     * Methode utilisée à chaque fin de contact
     * @param contact
     */
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
