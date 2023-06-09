import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Conversion {
    public VBox createConversionTab() {
        VBox conversionTab = new VBox(20);
        conversionTab.setAlignment(Pos.CENTER);
        conversionTab.setStyle("-fx-background-color: #333951; -fx-padding: 20; -fx-font-size: 20;");

        // Create components for the conversion tab
        Label titleLabel = new Label("Calculatrice de conversion");
        titleLabel.setStyle("-fx-text-fill: white;");

        Label valueLabel = new Label("Valeur :");
        valueLabel.setStyle("-fx-text-fill: white;");
        TextField valueField = new TextField();
        valueField.setPromptText("Entrez une valeur entière");
        valueField.setStyle("-fx-text-fill: white;");

        Label conversionTypeLabel = new Label("Type de conversion :");
        conversionTypeLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> conversionTypeComboBox = new ComboBox<>();
        conversionTypeComboBox.getItems().addAll("Température (Celsius/Fahrenheit)", "Longueur (m/cm)", "Poids (kg/g)", "Volume (L/mL)");
        conversionTypeComboBox.setValue("Température (Celsius/Fahrenheit)");
        conversionTypeComboBox.setStyle("-fx-text-fill: white;");

        Label conversionFromLabel = new Label("De :");
        conversionFromLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> fromUnitComboBox = new ComboBox<>();
        fromUnitComboBox.setStyle("-fx-text-fill: white;");

        Label conversionToLabel = new Label("Vers :");
        conversionToLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> toUnitComboBox = new ComboBox<>();
        toUnitComboBox.setStyle("-fx-text-fill: white;");

        Label resultLabel = new Label("Résultat :");
        resultLabel.setStyle("-fx-text-fill: white;");
        TextField resultField = new TextField();
        resultField.setEditable(false);
        resultField.setStyle("-fx-text-fill: white;");

        Button resultButton = new Button("Résultat");
        resultButton.setOnAction(event -> {
            try {
                String selectedConversionType = conversionTypeComboBox.getValue();
                String selectedFromUnit = fromUnitComboBox.getValue();
                String selectedToUnit = toUnitComboBox.getValue();
                double inputValue = Double.parseDouble(valueField.getText());

                double result = 0.0;

                switch (selectedConversionType) {
                    case "Température (Celsius/Fahrenheit)" -> {
                        // Temperature conversion logic
                        if (selectedFromUnit.equals("Celsius") && selectedToUnit.equals("Fahrenheit")) {
                            result = (inputValue * 9 / 5) + 32;
                        } else if (selectedFromUnit.equals("Fahrenheit") && selectedToUnit.equals("Celsius")) {
                            result = (inputValue - 32) * 5 / 9;
                        } else {
                            displayError("Conversion invalide !");
                        }
                    }
                    case "Longueur (m/cm)" -> {
                        // Length conversion logic
                        if (selectedFromUnit.equals("m") && selectedToUnit.equals("cm")) {
                            result = inputValue * 100;
                        } else if (selectedFromUnit.equals("cm") && selectedToUnit.equals("m")) {
                            result = inputValue / 100;
                        } else {
                            displayError("Conversion invalide !");
                        }
                    }
                    case "Poids (kg/g)" -> {
                        // Weight conversion logic
                        if (selectedFromUnit.equals("kg") && selectedToUnit.equals("g")) {
                            result = inputValue * 1000;
                        } else if (selectedFromUnit.equals("g") && selectedToUnit.equals("kg")) {
                            result = inputValue / 1000;
                        } else {
                            displayError("Conversion invalide !");
                        }
                    }
                    case "Volume (L/mL)" -> {
                        // Volume conversion logic
                        if (selectedFromUnit.equals("L") && selectedToUnit.equals("mL")) {
                            result = inputValue * 1000;
                        } else if (selectedFromUnit.equals("mL") && selectedToUnit.equals("L")) {
                            result = inputValue / 1000;
                        } else {
                            displayError("Conversion invalide !");
                        }
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

            switch (selectedConversionType) {
                case "Température (Celsius/Fahrenheit)" -> {
                    fromUnitComboBox.getItems().addAll("Celsius", "Fahrenheit");
                    toUnitComboBox.getItems().addAll("Celsius", "Fahrenheit");
                }
                case "Longueur (m/cm)" -> {
                    fromUnitComboBox.getItems().addAll("m", "cm");
                    toUnitComboBox.getItems().addAll("m", "cm");
                }
                case "Poids (kg/g)" -> {
                    fromUnitComboBox.getItems().addAll("kg", "g");
                    toUnitComboBox.getItems().addAll("kg", "g");
                }
                case "Volume (L/mL)" -> {
                    fromUnitComboBox.getItems().addAll("L", "mL");
                    toUnitComboBox.getItems().addAll("L", "mL");
                }
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

