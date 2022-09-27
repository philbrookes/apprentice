package com.philthi.apprentice.maps;

import com.badlogic.gdx.math.Vector2;

public class PathNode {
    public Vector2 node;
    public float distance;

    public PathNode(){

    }

    public PathNode(Vector2 n, float d){
        node = new Vector2(n);
        distance = d;
    }
}
