package com.philthi.apprentice.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import javax.naming.NoInitialContextException;

public class Generator {
    private TileFactory tf;
    public Generator(TileFactory tf) {
        this.tf = tf;
    }
    //Generate a map named name
    //the width and height are the number of nodes in each axis to generate
    public Map Generate(String name, Vector2 size) {
        Map map = new Map(name, size);
        long seed = System.currentTimeMillis();
        NoiseLayer combined = new NoiseLayer(size, seed);
        while(size.x >2 && size.y > 2) {
            NoiseLayer layer = new NoiseLayer(size, seed+(int)size.x+(int)size.y);
            combined.combine(layer);
            size = new Vector2(size.x/2,size.y/2);
        }
        combined.AddBorderValue(0);
        map.populateFromNoiseLayer(combined, tf);
        return map;
    }


}
