package org.example.service;

import org.example.model.Agent;
import org.example.model.Order;
import org.example.model.Product;
import org.example.repository.AgentsRepository;
import org.example.repository.OrdersRepository;
import org.example.repository.ProductsRepository;
import org.example.utils.Observable;
import org.example.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class AgentService implements Observable {

    private List<Observer> observers;

    private AgentsRepository agentsRepository;
    private ProductsRepository productsRepository;
    private OrdersRepository ordersRepository;

    public AgentService(AgentsRepository agentsRepository, ProductsRepository productsRepository, OrdersRepository ordersRepository) {
        this.agentsRepository = agentsRepository;
        this.productsRepository = productsRepository;
        this.ordersRepository = ordersRepository;

        observers = new ArrayList<>();
    }

    //AGENT OPERATIONS
    public void login(String userName, String password) throws ServiceException {
        Agent agent = agentsRepository.findById(userName);
        if (agent == null || !agent.getPassword().equals(password))
            throw new ServiceException("Invalid credentials");
    }

    //PRODUCTS OPERATIONS
    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    //PLACE ORDER
    public void orderProduct(String agentId, int productId, int quantity, String clientName, String clientEmail, String paymentMethod, String courierType) throws ServiceException {
        Product product = productsRepository.findById(productId);
        if (product.getQuantity() < quantity) {
            throw new ServiceException("Not enough stock");
        }
        Order order = new Order(agentId, productId, quantity, clientName, clientEmail, paymentMethod, courierType);
        try {
            ordersRepository.save(order);
            product.setQuantity(product.getQuantity() - quantity);
            productsRepository.update(product, productId);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
