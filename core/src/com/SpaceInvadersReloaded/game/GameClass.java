package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


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
    InvaderSpawner is;
    CanonMan c;
    PolygonSpriteBatch psb;
    Horde h;
    Road r;
    BitmapFont bf;
    ShaderProgram SHADER;
    GarbageDestructor gd;

    // 1 mètre = 128 pixels
    float PIXEL_TO_METER = 16f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        renderer = new ShapeRenderer();

        //Creation d'une camera

        cam = new OrthographicCamera(Gdx.graphics.getWidth() / PIXEL_TO_METER, Gdx.graphics.getHeight() /PIXEL_TO_METER );
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
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

            float milieuX = (Gdx.graphics.getWidth() / 2);
            float milieuY = (Gdx.graphics.getHeight() / 2);

            //Gestion de l'input
            int touchX = Gdx.input.getX();
            int touchY = Gdx.input.getY();

            touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);

            if ((touchX < (milieuX + 100)) && (touchX > (milieuX - 100)) && (touchY < (milieuY + 25)) && (touchY > (milieuY - 25))) {
                //Creation d'un bord
                // Ground
                /*BodyDef groundBodyDef = new BodyDef();

                groundBodyDef.position.set(new Vector2(0, -10));

                Body groundBody = Box2Dworld.createBody(groundBodyDef);

                PolygonShape groundBox = new PolygonShape();

                groundBox.setAsBox(1000f, 10.0f);

                groundBody.createFixture(groundBox, 0.0f);*/

                r = new Road(Box2Dworld);
                is = new InvaderSpawner(Box2Dworld);
                /*is.spawn(10,8, InvaderSpawner.Type.BIG_INVADER);
                for (int i = 0;i<10;i++)
                {
                    is.spawn(10*i,8, InvaderSpawner.Type.BIG_INVADER);
                }*/
                //is.spawn(10,8, InvaderSpawner.Type.BIG_INVADER);
                c = new CanonMan(Box2Dworld, 1, 0);
                // Gestion de l'input.
                gesturelistener = new MyGestureListener();
                gesturelistener.initialize(c, cam);
                Gdx.input.setInputProcessor(new GestureDetector(gesturelistener));
                gd = new GarbageDestructor(Box2Dworld);
                psb = new PolygonSpriteBatch();
                h = new Horde(Box2Dworld);
                if (Gdx.app.getType()== Application.ApplicationType.Desktop)
                {
                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/stocky.ttf"));
                    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                    parameter.size = 30;
                    bf = generator.generateFont(parameter); // font size 12 pixels
                    generator.dispose(); // don't forget to dispose to avoid memory leaks!
                }
                if (Gdx.app.getType()== Application.ApplicationType.Android)
                {
                    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/stocky.ttf"));
                    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                    parameter.size = 30;
                    bf = generator.generateFont(parameter); // font size 12 pixels
                    generator.dispose(); // don't forget to dispose to avoid memory leaks!
                }

                //batch.setShader(SHADER);
                /*for (int i = 3;i<500;i++)
                {
                    h.spawn(i*4,1);
                }*/
                //essai();
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

            r.createRoad(c.getCenter());
            is.autoMove(c);
            h.autoMove(c.getCenter());
            h.draw(cam, renderer);
            //Gestion de la camera
            centrerCamera(500);

            cam.update();
            debugRenderer.setDrawJoints(false);
            is.draw(renderer, cam);
            c.draw(psb, cam);
            debugRenderer.render(Box2Dworld, cam.combined);
            // Step à garder à la fin du render
            Box2Dworld.step(1/40f, 3, 1);
            batch.begin();
            bf.draw(batch, c.essai(), 20, 20);
            bf.draw(batch, "GEAR UP", 420, 450);
            bf.draw(batch,"GEAR DOWN",420,100);
            bf.draw(batch,"GEAR :" +Integer.toString(c.getGEAR()),420,300);
            batch.end();
            // Detruire ce qu'il y a à détruire ici ne surtout rien détruire pendant le Box2dworld.step !
            gd.clean();

        }

    }
    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width/PIXEL_TO_METER;
        cam.viewportHeight = height/PIXEL_TO_METER;
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
    public void centrerCamera(int force)
    {
        Vector3 v = cam.project(new Vector3(c.getCenter().x,c.getCenter().y,0));
        cam.translate(-((cam.viewportWidth*PIXEL_TO_METER/2-v.x)/force),-((cam.viewportHeight*PIXEL_TO_METER/2-v.y)/force));
    }
    public void essai()
    {
        // 0. Create a loader for the file saved from the editor.
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.local("fixture/ylol.json"));

        // 1. Create a BodyDef, as usual.
        BodyDef bd = new BodyDef();
        bd.position.set(0, 10);
        bd.type = BodyDef.BodyType.DynamicBody;

        // 2. Create a FixtureDef, as usual.
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        // 3. Create a Body, as usual.
        Body bottleModel = Box2Dworld.createBody(bd);

        // 4. Create the body fixture automatically by using the loader.
        loader.attachFixture(bottleModel, "Name", fd, 20);
    }

}