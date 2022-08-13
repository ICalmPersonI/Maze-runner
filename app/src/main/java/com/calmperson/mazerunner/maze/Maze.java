package com.calmperson.mazerunner.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Maze {
    public static final int WALL = 0;
    public static final int PASS = 1;
    public static final int PATH = 2;

    private final int height;
    private final int width;

    private Cell entry;
    private Cell exit;

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

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Cell)) {
                return false;
            }
            Cell cell = (Cell) o;
            return cell.y == this.y && cell.x == this.x;
        }
    }

    void showPath() {
        LinkedList<Cell> stack = new LinkedList<>();
        ArrayList<Cell> history = new ArrayList<>();
        Cell current = entry;

        maze[current.y][current.x] = PATH;
        stack.add(current);
        history.add(current);

        boolean wasUsingStack = false;

        while (!current.equals(exit)) {
            Cell next = null;
            if (current.y + 1 < height && maze[current.y + 1][current.x] == PASS) {
                next = new Cell(current.y + 1, current.x);
                if (history.contains(next)) next = null;
            }
            if (next == null && current.x + 1 < width && maze[current.y][current.x + 1] == PASS) {
                next = new Cell(current.y, current.x + 1);
                if (history.contains(next)) next = null;
            }
            if (next == null && current.y - 1 > -1 && maze[current.y - 1][current.x] == PASS) {
                next = new Cell(current.y - 1, current.x);
                if (history.contains(next)) next = null;
            }
            if (next == null && current.x - 1 > -1 && maze[current.y][current.x - 1] == PASS) {
                next = new Cell(current.y, current.x - 1);
                if (history.contains(next)) next = null;
            }

            if (next != null) {
                if (wasUsingStack) {
                    stack.add(current);
                }
                stack.add(next);
                history.add(next);
                current = next;
                wasUsingStack = false;
            } else {
                wasUsingStack = true;
                current = stack.removeLast();
            }
        }
        stack.forEach(cell -> maze[cell.y][cell.x] = Maze.PATH);
    }

    //Aldous-Broder algorithm
    void generateNewMaze() {
        maze = new int[height][width];

        LinkedList<Cell> stack = new LinkedList<>();
        Cell current = new Cell(getRandomNumber(1, height - 1), getRandomNumber(1, width - 1));

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
        createInputAndOutput();
        showPath();
    }

    private Direction chooseNextCell(Cell current, int direction) {
        switch (direction) {
            case 1: {
                if (current.y + 2 < height - 1 && maze[current.y + 2][current.x] == 0) {
                    return Direction.TOP;
                }
            }
            case 2: {
                if (current.x + 2 < width - 1 && maze[current.y][current.x + 2] == 0) {
                    return Direction.RIGHT;
                }
            }
            case 3: {
                if (current.y - 2 > 0 && maze[current.y - 2][current.x] == 0) {
                    return Direction.BOTTOM;
                }
            }
            case 4: {
                if (current.x - 2 > 0 && maze[current.y][current.x - 2] == 0) {
                    return Direction.LEFT;
                }
            }
            default: return null;
        }
    }

    private void createInputAndOutput() {
        if (Math.random() > 0.5) {
            int y = getRandomNumber(1, height - 1);
            entry = new Cell(y, 0);
            exit = new Cell(y, width - 1);
            maze[y][entry.x] = PASS;
            maze[y][entry.x + 1] = PASS;
            maze[y][exit.x - 1] = PASS;
            maze[y][exit.x] = PASS;
        } else {
            int x = getRandomNumber(2, width - 1);
            entry = new Cell(0, x);
            exit = new Cell(height - 1, x);
            maze[entry.y][x] = PASS;
            maze[entry.y + 1][x] = PASS;
            maze[exit.y - 1][x] = PASS;
            maze[exit.y][x] = PASS;
        }
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
}
