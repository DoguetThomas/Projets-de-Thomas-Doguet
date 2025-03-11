package view;

import javax.swing.JFrame;
import java.awt.Dimension;

import java.util.Map;
import java.util.List;

import blockWorld.modelling.BWVariable;
import modelling.Variable;
import planning.Action;

import bwmodel.BWStateBuilder;
import bwmodel.BWState;
import bwui.BWIntegerGUI;
import bwui.BWComponent;

//mise en place de la vue
public class View {
    private BWVariable bwVariable;
    private Map<Variable, Object> instanciation;
    private String titre;
    private int n;

    //la vue prend en parametre les variables du monde de block et un etat 
    public View(String titre, BWVariable bwVariable, Map<Variable, Object> etat) {
        this.bwVariable = bwVariable;
        this.instanciation = etat;
        this.n = this.bwVariable.getOnbVar().size();
        this.titre = titre;
    }

    //cette méthode cree un etat grace a la librairie bwmodel
    public BWState<Integer> buildingState() {
        this.n = this.bwVariable.getOnbVar().size();
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(this.n);
        for (Variable onB : bwVariable.getOnbVar()) {
            int b = bwVariable.getIndex(onB);
            int under = (int) instanciation.get(onB);
            if (under >= 0)
                builder.setOn(b, under);
            }
        BWState<Integer> state = builder.getState();
        return state;
    }

    //cette methode crée une fenettre pour afficher un état du monde block grace a la librairie Bwui
    public void displaying(){
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(this.titre);
        frame.setLocation(0, 0);
        frame.setPreferredSize(new Dimension(500, 500));
        BWState<Integer> state = this.buildingState();
        frame.add(gui.getComponent(state));
        frame.pack();
        frame.setVisible(true);
    }

    //cette methode crée une fenettre pour afficher le deroulement d'un plan état par état grace a la librairie Bwui

    public void playingPlan(List<Action> actions){
        this.n = this.bwVariable.getOnbVar().size();
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(this.titre);
        frame.setLocation(0, 0);
        frame.setPreferredSize(new Dimension(500, 500));
        BWState<Integer> bwstate = this.buildingState();
        BWComponent<Integer> component = gui.getComponent(bwstate);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);
        for (Action a: actions) {
            try { Thread.sleep(1_000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            this.instanciation=a.successor(this.instanciation);
            component.setState(buildingState());
            }
        System.out.println("Simulation of plan: done.");
    }
}
