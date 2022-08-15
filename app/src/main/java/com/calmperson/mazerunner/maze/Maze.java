package com.calmperson.mazerunner.maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.BiConsumer;

public class Maze {

    private final int height;
    private final int width;

    private Node entry;
    private Node exit;
    private Node[][] maze;

    public boolean isPathFound;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
    }

    private enum Direction {
        TOP, BOTTOM, RIGHT, LEFT;

        static Direction[] getAll() {
            return new Direction[] {TOP, BOTTOM, RIGHT, LEFT};
        }
    }

    boolean isUserPathValid() {
        if (maze[entry.getY()][entry.getX()].getType() == Node.Type.PATH
                && maze[exit.getY()][exit.getX()].getType() == Node.Type.PATH) {
            LinkedList<Node> stack = new LinkedList<>();
            ArrayList<Node> history = new ArrayList<>();
            Node current = entry;

            stack.add(current);
            history.add(current);
            while (!current.equals(exit)) {
                Node next = chooseNextCell(history, current, Node.Type.PATH);
                if (next != null) {
                    stack.add(next);
                    history.add(next);
                    current = next;
                } else {
                    if (stack.isEmpty()) return false;
                    current = stack.removeLast();
                }
            }
            return true;
        }
        return false;
    }

    void showPath() {
        clear();

        LinkedList<Node> stack = new LinkedList<>();
        ArrayList<Node> history = new ArrayList<>();
        Node current = entry;
        boolean wasUsingStack = false;

        stack.add(current);
        history.add(current);

        while (true) {
            Node next = chooseNextCell(history, current, Node.Type.PASS);

            if (next != null) {
                if (wasUsingStack) stack.add(current);
                stack.add(next);
                history.add(next);
                current = next;
                if (current.equals(exit)) break;
                wasUsingStack = false;
            } else {
                if (stack.isEmpty()) break;
                current = stack.removeLast();
                wasUsingStack = true;
            }
        }
        stack.forEach(node -> node.setType(Node.Type.PATH));
        isPathFound = true;
    }

    //Aldous-Broder algorithm
    void generateNewMaze() {
        initNewMaze();
        isPathFound = false;

        LinkedList<Node> stack = new LinkedList<>();
        Node current = maze[getRandomNumber(1, height - 1)][getRandomNumber(1, width - 1)];

        maze[current.getY()][current.getX()].setType(Node.Type.PASS);
        stack.add(current);

        while (!stack.isEmpty()) {
            Node next = null;
            Node wall = null;
            Direction d = chooseNextDirection(current, getRandomNumber(1, 4));
            if (d == null) d = chooseNextDirection(current, 1);

            if (d == Direction.TOP) {
                next = maze[current.getY() + 2][current.getX()];
                wall = maze[current.getY() + 1][current.getX()];
            } else if (d == Direction.BOTTOM) {
                next = maze[current.getY() - 2][current.getX()];
                wall = maze[current.getY() - 1][current.getX()];
            } else if (d == Direction.RIGHT) {
                next = maze[current.getY()][current.getX() + 2];
                wall = maze[current.getY()][current.getX() + 1];
            } else if (d == Direction.LEFT) {
                next = maze[current.getY()][current.getX() - 2];
                wall = maze[current.getY()][current.getX() - 1];
            }

            if (next != null) {
                current = next;
                stack.add(current);
                current.setType(Node.Type.PASS);
                wall.setType(Node.Type.PASS);
            } else {
                current = stack.removeLast();
            }
        }
        createInputAndOutput();
    }

    private Node chooseNextCell(ArrayList<Node> history, Node current, Node.Type type) {
        Node next = null;
        if (current.getY() + 1 < height
                && maze[current.getY() + 1][current.getX()].getType() == type) {
            next = maze[current.getY() + 1][current.getX()];
            if (history.contains(next)) next = null;
        }
        if (next == null && current.getX() + 1 < width
                && maze[current.getY()][current.getX() + 1].getType() == type) {
            next = maze[current.getY()][current.getX() + 1];
            if (history.contains(next)) next = null;
        }
        if (next == null && current.getY() - 1 > -1
                && maze[current.getY() - 1][current.getX()].getType() == type) {
            next = maze[current.getY() - 1][current.getX()];
            if (history.contains(next)) next = null;
        }
        if (next == null && current.getX() - 1 > -1
                && maze[current.getY()][current.getX() - 1].getType() == type) {
            next = maze[current.getY()][current.getX() - 1];
            if (history.contains(next)) next = null;
        }
        return next;
    }

    private Direction chooseNextDirection(Node current, int direction) {
        switch (direction) {
            case 1: {
                if (current.getY() + 2 < height - 1 && maze[current.getY() + 2][current.getX()].getType() == Node.Type.WALL) {
                    return Direction.TOP;
                }
            }
            case 2: {
                if (current.getX() + 2 < width - 1 && maze[current.getY()][current.getX() + 2].getType() == Node.Type.WALL) {
                    return Direction.RIGHT;
                }
            }
            case 3: {
                if (current.getY() - 2 > 0 && maze[current.getY() - 2][current.getX()].getType() == Node.Type.WALL) {
                    return Direction.BOTTOM;
                }
            }
            case 4: {
                if (current.getX() - 2 > 0 && maze[current.getY()][current.getX() - 2].getType() == Node.Type.WALL) {
                    return Direction.LEFT;
                }
            }
            default: return null;
        }
    }

    private void createInputAndOutput() {
        Direction[] directions = Direction.getAll();
        Direction directionOfEntry;
        Direction directionOfExit;

        do {
            directionOfEntry = directions[getRandomNumber(0, directions.length - 1)];
            directionOfExit = directions[getRandomNumber(0, directions.length - 1)];
        } while (directionOfEntry == directionOfExit);

        BiConsumer<Direction, Boolean> creator = (d, isItEntry) -> {
            while (true) {
                int x = getRandomNumber(1, width - 2);
                int y = getRandomNumber(1, height - 2);
                if (d == Direction.TOP) {
                    if (isItEntry) entry = maze[1][x];
                    else exit = maze[1][x];
                    if (maze[1][x].getType() == Node.Type.PASS) {
                        maze[0][x].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        break;
                    } else if (maze[2][x].getType() == Node.Type.PASS) {
                        maze[0][x].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        maze[1][x].setType(Node.Type.PASS);
                        break;
                    }
                } else if (d == Direction.BOTTOM) {
                    if (isItEntry) entry = maze[height - 2][x];
                    else exit = maze[height - 2][x];
                    if (maze[height - 2][x].getType() == Node.Type.PASS) {
                        maze[height - 1][x].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        break;
                    } else if (maze[height - 3][x].getType() == Node.Type.PASS) {
                        maze[height - 1][x].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        maze[height - 2][x].setType(Node.Type.PASS);
                        break;
                    }
                } else if (d == Direction.RIGHT) {
                    if (isItEntry) entry = maze[y][1];
                    else exit = maze[y][1];
                    if (maze[y][1].getType() == Node.Type.PASS) {
                        maze[y][0].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        break;
                    } else if (maze[y][2].getType() == Node.Type.PASS) {
                        maze[y][0].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        maze[y][1].setType(Node.Type.PASS);
                        break;
                    }
                } else if (d == Direction.LEFT) {
                    if (isItEntry) entry = maze[y][width - 2];
                    else exit = maze[y][width - 2];
                    if (maze[y][width - 2].getType() == Node.Type.PASS) {
                        maze[y][width - 1].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        break;
                    } else if (maze[y][width - 3].getType() == Node.Type.PASS) {
                        maze[y][width - 1].setType(isItEntry ? Node.Type.ENTRY : Node.Type.EXIT);
                        maze[y][width - 2].setType(Node.Type.PASS);
                        break;
                    }
                }
            }
        };

        creator.accept(directionOfEntry, true);
        creator.accept(directionOfExit, false);
    }

    private void initNewMaze() {
        maze = new Node[height][width];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = new Node(i, j, Node.Type.WALL);
            }
        }
    }

    private void clear() {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].getType() == Node.Type.PATH) maze[i][j].setType(Node.Type.PASS);
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public Node[][] getMaze() {
        return maze;
    }

}
