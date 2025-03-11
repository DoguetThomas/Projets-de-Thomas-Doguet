package datamining;

import java.util.Set;

import modelling.BooleanVariable;

//interface permettant d'acceder a une regle d'association
public interface AssociationRuleMiner{
    public BooleanDatabase getDatabase();
    public Set<AssociationRule> extract(float minFrequency, float minConfidence);
}