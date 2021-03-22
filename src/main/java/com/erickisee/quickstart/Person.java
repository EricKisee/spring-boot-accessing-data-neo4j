package com.erickisee.quickstart;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Person {

    @Id @GeneratedValue private long id;

    private String name ;

    private Person(){}

    public Person (String name ){
        this.name = name;
    }

    @Relationship (type = "TEAMMATE")
    public Set <Person> teammates;

    public void worksWith (Person person){
        if(teammates==null)
            teammates = new HashSet<>();
        teammates.add(person);
    }

    public String toString (){
        return this.name+"'s teammates =>"+Optional.ofNullable(this.teammates)
        .orElse(Collections.emptySet())
        .stream()
        .map(Person::getName)
        .collect(Collectors.toList());
    }

    public String getName (){return this.name;}

    public void setName (String name){ this.name = name;}

}
