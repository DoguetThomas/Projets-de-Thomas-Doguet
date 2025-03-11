package planning;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Collections;
import modelling.Variable;

public class DijkstraPlanner implements Planner {

	//Création des attributs
    private Map<Variable, Object> etat_initial;
    private Set<Action> actions;
    private Goal but;
	private boolean isActiveCpt;
	private int cpt;
	
	//Constructeur prennant en paramètre un etat initial, un set d'actions et un but
    public DijkstraPlanner(Map<Variable, Object> etat_initial, Set<Action> actions, Goal but) {
        this.etat_initial = etat_initial;
        this.actions = actions;
        this.but = but;
        
        this.isActiveCpt = false;
        this.cpt = 0;
    }
	
	//Méthode appliquant l'algorithme dijkstra
	@Override
    public List<Action> plan() {
		Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        open.add(this.getInitialState());
        father.put(this.getInitialState(), null);
        distance.put(this.getInitialState(), 0.0);
        
        
        this.cpt = 0;
        while(!open.isEmpty()){
			
			if(this.isActiveCpt){
				this.cpt+=1;
			}
			
			Map<Variable, Object> instanciation = minDistance(open, distance);
			open.remove(instanciation);
			if (this.getGoal().isSatisfiedBy(instanciation)){
				goals.add(instanciation);
			}
			for (Action action : this.getActions()){
				if (action.isApplicable(instanciation)){
					Map<Variable, Object> next = action.successor(instanciation);
					distance.putIfAbsent(next, Double.POSITIVE_INFINITY);
					double newCost = distance.get(instanciation) + action.getCost();
					if (distance.get(next)>newCost) {
                        distance.put(next, newCost);
                        father.put(next, instanciation);
                        plan.put(next, action);
                        open.add(next);
                    }
				}
			}
		}
		if (goals.isEmpty()){
			return null;
		}
		else {
			return getDijkstraPlan(father, plan, goals, distance);
		} 
    }
	
	//Méthode permettant de récuperer le plan pour appliquer la méthode dijkstra
	private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Double> distance) {
		Queue<Action> DIJ_plan = new LinkedList<>();
		 Map<Variable, Object> goal = null;
		 double minDistance = Double.POSITIVE_INFINITY;
         for (Map<Variable, Object> pregoal : goals) {
            if (distance.containsKey(pregoal) && distance.get(pregoal) < minDistance) {
                minDistance = distance.get(pregoal);
                goal = pregoal;
            }
        }
		 while(father.get(goal)!=null){
			 DIJ_plan.add(plan.get(goal));
			 goal = father.get(goal);
		 }
		 
		 List<Action> result = new LinkedList<>(DIJ_plan);
		 Collections.reverse(result);
        return result;
	}
	
	//Méthode permettant d'avoir la minDistance 
	private Map<Variable, Object> minDistance(Set<Map<Variable, Object>> open,
                                                    Map<Map<Variable, Object>, Double> distance) {
        Map<Variable, Object> EtatMin = null;
        double distanceMin = Double.POSITIVE_INFINITY;
        
        for (Map<Variable, Object> state : open) {
			if (distance.containsKey(state)) {
				double stateDistance = distance.get(state);
				if (stateDistance < distanceMin) {
					distanceMin = stateDistance;
					EtatMin = state;
				}
			}
        }

        return EtatMin;
	}	
	
	//Méthode d'accés à l'état l'état initial
	@Override
    public Map<Variable, Object> getInitialState() {
        return this.etat_initial;
    }
	
	//Méthode d'accés au set d'actions
	@Override
    public Set<Action> getActions() {
        return this.actions;
    }
	
	//Méthode d'accés au but
	@Override
    public Goal getGoal() {
        return this.but;
    }
    
    public int getNodeCount() {
    	return this.cpt;
	}
    
    //Méthode permettant l'utilisation de la sonde
    public void activateBooleanCount(boolean nouveau){
		if (nouveau == true){
			this.cpt = 0;
		}
		this.isActiveCpt = nouveau;
	}
}
