package planning;

import java.util.*;
import modelling.Variable;
import planningtests.AStarPlannerTests;
import planningtests.BFSPlannerTests;
import planningtests.BasicActionTests;
import planningtests.BasicGoalTests;
import planningtests.DFSPlannerTests;
import planningtests.DijkstraPlannerTests;

class Exe{

	public static void main(String[] args){
	
		//Executable utilisé pour appliquer les libs et les diffèrents test coréspondant
		boolean ok = true;
		ok = ok && BasicActionTests.testIsApplicable();
		ok = ok && BasicActionTests.testSuccessor();
		ok = ok && BasicActionTests.testGetCost();
		ok = ok && BasicGoalTests.testIsSatisfiedBy();
		ok = ok && DFSPlannerTests.testPlan();
		ok = ok && BFSPlannerTests.testPlan();
		ok = ok && DijkstraPlannerTests.testPlan();
		ok = ok && AStarPlannerTests.testPlan();
		System.out.println(ok ? "All tests OK" : "At least one test KO");

		Map<Variable, Object> etatInitial = new HashMap<>();
		Set<Action> actions = new HashSet<>();
		Map<Variable, Object> goalState = new HashMap<>();

		Variable v1 = new Variable("v1", new HashSet<>());
		Variable v2 = new Variable("v2", new HashSet<>());
		Variable v3 = new Variable("v3", new HashSet<>());
		Variable v4 = new Variable("v4", new HashSet<>());

		etatInitial.put(v1, 0);
		etatInitial.put(v2, 0);
		etatInitial.put(v3, 0);
		etatInitial.put(v4, 0);

		goalState.put(v1, 5);
		goalState.put(v2, 4);
		goalState.put(v3, 3);
		goalState.put(v4, 2);

		actions.add(new BasicAction(Map.of(v1, 0), Map.of(v1, 1), 1));
		actions.add(new BasicAction(Map.of(v1, 1), Map.of(v1, 2), 2));
		actions.add(new BasicAction(Map.of(v1, 2), Map.of(v1, 3), 1));
		actions.add(new BasicAction(Map.of(v1, 3), Map.of(v1, 4), 5));
		actions.add(new BasicAction(Map.of(v1, 4), Map.of(v1, 5), 1));

		actions.add(new BasicAction(Map.of(v2, 0), Map.of(v2, 1), 2));
		actions.add(new BasicAction(Map.of(v2, 1), Map.of(v2, 2), 2));
		actions.add(new BasicAction(Map.of(v2, 2), Map.of(v2, 3), 1));
		actions.add(new BasicAction(Map.of(v2, 3), Map.of(v2, 4), 2));
		actions.add(new BasicAction(Map.of(v2, 4), Map.of(v2, 5), 1));

		actions.add(new BasicAction(Map.of(v3, 0), Map.of(v3, 1), 5));
		actions.add(new BasicAction(Map.of(v3, 1), Map.of(v3, 2), 3));
		actions.add(new BasicAction(Map.of(v3, 2), Map.of(v3, 3), 2));
		actions.add(new BasicAction(Map.of(v3, 3), Map.of(v3, 4), 1));
		actions.add(new BasicAction(Map.of(v3, 4), Map.of(v3, 5), 1));

		actions.add(new BasicAction(Map.of(v4, 0), Map.of(v4, 1), 1));
		actions.add(new BasicAction(Map.of(v4, 1), Map.of(v4, 2), 2));
		actions.add(new BasicAction(Map.of(v4, 2), Map.of(v4, 3), 1));
		actions.add(new BasicAction(Map.of(v4, 3), Map.of(v4, 4), 2));
		actions.add(new BasicAction(Map.of(v4, 4), Map.of(v4, 5), 1));

		Goal but = new BasicGoal(goalState);
			
		//test DFS Planner
		DFSPlanner dfsPlanner = new DFSPlanner(etatInitial, actions, but);
		dfsPlanner.activateBooleanCount(true);
		List<Action> dfsPlan = dfsPlanner.plan();
		int dfsNodesExplored = dfsPlanner.getNodeCount();
		System.out.println("DFS a exploré " + dfsNodesExplored + " nœuds.");
		
		// test BFS Planner
		BFSPlanner bfsPlanner = new BFSPlanner(etatInitial, actions, but);
		bfsPlanner.activateBooleanCount(true);
		List<Action> bfsPlan = bfsPlanner.plan();
		int bfsNodesExplored = bfsPlanner.getNodeCount();
		System.out.println("BFS a exploré " + bfsNodesExplored + " nœuds.");

		//test Dijkstra Planner
		DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInitial, actions, but);
		dijkstraPlanner.activateBooleanCount(true);
		List<Action> dijkstraPlan = dijkstraPlanner.plan();
		int dijkstraNodesExplored = dijkstraPlanner.getNodeCount();
		System.out.println("Dijkstra a exploré " + dijkstraNodesExplored + " nœuds.");
	}

}
