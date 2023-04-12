package com.philthi.apprentice.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.philthi.apprentice.Apprentice;
import com.philthi.apprentice.actors.ActorManager;
import com.philthi.apprentice.actors.DungeonCreatureManager;
import com.philthi.apprentice.actors.PathActor;
import com.philthi.apprentice.actors.Player;
import com.philthi.apprentice.actors.Waypoint;
import com.philthi.apprentice.maps.DungeonTileFactory;
import com.philthi.apprentice.maps.ExceptionNoPathFound;
import com.philthi.apprentice.maps.Generator;
import com.philthi.apprentice.maps.Map;
import com.philthi.apprentice.maps.Path;
import com.philthi.apprentice.maps.PathFinder;

public class GameScreen implements Screen, GestureDetector.GestureListener {
    private Apprentice game;
    private Stage level;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private float initialZoom;
    private Player p;
    private Vector2 tileSize, playerSize, worldSize;
    private PathFinder pf;

    private Array<PathActor> creatures;


    private ActorManager creatureFactory;
    public GameScreen(Apprentice game) {
        super();
        tileSize = new Vector2(32, 32);
        playerSize = new Vector2(16, 16);
        worldSize = new Vector2(256,256);
        creatures = new Array<>();
        this.game = game;
        level = new Stage();
        DungeonTileFactory tf = new DungeonTileFactory(game.getAssets().get("dungeon/dungeon_tiles.atlas", TextureAtlas.class), tileSize);
        cam = ((OrthographicCamera) level.getCamera());
        cam.zoom = 0.2f;
        this.batch = new SpriteBatch();
        Generator g = new Generator(tf);
        Map map = g.Generate("main", worldSize);
        pf = new PathFinder(map);
        //find an empty square closest to center of map
        Vector2 pPos = map.findNearestEmpty(new Vector2((worldSize.x)/2,(worldSize.y)/2));
        p = new Player(game.getAssets().get("wizards/wizard_walking.atlas", TextureAtlas.class), new Vector2(pPos.x*tileSize.x, pPos.y * tileSize.y), playerSize);
        creatureFactory = new DungeonCreatureManager(game.getAssets().get("actors/actors.atlas", TextureAtlas.class), 30000, level, pf, p);
        level.addActor(map);
        level.addActor(p);
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(61.0f / 255.0f,
                65.0f / 255.0f,
                79.0f / 255.0f,
                1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        level.act(delta);

        cam.translate(p.position.x-cam.position.x, p.position.y-cam.position.y);
        cam.update();
        level.draw();
        creatureFactory.Act(delta);
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        initialZoom = cam.zoom;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 world = cam.unproject(new Vector3(x, y, 0));
        world.x = Math.round(world.x/tileSize.x);
        world.y = Math.round(world.y/tileSize.y);
        Vector2 from = new Vector2();
        from.x = Math.round(p.position.x/tileSize.x);
        from.y = Math.round(p.position.y/tileSize.y);
        Vector2 to = new Vector2(world.x, world.y);
        try {
            Path route = pf.getPath(from, to);
            route.convertToCoOrds(tileSize);
            Waypoint wp = new Waypoint(game.getAssets().<TextureAtlas>get("icons/icons.atlas"), route.end().node, new Vector2(8, 8));
            p.setPath(route, wp);
            Gdx.app.log("APPR/GAME", "path: " + route);
        } catch (ExceptionNoPathFound e) {
            Gdx.app.log("APPR/GAME", "path finding error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //cam.translate(new Vector2(-deltaX*cam.zoom, deltaY*cam.zoom));
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float max = 0.3f;
        float min = 0.1f;

        float zoomFactor = initialDistance / distance;

        cam.zoom = initialZoom * zoomFactor;
            if (cam.zoom > max) {
                cam.zoom = max;
            }
            if (cam.zoom < min) {
                cam.zoom = min;
            }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
