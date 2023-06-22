import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;

public class Calculator {
    private static final String[][] template = {
            {"7", "8", "9", "c"},
            {"4", "5", "6", "/"},
            {"1", "2", "3", "*"},
            {",", "0", "-", "+", "pow", "%", "MS", "MR", "MC"},
            {"sin", "cos", "tan", "ln", "log", "sqrt", "="} // Added scientific buttons
    };

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
        screen.setAlignment(Pos.CENTER_RIGHT);
        screen.setPrefHeight(50);
        screen.setEditable(false);
        screen.textProperty().bind(Bindings.format("%.3f", value));
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

    private Button createButton(final String s) {
        Button button = makeStandardButton(s);

        switch (s) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> makeNumericButton(s, button);
            case "c" -> makeClearButton(button);
            case "=" -> makeEqualsButton(button);
            case "%" -> makePercentageButton(button);
            case "MS" -> makeMemoryStoreButton(button);
            case "MR" -> makeMemoryRecallButton(button);
            case "MC" -> makeMemoryClearButton(button);
            case "sin" -> makeSineButton(button);
            case "cos" -> makeCosineButton(button);
            case "tan" -> makeTangentButton(button);
            case "ln" -> makeNaturalLogButton(button);
            case "log" -> makeLogButton(button);
            case "sqrt" -> makeSquareRootButton(button);
            case "pow" -> makePowerButton(button);
            case "," -> makeCommaButton(button);
            default -> {
                final ObjectProperty<Op> triggerOp = determineOperand(s);
                if (triggerOp.get() != Op.NOOP) {
                    makeOperandButton(button, triggerOp);
                }
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
        button.setStyle("-fx-base: #333951;");
        button.setOnAction(actionEvent -> curOp = triggerOp.get());
    }

    private Button makeStandardButton(String s) {
        Button button = new Button(s);
        button.setStyle("-fx-base: #333951;");
        button.setPrefSize(94, 80);
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
        button.setStyle("-fx-base: #2C324A;");
        button.setOnAction(actionEvent -> {
            value.set(0);
            percentage.set(0);
        });
    }

    private void makeEqualsButton(Button button) {
        button.setStyle("-fx-base: #2C324A;");
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
        button.setStyle("-fx-base: #333951;");
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
        button.setOnAction(actionEvent -> value.set(Math.log(value.get())));
    }

    private void makeLogButton(Button button) {
        button.setOnAction(actionEvent -> value.set(Math.log10(value.get())));
    }

    private void makeSquareRootButton(Button button) {
        button.setOnAction(actionEvent -> value.set(Math.sqrt(value.get())));
    }

    private void makePowerButton(Button button) {
        button.setOnAction(actionEvent -> {
            double poweredValue = Math.pow(value.get(), 2);
            value.set(poweredValue);
        });
    }

    private void makeCommaButton(Button button) {
        button.setOnAction(actionEvent -> value.set(value.get() + 0.));
    }


}
