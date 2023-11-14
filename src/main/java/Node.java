import java.util.Objects;

public class Node {
    protected State state;
    protected Node parent;
    protected Action action;
    protected int depth;

    public Node(State state, Node parent, Action action, int depth) {
        this.state = state;
        this.parent = parent;
        this.action = action; 
        this.depth = depth;
    }

    public String VisitedString(){
        String Delays = " FoodDelay: " + this.getAction().isFoodDelay() + " EnergyDelay: " + this.getAction().isEnergyDelay() + " MaterialDelay: " + this.getAction().isMaterialsDelay();
        return "Money: " + this.getState().getMoney() + 
        " Food: " +  this.getState().getFood() + 
        " Energy: " + this.getState().getEnergy() + 
        " Material: " + this.getState().getMaterials() + 
        " Prosperity: " + this.getState().getProsperity() +
        " Delay: " + this.getAction().getDelay() + Delays  + 
        " Amount Delayed: " + this.getAction().getAmount() +
        " Money Spent: "  + GenericSearch.getPathCost(this);
    }

    public String toString(){
        String Delays = " FoodDelay: " + this.getAction().isFoodDelay() + " EnergyDelay: " + this.getAction().isEnergyDelay() + " MaterialDelay: " + this.getAction().isMaterialsDelay();
        return "Money: " + this.getState().getMoney() + 
        " Food: " +  this.getState().getFood() + 
        " Energy: " + this.getState().getEnergy() + 
        " Material: " + this.getState().getMaterials() + 
        " Prosperity: " + this.getState().getProsperity() + 
        " Action: " + this.getAction().getName() + 
        " Delay: " + this.getAction().getDelay() + Delays  + 
        " Delay Amount: " + this.action.getAmount() + 
        " Depth: "  + this.getDepth();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getPathCost(){
        return GenericSearch.getPathCost(this);
    }
}
