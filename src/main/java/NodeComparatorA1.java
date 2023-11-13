import java.util.*;   
   
   class NodeComparatorA1 implements Comparator<Node>{
             
            // Overriding compare()method of Comparator 
                        // for descending order of cgpa
            public int compare(Node s1, Node s2) {
                
                if (GenericSearch.heuristic1(s1)+GenericSearch.getPathCost(s1) < GenericSearch.heuristic1(s2)+GenericSearch.getPathCost(s2))
                    return -1;
                else if (GenericSearch.heuristic1(s1)+GenericSearch.getPathCost(s1) > GenericSearch.heuristic1(s2)+GenericSearch.getPathCost(s2))
                    return 1;
                    //
                return 0;
                }
        }