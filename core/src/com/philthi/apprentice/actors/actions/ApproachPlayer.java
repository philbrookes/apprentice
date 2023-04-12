package com.philthi.apprentice.actors.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.philthi.apprentice.actors.Player;
import com.philthi.apprentice.maps.PathFinder;

public class ApproachPlayer extends Action {
    private Player player;
    private PathFinder pf;

    public ApproachPlayer(Player p, PathFinder pf)  {
        player = p;
        this.pf = pf;
    }
    @Override
    public boolean act(float delta) {
        return false;
    }
}
