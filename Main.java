import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Calculator calculator = new Calculator();
        final TextField screen = calculator.createScreen();
        final TilePane buttons = calculator.createButtons();

        stage.setTitle("Calculator");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setScene(new Scene(createLayout(screen, buttons)));
        buttons.setStyle("-fx-background-color: #1A2134;");
        stage.show();
    }

    private TabPane createLayout(TextField screen, TilePane buttons) {
        final TabPane layout = new TabPane();;
        layout.setStyle("-fx-background-color: #1A2134; -fx-padding: 7; -fx-font-size: 20;");

        // Create the main calculator tab
        Tab calculatorTab = new Tab("Calculator");
        VBox calculatorTabContent = new VBox(20);
        calculatorTabContent.setAlignment(Pos.CENTER);
        calculatorTabContent.getChildren().addAll(screen, buttons);
        calculatorTab.setContent(calculatorTabContent);
        layout.getTabs().add(calculatorTab);

        // Create the conversion tab
        Conversion conversion = new Conversion();
        Tab conversionTab = new Tab("Conversion");
        VBox conversionTabContent = conversion.createConversionTab();
        conversionTab.setContent(conversionTabContent);
        layout.getTabs().add(conversionTab);

        return layout;
    }
}

