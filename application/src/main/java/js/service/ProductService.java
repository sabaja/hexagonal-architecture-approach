package js.service;

import js.model.Product;
import js.model.port.ProductPort;
import js.request.ProductRequest;
import js.response.ProductResponse;

import java.util.List;

public class ProductService {
    private final ProductPort productPort;

    public ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = productPort.save(productRequest.toModel());
        return mappingResponse(product);
    }

    public List<ProductResponse> find(
            int page,
            int sizePerPage,
            String sortField,
            String sortDirection
    ) {
        return productPort.findAll(page, sizePerPage, sortField, sortDirection)
                .stream()
                .map(this::mappingResponse)
                .toList();
    }

    private ProductResponse mappingResponse(Product product) {
        return new ProductResponse(product.id(), product.name(), product.description(), product.price());
    }
}