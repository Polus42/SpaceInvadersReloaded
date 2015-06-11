package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 07/06/15.
 */
public class Alien {
    private World world;
    private float HAUTEUR = 2;
    private float LARGEUR = 0.6f;
    private Body alienbody;
    private Body alienarm;
    private Body alienhead;
    private ArrayList<Body> bodylist = new ArrayList<Body>();
    // PolygonSpriteBatch necessaire au dessin du personnage
    private PolygonSpriteBatch polyBatch;
    private PolygonRegion polyReg;
    private boolean DEAD;

    Alien(World world,float posX,float posY)
    {
        this.world = world;
        DEAD = false;
        // Corps
        BodyDef squarebodydef =new BodyDef();

        squarebodydef.type = BodyDef.BodyType.DynamicBody;

        squarebodydef.position.set(new Vector2(posX, posY));

        alienbody = world.createBody(squarebodydef);

        PolygonShape squareshape = new PolygonShape();

        squareshape.setAsBox(LARGEUR /2, HAUTEUR /2);

        FixtureDef squarefixture = new FixtureDef();
        squarefixture.shape = squareshape;
        squarefixture.density = 30f;
        squarefixture.friction = 0.5f;
        squarefixture.restitution = 0.1f; // Make it bounce a little bit
        squarefixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        squarefixture.filter.categoryBits=0x0006; // I am a
        Fixture f = alienbody.createFixture(squarefixture);
        BodyUserData bud1 = new BodyUserData(BodyUserData.State.IS_ALIEN);
        bud1.setObjectdata(this);
        alienbody.setUserData(bud1);
        squareshape.dispose();

        // Bras
        BodyDef armbodydef =new BodyDef();

        armbodydef.type = BodyDef.BodyType.DynamicBody;

        armbodydef.position.set(new Vector2(posX+ LARGEUR /2, posY+ HAUTEUR /2));

        alienarm = world.createBody(armbodydef);

        PolygonShape alienshape = new PolygonShape();

        alienshape.setAsBox(HAUTEUR /3, LARGEUR /3);

        FixtureDef armfixture = new FixtureDef();
        armfixture.shape = alienshape;
        armfixture.density = 10f;
        armfixture.friction = 0.5f;
        armfixture.restitution = 0.1f; // Make it bounce a little bit
        armfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        armfixture.filter.categoryBits=0x0006; // I am a
        alienarm.createFixture(armfixture);
        BodyUserData bud2 = new BodyUserData(BodyUserData.State.IS_ALIEN);
        bud2.setObjectdata(this);
        alienarm.setUserData(bud2);
        alienshape.dispose();

        // Tete
        BodyDef headbodydef =new BodyDef();

        headbodydef.type = BodyDef.BodyType.DynamicBody;

        headbodydef.position.set(new Vector2(posX, posY+ HAUTEUR /2+ LARGEUR));

        alienhead = world.createBody(headbodydef);

        CircleShape headshape = new CircleShape();

        headshape.setRadius(LARGEUR /2);

        FixtureDef headfixture = new FixtureDef();
        headfixture.shape = headshape;
        headfixture.density = 10f;
        headfixture.friction = 0.5f;
        headfixture.restitution = 0.1f; // Make it bounce a little bit
        headfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        headfixture.filter.categoryBits=0x0006; // I am a
        alienhead.createFixture(headfixture);
        alienhead.setAngularDamping(10);
        BodyUserData bud3 = new BodyUserData(BodyUserData.State.IS_ALIEN);
        bud3.setObjectdata(this);
        alienhead.setUserData(bud3);
        headshape.dispose();

        // Attache le bras au corps
        WeldJointDef wjd = new WeldJointDef();
        wjd.initialize(alienarm, alienbody, alienbody.getWorldCenter());
        wjd.frequencyHz = 5;
        wjd.dampingRatio = 1;
        wjd.collideConnected = false;
        world.createJoint(wjd);
        // Attache tete au corps
        WeldJointDef wjd2 = new WeldJointDef();
        wjd2.initialize(alienhead, alienbody, alienbody.getWorldCenter());
        wjd2.frequencyHz = 10;
        wjd2.dampingRatio = 15;
        wjd2.collideConnected = false;
        world.createJoint(wjd2);
        // Add bodies to bodylist
        bodylist.add(alienarm);
        bodylist.add(alienbody);
        bodylist.add(alienhead);
        // Initialisation de l'affichage
        initializeDrawing();
    }
    private void lookRight()
    {
        //alienarm.setTransform(alienbody.getPosition().x-LARGEUR /2,alienbody.getPosition().y+ HAUTEUR /2,0);
    }
    private void lookLeft()
    {
        //alienarm.setTransform(-0.3f,1,0);
    }

    public void autoMove(Vector2 position)
    {
        if (!DEAD)
        {
            if (position.x<alienbody.getWorldCenter().x)
            {
                lookLeft();
                alienbody.setLinearVelocity(-0.5f,0);
            }
            else
            {
                lookRight();
                alienbody.setLinearVelocity(0.5f,0);
            }
        }
    }
    private void initializeDrawing()
    {
        polyBatch = new PolygonSpriteBatch(); // To assign at the beginning
        Texture textureSolid;

        // Creating the color filling (but textures would work the same way)
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0f,0f,255f,0.5f); // DE is red, AD is green and BE is blue.
        pix.fill();
        textureSolid = new Texture(pix);
        polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                new float[] {      // Four vertices
                        0, 0,            // Vertex 0         3--2
                        LARGEUR, 0,          // Vertex 1         | /|
                        LARGEUR, HAUTEUR,        // Vertex 2         |/ |
                        0, HAUTEUR           // Vertex 3         0--1
                }, new short[] {
                0, 1, 2,         // Two triangles using vertex indices.
                0, 2, 3          // Take care of the counter-clockwise direction.
        });
        polyBatch = new PolygonSpriteBatch();
    }
    public void draw(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        for (Body b : bodylist)
        {
            shapeRenderer.setProjectionMatrix(cam.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 1);
            shapeRenderer.rect(b.getWorldCenter().x,b.getWorldCenter().y,0,0,0,0,1,1, (float) Math.toDegrees(b.getAngle()));
            shapeRenderer.end();
        }
    }

    /**
     * Appelé lors de la mort de l'alien
     * @param b
     */
    public void clean (Body b)
    {
        bodylist.remove(b);
        DEAD = true;
    }

}
