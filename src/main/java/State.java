public class State {
    private int prosperity;
    private int food;
    private int materials;
    private int energy;
    private int money;

    public State(int prosperity, int food, int materials, int energy, int money) {
        this.prosperity = prosperity;
        this.food = food;
        this.materials = materials;
        this.energy = energy;
        this.money = money;
    }

    public State(State currentState) {
        this.prosperity = currentState.prosperity;
        this.food = currentState.food;
        this.materials = currentState.materials;
        this.energy = currentState.energy;
        this.money = currentState.money;
    }

    public int getProsperity() {
        return prosperity;
    }

    public void setProsperity(int prosperity) {
        this.prosperity = prosperity;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMaterials() {
        return materials;
    }

    public void setMaterials(int materials) {
        this.materials = materials;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

}