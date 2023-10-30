import java.util.*;

public class GenericSearch extends State{

    public static Node search(State initialState, String strategy) {

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
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(expand(initialNode)::getCost));
        priorityQueue.add(initialNode);

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

    private static List<Node> expand(Node node) {
        // Generate successor nodes from the current node
        // Implement actions and state transitions here
        List<Node> successors = new ArrayList<>();
        // Add logic to generate successor nodes
        return successors;
    }

    private static boolean isGoalState(State state) {
        // Define the goal state condition
        return state.getProsperity() == 100;
    }
}
