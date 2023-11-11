import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


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

        public static int foodTB = 0;
        public static int materialsTB = 0;
        public static int energyTB = 0;

        public static boolean visuals;

    public static String solve(String initialState, String strategy, boolean visualize) {
        visuals = visualize;
        State initial = parseInitialState(initialState);

        Node node = GeneralSearch(initial, strategy);

        int cost = 0;
        List<String> nodes = new LinkedList<>(); 
        List<String> plan = new LinkedList<>();

        String answer = "";

        while(node != null){
            cost = cost + node.getAction().getPrice();
            plan.add(0, node.getAction().getName());
            nodes.add(0, node.toString());
            node = node.parent;
        }

        answer = plan + "," + cost + "," + nodes; 

        return answer;
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
            prosperity, food, materials, energy, 100000
        );
    }

    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    public static Node requestFood(Node node) {
        if (node.state.getFood() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() - 1,
                    node.state.getMaterials() - 1,
                    node.state.getEnergy() - 1,
                    node.state.getMoney() - energyPrice - foodPrice - materialsPrice
                ),
                node,
                new Action(
                    "Request Food",
                    amountRequestFood,
                    delayRequestFood,
                    energyPrice + foodPrice + materialsPrice,
                    -1,
                    -1,
                    -1,
                    0
                ),
                node.getDepth() + 1
            );
        }
        foodTB = amountRequestFood;;
        return null;
    }

    public static Node requestMaterials(Node node) {
        if (node.state.getMaterials() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() - 1,
                    node.state.getMaterials() - 1,
                    node.state.getEnergy() - 1,
                    node.state.getMoney() - energyPrice - foodPrice - materialsPrice
                ),
                node,
                new Action(
                    "Request Materials",
                    amountRequestMaterials,
                    delayRequestMaterials,
                    energyPrice + foodPrice + materialsPrice,
                    -1,
                    -1,
                    -1,
                    0
                ),
                node.getDepth() + 1
            );
        }
        materialsTB = amountRequestMaterials;
        return null;
    }

    public static Node requestEnergy(Node node){
        if (node.state.getEnergy() < 50) {
            return new Node(
                new State(
                    node.state.getProsperity(),
                    node.state.getFood() - 1,
                    node.state.getMaterials() - 1,
                    node.state.getEnergy() - 1,
                    node.state.getMoney() - energyPrice - foodPrice - materialsPrice
                ),
                node,
                new Action(
                    "Request Energy",
                    amountRequestEnergy,
                    delayRequestEnergy,
                    energyPrice + foodPrice + materialsPrice,
                    -1,
                    -1,
                    -1,
                    0
                ),
                node.getDepth() + 1
            );
        }
        energyTB = amountRequestEnergy;
        return null;
    }

    public static Node wait(Node node) {
        int tempF = foodTB; 
        int tempM = materialsTB;
        int tempE = energyTB;

        foodTB = 0;
        materialsTB = 0;
        energyTB = 0;

        return new Node(
            new State(
                node.state.getProsperity(),
                node.state.getFood() + tempF - 1,
                node.state.getMaterials() + tempM - 1,
                node.state.getEnergy() + tempE - 1,
                node.state.getMoney() - energyPrice - foodPrice - materialsPrice
            ),
            node,
            new Action(
                "Wait",
                0,
                min(node.action.getDelay() - 1,0),
                energyPrice + foodPrice + materialsPrice,
                tempF - 1,
                tempM - 1,
                tempE - 1,
                0
            ),
            node.getDepth() + 1
        );
    }

    public static Node build1(Node node) {
        int tempF = foodTB; 
        int tempM = materialsTB;
        int tempE = energyTB;

        foodTB = 0;
        materialsTB = 0;
        energyTB = 0;

        if (node.state.getFood() >= foodUseBUILD1 && node.state.getMaterials() >= materialsUseBUILD1 && node.state.getEnergy() >= energyUseBUILD1 && node.state.getMoney() >= priceBUILD1) {
            return new Node(
                new State(
                    node.state.getProsperity() + prosperityBUILD1,
                    node.state.getFood() + tempF - foodUseBUILD1,
                    node.state.getMaterials() + tempM - materialsUseBUILD1,
                    node.state.getEnergy() + tempE - energyUseBUILD1,
                    node.state.getMoney() - priceBUILD1 - (energyUseBUILD1 * energyPrice) - (materialsUseBUILD1 * materialsPrice) - (foodUseBUILD1 * foodPrice)
                ),
                node,
                new Action(
                    "Build 1",
                    1,
                    1,
                    priceBUILD1 + (energyUseBUILD1 * energyPrice) + (materialsUseBUILD1 * materialsPrice) + (foodUseBUILD1 * foodPrice),
                    foodUseBUILD1,
                    materialsUseBUILD1,
                    energyUseBUILD1,
                    prosperityBUILD1
                ),
                node.getDepth() + 1
            );
        }
        return null;
    }

    public static Node build2(Node node) {
        int tempF = foodTB; 
        int tempM = materialsTB;
        int tempE = energyTB;

        foodTB = 0;
        materialsTB = 0;
        energyTB = 0;

        if (node.state.getFood() >= foodUseBUILD2 && node.state.getMaterials() >= materialsUseBUILD2 && node.state.getEnergy() >= energyUseBUILD2 && node.state.getMoney() >= priceBUILD1) {
            return new Node(
                new State(
                    node.state.getProsperity() + prosperityBUILD2,
                    node.state.getFood() + tempF - foodUseBUILD2,
                    node.state.getMaterials() + tempM - materialsUseBUILD2,
                    node.state.getEnergy() + tempE - energyUseBUILD2,
                    node.state.getMoney() - priceBUILD2 - (energyUseBUILD2 * energyPrice) - (materialsUseBUILD2 * materialsPrice) - (foodUseBUILD2 * foodPrice)
                ),
                node,
                new Action(
                    "Build 2",
                    1,
                    1,
                    priceBUILD2 + (energyUseBUILD2 * energyPrice) + (materialsUseBUILD2 * materialsPrice) + (foodUseBUILD2 * foodPrice),
                    foodUseBUILD2,
                    materialsUseBUILD2,
                    energyUseBUILD2,
                    prosperityBUILD2
                ),
                node.getDepth() + 1
                
            );
        }
        return null;
    }

    public static void main(String[] args) {
        String initialState0 = "17;" +
        "49,30,46;" +
        "7,57,6;" +
        "7,1;20,2;29,2;" +
        "350,10,9,8,28;" +
        "408,8,12,13,34;";


        String solution = solve(initialState0, "UC", false);

        System.out.println("Solution found!");
        System.out.println(solution);
        System.out.println(visitedStates);

        //LLAPPlanChecker pc = new LLAPPlanChecker(initialState0);
		//assertTrue("The output actions do not lead to a goal state.", pc.applyPlan(initialState0, solution));

	}

}
