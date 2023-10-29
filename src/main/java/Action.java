public class Action {
    private String name;
    private int amount;
    private int delay;
    private int price;
    private int foodChange;
    private int materialsChange;
    private int energyChange;
    private int prosperityChange;

    public Action(String name, int amount, int delay, int price, int foodChange, int materialsChange, int energyChange, int prosperityChange) {
        this.name = name;
        this.amount = amount;
        this.delay = delay;
        this.price = price;
        this.foodChange = foodChange;
        this.materialsChange = materialsChange;
        this.energyChange = energyChange;
        this.prosperityChange = prosperityChange;
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

    public int getPrice() {
        return price;
    }   

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFoodChange() {
        return foodChange;
    }

    public void setFoodChange(int foodChange) {
        this.foodChange = foodChange;
    }

    public int getMaterialsChange() {
        return materialsChange;
    }

    public void setMaterialsChange(int materialsChange) {
        this.materialsChange = materialsChange;
    }

    public int getEnergyChange() {
        return energyChange;
    }

    public void setEnergyChange(int energyChange) {
        this.energyChange = energyChange;
    }

    public int getProsperityChange() {
        return prosperityChange;
    }

    public void setProsperityChange(int prosperityChange) {
        this.prosperityChange = prosperityChange;
    }
}
