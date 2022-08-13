package com.calmperson.mazerunner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.calmperson.mazerunner.maze.Maze;
import com.calmperson.mazerunner.maze.MazeController;
import com.calmperson.mazerunner.maze.MazeView;


public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        FrameLayout layout = findViewById(R.id.frameLayout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        Context context = this;
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = layout.getMeasuredHeight() / MazeView.CELL_HEIGHT;
                int width = layout.getMeasuredWidth() / MazeView.CELL_WIDTH;
                Maze maze = new Maze(height, width);
                MazeView view = new MazeView(context);
                MazeController controller = new MazeController(maze, view);
                controller.generateMaze();
                layout.addView(view);
            }
        });

    }

    private DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }
}
