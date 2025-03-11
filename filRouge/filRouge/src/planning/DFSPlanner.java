package planning;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import modelling.Variable;
import modelling.BooleanVariable;

public class DFSPlanner implements Planner{
	
	//Création des attributs
	private Map<Variable,Object> etat_initial;
	private Set<Action> actions;
	private Goal but;
	private boolean isActiveCpt;
	private int cpt;
	
	//Constructeur prennant en paramètre un etat initial, un set d'actions et un but
	public DFSPlanner(Map<Variable,Object> etat_initial, Set<Action> actions, Goal but){
		this.etat_initial = etat_initial;
		this.actions = actions;
		this.but = but;
		this.isActiveCpt = false;
        this.cpt = 0;
        
	}
	
	//Méthode (Override) permettant de récuper le plan pour la méthode DFS
	@Override
	public List<Action> plan(){
		this.cpt = 0;
		
		return dfs(etat_initial, new Stack<Action>(), new HashSet<Map<Variable,Object>>());
	} 
	
	//Méthode appliquant l'algorithme DFS
	private List<Action> dfs(Map<Variable,Object> instantiation, Stack<Action> plan, Set<Map<Variable,Object>> closed){
		
		if(this.isActiveCpt){
			this.cpt+=1;
		}
		if (but.isSatisfiedBy(instantiation)){
			return plan;
		}
		else{
			for (Action action : this.getActions()){
				if (action.isApplicable(instantiation)){
					Map<Variable,Object> newState = action.successor(instantiation);
					if (!closed.contains(newState)){
						plan.push(action);
						closed.add(newState);
						List<Action> subPlan = dfs(newState, plan, closed);
						if (subPlan!=null){
							return subPlan;
						}
						else{
							plan.pop();
						}
					}
				}
			}
			return null;
		}
	}
	
	//Méthode d'accés à l'état initial
	@Override
	public Map<Variable, Object> getInitialState(){
		return this.etat_initial;
	}
	
	//Méthode d'accés au set d'actions
	@Override
	public Set<Action> getActions(){
		return this.actions;
	}
	
	//Méthode d'accés au but
	@Override
	public Goal getGoal(){
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
