package com.sdc.navigation.backend;

import com.sdc.navigation.backend.util.DBHelper;
import com.sdc.navigation.backend.domain.ServiceStation;
import com.sdc.navigation.backend.repository.ServiceStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
@ImportResource(locations = {"app-context.xml"})
@EnableJSONDoc
@Slf4j
public class BackendApplication implements CommandLineRunner {

	@Autowired
	private ServiceStationRepository repository;

	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll();
		log.info("Remove all data from collection");
		List<ServiceStation> collection = DBHelper.getCollections();
		repository.insert(collection);
		log.info("Inserts {} objects", collection.size());
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
