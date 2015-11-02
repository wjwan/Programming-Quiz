package amazonOpt;
import java.util.*;
import java.io.*;

public class ScheduleProcess {

	public HashSet<ScheduledContent> getSchedule(ArrayList<Content> contents, double weight){	
		Schedule schedule = new Schedule(weight);
		contents.sort(new ContentComparator());
		HashSet<ScheduledContent> maxScoreSchedule = new HashSet<ScheduledContent>();
		int maxScore = 0;
		int times = 0;
		do{
			for(Content content:contents){
				schedule.insertACandidate(content);
			}
			times++;
			int tmpScore = schedule.getScore();
			if(tmpScore>maxScore){
				maxScore = tmpScore;
				maxScoreSchedule = schedule.getScheduledContents();
			}
			else
				break;
			//System.out.println("@");
			//schedule.printArea();
			//System.out.println(tmpScore);
		}while(times<10);
		System.out.println("@");
		schedule.printArea();
		return maxScoreSchedule;
	}
}

/**
 * This class describes a advertisement content.
 * 		- id is the identification of the advertisement.
 * 		- begin, end indicates the position where this content will be inserted 
 * 		  on the schedule.
 * 
 * It contains a constructor with three parameters and a copy constructor.
 * 
 */

class Content{
	int id;
	int begin;
	int end;
	int value;
	
	public Content(){}
	
	public Content(int _id, int _begin, int _end, int _value){
		this.id = _id;
		this.begin = _begin;
		this.end = _end;
		this.value = _value;
	}
	
	public Content(Content content){
		this.id = content.id;
		this.begin = content.begin;
		this.end = content.end;
		this.value = content.value;
	}
	
	public int GetScore(){
		return value*(end-begin+1);
	}
}


/**
 * This class describes an advertisement content has been inserted onto 
 * the schedule.
 * 
 * Besides derived values, depth indicates the position on the schedule
 * it is inserted, and weight indicates the weight of the area on the
 * web site. 
 * 
 */
class ScheduledContent extends Content{
	
	int depth;
	double weight;
	
	public ScheduledContent(){}
	
	public ScheduledContent(int _id, int _begin, int _end, int _value){
		super(_id,_begin,_end,_value);
		this.depth = -1;
		this.weight = 0.0;
	}
	
	public ScheduledContent(Content content, int _depth, double _weight){
		super(content);
		this.depth = _depth;
		this.weight = _weight;
	}
	
	public ScheduledContent(ScheduledContent content){
		super(content);
		this.depth = content.depth;
		this.weight = content.weight;
	}
}

/**
 * This class describes the schedule of an area on the web page.
 * 
 * Variables in this class:
 * 		_timeLine is a hash map to store the scheduled contents on each time block.
 * 		
 * 		_scheduledContents is a hash set to store those contents have been selected.
 * 
 */
class Schedule{	
	private HashMap<Integer, ScheduledContent[]> _timeLine;
	private HashSet<ScheduledContent> _scheduledContents;
	private final static int MAXDEPTH = 3;
	private double WEIGHT;
	
	public Schedule(double weight){
		_timeLine = new HashMap<Integer, ScheduledContent[]>();
		_scheduledContents = new HashSet<ScheduledContent>();
		WEIGHT = weight;
	}
	
	/**
	 * This function inserts a content into the schedule.
	 * 		1. First it will check whether there is a content of same advertisement 
	 * 		   on the schedule. If there is a repeat advertisement within its time period, 
	 * 		   the insertion will skip.
	 * 		2. Find a vacant place on the schedule. Because there are three available 
	 * 		   positions on each time block, we always check from the #0 position to the
	 * 		   #2 position. If there is a vacant position, insert the content there.
	 * 		3. If there is no vacant position, which means there are 3 scheduled contents
	 * 		   on the time line. Those three contents should compete with the current
	 * 		   candidate and the one with lowest score will be removed from the schedule.
	 * 
	 * @param content An instance of Content, the candidate to be inserted into the schedule.
	 * @see Content
	 */
	
	public void insertACandidate(Content content){
		// skip the contents has been scheduled
		if(!_scheduledContents.contains(content)){
			if(!findRepeat(content)){
				int vacant = findVacantDepth(content);
				if(vacant>=MAXDEPTH){
					/*
					 * If there is not a vacant position on the time line. The candidate will 
					 * compete with the other three contents already scheduled on the time line.
					 * The highest three contents will be kept on the schedule.
					 * 
					 */
					keepHigherScoredContent(content);
				}
				else{
					/*
					 * If there is a vacant position or the conflict has been solved. Insert 
					 * the content onto the time line.
					 * 
					 */
					ScheduledContent scheduledContent = new ScheduledContent(content,vacant,WEIGHT);
					for(int time=scheduledContent.begin; time<=scheduledContent.end; time++){
						if(_timeLine.get(time)==null){
							_timeLine.put(time, new ScheduledContent[MAXDEPTH]);
						}
						_timeLine.get(time)[scheduledContent.depth] = scheduledContent;
						_scheduledContents.add(scheduledContent);
					}
				}
			}
		}
	}
	
	/**
	 * This function calculates the score of contents with the positions where they are inserted.
	 * The main criteria of this score function is to keep contents with higher value. The
	 * value of a content is defined by its value times its length.
	 * 
	 * @param scheduledContents It is a set of contents
	 * @return Integer
	 * @see ScheduledContent
	 * @see Content
	 */
	public int getScore(HashSet<ScheduledContent> scheduledContents){
		int score = 0;
		Object[] contents = scheduledContents.toArray();
		for(int i=0;i<contents.length;i++){
			ScheduledContent content = (ScheduledContent)contents[i];
			score += content.value;
		}
		return score;
	}
	
