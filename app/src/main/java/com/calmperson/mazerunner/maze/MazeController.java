package com.calmperson.mazerunner.maze;

public class MazeController {

    private final Maze maze;
    private final MazeView view;

    public MazeController(Maze maze, MazeView view) {
        this.maze = maze;
        this.view = view;
    }

    public void generateMaze() {
        maze.generateNewMaze();
        updateView(maze.getMaze());
    }

    private void updateView(int[][] maze) {
        view.update(maze);
    }

}
