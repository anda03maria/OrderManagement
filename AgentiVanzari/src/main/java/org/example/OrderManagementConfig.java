package org.example;


import org.example.repository.AdminsRepository;
import org.example.repository.AgentsRepository;
import org.example.repository.OrdersRepository;
import org.example.repository.ProductsRepository;
import org.example.repository.hibernate.AdminsDbOrmRepository;
import org.example.repository.hibernate.AgentsDbOrmRepository;
import org.example.repository.hibernate.OrdersDbOrmRepository;
import org.example.repository.hibernate.ProductsDbOrmRepository;
import org.example.service.AdminService;
import org.example.service.AgentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderManagementConfig {

    @Bean
    AdminsRepository adminsRepository() {
        return new AdminsDbOrmRepository();
    }

    @Bean
    AgentsRepository agentsRepository() {
        return new AgentsDbOrmRepository();
    }

    @Bean
    OrdersRepository ordersRepository() {
        return new OrdersDbOrmRepository();
    }

    @Bean
    ProductsRepository productsRepository() {
        return new ProductsDbOrmRepository();
    }

    @Bean
    AgentService agentService() {
        return new AgentService(
                agentsRepository(),
                productsRepository(),
                ordersRepository());
    }

    @Bean
    AdminService adminService() {
        return new AdminService(
                adminsRepository(),
                agentsRepository(),
                productsRepository()
        );
    }

}
