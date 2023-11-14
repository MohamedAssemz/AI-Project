import java.util.*;
import java.util.stream.Collectors;

public class GenericSearch{
    public static int expansions = 0;
    public static  int currentDepth;
    
    public static HashSet<String> visitedStates = new HashSet<>();


    public static boolean isStateVisited(String state) {
        return visitedStates.contains(state);
    }

    public static void markStateVisited(String state) {
        visitedStates.add(state);
    }

    public static void clearVisitedStates() {
        visitedStates.clear();
    }


    public static Node GeneralSearch(State initialState, String strategy) {


        Node initialNode = new Node(initialState, null, new Action(
            "Initialize Node",
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            false,
            false,
            false,
            0,
            0,
            0
        ),
        0
        );

        if(initialNode.state.getEnergy() == 0 || initialNode.state.getFood() == 0 || initialNode.state.getMaterials() == 0 || initialNode.state.getMoney() == 0){
            return null;
        }

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
            expansions++;

            if(node!=null && !isStateVisited(node.VisitedString())){
                if (LLAPSearch.visuals) {
                    if(node.parent!=null){
                        System.out.println("Parent state: " + node.parent.toString()  + "\n");
                    }
                    System.out.println("Current state: " + node.toString()  + "\n");
                    System.out.println("Remaining nodes in queue: " + queue.size()  + "\n");                }  
                
                if (isGoalState(node.getState()) ) {
                    if (LLAPSearch.visuals) {
                        System.out.println("Goal Node: " + node.toString() + "\n");
                        System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                        System.out.println("--------------------------------------------");
                    }
                    return node; // Goal state found
                }

                markStateVisited(node.VisitedString());
            
                List<Node> successors = expand(node);
                queue.addAll(successors);


                if(LLAPSearch.visuals && !queue.isEmpty()){
                    System.out.println("Next Action: " + queue.peek().action.getName()  + "\n");
                    System.out.println("--------------------------------------------");
                }

            }else{
                if(node == null && LLAPSearch.visuals){
                    System.out.println("Null Node");
                }else if(isStateVisited(node.VisitedString()) && LLAPSearch.visuals){
                    System.out.println("Already Visited Node: " + node.toString()  + "\n");
                }
            }
        }

