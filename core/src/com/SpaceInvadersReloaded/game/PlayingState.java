package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.RayHandler;

/**
 * Created by figiel-paul on 15/06/15.
 */
public class PlayingState implements GameState {
    private GameClass gameClass;
    private OrthographicCamera orthographicCamera;
    private MyGestureListener myGestureListener;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private Truck truck;
    private Horde horde;
    private Road road;
    private BitmapFont bitmapFont;
    private GarbageDestructor garbageDestructor;
    private RayHandler rayHandler;
    private SpriteBatch spriteBatch;
    private GestionContact gestionContact;


    public PlayingState(GameClass gameClass)
    {
        this.gameClass=gameClass;
    }
    @Override
    public void create() {
        // Init camera
        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth() / GameClass.PIXEL_TO_METER, Gdx.graphics.getHeight() /GameClass.PIXEL_TO_METER );
        orthographicCamera.position.set(orthographicCamera.viewportWidth / 2, orthographicCamera.viewportHeight / 2, 0);
        orthographicCamera.update();
        // Init Box2d
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        rayHandler = new RayHandler(world);
        //
        road = new Road(world);
        truck = new Truck(world,3,10);
        horde = new Horde(world);
        // Init bitmap font
        if (Gdx.app.getType()== Application.ApplicationType.Desktop)
        {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.local("fonts/stocky.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 30;
            bitmapFont = generator.generateFont(parameter); // font size 12 pixels
            generator.dispose(); // don't forget to dispose to avoid memory leaks!
        }
        else
        {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/stocky.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 30;
            bitmapFont = generator.generateFont(parameter); // font size 12 pixels
            generator.dispose(); // don't forget to dispose to avoid memory leaks!
        }
        // Init input
        myGestureListener = new MyGestureListener();
        myGestureListener.initialize(truck,orthographicCamera);
        //
        garbageDestructor = new GarbageDestructor(world);
        //
        spriteBatch = new SpriteBatch();
        // ContactGesture
        gestionContact = new GestionContact();
        gestionContact.initialize(world);
        world.setContactListener(gestionContact);
        Gdx.input.setInputProcessor(new GestureDetector(myGestureListener));
    }

    @Override
    public void render() {
        // Cleaning screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthographicCamera.update();


        road.createRoad(truck.getCenter());
        horde.autoMove(truck.getCenter());
        //Gestion de la camera
        centerCamera(100);

        rayHandler.setCombinedMatrix(orthographicCamera.combined);
        rayHandler.updateAndRender();
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, truck.essai(), 20, 20);
        bitmapFont.draw(spriteBatch, "GEAR UP", 420, 450);
        bitmapFont.draw(spriteBatch, "GEAR DOWN", 420, 100);
        bitmapFont.draw(spriteBatch, "GEAR :" + Integer.toString(truck.getGEAR()), 420, 300);
        spriteBatch.end();
        //
        orthographicCamera.update();
        box2DDebugRenderer.setDrawJoints(true);
        box2DDebugRenderer.render(world, orthographicCamera.combined);
        // Step à garder à la fin du render
        world.step(1 / 40f, 3, 1);
        // Detruire ce qu'il y a à détruire ici ne surtout rien détruire pendant le Box2dworld.step !
        garbageDestructor.clean();

    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.viewportWidth = width/GameClass.PIXEL_TO_METER;
        orthographicCamera.viewportHeight = height/GameClass.PIXEL_TO_METER;
        orthographicCamera.update();
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
    private void centerCamera(int force)
    {
        Vector3 v = orthographicCamera.project(new Vector3(truck.getCenter().x,truck.getCenter().y,0));
        orthographicCamera.translate(-((orthographicCamera.viewportWidth*GameClass.PIXEL_TO_METER/2-v.x)/force),-((orthographicCamera.viewportHeight*GameClass.PIXEL_TO_METER/2-v.y)/force));
    }
}
