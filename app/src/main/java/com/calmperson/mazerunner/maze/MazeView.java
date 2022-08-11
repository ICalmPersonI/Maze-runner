package com.calmperson.mazerunner.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MazeView extends View {

    private static final int PIXEL_WIDTH = 10;
    public static final int PIXEL_HEIGHT = 10;

    private final Rect rect;
    private final Paint paint;

    private int[][] maze;

    public MazeView(Context context) {
        super(context);
        this.rect = new Rect();
        this.paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; i++) {

            }
        }
    }

    public void update(int[][] maze) {
        this.maze = maze;
        invalidate();
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
}
