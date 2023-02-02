import java.io.*;
import java.util.*;

public class project4 {

    public static HashMap<String,Node> Nodes = new HashMap<>();
    public static void main(String[] args) throws IOException {
        long start1 = System.currentTimeMillis();
        FileReader input = new FileReader(args[0]);
        File output = new File(args[1]);
        BufferedReader reader = new BufferedReader(input);
        int no_Nodes =  Integer.parseInt(reader.readLine());
        int no_flags = Integer.parseInt(reader.readLine());
        String[] dest = reader.readLine().split(" ");
        String start = dest[0];
        String end = dest[1];
        HashSet<String> flags = new HashSet<>(Arrays.stream(reader.readLine().split(" ")).toList());
        for(int i=0;i<no_Nodes;i++){
            //Nodes are created and connections are established
            String[] arr = reader.readLine().split(" ");
            String new_Node = arr[0];
            if(!Nodes.containsKey(new_Node)){
                Nodes.put(new_Node,new Node(new_Node,flags.contains(new_Node)));
            }
            Node temp = Nodes.get(new_Node);
            for(int j=1;j<arr.length;j+=2){
                if(!Nodes.containsKey(arr[j])) {
                    Nodes.put(arr[j], new Node(arr[j], flags.contains(arr[j])));
                }
                Nodes.get(arr[j]).addCon(temp,Integer.parseInt(arr[j+1]));
                temp.addCon(Nodes.get(arr[j]), Integer.parseInt(arr[j + 1]));
            }
        }
        FileWriter write = new FileWriter(output);
        //shortest path
        String val1 = String.valueOf(pathfinder(Nodes.get(start), Nodes.get(end)));
        write.write(val1);
        write.write("\n");
        Iterator<String> a = flags.iterator();
        Node x =Nodes.get(a.next());
        //flag cutting
        String val2=String.valueOf(flag_2(x,no_flags));
        write.write(val2);
        write.close();
        reader.close();
        long end1 = System.currentTimeMillis();
        long runningTime = end1 - start1;
        System.out.println("Running Time: " + runningTime);
    }
    public static int pathfinder(Node start, Node end){
        int res = -1;
        if(start.equals(end)){
            return 0;
        }
        //in this function, I added first element's connected nodes to priority queue
        PriorityQueue<Node> find = new PriorityQueue<>(new NodeComparator1());
        for(Tuple i:start.getConnections()){
            i.first.dist1 = i.second;
            find.add(i.first);
        }
        start.isVisited1=true;
        //then I run the code until the priority queue is empty(means there is no path) or the current node is final destination
        while(!find.isEmpty()){
            Node cur = find.poll();
            if(cur.isVisited1){
                continue;
            }
            if(cur.equals(end)){
                res = end.dist1;
                break;
            }
            //updates connected nodes distances if their old distance is larger
            for(Tuple i:cur.getConnections()){
                if(!i.first.isVisited1) {
                    if(i.first.dist1>i.second+cur.dist1){
                        i.first.dist1 = i.second+cur.dist1;
                    }
                    find.add(i.first);
                }
            }
            cur.isVisited1 = true;
        }
        return res;
    }

    public static int flag_2(Node start,int no_of_flags){
        // In this function I use BFS to reach other flags
        // if one of the flags is unreachable function returns -1
        // else it reaches the closest flag makes the closest flag the current flag
        // the flag in the current node is removed
        // if all the flags are removed
        // total distance is returned
        int counter = 1;
        int res=0;
        while(counter<no_of_flags){
            boolean check=true;
            PriorityQueue<Tuple> pri = new PriorityQueue<>(new CustomComparator());
            HashSet<Node> vis = new HashSet<>();
            pri.addAll(start.getConnections());
            vis.add(start);
            start.isFlag=false;
            while(!pri.isEmpty()){
                Tuple x = pri.poll();

                if(x.first.isFlag){
                    counter++;
                    x.first.isFlag=false;
                    start.addCon(x.first,0);
                    x.first.addCon(start,0);
                    res+=x.second;
                    start = x.first;
                    check=false;
                    break;
                }
                if(!vis.contains(x.first)){

                    for(Tuple i:x.first.getConnections()){
                        pri.add(new Tuple(i.first,i.second+x.second));
                    }
                    vis.add(x.first);
                }
            }
            if(check){
                return -1;
            }

        }
        return res;
    }

}