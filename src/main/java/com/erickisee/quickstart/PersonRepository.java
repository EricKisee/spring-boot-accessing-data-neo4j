package com.erickisee.quickstart;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Person findByName (String name);
    List<Person> findTeammatesName (String name);
    
}
