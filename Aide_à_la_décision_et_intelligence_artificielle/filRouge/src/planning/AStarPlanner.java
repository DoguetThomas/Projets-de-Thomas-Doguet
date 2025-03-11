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

public class AStarPlanner implements Planner {

	//Création des attributs
    private Map<Variable, Object> etat_initial;
    private Set<Action> actions;
    private Goal but;
    private Heuristic heuristic;
    private boolean isActiveCpt;
	private int cpt;

	//Constructeur prenant en paramètre un etat initial, un set d'actions, un but et une heuristic
    public AStarPlanner(Map<Variable, Object> etat_initial, Set<Action> actions, Goal but, Heuristic heuristic) {
        this.etat_initial = etat_initial;
        this.actions = actions;
        this.but = but;
        this.heuristic = heuristic ;
        
        this.isActiveCpt = false;
        this.cpt = 0;
    }

	//Méthode (Override) appliquant l'algo Astar
	@Override
    public List<Action> plan() {
		
		this.cpt = 0;
		
		Set<Map<Variable, Object>> open = new HashSet<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        BFSPlanner bfs = new BFSPlanner(this.getInitialState(), this.getActions(), this.getGoal());
        
        open.add(this.getInitialState());
        father.put(this.getInitialState(), null);
        Double a = 0.0;
        distance.put(this.getInitialState(), a);
        value.put(this.getInitialState(), this.heuristic.estimate(this.getInitialState()));
        while (!open.isEmpty()){
			if(this.isActiveCpt){
				this.cpt+=1;
			}
			Map<Variable, Object> instanciation = minDistance(open, distance);
			open.remove(instanciation);
			if (this.getGoal().isSatisfiedBy(instanciation)){
				return bfs.getBFSPlan(father, plan, instanciation);
			}
			else{
				for (Action action : this.getActions()){
					if(action.isApplicable(instanciation)){
						Map<Variable, Object> next = action.successor(instanciation);
						distance.putIfAbsent(next, Double.POSITIVE_INFINITY);
						Double newCost = distance.get(instanciation) + action.getCost();
						if (distance.get(next)>newCost) {
							distance.put(next, newCost);
							father.put(next, instanciation);
                            Float f = distance.get(instanciation).floatValue() + action.getCost() + heuristic.estimate(next);
							value.put(next, f);
							open.add(next);
                            plan.put(next, action);
						}
					
                    }
				}
			}
		}
		return null;
    }
	
	//Méthode permettant d'avoir la minDistance 
	private Map<Variable, Object> minDistance(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Double> distance) {
        Map<Variable, Object> EtatMin = null;
        Double distanceMin = Double.POSITIVE_INFINITY;
        
        for (Map<Variable, Object> state : open) {
			if (distance.containsKey(state)) {
				Double stateDistance = distance.get(state);
				if (stateDistance < distanceMin) {
					distanceMin = stateDistance;
					EtatMin = state;
				}
			}
        }

        return EtatMin;
}	

	//Méthode d'accés à l'état initial
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

