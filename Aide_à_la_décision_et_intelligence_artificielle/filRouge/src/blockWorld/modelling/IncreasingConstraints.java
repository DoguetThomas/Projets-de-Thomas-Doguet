package blockWorld.modelling;

import java.util.Set;
import java.util.HashSet;
import modelling.Variable;
import modelling.BooleanVariable;
import modelling.Constraint;
import modelling.UnaryConstraint;

//classe permettant de mettre en place les contraintes "croissantes"
public class IncreasingConstraints {

    private RegularityConstraints regularityConstraints;
    private Set<Constraint> contraintes;
    private Set<Constraint> increasingConstraints;

    public IncreasingConstraints(RegularityConstraints regularityConstraints) {
        this.regularityConstraints = regularityConstraints;
        this.contraintes = new HashSet<>();
        this.increasingConstraints = new HashSet<>();
        this.createIncreasingConstraints();
    }

    //mise en place des contraintes croissantes, c'eest a dire que chaque block du dessu sera forcement supérieur a celui du dessous
    public void createIncreasingConstraints(){
        for (Variable onVar : this.regularityConstraints.getBlockWorld().getBWVariable().getOnbVar()) {
            int index = this.regularityConstraints.getBlockWorld().getBWVariable().getIndex(onVar);

            Set<Object> domaine = new HashSet<>();
            for (int i = -this.regularityConstraints.getBlockWorld().getNbPiles(); i < index; i++) {
                domaine.add(i);
            }

            this.contraintes.add(new UnaryConstraint(onVar, domaine));
            this.increasingConstraints.add(new UnaryConstraint(onVar, domaine));
        }
    }

    //acsesseurs de cette classe

    public Set<Constraint> getConstraints() {
        return this.contraintes;
    }

    public Set<Constraint> getIncreasingConstraints() {
        return this.increasingConstraints;
    }

}
