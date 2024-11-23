package com.example.postservice.repository;


import com.example.postservice.domain.PostOffice;
import com.example.postservice.domain.PostalCar;
import com.example.postservice.domain.RouteDistanceToOffice;
import com.example.postservice.service.interfaces.DistanceCalculatorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.PriorityQueue;

@Repository
public class RouteDistanceToOfficeRepoImp {

    private final DistanceCalculatorService distanceCalculatorService;

    @PersistenceContext
    private EntityManager entityManager;

    public RouteDistanceToOfficeRepoImp(@Qualifier(value = "distanceCalculatorServiceImp") DistanceCalculatorService distanceCalculatorService) {
        this.distanceCalculatorService = distanceCalculatorService;
    }


    public void save(PostalCar postalCar, PostOffice postOffice) {
        entityManager.persist(
                new RouteDistanceToOffice(
                        distanceCalculatorService.calculateDistance
                                (postalCar.getPostOffice().getLatitude()
                                        , postalCar.getPostOffice().getLongitude(),
                                        postOffice.getLatitude(), postOffice.getLongitude()
                                )
                        , postOffice
                        , postalCar
                )
        );
    }

    public void deleteRouteByCarId(Long id) {
        this.entityManager.createQuery(
                        "delete  from routeDistances r " +
                                "where r.postalCar.id = :id")
                .setParameter("id", id)
                .executeUpdate();

    }

    public List<RouteDistanceToOffice> getRouteDistanceToOfficesByCarId(List<Long> id){
         return  this.entityManager
                .createQuery("select r from routeDistances r " +
                "join  r.postalCar c " +
                        "where c.id in :id").setParameter("id",id).getResultList();
    }


}
