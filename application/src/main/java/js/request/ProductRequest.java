package js.request;

import js.exceptions.ApplicationException;
import js.model.Product;

import java.util.Objects;

public record ProductRequest(String name,
                             String description,
                             double price) {

    public Product toModel() {
        try {
            return new Product(null, Objects.requireNonNull(name), Objects.requireNonNull(description), price);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
