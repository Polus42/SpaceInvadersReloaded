package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 06/06/15.
 */
public class InvaderSpawner {
    private ArrayList<Invaders> listeinvader;
    private World w;
    public enum Type
    {
        INVADER,
        BIG_INVADER
    }

    InvaderSpawner(World w)
    {
        this.w = w;
        listeinvader = new ArrayList<Invaders>();
    }
    public void spawn(float posX,float posY,Type t)
    {
        if (t==Type.BIG_INVADER)
        {
            listeinvader.add(new BigInvader(w,posX,posY));
        }
        if (t==Type.INVADER)
        {
            listeinvader.add(new Invader(w,posX,posY));
        }
    }
    public void autoMove(CanonMan c)
    {
        for (Invaders i : listeinvader)
        {
            i.autoMove(c);
        }
    }
    public void draw(ShapeRenderer renderer,OrthographicCamera cam)
    {
        for (Invaders i : listeinvader)
        {
            i.draw(renderer,cam);
        }
    }

}
