package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Product;
import org.example.service.AdminService;
import org.example.service.ServiceException;
import org.example.utils.Observer;

import java.io.IOException;
import java.util.List;

public class AdminController implements Observer {


    private AdminService service;

    private ObservableList<Product> productObservableList = FXCollections.observableArrayList();

    @FXML
    TextField idTF;

    @FXML
    TextField passwordTF;

    @FXML
    TextField nameTF;

    @FXML
    TextField emailTF;

    @FXML
    TextField nameProductTF;

    @FXML
    TextField descriptionTF;

    @FXML
    TextField priceTF;

    @FXML
    Spinner<Integer> quantityS;

    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> columnProductName;
    @FXML
    private TableColumn<Product, Double> columnProductPrice;
    @FXML
    private TableColumn<Product, String> columnProductDescription;
    @FXML
    private TableColumn<Product, Integer> columnProductQuantity;


    public AdminController(AdminService adminService) {
        this.service = adminService;
    }

    public void initialize() {
        setUpProductsTable();
        loadProducts();
    }

    private void setUpProductsTable() {
        productsTable.setItems(productObservableList);
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnProductDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnProductQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productsTable.getColumns().setAll(List.of(columnProductName, columnProductPrice, columnProductDescription, columnProductQuantity));
    }

    private void loadProducts() {
        productObservableList.setAll(service.getProducts());
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addAgent(ActionEvent actionEvent) throws IOException{
        String id = idTF.getText();
        String name = nameTF.getText();
        String email = emailTF.getText();
        String password = passwordTF.getText();
        try {
            service.addAgent(id, name, email, password);
            showMessage("Agent added successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    public void updateAgent(ActionEvent actionEvent) throws IOException{
        String id = idTF.getText();
        String newName = nameTF.getText();
        String newEmail = emailTF.getText();
        String newPassword = passwordTF.getText();
        try {
            service.updateAgent(id, newName, newEmail, newPassword);
            showMessage("Agent updated successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void deleteAgent(ActionEvent actionEvent) throws IOException {
        String id = idTF.getText();
        try {
            service.deleteAgent(id);
            showMessage("Agent deleted successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(ActionEvent actionEvent) throws IOException{
        String name = nameProductTF.getText();
        String description = descriptionTF.getText();
        Double price = Double.valueOf(priceTF.getText());
        Integer quantity = quantityS.getValue();
        try {
            service.addProduct(name, description, price, quantity);
            productObservableList.add(new Product(price, name, description, quantity));
            showMessage("Product added successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(ActionEvent actionEvent) throws IOException{
        String name = nameProductTF.getText();
        String description = descriptionTF.getText();
        Double price = Double.valueOf(priceTF.getText());
        Integer quantity = quantityS.getValue();
        try {
            service.updateProduct(name, description, price, quantity);
            int index = findProductIndexByName(name);
            if (index >= 0) {
                productObservableList.set(index, new Product(price, name, description, quantity));
            }            showMessage("Product updated successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException{
        String name = nameProductTF.getText();
        try {
            service.deleteProduct(name);
            int index = findProductIndexByName(name);
            if (index >= 0) {
                productObservableList.remove(index);
            }
            showMessage("Product deleted successfully");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private int findProductIndexByName(String name) {
        for (int i = 0; i < productObservableList.size(); i++) {
            if (productObservableList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        loadProducts();
    }
}
