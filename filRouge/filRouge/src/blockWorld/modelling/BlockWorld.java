package blockWorld.modelling;

import java.util.Set;
import java.util.HashSet;

import modelling.Variable;
import modelling.BooleanVariable;
import modelling.Constraint;
import modelling.Implication;
import modelling.DifferenceConstraint;

//classe representantes un nombre block en créants les containtes basiques en ayant pris un nombre de block et un nombre de pile en parametre
public class BlockWorld{

    private int nbBlocks;
    private int nbPiles;
    private BWVariable bwVariables;
    private Set<Constraint> contraintes;
    private Set<Constraint> basicConstraintes;

    //constructeur recperants toutes les variables et metant en place une liste de contrainte
    public BlockWorld(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.bwVariables = new BWVariable(nbBlocks, nbPiles);
        RegularityConstraints regConstraints = new RegularityConstraints(this);
        IncreasingConstraints incConstraints = new IncreasingConstraints(regConstraints);

        this.contraintes = new HashSet<>();
        this.basicConstraintes = new HashSet<>();
        this.createConstraint();
        this.contraintes.addAll(regConstraints.getConstraints());
        this.contraintes.addAll(incConstraints.getConstraints());
    }

    //méthode creant toutes les contraintes basiques
    public void createConstraint(){
        Set<Variable> onbVariables = this.bwVariables.getOnbVar();

        //creations de contraintes de differences, qui stipulent qu'aucun block ne peut etre sur le meme block qu'un autre
        for(Variable var1 : onbVariables){
            for(Variable var2 : onbVariables){
                if(!var1.equals(var2)){
                    this.contraintes.add(new DifferenceConstraint(var1, var2));
                    this.basicConstraintes.add(new DifferenceConstraint(var1, var2));
                }
            }
        }

        //creations de contraintes d'implication, qui stipulent que chaques blocks sous un autre doit etre considéré comme "fixed"
        for (Variable onVar1 : bwVariables.getOnbVar()) {
            int index1 = this.bwVariables.getIndex(onVar1);
            for(Variable fixedb2: this.bwVariables.getFixedbVar()) {
                int b2Index = this.bwVariables.getIndex(fixedb2);
                if(index1 != b2Index) {
                    Set<Object> domaine1 = new HashSet<>();
                    domaine1.add(b2Index);

                    Set<Object> domaine2 = new HashSet<>();
                    domaine2.add(true);
                    
                    this.contraintes.add(new Implication(onVar1, domaine1, fixedb2, domaine2));
                    this.basicConstraintes.add(new Implication(onVar1, domaine1, fixedb2, domaine2));
                }
            }
        //creations de contraintes d'implication, qui stipulent que chaques piles composés de au moins un block sont forcément considérés comme "non libre"
            for(Variable free1: this.bwVariables.getFreepVar()) {
                int freeIndex1 = this.bwVariables.getIndex(free1);

                Set<Object> domaine12 = new HashSet<>();
                domaine12.add(-(freeIndex1+1));
                Set<Object> domaine22 = new HashSet<>();
                domaine22.add(false);

                this.contraintes.add(new Implication(onVar1, domaine12, free1, domaine22));
                this.basicConstraintes.add(new Implication(onVar1, domaine12, free1, domaine22));
            }
        }
    }

    //acsesseurs vers les elements de cette classe

    public int getNbBlock(){
        return this.nbBlocks;
    }

    public int getNbPiles(){
        return this.nbPiles;
    }

    public Set<Variable> getVariables(){
        return this.bwVariables.getVariables();
    }

    public BWVariable getBWVariable(){
        return this.bwVariables;
    }
    public Set<Constraint> getConstrains(){
        return this.contraintes;
    }
    public Set<Constraint> getBasiconstrains(){
        return this.basicConstraintes;
    }

    //mise en place de la liste de contrainte
    public void setConstraints(Set<Constraint> contraintes){
        this.contraintes = contraintes;
    }
}