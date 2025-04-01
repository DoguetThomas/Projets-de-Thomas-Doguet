package blockWorld.planning;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import planning.Action;
import planning.BasicAction;
import modelling.Variable;
import blockWorld.modelling.BlockWorld;

//classe permettant de mettre en place les différentes actions possibles dans un monde block
public class BWAction{
    private Set<Action> actions;
    private BlockWorld blockWorld;

    //la classe prend en argument un nombre de pile et un nombre de block
    public BWAction(int nbBlocks, int nbPiles){
        this.actions = new HashSet<>();
        this.blockWorld = new BlockWorld(nbBlocks, nbPiles);
        this.createActions();
    }

    public void createActions(){
        for(int block1=0; block1 < this.blockWorld.getNbBlock(); block1++) {

            Variable onBlock1 = this.blockWorld.getBWVariable().getOnBlockVarWithBlockIndex("On_" + block1);

            Variable fixedBlock1 = this.blockWorld.getBWVariable().getFixedBooleanVarWithBlockIndex("fixed_" + block1);

            for(int block2=0; block2 < this.blockWorld.getNbBlock(); block2++) {
                Variable fixedBlock2 = this.blockWorld.getBWVariable().getFixedBooleanVarWithBlockIndex("fixed_" + block2);
                if(block1 != block2){
                    for(int block3=0; block3 < this.blockWorld.getNbBlock(); block3++) {
                        if (block3 != block1 && block3 != block2){
                            Variable fixedBlock3 = this.blockWorld.getBWVariable().getFixedBooleanVarWithBlockIndex("fixed_" + block3);
                            if(!(block3 == block1 || block3 == block2)){
                                //actions n1, deplacer un block b au dessus d'un block b2 vers le dessus d'un block b3
                                this.createAndAddAction(onBlock1, fixedBlock1, fixedBlock3, block2, false, false, 
                                                    onBlock1, fixedBlock2, fixedBlock3, block3, false, true);
                            }
                        }
                        

                        
                    }
                    for(int pile1=0; pile1 < this.blockWorld.getNbPiles(); pile1++) {
                        Variable freePile1 = this.blockWorld.getBWVariable().getFreeBooleanVarWithPileIndex("free_" + pile1);

                        //actions n2, deplacer un block b1 placé sur un block b2 vers une pile vide p
                        this.createAndAddAction(onBlock1, fixedBlock1, freePile1, block2, false, true, 
                                                onBlock1, fixedBlock2, freePile1, (-pile1 - 1), false, false);

                        // actions n3 déplacer un bloc b du dessous d’une pile p vers le dessus d’un bloc b′,
                        this.createAndAddAction(onBlock1, fixedBlock1, fixedBlock2, (-pile1 - 1), false, false, 
                                                onBlock1, freePile1, fixedBlock2, block2, true, true);

                        for(int pile2=0; pile2 < this.blockWorld.getNbPiles(); pile2++) {
                            if (pile2 != pile1){
                                Variable freePile2 = this.blockWorld.getBWVariable().getFreeBooleanVarWithPileIndex("free_" + pile2);
                                
                                //actions n4, deplacer un block placé en bas d'un pile p1(comptant pour seul elemnt p1)
                                //vers en bas d'une pile p2(qui etait initialement vide)
                                this.createAndAddAction(onBlock1, fixedBlock1, freePile2, (-pile1 - 1), false, true, 
                                                    onBlock1, freePile1, freePile2, (-pile2 - 1), true, false);
                            }
                            
                        }

                }
                } 
                
            }
        }
    }


    public BlockWorld getBlocksWorld() {
        return this.blockWorld;
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    private Map<Variable, Object> createState(Variable v1, Variable v2, Variable v3, Object o1, Object o2, Object o3){
        Map<Variable, Object> res = new HashMap<>();
        res.put(v1, o1);
        res.put(v2, o2);
        res.put(v3, o3);
        return res;
    }

    public void createAndAddAction(Variable v11, Variable v12, Variable v13, Object o11, Object o12, Object o13,
                                    Variable v21, Variable v22, Variable v23, Object o21, Object o22, Object o23){
            Map<Variable, Object> precondition = createState(v11, v12, v13, o11, o12, o13);
            Map<Variable, Object> effect = createState(v21, v22, v23, o21, o22, o23);
            this.actions.add(new BasicAction(precondition, effect, 1));
        }

}
