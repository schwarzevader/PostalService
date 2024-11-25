package com.example.postservice;

import com.example.postservice.domain.PostOffice;
import com.example.postservice.repository.PostOfficeRepo;
import com.example.postservice.repository.RouteDistanceToOfficeRepoImp;
import com.example.postservice.service.interfaces.ShortestPathAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostServiceApplication implements CommandLineRunner {

    @Autowired
    PostOfficeRepo postOfficeRepo;

    @Autowired
    @Qualifier(value = "aStarAlgorithm")
    ShortestPathAlgorithm shortestPathAlgorithm;

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);

    }

    @Override
    public void run(String[] args) {

//        shortestPathAlgorithm.findShortestPath(1000L,1017L);
//        System.out.println("size of path============"+shortestPathAlgorithm.findShortestPath(1000L,1017L).size());
        shortestPathAlgorithm.findShortestPath(1000L,1017L).forEach(e-> System.out.println(e.getId()));
//        System.out.println("id = "+postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(1000L).getId());
//        System.out.println(postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(1000L).getPostalCars().toString());
//        System.out.println(postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(1000L)
//                .getPostalCars().stream().flatMap(car -> car.getRouterDirectoryAndDistance().stream()).toList().toString());


    }
}
