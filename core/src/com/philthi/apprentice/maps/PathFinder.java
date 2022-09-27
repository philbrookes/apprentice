package com.philthi.apprentice.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathFinder {
    private final Map map;
    public PathFinder(Map m){
        map = m;
    }

    public Path getPath(Vector2 from, Vector2 to) throws ExceptionNoPathFound{
        Tile toTile = map.getTile(to);
        if(toTile == null || !toTile.passable) {
            //destination is not a tile or is a non-passable tile
            throw new ExceptionNoPathFound(from, to);
        }
        map.resetTested();
        Array<Path> paths = new Array<>();
        Path start = new Path();
        start.AddNode(new PathNode(from, from.dst(to)));
        paths.add(start);
        while(paths.size > 0) {
            //find path with shortest length and extend it
            Path closestPath = null;
            Array<Path> newPaths = new Array<>();
            for (int i = 0; i < paths.size; i++) {
                Path p = paths.get(i);
                if (p.loop) {
                    continue;
                }

                if (p.end().node.x == to.x && p.end().node.y == to.y) {
                    //delete node stood on
                    p.nextNode();
                    return p;
                }

                if(closestPath == null || p.length() < closestPath.length()) {
                    if(closestPath != null) {
                        newPaths.add(closestPath);
                    }
                    closestPath = p.clone();
                    continue;
                }
                newPaths.add(p);
            }
            Array<Path> gotPaths = extendPath(closestPath, to);
            newPaths.addAll(gotPaths);
            paths = newPaths;

        }
        throw new ExceptionNoPathFound(from, to);
    }

    private Array<Path> extendPath(Path p, Vector2 to) {
        Vector2 n = p.end().node;
        Array<Tile> tiles = new Array<>();
        tiles.add(map.getTile(new Vector2(n.x-1, n.y)));
        tiles.add(map.getTile(new Vector2(n.x, n.y+1)));
        tiles.add(map.getTile(new Vector2(n.x+1, n.y)));
        tiles.add(map.getTile(new Vector2(n.x, n.y-1)));
        tiles.add(map.getTile(new Vector2(n.x-1, n.y+1)));
        tiles.add(map.getTile(new Vector2(n.x+1, n.y+1)));
        tiles.add(map.getTile(new Vector2(n.x+1, n.y-1)));
        tiles.add(map.getTile(new Vector2(n.x-1, n.y-1)));
        Array<Path> paths = new Array<>();

        for (Tile tile :
                tiles) {
            if(tile != null && tile.passable && !tile.tested){
                tile.tested = true;
                Path newPath = p.clone();
                PathNode pn = new PathNode();
                pn.node = new Vector2(tile.position);
                pn.distance = pn.node.dst(to);
                newPath.AddNode(pn);
                if(!newPath.loop) {
                    paths.add(newPath);
                }
            }
        }
        return paths;
    }
}
