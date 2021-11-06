package org.morons.piggypig;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class piggyMainApplication extends Application implements Runnable{
    @Override
    public void start(Stage stage)throws Exception{
        FXMLLoader fxmlLoader= new FXMLLoader(piggyMainApplication.class.getResource("fxPiggy.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),500.0,300.0);
        scene.getStylesheets().addAll(
                Objects.requireNonNull(
                        piggyMainApplication.class.getResource("PiggyMenuStyle.css")
                ).toExternalForm(),
                Objects.requireNonNull(
                        piggyMainApplication.class.getResource("PiggyApplicationStyle.css")
                ).toExternalForm(),
                Objects.requireNonNull(
                        piggyMainApplication.class.getResource("PiggyButtonStyle.css")
                ).toExternalForm()
        );
        stage.setTitle("Piggy die game ");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void quitAction(){
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        new Thread(new piggyMainApplication(),"PIG").start();
    }

    @Override
    public void run() {
        System.out.println("INSIDE RUN " + Thread.currentThread().getName());
        Application.launch(piggyMainApplication.class);
    }
}
