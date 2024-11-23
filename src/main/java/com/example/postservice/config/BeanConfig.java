package com.example.postservice.config;

import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.routing.util.PriorityCode;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.GHUtility;
import com.graphhopper.util.TurnCostsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BeanConfig {

    @Value("${graphhopper.osmFile}")
    private String osmFile;
    @Value("${graphhopper.graphFolder}")
    private String graphFolder;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public GraphHopper getGraphHopper(){
//        GraphHopper hopper = new GraphHopper();
//        hopper.setOSMFile(osmFile);
//        hopper.setGraphHopperLocation(graphFolder);
//
//        CustomModel customModel = new CustomModel();
//
//
//
////        customModel.addToSpeed(Statement.If("condition1", Arrays.asList(
////                Statement.If("condition2", Statement.Op.MULTIPLY, "10"),
////                Statement.Else(Statement.Op.LIMIT, "5")
////        )));
//
//
//
//
//        Profile carProfile = new Profile("car")
//                .setTurnCostsConfig(new TurnCostsConfig(TurnCostsConfig.car()))
//                .setWeighting("custom")
//                .setCustomModel(customModel);
//
//        hopper.setProfiles(carProfile);
//
//        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
//
//
//
//
//
//        hopper.importOrLoad();
//        return hopper;


        GraphHopper hopper = new GraphHopper();
        hopper.setOSMFile(osmFile);
        hopper.setGraphHopperLocation("target/routing-tc-graph-cache");
        // add all encoded values that are used in the custom model, these are also available as path details or for client-side custom models
        hopper.setEncodedValuesString("car_access, car_average_speed, road_access");
        Profile profile = new Profile("car").setCustomModel(GHUtility.loadCustomModelFromJar("car.json"))
                // enabling turn costs means OSM turn restriction constraints like 'no_left_turn' will be taken into account for the specified access restrictions
                // we can also set u_turn_costs (in seconds). i.e. we will consider u-turns at all junctions with a 40s time penalty
                .setTurnCostsConfig(new TurnCostsConfig(List.of("motorcar", "motor_vehicle"), 40));
        hopper.setProfiles(profile);
        // enable CH for our profile. since turn costs are enabled this will take more time and memory to prepare than
        // without turn costs.
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile(profile.getName()));
        hopper.importOrLoad();
        return hopper;
    }
}
