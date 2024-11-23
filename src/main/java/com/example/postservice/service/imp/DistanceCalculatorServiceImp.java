package com.example.postservice.service.imp;

import com.example.postservice.service.interfaces.DistanceCalculatorService;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.TurnCostsConfig;
import com.graphhopper.util.shapes.GHPoint;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "distanceCalculatorServiceImp")
public class DistanceCalculatorServiceImp implements DistanceCalculatorService {

//    @Value("${graphhopper.osmFile}")
//    private String osmFile;
//    @Value("${graphhopper.graphFolder}")
//    private String graphFolder;

    private final GraphHopper hopper  ;

    @Autowired
    public DistanceCalculatorServiceImp(GraphHopper hopper) {
        this.hopper =hopper;
    }

    //    public DistanceCalculatorServiceImp() {
//        this.hopper = new GraphHopper();
//        hopper.setOSMFile(osmFile);
//        hopper.setGraphHopperLocation(graphFolder);
//
//        hopper.setProfiles(new
//                Profile("car").
//                setTurnCostsConfig(new TurnCostsConfig()).
//                setWeighting("fastest"));
//        hopper.getCHPreparationHandler().
//
//                setCHProfiles(new CHProfile("car"));
//
//        hopper.importOrLoad();
//    }
//
//
//    public void close() {
//
//        hopper.close();
//    }

//    public DistanceCalculatorServiceImp(GraphHopper hopper) {
//    public DistanceCalculatorServiceImp() {
//        this.hopper = new GraphHopper();
//    }

//    @PostConstruct
//    public void init() {
//        hopper.setOSMFile(osmFile);
//        hopper.setGraphHopperLocation(graphFolder);
//
//        hopper.setProfiles(new
//                Profile("car").
//                setTurnCostsConfig(new TurnCostsConfig().setVehicleTypes(List.of("car"))).
//                setWeighting("fastest"));
//        hopper.getCHPreparationHandler().
//
//                setCHProfiles(new CHProfile("car"));
//
//        hopper.importOrLoad();
//
////        hopper.setProfiles(new Profile(profile)
////                .setTurnCostsConfig(new TurnCostsConfig())
////                .setWeighting("fastest"));
////
////        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile(profile));
////        hopper.importOrLoad();
//    }

//    @PreDestroy
//    public void close() {
//        if (hopper != null) {
//            hopper.close();
//        }
//    }


    @Override
    public double calculateDistance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        GHRequest request = new GHRequest(new GHPoint(fromLatitude, fromLongitude),
                new GHPoint(toLatitude, toLongitude))
                .setProfile("car");
        GHResponse response = hopper.route(request);


        if (response.hasErrors()) {
            response.getErrors().forEach(System.out::println);
            return -1;
        }

        return response.getBest().getDistance();
    }
}
