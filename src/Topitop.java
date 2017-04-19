/*Yýldýrým Can Þehirlioðlu
 * 080302010
 * COMPE 323 HW2
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Topitop {

			public HashMap<Vertex, ArrayList<Vertex>> map;
			
			public Topitop(String filePath) {
				map = new HashMap<>();
				
				try {
						Scanner scanner = new Scanner(new FileReader(filePath));
						
						while (scanner.hasNextLine()) {
									String[] match = scanner.nextLine().split(" ");
									add(match[0], match[1]);
						}
						
						scanner.close();
				} catch (FileNotFoundException e) {
						e.printStackTrace();
				}
			}
			
			// Function 1, which reads the text file and constructs the graph of
			//courses as an adjacency list
			public void add(String keyID, String valueID){
					if ( !containsKey(keyID))
							map.put(new Vertex(keyID), new ArrayList<Vertex>());
					if ( !containsKey(valueID))
							map.put(new Vertex(valueID), new ArrayList<Vertex>());
					if ( !containsValue(keyID, valueID)){
							get(keyID).add(getKey(valueID));
					}
			}
			
			//Function 2 topologically sorts the courses given the graph of courses
			//as an adjacency list
			public void sort() {
					int time = 0;
					for (Vertex v : map.keySet())
							if (v.color.equals("white"))
									time = doDFSVisit(v, time);
					
			}
			
			private int doDFSVisit(Vertex vertex, int time) {
					vertex.color = "gray";
					time++;
					vertex.d = time;
					for (Vertex v : map.get(vertex)) {
							if (v.color.equals("white")) {
									v.parent = vertex;
									time = doDFSVisit(v, time);
							}
					}
					vertex.color = "black";
					time++;
					vertex.f = time;
					return time;
			}
			
			public ArrayList<Vertex> get(String keyID) {
					return map.get(getKey(keyID));
			}
			
			public Vertex getKey(String keyID) {
					for (Vertex v : map.keySet()) {
							if (v.id.equals(keyID))
								return v;
					}
					return null;
			}
			
			public boolean containsKey(String keyID) {
					return getKey(keyID) != null;
			}
			
			public boolean containsValue(String keyID, String valueID) {
					boolean result = false;
					Vertex key = getKey(keyID);
					if (key != null) {
							for (Vertex v : get(keyID))
									if (v.id.equals(valueID))
											result = true;
					}
					return result;
			}
			
			private class Vertex {
				
					public String id;
					public String color;
					public Vertex parent;
					public int d;
					public int f;
					
					Vertex(String id) {
							this.id = id;
							color = "white";
							parent = null;
							d = 0;
							f = 0;
					}
			}
			
			public static void main(String[] args) {
					Topitop topi = new Topitop("sample.txt");
					topi.sort();
					for (Vertex v : topi.map.keySet()) {
							System.out.println(v.id + " " + v.d + "/" + v.f);
					}
			}
}
