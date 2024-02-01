package com.innowise.queryparametrization.controller;

import com.innowise.queryparametrization.specification.SpecificationService;
import com.innowise.queryparametrization.dto.PersonDto;
import com.innowise.queryparametrization.mapper.PersonMapper;
import com.innowise.queryparametrization.service.PersonService;
import com.innowise.queryparametrization.specification_context_revision.annotation.EnableDynamicSpecification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/persons")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PersonController {

    SpecificationService specificationService;

    PersonService personService;

    PersonMapper personMapper;

    @GetMapping
    @EnableDynamicSpecification
    public Page<PersonDto> findAllWithFilter(@RequestParam MultiValueMap<String, Object> filters,
                                             Pageable pageable) {
//    Specification<Person> spec = specificationService.generateSpecification(filters, Person.class);

        return personService.findPage(null, pageable).map(personMapper::toDto);
    }
}
