package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by figiel-paul on 12/06/15.
 */
public abstract class RoadEvent {
private World world;
    public RoadEvent(World world)
    {
        this.world = world;
    }

    public abstract void createEvent(World world);
}
