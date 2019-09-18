package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

Page<Product> findByNameContaining(String partialName, Pageable pageable);

Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName, Integer minimumQuantity, Pageable pageable);


//*************
//nu prea folosim liste oricum dar asta e un exeplu mai complex
    //JPQL SYNTAX ( JAVA PERSISTANCE QUERY LANGUAGE )
   // @Query("SELECT product FROM Product product where name Like '%:partialName%'")
    @Query(value = "SELECT product FROM Product product where name Like '%?%'",
            nativeQuery= true)
    List<Product> findByPartialName(String partialName);
//*************

}
