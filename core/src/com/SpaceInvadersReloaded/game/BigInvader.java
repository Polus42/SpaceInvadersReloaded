package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 06/06/15.
 */
public class BigInvader implements Invaders {
    private  float[][] emplacements;
    //private float cotecube = 0.32f;
    private float cotecube = 1f;
    private ArrayList<Body> listecube = new ArrayList<Body>();
    private Vector2 center;
    private World world;
    Body centercube;

    BigInvader(World w,float posX,float posY)
    {
        // Initialiser les parties physiques de l'objet
        emplacements = new float[][]
                {
                        {0,0,0,0,0,1,1,1,0,0,0,0,0},
                        {0,0,0,1,1,1,1,1,1,1,0,0,0},
                        {0,0,1,1,1,1,1,1,1,1,1,0,0},
                        {0,1,1,0,1,0,1,0,1,0,1,1,0},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {0,0,1,1,1,0,1,0,1,1,1,0,0},
                        {0,0,0,1,0,0,0,0,0,1,0,0,0},
                };

        // Generation des cube
        //
        for (int x = 0;x<13 ;x++)
        {
            for (int y = 0;y<7 ;y++)
            {
                if (emplacements[y][x]==1)
                {
                    // Cube de base
                    BodyDef squarebodydef =new BodyDef();

                    squarebodydef.type = BodyDef.BodyType.DynamicBody;

                    squarebodydef.position.set(new Vector2(posX + x * cotecube, posY - y * cotecube));

                    Body square = w.createBody(squarebodydef);

                    PolygonShape squareshape = new PolygonShape();

                    squareshape.setAsBox(cotecube/2, cotecube/2);

                    FixtureDef squarefixture = new FixtureDef();
                    squarefixture.shape = squareshape;
                    squarefixture.density = 10f;
                    squarefixture.friction = 10f;
                    squarefixture.restitution = 0.1f; // Make it bounce a little bit
                    squarefixture.filter.maskBits=0x0004|0x0001; // I will collide with... | que des nombres pairs!
                    squarefixture.filter.categoryBits=0x0002; // I am a
                    Fixture f = square.createFixture(squarefixture);
                    squareshape.dispose();
                    square.setUserData(new BodyUserData(BodyUserData.State.NORMAL));
                    listecube.add(square);
                    square.setAngularDamping(100000000);
                    ((BodyUserData) square.getUserData()).setObjectdata(this);
                    // si c'est le cube central
                    if ((x == 6)&&(y == 3))
                    {
                        BodyUserData bd = new BodyUserData(BodyUserData.State.IS_CENTER);
                        bd.setObjectdata(this);
                        square.setUserData(bd);
                        centercube = square;
                        square.setFixedRotation(true);
                    }
                }
            }
        }
        // Definition du centre
        center = new Vector2(posX+5,posY+3);
        // Creation des joints
        for (Body b1 : listecube)
        {
            for (Body b2 : listecube)
            {
                if (b1 != b2)
                {
                    if (b1.getWorldCenter().dst(b2.getWorldCenter())<cotecube*2)
                    {
                        WeldJointDef wjd = new WeldJointDef();
                        wjd.initialize(b1,b2,b1.getWorldCenter());
                        wjd.frequencyHz = 10;
                        wjd.dampingRatio = 1;
                        wjd.collideConnected = false;
                        w.createJoint(wjd);
                    }
                }
            }

        }
        this.world = w;
    }
    @Override
    public void autoMove(CanonMan cm) {
        // Si trop à droite
        if (!listecube.isEmpty()&&centercube!=null)
        {
            if (centercube.getWorldCenter().x>cm.getCenter().x+1)
            {
                centercube.applyForceToCenter(-5000f*cotecube, 0, true);
            }
            // Si trop à gauche
            if (centercube.getWorldCenter().x<cm.getCenter().x-1)
            {
                centercube.applyForceToCenter(5000f*cotecube, 0, true);
            }
            // Si trop en bas;
            if (centercube.getWorldCenter().y<cm.getCenter().y+20)
            {
                float factor = centercube.getPosition().y-(cm.getCenter().y);
                centercube.applyForceToCenter(0, 5000f*cotecube, true);
            }
            // Si trop en haut
            if (centercube.getWorldCenter().y>cm.getCenter().y+20)
            {
                centercube.applyForceToCenter(0, -5000f*cotecube, true);
            }
        }
    }

    @Override
    public void shoot(CanonMan cm) {

    }

    @Override
    public void draw(ShapeRenderer renderer, OrthographicCamera cam) {
        for (Body b : listecube)
        {
            renderer.setProjectionMatrix(cam.combined);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(1, 1, 1, 1);
            renderer.rect(b.getWorldCenter().x-cotecube/2,b.getWorldCenter().y-cotecube/2,cotecube/2,cotecube/2,cotecube,cotecube,1,1, (float) Math.toDegrees(b.getAngle()));
            renderer.end();
        }
    }
    public void clean(Body b)
    {
        listecube.remove(b);
    }
}
