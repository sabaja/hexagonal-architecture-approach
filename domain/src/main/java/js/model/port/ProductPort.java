package js.model.port;

import js.model.Product;

import java.util.List;

public interface ProductPort {
    Product save(Product product);

    List<Product> findAll(
            int page,
            int sizePerPage,
            String sortField,
            String sortDirection
    );
}
