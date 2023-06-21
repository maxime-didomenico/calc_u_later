import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
    private static final String[][] template = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", "c", "=", "+", "%", "MS", "MR", "MC"},
            {"sin", "cos", "tan", "ln", "log", "sqrt", "pow"} // Added scientific buttons
    };

    private final Map<String, Button> accelerators = new HashMap<>();

    private final DoubleProperty stackValue = new SimpleDoubleProperty();
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final DoubleProperty percentage = new SimpleDoubleProperty();
    private final DoubleProperty memory = new SimpleDoubleProperty();
    private final BooleanProperty memorySet = new SimpleBooleanProperty();

    private enum Op {NOOP, ADD, SUBTRACT, MULTIPLY, DIVIDE}

    private Op curOp = Op.NOOP;
    private Op stackOp = Op.NOOP;

    public TextField createScreen() {
        final TextField screen = new TextField();
        screen.setStyle("-fx-background-color: aquamarine;");
        screen.setAlignment(Pos.CENTER_RIGHT);
        screen.setEditable(false);
        screen.textProperty().bind(Bindings.format("%.4f", value)); // Adjusted formatting to display more decimal places
        return screen;
    }

    public TilePane createButtons() {
        TilePane buttons = new TilePane();
        buttons.setVgap(7);
        buttons.setHgap(7);
        buttons.setPrefColumns(template[0].length);
        for (String[] r : template) {
            for (String s : r) {
                buttons.getChildren().add(createButton(s));
            }
        }
        return buttons;
    }

    public void handleAccelerators(KeyEvent keyEvent) {
        Button activated = accelerators.get(keyEvent.getText());
        if (activated != null) {
            activated.fire();
        }
    }

    private Button createButton(final String s) {
        Button button = makeStandardButton(s);

        if (s.matches("[0-9]")) {
            makeNumericButton(s, button);
        } else {
            final ObjectProperty<Op> triggerOp = determineOperand(s);
            if (triggerOp.get() != Op.NOOP) {
                makeOperandButton(button, triggerOp);
            } else if ("c".equals(s)) {
                makeClearButton(button);
            } else if ("=".equals(s)) {
                makeEqualsButton(button);
            } else if ("%".equals(s)) {
                makePercentageButton(button);
            } else if ("MS".equals(s)) {
                makeMemoryStoreButton(button);
            } else if ("MR".equals(s)) {
                makeMemoryRecallButton(button);
            } else if ("MC".equals(s)) {
                makeMemoryClearButton(button);
            } else if ("sin".equals(s)) { // Added sine button
                makeSineButton(button);
            } else if ("cos".equals(s)) { // Added cosine button
                makeCosineButton(button);
            } else if ("tan".equals(s)) { // Added tangent button
                makeTangentButton(button);
            } else if ("ln".equals(s)) { // Added natural logarithm button
                makeNaturalLogButton(button);
            } else if ("log".equals(s)) { // Added base 10 logarithm button
                makeLogButton(button);
            } else if ("sqrt".equals(s)) { // Added square root button
                makeSquareRootButton(button);
            } else if ("pow".equals(s)) { // Added power button
                makePowerButton(button);
            }
        }

        return button;
    }

    private ObjectProperty<Op> determineOperand(String s) {
        final ObjectProperty<Op> triggerOp = new SimpleObjectProperty<>(Op.NOOP);
        switch (s) {
            case "+" -> triggerOp.set(Op.ADD);
            case "-" -> triggerOp.set(Op.SUBTRACT);
            case "*" -> triggerOp.set(Op.MULTIPLY);
            case "/" -> triggerOp.set(Op.DIVIDE);
        }
        return triggerOp;
    }

    private void makeOperandButton(Button button, final ObjectProperty<Op> triggerOp) {
        button.setStyle("-fx-base: lightgray;");
        button.setOnAction(actionEvent -> curOp = triggerOp.get());
    }

    private Button makeStandardButton(String s) {
        Button button = new Button(s);
        button.setStyle("-fx-base: beige;");
        accelerators.put(s, button);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return button;
    }

    private void makeNumericButton(final String s, Button button) {
        button.setOnAction(actionEvent -> {
            if (curOp == Op.NOOP) {
                value.set(value.get() * 10 + Integer.parseInt(s));
            } else {
                stackValue.set(value.get());
                value.set(Integer.parseInt(s));
                stackOp = curOp;
                curOp = Op.NOOP;
            }
        });
    }

    private void makeClearButton(Button button) {
        button.setStyle("-fx-base: mistyrose;");
        button.setOnAction(actionEvent -> {
            value.set(0);
            percentage.set(0);
        });
    }

    private void makeEqualsButton(Button button) {
        button.setStyle("-fx-base: ghostwhite;");
        button.setOnAction(actionEvent -> {
            switch (stackOp) {
                case ADD -> value.set(stackValue.get() + value.get());
                case SUBTRACT -> value.set(stackValue.get() - value.get());
                case MULTIPLY -> value.set(stackValue.get() * value.get());
                case DIVIDE -> value.set(stackValue.get() / value.get());
            }
            percentage.set(0);
        });
    }

    private void makePercentageButton(Button button) {
        button.setOnAction(actionEvent -> {
            double percentValue = value.get() * percentage.get() / 100;
            value.set(percentValue);
        });
    }

    private void makeMemoryStoreButton(Button button) {
        button.setOnAction(actionEvent -> {
            memory.set(value.get());
            memorySet.set(true);
        });
    }

    private void makeMemoryRecallButton(Button button) {
        button.setOnAction(actionEvent -> {
            if (memorySet.get()) {
                value.set(memory.get());
            }
        });
    }

    private void makeMemoryClearButton(Button button) {
        button.setStyle("-fx-base: lightgray;");
        button.setOnAction(actionEvent -> {
            memory.set(0);
            memorySet.set(false);
        });
    }

    private void makeSineButton(Button button) {
        button.setOnAction(actionEvent -> {
            double radianValue = Math.toRadians(value.get());
            value.set(Math.sin(radianValue));
        });
    }

    private void makeCosineButton(Button button) {
        button.setOnAction(actionEvent -> {
            double radianValue = Math.toRadians(value.get());
            value.set(Math.cos(radianValue));
        });
    }

    private void makeTangentButton(Button button) {
        button.setOnAction(actionEvent -> {
            double radianValue = Math.toRadians(value.get());
            value.set(Math.tan(radianValue));
        });
    }

    private void makeNaturalLogButton(Button button) {
        button.setOnAction(actionEvent -> {
            value.set(Math.log(value.get()));
        });
    }

    private void makeLogButton(Button button) {
        button.setOnAction(actionEvent -> {
            value.set(Math.log10(value.get()));
        });
    }

    private void makeSquareRootButton(Button button) {
        button.setOnAction(actionEvent -> {
            value.set(Math.sqrt(value.get()));
        });
    }

    private void makePowerButton(Button button) {
        button.setOnAction(actionEvent -> {
            double poweredValue = Math.pow(value.get(), 2); // Using Math.pow to calculate power with exponent 2
            value.set(poweredValue);
        });
    }

}
