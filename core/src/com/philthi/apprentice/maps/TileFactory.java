package com.philthi.apprentice.maps;

import com.badlogic.gdx.math.Vector2;

public interface TileFactory {
    void SetSize(Vector2 size);
    Vector2 GetSize();
    Tile GetTile(NoiseLayer neighbours, Vector2 p);
}
