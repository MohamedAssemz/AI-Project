import java.util.*;

public class GenericSearch {
    public static String search(State initialState, String strategy) {
        switch (strategy) {
            case "BF":
                return breadthFirstSearch(initialState);
            case "DF":
                return depthFirstSearch(initialState);
            case "ID":
                return iterativeDeepeningSearch(initialState);
            case "UC":
                return uniformCostSearch(initialState);
            case "GR1":
                return greedySearch(initialState, heuristic1);
            case "GR2":
                return greedySearch(initialState, heuristic2);
            case "AS1":
                return aStarSearch(initialState, heuristic1);
            case "AS2":
                return aStarSearch(initialState, heuristic2);
            default:
                return "Invalid strategy";
        }
    }

    private static String breadthFirstSearch(State initialState) {
        Queue<Node> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.add(new Node(initialState, null, null));
        int nodesExpanded = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            visited.add(currentState);

            for (Action action : currentState.getActions()) {
                State nextState = applyAction(currentState, action);
                if (!visited.contains(nextState)) {
                    queue.add(new Node(nextState, currentNode, action));
                }
            }
        }

        return "NOSOLUTION";
    }

    private static String depthFirstSearch(State initialState) {
        Stack<Node> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        stack.push(new Node(initialState, null, null));
        int nodesExpanded = 0;

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            visited.add(currentState);

            for (Action action : currentState.getActions()) {
                State nextState = applyAction(currentState, action);
                if (!visited.contains(nextState)) {
                    stack.push(new Node(nextState, currentNode, action));
                }
            }
        }

        return "NOSOLUTION";
    }

    private static String iterativeDeepeningSearch(State initialState) {
        for (int depth = 0; ; depth++) {
            String result = depthLimitedSearch(initialState, depth);
            if (!result.equals("NOSOLUTION")) {
                return result;
            }
        }
    }

    private static String depthLimitedSearch(State state, int depthLimit) {
        Stack<Node> stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        stack.push(new Node(state, null, null));
        int nodesExpanded = 0;

        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            if (currentNode.getDepth() < depthLimit) {
                visited.add(currentState);

                for (Action action : currentState.getActions()) {
                    State nextState = applyAction(currentState, action);
                    if (!visited.contains(nextState)) {
                        stack.push(new Node(nextState, currentNode, action, currentNode.getDepth() + 1));
                    }
                }
            }
        }

        return "NOSOLUTION";
    }

    private static String uniformCostSearch(State initialState) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparing(Node::getCost));
        Set<State> visited = new HashSet<>();
        priorityQueue.add(new Node(initialState, null, null, 0));
        int nodesExpanded = 0;

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            visited.add(currentState);

            for (Action action : currentState.getActions()) {
                State nextState = applyAction(currentState, action);
                int cost = currentNode.getCost() + action.getCost();
                if (!visited.contains(nextState)) {
                    priorityQueue.add(new Node(nextState, currentNode, action, cost));
                }
            }
        }

        return "NOSOLUTION";
    }

    private static String greedySearch(State initialState, Heuristic heuristic) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> heuristic.apply(n.getState())));
        Set<State> visited = new HashSet<>();
        priorityQueue.add(new Node(initialState, null, null));
        int nodesExpanded = 0;

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            visited.add(currentState);

            for (Action action : currentState.getActions()) {
                State nextState = applyAction(currentState, action);
                if (!visited.contains(nextState)) {
                    priorityQueue.add(new Node(nextState, currentNode, action));
                }
            }
        }

        return "NOSOLUTION";
    }

    private static String aStarSearch(State initialState, Heuristic heuristic) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(n -> n.getCost() + heuristic.apply(n.getState())));
        Set<State> visited = new HashSet<>();
        priorityQueue.add(new Node(initialState, null, null, 0));
        int nodesExpanded = 0;

        while (!priorityQueue.isEmpty()) {
            Node currentNode = priorityQueue.poll();
            State currentState = currentNode.getState();
            nodesExpanded++;

            if (currentState.isGoal()) {
                return extractPlan(currentNode, nodesExpanded);
            }

            visited.add(currentState);

            for (Action action : currentState.getActions()) {
                State nextState = applyAction(currentState, action);
                int cost = currentNode.getCost() + action.getCost();
                if (!visited.contains(nextState)) {
                    priorityQueue.add(new Node(nextState, currentNode, action, cost));
                }
            }
        }

        return "NOSOLUTION";
    }

    private static State applyAction(State currentState, Action action) {
        int newProsperity = currentState.getProsperity() + action.getProsperityChange();
        int newFood = currentState.getFood() + action.getFoodChange();
        int newMaterials = currentState.getMaterials() + action.getMaterialsChange();
        int newEnergy = currentState.getEnergy() + action.getEnergyChange();
        int newMoney = currentState.getMoney() - action.getPrice();

        return new State(newProsperity, newFood, newMaterials, newEnergy, newMoney);
    }

    private static String extractPlan(Node goalNode, int nodesExpanded) {
        List<String> actions = new ArrayList<>();
        List<Integer> costs = new ArrayList<>();
        Node currentNode = goalNode;
        while (currentNode.getAction() != null) {
            actions.add(currentNode.getAction().getName());
            costs.add(currentNode.getAction().getPrice());
            currentNode = currentNode.getParent();
        }

        Collections.reverse(actions);
        Collections.reverse(costs);

        StringBuilder plan = new StringBuilder();
        for (int i = 0; i < actions.size(); i++) {
            plan.append(actions.get(i));
            if (i < actions.size() - 1) {
                plan.append(",");
            }
        }

        int monetaryCost = costs.stream().mapToInt(Integer::intValue).sum();
        return plan + ";" + monetaryCost + ";" + nodesExpanded;
    }

    // Implement the specified heuristics
    private static int heuristic1(State state) {
        // Heuristic 1: Estimate based on the remaining resources
        int remainingResources = state.getFood() + state.getMaterials() + state.getEnergy();
        return remainingResources;
    }

    private static int heuristic2(State state) {
        // Heuristic 2: A custom heuristic based on your project requirements
        // You can implement your own heuristic based on your problem domain
        // Return an estimate of the cost to reach the goal state from the given state
        // This is a placeholder; replace with your specific heuristic
        return 0;
    }
}
