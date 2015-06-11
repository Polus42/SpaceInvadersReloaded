package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 04/06/15.
 */
public class Invader implements Invaders {

    // Emplacement de chaque square
    private float[][] emplacements = new float[11][8];
    private float cotecube = 0.32f;
    private Array<Fixture> listecube = new Array<Fixture>();
    private  World world;
    Body invaderbody;
    float posX,posY;

    /**
     * Constructeur du personnage invader avec creation de ces objets physique
     * @param w Monde dans lequel il apparait
     * @param posX
     * @param posY
     */
    Invader(World w,float posX,float posY)
    {
        this.posX = posX;
        this.posY = posY;
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
        // Creation du corps solide

        BodyDef bodydef =new BodyDef();

        bodydef.type = BodyDef.BodyType.DynamicBody;

        bodydef.position.set(new Vector2(posX,posY));

        invaderbody = w.createBody(bodydef);
       for (int x = 0;x<13 ;x++)
        {
            for (int y = 0;y<7 ;y++)
            {
                if (emplacements[y][x]==1)
                {
                    // Cube de base


                    PolygonShape squareshape = new PolygonShape();

                    squareshape.setAsBox(cotecube/2, cotecube/2,new Vector2(posX+x*cotecube,posY-y*cotecube),0);

                    FixtureDef squarefixture = new FixtureDef();
                    squarefixture.shape = squareshape;
                    squarefixture.density = 10f;
                    squarefixture.friction = 10f;
                    squarefixture.restitution = 0.1f; // Make it bounce a little bit
                    squarefixture.filter.maskBits=0x0004|0x0001; // I will collide with... | que des nombres pairs!
                    squarefixture.filter.categoryBits=0x0002; // I am a
                    Fixture f = invaderbody.createFixture(squarefixture);
                    squareshape.dispose();
                    f.setUserData(new BodyUserData(BodyUserData.State.NORMAL));
                    invaderbody.setFixedRotation(true);
                    listecube.add(f);
                }
            }
        }
        invaderbody.setUserData(new BodyUserData(BodyUserData.State.NORMAL));
        ((BodyUserData) invaderbody.getUserData()).setObjectdata(this);
        this.world = w;
        // Creation du corps destructible

    }

    /**
     * Gestion des deplacements de l'invader en fonction de la position d'un autre individu
     * @param cm
     */
    public void autoMove(CanonMan cm)
    {
        // Si trop à droite
            if (invaderbody.getWorldCenter().x>cm.getCenter().x+1)
            {
                invaderbody.applyForceToCenter(-50f*invaderbody.getMass(), 0, true);
            }
            // Si trop à gauche
            if (invaderbody.getWorldCenter().x<cm.getCenter().x-1)
            {
                invaderbody.applyForceToCenter(50f*invaderbody.getMass(), 0, true);
            }
            // Si trop en bas;
            if (invaderbody.getWorldCenter().y<cm.getCenter().y+20)
            {
                float factor = invaderbody.getPosition().y-(cm.getCenter().y);
                invaderbody.applyForceToCenter(0, 100*invaderbody.getMass(), true);
            }
            // Si trop en haut
            if (invaderbody.getWorldCenter().y>cm.getCenter().y+5)
            {
                invaderbody.applyForceToCenter(0, -50f*invaderbody.getMass(), true);
            }
    }

    /**
     * Tire !
     */
    public void shoot(CanonMan cm)
    {

    }

    /**
     * Getter
     * @return le monde dans lequel vit l'invader
     */
    public World getWorld()
    {
        return this.world;
    }


    /**
     * Dessine un bonhomme vert
     * @param renderer
     * @param cam
     */
    public void draw(ShapeRenderer renderer,OrthographicCamera cam)
    {
        for (Fixture f : listecube)
        {
            /*renderer.setProjectionMatrix(cam.combined);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(0, 1, 0, 1);
            renderer.rect(f.getWorldCenter().x-cotecube/2,b.getWorldCenter().y-cotecube/2,0,0,cotecube,cotecube,1,1, b.getAngle());
            renderer.end();*/
        }
    }

    @Override
    public void clean(Body b) {

    }

    public Vector2 getFixturePosition(Fixture f)
    {
        Shape fixtureshape = f.getShape();
        if (fixtureshape.getType()== Shape.Type.Circle)
        {
            return ((CircleShape) fixtureshape).getPosition();
        }
        else if (fixtureshape.getType() == Shape.Type.Polygon) {
            Vector2 v = new Vector2();
            ((PolygonShape) fixtureshape).getVertex(0,v);
            return v;
        }
        return Vector2.Zero;
    }
    public void clean()
    {
        //centercube = null;
        //listecube.clear();
    }

    /**
     * Transforme l'ensemble de fixture en bodies
     */
    public void explode()
    {
        for (Fixture f : listecube)
        {
            invaderbody.destroyFixture(f);
        }
        // Initialiser les parties physiques de l'objet

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

                    squarebodydef.position.set(new Vector2(posX + invaderbody.getPosition().x + x * cotecube, posY + invaderbody.getPosition().y - y * cotecube));

                    Body square = world.createBody(squarebodydef);

                    PolygonShape squareshape = new PolygonShape();

                    squareshape.setAsBox(cotecube/2, cotecube/2);

                    FixtureDef squarefixture = new FixtureDef();
                    squarefixture.shape = squareshape;
                    squarefixture.density = 10f;
                    squarefixture.friction = 10f;
                    squarefixture.restitution = 0.1f; // Make it bounce a little bit
                    squarefixture.filter.maskBits=0x0004|0x0001|0x0002; // I will collide with... | que des nombres pairs!
                    squarefixture.filter.categoryBits=0x0002; // I am a
                    Fixture f = square.createFixture(squarefixture);
                    squareshape.dispose();
                    square.setUserData(new BodyUserData(BodyUserData.State.NORMAL));
                    square.setAngularVelocity(10);
                    square.setLinearVelocity(invaderbody.getLinearVelocity().x,invaderbody.getLinearVelocity().y-100);
                    invaderbody.setUserData(new BodyUserData(BodyUserData.State.TO_DESTROY));
                }
            }
        }
        // Definition du centre
        /*center = new Vector2(posX+5,posY+3);
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
                        wjd.frequencyHz = 0;
                        wjd.dampingRatio = 1;
                        wjd.referenceAngle=0;
                        wjd.collideConnected = false;
                        w.createJoint(wjd);
                    }
                }
            }

        }*/
    }
}
