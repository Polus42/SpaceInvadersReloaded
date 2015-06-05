package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Gdx;
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

    CanonMan(World b2w,float posX,float posY)
    {
        w = b2w;
        float damping = 20;
        float frequency = 15;
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
        float[] centralshapevertices = new float[]
                {
                        0.11f,0.31f,
                        0.56f,0.31f,
                        0.054f,0.22f,
                        0.70f,0.22f,
                };

        centralshape.set(centralshapevertices);

        FixtureDef centralfixturedef = new FixtureDef();
        centralfixturedef.shape = centralshape;
        centralfixturedef.density = 20f;
        centralfixturedef.friction = 10f;
        centralfixturedef.restitution = 0.1f; // Make it bounce a little bit
        centralbody.createFixture(centralfixturedef);
        centralbody.setFixedRotation(false);
        // Une roue -------------------------------------
        BodyDef wheelDef = new BodyDef();

        wheelDef.type = BodyDef.BodyType.DynamicBody;

        wheelDef.position.set(posX+0.21f, posY+0.11f);

        wheelbody = w.createBody(wheelDef);

        CircleShape wheelcircle = new CircleShape();
        wheelcircle.setRadius(0.078f);

        FixtureDef wheelfixtureDef = new FixtureDef();
        wheelfixtureDef.shape = wheelcircle;
        wheelfixtureDef.density = 0.5f;
        wheelfixtureDef.friction = 10f;
        wheelfixtureDef.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture = wheelbody.createFixture(wheelfixtureDef);
        // Une roue 2 -------------------------------------
        BodyDef wheelDef2 = new BodyDef();

        wheelDef2.type = BodyDef.BodyType.DynamicBody;

        wheelDef2.position.set(posX+0.40f, posY+0.11f);

        wheelbody2 = w.createBody(wheelDef2);

        CircleShape wheelcircle2 = new CircleShape();
        wheelcircle2.setRadius(0.078f);

        FixtureDef wheelfixtureDef2 = new FixtureDef();
        wheelfixtureDef2.shape = wheelcircle2;
        wheelfixtureDef2.density = 0.5f;
        wheelfixtureDef2.friction = 10f;
        wheelfixtureDef2.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture2 = wheelbody2.createFixture(wheelfixtureDef2);
        // Une roue 3 -------------------------------------
        BodyDef wheelDef3 = new BodyDef();

        wheelDef3.type = BodyDef.BodyType.DynamicBody;

        wheelDef3.position.set(posX+0.56f, posY+0.11f);

        wheelbody3 = w.createBody(wheelDef3);

        CircleShape wheelcircle3 = new CircleShape();
        wheelcircle3.setRadius(0.078f);

        FixtureDef wheelfixtureDef3 = new FixtureDef();
        wheelfixtureDef3.shape = wheelcircle3;
        wheelfixtureDef3.density = 0.5f;
        wheelfixtureDef3.friction = 10f;
        wheelfixtureDef3.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture3 = wheelbody3.createFixture(wheelfixtureDef3);
        // Petite roue gauche -----------------------------------
        BodyDef wheelDef4 = new BodyDef();

        wheelDef4.type = BodyDef.BodyType.DynamicBody;

        wheelDef4.position.set(posX+0.054f, posY+0.14f);

        wheelbody4 = w.createBody(wheelDef4);

        CircleShape wheelcircle4 = new CircleShape();
        wheelcircle4.setRadius(0.054f);

        FixtureDef wheelfixtureDef4 = new FixtureDef();
        wheelfixtureDef4.shape = wheelcircle4;
        wheelfixtureDef4.density = 0.5f;
        wheelfixtureDef4.friction = 10f;
        wheelfixtureDef4.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture4 = wheelbody4.createFixture(wheelfixtureDef4);
        // Petite roue droite -----------------------------------
        BodyDef wheelDef5 = new BodyDef();

        wheelDef5.type = BodyDef.BodyType.DynamicBody;

        wheelDef5.position.set(posX+0.73f, posY+0.14f);

        wheelbody5 = w.createBody(wheelDef5);

        CircleShape wheelcircle5 = new CircleShape();
        wheelcircle5.setRadius(0.054f);

        FixtureDef wheelfixtureDef5 = new FixtureDef();
        wheelfixtureDef5.shape = wheelcircle5;
        wheelfixtureDef5.density = 0.5f;
        wheelfixtureDef5.friction = 10f;
        wheelfixtureDef5.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture5 = wheelbody5.createFixture(wheelfixtureDef5);
        // Les joints --------------------------------------
        WheelJointDef wheeljointdef = new WheelJointDef();

        wheeljointdef.type = JointDef.JointType.WheelJoint;

        wheeljointdef.initialize(centralbody, wheelbody, new Vector2(wheelbody.getPosition()), new Vector2(0, 1));

        wheeljoint = (WheelJoint)w.createJoint(wheeljointdef);

        wheeljoint.enableMotor(true);
        wheeljoint.setMotorSpeed(0.0f);
        wheeljoint.setSpringDampingRatio(damping);
        wheeljoint.setSpringFrequencyHz(frequency);
        wheeljoint.setMaxMotorTorque(20);
        // -------------------------------------------------
        WheelJointDef wheeljointdef2 = new WheelJointDef();

        wheeljointdef2.type = JointDef.JointType.WheelJoint;

        wheeljointdef2.initialize(centralbody, wheelbody2, new Vector2(wheelbody2.getPosition()), new Vector2(0, 1));

        wheeljoint2 = (WheelJoint)w.createJoint(wheeljointdef2);

        wheeljoint2.enableMotor(true);
        wheeljoint2.setMotorSpeed(0.0f);
        wheeljoint2.setSpringDampingRatio(damping);
        wheeljoint2.setSpringFrequencyHz(frequency);
        wheeljoint2.setMaxMotorTorque(20);
        // ------------------------------------------------
        WheelJointDef wheeljointdef3 = new WheelJointDef();

        wheeljointdef3.type = JointDef.JointType.WheelJoint;

        wheeljointdef3.initialize(centralbody, wheelbody3, new Vector2(wheelbody3.getPosition()), new Vector2(0, 1));

        wheeljoint3 = (WheelJoint)w.createJoint(wheeljointdef3);

        wheeljoint3.enableMotor(true);
        wheeljoint3.setMotorSpeed(0.0f);
        wheeljoint3.setSpringDampingRatio(damping);
        wheeljoint3.setSpringFrequencyHz(frequency);
        wheeljoint3.setMaxMotorTorque(20);
        // ----------------------------------------------
        WheelJointDef wheeljointdef4 = new WheelJointDef();

        wheeljointdef4.type = JointDef.JointType.WheelJoint;

        wheeljointdef4.initialize(centralbody, wheelbody4, new Vector2(wheelbody4.getPosition()), new Vector2(0, 1));

        wheeljoint4 = (WheelJoint)w.createJoint(wheeljointdef4);

        wheeljoint4.enableMotor(true);
        wheeljoint4.setMotorSpeed(0.0f);
        wheeljoint4.setSpringDampingRatio(damping);
        wheeljoint4.setSpringFrequencyHz(frequency);
        // -------------------------------------------------
        WheelJointDef wheeljointdef5 = new WheelJointDef();

        wheeljointdef5.type = JointDef.JointType.WheelJoint;

        wheeljointdef5.initialize(centralbody, wheelbody5, new Vector2(wheelbody5.getPosition()), new Vector2(0, 1));

        wheeljoint5 = (WheelJoint)w.createJoint(wheeljointdef5);

        wheeljoint5.enableMotor(true);
        wheeljoint5.setMotorSpeed(0.0f);
        wheeljoint5.setSpringDampingRatio(damping);
        wheeljoint5.setSpringFrequencyHz(frequency);
        // Setting user data ----------------------------------------

    }
    public void move(float intensite)
    {
        wheeljoint.setMotorSpeed(intensite);
        wheeljoint2.setMotorSpeed(intensite);
        wheeljoint3.setMotorSpeed(intensite);
        wheeljoint4.setMotorSpeed(intensite);
        wheeljoint5.setMotorSpeed(intensite);
    }
    public void shoot(World w,Vector2 direction)
    {
        // Creation du boulet---------------------------
        BodyDef bouletDef = new BodyDef();

        bouletDef.type = BodyDef.BodyType.DynamicBody;

        bouletDef.position.set(centralbody.getWorldCenter().x, centralbody.getWorldCenter().y + 0.156f);

        Body bouletbody = w.createBody(bouletDef);

        CircleShape wheelcircle = new CircleShape();
        wheelcircle.setRadius(0.031f);
        bouletbody.isBullet();
        FixtureDef bouletfixtureDef = new FixtureDef();
        bouletfixtureDef.shape = wheelcircle;
        bouletfixtureDef.density = 50f;
        bouletfixtureDef.friction = 0f;
        bouletfixtureDef.restitution = 0f; // Make it bounce a little bit

        Fixture wheelfixture = bouletbody.createFixture(bouletfixtureDef);
        bouletbody.setUserData(this);
        // ------------------------------------------
        Vector2 v = new Vector2(direction.x-bouletbody.getPosition().x,direction.y-bouletbody.getPosition().y);
        bouletbody.setLinearVelocity(v.x*10,v.y*10);
        bulletsent.add(bouletbody);
    }

    public ArrayList<Body> getBulletsent() {
        return bulletsent;
    }
    public Vector2 getCenter()
    {
        return centralbody.getWorldCenter();
    }

    public World getW() {
        return w;
    }
    /*
    public void draw(ShapeRenderer renderer)
    {
        for (Body b : bodies)
        {
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 1, 0, 1);
            if (b.getFixtureList().first().getShape().getType()== Shape.Type.Circle)
            {
                renderer.circle(b.getPosition().x,b.getPosition().y,b.getFixtureList().first().getShape().getRadius());

            }
            if (b.getFixtureList().first().getShape().getType()== Shape.Type.Polygon)
            {
                PolygonShape ps = new PolygonShape();
                ps = (PolygonShape)b.getFixtureList().first().getShape();
                float[] vertices;
                for (int i=0;i<ps.getVertexCount();i++)
                {
                    //vertices += ps.getVertex(i);
                }
                //renderer.polygon(ps.);
            }
            renderer.end();
        }
    }*/
}
