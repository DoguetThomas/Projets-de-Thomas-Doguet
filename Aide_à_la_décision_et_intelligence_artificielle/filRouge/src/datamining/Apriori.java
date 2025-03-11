package datamining;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;

import modelling.BooleanVariable;

//classe apriori etendant la classe abstractItemSetMiner
public class Apriori extends AbstractItemsetMiner{
    public Apriori(BooleanDatabase base){
        super(base);
    }

    //methode retournant l'ensemble de tous les itemsets singletons, avec leurs frequence, qui au moins egale a celle en argument
    public Set<Itemset> frequentSingletons(float frequence){
        Set<Itemset> singletons = new HashSet<>();
        
        for (BooleanVariable item: this.base.getItems()){
            Set<BooleanVariable> items = new HashSet<>();
            items.add(item);
            if (this.frequency(items) >= frequence){
                singletons.add(new Itemset(items, this.frequency(items)));
            }
        }
        return singletons;
    }

    //methode retournant un ensemble trié d'items conbiné de deux ensemble d'items triés, en fonction de trois conditions
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> e1, SortedSet<BooleanVariable> e2){
        if (e1.size() == 0){
            return null;
        }
        SortedSet<BooleanVariable> combined = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        if (e1.size() != e2.size() ) {
            return null;
        }
        else{
            SortedSet<BooleanVariable> ee1 = e1.subSet(e1.first(), e1.last());
            SortedSet<BooleanVariable> ee2 = e2.subSet(e2.first(), e2.last());

            if(ee1.equals(ee2)) { 
                if(!e1.last().equals(e2.last())) { 
                    combined.addAll(e1);
                    combined.add(e2.last());
                    return combined;
                } 
                else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    //methode statique retournat true si tous les sous ensembles de obtenus en supprimant un element de l'ensemblene argument sont contenus dans la collection
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> collEnsItems){
        for (BooleanVariable item : items) {
            SortedSet<BooleanVariable> liste = new TreeSet<>(COMPARATOR);
            liste.addAll(items);
            liste.remove(item);

            if (!collEnsItems.contains(liste)) {
                return false;
            }
        }
        return true;
    }

    //implementation de la methode extract, qui extrait tout les itemset frequents en fonction des methodes crées auparavant.
    @Override
    public Set<Itemset> extract(float frequence){
        Set<Itemset> res = new HashSet<>();
        Set<Itemset> singletons = frequentSingletons(frequence);
        res.addAll(singletons);
        List<SortedSet<BooleanVariable>> actuel = new ArrayList<>();

        for(Itemset item :res){
            actuel.add(new TreeSet<>(COMPARATOR){{addAll(item.getItems());}});
        }

        while (!actuel.isEmpty()){
            ArrayList<SortedSet<BooleanVariable>> nouv = new ArrayList<>();            
            for (int i = 0; i < actuel.size(); i++) {
                for (int j = i + 1; j < actuel.size(); j++) {
                    SortedSet<BooleanVariable> newSet = combine(actuel.get(i), actuel.get(j));
                    if (newSet != null && allSubsetsFrequent(newSet, actuel)) {
                        float frequency = frequency(newSet);
                        if (frequency >= frequence) {
                            res.add(new Itemset(newSet, frequency));
                            nouv.add(newSet);
                        }
                    }
                }
            }
            actuel = nouv;
        }   
        return res;
    }
}