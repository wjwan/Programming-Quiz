package amazonOpt;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

public class ScheduleProcessTest {
	private HashMap<Integer, Integer> valueMap;
	private ArrayList<Content> contents;
	private ScheduleProcess scheduleProcess = new ScheduleProcess();
	
	@Before
	public void setUp() throws Exception {
		valueMap = new HashMap<Integer, Integer>();
		contents = new ArrayList<Content>();
		BufferedReader buffReader = new BufferedReader(new FileReader("Files/values.txt"));
		String line;
		while((line=buffReader.readLine())!=null){
			String[] strs = line.split(",");
			if(strs.length==2)
				valueMap.put(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
		}
		buffReader.close();
		
		buffReader = new BufferedReader(new FileReader("Files/contents.txt"));
		while((line=buffReader.readLine())!=null){
			String[] strs = line.split(",");
			if(strs.length==3){
				int id = Integer.parseInt(strs[0]);
				int value = valueMap.get(id);
				contents.add(new Content(id, Integer.parseInt(strs[1]), Integer.parseInt(strs[2]), value));
			}
		}
		buffReader.close();
	}
	
	
	@Test
	public void testGetSchedule() {
		HashSet<ScheduledContent> selectedContents = scheduleProcess.getSchedule(contents,1.0);
		int score = 0;
		Object[] contents = selectedContents.toArray();
		for(int i=0;i<contents.length;i++){
			ScheduledContent content = (ScheduledContent)contents[i];
			score += content.GetScore();
		}
		System.out.println(score);
		System.out.println("This is old version");
	}
	
	/*
	@Test
	public void testGetScheduleOnSelectHigherValueContent() {
		
		ArrayList<Content> contents = new ArrayList<Content>();
		contents.add(new Content(0,0,5,100));
		contents.add(new Content(1,0,5,80));
		contents.add(new Content(2,0,5,70));
		contents.add(new Content(3,0,5,60));
		contents.add(new Content(4,0,5,100));
		
		HashSet<ScheduledContent> selectedContents = scheduleProcess.getSchedule(contents,1.0);
		int score = 0;
		Object[] contentArr = selectedContents.toArray();
		for(int i=0;i<contentArr.length;i++){
			ScheduledContent content = (ScheduledContent)contentArr[i];
			score += content.GetScore();
		}
		System.out.println(score);
		
		assertEquals(score,1680);
	}
	
	@Test
	public void testGetScheduleOnRemoveSameAdvertisementContent() {
		
		ArrayList<Content> contents = new ArrayList<Content>();
		contents.add(new Content(0,0,5,100));
		contents.add(new Content(1,0,5,80));
		contents.add(new Content(2,0,5,70));
		contents.add(new Content(3,0,5,60));
		contents.add(new Content(0,0,5,100));
		
		HashSet<ScheduledContent> selectedContents = scheduleProcess.getSchedule(contents,1.0);
		int score = 0;
		Object[] contentArr = selectedContents.toArray();
		for(int i=0;i<contentArr.length;i++){
			ScheduledContent content = (ScheduledContent)contentArr[i];
			score += content.GetScore();
		}
		System.out.println(score);
		
		assertEquals(score,250*6);
	}
	
	
	@Test
	public void testGetScheduleOnSelectHigherValueContent2() {
		
		ArrayList<Content> contents = new ArrayList<Content>();
		contents.add(new Content(0,0,5,50));
		contents.add(new Content(1,0,2,20));
		contents.add(new Content(2,3,5,20));
		contents.add(new Content(3,0,5,30));
		contents.add(new Content(4,0,5,40));
		
		HashSet<ScheduledContent> selectedContents = scheduleProcess.getSchedule(contents,1.0);
		int score = 0;
		Object[] contentArr = selectedContents.toArray();
		for(int i=0;i<contentArr.length;i++){
			ScheduledContent content = (ScheduledContent)contentArr[i];
			score += content.GetScore();
		}
		System.out.println(score);
		
		assertEquals(score,120*6);
	}
	
	@Test
	public void testGetScheduleOnSelectHigherValueContent3() {
		
		ArrayList<Content> contents = new ArrayList<Content>();
		contents.add(new Content(0,0,5,50));
		contents.add(new Content(1,0,2,80));
		contents.add(new Content(2,3,5,80));
		contents.add(new Content(3,0,5,30));
		contents.add(new Content(4,0,5,40));
		
		HashSet<ScheduledContent> selectedContents = scheduleProcess.getSchedule(contents,1.0);
		int score = 0;
		Object[] contentArr = selectedContents.toArray();
		for(int i=0;i<contentArr.length;i++){
			ScheduledContent content = (ScheduledContent)contentArr[i];
			score += content.GetScore();
		}
		System.out.println(score);
		
		assertEquals(score,170*6);
	}
	*/

}
