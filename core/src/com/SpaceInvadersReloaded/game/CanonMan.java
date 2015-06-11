package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.sun.javafx.geom.RectangularShape;

import java.util.ArrayList;

/**
 * Created by figiel-paul on 02/06/15.
 */

public class CanonMan {

    private WheelJoint wheeljoint,wheeljoint2,wheeljoint3,wheeljoint4,wheeljoint5,wheeljoint6;
    private Body wheelbody,wheelbody2,wheelbody3,wheelbody4,wheelbody5,centralbody;
    private ArrayList<Body> bulletsent = new ArrayList<Body>();
    private World w;
    private float[] centralshapevertices;
    private float FRICTION =30;
    private float FREQUENCY =7;
    private float DAMPING =0;
    private float SCALE = 1.5f;
    private float DENSITY = 100f;
    private float MAXMOTORTORQUE = 10000f;
    private float ANGULARDAMPING = 10;
    private int LIFE = 1000;
    private int GEAR = 0;

    /**
     * Constructeur.
     * Initialise un personnage joueur CanonMan
     * @param b2w Monde dans lequel il apparait
     * @param posX
     * @param posY
     */
    CanonMan(World b2w,float posX,float posY)
    {
        w = b2w;
        // Initialiser les parties physiques de l'objet
        // Partie Centrale du tank ----------------------
        BodyDef centralbodydef =new BodyDef();

        centralbodydef.type = BodyDef.BodyType.DynamicBody;

        centralbodydef.position.set(new Vector2(posX, posY));

        centralbody = w.createBody(centralbodydef);

        PolygonShape centralshape = new PolygonShape();

        /*float[] centralshapevertices = new float[]
                {
                        14.5f,40,
                        72.4f,40,
                        6.9f,28.3f,
                        89.7f,28.3f,
        };*/
        centralshapevertices = new float[]
                {
                        0.90f*SCALE,2.5f*SCALE,
                        4.52f*SCALE,2.5f*SCALE,
                        0.43f*SCALE,1.77f*SCALE,
                        5.60f*SCALE,1.77f*SCALE,
                };

        centralshape.set(centralshapevertices);

        FixtureDef centralfixturedef = new FixtureDef();
        centralfixturedef.shape = centralshape;
        centralfixturedef.density = DENSITY;
        centralfixturedef.friction = FRICTION;
        centralfixturedef.restitution = 0.1f; // Make it bounce a little bit
        centralbody.createFixture(centralfixturedef);
        centralshape.dispose();
        centralbody.setFixedRotation(false);
        centralbody.setAngularDamping(ANGULARDAMPING);
        centralbody.setSleepingAllowed(false);
        // Une roue -------------------------------------
        BodyDef wheelDef = new BodyDef();

        wheelDef.type = BodyDef.BodyType.DynamicBody;

        wheelDef.position.set(posX+1.68f*SCALE, posY+0.88f*SCALE);

        wheelbody = w.createBody(wheelDef);

        CircleShape wheelcircle = new CircleShape();
        wheelcircle.setRadius(0.624f*SCALE);

        FixtureDef wheelfixtureDef = new FixtureDef();
        wheelfixtureDef.shape = wheelcircle;
        wheelfixtureDef.density = DENSITY;
        wheelfixtureDef.friction = FRICTION;
        wheelfixtureDef.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture = wheelbody.createFixture(wheelfixtureDef);
        wheelcircle.dispose();
        wheelbody.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody.setSleepingAllowed(false);
        // Une roue 2 -------------------------------------
        BodyDef wheelDef2 = new BodyDef();

        wheelDef2.type = BodyDef.BodyType.DynamicBody;

        wheelDef2.position.set(posX+3.2f*SCALE, posY+0.88f*SCALE);

        wheelbody2 = w.createBody(wheelDef2);

        CircleShape wheelcircle2 = new CircleShape();
        wheelcircle2.setRadius(0.624f*SCALE);

        FixtureDef wheelfixtureDef2 = new FixtureDef();
        wheelfixtureDef2.shape = wheelcircle2;
        wheelfixtureDef2.density = DENSITY;
        wheelfixtureDef2.friction = FRICTION;
        wheelfixtureDef2.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture2 = wheelbody2.createFixture(wheelfixtureDef2);
        wheelcircle2.dispose();
        wheelbody2.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody2.setSleepingAllowed(false);
        // Une roue 3 -------------------------------------
        BodyDef wheelDef3 = new BodyDef();

        wheelDef3.type = BodyDef.BodyType.DynamicBody;

        wheelDef3.position.set(posX+4.48f*SCALE, posY+0.88f*SCALE);

        wheelbody3 = w.createBody(wheelDef3);

        CircleShape wheelcircle3 = new CircleShape();
        wheelcircle3.setRadius(0.624f*SCALE);

        FixtureDef wheelfixtureDef3 = new FixtureDef();
        wheelfixtureDef3.shape = wheelcircle3;
        wheelfixtureDef3.density = DENSITY;
        wheelfixtureDef3.friction = FRICTION;
        wheelfixtureDef3.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture3 = wheelbody3.createFixture(wheelfixtureDef3);
        wheelcircle3.dispose();
        wheelbody3.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody3.setSleepingAllowed(false);
        // Petite roue gauche -----------------------------------
        BodyDef wheelDef4 = new BodyDef();

        wheelDef4.type = BodyDef.BodyType.DynamicBody;

        wheelDef4.position.set(posX+0.432f*SCALE, posY+1.12f*SCALE);

        wheelbody4 = w.createBody(wheelDef4);

        CircleShape wheelcircle4 = new CircleShape();
        wheelcircle4.setRadius(0.432f*SCALE);

        FixtureDef wheelfixtureDef4 = new FixtureDef();
        wheelfixtureDef4.shape = wheelcircle4;
        wheelfixtureDef4.density = DENSITY;
        wheelfixtureDef4.friction = FRICTION;
        wheelfixtureDef4.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture4 = wheelbody4.createFixture(wheelfixtureDef4);
        wheelcircle4.dispose();
        wheelbody4.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody4.setSleepingAllowed(false);
        // Petite roue droite -----------------------------------
        BodyDef wheelDef5 = new BodyDef();

        wheelDef5.type = BodyDef.BodyType.DynamicBody;

        wheelDef5.position.set(posX+5.84f*SCALE, posY+1.12f*SCALE);

        wheelbody5 = w.createBody(wheelDef5);

        CircleShape wheelcircle5 = new CircleShape();
        wheelcircle5.setRadius(0.432f*SCALE);

        FixtureDef wheelfixtureDef5 = new FixtureDef();
        wheelfixtureDef5.shape = wheelcircle5;
        wheelfixtureDef5.density = DENSITY;
        wheelfixtureDef5.friction = FRICTION;
        wheelfixtureDef5.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture5 = wheelbody5.createFixture(wheelfixtureDef5);
        wheelcircle5.dispose();
        wheelbody5.setUserData(new BodyUserData(BodyUserData.State.IS_BOULET));
        wheelbody5.setSleepingAllowed(false);
        // Les joints --------------------------------------
        WheelJointDef wheeljointdef = new WheelJointDef();

        wheeljointdef.type = JointDef.JointType.WheelJoint;

        wheeljointdef.initialize(centralbody, wheelbody, new Vector2(wheelbody.getPosition()), new Vector2(0, 1));

        wheeljoint = (WheelJoint)w.createJoint(wheeljointdef);

        wheeljoint.enableMotor(true);
        wheeljoint.setMotorSpeed(0.0f);
        wheeljoint.setSpringDampingRatio(DAMPING);
        wheeljoint.setSpringFrequencyHz(FREQUENCY);
        // -------------------------------------------------
        WheelJointDef wheeljointdef2 = new WheelJointDef();

        wheeljointdef2.type = JointDef.JointType.WheelJoint;

        wheeljointdef2.initialize(centralbody, wheelbody2, new Vector2(wheelbody2.getPosition()), new Vector2(0, 1));

        wheeljoint2 = (WheelJoint)w.createJoint(wheeljointdef2);

        wheeljoint2.enableMotor(true);
        wheeljoint2.setMotorSpeed(0.0f);
        wheeljoint2.setSpringDampingRatio(DAMPING);
        wheeljoint2.setSpringFrequencyHz(FREQUENCY);
        // ------------------------------------------------
        WheelJointDef wheeljointdef3 = new WheelJointDef();

        wheeljointdef3.type = JointDef.JointType.WheelJoint;

        wheeljointdef3.initialize(centralbody, wheelbody3, new Vector2(wheelbody3.getPosition()), new Vector2(0, 1));

        wheeljoint3 = (WheelJoint)w.createJoint(wheeljointdef3);

        wheeljoint3.enableMotor(true);
        wheeljoint3.setMotorSpeed(0.0f);
        wheeljoint3.setSpringDampingRatio(DAMPING);
        wheeljoint3.setSpringFrequencyHz(FREQUENCY);
        // ----------------------------------------------
        WheelJointDef wheeljointdef4 = new WheelJointDef();

        wheeljointdef4.type = JointDef.JointType.WheelJoint;

        wheeljointdef4.initialize(centralbody, wheelbody4, new Vector2(wheelbody4.getPosition()), new Vector2(0, 1));

        wheeljoint4 = (WheelJoint)w.createJoint(wheeljointdef4);

        wheeljoint4.enableMotor(true);
        wheeljoint4.setMotorSpeed(0.0f);
        wheeljoint4.setSpringDampingRatio(DAMPING);
        wheeljoint4.setSpringFrequencyHz(FREQUENCY);
        // -------------------------------------------------
        WheelJointDef wheeljointdef5 = new WheelJointDef();

        wheeljointdef5.type = JointDef.JointType.WheelJoint;

        wheeljointdef5.initialize(centralbody, wheelbody5, new Vector2(wheelbody5.getPosition()), new Vector2(0, 1));

        wheeljoint5 = (WheelJoint)w.createJoint(wheeljointdef5);

        wheeljoint5.enableMotor(true);
        wheeljoint5.setMotorSpeed(0.0f);
        wheeljoint5.setSpringDampingRatio(DAMPING);
        wheeljoint5.setSpringFrequencyHz(FREQUENCY);
        // ----------------------------------------
        wheeljoint.setMaxMotorTorque(13000);
        wheeljoint2.setMaxMotorTorque(13000);
        wheeljoint3.setMaxMotorTorque(13000);
        wheeljoint4.setMaxMotorTorque(13000);
        wheeljoint5.setMaxMotorTorque(13000);

    }

