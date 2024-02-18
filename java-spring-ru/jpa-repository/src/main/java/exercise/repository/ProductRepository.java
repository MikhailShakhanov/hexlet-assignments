package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    @Query("select p from Product p where p.price>=:min and p.price<=:max")
    List<Product> findAllProductsInPriceRange(@Param("min") Integer min,@Param("max") Integer max, Sort sort);

    List<Product> findByPriceLessThanEqual(Integer price, Sort sort);
    List<Product> findByPriceGreaterThanEqualOrderByPriceAsc(Integer price);

    List<Product> findByPriceBetween(Integer min, Integer max, Sort sort);
    // END
}
