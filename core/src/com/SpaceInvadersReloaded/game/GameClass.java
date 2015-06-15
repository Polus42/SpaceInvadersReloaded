package com.SpaceInvadersReloaded.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;


public class GameClass extends ApplicationAdapter {
    public enum State
    {
        MENUSTATE,
        PLAYINGSTATE
    }
    public State state;

    GameState menuState;
    GameState playingState;

    // 1 mètre = 128 pixels
    public static final float PIXEL_TO_METER = 16f;

    @Override
    public void create() {
        state = State.MENUSTATE;
        menuState = new MenuState(this);
        playingState = new PlayingState(this);
        menuState.create();
        playingState.create();
    }

    @Override
    public void render() {

        if (state == State.MENUSTATE) {
            menuState.render();
        }
        if (state == State.PLAYINGSTATE)
        {
            playingState.render();
        }

    }
    @Override
    public void resize(int width, int height) {
        if (state == State.MENUSTATE) {
            menuState.resize(width, height);
        }
        if (state == State.PLAYINGSTATE)
        {
            playingState.resize(width, height);
        }
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