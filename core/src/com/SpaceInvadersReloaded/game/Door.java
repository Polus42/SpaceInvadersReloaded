package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by figiel-paul on 22/06/15.
 */
public class Door {
    public enum Orientation
    {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }
    private Vector2 pos;
    private Orientation orientation;
    private Room room;
    private Body doorbody;
    private World world;

    public Door(Room room,Orientation orientation)
    {
        this.room = room;
        this.orientation = orientation;
        this.world = room.getWorld();
        if (orientation == Orientation.LEFT)
        {
            BodyDef squarebodydef =new BodyDef();

            squarebodydef.type = BodyDef.BodyType.DynamicBody;

            squarebodydef.position.set(new Vector2(room.minX(), room.minY()+room.getHEIGHT()/2-0.1f));

            doorbody = world.createBody(squarebodydef);

            PolygonShape squareshape = new PolygonShape();

            squareshape.setAsBox(room.getWALLTHICKNESS() /2, room.getHEIGHT() /2-room.getWALLTHICKNESS()/2);

            FixtureDef squarefixture = new FixtureDef();
            squarefixture.shape = squareshape;
            squarefixture.density = 30f;
            squarefixture.restitution = 0.1f; // Make it bounce a little bit
            squarefixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
            squarefixture.filter.categoryBits=0x0006; // I am a
            Fixture f = doorbody.createFixture(squarefixture);
            BodyUserData bud1 = new BodyUserData(BodyUserData.State.IS_DOOR);
            bud1.setObjectdata(this);
            doorbody.setUserData(bud1);
            squareshape.dispose();

        }
        if (orientation==Orientation.RIGHT)
        {
            BodyDef squarebodydef =new BodyDef();

            squarebodydef.type = BodyDef.BodyType.DynamicBody;

            squarebodydef.position.set(new Vector2(room.maxX(), room.minY()+room.getHEIGHT()/2-0.1f));

            doorbody = world.createBody(squarebodydef);

            PolygonShape squareshape = new PolygonShape();

            squareshape.setAsBox(room.getWALLTHICKNESS() /2, room.getHEIGHT() /2-room.getWALLTHICKNESS()/2);

            FixtureDef squarefixture = new FixtureDef();
            squarefixture.shape = squareshape;
            squarefixture.density = 30f;
            squarefixture.restitution = 0.1f; // Make it bounce a little bit
            squarefixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
            squarefixture.filter.categoryBits=0x0006; // I am a
            Fixture f = doorbody.createFixture(squarefixture);
            BodyUserData bud1 = new BodyUserData(BodyUserData.State.IS_DOOR);
            bud1.setObjectdata(this);
            doorbody.setUserData(bud1);
            squareshape.dispose();
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }
}
