package com.fsAdmin.config.filter;


import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(3)  // This ensures it runs early
public class FilterChainInspector implements CommandLineRunner {


    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        String[] filterNames = context.getBeanNamesForType(Filter.class);

        System.out.println();
        System.out.println("Filters in SpringApplicationFilterChain:");
        for (String filterName : filterNames) {
            System.out.println(filterName + " -> " + context.getBean(filterName).getClass().getName());
        }
        System.out.println();
    }
}
