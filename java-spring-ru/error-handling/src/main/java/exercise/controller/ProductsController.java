package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // BEGIN
    //GET /products/{id} — просмотр конкретного товара
    //PUT /products/{id} — обновление данных товара
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product showProduct(@PathVariable Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id "+id+" not found"));
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        var prod = productRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product with id "+id+" not found"));

        var product2 = productRepository.findById(id).get();
        product2.setPrice(product.getPrice());
        product2.setTitle(product.getTitle());

        productRepository.save(product2);
        return product2;
    }
    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
