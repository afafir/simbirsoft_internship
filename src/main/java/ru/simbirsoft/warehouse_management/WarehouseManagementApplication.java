package ru.simbirsoft.warehouse_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = "ru.simbirsoft.warehouse_management")
@PropertySource("classpath:url.properties")
@EnableJpaRepositories
@EnableSwagger2
public class WarehouseManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(WarehouseManagementApplication.class, args);
  }
}
