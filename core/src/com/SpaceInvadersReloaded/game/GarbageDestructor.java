package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by figiel-paul on 05/06/15.
 */
public class GarbageDestructor {
    private World w;
    GarbageDestructor(World w)
    {
        this.w = w;
    }

    /**
     * Cleaning dead joints and bodies
     */
    public void clean()
    {
        com.badlogic.gdx.utils.Array<Body> bodyliste = new com.badlogic.gdx.utils.Array<Body>();
        w.getBodies(bodyliste);
        for (Body b : bodyliste)
        {
            if (b.getUserData()!=null)
            {
                BodyUserData data = (BodyUserData)(b.getUserData());
                if (data.getState() == BodyUserData.State.DESTROY_JOINTS)
                {
                    com.badlogic.gdx.utils.Array<JointEdge> jointlist = b.getJointList();
                    for (JointEdge j : jointlist)
                    {
                        w.destroyJoint(j.joint);
                    }
                }
                if (data.getState() == BodyUserData.State.IS_CENTER_TOUCHED)
                {
                    ((BigInvader) ((BodyUserData) b.getUserData()).getObjectdata()).clean(b);
                    w.destroyBody(b);
                }
                if (data.getState() == BodyUserData.State.TO_DESTROY)
                {
                    w.destroyBody(b);
                }
                if (data.getState() == BodyUserData.State.IS_ALIEN_DEAD)
                {
                    com.badlogic.gdx.utils.Array<JointEdge> jointlist = b.getJointList();
                    for (JointEdge j : jointlist)
                    {
                        w.destroyJoint(j.joint);
                    }
                    ((Zombie) ((BodyUserData) b.getUserData()).getObjectdata()).clean(b);
                }
                /*if (b.getType()== BodyDef.BodyType.DynamicBody&&!b.isAwake())
                {
                    if (((BodyUserData) b.getUserData()).getObjectdata()instanceof BigInvader )
                    {
                        ((BigInvader) ((BodyUserData) b.getUserData()).getObjectdata()).clean(b);

                    }
                    if (((BodyUserData) b.getUserData()).getObjectdata()instanceof Zombie)
                    {
                        ((Zombie) ((BodyUserData) b.getUserData()).getObjectdata()).clean(b);

                    }
                    w.destroyBody(b);
                }*/
            }
        }
    }
}
