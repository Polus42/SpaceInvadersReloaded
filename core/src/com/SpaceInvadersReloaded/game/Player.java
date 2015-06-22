package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;

import box2dLight.RayHandler;

/**
 * Created by figiel-paul on 15/06/15.
 */
public class Player {
    private Car car;
    private int LIFE;
    private int HUNGER;
    private World world;
    private Body playerbody,playerarm,playerhead;
    private float FRICTION = 10;
    private float LARGEUR =1.3f;
    private float HAUTEUR = 3;
    private RevoluteJoint armjoint;
    private PolygonSprite polygonSprite;
    private PolygonSpriteBatch polygonSpriteBatch;
    private Array<Body> bodylist;


    Player(World world,int LIFE,float posX,float posY)
    {
        this.LIFE = LIFE;
        this.world = world;
        this.car = null;
        this.world = world;
        // Corps
        BodyDef squarebodydef =new BodyDef();

        squarebodydef.type = BodyDef.BodyType.DynamicBody;

        squarebodydef.position.set(new Vector2(posX, posY));

        playerbody = world.createBody(squarebodydef);

        PolygonShape squareshape = new PolygonShape();

        squareshape.setAsBox(LARGEUR /2, HAUTEUR /2);

        FixtureDef squarefixture = new FixtureDef();
        squarefixture.shape = squareshape;
        squarefixture.density = 30f;
        squarefixture.friction = FRICTION;
        squarefixture.restitution = 0.1f; // Make it bounce a little bit
        squarefixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        squarefixture.filter.categoryBits=0x0006; // I am a
        Fixture f = playerbody.createFixture(squarefixture);
        BodyUserData bud1 = new BodyUserData(BodyUserData.State.IS_PLAYER);
        bud1.setObjectdata(this);
        playerbody.setUserData(bud1);
        squareshape.dispose();
        playerbody.setFixedRotation(true);

        // Bras
        BodyDef armbodydef =new BodyDef();

        armbodydef.type = BodyDef.BodyType.DynamicBody;

        armbodydef.position.set(new Vector2(posX+ LARGEUR /2, posY+ HAUTEUR /2));

        playerarm = world.createBody(armbodydef);

        PolygonShape alienshape = new PolygonShape();

        alienshape.setAsBox(HAUTEUR /3, LARGEUR /3);

        FixtureDef armfixture = new FixtureDef();
        armfixture.shape = alienshape;
        armfixture.density = 10f;
        armfixture.friction = FRICTION;
        armfixture.restitution = 0.1f; // Make it bounce a little bit
        armfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        armfixture.filter.categoryBits=0x0006; // I am a
        playerarm.createFixture(armfixture);
        BodyUserData bud2 = new BodyUserData(BodyUserData.State.IS_PLAYER);
        bud2.setObjectdata(this);
        playerarm.setUserData(bud2);
        alienshape.dispose();

        // Tete
        BodyDef headbodydef =new BodyDef();

        headbodydef.type = BodyDef.BodyType.DynamicBody;

        headbodydef.position.set(new Vector2(posX, posY+ HAUTEUR /2+ LARGEUR));

        playerhead = world.createBody(headbodydef);

        CircleShape headshape = new CircleShape();

        headshape.setRadius(LARGEUR /2);

        FixtureDef headfixture = new FixtureDef();
        headfixture.shape = headshape;
        headfixture.density = 10f;
        headfixture.friction = FRICTION;
        headfixture.restitution = 0.1f; // Make it bounce a little bit
        headfixture.filter.maskBits=0x0004|0x0001|0x0006; // I will collide with... | que des nombres pairs!
        headfixture.filter.categoryBits=0x0006; // I am a
        playerhead.createFixture(headfixture);
        playerhead.setAngularDamping(10);
        BodyUserData bud3 = new BodyUserData(BodyUserData.State.IS_PLAYER);
        bud3.setObjectdata(this);
        playerhead.setUserData(bud3);
        headshape.dispose();

        // Attache le bras au corps
        RevoluteJointDef rjd = new RevoluteJointDef();
        rjd.initialize(playerarm, playerbody, new Vector2(playerarm.getWorldCenter().x-1f,playerarm.getWorldCenter().y));
        rjd.enableMotor = true;
        rjd.maxMotorTorque = 500;
        rjd.collideConnected = false;
        armjoint = (RevoluteJoint) world.createJoint(rjd);
        // Attache tete au corps
        WeldJointDef wjd2 = new WeldJointDef();
        wjd2.initialize(playerhead, playerbody, playerbody.getWorldCenter());
        wjd2.frequencyHz = 0;
        wjd2.dampingRatio = 1000;
        wjd2.collideConnected = false;
        world.createJoint(wjd2);
        //
        bodylist = new Array<Body>();
        bodylist.add(playerarm);
        bodylist.add(playerbody);
        bodylist.add(playerhead);

    }
    public void enterCar(Car car)
    {
        this.car = car;
    }
    public void leaveCar()
    {
        this.car = null;
    }
    public void moveRight()
    {
        if (car!=null)
        {
            car.gearUp();
        }
        else
        {
            playerbody.setLinearVelocity(10, playerbody.getLinearVelocity().y);
        }
    }
    public void moveLeft()
    {
        if (car!=null)
        {
            car.gearDown();
        }
        else
        {
            playerbody.setLinearVelocity(-10, playerbody.getLinearVelocity().y);
        }
    }
    public void jump()
    {
        playerbody.applyForceToCenter(0,10000,true);
    }
    public Vector2 getCenter()
    {
        return playerbody.getWorldCenter();
    }
    public void draw(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        drawHead(cam,shapeRenderer);
        drawArm(cam, shapeRenderer);
        drawBody(cam,shapeRenderer);
    }
    public void drawHead(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.circle(playerhead.getWorldCenter().x, playerhead.getWorldCenter().y, playerhead.getFixtureList().first().getShape().getRadius(), 30);
        shapeRenderer.end();
    }
    public void drawArm(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(playerarm.getWorldCenter().x - HAUTEUR / 3 * 2 / 2, playerarm.getWorldCenter().y - LARGEUR / 3 * 2 / 2, HAUTEUR / 3 * 2 / 2, LARGEUR / 3 * 2 / 2, HAUTEUR / 3 * 2, LARGEUR / 3 * 2, 1, 1, (float) Math.toDegrees(playerarm.getAngle()));
        shapeRenderer.end();
    }
    public void drawBody(OrthographicCamera cam,ShapeRenderer shapeRenderer)
    {
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(playerbody.getWorldCenter().x - LARGEUR/2, playerbody.getWorldCenter().y - HAUTEUR/2, LARGEUR/2, HAUTEUR/2, LARGEUR , HAUTEUR, 1, 1, (float) Math.toDegrees(playerbody.getAngle()));
        shapeRenderer.end();
    }
    public void pointToward(Vector3 direction)
    {
        Vector2 v = new Vector2(direction.x,direction.y);

        float xDiff = v.x-playerarm.getWorldCenter().x;
        float yDiff = v.y-playerarm.getWorldCenter().y;
        float angleDiff = (float) Math.atan2(xDiff , yDiff);
        float angleDiff2 = (float) (angleDiff-(-(playerarm.getAngle()-Math.PI/2)) );


        //Gdx.app.error("", String.valueOf((Math.toDegrees(-(playerarm.getAngle()-Math.PI/2)))));
        //Gdx.app.error("", String.valueOf((Math.toDegrees(angleDiff2))));

        armjoint.setMotorSpeed(angleDiff2*10);


    }
}
