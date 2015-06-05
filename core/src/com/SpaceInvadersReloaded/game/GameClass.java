package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameClass extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    BitmapFont font;
    ShapeRenderer renderer;
    MyGestureListener gesturelistener;
    int GAMESTATE;
    World Box2Dworld;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    Vector3 touchPos;
    CanonMan c;
    Invader i;
    // 1 mètre = 128 pixels
    float PIXEL_TO_METER = 128f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        renderer = new ShapeRenderer();

        //Creation d'une camera

        cam = new OrthographicCamera(Gdx.graphics.getWidth() / PIXEL_TO_METER, Gdx.graphics.getHeight() /PIXEL_TO_METER );
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        batch.setProjectionMatrix(cam.combined);
        cam.update();


        GAMESTATE = 1;
        // Initialisation de monde Box2D
        Box2D.init();
        Box2Dworld = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render() {
        // Au demmarage
        if (GAMESTATE == 1) {

            cam = new OrthographicCamera(Gdx.graphics.getWidth() / PIXEL_TO_METER, Gdx.graphics.getHeight() /PIXEL_TO_METER );
            cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            //Affichage bouton start

            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(1, 1, 0, 1);
            float milieuX = (Gdx.graphics.getWidth() / 2);
            float milieuY = (Gdx.graphics.getHeight() / 2);
            renderer.rect(milieuX - 100, milieuY - 25, 200, 50);
            renderer.end();
            batch.begin();
            font.draw(batch, "START", milieuX, milieuY);
            batch.end();

            //Gestion de l'input
            int touchX = Gdx.input.getX();
            int touchY = Gdx.input.getY();

            touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if ((touchX < (milieuX + 100)) && (touchX > (milieuX - 100)) && (touchY < (milieuY + 25)) && (touchY > (milieuY - 25))) {
                //Creation d'un bord
                // Create our body definition
                BodyDef groundBodyDef = new BodyDef();

                groundBodyDef.position.set(new Vector2(0, -10));

                Body groundBody = Box2Dworld.createBody(groundBodyDef);

                PolygonShape groundBox = new PolygonShape();

                groundBox.setAsBox(1000f, 10.0f);

                groundBody.createFixture(groundBox, 0.0f);

                ///-------------------------------------------

				/*BodyDef groundBodyDef2 =new BodyDef();

				groundBodyDef2.position.set(new Vector2(0, Gdx.graphics.getHeight()));

				Body groundBody2 = Box2Dworld.createBody(groundBodyDef2);

				PolygonShape groundBox2 = new PolygonShape();

				groundBox2.setAsBox(1000f, 10.0f);

				groundBody2.createFixture(groundBox2, 0.0f);

                ///------------------------------------------
                BodyDef groundBodyDef3 =new BodyDef();

                groundBodyDef3.position.set(new Vector2(0, 10));

                Body groundBody3 = Box2Dworld.createBody(groundBodyDef3);

                PolygonShape groundBox3 = new PolygonShape();

                groundBox3.setAsBox(10f, 1000.0f);

                groundBody3.createFixture(groundBox3, 0.0f);*/

                ///------------------------------------------
                c = new CanonMan(Box2Dworld, 2, 0);
                i = new Invader(Box2Dworld, 1, 1);
                // Gestion de l'input.
                gesturelistener = new MyGestureListener();
                gesturelistener.initialize(c,cam);
                Gdx.input.setInputProcessor(new GestureDetector(gesturelistener));
                GAMESTATE = 2; // On passe en phase de jeu
            }
        }
        // Si on a appuyé sur start
        if (GAMESTATE == 2) {
            cam.update();
            // Transformation de la position touchée en position relative
            touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            // Nettoyer l'ecran
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // Gestion des contacts
            GestionContact gc = new GestionContact();
            gc.initialize(Box2Dworld);
            Box2Dworld.setContactListener(gc);

            /*if (Gdx.input.justTouched()) {
                //CanonMan c = new CanonMan(Box2Dworld,touchPos.x,touchPos.y );
                //c.move(-20);
                if(Gdx.input.getX()<Gdx.graphics.getWidth()/2)
                {
                    c.move(30);
                }
                if(Gdx.input.getX()>Gdx.graphics.getWidth()/2)
                {
                    c.move(-30);
                }
                //c.shoot(Box2Dworld, new Vector2(touchPos.x, touchPos.y));
                //Invader i = new Invader(Box2Dworld,touchPos.x,touchPos.y);

            }*/
            i.propulseursIA(c);

            //Gestion de la camera

            if (cam.project(new Vector3(c.getCenter().x,c.getCenter().y,0)).x<cam.viewportWidth/2)
            {
                cam.translate(cam.project(new Vector3(c.getCenter().x,c.getCenter().y,0)).x-cam.viewportWidth/2,0);
            }
            cam.update();
            debugRenderer.setDrawJoints(false);
            debugRenderer.render(Box2Dworld, cam.combined);
            // Step à garder à la fin du render
            Box2Dworld.step(1/60f, 6, 2);
            // Detruire ce qu'il y a à détruire ici ne surtout rien détruire pendant le Box2dworld.step !
            i.destroy();

        }

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}