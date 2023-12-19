package com.innowise.queryparametrization.domain;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = PRIVATE)
public class Product {

  @Id
  @GeneratedValue(strategy = UUID)
  String id;

  String name;

  String type;

  @ManyToMany(cascade = PERSIST)
  @JoinTable(
      name = "products_categories",
      joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
  )
  Set<Category> category;

  BigDecimal price;

  @CreationTimestamp
  LocalDateTime createdAt;

  @UpdateTimestamp
  LocalDateTime updatedAt;
}
