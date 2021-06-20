package services;

import entity.Product;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;
import repository.UserRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public void deleteLink(Long cartId){
        productRepository.deleteLink(cartId);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){productRepository.deleteById(id);}
}
