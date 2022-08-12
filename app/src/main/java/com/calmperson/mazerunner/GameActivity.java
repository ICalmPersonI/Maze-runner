package com.calmperson.mazerunner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
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
        DisplayMetrics metrics = getDisplayMetrics(this);
        int height = metrics.heightPixels / MazeView.CELL_HEIGHT;
        int width = metrics.widthPixels / MazeView.CELL_WIDTH;

        Maze maze = new Maze(height, width);
        MazeView view = new MazeView(this);
        MazeController controller = new MazeController(maze, view);

        controller.generateMaze();
        setContentView(view);

    }

    private DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }
}
