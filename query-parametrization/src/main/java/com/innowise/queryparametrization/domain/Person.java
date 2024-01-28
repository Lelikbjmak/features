package com.innowise.queryparametrization.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@BatchSize(size = 25)
@FieldDefaults(level = PRIVATE)
public class Person {

    @Id
    @GeneratedValue(strategy = UUID)
    String id;

    String name;

    String type;

    @ManyToMany(cascade = PERSIST)
    @JoinTable(
            name = "persons_addresses",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id")
    )
    Set<Address> address;

    BigDecimal price;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
