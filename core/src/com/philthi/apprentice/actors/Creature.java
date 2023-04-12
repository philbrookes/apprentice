package com.philthi.apprentice.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.philthi.apprentice.actors.actions.ApproachPlayer;
import com.philthi.apprentice.maps.PathFinder;

public class Creature extends PathActor{
    private Animation<Sprite> animation;
    private float speed;
    private float maxHp, currentHp;

    public Creature(Vector2 p, PathFinder pf, Player player) {
        super(p);
        ApproachPlayer a = new ApproachPlayer(player, pf);
        this.addAction(a);
    }


}
