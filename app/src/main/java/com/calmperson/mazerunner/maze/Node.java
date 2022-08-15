package com.calmperson.mazerunner.maze;

import androidx.annotation.NonNull;

public class Node {
    enum Type {
        PATH, PASS, WALL, ENTRY, EXIT
    }

    private final int y;
    private final int x;
    private Type type;

    Node(int y, int x, Type type) {
        this.y = y;
        this.x = x;
        this.type = type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof Node)) return false;

        Node n = (Node) o;
        return n.y == this.y && n.x == this.x;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(" |%s %s %s| ", this.y, this.x, this.type);
    }
}
