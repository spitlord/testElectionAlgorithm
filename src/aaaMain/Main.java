/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaMain;

import algorithm.Metric;
import algorithm.Move;
import algorithm.MovesShort;
import algorithm.ObjectiveFunction;
import algorithm.RegionGrowing;
import algorithm.SimulatedAnnealing;
import dataTypes.StateName;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import manager.StateManager;
import mapObjects.State;
import seeding.IncumbentSeedStrategy;
import seeding.RandomSeedStrategy;
import seeding.SeedStrategy;

/**
 *
 * @author spitlord
 */
public class Main {

    public static void main(String[] args) {
        
        System.out.println("Started;");
        Toolkit.getDefaultToolkit().beep(); // makes a beep 
        
        
        
        ObjectiveFunction objectiveFunction = new ObjectiveFunction(retrieveMetrics());
        SeedStrategy seedStrategy = new RandomSeedStrategy();

        
        StateName stateName = StateName.OREGON;
        State s = StateManager.getInstance().getState(stateName.OREGON);
        s.setNumDistricts(5);

        
        /* Region                         Growing */
        RegionGrowing regionGrowing = new RegionGrowing(
                StateManager.getInstance().getState(StateName.OREGON),
                objectiveFunction,
                seedStrategy
        );

        regionGrowing.run();
        
        
        System.out.println(objectiveFunction.calcCompPP(s.getDistrict(1)));
        System.out.println(objectiveFunction.calcCompReock(s.getDistrict(1)));
        System.out.println(objectiveFunction.calcCompSchwartz(s.getDistrict(1)));
        
        
        
        System.out.println(regionGrowing.getMoves().size());
        System.out.println(regionGrowing.getMoves().toString());
        
        
        
        /* Simulated                         Annealing */

//        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(
//                StateManager.getInstance().getState(stateName),
//                objectiveFunction);
//
//        simulatedAnnealing.run();

    }

    private static Map<Metric, Double> retrieveMetrics() {
        Map<Metric, Double> metrics = new HashMap<Metric, Double>();
        metrics.put(Metric.COMPACTNESS, 0.3);
        metrics.put(Metric.POLTSBY_POPPER, 0.3);
        metrics.put(Metric.SCHWARTZBERG, 0.0);
        metrics.put(Metric.REOCK, 0.3);
        metrics.put(Metric.PARTISANFAIRNESS, 0.1);
        metrics.put(Metric.POPOULATIONEQUALITY, 0.3);
        metrics.put(Metric.CONSISTENCY, 0.3);
        metrics.put(Metric.GERRYMANDERING, 0.3);
        metrics.put(Metric.ALIGNMENT, 0.3);
        metrics.put(Metric.EFFICIENCYGAP, 0.3);
        return metrics;
    }

}