        return null; // Goal state not found
    }

    private static Node depthFirstSearch(Node initialNode) {
        Stack<Node> stack = new Stack<>();
        stack.push(initialNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            expansions++;

            if(node!=null){
                if (LLAPSearch.visuals) {
                    if(node.parent!=null){
                        System.out.println("Parent state: " + node.parent.toString()  + "\n");
                    }
                    System.out.println("Current state: " + node.toString()  + "\n");
                    System.out.println("Remaining nodes in queue: " + stack.size()  + "\n");
                }
                if (isGoalState(node.getState())) {
                    if (LLAPSearch.visuals) {
                        System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                        System.out.println("--------------------------------------------");
                    }
                    return node; // Goal state found
                }

                List<Node> successors = expand(node);
                stack.addAll(successors);

                if(LLAPSearch.visuals && !stack.isEmpty()){
                    System.out.println("Next Action: " + stack.peek().action.getName()  + "\n");
                    System.out.println("--------------------------------------------");
                }
                
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
            expansions++;

            if(node!=null && !isStateVisited(node.VisitedString()) && node.getDepth() <= currentDepth){
                if (LLAPSearch.visuals) {
                    if(node.parent!=null){
                        System.out.println("Parent state: " + node.parent.toString()  + "\n");
                    }
                    System.out.println("Current state: " + node.toString()  + "\n");
                    System.out.println("Remaining nodes in queue: " + stack.size()  + "\n");
                }
                if (isGoalState(node.getState())) {
                    if (LLAPSearch.visuals) {
                        System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                        System.out.println("--------------------------------------------");
                    }
                    return node; // Goal state found
                }

                markStateVisited(node.VisitedString());

                List<Node> successors = expand(node);
                stack.addAll(successors);

                if(LLAPSearch.visuals && !stack.isEmpty()){
                    System.out.println("Next Action: " + stack.peek().action.getName()  + "\n");
                    System.out.println("--------------------------------------------");
                }
                
            }else if(node.getDepth() > currentDepth){
                stack.removeAllElements();
                clearVisitedStates();
            }

        }
        if(currentDepth>1000) {
            return null;
        }
           currentDepth = currentDepth + 1;
           stack.push(initialNode);
       }
    }

    private static Node uniformCostSearch(Node initialNode) {

        //PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getPathCost()));

        priorityQueue.add(initialNode);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            if (node!=null){
            if( !isStateVisited(node.VisitedString())){
                if (LLAPSearch.visuals) {
                    if(node.parent!=null){
                        System.out.println("Parent state: " + node.parent.toString()  + "\n");
                    }
                    System.out.println("Current state: " + node.toString()  + "\n");
                    System.out.println("Remaining nodes in queue: " + priorityQueue.size()  + "\n");

                }
                if (isGoalState(node.getState())) {
                    if (LLAPSearch.visuals) {
                        System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                        System.out.println("--------------------------------------------");
                    }
                    return node; // Goal state found
                }else{
                    markStateVisited(node.VisitedString());
                }
            
            List<Node> successors = expand(node);
            priorityQueue.addAll(successors);

            if(LLAPSearch.visuals && !priorityQueue.isEmpty()){
                System.out.println("Next Action: " + priorityQueue.peek().action.getName()  + "\n");
                System.out.println("--------------------------------------------");
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
                if (LLAPSearch.visuals) {
                    System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                    System.out.println("--------------------------------------------");
                }
                return node; // Goal state found
            }

            List<Node> successors = expand(node);
            priorityQueue.addAll(successors);

            if (LLAPSearch.visuals && !priorityQueue.isEmpty()) {
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
                if (LLAPSearch.visuals) {
                    System.out.println("Prosperity: " + node.state.getProsperity() + "\n");
                    System.out.println("--------------------------------------------");
                }
                return node; // Goal state found
              }

              List<Node> successors = expand(node);
              priorityQueue.addAll(successors);

            if (LLAPSearch.visuals && !priorityQueue.isEmpty()) {
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

        public static int getPathCostProsperity(Node currentNode) {
    	int cost=0;
    	while (currentNode.depth!=0) {
    		cost+=currentNode.action.getProsperityChange();
    		currentNode=currentNode.parent;  		
    		
    	}
    	// cost for the root node action
    	cost+=currentNode.action.getProsperityChange();
    	return cost;	
    }
    
    
    public static int heuristic1(Node currentNode) {
    	// the heuristic assumes that the minimum cost to reach p.level=100 is the price of the min. build actions 
    	// to reach that level of prosperity
    	
    	// prosperity difference 
    	int prosDiff=100-currentNode.state.getProsperity();
    	//minimum No. of builds 
    	int minBuild;
    	// minimum cost needed 
    	int minCost;
    	if ( prosDiff==0 || prosDiff<0) {
    		minCost=0;
    	}
    
    	
    	if(LLAPSearch.prosperityBUILD1>LLAPSearch.prosperityBUILD2) {
           minBuild=prosDiff/LLAPSearch.prosperityBUILD1;
     	   minCost=minBuild*(LLAPSearch.priceBUILD1+LLAPSearch.foodUseBUILD1*LLAPSearch.foodPrice+LLAPSearch.materialsPrice*LLAPSearch.materialsUseBUILD1+LLAPSearch.energyPrice*LLAPSearch.energyUseBUILD1);

    	}
    	else {
    	  minBuild=prosDiff/LLAPSearch.prosperityBUILD2;
    	  minCost=minBuild*(LLAPSearch.priceBUILD2+LLAPSearch.foodPrice*LLAPSearch.foodUseBUILD2+LLAPSearch.materialsPrice*LLAPSearch.materialsUseBUILD2
          +LLAPSearch.energyPrice*LLAPSearch.energyUseBUILD2);
    	  minBuild=prosDiff/LLAPSearch.prosperityBUILD2;
    	}
    	return minCost;
    	
    	
    	
    }
    

    public static List<Node> expand(Node node) {
        List<Node> children = new ArrayList<>();

        if (node.action.getDelay() > 1 ){
            if(node.state.getFood() >= 1 && node.state.getMaterials() >= 1 && node.state.getEnergy() >= 1 && ((node.getState().getMoney_spent() + LLAPSearch.energyPrice + LLAPSearch.foodPrice + LLAPSearch.materialsPrice) <= 100000)){
                Node var = LLAPSearch.wait(node);
                if(var != null){
                    children.add(var);
                }
            }
            if (node.state.getFood() > LLAPSearch.foodUseBUILD1 && node.state.getMaterials() > LLAPSearch.materialsUseBUILD1 && node.state.getEnergy() > LLAPSearch.energyUseBUILD1 && (node.state.getMoney_spent() + (LLAPSearch.priceBUILD1 + (LLAPSearch.energyUseBUILD1 * LLAPSearch.energyPrice) + (LLAPSearch.materialsUseBUILD1 * LLAPSearch.materialsPrice) + (LLAPSearch.foodUseBUILD1 * LLAPSearch.foodPrice)) < 100000)){
                Node var = LLAPSearch.build1(node);
                if(var != null){
                    children.add(var);
                }
            }
            if (node.state.getFood() > LLAPSearch.foodUseBUILD2 && node.state.getMaterials() > LLAPSearch.materialsUseBUILD2 && node.state.getEnergy() > LLAPSearch.energyUseBUILD2 && (node.state.getMoney_spent() + (LLAPSearch.priceBUILD2 + (LLAPSearch.energyUseBUILD2 * LLAPSearch.energyPrice) + (LLAPSearch.materialsUseBUILD2 * LLAPSearch.materialsPrice) + (LLAPSearch.foodUseBUILD2 * LLAPSearch.foodPrice)) < 100000)) {
                Node var = LLAPSearch.build2(node);
                if(var != null){
                    children.add(var);
                }
            }
        }else if (node.getAction().getDelay() == 1 || node.getAction().getDelay() == 0){
            if (node.state.getFood() > LLAPSearch.foodUseBUILD1 && node.state.getMaterials() > LLAPSearch.materialsUseBUILD1 && node.state.getEnergy() > LLAPSearch.energyUseBUILD1 && (node.state.getMoney_spent() + (LLAPSearch.priceBUILD1 + (LLAPSearch.energyUseBUILD1 * LLAPSearch.energyPrice) + (LLAPSearch.materialsUseBUILD1 * LLAPSearch.materialsPrice) + (LLAPSearch.foodUseBUILD1 * LLAPSearch.foodPrice)) < 100000)){
                Node var = LLAPSearch.build1(node);
                if(var != null){
                    children.add(var);
                }
            }
            if (node.state.getFood() > LLAPSearch.foodUseBUILD2 && node.state.getMaterials() > LLAPSearch.materialsUseBUILD2 && node.state.getEnergy() > LLAPSearch.energyUseBUILD2 && (node.state.getMoney_spent() + (LLAPSearch.priceBUILD2 + (LLAPSearch.energyUseBUILD2 * LLAPSearch.energyPrice) + (LLAPSearch.materialsUseBUILD2 * LLAPSearch.materialsPrice) + (LLAPSearch.foodUseBUILD2 * LLAPSearch.foodPrice)) < 100000)) {
                Node var = LLAPSearch.build2(node);
                if(var != null){
                    children.add(var);
                }
            }
            if ((node.state.getFood() >= 1 && node.state.getMaterials() >= 1 && node.state.getEnergy() >= 1) && ((node.getState().getMoney_spent() + LLAPSearch.energyPrice + LLAPSearch.foodPrice + LLAPSearch.materialsPrice) <= 100000)){
                Node var = LLAPSearch.requestFood(node);
                Node var2 = LLAPSearch.requestEnergy(node);
                Node var3 = LLAPSearch.requestMaterials(node);
                if(var != null){
                    children.add(var);
                }

                if(var2 != null){
                    children.add(var2);
                }

                if(var3 != null){
                    children.add(var3);
                }
            }
            
        } 

        if(LLAPSearch.visuals){
            System.out.println("Prosperity: " + node.state.getProsperity() );
            System.out.println("Children: " + children.size() );
        }
        
        return children;
    }


    private static boolean isGoalState(State state) {
        // Define the goal state condition
        return state.getProsperity() >= 100;
    }
}
