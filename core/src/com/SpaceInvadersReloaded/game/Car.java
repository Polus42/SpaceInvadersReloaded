package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by figiel-paul on 15/06/15.
 */
public interface Car {
    public void gearUp();
    public void gearDown();
    public World getWorld();
    public int getGEAR();
    public Vector2 getCenter();
}
