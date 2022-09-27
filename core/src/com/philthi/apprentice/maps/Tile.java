package com.philthi.apprentice.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Tile extends Actor {
    public Vector2 position, size;
    public boolean passable, tested;
    public Sprite sprite;

    public Tile(Sprite t, boolean passable, Vector2 p, Vector2 s) {
        sprite = t;
        size = s;
        this.passable = passable;
        tested = false;
        position = p;
    }

    public void draw (Batch batch, float parentAlpha) {
        sprite.setScale(size.x / sprite.getWidth(), size.y / sprite.getWidth());
        sprite.setCenter(position.x*(sprite.getWidth()*sprite.getScaleX()), position.y*(sprite.getHeight()*sprite.getScaleY()));
        sprite.draw(batch);
    }

    public Tile clone() {
        Tile clone = new Tile(sprite, passable, new Vector2(position), new Vector2(size));
        return clone;
    }

    public String toString() {
        return position + ": [" + (passable?" ":"#") + "]";
    }

}
