package repository;

import entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from orders_cart where cart_id = :cartId", nativeQuery = true)
    int deleteLink(@Param("cartId")Long cartId);
}
