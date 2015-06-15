package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.sun.javafx.geom.RectangularShape;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 02/06/15.
 */

public class Truck {

    private WheelJoint wheeljoint,wheeljoint2;
    private WeldJoint frontjoint,backjoint;
    private Body wheelbody,wheelbody2,frontbody,backbody,centralbody;
    private ArrayList<Body> bulletsent = new ArrayList<Body>();
    private World w;
    private float FRICTION =30;
    private float FREQUENCY =7;
    private float DAMPING =0;
    private float SCALE = 30f;
    private float DENSITY = 100f;
    private float MAXMOTORTORQUE = 100000f;
    private float ANGULARDAMPING = 10;
    private float MAXSPEED = 5;
    private int LIFE = 1000;
    private int GEAR = 0;

    /**
     * Constructeur.
     * Initialise un personnage joueur Truck
     * @param b2w Monde dans lequel il apparait
     * @param posX
     * @param posY
     */
    Truck(World b2w, float posX, float posY)
    {
        w = b2w;
        // Loading Shapes
        BodyEditorLoader loader;
        if (Gdx.app.getType()== Application.ApplicationType.Desktop)
        {
            loader = new BodyEditorLoader(Gdx.files.local("fixture/car.json"));
        }
        else {
            loader = new BodyEditorLoader(Gdx.files.internal("fixture/car.json"));
        }

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.density = DENSITY;
        fd.friction = FRICTION;
        fd.restitution = 0.3f;

        // 3. Create a Body, as usual.
        centralbody = w.createBody(bd);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(centralbody, "Chassis", fd, SCALE);
        centralbody.setAngularDamping(ANGULARDAMPING);
        // -------------------------------------------------
        // 1. Create a BodyDef, as usual.
        BodyDef bd2 = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd2 = new FixtureDef();
        fd2.density = DENSITY;
        fd2.friction = FRICTION;
        fd2.restitution = 0.3f;

        // 3. Create a Body, as usual.
        wheelbody = w.createBody(bd);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(wheelbody, "FrontWheel", fd2, SCALE);
        wheelbody.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody.setSleepingAllowed(false);
        //--------------------------------------------------
        // 1. Create a BodyDef, as usual.
        BodyDef bd3 = new BodyDef();
        bd3.position.set(0, 0);
        bd3.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd3 = new FixtureDef();
        fd3.density =DENSITY;
        fd3.friction = FRICTION;
        fd3.restitution = 0.3f;

        // 3. Create a Body, as usual.
        wheelbody2 = w.createBody(bd);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(wheelbody2, "BackWheel", fd3, SCALE);
        wheelbody2.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody2.setSleepingAllowed(false);
        // -------------------------------------------------
        // 1. Create a BodyDef, as usual.
        BodyDef bd4 = new BodyDef();
        bd4.position.set(0, 0);
        bd4.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd4 = new FixtureDef();
        fd4.density =DENSITY;
        fd4.friction = FRICTION;
        fd4.restitution = 0.3f;

        // 3. Create a Body, as usual.
        frontbody = w.createBody(bd4);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(frontbody, "Front", fd4, SCALE);
        frontbody.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        frontbody.setSleepingAllowed(false);
        // -------------------------------------------------
        // 1. Create a BodyDef, as usual.
        BodyDef bd5 = new BodyDef();
        bd5.position.set(0, 0);
        bd5.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd5 = new FixtureDef();
        fd5.density =DENSITY;
        fd5.friction = FRICTION;
        fd5.restitution = 0.3f;

        // 3. Create a Body, as usual.
        backbody = w.createBody(bd5);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(backbody, "Back", fd5, SCALE);
        backbody.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        backbody.setSleepingAllowed(false);
        // Les joints --------------------------------------
        WheelJointDef wheeljointdef = new WheelJointDef();

        wheeljointdef.type = JointDef.JointType.WheelJoint;

        wheeljointdef.initialize(centralbody, wheelbody, new Vector2(wheelbody.getWorldCenter()), new Vector2(0, 1));

        wheeljoint = (WheelJoint)w.createJoint(wheeljointdef);

        wheeljoint.enableMotor(true);
        wheeljoint.setMotorSpeed(0.0f);
        wheeljoint.setSpringDampingRatio(DAMPING);
        wheeljoint.setSpringFrequencyHz(FREQUENCY);
        // -------------------------------------------------
        WheelJointDef wheeljointdef2 = new WheelJointDef();

        wheeljointdef2.type = JointDef.JointType.WheelJoint;

        wheeljointdef2.initialize(centralbody, wheelbody2, new Vector2(wheelbody2.getWorldCenter()), new Vector2(0, 1));

        wheeljoint2 = (WheelJoint)w.createJoint(wheeljointdef2);

        wheeljoint2.enableMotor(true);
        wheeljoint2.setMotorSpeed(0.0f);
        wheeljoint2.setSpringDampingRatio(DAMPING);
        wheeljoint2.setSpringFrequencyHz(FREQUENCY);
        // ----------------------------------------
        WeldJointDef wjd = new WeldJointDef();
        wjd.initialize(centralbody,frontbody,frontbody.getWorldCenter());
        wjd.frequencyHz = 10;
        wjd.dampingRatio = 1;
        wjd.collideConnected = false;
        frontjoint = (WeldJoint) w.createJoint(wjd);
        // ----------------------------------------
        WeldJointDef wjd2 = new WeldJointDef();
        wjd2.initialize(centralbody,backbody,backbody.getWorldCenter());
        wjd2.frequencyHz = 10;
        wjd2.dampingRatio = 1;
        wjd2.collideConnected = false;
        backjoint = (WeldJoint) w.createJoint(wjd2);
        // -----------------------------------------
        wheeljoint.setMaxMotorTorque(MAXMOTORTORQUE);
        wheeljoint2.setMaxMotorTorque(MAXMOTORTORQUE);

    }

