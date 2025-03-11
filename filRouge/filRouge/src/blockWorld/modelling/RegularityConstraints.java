package blockWorld.modelling;

import java.util.Set;
import java.util.HashSet;
import modelling.Variable;
import modelling.BooleanVariable;
import modelling.Constraint;
import modelling.Implication;

//classe permettant de mettre en place les contraintes "régulières"

public class RegularityConstraints {

    private BlockWorld blockWorld;
    private Set<Constraint> contraintes;
    private Set<Constraint> regularityConstraints;

    public RegularityConstraints(BlockWorld blockWorld) {
        this.blockWorld = blockWorld;
        this.contraintes = new HashSet<>();
        this.regularityConstraints = new HashSet<>();
        this.createRegularityConstraints();
        
    }

    //mise en place des contraintes régulières, c'est a dire qu'au sein d'une pile, les ecarts entre les blocks doivent etre les meme
    //(exemple 1/2/3/4 ou 2/4/6/8)
    public void createRegularityConstraints() {
        Set<Variable> onVar = this.blockWorld.getBWVariable().getOnbVar();
        for (Variable onVar1 : onVar) {
            int index1 = this.blockWorld.getBWVariable().getIndex(onVar1);
            for(Variable onVar2 : onVar) {
                int index2 = this.blockWorld.getBWVariable().getIndex(onVar2);
                if(index1 != index2) {
                    
                    Set<Object> domaine1 = new HashSet<>();
                    domaine1.add(index2);
                    
                    Set<Object> domaine2 = new HashSet<>();
                    int ecart = index2 - (index1 - index2);
                    for (int i = 1; i <= this.blockWorld.getNbPiles(); i++){
                        domaine2.add(-i);
                    }
                    if (ecart >= 0 && ecart < this.blockWorld.getNbBlock()){
                        domaine2.add(ecart);
                    }
                    this.contraintes.add(new Implication(onVar1, domaine1, onVar2, domaine2));
                    this.regularityConstraints.add(new Implication(onVar1, domaine1, onVar2, domaine2));
                }
            }
        }
    }

    //accésseurs de cette classe
    public BlockWorld getBlockWorld(){
        return this.blockWorld;
    }

    public Set<Constraint> getConstraints(){
        return this.contraintes;
    }

    public Set<Constraint> getRegularityConstraints(){
        return this.regularityConstraints;
    }
}