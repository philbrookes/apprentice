package com.philthi.apprentice.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.philthi.apprentice.Apprentice;

public class LoadingScreen implements Screen {
	private Apprentice game;

	public LoadingScreen(Apprentice game) {
		this.game = game;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if(game.getAssets().update()) {
			Gdx.app.log("APPR/LOAD", "loading complete");
			game.getScreens().activate(new GameScreen(game));
		} else {
			Gdx.app.log("APPR/LOAD", "loading...");
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
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
