package com.philthi.apprentice.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.security.InvalidKeyException;
import java.util.Random;

public class DungeonTileFactory implements TileFactory{
    private TextureAtlas sprites;
    private Vector2 size;
    public DungeonTileFactory(TextureAtlas sprites, Vector2 size) {
        this.size = size;
        this.sprites = sprites;
    }

    @Override
    public void SetSize(Vector2 size) {
        this.size = size;
    }

    @Override
    public Vector2 GetSize() {
        return size;
    }

    public Tile GetTile(NoiseLayer neighbours, Vector2 p) {
        float threshold = 0.33f;
        if (neighbours.getDimensions().x != 3 || neighbours.getDimensions().y != 3) {
            return null;
        }
        try {
            float center = neighbours.getNodeValue(1,1);
            if (center > threshold) {
                return new Tile(random(sprites.createSprites("empty_with_no_borders")), true, p, size);
            }
        } catch (Exception e) {
            return new Tile(random(sprites.createSprites("full_all_sides")), false, p, size);
        }

        boolean left = false;
        boolean right = false;
        boolean top = false;
        boolean bottom = false;
        try {
            left = neighbours.getNodeValue(0, 1) <= threshold;
            right = neighbours.getNodeValue(2, 1) <= threshold;
            top = neighbours.getNodeValue(1, 2) <= threshold;
            bottom = neighbours.getNodeValue(1, 0) <= threshold;
        } catch (InvalidKeyException e) {
            Gdx.app.log("APPR/DUNGEON", "bad neighbour request");
        }

        if(left && right && top && bottom){
            //all
            return new Tile(random(sprites.createSprites("full_with_no_borders")), false, p, size);
        } else if(top && !bottom){
            if(left && right) {
                //top_border_left_and_right
                return new Tile(random(sprites.createSprites("full_with_borders_bottom")), false, p, size);
            }else if(left){
                //top_border_left
                return new Tile(random(sprites.createSprites("full_with_borders_bottom_right")), false, p, size);
            }else if(right){
                //top_border_right
                return new Tile(random(sprites.createSprites("full_with_borders_bottom_left")), false, p, size);
            } else {
                //top_border_none
                return new Tile(random(sprites.createSprites("full_with_borders_bottom_left_right")), false, p, size);
            }
        } else if(bottom && !top) {
            if (left && right) {
                //bottom_border_left_and_right
                return new Tile(random(sprites.createSprites("full_with_borders_top")), false, p, size);
            } else if (left) {
                //bottom_border_left
                return new Tile(random(sprites.createSprites("full_with_borders_top_right")), false, p, size);
            } else if (right) {
                //bottom_border_right
                return new Tile(random(sprites.createSprites("full_with_borders_top_left")), false, p, size);
            } else {
                //bottom_border_none
                return new Tile(random(sprites.createSprites("full_with_borders_top_left_right")), false, p, size);
            }
        }else if(top && bottom) {
            if(left) {
                //left_border_top_and_bottom
                return new Tile(random(sprites.createSprites("full_with_borders_right")), false, p, size);
            }else if(right) {
                //right_border_top_and_bottom
                return new Tile(random(sprites.createSprites("full_with_borders_left")), false, p, size);
            } else {
                //mid_vertical_border
                return new Tile(random(sprites.createSprites("full_with_borders_left_right")), false, p, size);
            }
        } else if(left && right) {
            return new Tile(random(sprites.createSprites("full_with_borders_top_bottom")), false, p, size);
        } else if(left) {
            //mid_horizontal_border_left
            return new Tile(random(sprites.createSprites("full_with_borders_top_bottom_right")), false, p, size);
        } else if(right) {
            //mid_horizontal_border_right
            return new Tile(random(sprites.createSprites("full_with_borders_top_bottom_left")), false, p, size);
        } else {
            //no neighbours
            return new Tile(random(sprites.createSprites("full_with_borders_all")), false, p, size);
        }
    }

    private Sprite random(Array<Sprite> sprites) {
        Random r = new Random();
        int i = r.nextInt(sprites.size);
        return sprites.get(i);
    }

}
