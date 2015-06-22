package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by figiel-paul on 18/06/15.
 */
public class Room {
    private World world;
    public float HEIGHT=8;
    private float WALLTHICKNESS=1;
    private float posX,posY;
    private float FRICTION = 0.1f;
    public double WIDTH;
    private Body ceiling,leftwall,rightwall,floor;
    private Room rightRoom;
    private Room leftRoom;
    private Room upRoom;
    private Room bottomRoom;
    private Array<Door> doorlist;

    public Room(World world,float posX,float posY,float WIDTH)
    {
        this.world = world;
        this.posX = posX;
        this.posY=posY;
        this.WIDTH = WIDTH;
        this.rightRoom = null;
        this.leftRoom = null;
        this.upRoom = null;
        this.bottomRoom = null;
    }
    public void generate()
    {
        // Creating walls
        genrateFloor((float) WIDTH);
        generateCeiling((float) WIDTH);
        generateLeftWall((float) WIDTH);
        generateRightWall((float) WIDTH);

    }
    private void genrateFloor(float width)
    {
        // Ground
        BodyDef walldef =new BodyDef();

        walldef.type = BodyDef.BodyType.StaticBody;

        walldef.position.set(new Vector2(posX+width/2, posY));

        floor = world.createBody(walldef);

        PolygonShape wallshape = new PolygonShape();

        wallshape.setAsBox((width /2), WALLTHICKNESS /2);

        FixtureDef wallfixture = new FixtureDef();
        wallfixture.shape = wallshape;
        wallfixture.density = 0f;
        wallfixture.friction = FRICTION;
        wallfixture.restitution = 0.1f; // Make it bounce a little bit
        wallfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        wallfixture.filter.categoryBits=0x0006; // I am a
        Fixture f = floor.createFixture(wallfixture);
        wallshape.dispose();
    }
    private void generateLeftWall(float width)
    {
        Door d = new Door(this, Door.Orientation.LEFT);
    }
    private void generateRightWall(float width)
    {
        Door d = new Door(this, Door.Orientation.RIGHT);
    }
    private void generateCeiling(float width)
    {
        // Ground
        BodyDef walldef =new BodyDef();

        walldef.type = BodyDef.BodyType.StaticBody;

        walldef.position.set(new Vector2(posX+width/2, posY+HEIGHT));

        ceiling = world.createBody(walldef);

        PolygonShape wallshape = new PolygonShape();

        wallshape.setAsBox(width/2, WALLTHICKNESS/2);

        FixtureDef wallfixture = new FixtureDef();
        wallfixture.shape = wallshape;
        wallfixture.density = 0f;
        wallfixture.friction = FRICTION;
        wallfixture.restitution = 0.1f; // Make it bounce a little bit
        wallfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        wallfixture.filter.categoryBits=0x0006; // I am a
        Fixture f = ceiling.createFixture(wallfixture);
        wallshape.dispose();
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public Vector2 getCenter()
    {
        return new Vector2((float) (posX+WIDTH/2),posY+HEIGHT/2);
    }
    public float maxX()
    {
        return (float) (getCenter().x+WIDTH/2);
    }
    public float minX()
    {
        return (float) (getCenter().x-WIDTH/2);
    }
    public float maxY()
    {
        return (float) (getCenter().y+HEIGHT/2);
    }
    public float minY()
    {
        return (float) (getCenter().y-HEIGHT/2);
    }
    public boolean equals(Object o)
    {
        if (o instanceof Room)
        {
            return  (this.posY == ((Room) o).posY)&&(this.posX == ((Room) o).posX)&&(this.WIDTH== ((Room) o).WIDTH);
        }
        else return false;
    }

    public World getWorld() {
        return world;
    }

    public float getWALLTHICKNESS() {
        return WALLTHICKNESS;
    }

    public float getHEIGHT() {
        return HEIGHT;
    }

    public void setRightRoom(Room rightRoom) {
        this.rightRoom = rightRoom;
    }

    public void setLeftRoom(Room leftRoom) {
        this.leftRoom = leftRoom;
    }

    public void setUpRoom(Room upRoom) {
        this.upRoom = upRoom;
    }

    public void setBottomRoom(Room bottomRoom) {
        this.bottomRoom = bottomRoom;
    }

    public Room getRightRoom() {
        return rightRoom;
    }

    public Room getLeftRoom() {
        return leftRoom;
    }

    public Room getUpRoom() {
        return upRoom;
    }

    public Room getBottomRoom() {
        return bottomRoom;
    }


}
