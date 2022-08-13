package com.calmperson.mazerunner.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Arrays;

public class MazeView extends View {

    public static final int CELL_WIDTH = 20;
    public static final int CELL_HEIGHT = 20;

    private final Rect rect;
    private final Paint paint;

    private int[][] maze;
    private int rectY;
    private int rectX;

    public MazeView(Context context) {
        super(context);
        this.rect = new Rect();
        this.paint = new Paint();
        this.rectY = 0;
        this.rectX = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //addBorder();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                int color;
                if (maze[i][j] == Maze.WALL) {
                    color = Color.BLACK;
                } else if (maze[i][j] == Maze.PASS) {
                    color = Color.WHITE;
                } else {
                    color = Color.RED;
                }
                paint.setColor(color);
                rect.set(rectX, rectY, rectX + CELL_WIDTH, rectY + CELL_HEIGHT);
                canvas.drawRect(rect, paint);
                rectX += CELL_WIDTH;
            }
            rectX = 0;
            rectY += CELL_HEIGHT;
        }
        rectY = 0;
        rectX = 0;
    }

    public void update(int[][] maze) {
        this.maze = maze;
        invalidate();
    }

    private void addBorder() {
        Arrays.fill(maze[0], Maze.WALL);
        for (int i = 1; i < maze.length - 1; i++) {
            maze[i][0] = Maze.WALL;
            maze[i][maze[maze.length - 1].length - 1] = Maze.WALL;
        }
        Arrays.fill(maze[maze.length - 1], Maze.WALL);
    }
}
