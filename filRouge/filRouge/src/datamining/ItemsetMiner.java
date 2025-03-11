package datamining;

import java.util.Set;

import modelling.BooleanVariable;

//interface declarant une methode permettant d'acceder a une base de donnée transctionnelle et une autre servant a extraire les itelsets frequents

public interface ItemsetMiner{
    public BooleanDatabase getDatabase();
    public Set<Itemset> extract(float frequence);
}