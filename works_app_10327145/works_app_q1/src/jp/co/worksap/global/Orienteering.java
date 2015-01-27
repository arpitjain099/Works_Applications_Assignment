package jp.co.worksap.global;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Orienteering {

	public static int input(Scanner scanner, char[][] map, int w, int h){
		int sum = 0;
		String in = "";
		int i = 0, j = 0;
		scanner.nextLine();
		char c = ' ';
		while(scanner.hasNextLine()){
			in = scanner.nextLine();
			for(i = 0; i < w; i++){
				c = in.charAt(i);
				map[j][i] = c;
				if(c == 'S' || c == 'G' || c == '@'){
					sum ++;
				}
			}
			j ++;
			if(j >= h){
				break;
			}
		}
		return sum;
	}
	
	public static List<int[]> fetchArray(int[] coca)
    {   
		int[] new_data = null;
        List<int[]> list = new ArrayList<int[]> ();            
        if(coca ==null){
       	 return list;
        }
        if (coca.length == 1)
        {           
            list.add(coca);
        } else {
            int[] da = new int[coca.length - 1];
            for (int i = 0; i < coca.length - 1; i++)
            {
                da[i] = coca[i + 1];
            }
            List<int[]> listOld = fetchArray(da); 			
       
            int newNum = coca[0];
            
            for (int i = 0; i < listOld.size(); i++)           	
            {
                for (int j = 0; j <= listOld.get(i).length; j++)  	
                {
                    new_data = new int[listOld.get(i).length + 1];
                    new_data[j] = newNum;
                    int currentIndex = 0;
                    for (int n = 0; n < listOld.get(i).length; n++)
                    {
                        if (currentIndex == j)
                            currentIndex++;
                        new_data[currentIndex] = listOld.get(i)[n];
                        currentIndex++;
                    }
                    list.add(new_data);
                }
            }
        }
        return list;
    }
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int w = scanner.nextInt();
		int h = scanner.nextInt();
		char[][] map = new char[h][w];
		int sum = input(scanner, map, w, h);
		int num = h * w;					
		char[] input = new char[num];
		int[] inp = new int[sum];			
		char c = ' ';
		final int max_i = Integer.MAX_VALUE;
		int[][] array = new int[num][num];
		int[][] path = new int[num][num];
		int i_x = 0, i_y = 0, j_x = 0, j_y = 0,swap,max=0,allstep=0,i=0,j=0,letter=0;
		for(i = 0; i < h; i++){
			for(j = 0; j < w; j++){
				c = map[i][j];
				input[i * w + j] = c;
				
				if(c=='@'){
					inp[letter] = i * w + j;
					letter ++;
				}
					else if(c=='S'){
					inp[letter] = i * w + j;
					swap = inp[0];
					inp[0] = inp[letter];
					inp[letter] = swap;
					letter ++;
					}
					else if(c=='G'){
					inp[letter] = i * w + j;
					swap = inp[sum - 1];
					inp[sum - 1] = inp[letter];
					inp[letter] = swap;
					}
				
			}
		}
		
		
		for(i = 0; i < num; i++){
			i_x = i / w;
			i_y = i % w;
			for(j = 0; j < num; j++){
				j_x = j/w;
				j_y = j%w;
				if(i == j){
					array[i][j] = 0;
				}
				else if( i_x == j_x && Math.abs(j_y - i_y) == 1 && input[i] != '#' && input[j] != '#'){
					array[i][j] = 1;
				}
				else if( i_y == j_y && Math.abs(j_x - i_x) == 1 && input[i] != '#' && input[j] != '#')
				{
					array[i][j] = 1;
				}
				else{
					array[i][j] = max_i;
				}
			}
		}
		
		for ( i = 0; i < num; i++) {
			for ( j = 0; j < num; j++) {
				path[i][j] = i;
			}
		}
		
		for (int k = 0; k < num; k++) {
			for (i = 0; i < num; i++) {
				for ( j = 0; j < num; j++) {
					
					if(array[i][k] != max_i && array[k][j] != max_i)
						max=array[i][k] + array[k][j];
					else max=max_i;
					if (array[i][j] > max) {
						path[i][j] = path[k][j];
						array[i][j] = max;
					}
				}
			}
		}
		
		int mMax = max_i;
		
		int[] data = new int[sum-2];			
		for(i = 1; i < sum-1; i++){
			data[i-1] = inp[i];
		}
		List<int[]> list = null;
		if(data.length != 0){
			list = fetchArray(data);
		}
		if(list != null){
			for(i = 0; i < list.size(); i++){
				int[] num_temp = list.get(i);
				for(j = 0; j < num_temp.length; j++){
					inp[j+1] = num_temp[j];		
				}
				allstep = 0;
				for(j = 0; j < sum-1; j++){
					allstep += array[inp[j]][inp[j+1]];
				}
				if(mMax > allstep)
					mMax=allstep;
			}
		}else{
			mMax =  array[inp[0]][inp[sum-1]];
		}
		System.out.println(mMax);
	}
}