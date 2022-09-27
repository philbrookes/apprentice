package com.philthi.apprentice.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.philthi.apprentice.actors.Actor;

import java.util.HashMap;

public class Map extends com.badlogic.gdx.scenes.scene2d.Actor {
    public String name;
    public Vector2 dimensions;
    public Map parent;
    public HashMap<String, Map> children;
    public HashMap<Vector2, Tile> tiles;
    public HashMap<String, Actor> actors;

    public void draw (Batch batch, float parentAlpha) {
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = ((int) dimensions.y) - 1; y >= 0; y--) {
                Vector2 p = new Vector2(x, y);
                Tile t = tiles.get(p);
                if(t!=null) {
                    t.draw(batch, parentAlpha);
                }
            }
        }
    }

    public Map(String name, Vector2 dimensions) {
        this.name = name;
        this.dimensions = dimensions;
        parent = null;
        children = new HashMap<>();
        tiles = new HashMap<>();
        actors = new HashMap<>();
    }

    public void resetTested() {
        for (Tile t :
                tiles.values()) {
            t.tested = false;
        }
    }

    public Vector2 findNearestEmpty(Vector2 pos) {
        Tile closest = null;
        for (Tile tile :
                tiles.values()) {
            if(tile.passable) {
                if(closest == null || tile.position.dst(pos) < closest.position.dst(pos)) {
                    closest = tile.clone();
                }
            }
        }
        if(closest == null) {
            return null;
        }
        return new Vector2(closest.position);
    }

    public Tile getTile(Vector2 p) {
        return tiles.get(p);
    }
    public void setTile(Vector2 position, Tile tile) {
        tiles.put(position, tile);
    }

    public void populateFromNoiseLayer(NoiseLayer l, TileFactory tf) {
        NoiseLayer scaled = l.scaleTo(dimensions);
        tiles = new HashMap<>();
        Gdx.app.log("APPR/MAP", "loading map with dimensions: " + dimensions);
        for (int x = 0; x < dimensions.x; x++) {
            for (int y = ((int) dimensions.y - 1); y >= 0; y--) {
                Vector2 p = new Vector2(x, y);
                NoiseLayer neighbours = scaled.getArea(new Vector2(p.x-1,p.y-1), new Vector2(3,3));
                tiles.put(p, tf.GetTile(neighbours, p));
            }
        }
    }

    public void drawToLog(){
        for (int x = 0; x < dimensions.x; x++) {
            String line = "";
            for (int y = ((int) dimensions.y - 1); y >= 0; y--) {
                line += getTile(new Vector2(x,y));
            }
            Gdx.app.log("APPR/MAP", line);
        }
    }

    // returns a map from x to x+width and from y to y+height
    public Map getSubMap(Vector2 xy, Vector2 widthHeight, String name) {
        Map sub = new Map(name, widthHeight);
        sub.parent = this;
        for(int x=((int)xy.x);x<((int)xy.x+widthHeight.x);x++) {
            for(int y=((int)xy.y);y<((int)xy.y+widthHeight.y);y++) {
                Vector2 p = new Vector2(x,y);
                Tile v = tiles.get(p);
                if(v == null) {
                    continue;
                }
                sub.setTile(p, v);
            }
        }
        return sub;
    }

    public Map getParentMap() {
        return parent;
    }
}
