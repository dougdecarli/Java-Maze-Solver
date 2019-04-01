package labII.trabGB.com;

import java.io.File;
import java.io.IOException;

public class MazeTest {

	public static void main(String[] args) {
		IMaze maze = new Maze();
		
		try {
			maze.load(new File("C:\\Users\\Douglas\\Downloads\\maze1 (1).txt"));
			//maze.load(new File("/Users/progittecnologia/Downloads/maze3.txt"));
			//maze.load(new File("C:\\Users\\SUARIO\\Downloads\\maze1.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("BEFORE FINDOUT");
		maze.show();
		maze.findOut();
		System.out.println();
		System.out.println();
		System.out.println("AFTER FINDOUT");
		maze.show();
		System.out.println();
		maze.showTracking();
	}

}
