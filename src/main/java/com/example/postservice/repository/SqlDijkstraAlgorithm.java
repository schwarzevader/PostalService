package com.example.postservice.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SqlDijkstraAlgorithm {


    @PersistenceContext
    private EntityManager entityManager;

    public void sqlDijkstraAlgorithm(Object from ,Object to){
        
    }




}
