package com.philthi.apprentice.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.philthi.apprentice.maps.Path;

import java.util.HashMap;

public class Player extends PathActor implements Actor{
    private TextureAtlas sprites;
    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation<Sprite> activeAnimation;
    private Sprite frame;
    private float stateTime=0;
    protected Waypoint wp;

    public Player(TextureAtlas sprites, Vector2 p, Vector2 size) {
        super(p);
        this.sprites = sprites;
        position = p;
        this.size = size;
        animations.put("down", new Animation<Sprite>(0.2f, sprites.createSprites("down")));
        animations.put("up", new Animation<Sprite>(0.2f, sprites.createSprites("up")));
        animations.put("left", new Animation<Sprite>(0.2f, sprites.createSprites("left")));
        animations.put("right", new Animation<Sprite>(0.2f, sprites.createSprites("right")));
        animations.put("idle", new Animation<Sprite>(0.2f, sprites.createSprite("down", 0)));
        activeAnimation = animations.get("idle");
        frame = activeAnimation.getKeyFrame(0, true);
    }

    public void setPath(Path p, Waypoint wp) {
        super.setPath(p);
        if(this.wp != null) {
            this.wp.remove();
        }
        this.wp = wp;
        getStage().addActor(wp);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(path != null) {
            Vector2 move = processPath(delta);
            if(move.x != 0 || move.y != 0) {
                position.add(move);
                if (Math.abs(move.x) > Math.abs(move.y)) {
                    if (move.x > 0) {
                        setActiveAnimation(animations.get("right"));
                    } else {
                        setActiveAnimation(animations.get("left"));
                    }
                } else {
                    if (move.y > 0) {
                        setActiveAnimation(animations.get("up"));
                    } else {
                        setActiveAnimation(animations.get("down"));
                    }
                }
            }
        } else {
            if(this.wp != null) {
                this.wp.remove();
            }
            setActiveAnimation(animations.get("idle"));
        }
        frame = activeAnimation.getKeyFrame(stateTime += delta, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        frame.setScale(size.x / frame.getWidth(), size.y / frame.getWidth());
        frame.setCenter(position.x, position.y);
        frame.draw(batch);
    }

    private void setActiveAnimation(Animation<Sprite> animation) {
        if(animation == activeAnimation) {
            return;
        }
        activeAnimation = animation;
        stateTime = 0;
    }
}
