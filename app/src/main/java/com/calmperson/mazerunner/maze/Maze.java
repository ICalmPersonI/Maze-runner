package com.calmperson.mazerunner.maze;

import java.util.LinkedList;
import java.util.Random;

public class Maze {
    public static final int WALL = 0;
    public static final int PASS = 1;

    private final int height;
    private final int width;

    private int[][] maze;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
    }

    private enum Direction {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT
    }

    private static class Cell {
        int y;
        int x;

        Cell(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    //Aldous-Broder algorithm
    int[][] generate() {
        maze = new int[height][width];

        LinkedList<Cell> stack = new LinkedList<>();
        Cell current = new Cell(getRandomNumber(1, height - 2), getRandomNumber(1, width - 2));

        maze[current.y][current.x] = PASS;
        stack.add(current);

        while (!stack.isEmpty()) {
            Cell next = null;
            Direction d = chooseNextCell(current, getRandomNumber(1, 4));
            if (d == null) {
                d = chooseNextCell(current, 1);
            }
            int wallY = -1;
            int wallX = -1;
            if (d == Direction.TOP) {
                next = new Cell(current.y + 2, current.x);
                wallY = current.y + 1;
                wallX = current.x;
            } else if (d == Direction.BOTTOM) {
                next = new Cell(current.y - 2, current.x);
                wallY = current.y - 1;
                wallX = current.x;
            } else if (d == Direction.RIGHT) {
                next = new Cell(current.y, current.x + 2);
                wallY = current.y;
                wallX = current.x + 1;
            } else if (d == Direction.LEFT) {
                next = new Cell(current.y, current.x - 2);
                wallY = current.y;
                wallX = current.x - 1;
            }


            if (next != null) {
                current = next;
                stack.add(current);
                maze[current.y][current.x] = PASS;
                maze[wallY][wallX] = PASS;
            } else {
                current = stack.removeLast();
            }
        }
        return maze;
    }

    private Direction chooseNextCell(Cell current, int direction) {
        switch (direction) {
            case 1: {
                if (current.y + 2 < height && maze[current.y + 2][current.x] == 0) {
                    //return new Cell(current.y + 2, current.x);
                    return Direction.TOP;
                }
            }
            case 2: {
                if (current.x + 2 < width && maze[current.y][current.x + 2] == 0) {
                    //return new Cell(current.y, current.x + 2);
                    return Direction.RIGHT;
                }
            }
            case 3: {
                if (current.y - 2 > -1 && maze[current.y - 2][current.x] == 0) {
                    //return new Cell(current.y - 2, current.x);
                    return Direction.BOTTOM;
                }
            }
            case 4: {
                if (current.x - 2 > -1 && maze[current.y][current.x - 2] == 0) {
                    //return new Cell(current.y, current.x - 2);
                    return Direction.LEFT;
                }
            }
            default: return null;
        }
    }


    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
