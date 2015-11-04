import java.util.ArrayList;

//Overview: to find all the possible ways to transfer the message
public class FindPath implements Runnable{
	private int G[][];			//TODO
	private int maxSize;		//TODO
	private ArrayList<ArrayList <Integer> > path = null;		//store the path of every sender
	
	private int INF = 0x3ffffff;
	
	/*  requires: some exist variables, such as g, totalmachine, path
	 *  modifiers: G, maxSize, path
	 *  effects: these variables are changed
	 */ 
	public void init(int [][]G, int totalMachine, ArrayList< ArrayList <Integer> > path){	//TODO
		this.G = G;
		this.maxSize = totalMachine + 1;
		this.path = path;
		ArrayList<Integer> p = new ArrayList<Integer>();
		path.add(p);
	}
	
	//effects: return the value of G
	public int[][] getG(){
		return G;
	}
	
	/*  requires: none
	 *  modifiers: none
	 *  effects: find all the possible paths 
	 */ 
	public void traversePoints(){
		for(int i = 1; i < maxSize; i++){
			pathway(i);
		}
	}
	
	//repOK != null
	public boolean repOK(){
		return (path != null);
	}

	/*  requires: a point u
	 *  modifiers: path
	 *  effects: find all the paths for vertex u 
	 */ 
	public void pathway(int u){
		
		ArrayList<Integer> p = new ArrayList<Integer>();
		p.add(521);
		this.path.add(u, p);
		
		boolean [] visited = new boolean [maxSize];
		int [] dist = new int [maxSize];
		int [] near = new int [maxSize];
		
		for(int i = 0; i < maxSize; i++){		//init
			visited[i] = false;
			near[i] = -1;
			dist[i] = INF;
		}
		int min = INF, v = -1;
		
		for(int i = 1; i < maxSize; i++){
			dist[i] = G[u][i];
			if(dist[i] != INF){
				near[i] = u;
			}
		}
		dist[u] = 0;  
	    visited[u] = true;  
	    
	    for(int i = 1; i < maxSize; i++){
	    	min = INF;
	    	for(int j = 1; j < maxSize; j++){
	    		if(visited[j] != true && dist[j] < min){
	    			min = dist[j];
	    			v = j;
	    		}
	    	}
	    		
	    	if(v != -1){
//	    		p.add(v);
	    		visited[v] = true;
	    		
	    		for(int j = 1; j < maxSize; j++){
	    			if(visited[j] != true &&  dist[v] + G[v][j] < dist[j]){
	    				dist[j] = dist[v] + G[v][j];
	    		        near[j] = v;
	    			}
	    		}
	    	}
	    	
	    }
	    for(int i = 1; i < maxSize; i++){
	    	p.add(near[i]);
	    }
	    
	}

	/*  requires: none
	 *  modifiers: none
	 *  effects: start the thread 
	 */ 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ArrayList<ArrayList <Integer> > path = null;
			for(int ii = 1; ii < maxSize; ii++){
				ArrayList<Integer> p = new ArrayList<Integer>();
				p.add(521);
				path.add(ii, p);
				
				boolean [] visited = new boolean [maxSize];
				int [] dist = new int [maxSize];
				int [] near = new int [maxSize];
				
				for(int i = 0; i < maxSize; i++){
					visited[i] = false;
					near[i] = -1;
					dist[i] = INF;
				}
				int min = INF, v = -1;
				
				for(int i = 1; i < maxSize; i++){
					dist[i] = G[ii][i];
					if(dist[i] != INF){
						near[i] = ii;
					}
				}
				dist[ii] = 0;  
			    visited[ii] = true;  
			    
			    for(int i = 1; i < maxSize; i++){
			    	min = INF;
			    	for(int j = 1; j < maxSize; j++){
			    		if(visited[j] != true && dist[j] < min){
			    			min = dist[j];
			    			v = j;
			    		}
			    	}
			    		
			    	if(v != -1){
			    		visited[v] = true;
			    		
			    		for(int j = 1; j < maxSize; j++){
			    			if(visited[j] != true &&  dist[v] + G[v][j] < dist[j]){
			    				dist[j] = dist[v] + G[v][j];
			    		        near[j] = v;
			    			}
			    		}
			    	}
			    	
			    }
			    for(int i = 1; i < maxSize; i++){
			    	p.add(near[i]);
			    }
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error! Please restart again!");
			System.exit(1);
		}
	}
	
}