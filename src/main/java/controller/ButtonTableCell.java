package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Consumer;

public class ButtonTableCell<S, T> extends TableCell<S, T> {

    private final Button button;

    public ButtonTableCell(String buttonText, Consumer<S> action) {
        this.button = new Button(buttonText);
        this.button.setOnAction(event -> {
            if (action != null) {
                action.accept(getTableView().getItems().get(getIndex()));
            }
        });
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(String buttonText, Consumer<S> action) {
        return param -> new ButtonTableCell<>(buttonText, action);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}