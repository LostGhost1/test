package org.openjfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;


public class MainApp extends Application {
    int player_x = 0;
    int player_y = 0;
    int falling_block_x = 13;
    int falling_block_y = 0;
    GridPane grid = null;

    public static void main(String[] args) {
        launch(args);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private Parent createContent() {
        grid = new GridPane();
        int dimy = 20;
        int dimx = 14;
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        for (int i = 0; i < dimx; i++) {
            for (int j = 0; j < dimy; j++) {
                Rectangle r = new Rectangle();
                r.setWidth(8);
                r.setHeight(8);
                r.setFill(Color.GREY);
                grid.add(r, i, j);

            }
        }
        UpdateGrid(grid);
        VBox menubox = new VBox(new Button(), new Button(), new Button());
        VBox infobox = new VBox(new Button(), new Button(), new Button());
        HBox box = new HBox(menubox, grid, infobox);
        box.setMargin(grid, new Insets(10, 10, 10, 10));
        return box;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene sc = new Scene(createContent(), 300, 300);
        sc.setOnKeyPressed(key -> {
            if (key.getCode() == KeyCode.A)
                player_x -= 1;
            if (key.getCode() == KeyCode.D)
                player_x += 1;
            if (key.getCode() == KeyCode.W)
                player_y -= 1;
            if (key.getCode() == KeyCode.S)
                player_y += 1;
            UpdateGrid(grid);
        });
        stage.setScene(sc);
        Timer falling_block_timer = new Timer();
        falling_block_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                falling_block_y += 1;
                if (falling_block_y > 13) falling_block_y = 0;
                UpdateGrid(grid);
            }
        }, 0, 1000);
        stage.show();
    }

    private void UpdateGrid(GridPane pane) {
        for (Node node : pane.getChildren()) {
            ((Rectangle) node).setFill(Color.GRAY);
        }
        ((Rectangle) getNodeFromGridPane(pane, player_x, player_y)).setFill(Color.BLUE);
        ((Rectangle) getNodeFromGridPane(pane, falling_block_x, falling_block_y)).setFill(Color.RED);
    }

}
