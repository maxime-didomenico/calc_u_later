import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Conversion {
    public VBox createConversionTab() {
        VBox conversionTab = new VBox(20);
        conversionTab.setAlignment(Pos.CENTER);
        conversionTab.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-font-size: 20;");

        // Create components for the conversion tab
        Label titleLabel = new Label("Calculatrice de conversion");

        Label valueLabel = new Label("Valeur :");
        TextField valueField = new TextField();
        valueField.setPromptText("Entrez une valeur entière");

        Label conversionTypeLabel = new Label("Type de conversion :");
        ComboBox<String> conversionTypeComboBox = new ComboBox<>();
        conversionTypeComboBox.getItems().addAll("Température (Celsius/Fahrenheit)", "Longueur (m/cm)", "Poids (kg/g)", "Volume (L/mL)");
        conversionTypeComboBox.setValue("Température (Celsius/Fahrenheit)");

        Label conversionFromLabel = new Label("De :");
        ComboBox<String> fromUnitComboBox = new ComboBox<>();

        Label conversionToLabel = new Label("Vers :");
        ComboBox<String> toUnitComboBox = new ComboBox<>();

        Label resultLabel = new Label("Résultat :");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        Button resultButton = new Button("Résultat");
        resultButton.setOnAction(event -> {
            try {
                String selectedConversionType = conversionTypeComboBox.getValue();
                String selectedFromUnit = fromUnitComboBox.getValue();
                String selectedToUnit = toUnitComboBox.getValue();
                double inputValue = Double.parseDouble(valueField.getText());

                double result = 0.0;

                if (selectedConversionType.equals("Température (Celsius/Fahrenheit)")) {
                    // Temperature conversion logic
                    if (selectedFromUnit.equals("Celsius") && selectedToUnit.equals("Fahrenheit")) {
                        result = (inputValue * 9 / 5) + 32;
                    } else if (selectedFromUnit.equals("Fahrenheit") && selectedToUnit.equals("Celsius")) {
                        result = (inputValue - 32) * 5 / 9;
                    } else {
                        displayError("Conversion invalide !");
                    }
                } else if (selectedConversionType.equals("Longueur (m/cm)")) {
                    // Length conversion logic
                    if (selectedFromUnit.equals("m") && selectedToUnit.equals("cm")) {
                        result = inputValue * 100;
                    } else if (selectedFromUnit.equals("cm") && selectedToUnit.equals("m")) {
                        result = inputValue / 100;
                    } else {
                        displayError("Conversion invalide !");
                    }
                } else if (selectedConversionType.equals("Poids (kg/g)")) {
                    // Weight conversion logic
                    if (selectedFromUnit.equals("kg") && selectedToUnit.equals("g")) {
                        result = inputValue * 1000;
                    } else if (selectedFromUnit.equals("g") && selectedToUnit.equals("kg")) {
                        result = inputValue / 1000;
                    } else {
                        displayError("Conversion invalide !");
                    }
                } else if (selectedConversionType.equals("Volume (L/mL)")) {
                    // Volume conversion logic
                    if (selectedFromUnit.equals("L") && selectedToUnit.equals("mL")) {
                        result = inputValue * 1000;
                    } else if (selectedFromUnit.equals("mL") && selectedToUnit.equals("L")) {
                        result = inputValue / 1000;
                    } else {
                        displayError("Conversion invalide !");
                    }
                }

                resultField.setText(String.valueOf(result));
            } catch (NumberFormatException e) {
                displayError("Valeur invalide !");
            }
        });



        // Add components to the conversion tab
        conversionTab.getChildren().addAll(
                titleLabel,
                valueLabel, valueField,
                conversionTypeLabel, conversionTypeComboBox,
                conversionFromLabel, fromUnitComboBox,
                conversionToLabel, toUnitComboBox,
                resultLabel, resultField,
                resultButton
        );

        // Update the available conversion units based on the selected conversion type
        conversionTypeComboBox.setOnAction(event -> {
            String selectedConversionType = conversionTypeComboBox.getValue();
            fromUnitComboBox.getItems().clear();
            toUnitComboBox.getItems().clear();

            if (selectedConversionType.equals("Température (Celsius/Fahrenheit)")) {
                fromUnitComboBox.getItems().addAll("Celsius", "Fahrenheit");
                toUnitComboBox.getItems().addAll("Celsius", "Fahrenheit");
            } else if (selectedConversionType.equals("Longueur (m/cm)")) {
                fromUnitComboBox.getItems().addAll("m", "cm");
                toUnitComboBox.getItems().addAll("m", "cm");
            } else if (selectedConversionType.equals("Poids (kg/g)")) {
                fromUnitComboBox.getItems().addAll("kg", "g");
                toUnitComboBox.getItems().addAll("kg", "g");
            } else if (selectedConversionType.equals("Volume (L/mL)")) {
                fromUnitComboBox.getItems().addAll("L", "mL");
                toUnitComboBox.getItems().addAll("L", "mL");
            }

            fromUnitComboBox.setValue(fromUnitComboBox.getItems().get(0));
            toUnitComboBox.setValue(toUnitComboBox.getItems().get(0));
        });
        return conversionTab;
    }

    private void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de conversion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

