import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class calculator extends Application {
    private Label displayLabel;
    private double firstNumber = 0;
    private String operator = "";
    private boolean start = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculatrice");
        GridPane gridPane = createGridPane();
        addButtonsToGridPane(gridPane);

        Scene scene = new Scene(gridPane, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    private void addButtonsToGridPane(GridPane gridPane) {
        displayLabel = new Label();
        displayLabel.setPrefHeight(40);
        displayLabel.setStyle("-fx-border-color: black; -fx-padding: 5px; -fx-font-size: 18px;");
        gridPane.add(displayLabel, 0, 0, 4, 1);

        // Create numeric buttons
        int num = 1;
        for (int row = 1; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = createButton(Integer.toString(num));
                gridPane.add(button, col, row);
                num++;
            }
        }

        // Create operation buttons
        String[] operations = {"+", "-", "*", "/"};
        for (int i = 0; i < operations.length; i++) {
            Button button = createButton(operations[i]);
            gridPane.add(button, 3, i + 1);
        }

        // Create other buttons (e.g., equal, clear)
        Button equalButton = createButton("=");
        gridPane.add(equalButton, 0, 4, 2, 1);

        Button zeroButton = createButton("0");
        gridPane.add(zeroButton, 2, 4);

        Button clearButton = createButton("C");
        gridPane.add(clearButton, 3, 4);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(70, 70);
        button.setStyle("-fx-font-size: 18px;");
        button.setOnAction(event -> handleButtonClick(text));
        return button;
    }

    private void handleButtonClick(String text) {
      if (start) {
          displayLabel.setText("");
          start = false;
      }
      if (text.matches("[0-9]")) {
          displayLabel.setText(displayLabel.getText() + text);
      } else if (text.matches("[+\\-*/]")) {
          firstNumber = Double.parseDouble(displayLabel.getText());
          operator = text;
          displayLabel.setText("");
      } else if (text.equals("=")) {
          double secondNumber = Double.parseDouble(displayLabel.getText());
          double result = calculate(firstNumber, secondNumber, operator);
          displayLabel.setText(Double.toString(result));
          operator = "";
          start = true;
      } else if (text.equals("C")) {
          displayLabel.setText("");
      }
    }

    private double calculate(double firstNumber, double secondNumber, String operator) {
      switch (operator) {
          case "+":
              return firstNumber + secondNumber;
          case "-":
              return firstNumber - secondNumber;
          case "*":
              return firstNumber * secondNumber;
          case "/":
              if (secondNumber == 0) {
                  return 0;
              }
              return firstNumber / secondNumber;
      }
      return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
