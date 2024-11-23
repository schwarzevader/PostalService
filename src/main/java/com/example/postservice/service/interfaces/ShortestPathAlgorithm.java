package com.example.postservice.service.interfaces;

import com.example.postservice.domain.PostOffice;

import java.util.List;

public interface ShortestPathAlgorithm {

    public  List<PostOffice> findShortestPath(Long startId, Long goalId) ;
}
