package com.erickisee.quickstart;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class QuickstartApplication {
  
	private final static Logger log = LoggerFactory.getLogger(QuickstartApplication.class);
	
	public static void main(String[] args) {
	SpringApplication.run(QuickstartApplication.class, args);
	}
	
	@Bean
	CommandLineRunner demo (PersonRepository personRepository){
		return  args -> {
			personRepository.deleteAll();

			Person greg = new Person ("Greg");
			Person roy = new Person ("roy");
			Person craig = new Person ("craig");

			List <Person> team = Arrays.asList(greg, roy, craig);

			log.info("Before linking up with Neo4j.....");

			team.stream().forEach(person -> log.info("\t"+personRepository.findByName(person.getName()).toString()));

			personRepository.save(greg);
			personRepository.save(roy);
			personRepository.save(craig);

			greg = personRepository.findByName(greg.getName());
			greg.worksWith(roy);
			greg.worksWith(craig);
			personRepository.save(greg);

			roy = personRepository.findByName(roy.getName());
			roy.worksWith(craig);
			personRepository.save(roy);

			log.info("Look up each person by name....");
			team.stream().forEach(person -> log.info("\t"+personRepository.findByName(person.getName()).toString()));

			List <Person> teammates = personRepository.findByTeammatesName (greg.getName());
			log.info("the following have greg as a teammate....");
			teammates.stream().forEach(person->log.info("\t"+person.getName()));
		};
	}
  
}

