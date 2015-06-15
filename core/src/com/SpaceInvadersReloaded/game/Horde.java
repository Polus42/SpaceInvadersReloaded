package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 08/06/15.
 */
public class Horde {
    private ArrayList<Zombie> alienlist;
    private World world;
    Horde(World box2Dworld)
    {
        this.world  = box2Dworld;
        alienlist = new ArrayList<Zombie>();
    }
    public void spawn(float x,float y)
    {
        alienlist.add(new Zombie(world,x,y));
    }
    public void draw(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        for (Zombie a : alienlist)
        {
            a.draw(cam,shapeRenderer);
        }
    }
    public void autoMove(Vector2 vector2)
    {
        for (Zombie a : alienlist)
        {
            a.autoMove(vector2);
        }
    }
}
