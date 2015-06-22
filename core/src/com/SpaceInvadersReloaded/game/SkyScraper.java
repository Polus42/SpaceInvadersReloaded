package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 18/06/15.
 */
public class SkyScraper {
    private ArrayList<Room> roomlist;
    private World world;
    private Player player;
    private Room currentRoom;
    private float MAXWIDTH = 30;
    private float MINWIDTH = 5;

    public SkyScraper(World world,float posX,float posY,Player player)
    {
        this.player = player;
        this.world =world;
        this.roomlist = new ArrayList<Room>();
    }
    public void update()
    {
        if (player.getCenter().x>currentRoom.getCenter().x)
        {
            float width = (float) (Math.random()*MAXWIDTH+MINWIDTH);
            if (currentRoom.getRightRoom()==null)
            {Gdx.app.error("","Right");
                Room r = new Room(world,currentRoom.maxX(),currentRoom.minY(),width);
                currentRoom.setRightRoom(r);
                r.setLeftRoom(currentRoom);
                roomlist.add(r);
                r.generate();
            }
        }
        if (player.getCenter().x<currentRoom.getCenter().x)
        {

            float width = (float) (Math.random()*MAXWIDTH+MINWIDTH);
            if (currentRoom.getLeftRoom()==null)
            {Gdx.app.error("","LEft");
                Room r = new Room(world,currentRoom.minX()-width,currentRoom.minY(),width);
                currentRoom.setLeftRoom(r);
                r.setRightRoom(currentRoom);
                roomlist.add(r);
                r.generate();
            }
        }

        if (player.getCenter().y>currentRoom.getCenter().y)
        {
            float width = (float) (Math.random()*MAXWIDTH+MINWIDTH);
            if (currentRoom.getUpRoom()==null)
            {Gdx.app.error("","UP");
                Room r = new Room(world,currentRoom.minX(),currentRoom.maxY(),width);
                currentRoom.setUpRoom(r);
                r.setBottomRoom(currentRoom);
                roomlist.add(r);
                r.generate();
            }
        }
        if (player.getCenter().y<currentRoom.getCenter().y)
        {

            float width = (float) (Math.random()*MAXWIDTH+MINWIDTH);
            if (currentRoom.getBottomRoom()==null)
            {Gdx.app.error("","DOWN");
                Room r = new Room(world,currentRoom.minX(),currentRoom.minY()-currentRoom.HEIGHT,width);
                currentRoom.setBottomRoom(r);
                r.setUpRoom(currentRoom);
                roomlist.add(r);
                r.generate();
            }
        }
        if (player.getCenter().x>currentRoom.maxX())
        {
            currentRoom = currentRoom.getRightRoom();
        }
        if (player.getCenter().x<currentRoom.minX())
        {
            currentRoom = currentRoom.getLeftRoom();
        }
        if (player.getCenter().y>currentRoom.maxY())
        {
            currentRoom = currentRoom.getUpRoom();
        }
        if (player.getCenter().y<currentRoom.minY())
        {
            currentRoom = currentRoom.getBottomRoom();
        }

    }
    public void setCurrentRoom(Room r)
    {
        this.currentRoom = r;
    }
}
