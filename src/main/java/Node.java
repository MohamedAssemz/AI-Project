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
        return "Money: " + this.getState().getMoney() + " Food: " +  
        this.getState().getFood() + " Energy: " + 
        this.getState().getEnergy() + " Material: " + 
        this.getState().getMaterials() + " Prosperity: " + this.getState().getProsperity() + " Delay: " + this.getAction().getDelay() + Delays  + " Food Amount: "  + this.getAction().getFoodAmount() + " Energy Amount: "  + this.getAction().getEnergyAmount() + " Material Amount: "  + this.getAction().getMaterialsAmount();
    }

    public String toString(){
        String Delays = " FoodDelay: " + this.getAction().isFoodDelay() + " EnergyDelay: " + this.getAction().isEnergyDelay() + " MaterialDelay: " + this.getAction().isMaterialsDelay();
        return "Money: " + this.getState().getMoney() + " Food: " +  
        this.getState().getFood() + " Energy: " + 
        this.getState().getEnergy() + " Material: " + 
        this.getState().getMaterials() + " Prosperity: " + this.getState().getProsperity() + " Action: " + this.getAction().getName() + " Delay Flags: " + this.getAction().getDelay() + Delays  + " Food Amount: "  + this.getAction().getAmount() + " Energy Amount: "  + this.getAction().getEnergyAmount() + " Material Amount: "  + this.getAction().getMaterialsAmount();
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

    public int getPathCost() {
        Node temp = this;
    	int cost=0;
    	while (temp.depth!=0) {
    		cost+=temp.action.getPrice();
    		temp=temp.parent;  			
    	}
    	// cost for the root node action
    	cost+=temp.action.getPrice();
    	return cost;
    }
}
