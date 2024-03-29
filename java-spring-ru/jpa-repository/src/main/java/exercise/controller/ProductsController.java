package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "/my")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> showProductsInPriceRange(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        var sort = Sort.by(Sort.Order.asc("price"));
        if (min != null && max != null) {
            return productRepository.findAllProductsInPriceRange(min, max, sort).stream().toList();
        } else if (min == null && max != null) {
            return productRepository.findByPriceLessThanEqual(max, sort).stream().toList();
        }else if (min != null) {
            return productRepository.findByPriceGreaterThanEqualOrderByPriceAsc(min).stream().toList();
        }
        return productRepository.findAll(sort).stream().toList();
    }

    @GetMapping(path = "")
    public List<Product> index(
            @RequestParam(defaultValue = Integer.MIN_VALUE + "") Integer min,
            @RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer max) {

        Sort sort = Sort.by(Sort.Order.asc("price"));
        return productRepository.findByPriceBetween(min, max, sort);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
