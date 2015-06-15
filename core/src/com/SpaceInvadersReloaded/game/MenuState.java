package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by figiel-paul on 15/06/15.
 */
public class MenuState implements GameState{
    private GameClass gameClass;
    private BitmapFont bitmapFont;
    private SpriteBatch spriteBatch;
    private OrthographicCamera orthographicCamera;

    public MenuState(GameClass gameClass)
    {
        this.gameClass = gameClass;
    }
    @Override
    public void create() {
        // Initialize font
        bitmapFont = new BitmapFont();
        spriteBatch = new SpriteBatch();
        orthographicCamera = new OrthographicCamera();

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

    }

    @Override
    public void render() {
        // Clearing the sprite
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Render the menu
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, "Press to start\nyour journey", Gdx.graphics.getWidth()/2f-100, Gdx.graphics.getHeight()/2f+40);
        spriteBatch.end();
        // Input
        if (Gdx.input.justTouched())
        {
            gameClass.state = GameClass.State.PLAYINGSTATE;
        }
    }

    @Override
    public void resize(int width, int height) {

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
