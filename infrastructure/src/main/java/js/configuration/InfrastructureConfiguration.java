package js.configuration;

import js.model.port.ProductPort;
import js.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfrastructureConfiguration {

    @Bean
    public ProductService productService(ProductPort productPort) {
        return new ProductService(productPort);
    }
}
