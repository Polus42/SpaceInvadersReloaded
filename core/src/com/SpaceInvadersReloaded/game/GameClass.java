package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.ApplicationAdapter;


public class GameClass extends ApplicationAdapter {
    public enum State
    {
        MENUSTATE,
        PLAYINGSTATE
    }
    public State state;

    GameState menuState;
    GameState playingState;

    // 1 mètre = 128 pixels
    public static final float PIXEL_TO_METER = 16f;

    @Override
    public void create() {
        state = State.MENUSTATE;
        menuState = new MenuState(this);
        playingState = new PlayingState(this);
        menuState.create();
        playingState.create();
    }

    @Override
    public void render() {

        if (state == State.MENUSTATE) {
            menuState.render();
        }
        if (state == State.PLAYINGSTATE)
        {
            playingState.render();
        }

    }
    @Override
    public void resize(int width, int height) {
        if (state == State.MENUSTATE) {
            menuState.resize(width, height);
        }
        if (state == State.PLAYINGSTATE)
        {
            playingState.resize(width, height);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}