//import java.util.Arrays;
import java.util.ArrayList;
public class AstarSearch2 {

	public static void main(String[] args) {
		// First we need to initialize the map as a graph and the city as a node
		int Adj[][] = new int[20][20];
		Adj [0][1] = Adj [1][0]= 71;
		Adj [0][7] = Adj [7][0] = 151;
		Adj [1][2] = Adj [2][1] = 75;
		Adj [2][3] = Adj [3][2] = 118;
		Adj [2][7] = Adj [7][2] = 140;
		Adj [3][4] = Adj [4][3] = 111;
		Adj [4][5] = Adj [5][4] = 70;
		Adj [5][6] = Adj [6][5] = 75;
		Adj [6][9] = Adj [9][6] = 120;
		Adj [7][8] = Adj [8][7] = 80;
		Adj [7][10] = Adj [10][7] = 99;
		Adj [8][9] = Adj [9][8] = 146;
		Adj [8][11] = Adj [11][8] = 97;
		Adj [9][11] = Adj [11][9] = 138;
		Adj [10][12] = Adj [12][10] = 211;
		Adj [11][12] = Adj [12][11] = 101;
		Adj [12][13] = Adj [13][12] = 90;
		Adj [12][14] = Adj [14][12] = 85;
		Adj [14][15] = Adj [15][14] = 142;
		Adj [14][18] = Adj [18][14] = 98;
		Adj [15][16] = Adj [16][15] = 92;
		Adj [16][17] = Adj [17][16] = 87;
		Adj [18][19] = Adj [19][18] = 86;
		Node[] City = new Node[20];
		for(int i=0;i<20;i++){
			City[i] = new Node();
			City[i].Number=i;
		}
		City[0].Name ="Oradea";
		City[0].SDdistance = 380;
		City[1].Name ="Zerind";
		City[1].SDdistance = 374;
		City[2].Name ="Arad";
		City[2].SDdistance = 366;
		City[3].Name ="Timisoara";
		City[3].SDdistance = 329;
		City[4].Name ="Lugoj";
		City[4].SDdistance = 244;
		City[5].Name ="Mehadia";
		City[5].SDdistance = 241;
		City[6].Name ="Dobreta";
		City[6].SDdistance = 242;
		City[7].Name ="Sibiu";
		City[7].SDdistance = 253;
		City[8].Name ="Rimnicu Vilcea";
		City[8].SDdistance = 193;
		City[9].Name ="Cralova";
		City[9].SDdistance = 160;
		City[10].Name ="Fagaras";
		City[10].SDdistance = 178;
		City[11].Name ="Pitesti";
		City[11].SDdistance = 98;
		City[12].Name ="Bucharest";
		City[12].SDdistance = 0;
		City[13].Name ="Giurgiu";
		City[13].SDdistance = 77;
		City[14].Name ="Urziceni";
		City[14].SDdistance = 80;
		City[15].Name ="Vaslui";
		City[15].SDdistance = 199;
		City[16].Name ="Iasi";
		City[16].SDdistance = 226;
		City[17].Name ="Neamt";
		City[17].SDdistance = 234;
		City[18].Name ="Hirsova";
		City[18].SDdistance = 151;
		City[19].Name ="Eforie";
		City[19].SDdistance = 161;
//		for(int i=0;i<20;i++){
//		System.out.println(City[i].Name+""+City[i].Number);
//		}
		// Now we can use the A* search method to get the optimal path
		ArrayList<String> Path = new ArrayList<String>();
		ArrayList<String> Path1 = new ArrayList<String>();
		ArrayList<String> Path2 = new ArrayList<String>();
		int Adj0[][] = Adj;
		Path.add(City[1].Name);
		Path1.add(City[1].Name);
		Path2.add(City[1].Name);
		AstarSearch2 a = new AstarSearch2();	
		System.out.println("w=0.75 ");
		int ex = 1+a.AstarFind(0, City[1], City[12], City, Adj0, (float) 0.75, 0, Path);//ex is the number of expanded nodes, +1 is because we have to include the first node
		System.out.print("The number of expanded nodes are :  ");
		System.out.println(ex);

	}
	// g is g*(n) part of the evaluation function, d is weight of the function (w).  Cost is used to record cost. Path is used to record cities.
	public int AstarFind(float g, Node Start, Node Goal, Node[] City, int Adj[][],float d, int Cost, ArrayList <String> Path){
		float f=(g)*(1-d)+Start.SDdistance*d;
		int expNodes = 0;
		if (Start == Goal){
			System.out.println("The route is:");
			for (String element : Path){
				if (element == Path.get(Path.size()-1)){
				System.out.println(element);	
				}
				else
				System.out.print(element + "--> ");
				}
			System.out.print("The cost of the solution is: ");
			System.out.println(Cost);
			return expNodes++;
		}
		else {
			int j=0;
			int min = -1;
			for(int i=0;i<20;i++){
				//find the right son
				if(Adj[Start.Number][i]==0)continue;
				else{
				    j++;
				    if (j==1){
					f = (g+Adj[Start.Number][i])*(1-d)+City[i].SDdistance*d;
					min = i; // index of first no zero City[i]
				    }
				    else if (f>(g+Adj[Start.Number][i])*(1-d)+City[i].SDdistance*d){
				    	f = (g+Adj[Start.Number][i])*(1-d)+City[i].SDdistance*d;
				    	min = i;// if City[i] has a less f, it's index become the min
				    	//System.out.println(f);
				    }	
				}
			}
			if(min==-1){
				System.out.println(f);
			}
			g = Adj[Start.Number][min]+g;
			Cost = Cost + Adj[Start.Number][min];
			Path.add(City[min].Name);
			Adj[Start.Number][min]=Adj[min][Start.Number]=0;
		return  AstarFind(g, City[min], Goal, City, Adj, d, Cost, Path)+1 ;
		}
	}
}
