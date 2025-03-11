package planning;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.Collections;
import modelling.Variable;

public class BFSPlanner implements Planner {

	//Création des attributs
    private Map<Variable, Object> etat_initial;
    private Set<Action> actions;
    private Goal but;
    private boolean isActiveCpt;
	private int cpt;

	//Constructeur prennant en paramètre un etat_initial, un set d'actions et un but
    public BFSPlanner(Map<Variable, Object> etat_initial, Set<Action> actions, Goal but) {
        this.etat_initial = etat_initial;
        this.actions = actions;
        this.but = but;
        this.isActiveCpt = false;	
        this.cpt = 0;
    }
	
	//Méthode (Override) appliquant l'algorithme BFS
	@Override
    public List<Action> plan() {
        
        this.cpt = 0;
        
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Set<Map<Variable, Object>> closed = new HashSet<>();
        Queue<Map<Variable, Object>> open = new LinkedList<>();
        open.add(this.getInitialState());
        father.put(this.getInitialState(), null);
        if (this.but.isSatisfiedBy(this.etat_initial)){
			return getBFSPlan(father, plan, this.etat_initial);
		}
		while (!open.isEmpty()) {
            Map<Variable, Object> instanciation = new HashMap<>();
            instanciation = open.poll();
            closed.add(instanciation);
            
            if(this.isActiveCpt){
				this.cpt+=1;
			}
            for (Action action : this.getActions()) {
                if (action.isApplicable(instanciation)) {
                    Map<Variable, Object> next = action.successor(instanciation);
                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instanciation);
                        plan.put(next, action);
                        if (this.getGoal().isSatisfiedBy(next)) {
                            return getBFSPlan(father, plan, next);
                        }
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

	//Méthode permmetant d'initialiser le plan pour le BFS
    public List<Action> getBFSPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> state) {
        List<Action> res = new ArrayList<>();
        while(state != null){
			Action action = plan.get(state);
			if (action != null) {
                res.add(action);
            }
            state = father.get(state);
        }
        Collections.reverse(res);
        return res;
    }
	
	//Méthode d'accés de l'état initial
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
