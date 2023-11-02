import java.util.List;

public class LLAPSearch extends GenericSearch {

        public static int foodPrice;
        public static int materialsPrice;
        public static int energyPrice;

        public static int amountRequestFood;
        public static int delayRequestFood;

        public static int amountRequestMaterials;
        public static int delayRequestMaterials;

        public static int amountRequestEnergy;
        public static int delayRequestEnergy;


        public static int priceBUILD1;
        public static int foodUseBUILD1;
        public static int materialsUseBUILD1;
        public static int energyUseBUILD1;
        public static int prosperityBUILD1;

        public static int priceBUILD2;
        public static int foodUseBUILD2;
        public static int materialsUseBUILD2;
        public static int energyUseBUILD2;
        public static int prosperityBUILD2;

    public static Node solve(String initialState, String strategy, boolean visualize) {
        State initial = parseInitialState(initialState);
        return GeneralSearch(initial, strategy);
    }

    private static State parseInitialState(String initialState) {
        String[] parts = initialState.split(";");
        int prosperity = Integer.parseInt(parts[0]);

        String[] resourceLevels = parts[1].split(",");
        int food = Integer.parseInt(resourceLevels[0]);
        int materials = Integer.parseInt(resourceLevels[1]);
        int energy = Integer.parseInt(resourceLevels[2]);

        String[] resourcePrices = parts[2].split(",");
        foodPrice = Integer.parseInt(resourcePrices[0]);
        materialsPrice = Integer.parseInt(resourcePrices[1]);
        energyPrice = Integer.parseInt(resourcePrices[2]);

        String[] foodRequest = parts[3].split(",");
        amountRequestFood = Integer.parseInt(foodRequest[0]);
        delayRequestFood = Integer.parseInt(foodRequest[1]);

        String[] materialsRequest = parts[4].split(",");
        amountRequestMaterials = Integer.parseInt(materialsRequest[0]);
        delayRequestMaterials = Integer.parseInt(materialsRequest[1]);

        String[] energyRequest = parts[5].split(",");
        amountRequestEnergy = Integer.parseInt(energyRequest[0]);
        delayRequestEnergy = Integer.parseInt(energyRequest[1]);

        String[] build1Params = parts[6].split(",");
        priceBUILD1 = Integer.parseInt(build1Params[0]);
        foodUseBUILD1 = Integer.parseInt(build1Params[1]);
        materialsUseBUILD1 = Integer.parseInt(build1Params[2]);
        energyUseBUILD1 = Integer.parseInt(build1Params[3]);
        prosperityBUILD1 = Integer.parseInt(build1Params[4]);
        
        String[] build2Params = parts[7].split(",");
        priceBUILD2 = Integer.parseInt(build2Params[0]);
        foodUseBUILD2 = Integer.parseInt(build2Params[1]);
        materialsUseBUILD2 = Integer.parseInt(build2Params[2]);
        energyUseBUILD2 = Integer.parseInt(build2Params[3]);
        prosperityBUILD2 = Integer.parseInt(build2Params[4]);

        return new State(
            prosperity, food, materials, energy, 1000000
        );
    }

    public static Node requestFood(Node node) {
        if (node.state.getFood() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() + amountRequestFood - 1,
                    node.state.getMaterials() - 1,
                    node.state.getEnergy() - 1,
                    node.state.getMoney() - (amountRequestFood * foodPrice)
                ),
                node,
                new Action(
                    "Request Food",
                    amountRequestFood,
                    delayRequestFood,
                    amountRequestFood * foodPrice,
                    node.state.getFood() - amountRequestFood,
                    -1,
                    -1,
                    0
                )
            );
        }
        return null;
    }

    public static Node requestMaterials(Node node) {
        if (node.state.getMaterials() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() - 1,
                    node.state.getMaterials() + amountRequestMaterials - 1,
                    node.state.getEnergy() - 1,
                    node.state.getMoney() - (amountRequestMaterials * materialsPrice)
                ),
                node,
                new Action(
                    "Request Materials",
                    amountRequestMaterials,
                    delayRequestMaterials,
                    amountRequestMaterials * materialsPrice,
                    0,
                    node.state.getMaterials() - amountRequestMaterials,
                    0,
                    0
                )
            );
        }
        return null;
    }

    public static Node requestEnergy(Node node){
        if (node.state.getEnergy() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() - 1,
                    node.state.getMaterials() - 1,
                    node.state.getEnergy() + amountRequestEnergy - 1,
                    node.state.getMoney() - (amountRequestEnergy * energyPrice)
                ),
                node,
                new Action(
                    "Request Energy",
                    amountRequestEnergy,
                    delayRequestEnergy,
                    amountRequestEnergy * energyPrice,
                    0,
                    0,
                    node.state.getEnergy() - amountRequestEnergy,
                    0
                )
            );
        }
        return null;
    }

    public static Node wait(Node node) {
        return new Node(
            new State(
                node.state.getProsperity(),
                node.state.getFood() - 1,
                node.state.getMaterials() - 1,
                node.state.getEnergy() - 1,
                node.state.getMoney()
            ),
            node,
            new Action(
                "Wait",
                0,
                1,
                0,
                0,
                0,
                0,
                0
            )
        );
    }

    public static Node build1(Node node) {
        if (node.state.getFood() >= foodUseBUILD1 && node.state.getMaterials() >= materialsUseBUILD1 && node.state.getEnergy() >= energyUseBUILD1) {
            return new Node(
                new State(
                    node.state.getProsperity() + prosperityBUILD1,
                    node.state.getFood() - foodUseBUILD1,
                    node.state.getMaterials() - materialsUseBUILD1,
                    node.state.getEnergy() - energyUseBUILD1,
                    node.state.getMoney() - priceBUILD1
                ),
                node,
                new Action(
                    "Build 1",
                    1,
                    1,
                    priceBUILD1,
                    foodUseBUILD1,
                    materialsUseBUILD1,
                    energyUseBUILD1,
                    prosperityBUILD1
                )
            );
        }
        return null;
    }

    public static Node build2(Node node) {
        if (node.state.getFood() >= foodUseBUILD2 && node.state.getMaterials() >= materialsUseBUILD2 && node.state.getEnergy() >= energyUseBUILD2) {
            return new Node(
                new State(
                    node.state.getProsperity() + prosperityBUILD2,
                    node.state.getFood() - foodUseBUILD2,
                    node.state.getMaterials() - materialsUseBUILD2,
                    node.state.getEnergy() - energyUseBUILD2,
                    node.state.getMoney() - priceBUILD2
                ),
                node,
                new Action(
                    "Build 2",
                    1,
                    1,
                    priceBUILD2,
                    foodUseBUILD2,
                    materialsUseBUILD2,
                    energyUseBUILD2,
                    prosperityBUILD2
                )
            );
        }
        return null;
    }
}
