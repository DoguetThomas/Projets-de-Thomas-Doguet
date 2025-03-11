package datamining;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import modelling.BooleanVariable;

//classe étandant la classe abstraite AbstractAssociationRuleMiner qui va donc implementerla méthode xtrtact de associationRuleMiner
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner{
    
    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        super(database);
    }

    //methode retournat tout les sous ensemble d'un ensemble d'item donné en paramettre excluant l'ensemble vide
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items){
        Set<Set<BooleanVariable>> res = new HashSet<>();
        List<BooleanVariable> itemList = new ArrayList<>(items);

        for (int i = 1; i < (1 << itemList.size()) - 1; i++) {
            Set<BooleanVariable> sousEnsemble = new HashSet<>();
            for(int j = 0; j < itemList.size(); j++){
                if ((i & (1 << j)) != 0) {
                    sousEnsemble.add(itemList.get(j));
                }
            }
            res.add(sousEnsemble);
        } 
        return res;
    }

    //methode permettant l'extraction de des regles d'association fréquentes et précises
    @Override
    public Set<AssociationRule> extract(float frequenceMin, float confianceMin){
        Set<AssociationRule> res = new HashSet<>();
        Apriori miner = new Apriori(this.getDatabase());
        Set<Itemset> currents = miner.extract(frequenceMin);

        for (Itemset itemset : currents){
            Set<BooleanVariable> items = itemset.getItems();
            Set<Set<BooleanVariable>> premises = this.allCandidatePremises(items);
            for (Set<BooleanVariable> premise : premises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);
                
                float frequency = this.frequency(items, currents);
                if (frequency >= frequenceMin) {
                    float confiance = confidence(premise, conclusion, currents);
                    if (confiance >= confianceMin) {
                        res.add(new AssociationRule(premise, conclusion, frequency, confiance));
                    }
                }
            }
        }
        return res;        
    }
}