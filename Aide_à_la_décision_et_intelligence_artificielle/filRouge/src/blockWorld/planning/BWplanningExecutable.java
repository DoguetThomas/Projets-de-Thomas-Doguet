package blockWorld.planning;

import java.util.Set;
import java.util.Map;
import java.util.List;

import blockWorld.modelling.BWVariable;
import modelling.Variable;
import planning.Action;
import planning.BasicGoal;
import planning.Goal;
import planning.BFSPlanner;
import planning.DFSPlanner;
import planning.DijkstraPlanner;
import planning.AStarPlanner;
import view.View;
import blockWorld.view.BWView;

public class BWplanningExecutable{
    public static void main(String[] args){


        //Initalisation des diffèrentes variables pour tester les différents planner 
        BWAction BwAction = new BWAction(5, 4);
        BWVariable BwVariable = BwAction.getBlocksWorld().getBWVariable();
        Set<Action> actions = BwAction.getActions();


        //Initialisation de l'état principal et du but
        int[][] preEtat = {
        	{0},
            {1},
            {2},
            {3,4},
        };

        int[][] preBut = {
            {0},
            {1},
            {2, 4},
            {3},
        };

        Map<Variable, Object> etat = BwVariable.setState(preEtat);
        Map<Variable, Object> tempBut = BwVariable.setState(preBut);
        Goal but = new BasicGoal(tempBut);

        //Test du dfsPlanner avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        DFSPlanner ActionDFS = new DFSPlanner(etat, actions, but);
        ActionDFS.activateBooleanCount(true);
        long debut = System.nanoTime();
        List<Action> planDFS = ActionDFS.plan();
        System.out.println(planDFS);
        long fin = System.nanoTime();
        float temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le DFS a pris : " + temp + "s, a visité " + ActionDFS.getNodeCount() +  "noeuds et la plan est de taille : " + planDFS.size());

        //Test du bfsPlanner avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        BFSPlanner ActionBFS = new BFSPlanner(etat, actions, but);
        ActionBFS.activateBooleanCount(true);
        debut = System.nanoTime();
        List<Action> planBFS = ActionBFS.plan();
        System.out.println(planBFS);
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le BFS a pris : " + temp + "s, a visité " + ActionBFS.getNodeCount() + "noeuds et la plan est de taille : " + planBFS.size());

        //Test d'astar et le premier heuristic avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        BlockWithWrongPositionHeuristic heuristic1 = new BlockWithWrongPositionHeuristic(tempBut, BwVariable);
        AStarPlanner ActionAstar1 = new AStarPlanner(etat, actions, but, heuristic1);
        ActionAstar1.activateBooleanCount(true);
        debut = System.nanoTime();
        List<Action> planAstar1 = ActionAstar1.plan();
        System.out.println(planAstar1);
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le Astar1 a pris : " + temp + "s, a visité " + ActionAstar1.getNodeCount() + "noeuds et la plan est de taille : " + planAstar1.size());

        //Test d'astar et le deuxieme heuristic avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        BlockedBlockHeuristic heuristic2 = new BlockedBlockHeuristic(BwVariable);
        AStarPlanner ActionAstar2 = new AStarPlanner(etat, actions, but, heuristic2);
        ActionAstar2.activateBooleanCount(true);
        debut = System.nanoTime();
        List<Action> planAstar2 = ActionAstar2.plan();
        System.out.println(planAstar2);
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le Astar2 a pris : " + temp + "s, a visité " + ActionAstar2.getNodeCount()+ "noeuds et la plan est de taille : " + planAstar2.size());

        //Test d'astar avec le premier et le deuxieme heuristic, avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        BlockedBlockHeuristicOnWrongPosition heuristic3 = new BlockedBlockHeuristicOnWrongPosition(tempBut, BwVariable);
        AStarPlanner ActionAstar3 = new AStarPlanner(etat, actions, but, heuristic3);
        ActionAstar3.activateBooleanCount(true);
        debut = System.nanoTime();
        List<Action> planAstar3 = ActionAstar3.plan();
        System.out.println(planAstar3);
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le Astar3 a pris : " + temp + "s, a visité " + ActionAstar3.getNodeCount()+ "noeuds et la plan est de taille : " + planAstar3.size());

        //Test du Dijkstra planner avec le temps d'éxecution, le nombre de noeuds visités et la taille du plan trouvé .
        DijkstraPlanner ActionDijkstra = new DijkstraPlanner(etat, actions, but);
        ActionDijkstra.activateBooleanCount(true);
        debut = System.nanoTime();
        List<Action> planDijkstra = ActionDijkstra.plan();
        System.out.println(planDijkstra);
        fin = System.nanoTime();
        temp = (float) (fin - debut) / 1000000000;
        System.out.println("Le Dijkstra a pris : " + temp + "s, a visité " + ActionDijkstra.getNodeCount() + "noeuds et la plan est de taille : " + planDijkstra.size());
		
        //Affichage avec la vue du plan de DFS
		View viewBfs = new View("planDFS", BwVariable, etat);
        new BWView(viewBfs, planDFS).start();

        //Affichage avec la vue du plan de BFS
        viewBfs = new View("planBFS",BwVariable, etat);
        new BWView(viewBfs, planBFS).start();

        //Affichage avec la vue du plan d'astar avec le preumier heurisitic
        viewBfs = new View("planAstar1", BwVariable, etat);
        new BWView(viewBfs, planAstar1).start();

        //Affichage avec la vue du plan d'astar avec le deuxieme heuristic
        viewBfs = new View("planAstar2", BwVariable, etat);
        new BWView(viewBfs, planAstar2).start();

        //Affichage avec la vue du plan d'astar avec le premier et le deuxieme heuristic
        viewBfs = new View("planAstar3", BwVariable, etat);
        new BWView(viewBfs, planAstar3).start();

        //Affichage avec la vue du plan de Djikstra
        viewBfs = new View("planDijsktra", BwVariable, etat);
        new BWView(viewBfs, planDijkstra).start();

    }
}
