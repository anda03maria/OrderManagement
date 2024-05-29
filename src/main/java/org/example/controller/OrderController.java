package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.model.Agent;
import org.example.model.Product;
import org.example.service.AgentService;
import org.example.service.ServiceException;

import java.io.IOException;

public class OrderController {

    private ObservableList<String> paymentOptions = FXCollections.observableArrayList("VISA", "APPLE_PAY", "MASTERCARD", "PAYPAL");
    private ObservableList<String> courierOptions = FXCollections.observableArrayList("DHL", "FEDEX", "UPS");

    private Product product;
    private Agent agent;
    private AgentService service;

    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private TextField addressTF;
    @FXML
    private ComboBox<String> paymentCB;
    @FXML
    private ComboBox<String> courierCB;
    @FXML
    private Label finalPriceLabel;
    @FXML
    private TextField clientNameTF;
    @FXML
    private TextField clientEmailTF;


    
    @FXML
    public void initialize() {
        paymentCB.setItems(paymentOptions);
        courierCB.setItems(courierOptions);
    }

    public OrderController(Agent agent, Product product, AgentService agentService) {
        this.agent = agent;
        this.product = product;
        this.service = agentService;
    }

    @FXML
    public void confirmOrder(ActionEvent actionEvent) throws IOException {
        String clientName = clientNameTF.getText();
        String clientEmail = clientEmailTF.getText();

        int quantity = quantitySpinner.getValue();
        String address = addressTF.getText();
        String paymentMethod = paymentCB.getValue();
        String courierType = courierCB.getValue();

        if (clientName.equals("") || clientEmail.equals("") || address.equals("") || paymentMethod == null || courierType == null) {
            new Alert(Alert.AlertType.ERROR, "Invalid input").show();
            return;
        }
        try {
            service.orderProduct(agent.getId(), product.getId(), quantity, clientName, clientEmail, paymentMethod, courierType);
            new Alert(Alert.AlertType.CONFIRMATION, "Order confirmed!").show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (ServiceException exception) {
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }

    @FXML
    public void updatePrice(MouseEvent mouseEvent) {
        int quantity = quantitySpinner.getValue();
        if (quantity > product.getQuantity()) {
            finalPriceLabel.setText("Not enough stock!");
            finalPriceLabel.setTextFill(Color.RED);
        } else {
            finalPriceLabel.setTextFill(Color.WHITE);
            double totalPrice = product.getPrice() * quantity;
            finalPriceLabel.setText(String.format("%f", totalPrice));
        }
    }

    @FXML
    public void cancelOrder(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
