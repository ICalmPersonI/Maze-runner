package com.calmperson.mazerunner.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MazeView extends View {

    public static final int CELL_WIDTH = 30;
    public static final int CELL_HEIGHT = 30;

    private final Rect rect;
    private final Paint paint;

    private Node[][] maze;
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
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                int color;
                if (maze[i][j].getType() == Node.Type.WALL) color = Color.BLACK;
                else if (maze[i][j].getType() == Node.Type.PASS) color = Color.WHITE;
                else if (maze[i][j].getType() == Node.Type.ENTRY) color = Color.GREEN;
                else if (maze[i][j].getType() == Node.Type.EXIT) color = Color.RED;
                else color = Color.MAGENTA;
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

    public void update(Node[][] maze) {
        this.maze = maze;
        invalidate();
    }
}
