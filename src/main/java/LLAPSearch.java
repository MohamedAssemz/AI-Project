import java.util.List;

public class LLAPSearch extends GenericSearch {
    public static String solve(String initialState, String strategy, boolean visualize) {
        State initial = parseInitialState(initialState);
        return search(initial, strategy);
    }

    private static State parseInitialState(String initialState) {
        String[] parts = initialState.split(";");
        int prosperity = Integer.parseInt(parts[0]);

        String[] resourceLevels = parts[1].split(",");
        int food = Integer.parseInt(resourceLevels[0]);
        int materials = Integer.parseInt(resourceLevels[1]);
        int energy = Integer.parseInt(resourceLevels[2]);

        String[] resourcePrices = parts[2].split(",");
        int foodPrice = Integer.parseInt(resourcePrices[0]);
        int materialsPrice = Integer.parseInt(resourcePrices[1]);
        int energyPrice = Integer.parseInt(resourcePrices[2]);

        String[] foodRequest = parts[3].split(",");
        int amountRequestFood = Integer.parseInt(foodRequest[0]);
        int delayRequestFood = Integer.parseInt(foodRequest[1]);

        String[] materialsRequest = parts[4].split(",");
        int amountRequestMaterials = Integer.parseInt(materialsRequest[0]);
        int delayRequestMaterials = Integer.parseInt(materialsRequest[1]);

        String[] energyRequest = parts[5].split(",");
        int amountRequestEnergy = Integer.parseInt(energyRequest[0]);
        int delayRequestEnergy = Integer.parseInt(energyRequest[1]);

        String[] build1Params = parts[6].split(",");
        int priceBUILD1 = Integer.parseInt(build1Params[0]);
        int foodUseBUILD1 = Integer.parseInt(build1Params[1]);
        int materialsUseBUILD1 = Integer.parseInt(build1Params[2]);
        int energyUseBUILD1 = Integer.parseInt(build1Params[3]);
        int prosperityBUILD1 = Integer.parseInt(build1Params[4]);
        
        String[] build2Params = parts[7].split(",");
        int priceBUILD2 = Integer.parseInt(build2Params[0]);
        int foodUseBUILD2 = Integer.parseInt(build2Params[1]);
        int materialsUseBUILD2 = Integer.parseInt(build2Params[2]);
        int energyUseBUILD2 = Integer.parseInt(build2Params[3]);
        int prosperityBUILD2 = Integer.parseInt(build2Params[4]);

        return new State(
            prosperity, food, materials, energy, 100000,
            foodPrice, materialsPrice, energyPrice,
            amountRequestFood, delayRequestFood,
            amountRequestMaterials, delayRequestMaterials,
            amountRequestEnergy, delayRequestEnergy,
            priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1,
            priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2
        );
    }
}
