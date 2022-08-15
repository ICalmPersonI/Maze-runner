package com.calmperson.mazerunner.maze;

public class MazeController {

    private final Maze maze;
    private final MazeView view;

    public MazeController(Maze maze, MazeView view) {
        this.maze = maze;
        this.view = view;
    }

    public void generateNewMaze() {
        maze.generateNewMaze();
        updateView(maze.getMaze());
    }

    public void showPath() {
        if (!maze.isPathFound) {
            maze.showPath();
            updateView(maze.getMaze());
        }
    }

    public boolean isMazeCompleted() {
        return maze.isUserPathValid();
    }

    public void drawPath(int y, int x) {
        maze.getMaze()[y][x].setType(Node.Type.PATH);
        updateView(maze.getMaze());
    }

    private void updateView(Node[][] maze) {
        view.update(maze);
    }

}
