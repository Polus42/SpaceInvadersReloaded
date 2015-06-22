package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by figiel-paul on 09/06/15.
 */
public class Road {
    private World world;
    private ChainShape groundBox;
    private Body groundBody;
    private Fixture f;
    private Vector2[] vertices;
    private Array<Vector2> vector2Stack;
    private int SIZE = 40; //Number of road bits
    private float BITLENGHT = 10;
    private float DISTANCE = 40; // distance of generation
    private float STRENGHT = 10; // strenght of generation
    private Horde h;

    public Road(World world)
    {
        h = new Horde(world);
        this.world=world;
        vector2Stack = new Array<Vector2>();
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        groundBodyDef.position.set(new Vector2(0, 0));

        groundBody = world.createBody(groundBodyDef);

        groundBox = new ChainShape();
        vertices = new Vector2[SIZE];
        for (int i1 =0;i1<SIZE;i1++)
        {
            vertices[i1] = new Vector2(BITLENGHT*i1-40,0);
        }
        for (int i =0;i<vertices.length;i++)
        {
            vector2Stack.add(vertices[i]);
        }

        groundBox.createChain(vertices);

        f = groundBody.createFixture(groundBox, 0.0f);
    }

    /**
     * Create new road bits
     */
    public void add(Vector2 vector2)
    {
        vertices = new Vector2[SIZE];
        vector2Stack.removeIndex(0);
        vector2Stack.add(vector2);

        for (int i = 0;i<vector2Stack.size;i++)
        {
            vertices[i] = vector2Stack.get(i);
        }
        groundBox = new ChainShape();
        groundBox.createChain(vertices);
        groundBody.destroyFixture(f);
        f = groundBody.createFixture(groundBox, 0.0f);

    }
    public void createRoad(Vector2 player)
    {
        h.autoMove(player);
        if (vector2Stack.peek().x-player.x<DISTANCE)
        {
            add(new Vector2(vector2Stack.peek().x + BITLENGHT, generator(player.x)*STRENGHT ));
            float random = (float) Math.floor(Math.random()*6);
            if (random == 5)
            {
                h.spawn(vector2Stack.peek().x,generator(player.x)*STRENGHT+5);
            }

        }
    }
    private float generator(float playerx)
    {
        return (float) (Math.cos(playerx/100*(Math.random()/100+1)));
    }

}
