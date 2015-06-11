package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by figiel-paul on 05/06/15.
 */
public class BodyUserData {
    private State state;
    private Object objectdata;
    private Vector2 fixtureposition;
    public enum State
    {
        NORMAL,
        DESTROY_JOINTS,
        TO_DESTROY,
        IS_CENTER,
        IS_CENTER_TOUCHED,
        IS_BOULET,
        IS_ALIEN_DEAD,
        IS_ALIEN;
    }

    /**
     * Constructeur.
     * Donne un etat à une fixture 0 = normal ; 1 = on doit detruire les joints de cette fixture ;
     * 2 = on doit detruire cette fixture
     */
    BodyUserData(State state)
    {
        this.state = state;
    }
    public void setState(State s)
    {
        this.state = s;
    }
    public State getState()
    {
        return state;
    }

    public Object getObjectdata() {
        return objectdata;
    }

    public Vector2 getFixtureposition() {
        return fixtureposition;
    }

    public void setFixtureposition(Vector2 fixtureposition) {
        this.fixtureposition = fixtureposition;
    }

    public void setObjectdata(Object objectdata) {
        this.objectdata = objectdata;

    }
}
