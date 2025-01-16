package com.innowise.graphql.testgraphql.repository;

import com.innowise.graphql.testgraphql.entity.Category;
import com.innowise.graphql.testgraphql.entity.Product;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

  List<Product> findByCategory(Category category);
}
