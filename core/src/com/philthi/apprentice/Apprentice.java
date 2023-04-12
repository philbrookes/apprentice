package com.philthi.apprentice;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.philthi.apprentice.screenManager.ScreenManager;
import com.philthi.apprentice.screens.GameScreen;
import com.philthi.apprentice.screens.LoadingScreen;
import com.philthi.apprentice.screens.MainMenu;

public class Apprentice extends Game implements ApplicationListener {
	private ScreenManager screens;
	private AssetManager assets;

	@Override
	public void create () {
		assets = new AssetManager();
		screens = new ScreenManager(this);
		screens.activate(new LoadingScreen(this));

		assets.load("caves/caves.atlas", TextureAtlas.class);
		assets.load("dungeon/dungeon_tiles.atlas", TextureAtlas.class);
		assets.load("wizards/wizard_fire/tp_sheet.atlas", TextureAtlas.class);
		assets.load("wizards/wizard_walking.atlas", TextureAtlas.class);
		assets.load("icons/icons.atlas", TextureAtlas.class);
		assets.load("actors/actors.atlas", TextureAtlas.class);
	}

	@Override
	public void dispose () {
		super.dispose();
		assets.dispose();
	}


	public AssetManager getAssets() {
		return assets;
	}

	public ScreenManager getScreens() {
		return screens;
	}
}