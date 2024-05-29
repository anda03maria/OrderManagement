package org.example.service;

import org.example.model.Admin;
import org.example.model.Agent;
import org.example.model.Product;
import org.example.repository.AdminsRepository;
import org.example.repository.AgentsRepository;
import org.example.repository.ProductsRepository;

import java.util.List;

public class AdminService {

    private AdminsRepository adminsRepository;
    private AgentsRepository agentsRepository;
    private ProductsRepository productsRepository;

    public AdminService(AdminsRepository adminsRepository, AgentsRepository agentsRepository, ProductsRepository productsRepository) {
        this.adminsRepository = adminsRepository;
        this.agentsRepository = agentsRepository;
        this.productsRepository = productsRepository;
    }

    //ADMIN OPERATION-----------------------------------------------------------------------------
    public void login(String userName, String password) throws ServiceException {
        Admin admin = adminsRepository.findById(userName);
        if (admin == null || !admin.getPassword().equals(password))
            throw new ServiceException("Invalid credentials");
    }

    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    //AGENT OPERATIONS----------------------------------------------------------------------------
    public void addAgent(String id, String name, String email, String password) throws ServiceException{
        Agent agent = new Agent(id, name, email, password);
        boolean saved = agentsRepository.save(agent);
        if(!saved){
            throw new ServiceException("Failed to save agent");
        }
    }

    public void updateAgent(String agentId, String newName, String newEmail, String newPassword) throws ServiceException{
        Agent agentToUpdate = agentsRepository.findById(agentId);
        if (agentToUpdate == null) {
            throw new ServiceException("Agent not found");
        }
        agentToUpdate.setName(newName);
        agentToUpdate.setEmail(newEmail);
        agentToUpdate.setPassword(newPassword);
        boolean updated = agentsRepository.update(agentToUpdate, agentId);
        if (!updated) {
            throw new ServiceException("Failed to update agent");
        }
    }

    public void deleteAgent(String agentId) throws ServiceException{
        Agent agentToDelete = agentsRepository.findById(agentId);
        if (agentToDelete == null) {
            throw new ServiceException("Agent not found");
        }

        Agent deletedAgent = agentsRepository.delete(agentId);
        if (deletedAgent == null) {
            throw new ServiceException("Failed to delete agent");
        }
    }

    //PRODUCTS OPERATIONS------------------------------------------------------------------
    public void addProduct(String name, String description, Double price, Integer quantity) throws ServiceException{
        Product product = new Product(price, name, description, quantity);
        boolean saved = productsRepository.save(product);
        if(!saved){
            throw new ServiceException("Failed to save product");
        }
    };

    public void updateProduct(String name, String newDescription, Double newPrice, Integer newQuantity) throws ServiceException{
        Product productToUpdate = productsRepository.findAll().stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst()
                .orElse(null);
        if (productToUpdate == null) {
            throw new ServiceException("Product not found");
        }
        productToUpdate.setDescription(newDescription);
        productToUpdate.setPrice(newPrice);
        productToUpdate.setQuantity(newQuantity);
        boolean updated = productsRepository.update(productToUpdate, productToUpdate.getId());
        if (!updated) {
            throw new ServiceException("Failed to update product");
        }
    }

    public void deleteProduct(String name) throws ServiceException{
        Product productToDelete = productsRepository.findAll().stream()
                .filter(product -> name.equals(product.getName()))
                .findFirst()
                .orElse(null);
        if (productToDelete == null) {
            throw new ServiceException("Product not found");
        }

        Product productDeleted = productsRepository.delete(productToDelete.getId());
        if (productDeleted == null) {
            throw new ServiceException("Failed to delete product");
        }
    }

}
