package com.SpaceInvadersReloaded.game;

/**
 * Created by figiel-paul on 15/06/15.
 */
public interface GameState {
    public void create();
    public void render();
    public void resize(int width, int height);
    public void pause();
    public void resume();
    public void dispose();
}
