package com.innowise.graphql.testgraphql.entity;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "products")
public class Product {

  @Id
  String id;

  String name;

  String description;

  Category category;

  @Field(type = FieldType.Nested, includeInParent = true)
  ProductDetails productDetails;

  @Field(type = FieldType.Nested, includeInParent = true)
  List<Property> properties;
}
