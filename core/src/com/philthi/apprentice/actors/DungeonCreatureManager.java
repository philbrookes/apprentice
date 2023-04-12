package com.philthi.apprentice.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.philthi.apprentice.maps.PathFinder;

public class DungeonCreatureManager implements ActorManager {
    private TextureAtlas spriteSheet;
    private long spawnInterval;
    private long lastSpawn;

    private Array<PathActor> creatures;

    private Stage creatureStage;

    private PathFinder pf;

    private Player player;

    public DungeonCreatureManager(TextureAtlas sprites, long spawnIntervalMillis, Stage creatureStage, PathFinder pf, Player player) {
        spriteSheet = sprites;
        this.spawnInterval = spawnIntervalMillis;
        this.creatures = new Array<>();
        this.creatureStage = creatureStage;
        this.pf = pf;
        this.player = player;
    }

    @Override
    public void Act(float delta) {
        if(TimeUtils.timeSinceMillis(lastSpawn) >= spawnInterval) {
            Gdx.app.log("APPR/CREATURES", "would spawn");
            lastSpawn = TimeUtils.millis();
        }
    }
}
