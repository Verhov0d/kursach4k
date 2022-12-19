package com.example.bigsevaup.Repository;
import com.example.bigsevaup.Model.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByNameproductContaining(String nameproduct);
    long countBykategoryProduct_Id(long id);
}