import java.util.*;

public class GenericSearch{
    public static int foodPrice = LLAPSearch.foodPrice;
    public static int materialsPrice = LLAPSearch.materialsPrice;
    public static int energyPrice = LLAPSearch.energyPrice;

    public static int amountRequestFood = LLAPSearch.amountRequestFood;
    public static int delayRequestFood = LLAPSearch.delayRequestFood;

    public static int amountRequestMaterials = LLAPSearch.amountRequestMaterials;
    public static int delayRequestMaterials = LLAPSearch.delayRequestMaterials;

    public static int amountRequestEnergy = LLAPSearch.amountRequestEnergy;
    public static int delayRequestEnergy = LLAPSearch.delayRequestEnergy;

    public static int priceBUILD1 = LLAPSearch.priceBUILD1;
    public static int foodUseBUILD1 = LLAPSearch.foodUseBUILD1;
    public static int materialsUseBUILD1 = LLAPSearch.materialsUseBUILD1;
    public static int energyUseBUILD1 = LLAPSearch.energyUseBUILD1;
    public static int prosperityBUILD1 = LLAPSearch.prosperityBUILD1;

    public static int priceBUILD2 = LLAPSearch.priceBUILD2;
    public static int foodUseBUILD2 = LLAPSearch.foodUseBUILD2;
    public static int materialsUseBUILD2 = LLAPSearch.materialsUseBUILD2;
    public static int energyUseBUILD2 = LLAPSearch.energyUseBUILD2;
    public static int prosperityBUILD2 = LLAPSearch.prosperityBUILD2;


    public static Node GeneralSearch(State initialState, String strategy) {

        Node initialNode = new Node(initialState, null, null);

        if (strategy.equals("BFS")) {
            return breadthFirstSearch(initialNode);
        } 
        else if (strategy.equals("DFS")) {
            return depthFirstSearch(initialNode);
        }
        else if (strategy.equals("ID")) {
            return iterativeDeepeningSearch(initialNode);
        } 
        else if (strategy.equals("UC")) {
            return uniformCostSearch(initialNode);
        } 
        else if (strategy.startsWith("GR")) {
            int heuristicIndex = Integer.parseInt(strategy.substring(2)); // Extract heuristic index
            return greedySearch(initialNode, heuristicIndex);
        } 
        else if (strategy.startsWith("AS")) {
            int heuristicIndex = Integer.parseInt(strategy.substring(2)); // Extract heuristic index
            return aStarSearch(initialNode, heuristicIndex);
        }
        else{
            System.out.println("Strategy not recognized");
            return null;
        }
    }

    private static Node breadthFirstSearch(Node initialNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(initialNode);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (isGoalState(node.getState())) {
                return node; // Goal state found
            }

            List<Node> successors = expand(node);
            queue.addAll(successors);
        }

        return null; // Goal state not found
    }

    private static Node depthFirstSearch(Node initialNode) {
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (isGoalState(node.getState())) {
                return node; // Goal state found
            }

            List<Node> successors = expand(node);
            stack.addAll(successors);
        }

        return null; // Goal state not found
    }

    private static Node iterativeDeepeningSearch(Node initialNode) {
        // Implement iterative deepening search
        // You'll need to repeatedly perform depth-first searches with increasing depth limits
        return null;
    }

    private static Node uniformCostSearch(Node initialNode) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getState().getProsperity()));

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            if (isGoalState(node.getState())) {
                return node; // Goal state found
            }

            List<Node> successors = expand(node);
            priorityQueue.addAll(successors);
        }

        return null; // Goal state not found
    }

    private static Node greedySearch(Node initialNode, int heuristicIndex) {
        // Implement greedy search with the specified heuristic
        // You'll need to use a priority queue based on the heuristic value
        return null;
    }

    private static Node aStarSearch(Node initialNode, int heuristicIndex) {
        // Implement A* search with the specified heuristic
        // You'll need to use a priority queue based on the sum of the path cost and heuristic value
        return null;
    }

    public static List<Node> expand(Node node) {
        List<Node> children = new ArrayList<>();

        if (node.action.getDelay() > 0){
            children.add(LLAPSearch.wait(node));
            children.add(LLAPSearch.build1(node));
            children.add(LLAPSearch.build2(node));
        }else if (node.action.getDelay() == 0){
            children.add(LLAPSearch.requestFood(node));
            children.add(LLAPSearch.requestMaterials(node));
            children.add(LLAPSearch.requestEnergy(node));
            children.add(LLAPSearch.wait(node));
            children.add(LLAPSearch.build1(node));
            children.add(LLAPSearch.build2(node));
        }

        return children;
    }

    private static boolean isGoalState(State state) {
        // Define the goal state condition
        return state.getProsperity() == 100;
    }
}