	public int getScore(){
		int score = 0;
		Object[] contents = _scheduledContents.toArray();
		for(int i=0;i<contents.length;i++){
			ScheduledContent content = (ScheduledContent)contents[i];
			score += content.GetScore();
		}
		return score;
	}
	
	public HashSet<ScheduledContent> getScheduledContents(){
		return new HashSet<ScheduledContent>(_scheduledContents);
	}
	
	/**
	 * This function is to find whether there is a repeat content on the time line. A
	 * content is considered a repeat to another content if they have the same advertisement.
	 * 
	 * @param content This is the candidate content will be check the repetition in the set
	 * of scheduled contents.
	 * @return boolean If there is a repeat content, return true. Otherwise return false.
	 */
	boolean findRepeat(Content content){
		for(int time=content.begin; time<=content.end; time++){
			if(_timeLine.containsKey(time)){
				for(int depth=0; depth<MAXDEPTH; depth++){
					if(_timeLine.get(time)[depth]!=null&&
							sameAdvertisement(_timeLine.get(time)[depth],content)){
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	boolean sameAdvertisement(Content c1, Content c2){
		return c1.id==c2.id;
	}
	
	int findVacantDepth(Content content){
		int vacant = 0;
		for(int time=content.begin; time<=content.end; time++){
			while(vacant<MAXDEPTH&&_timeLine.containsKey(time)&&_timeLine.get(time)[vacant]!=null){
				vacant++;
			}
			if(vacant>=MAXDEPTH)
				break;
		}
		return vacant;
	}
	
	void keepHigherScoredContent(Content content){
		HashMap<Integer,HashSet<Content>> toRemove = new HashMap<Integer,HashSet<Content>>();
		toRemove.put(0, new HashSet<Content>());
		toRemove.put(1, new HashSet<Content>());
		toRemove.put(2, new HashSet<Content>());
		for(int time=content.begin; time<=content.end; time++){
			for(int depth=0; depth<MAXDEPTH; depth++){
				if(_timeLine.containsKey(time)&&_timeLine.get(time)[depth]!=null)
					toRemove.get(depth).add(_timeLine.get(time)[depth]);
			}
		}
		ArrayList<Integer> scores = new ArrayList<Integer>();
		scores.add(content.GetScore());
		Object[] keys = toRemove.keySet().toArray();
		for(int i=0; i<keys.length; i++){
			HashSet<Content> removeContents = toRemove.get(keys[i]);
			Object[] removeContent = removeContents.toArray();
			scores.add(0);
			for(int j=0; j<removeContent.length; j++){
				scores.set(i+1, scores.get(i+1)+((Content)removeContent[j]).GetScore());
			}
			//scores.set(i+1, scores.get(i+1)/removeContent.length);
		}
		
		int minIndex = scores.indexOf(Collections.min(scores));
		if(minIndex!=0){
			removeContent(toRemove.get(minIndex-1));
			insertACandidate(content);
		}
	}
	
	void removeContent(HashSet<Content> removeContents){
		Object[] contents = removeContents.toArray();
		for(int i=0; i<contents.length; i++){
			ScheduledContent content = (ScheduledContent)contents[i];
			for(int time=content.begin; time<=content.end; time++){
				_timeLine.get(time)[content.depth] = null;
			}
			_scheduledContents.remove(content);
		}
	}
	
	public void printArea(){
		
		int size = Collections.max(_timeLine.keySet());
		ArrayList<ArrayList<Integer>> lines = new ArrayList<ArrayList<Integer>>();
		lines.add(new ArrayList<Integer>());
		lines.add(new ArrayList<Integer>());
		lines.add(new ArrayList<Integer>());
		for(int i=0; i<=size; i++){
			if(_timeLine.containsKey(i)){
				if(_timeLine.get(i)[0]!=null)
					lines.get(0).add(_timeLine.get(i)[0].id);
				else
					lines.get(0).add(-1);
				if(_timeLine.get(i)[1]!=null)
					lines.get(1).add(_timeLine.get(i)[1].id);
				else
					lines.get(1).add(-1);
				if(_timeLine.get(i)[2]!=null)
					lines.get(2).add(_timeLine.get(i)[2].id);
				else
					lines.get(2).add(-1);
			}
			else{
				lines.get(0).add(-1);
				lines.get(1).add(-1);
				lines.get(2).add(-1);
			}
		}
		for(int i=0; i<3; i++){
			for(int j=0; j<lines.get(i).size(); j++){
				int index = lines.get(i).get(j);
				System.out.format("%3d", index);
			}
			System.out.println();
		}
	}
}

/**
 * This class is a comparator designed for comparing contents.
 * The criteria is:
 * 		1. sort the decreasingly contents by their end time.
 * 		2. for those with same end time, sort the content with higher
 * 		   value in front.
 * The criteria is used to achieve:
 * 		1. schedule as many contents as possible
 * 		2. schedule those contents more valuable first
 * 
 */
class ContentComparator implements Comparator<Content>{
	

	@Override
	public int compare(Content c1, Content c2) {
		int result = Integer.compare(c1.end, c2.end);
		if(result==0)
			return Integer.compare(c2.value, c1.value);
		else
			return result;
	}
	
}


