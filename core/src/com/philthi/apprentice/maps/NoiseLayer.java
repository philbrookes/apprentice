package com.philthi.apprentice.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.security.InvalidKeyException;
import java.util.Random;

public class NoiseLayer {
    private Vector2 size;
    private Random r;
    private float[][] nodes;
    public NoiseLayer(Vector2 s) {
        size = s;
        nodes = new float[(int)size.x][(int)size.y];
    }
    public NoiseLayer(Vector2 s, long seed) {
        this(s);
        r = new Random();
        r.setSeed(seed);
        this.populate();
    }

    private void populate(){
        for(int x=0;x<size.x;x++){
            for(int y=(int)size.y-1;y>=0;y--){
                nodes[x][y] = r.nextFloat();
            }
        }
    }

    public Vector2 getDimensions() {
        return size;
    }

    public void log() {
        for(int x=0;x<size.x;x++) {
            String line = "";
            for(int y=(int)size.y-1;y>=0;y--) {
                line += "[" + (nodes[x][y]>0.25f ? "#" : " ") + "]";
            }
            Gdx.app.log("APPR/GEN", line);
        }
    }

    public void AddBorderValue(float value) {
        for (int x = 0; x < size.x; x++) {
            for (int y = (int)size.y - 1; y >= 0; y--) {
                if(x == 0 || x == (int)size.x-1 || y == 0 || y == (int)size.y-1) {
                    //set all edges to solid
                    setNodeValue(x, y, value);
                }
            }
        }
    }
    // returns an area from x to x+width and from y to y+height
    public NoiseLayer getArea(Vector2 xy, Vector2 widthHeight) {
        NoiseLayer sub = new NoiseLayer(widthHeight);
        for(int x=0;x<((int)widthHeight.x);x++) {
            for(int y=0;y<((int)widthHeight.y);y++) {
                try {
                    sub.setNodeValue(x, y, this.getNodeValue(x+(int)xy.x, y+(int)xy.y));
                } catch(Exception e) {
                    sub.setNodeValue(x,y,0);
                }
            }
        }
        return sub;
    }

    public NoiseLayer scaleTo(Vector2 newSize) {
        return scale((newSize.x/size.x), (newSize.y/size.y));
    }

    public NoiseLayer scale(float widthScale, float heightScale) {
        // create an empty matrix:
        int scaledWidth = ((int) (size.x * widthScale));
        int scaledHeight = ((int) (size.y * heightScale));
        NoiseLayer out = new NoiseLayer(new Vector2(scaledWidth, scaledHeight));

        for(int x = 0; x < scaledWidth; x++) {
            for(int y=scaledHeight-1; y>=0; y--) {
                int getX = ((int) (x/widthScale));
                int getY = ((int)(y/heightScale));
                try {
                    float v = getNodeValue(getX, getY);
                    out.setNodeValue(x, y, v);
                } catch (Exception e) {
                    Gdx.app.log("APPR/NOISE", "invalid parameters (x: "+getX+", y: "+getY+"): " + e.getMessage());
                }
            }
        }
        return out;
    }

    public float getNodeValue(Vector2 pos) throws InvalidKeyException {
        return getNodeValue(((int)pos.x), ((int)pos.y));
    }

    public float getNodeValue(int x, int y) throws InvalidKeyException {
        if(x > size.x || x < 0 || y > size.y || y < 0){
            throw new InvalidKeyException("x and y values must be within range of matrix");
        }
        return nodes[x][y];
    }

    public void setNodeValue(int x, int y, float value) {
        nodes[x][y] = value;
    }

    public NoiseLayer combine(NoiseLayer other) {
        Vector2 otherSize = other.getDimensions();
        int outWidth=Math.max((int)size.y, ((int)otherSize.y));
        int outHeight=Math.max((int)size.x, ((int)otherSize.x));
        Vector2 outSize = new Vector2(outHeight, outWidth);
        NoiseLayer out = new NoiseLayer(outSize);

        //scale layers to match output layer
        NoiseLayer thisLayer = scaleTo(outSize);
        NoiseLayer otherLayer = other.scaleTo(outSize);

        for(int x=0;x<outWidth;x++){
            for(int y=outHeight-1;y>=0;y--){
                try {
                    out.setNodeValue(x, y, (thisLayer.getNodeValue(x, y) + otherLayer.getNodeValue(x, y)) / 2);
                } catch (Exception e) {
                    Gdx.app.log("APPR/NOISE", "invalid parameters (x: "+x+", y: "+y+"): " + e.getMessage());
                }
            }
        }
        return out;
    }
}