    public void gearUp()
    {
        if (GEAR<6)
        {
            wheeljoint.setMaxMotorTorque(wheeljoint.getMaxMotorTorque() - 1/7f*MAXMOTORTORQUE);
            wheeljoint2.setMaxMotorTorque(wheeljoint2.getMaxMotorTorque() - 1/7f * MAXMOTORTORQUE);

            wheeljoint.setMotorSpeed(wheeljoint.getMotorSpeed() - MAXSPEED);
            wheeljoint2.setMotorSpeed(wheeljoint2.getMotorSpeed() - MAXSPEED);

            GEAR+=1;
        }
    }
    public void gearDown()
    {
        if (GEAR>-1)
        {
            GEAR-=1;
            wheeljoint.setMaxMotorTorque(wheeljoint.getMaxMotorTorque() + 1/7f*MAXMOTORTORQUE);
            wheeljoint2.setMaxMotorTorque(wheeljoint2.getMaxMotorTorque() + 1/7f*MAXMOTORTORQUE);


            wheeljoint.setMotorSpeed(wheeljoint.getMotorSpeed()+MAXSPEED);
            wheeljoint2.setMotorSpeed(wheeljoint2.getMotorSpeed()+MAXSPEED);

        }

    }

    /**
     * Permet de tirer un boulet très dense à une forte velocité
     * @param w
     * @param direction Point que le boulet va essayer d'atteindre
     */
    public void shoot(World w,Vector2 direction)
    {
        // Creation du boulet---------------------------
        BodyDef bouletDef = new BodyDef();

        bouletDef.type = BodyDef.BodyType.DynamicBody;

        bouletDef.position.set(centralbody.getWorldCenter().x, centralbody.getWorldCenter().y + 1.248f);

        Body bouletbody = w.createBody(bouletDef);

        CircleShape wheelcircle = new CircleShape();
        wheelcircle.setRadius(0.248f);
        FixtureDef bouletfixtureDef = new FixtureDef();
        bouletfixtureDef.shape = wheelcircle;
        bouletfixtureDef.density = 50f;
        bouletfixtureDef.friction = 10f;
        bouletfixtureDef.restitution = 0f; // Make it bounce a little bit
        bouletfixtureDef.filter.categoryBits=0x0004;
        bouletfixtureDef.filter.maskBits=0x0002|0x0001;
        Fixture wheelfixture = bouletbody.createFixture(bouletfixtureDef);
        wheelcircle.dispose();
        bouletbody.setBullet(true);
        bouletbody.setAngularDamping(100);
        // ------------------------------------------
        Vector2 v = new Vector2(direction.x-bouletbody.getPosition().x,direction.y-bouletbody.getPosition().y);
        bouletbody.setLinearVelocity(v.x*10,v.y*10);
        bouletbody.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        bulletsent.add(bouletbody);
    }

    public ArrayList<Body> getBulletsent() {
        return bulletsent;
    }

    /**
     * Getter
     * @return Centre de la partie centrale du tank
     */
    public Vector2 getCenter()
    {
        return centralbody.getWorldCenter();
    }

    /**
     * Monde dans lequel evolue le tank
     * @return Monde
     */
    public World getW() {
        return w;
    }
    public void draw(PolygonSpriteBatch psb, OrthographicCamera cam)
    {

    }
    public String essai()
    {
        LIFE -= Math.floor(Math.abs(frontjoint.getReactionForce(60).x/20000));
        return String.valueOf(LIFE);
    }

    public int getGEAR() {
        return GEAR;
    }
}
