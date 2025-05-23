package com.example.oopjproj;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class pie extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter data for the pie chart:");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-font-size: 14px;");

        TextField dataInput = new TextField();
        dataInput.setStyle("-fx-font-size: 14px;");

        Button addButton = new Button("Add Data");
        addButton.setStyle("-fx-font-size: 14px; -fx-background-color: #2DD881; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);");
        PieChart pieChart = new PieChart();

        TableView<ItemData> tableView = new TableView<>();

        TableColumn<ItemData, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());

        TableColumn<ItemData, Number> itemValueCol = new TableColumn<>("Value");
        itemValueCol.setCellValueFactory(cellData -> cellData.getValue().itemValueProperty());

        tableView.getColumns().addAll(itemNameCol, itemValueCol);


        ObservableList<ItemData> itemDataList = FXCollections.observableArrayList();

        addButton.setOnAction(event -> {
            try {
                String itemName = nameInput.getText();
                double dataValue = Double.parseDouble(dataInput.getText());
                ItemData itemData = new ItemData(itemName, dataValue);
                itemDataList.add(itemData);


                tableView.setItems(itemDataList);

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                double totalValue = 0.0;
                for (ItemData item : itemDataList) {
                    totalValue += item.getItemValue();
                }
                for (ItemData item : itemDataList) {
                    double percentage = (item.getItemValue() / totalValue) * 100;
                    pieChartData.add(new PieChart.Data(item.getItemName() + " (" + String.format("%.2f", percentage) + "%)", item.getItemValue()));
                }
                pieChart.setData(pieChartData);

                nameInput.clear();
                dataInput.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        });

        VBox root = new VBox(10, label, new HBox(10, new Label("Item Name:"), nameInput),
                new HBox(10, new Label("Value:"), dataInput), addButton, tableView, pieChart);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #f4f4f4;-fx-font-family: Cambria");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("PIE CHART GENERATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class ItemData {
        private final SimpleStringProperty itemName;
        private final SimpleDoubleProperty itemValue;

        public ItemData(String itemName, double itemValue) {
            this.itemName = new SimpleStringProperty(itemName);
            this.itemValue = new SimpleDoubleProperty(itemValue);
        }

        public String getItemName() {
            return itemName.get();
        }

        public SimpleStringProperty itemNameProperty() {
            return itemName;
        }

        public double getItemValue() {
            return itemValue.get();
        }

        public SimpleDoubleProperty itemValueProperty() {
            return itemValue;
        }
    }
}
