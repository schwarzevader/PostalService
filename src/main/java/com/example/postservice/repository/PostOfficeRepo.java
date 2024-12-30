package com.example.postservice.repository;


import com.example.postservice.domain.PostOffice;
import com.example.postservice.domain.PostalCar;
import com.example.postservice.domain.RouteDistanceToOffice;
import com.example.postservice.dto.PostOfficeDto;
import com.example.postservice.dto.PostalCarDto;
import com.example.postservice.util.utilTreeForPostOffice.ClassId;
import com.example.postservice.util.utilTreeForPostOffice.EntityGraphBuilder;
import com.example.postservice.util.utilTreeForPostOffice.EntityVisitor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostOfficeRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private RouteDistanceToOfficeRepoImp routeDistanceToOfficeRepoImp;

    @Autowired
    public PostOfficeRepo(RouteDistanceToOfficeRepoImp routeDistanceToOfficeRepoImp) {
        this.routeDistanceToOfficeRepoImp = routeDistanceToOfficeRepoImp;
    }


    //    @Cacheable(value = "products", key = "#id")
//    @CachePut(value = "products", key = "#product.id")
//    @CacheEvict(value = "products", key = "#id")
//    public void deleteProduct(Long id) {





    //    https://vladmihalcea.com/hibernate-facts-multi-level-fetching/
    public PostOffice findPostOfficeByIdWithAllCarsAndRout(Long id){
        List<RouteDistanceToOffice> distanceToOfficeList =  this.entityManager
                .createQuery("select r " +
                        "from  routeDistances  r " +
                                "inner join fetch r.toPostOffice tpo " +
                                "inner join fetch tpo.postalCars pc " +
                                "inner join fetch r.postalCar c " +
                                "inner join fetch c.postOffice p " +
                                "where p.id =:id"
                        ,RouteDistanceToOffice.class)
                .setParameter("id",id)
                .getResultList();
        return this.transformFromValueToCategory(distanceToOfficeList,id);
//        return this.transformFromValueToCategory(routeDistanceToOfficeRepoImp.getRoutDistanceToOfficeByOfficeId(id),id);
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

//    public List<CategoriesAndSpecsDto> getAllCategories(boolean cacheable) {
//        stopWatch.start();
//        Map<Long, Specs> specsMap = new HashMap<>();
//        Map<Long, CategoriesAndSpecsDto> categoriesMap = new HashMap<>();
//        this.entityManager.createQuery("select " +
//                        "c.id," +
//                        "c.nameOfCategory," +
//                        "sn.id," +
//                        "sn.name," +
//                        "sv.id," +
//                        "sv.value " +
//                        "from product_category c " +
//                        "join c.productSpecNames sn " +
//                        "join sn.productSpecValues sv")
//                .unwrap(Query.class)
////                .setMaxResults(1000)
//                .setHint(QueryHints.HINT_CACHEABLE, cacheable)
//                .setTupleTransformer((tuples, aliases) -> {
//                    Long categoryId = (Long) tuples[0];
//                    Long specId = (Long) tuples[2];
//
//                    Specs spec = specsMap.computeIfAbsent(specId, k -> new Specs(specId, (String) tuples[3]));
//                    spec.getProductSpecValues().add(new SpecValue((Long) tuples[4], (String) tuples[5], specId));
//                    CategoriesAndSpecsDto category =
//                            categoriesMap.computeIfAbsent(categoryId, k -> new CategoriesAndSpecsDto(categoryId, (String) tuples[1]));
//                    if (!category.getSpecsList().contains(spec)) {
//                        category.getSpecsList().add(spec);
//                    }
//                    /////////////////////////////////////
//
//                    //                    categoriesMap.computeIfAbsent(categoryId,k->
////                                    new CategoriesAndSpecsDto(categoryId,(String) tuples[1]))
////                            .getSpecsMap().putIfAbsent(spec.getId(),spec);
//                    return null;
//                }).getResultList();
//
////        categoriesMap.values().forEach(c->{
////            c.setSpecsList(new ArrayList<>(c.getSpecsMap().values()));
////        });
//
//        stopWatch.stop();
//        System.out.println("time=" + stopWatch.getTotalTimeMillis());
//
//        return new ArrayList<>(categoriesMap.values());
//    }

    public PostOfficeDto findPostOfficeDtoByIdWithAllCarsAndRout(Long id){
        Map<Long, PostalCarDto> specsMap = new HashMap<>();
        Map<Long,PostOfficeDto> categoriesMap = new HashMap<>();
        this.entityManager
                .createQuery("select r.id,r.distance,r.toPostOffice,r.postalCar,pc.id, " +
                                "from  routeDistances  r " +
                                "inner join fetch r.toPostOffice tpo " +
                                "inner join fetch tpo.postalCars pc " +
                                "inner join fetch r.postalCar c " +
                                "inner join fetch c.postOffice p " +
                                "where p.id =:id"
                        )
                .unwrap(Query.class)
                .setTupleTransformer((tuples, aliases) -> {

                    return null;
                })
                .setParameter("id",id)
                .getResultList();

        return null;
    }

}
