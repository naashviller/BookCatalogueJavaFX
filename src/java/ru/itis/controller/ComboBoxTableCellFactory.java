package ru.itis.controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;

public class ComboBoxTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

  private ObservableList<T> nodes;

  public void setNodes(ObservableList<T> nodes) {
    this.nodes = nodes;
  }

  public ObservableList<T> getNodes() {
    return nodes;
  }

  @Override
  public TableCell<S, T> call(TableColumn<S, T> param) {
    return nodes != null ? new ComboBoxTableCell<>(nodes) : new ComboBoxTableCell<>();
  }
}
