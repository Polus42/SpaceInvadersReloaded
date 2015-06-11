package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by figiel-paul on 06/06/15.
 */
public interface Invaders {
    public void autoMove(CanonMan cm);
    public void shoot(CanonMan cm);
    public void draw(ShapeRenderer renderer,OrthographicCamera cam);
    public void clean(Body b);

}
