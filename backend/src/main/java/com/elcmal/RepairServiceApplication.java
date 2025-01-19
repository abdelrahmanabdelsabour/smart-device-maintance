package com.elcmal;

import com.elcmal.model.OrderItem;
import com.elcmal.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RepairServiceApplication   {
    public static void main(String[] args) {
        SpringApplication.run(RepairServiceApplication.class, args);
    }


}
