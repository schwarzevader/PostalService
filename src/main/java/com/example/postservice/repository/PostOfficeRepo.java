package com.example.postservice.repository;


import com.example.postservice.domain.PostOffice;
import com.example.postservice.domain.PostalCar;
import com.example.postservice.domain.RouteDistanceToOffice;
import com.example.postservice.util.utilTreeForPostOffice.ClassId;
import com.example.postservice.util.utilTreeForPostOffice.EntityGraphBuilder;
import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostOfficeRepo {
    @PersistenceContext
    private EntityManager entityManager;



//    https://vladmihalcea.com/hibernate-facts-multi-level-fetching/
    public PostOffice findPostOfficeByIdWithAllCarsAndRout(Long id){
        List<RouteDistanceToOffice> distanceToOfficeList =  this.entityManager
                .createQuery("select r " +
                        "from  routeDistances  r " +
                                "inner join r.postalCar c " +
                                "inner join c.postOffice p " +
                                "where p.id =:id"
                        ,RouteDistanceToOffice.class)
                .setParameter("id",id)
                .getResultList();

        return this.transformFromValueToCategory(distanceToOfficeList,id);
    }



    private  PostOffice transformFromValueToCategory(List<RouteDistanceToOffice> values , Long postOfficeId){
        EntityGraphBuilder entityGraphBuilder = new EntityGraphBuilder(
                new EntityVisitor[] {
                        RouteDistanceToOffice.ENTITY_VISITOR,
                        PostalCar.ENTITY_VISITOR,
                        PostOffice.ENTITY_VISITOR
                }
        ).build(values);

        ClassId<PostOffice> productTypeClassId = new ClassId<PostOffice>(
                PostOffice.class,
                postOfficeId
        );

        return  entityGraphBuilder.getEntityContext().getObject(
                productTypeClassId
        );
    }



    public PostOffice findPostOfficeById(Long id){
        return this.entityManager.find(PostOffice.class,id);
    }

}
