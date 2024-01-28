package com.innowise.queryparametrization.service.impl;

import com.innowise.crudlib.service.AbstractGenericCrudService;
import com.innowise.queryparametrization.domain.Person;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.repository.PersonRepository;
import com.innowise.queryparametrization.repository.ProductRepository;
import com.innowise.queryparametrization.service.PersonService;
import com.innowise.queryparametrization.service.ProductService;
import com.innowise.queryparametrization.specification_context_revision.SpecificationContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonServiceImpl extends
    AbstractGenericCrudService<Person, String, PersonRepository> implements PersonService {

  @Autowired
  SpecificationContext<Person> specificationContext;

  public PersonServiceImpl(PersonRepository repository) {
    super(repository);
//      this.specificationContext = specificationContext;
  }

}
