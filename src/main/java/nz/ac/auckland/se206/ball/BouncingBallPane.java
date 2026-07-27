
package nz.ac.auckland.se206.ball;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BouncingBallPane extends Pane {

  private double ballRadius = 10;
  private double xSpeed = 1;
  private double ySpeed = 1;

  public BouncingBallPane() {
    Circle ball = new Circle(ballRadius);

    getChildren().add(ball);
    ball.setLayoutX(getWidth() / 2);
    ball.setLayoutY(getHeight() / 2);

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), e -> move(ball)));
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }

  private void move(Circle ball) {
    ball.setLayoutX(ball.getLayoutX() + xSpeed);
    ball.setLayoutY(ball.getLayoutY() + ySpeed);

    if (ball.getLayoutX() > getWidth() || ball.getLayoutX() < 0) {
      xSpeed *= -1;
    }
    if (ball.getLayoutY() > getHeight() || ball.getLayoutY() < 0) {
      ySpeed *= -1;
    }
  }

}
