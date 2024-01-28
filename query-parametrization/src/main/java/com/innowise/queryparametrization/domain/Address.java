package com.innowise.queryparametrization.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.UUID;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = PRIVATE)
public class Address {

    @Id
    @GeneratedValue(strategy = UUID)
    String id;

    String name;
}
