import java.util.*;   
   
   class NodeComparator implements Comparator<Node>{
             
            // Overriding compare()method of Comparator 
                        // for descending order of cgpa
            public int compare(Node s1, Node s2) {
                
                if (GenericSearch.getPathCostProsperity(s1) < GenericSearch.getPathCostProsperity(s2))
                    return 1;
                else if (GenericSearch.getPathCostProsperity(s1) > GenericSearch.getPathCostProsperity(s2))
                    return -1;
                    //
                return 0;
                }
        }