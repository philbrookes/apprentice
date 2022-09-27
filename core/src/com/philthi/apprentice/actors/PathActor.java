package com.philthi.apprentice.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.philthi.apprentice.maps.Path;
import com.philthi.apprentice.maps.PathNode;

abstract public class PathActor extends Actor {
    public Vector2 position, size;
    protected Path path;
    protected float speed=25f;

    public PathActor(Vector2 p) {
        position = p;
    }


    public void setPath(Path p) {
        this.path = p;
    }

    protected Vector2 processPath(float delta) {
        float stride = speed * delta;
        PathNode pn = path.nextNode();
        if (pn == null) {
            path = null;
            return new Vector2();
        } else if(pn.node.dst(position) <= stride) {
            position = new Vector2(pn.node);
            path.nodes.removeFirst();
            return new Vector2();
        } else {
            Vector2 direction = new Vector2(pn.node).sub(position).nor();
            Vector2 move = new Vector2(direction).scl(stride);
            return move;
        }
    }

}
