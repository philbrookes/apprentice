package com.philthi.apprentice.screenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.philthi.apprentice.Apprentice;

import java.util.Stack;

public class ScreenManager {
    private Stack<Screen> screens;
    private Apprentice game;

    public ScreenManager(Apprentice game){
        screens = new Stack<>();
        this.game = game;
    }

    public void activate(Screen active) {
        screens.push(active);
        game.setScreen(active);
    }

    public void close() {
        Screen screen = screens.peek();
        if(screen == null) {
            Gdx.app.exit();
        }

        game.setScreen(screens.pop());
    }
}
