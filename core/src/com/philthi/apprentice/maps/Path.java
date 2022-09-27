package com.philthi.apprentice.maps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
import java.util.LinkedList;

public class Path {
    public LinkedList<PathNode> nodes;
    public boolean loop;
    private float length;

    public Path(){
        loop = false;
        length=0;
        nodes = new LinkedList<>();
    }

    public boolean AddNode(PathNode n){
        if(nodes.contains(n)) {
            //loop detected
            loop = true;
            return false;
        }
        if(nodes.size() > 0) {
            length += nodes.getLast().node.dst(n.node);
        }
        nodes.add(n);
        return true;
    }

    public float length(){
        return length;
    }

    public PathNode nextNode() {
        if (nodes.size() == 0) {
            return null;
        }
        PathNode next = nodes.getFirst();
        return next;
    }

    public PathNode end(){
        return nodes.getLast();
    }

    public Path clone() {
        Path clone = new Path();
        clone.setNodes((LinkedList<PathNode>) nodes.clone());
        clone.length = length;
        clone.loop = loop;
        return clone;
    }

    public void setNodes(LinkedList<PathNode> nodes) {
        this.nodes = nodes;
    }

    public String toString() {
        String ret = "";
        for (PathNode n : nodes) {
            ret += " > " + n.node;
        }
        return ret;
    }

    public void convertToCoOrds(Vector2 scale) {
        for (PathNode n : nodes) {
            n.node.x = n.node.x * scale.x;
            n.node.y = n.node.y * scale.y;
        }

    }
}
