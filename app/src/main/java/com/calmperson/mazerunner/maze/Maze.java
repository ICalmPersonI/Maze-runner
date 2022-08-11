package com.calmperson.mazerunner.maze;

public class Maze {
    private static final int WALL = 1;
    private static final int PASS = 0;

    private final int height;
    private final int width;

    Maze(int height, int width) {
        this.height = height;
        this.width = width;
    }

    int[][] generate() {
        int[][] maze = new int[height][width];

        return maze;
    }
}
