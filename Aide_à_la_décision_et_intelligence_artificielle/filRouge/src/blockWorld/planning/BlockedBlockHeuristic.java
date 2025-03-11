package blockWorld.planning;

import java.util.Map;

import planning.Heuristic;
import modelling.Variable;
import blockWorld.modelling.BWVariable;

//heuristique n1, calculant le nombre de blocks bloqués, c'est a dire les nombre de blocks placé au dessous d'un autre
public class BlockedBlockHeuristic implements Heuristic{

    private BWVariable bwVariable;

    public BlockedBlockHeuristic(BWVariable bwVariable){
        this.bwVariable = bwVariable;
    }

    //méhode de calcul
    @Override
    public float estimate(Map<Variable, Object> state){
        int res = 0;

        for (Variable onbVar : this.getBwVariable().getOnbVar()) {
            int blockIndex = this.getBwVariable().getIndex(onbVar);
            Variable fixedVar = this.getBwVariable().getFixedBooleanVarWithBlockIndex("fixed_" + blockIndex);
            if (state.get(fixedVar).equals(true)) {
                res++;
            }
        }
        return res;
    }

    //accsseurs de la classe
    public BWVariable getBwVariable(){
        return this.bwVariable;
    }
}