package moa.util;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomTopBar {
    public static double initialX;
    public static double initialY;
    public static void configurationTopBar(HBox top_bar, Label minimum, Label close) {
        if(minimum != null || close != null) {
            minimum.setGraphic(new ImageView("/images/moa_-.png"));
            close.setGraphic(new ImageView("/images/moa_x.png"));
            minimum.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.setIconified(true);
                }
            });
            close.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.exit(-1);
                    Platform.exit();
                }
            });
        }

        top_bar.setPrefHeight(LayoutSize.TOP_BAR_HEIGHT);
//        top_bar.setMaxHeight(LayoutSize.TOP_BAR_HEIGHT);
        top_bar.setMinHeight(LayoutSize.TOP_BAR_HEIGHT);

        top_bar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            }
        });
        top_bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    top_bar.getScene().getWindow().setX(me.getScreenX() - initialX);
                    top_bar.getScene().getWindow().setY(me.getScreenY() - initialY);
                }
            }
        });
    }
}
