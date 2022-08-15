package com.calmperson.mazerunner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.calmperson.mazerunner.maze.Maze;
import com.calmperson.mazerunner.maze.MazeController;
import com.calmperson.mazerunner.maze.MazeView;

public class MainActivity extends AppCompatActivity {

    private MazeController controller;

    private AlertDialog completedMazeNotif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        completedMazeNotif = new AlertDialog.Builder(this)
                .setTitle("Congratulation!")
                .setMessage("You completed the maze!")
                .setPositiveButton(android.R.string.ok, null)
                .create();

        Context context = this;
        FrameLayout layout = findViewById(R.id.frameLayout);
        ViewTreeObserver vto = layout.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = layout.getMeasuredHeight() / MazeView.CELL_HEIGHT;
                int width = layout.getMeasuredWidth() / MazeView.CELL_WIDTH;

                Maze maze = new Maze(height, width);
                MazeView view = new MazeView(context);
                controller = new MazeController(maze, view);

                controller.generateNewMaze();
                layout.addView(view);
                initOnTouchListener(view);

                findViewById(R.id.refresh).setOnClickListener(v -> controller.generateNewMaze());
                findViewById(R.id.show_path).setOnClickListener(v -> controller.showPath());
                findViewById(R.id.exit).setOnClickListener(v -> {
                    finish();
                    System.exit(0);
                });
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initOnTouchListener(View view) {
        view.setOnTouchListener((v, e) -> {
            if (controller.isMazeCompleted()) {
                completedMazeNotif.show();
            } else {
                int x = (int) e.getX();
                int y = (int) e.getY();
                v.setDrawingCacheEnabled(true);
                try {
                    Bitmap bitmap = v.getDrawingCache();
                    if (bitmap.getPixel(x, y) == Color.WHITE) {
                        controller
                                .drawPath(y / MazeView.CELL_HEIGHT, x / MazeView.CELL_WIDTH);
                    }
                } catch(IllegalArgumentException ex) {
                    ex.printStackTrace();
                }
                v.setDrawingCacheEnabled(false);
            }
            return true;
        });
    }
}