    public void gearUp()
    {
        if (GEAR<6)
        {
            wheeljoint.setMaxMotorTorque(wheeljoint.getMaxMotorTorque()-2000);
            wheeljoint2.setMaxMotorTorque(wheeljoint2.getMaxMotorTorque()-2000);
            wheeljoint3.setMaxMotorTorque(wheeljoint3.getMaxMotorTorque()-2000);
            wheeljoint4.setMaxMotorTorque(wheeljoint4.getMaxMotorTorque()-2000);
            wheeljoint5.setMaxMotorTorque(wheeljoint5.getMaxMotorTorque()-2000);
            wheeljoint.setMotorSpeed(wheeljoint.getMotorSpeed()-10);
            wheeljoint2.setMotorSpeed(wheeljoint2.getMotorSpeed()-10);
            wheeljoint3.setMotorSpeed(wheeljoint3.getMotorSpeed()-10);
            wheeljoint4.setMotorSpeed(wheeljoint4.getMotorSpeed()-10);
            wheeljoint5.setMotorSpeed(wheeljoint5.getMotorSpeed()-10);
            GEAR+=1;
        }
    }
    public void gearDown()
    {
        if (GEAR>-1)
        {
            GEAR-=1;
            wheeljoint.setMaxMotorTorque(wheeljoint.getMaxMotorTorque()+2000);
            wheeljoint2.setMaxMotorTorque(wheeljoint2.getMaxMotorTorque()+2000);
            wheeljoint3.setMaxMotorTorque(wheeljoint3.getMaxMotorTorque()+2000);
            wheeljoint4.setMaxMotorTorque(wheeljoint4.getMaxMotorTorque()+2000);
            wheeljoint5.setMaxMotorTorque(wheeljoint5.getMaxMotorTorque()+2000);

            wheeljoint.setMotorSpeed(wheeljoint.getMotorSpeed()+10);
            wheeljoint2.setMotorSpeed(wheeljoint2.getMotorSpeed()+10);
            wheeljoint3.setMotorSpeed(wheeljoint3.getMotorSpeed()+10);
            wheeljoint4.setMotorSpeed(wheeljoint4.getMotorSpeed()+10);
            wheeljoint5.setMotorSpeed(wheeljoint5.getMotorSpeed()+10);
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
            /*renderer.setProjectionMatrix(cam.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 1, 1, 1);
            renderer.polygon(centralshapevertices);
            renderer.end();*/
    }
    public String essai()
    {
        LIFE -= Math.floor(Math.abs(wheeljoint5.getReactionForce(60).x/10000));
        return String.valueOf(LIFE);
    }

    public int getGEAR() {
        return GEAR;
    }
}
