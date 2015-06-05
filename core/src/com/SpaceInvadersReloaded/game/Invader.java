package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 04/06/15.
 */
public class Invader {

    // Emplacement de chaque square
    private float[][] emplacements = new float[11][8];
    private float cotecube = 0.04f;
    private ArrayList<Body> listecube = new ArrayList<Body>();
    private Vector2 center;
    private  World world;

    Invader(World w,float posX,float posY)
    {
        // Initialiser les parties physiques de l'objet
        emplacements[0][0] = 0;
        emplacements[1][0] = 0;
        emplacements[2][0] = 0;
        emplacements[3][0] = 1;
        emplacements[4][0] = 1;
        emplacements[5][0] = 0;
        emplacements[6][0] = 1;
        emplacements[7][0] = 1;
        emplacements[8][0] = 0;
        emplacements[9][0] = 0;
        emplacements[10][1] = 0;

        emplacements[0][1] = 1;
        emplacements[1][1] = 0;
        emplacements[2][1] = 1;
        emplacements[3][1] = 0;
        emplacements[4][1] = 0;
        emplacements[5][1] = 0;
        emplacements[6][1] = 0;
        emplacements[7][1] = 0;
        emplacements[8][1] = 1;
        emplacements[9][1] = 0;
        emplacements[10][1] = 1;

        emplacements[0][2] = 1;
        emplacements[1][2] = 0;
        emplacements[2][2] = 1;
        emplacements[3][2] = 1;
        emplacements[4][2] = 1;
        emplacements[5][2] = 1;
        emplacements[6][2] = 1;
        emplacements[7][2] = 1;
        emplacements[8][2] = 1;
        emplacements[9][2] = 0;
        emplacements[10][2] = 1;

        emplacements[0][3] = 1;
        emplacements[1][3] = 1;
        emplacements[2][3] = 1;
        emplacements[3][3] = 1;
        emplacements[4][3] = 1;
        emplacements[5][3] = 1;
        emplacements[6][3] = 1;
        emplacements[7][3] = 1;
        emplacements[8][3] = 1;
        emplacements[9][3] = 1;
        emplacements[10][3] = 1;

        emplacements[0][4] = 0;
        emplacements[1][4] = 1;
        emplacements[2][4] = 1;
        emplacements[3][4] = 0;
        emplacements[4][4] = 1;
        emplacements[5][4] = 1;
        emplacements[6][4] = 1;
        emplacements[7][4] = 0;
        emplacements[8][4] = 1;
        emplacements[9][4] = 1;
        emplacements[10][4] = 0;

        emplacements[0][5] = 0;
        emplacements[1][5] = 0;
        emplacements[2][5] = 1;
        emplacements[3][5] = 1;
        emplacements[4][5] = 1;
        emplacements[5][5] = 1;
        emplacements[6][5] = 1;
        emplacements[7][5] = 1;
        emplacements[8][5] = 1;
        emplacements[9][5] = 0;
        emplacements[10][5] = 0;

        emplacements[0][6] = 0;
        emplacements[1][6] = 0;
        emplacements[2][6] = 0;
        emplacements[3][6] = 1;
        emplacements[4][6] = 0;
        emplacements[5][6] = 0;
        emplacements[6][6] = 0;
        emplacements[7][6] = 1;
        emplacements[8][6] = 0;
        emplacements[9][6] = 0;
        emplacements[10][6] = 0;

        emplacements[0][7] = 0;
        emplacements[1][7] = 0;
        emplacements[2][7] = 1;
        emplacements[3][7] = 0;
        emplacements[4][7] = 0;
        emplacements[5][7] = 0;
        emplacements[6][7] = 0;
        emplacements[7][7] = 0;
        emplacements[8][7] = 1;
        emplacements[9][7] = 0;
        emplacements[10][7] = 0;
        // Weld joint----------------------------------------
        /*WeldJointDef wjd = new WeldJointDef();
        wjd.initialize(square,square2,new Vector2(posX,posY));
        w.createJoint(wjd);*/

        // Generation des cubes
        for (int x = 0;x<11 ;x++)
        {
            for (int y = 0;y<8 ;y++)
            {
                if (emplacements[x][y]==1)
                {
                    // Cube de base
                    BodyDef squarebodydef =new BodyDef();

                    squarebodydef.type = BodyDef.BodyType.DynamicBody;

                    squarebodydef.position.set(new Vector2(posX + x * cotecube, posY + y * cotecube));

                    Body square = w.createBody(squarebodydef);

                    PolygonShape squareshape = new PolygonShape();

                    squareshape.setAsBox(cotecube/2, cotecube/2);

                    FixtureDef squarefixture = new FixtureDef();
                    squarefixture.shape = squareshape;
                    squarefixture.density = 10f;
                    squarefixture.friction = 10f;
                    squarefixture.restitution = 0.1f; // Make it bounce a little bit
                    squarefixture.filter.maskBits=1;
                    squarefixture.filter.categoryBits=1;
                    square.createFixture(squarefixture);
                    square.setUserData(this);
                    listecube.add(square);
                }
            }
        }
        // Creation des joints
        for (Body b1 : listecube)
        {
            for (Body b2 : listecube)
            {
                if (b1 != b2)
                {
                    WeldJointDef wjd = new WeldJointDef();
                    wjd.initialize(b1,b2,new Vector2(posX,posY));
                    wjd.collideConnected =false;
                    wjd.frequencyHz = 0;
                    wjd.dampingRatio = 10;
                    w.createJoint(wjd);
                }
            }
        }
        this.world = listecube.get(0).getWorld();
    }
    public void propulseursIA(CanonMan cm)
    {
        // Si trop à droite
        if (!listecube.isEmpty())
        {
            if (listecube.get(0).getPosition().x>cm.getCenter().x+1)
            {
                for (Body b :listecube) {
                    b.applyForceToCenter(-0.05f, 0, true);
                }
            }
            // Si trop à gauche
            if (listecube.get(0).getPosition().x<cm.getCenter().x-1)
            {
                for (Body b :listecube) {
                    b.applyForceToCenter(0.05f, 0, true);
                }
            }
            // Si trop en bas;
            if (listecube.get(0).getPosition().y<cm.getCenter().y+5)
            {
                float factor = listecube.get(0).getPosition().y-(cm.getCenter().y);
                for (Body b :listecube) {
                    b.applyForceToCenter(0, 1/(8f*factor), true);
                }
            }
            // Si trop en haut
            if (listecube.get(0).getPosition().y>cm.getCenter().y+5)
            {
                for (Body b :listecube) {
                    b.applyForceToCenter(0, -0.05f, true);
                }
            }
        }

        /*if ((Math.toDegrees((double) listecube.get(0).getAngle()))>25)
        {
            for (Body b :listecube)
            {
                b.applyAngularImpulse(-100,true);
            }
        }
        if ((Math.toDegrees((double) listecube.get(0).getAngle()))<-25)
        {
            for (Body b :listecube)
            {
                b.applyAngularImpulse(100, true);
            }
        }*/
    }
    public void destroy()
    {
        for (Body b : listecube)
        {
            if (b.getFixtureList().first().getUserData()!=null)
            {Gdx.app.error("",",pog,osf");
                if (b.getFixtureList().first().getUserData().equals(new String("ToDestroy")))
                {
                    Gdx.app.error("",",pog,osf");
                    world.destroyJoint(b.getFixtureList().first().getBody().getJointList().first().joint);
                }
            }
        }
        listecube.clear();
    }
    public World getWorld()
    {
        return this.world;
    }
}
