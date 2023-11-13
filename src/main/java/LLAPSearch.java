
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

        public static boolean visuals = false;

    public static String search(String initialState, String strategy, boolean visualize) {
        visuals = visualize;
        State initial = parseInitialState(initialState);

        Node node = GeneralSearch(initial, strategy);
        String answer = "";

        if(node == null){
            answer = "NOSOLUTION";
            return answer;
        }else if(node != null){

            int cost = 0;
            List<String> nodes = new LinkedList<>(); 
            List<String> plan = new LinkedList<>();
            String p = "";
            int NE = visitedStates.size();

            

            while(node != null){
                cost = cost + node.getAction().getPrice();
                plan.add(0, node.getAction().getName());
                nodes.add(0, node.toString());
                node = node.parent;
            }

            plan.remove(0);

            while(plan.size() > 1){
                p = p + plan.remove(0) + ",";
            }

            p = p + plan.remove(0);

            answer = p + ";" + cost + ";" + NE; 
            //System.out.println("Solution Found!");

        }

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

    public static int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static int min(int a, int b){
        if(a < b){
            return a;
        }else{
            return b;
        }
    }

    public static Node requestFood(Node node) {
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
        }
        return new Node(
            new State(
                node.state.getProsperity(),
                min(node.state.getFood() - 1  + tempF,50),
                min(node.state.getMaterials() - 1 + tempM,50),
                min(node.state.getEnergy() - 1 + tempE,50),
                node.state.getMoney() - energyPrice - foodPrice - materialsPrice
            ),
            node,
            new Action(
                "RequestFood",
                amountRequestFood,
                delayRequestFood,
                energyPrice + foodPrice + materialsPrice,
                -1,
                -1,
                -1,
                0,
                true,
                false,
                false,
                amountRequestFood,
                0,
                0
            ),
            node.getDepth() + 1
        );
    }

    public static Node requestMaterials(Node node) {
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
        }
 
        return new Node(
            new State(
                node.state.getProsperity(),
                min(node.state.getFood() - 1  +tempF,50),
                min(node.state.getMaterials() - 1 + tempM,50),
                min(node.state.getEnergy() - 1 + tempE,50),
                node.state.getMoney() - energyPrice - foodPrice - materialsPrice
            ),
            node,
            new Action(
                "RequestMaterials",
                amountRequestMaterials,
                delayRequestMaterials,
                energyPrice + foodPrice + materialsPrice,
                -1,
                -1,
                -1,
                0,
                false,
                false,
                true,
                0,
                0,
                amountRequestMaterials
            ),
            node.getDepth() + 1
        );
    }

    public static Node requestEnergy(Node node){
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
        }

        return new Node(
            new State(
                node.state.getProsperity(),
                min(node.state.getFood() - 1  +tempF,50),
                min(node.state.getMaterials() - 1 + tempM,50),
                min(node.state.getEnergy() - 1 + tempE,50),
                node.state.getMoney() - energyPrice - foodPrice - materialsPrice
            ),
            node,
            new Action(
                "RequestEnergy",
                amountRequestEnergy,
                delayRequestEnergy,
                energyPrice + foodPrice + materialsPrice,
                -1,
                -1,
                -1,
                0,
                false,
                true,
                false,
                0,
                amountRequestEnergy,
                0
            ),
            node.getDepth() + 1
        );
    }

    public static Node wait(Node node) {
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        int tempF2 = 0;
        int tempE2 = 0;
        int tempM2 = 0;

        boolean delayfood = false;
        boolean delayenergy = false;
        boolean delaymaterials = false;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
            tempF2 = node.getAction().getFoodAmount();
            delayfood = node.getAction().isFoodDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
            tempE2 = node.getAction().getEnergyAmount();
            delayenergy = node.getAction().isEnergyDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
            tempM2 = node.getAction().getMaterialsAmount();
            delaymaterials = node.getAction().isMaterialsDelay();
        }

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
                "WAIT",
                0,
                max(node.action.getDelay() - 1,0),
                energyPrice + foodPrice + materialsPrice,
                tempF - 1,
                tempM - 1,
                tempE - 1,
                0,
                delayfood,
                delayenergy,
                delaymaterials,
                tempF2,
                tempE2,
                tempM2
            ),
            node.getDepth() + 1
        );
    }

    public static Node build1(Node node) {
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        int tempF2 = 0;
        int tempE2 = 0;
        int tempM2 = 0;

        boolean delayfood = false;
        boolean delayenergy = false;
        boolean delaymaterials = false;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
            tempF2 = node.getAction().getFoodAmount();
            delayfood = node.getAction().isFoodDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
            tempE2 = node.getAction().getEnergyAmount();
            delayenergy = node.getAction().isEnergyDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
            tempM2 = node.getAction().getMaterialsAmount();
            delaymaterials = node.getAction().isMaterialsDelay();
        }
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
                "BUILD1",
                0,
                max(node.action.getDelay() - 1,0),
                priceBUILD1 + (energyUseBUILD1 * energyPrice) + (materialsUseBUILD1 * materialsPrice) + (foodUseBUILD1 * foodPrice),
                foodUseBUILD1,
                materialsUseBUILD1,
                energyUseBUILD1,
                prosperityBUILD1,
                delayfood,
                delayenergy,
                delaymaterials,
                tempF2,
                tempE2,
                tempM2
            ),
            node.getDepth() + 1
        );
    }

    public static Node build2(Node node) {
        int tempF = 0;
        int tempE = 0;
        int tempM = 0;

        int tempF2 = 0;
        int tempE2 = 0;
        int tempM2 = 0;

        boolean delayfood = false;
        boolean delayenergy = false;
        boolean delaymaterials = false;

        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isFoodDelay()){
            tempF = node.getAction().getFoodAmount(); 
        }else{
            tempF = 0;
            tempF2 = node.getAction().getFoodAmount();
            delayfood = node.getAction().isFoodDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isEnergyDelay()){
            tempE = node.getAction().getEnergyAmount();
        }else{
            tempE = 0;
            tempE2 = node.getAction().getEnergyAmount();
            delayenergy = node.getAction().isEnergyDelay();
        }
        if(node.parent != null && node.action.getDelay() == 0 && node.getAction().isMaterialsDelay()){
            tempM = node.getAction().getMaterialsAmount();
        }else{
            tempM = 0;
            tempM2 = node.getAction().getMaterialsAmount();
            delaymaterials = node.getAction().isMaterialsDelay();
        }

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
                "BUILD2",
                0,
                max(node.action.getDelay() - 1,0),
                priceBUILD2 + (energyUseBUILD2 * energyPrice) + (materialsUseBUILD2 * materialsPrice) + (foodUseBUILD2 * foodPrice),
                foodUseBUILD2,
                materialsUseBUILD2,
                energyUseBUILD2,
                prosperityBUILD2,
                delayfood,
                delayenergy,
                delaymaterials,
                tempF2,
                tempE2,
                tempM2
            ),
            node.getDepth() + 1
            
        );
    }

    public static void main(String[] args) {
        String initialState0 = "17;" +
                "49,30,46;" +
                "7,57,6;" +
                "7,1;20,2;29,2;" +
                "350,10,9,8,28;" +
                "408,8,12,13,34;";
        String initialState1 = "50;" +
                "12,12,12;" +
                "50,60,70;" +
                "30,2;19,2;15,2;" +
                "300,5,7,3,20;" +
                "500,8,6,3,40;";
        String initialState2 = "30;" +
                "30,25,19;" +
                "90,120,150;" +
                "9,2;13,1;11,1;" +
                "3195,11,12,10,34;" +
                "691,7,8,6,15;";
        String initialState3 = "0;" +
                "19,35,40;" +
                "27,84,200;" +
                "15,2;37,1;19,2;" +
                "569,11,20,3,50;"+
                "115,5,8,21,38;" ;

        String initialState4 = "21;" +
                "15,19,13;" +
                "50,50,50;" +
                "12,2;16,2;9,2;" +
                "3076,15,26,28,40;" +
                "5015,25,15,15,38;";
        String initialState5 = "72;" +
                "36,13,35;" +
                "75,96,62;" +
                "20,2;5,2;33,2;" +
                "30013,7,6,3,36;" +
                "40050,5,10,14,44;";
        String initialState6 = "29;" +
                "14,9,26;" +
                "650,400,710;" +
                "20,2;29,2;38,1;" +
                "8255,8,7,9,36;" +
                "30670,12,12,11,36;";
        String initialState7= "1;" +
            "6,10,7;" +
            "2,1,66;" +
            "34,2;22,1;14,2;" +
            "1500,5,9,9,26;" +
            "168,13,13,14,46;";
        String initialState8 = "93;" +
            "46,42,46;" +
            "5,32,24;" +
            "13,2;24,1;20,1;" +
            "155,7,5,10,7;" +
            "5,5,5,4,4;";
        String initialState9 = "50;" +
            "20,16,11;" +
            "76,14,14;" +
            "7,1;7,1;7,1;" +
            "359,14,25,23,39;" +
            "524,18,17,17,38;";
        String initialState10= "32;" +
            "20,16,11;" +
            "76,14,14;" +
            "9,1;9,2;9,1;" +
            "358,14,25,23,39;" +
            "5024,20,17,17,38;";


        String solution = search(initialState0, "DF", true);

        System.out.println(solution);
        //System.out.println("Visited Nodes: " + visitedStates.size());

        //LLAPPlanChecker pc = new LLAPPlanChecker(initialState0);
		//assertTrue("The output actions do not lead to a goal state.", pc.applyPlan(initialState0, solution));

	}

}
