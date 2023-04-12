package com.philthi.apprentice.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Waypoint extends com.badlogic.gdx.scenes.scene2d.Actor implements Actor {
    private TextureAtlas sprites;
    private Sprite marker, base;
    public Vector2 position, size, markerPosition, basePosition;
    private boolean up=true;
    private float speed=15f;

    public Waypoint(TextureAtlas s, Vector2 p, Vector2 size) {
        super();
        sprites = s;
        marker = s.createSprite("marker");
        base = s.createSprite("base");
        position = markerPosition = p;
        basePosition = new Vector2(position.x, position.y-7);
        this.size = size;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        //bounce
        if(up) {
            markerPosition = new Vector2(markerPosition.x, markerPosition.y-(speed*delta));
        } else {
            markerPosition = new Vector2(markerPosition.x, markerPosition.y+(speed*delta));
        }

        if(markerPosition.y - position.y > 2.5f){
            up = true;
        }
        if(markerPosition.y - position.y < -2.5f){
            up =  false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        base.setScale(size.x / base.getWidth(), size.y / base.getWidth());
        base.setCenter(basePosition.x, basePosition.y);
        base.draw(batch);

        marker.setScale(size.x / marker.getWidth(), size.y / marker.getWidth());
        marker.setCenter(markerPosition.x, markerPosition.y);
        marker.draw(batch);
    }
}
