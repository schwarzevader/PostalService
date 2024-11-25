package com.example.postservice.service.imp;

import com.example.postservice.domain.PostOffice;
import com.example.postservice.repository.PostOfficeRepo;
import com.example.postservice.service.interfaces.DistanceCalculatorService;
import com.example.postservice.service.interfaces.ShortestPathAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.postservice.domain.RouteDistanceToOffice;
import java.util.*;


@Service("aStarAlgorithm")
@Slf4j
public class AStarAlgorithm implements ShortestPathAlgorithm {


    private DistanceCalculatorService distanceCalculatorService;

    private PostOfficeRepo postOfficeRepo;
    @Autowired
    public AStarAlgorithm(@Qualifier(value = "distanceCalculatorServiceImp") DistanceCalculatorService distanceCalculatorService ,PostOfficeRepo postOfficeRepo) {
        this.distanceCalculatorService = distanceCalculatorService;
        this.postOfficeRepo=postOfficeRepo;
    }

    public  List<PostOffice> findShortestPath(Long startId, Long goalId) {

        PostOffice start = this.postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(startId);
        PostOffice goal =this.postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(goalId);

        // Open set for exploration
        PriorityQueue<PostOffice> openSet = new PriorityQueue<>(Comparator.comparingDouble(po -> po.getDistance() + po.getHeuristic()));
        // Closed set for already visited nodes
        Set<PostOffice> closedSet = new HashSet<>();

        // Initialize the start node
        start.setDistance(0);
        start.setHeuristic(calculateHeuristic(start, goal));
        openSet.add(start);

        // Store the path
        Map<PostOffice, PostOffice> cameFrom = new HashMap<>();

        while (!openSet.isEmpty()) {
            PostOffice current = this.postOfficeRepo.findPostOfficeByIdWithAllCarsAndRout(openSet.poll().getId());


            // If the goal is reached, reconstruct the path
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            // Explore neighbors
            for (RouteDistanceToOffice edge :current.getPostalCars().stream().flatMap(car -> car.getRouterDirectoryAndDistance().stream()).toList() ) {

                PostOffice neighbor = edge.getToPostOffice();


                if (closedSet.contains(neighbor)) continue;

                double tentativeGScore = current.getDistance() + edge.getDistance();


                if (!openSet.contains(neighbor) || tentativeGScore < neighbor.getDistance()) {
                    cameFrom.put(neighbor, current);
                    neighbor.setDistance(tentativeGScore);
                    neighbor.setHeuristic(calculateHeuristic(neighbor, goal));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        // Return an empty path if no path is found
        return new ArrayList<>();
    }

    private  double calculateHeuristic(PostOffice from, PostOffice to) {
        return this.distanceCalculatorService.calculateDistance(from.getLatitude(),from.getLongitude(),to.getLatitude(),to.getLongitude());
    }

    private static List<PostOffice> reconstructPath(Map<PostOffice, PostOffice> cameFrom, PostOffice current) {
        log.info("reconstructPath");
        List<PostOffice> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
