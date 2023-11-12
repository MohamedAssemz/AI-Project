import java.util.*;
import java.util.stream.Collectors;

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
    public static  int currentDepth;
    
    public static Map<String, Boolean> visitedStates = new HashMap<>();


    public static boolean isStateVisited(String state) {
        return visitedStates.containsKey(state);
    }

    public static void markStateVisited(String state) {
        visitedStates.put(state, true);
    }

    public static void clearVisitedStates() {
        visitedStates.clear();
    }


    public static Node GeneralSearch(State initialState, String strategy) {

        System.out.println("Visualize: " + LLAPSearch.visuals);


        Node initialNode = new Node(initialState, null, new Action(
            "Initialize Node",
            0,
            0,
            0,
            0,
            0,
            0,
            0
        ),
        0
        );

        if (strategy.equals("BF")) {
            return breadthFirstSearch(initialNode);
        } 
        else if (strategy.equals("DF")) {
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

            if(node!=null && !isStateVisited(node.toString())){
                
                if (isGoalState(node.getState()) ) {
                    return node; // Goal state found
                }

                markStateVisited(node.toString());
            
                List<Node> successors = expand(node);
                queue.addAll(successors);
            }

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString()  + "\n");
                System.out.println("Remaining nodes in queue: " + queue.size()  + "\n");
            }  
        }

        return null; // Goal state not found
    }

    private static Node depthFirstSearch(Node initialNode) {
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if(node!=null && !isStateVisited(node.toString())){
                if (isGoalState(node.getState())) {
                    return node; // Goal state found
                }

                markStateVisited(node.toString());

                List<Node> successors = expand(node);
                stack.addAll(successors);
                
            }

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString()  + "\n");
                System.out.println("Remaining nodes in queue: " + stack.size()  + "\n");
            }
        }

        return null; // Goal state not found
    }

    private static Node iterativeDeepeningSearch(Node initialNode) {
        Stack<Node> stack = new Stack<>();
        currentDepth = 0;
  
       while(true) {
        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if(node!=null && !isStateVisited(node.toString())){
                if (isGoalState(node.getState())) {
                    return node; // Goal state found
                }

                markStateVisited(node.toString());

                List<Node> successors = expand(node);
                stack.addAll(successors);
                
            }

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString()  + "\n");
                System.out.println("Remaining nodes in queue: " + stack.size()  + "\n");
            }
        }
        if(currentDepth>1000) {
            return null;
        }
           currentDepth+=1;
           stack.push(initialNode);
       }
    }

    private static Node uniformCostSearch(Node initialNode) {

        //Comparator<Node> priceComparator = Comparator.comparingInt(Node::getPathCost);

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());

        priorityQueue.add(initialNode);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            if (node!=null){
            if( !isStateVisited(node.toString())){
                if (isGoalState(node.getState())) {
                    return node; // Goal state found
                }else{
                    markStateVisited(node.toString());
                }
            
            List<Node> successors = expand(node);
            if (successors != null) {
                // Filter out null elements before adding to the PriorityQueue
                priorityQueue.addAll(successors.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            }

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString()  + "\n");
                System.out.println("Remaining nodes in queue: " + priorityQueue.size()  + "\n");
            }
        }
        
        }
        }

        return null; // Goal state not found
    }

    private static Node greedySearch(Node initialNode, int heuristicIndex) {
        // Implement greedy search with the specified heuristic
        // You'll need to use a priority queue based on the heuristic value
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> heuristic1(node)));
        priorityQueue.add(initialNode);
        
        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            if (isGoalState(node.getState())) {
                return node; // Goal state found
            }

            List<Node> successors = expand(node);
            priorityQueue.addAll(successors);

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString() + "\n");
                System.out.println("Remaining nodes in queue: " + priorityQueue.size() + "\n");
            }
        }
    	
    	
        return null;
    }

    private static Node aStarSearch(Node initialNode, int heuristicIndex) {
        // Implement A* search with the specified heuristic
        // You'll need to use a priority queue based on the sum of the path cost and heuristic value
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> getPathCost(node)+heuristic1(node)));
        priorityQueue.add(initialNode);
          
          while (!priorityQueue.isEmpty()) {
              Node node = priorityQueue.poll();

              if (isGoalState(node.getState())) {
                  return node; // Goal state found
              }

              List<Node> successors = expand(node);
              priorityQueue.addAll(successors);

            if (LLAPSearch.visuals) {
                System.out.println("Current state: " + node.toString());
                System.out.println("Remaining nodes in queue: " + priorityQueue.size());
            }
          }
      	
      	
          return null;
    	
    }
    public static int getPathCost(Node currentNode) {
    	int cost=0;
    	while (currentNode.depth!=0) {
    		cost+=currentNode.action.getPrice();
    		currentNode=currentNode.parent;  		
    		
    	}
    	// cost for the root node action
    	cost+=currentNode.action.getPrice();
    	return cost;
    	
    	
    	
    }
    
    
    private static int heuristic1(Node currentNode) {
    	// the heuristic assumes that the minimum cost to reach p.level=100 is the price of the min. build actions 
    	// to reach that level of prosperity
    	
    	// prosperity difference 
    	int prosDiff=100-currentNode.state.getProsperity();
    	//minimum No. of builds 
    	int minBuild;
    	// minimum cost needed 
    	int minCost;
    	
    	if(prosperityBUILD1>prosperityBUILD2) {
           minBuild=prosDiff/prosperityBUILD1;
     	   minCost=minBuild*(priceBUILD1+foodUseBUILD1+materialsUseBUILD1+energyUseBUILD1);

    	}
    	else {
    	  minBuild=prosDiff/prosperityBUILD2;
    	  minCost=minBuild*(priceBUILD2+foodUseBUILD2+materialsUseBUILD2+energyUseBUILD2);
    	}
    	return minCost;
    	
    	
    	
    }
    

    public static List<Node> expand(Node node) {
        List<Node> children = new ArrayList<>();

        if (node != null && node.action.getDelay() > 0 && node.state.getFood() > 0 && node.state.getMaterials() > 0 && node.state.getEnergy() > 0 && node.state.getMoney() > 0){
            children.add(LLAPSearch.wait(node));
            children.add(LLAPSearch.build1(node));
            children.add(LLAPSearch.build2(node));
        }else if (node != null && node.action.getDelay() == 0 && node.state.getFood() > 0 && node.state.getMaterials() > 0 && node.state.getEnergy() > 0 && node.state.getMoney() > 0){
            children.add(LLAPSearch.requestFood(node));
            children.add(LLAPSearch.requestMaterials(node));
            children.add(LLAPSearch.requestEnergy(node));
            children.add(LLAPSearch.build1(node));
            children.add(LLAPSearch.build2(node));
        }

        while(children.remove(null));

        for(int i=0; i<children.size(); i++){
            if(isStateVisited(children.get(i).toString())){
                children.remove(i);
            }
        }

        System.out.println("Action: " + node.action.getName() + "\n");
        System.out.println("Children: " + children + "\n");
        System.out.println("--------------------------------------------");
        
        return children;
    }


    private static boolean isGoalState(State state) {
        // Define the goal state condition
        return state.getProsperity() >= 100;
    }
}
