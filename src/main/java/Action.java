public class Action {
    private String name;
    private int amount;
    private int delay;
    private int price;
    private int foodChange;
    private int materialsChange;
    private int energyChange;
    private int prosperityChange;
    private boolean FoodDelay;
    private boolean EnergyDelay;
    private boolean MaterialsDelay;
    private int FoodAmount;
    private int EnergyAmount;
    private int MaterialsAmount;

    public Action(String name, int amount, int delay, int price, int foodChange, int materialsChange, int energyChange, int prosperityChange, boolean FoodDelay, boolean EnergyDelay, boolean MaterialsDelay, int FoodAmount, int EnergyAmount, int MaterialsAmount) {
        this.name = name;
        this.amount = amount;
        this.delay = delay;
        this.price = price;
        this.foodChange = foodChange;
        this.materialsChange = materialsChange;
        this.energyChange = energyChange;
        this.prosperityChange = prosperityChange;
        this.FoodDelay = FoodDelay;
        this.EnergyDelay = EnergyDelay;
        this.MaterialsDelay = MaterialsDelay;
        this.FoodAmount = FoodAmount;
        this.EnergyAmount = EnergyAmount;
        this.MaterialsAmount = MaterialsAmount;
    }


    public int getFoodAmount() {
        return FoodAmount;
    }

    public void setFoodAmount(int FoodAmount) {
        this.FoodAmount = FoodAmount;
    }

    public int getEnergyAmount() {
        return EnergyAmount;
    }

    public void setEnergyAmount(int EnergyAmount) {
        this.EnergyAmount = EnergyAmount;
    }

    public int getMaterialsAmount() {
        return MaterialsAmount;
    }

    public void setMaterialsAmount(int MaterialsAmount) {
        this.MaterialsAmount = MaterialsAmount;
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
