import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calc_u_later");

        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab();
        tab1.setText("Calculatrice");
        calculator calculator1 = new calculator();
        tab1.setContent(calculator1.createContent());

        Tab tab2 = new Tab();
        tab2.setText("Pourcentages");
        calculator calculator2 = new calculator();
        tab2.setContent(calculator2.createContent());

        Tab tab3 = new Tab();
        tab3.setText("Conversion");
        calculator calculator3 = new calculator();
        tab3.setContent(calculator3.createContent());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        Scene scene = new Scene(tabPane, 350, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}