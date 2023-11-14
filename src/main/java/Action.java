public class Action {
    private String name;
    private int amount;
    private int delay;
    private boolean FoodDelay;
    private boolean EnergyDelay;
    private boolean MaterialsDelay;

    public Action(String name, int amount, int delay, boolean FoodDelay, boolean EnergyDelay, boolean MaterialsDelay) {
        this.name = name;
        this.amount = amount;
        this.delay = delay;
        this.FoodDelay = FoodDelay;
        this.EnergyDelay = EnergyDelay;
        this.MaterialsDelay = MaterialsDelay;
    }

    public boolean isFoodDelay() {
        return FoodDelay;
    }

    public void setFoodDelay(boolean FoodDelay) {
        this.FoodDelay = FoodDelay;
    }

    public boolean isEnergyDelay() {
        return EnergyDelay;
    }

    public void setEnergyDelay(boolean EnergyDelay) {
        this.EnergyDelay = EnergyDelay;
    }

    public boolean isMaterialsDelay() {
        return MaterialsDelay;
    }

    public void setMaterialsDelay(boolean MaterialsDelay) {
        this.MaterialsDelay = MaterialsDelay;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }       
}
