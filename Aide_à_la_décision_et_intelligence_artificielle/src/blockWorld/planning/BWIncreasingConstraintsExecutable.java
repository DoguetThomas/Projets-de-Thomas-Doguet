package blockWorld.planning;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import blockWorld.modelling.BlockWorld;
import modelling.Variable;
import blockWorld.modelling.RegularityConstraints;
import blockWorld.modelling.IncreasingConstraints;
import modelling.Constraint;
import cp.Solver;
import cp.BacktrackSolver;
import cp.MACSolver;
import cp.ValueHeuristic;
import cp.DomainSizeVariableHeuristic;
import cp.RandomValueHeuristic;
import cp.VariableHeuristic;
import cp.HeuristicMACSolver;
import view.View;
import blockWorld.view.BWView;


public class BWIncreasingConstraintsExecutable{

    public static void main(String[] args){

        //Initalisation des diffèrentes variables pour tester les différents solver par rapport aux contraintes croissante
        BlockWorld world = new BlockWorld(5, 3);
        Set<Variable> BwVariable = world.getVariables();
        RegularityConstraints reguliere = new RegularityConstraints(world);
        IncreasingConstraints croissante = new IncreasingConstraints(reguliere);
        Set<Constraint> contrainteCroissante = croissante.getConstraints();

        //Test du backtrack solver par rapport aux containtes croissante, avec le temp d'exécution et le nombre d'actions à réalisé
        Solver backtrack = new BacktrackSolver(BwVariable, contrainteCroissante);
        long debut = System.nanoTime();
        Map<Variable, Object> resBacktrack = backtrack.solve();
        long fin = System.nanoTime();
        float temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le backtrack a pris : " + temp + "s et avec un resultat de taille : " + resBacktrack.size());

        //Test du MAC solver par rapport aux containtes croissante, avec le temp d'exécution et le nombre d'actions à réalisé
        Solver mac = new MACSolver(BwVariable, contrainteCroissante);
        debut = System.nanoTime();
        Map<Variable, Object> resMAC= mac.solve();
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le MAC a pris : " + temp + "s et avec un resultat de taille : " + resMAC.size());
    
        //Test du MACHereuristic solver par rapport aux containtes croissante, avec le temp d'exécution et le nombre d'actions à réalisé
        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(true);
        ValueHeuristic valeurHeuristic = new RandomValueHeuristic(new Random());
        Solver macHeuristique = new HeuristicMACSolver(BwVariable, contrainteCroissante, variableHeuristic, valeurHeuristic);
        debut = System.nanoTime();
        Map<Variable, Object> resMACHeuristique= macHeuristique.solve();
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le MACHeuristique a pris : " + temp + "s et avec un resultat de taille : " + resMACHeuristique.size());
        
        System.out.println("Resultat du backtrack: " +resBacktrack);
        //Affichage avec la vue du résultat du backtrack
        View viewBacktrack = new View("resBacktrack", world.getBWVariable(), resBacktrack);
        viewBacktrack.displaying();

        System.out.println("Resultat du mac: " +resMAC);
        //Affichage avec la vue du résultat du MAC
        View viewMAC = new View("resMAC", world.getBWVariable(), resMAC);
        viewMAC.displaying();

        System.out.println("Resultat du macHeuristique: " +resMACHeuristique);
        //Affichage avec la vue du résultat du MACHeuristique
        View viewMACHeuristique = new View("resMACHeuristique", world.getBWVariable(), resMACHeuristique);
        viewMACHeuristique.displaying();
    }
}