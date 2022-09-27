package com.philthi.apprentice.maps;

import com.badlogic.gdx.math.Vector2;

public class ExceptionNoPathFound extends Exception {
    public ExceptionNoPathFound(Vector2 from, Vector2 to) {
        super("No path found, from: " + from + ", to: " + to);
    }
}
