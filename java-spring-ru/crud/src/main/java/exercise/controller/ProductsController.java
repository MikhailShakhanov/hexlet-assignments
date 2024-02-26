package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    //GET /products – просмотр списка всех товаров
    //GET /products/{id} – просмотр конкретного товара
    //POST /products – добавление нового товара. При указании id несуществующей категории должен вернуться ответ с кодом 400 Bad request
    //PUT /products/{id} – редактирование товара. При редактировании мы должны иметь возможность поменять название, цену и категорию товара. При указании id несуществующей категории также должен вернуться ответ с кодом 400 Bad request
    //DELETE /products/{id} – удаление товара

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> showProducts() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO showProduct(@PathVariable long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        return productMapper.map(product);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
//        var product = productMapper.map(productCreateDTO);
//        var category = categoryRepository.findById(productCreateDTO.getCategoryId())
//                .orElseThrow(() -> new BadRequestException("Category not found"));
//
//        product.setCategory(category);
//        productRepository.save(product);
//        return productMapper.map(product);
        var product = productMapper.map(productCreateDTO);
        productRepository.save(product);
        var productDto = productMapper.map(product);
        return productDto;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@PathVariable long id, @RequestBody @Valid ProductUpdateDTO productUpdateDTO) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        productMapper.update(productUpdateDTO, product);
        var category = categoryRepository.findById(productUpdateDTO.getCategoryId().get())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.map(product);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
        productRepository.deleteById(id);
    }
    // END
}
