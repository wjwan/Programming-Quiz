package amazonOpt;
import java.util.*;
import java.io.*;

public class OptimizationProcess {
	private ArrayList<ScheduleOPT> webPage;
	
	public OptimizationProcess(){
		webPage = new ArrayList<ScheduleOPT>();
		webPage.add(new ScheduleOPT(1.0,0));
		webPage.add(new ScheduleOPT(0.8,1));
		webPage.add(new ScheduleOPT(0.75,2));
		webPage.add(new ScheduleOPT(0.5,3));
		webPage.add(new ScheduleOPT(0.35,4));
		webPage.add(new ScheduleOPT(0.3,5));
	}
	public void setAllSchedules(String filePath) throws Exception{
		BufferedReader buffReader = new BufferedReader(new FileReader(filePath));
		String line;
		int index = -1;
		while((line=buffReader.readLine())!=null){
			if(line.contains("@"))
				index++;
			if(index>=6)
				break;
			String[] strs = line.split(",");
			if(strs.length==4){
				int id = Integer.parseInt(strs[0]);
				int btime = Integer.parseInt(strs[1]);
				int etime = Integer.parseInt(strs[2]);
				int depth = Integer.parseInt(strs[3]);
				ScheduledContent content = new ScheduledContent();
				content.id = id;
				content.begin = btime;
				content.end = etime;
				content.depth = depth;
				webPage.get(index).InsertContent(content);
			}
		}
		buffReader.close();
		for(int i=0; i<6; i++){
			System.out.println("@"+i);
			webPage.get(i).printArea();
		}
	}
	public PotentialInsertion getOptimizeInsertion(Insertion content){
		PriorityQueue<PotentialInsertion> insertions = new PriorityQueue<PotentialInsertion>(6,new InsertionComparator());
		for(int i=0; i<6; i++){
			PotentialInsertion tmp = webPage.get(i).getTheOptimizedInsertion(content);
			if(tmp!=null){
				insertions.add(tmp);
				System.out.println(tmp.getScore()+":("+tmp.space.begin+","+tmp.space.end+","+tmp.space.depth+")");
			}
		}
		return insertions.peek();
	}
	
}

class Insertion{
	int id;
	int adLength;
	int value;
	
	public Insertion(){}
	
	public Insertion(int _id, int _adLength, int _value){
		this.id = _id;
		this.adLength = _adLength;
		this.value = _value;
	}
	
	public Insertion(Insertion insertion){
		this.id = insertion.id;
		this.adLength = insertion.adLength;
		this.value = insertion.value;
	}
}

class VacantSpace{
	int areaID;
	double areaWeight;
	int begin;
	int end;
	int depth;
	int scheduleSize;
	double utilization;
	
	public VacantSpace(){}
	
	public VacantSpace(VacantSpace vspace){
		this.areaID = vspace.areaID;
		this.areaWeight = vspace.areaWeight;
		this.begin = vspace.begin;
		this.end = vspace.end;
		this.depth = vspace.depth;
		this.scheduleSize = vspace.scheduleSize;
		this.utilization = vspace.utilization;
	}
	
	public void setValues(int _areaID, double _areaWeight, int _begin,
			int _end, int _depth, int _scheduleSize, double _utilization){
		this.areaID = _areaID;
		this.areaWeight = _areaWeight;
		this.begin = _begin;
		this.end = _end;
		this.depth = _depth;
		this.scheduleSize = _scheduleSize;
		this.utilization = _utilization;
	}
}

class PotentialInsertion{
	Insertion insertion;
	VacantSpace space;
	
	public PotentialInsertion(Insertion insertion, VacantSpace space){
		this.insertion = new Insertion(insertion);
		this.space = new VacantSpace(space);
	}
	
	public double getScore(){
		double score = space.areaWeight;
		
		double fit = (double)(space.end-space.begin+1)/(double)insertion.adLength;
		if(fit>1)
			fit = 1/fit;
		
		double wait = (double)(space.scheduleSize-space.begin)/(double)space.scheduleSize;
		if(wait<0.33)
			wait = 0.5;
		else if(wait<0.67)
			wait = 0.75;
		else
			wait = 1.0;
		
		double utilization = this.space.utilization;
		
		return (score*fit*wait)/utilization;
	}
}

class InsertionComparator implements Comparator<PotentialInsertion>{

	@Override
	public int compare(PotentialInsertion o1, PotentialInsertion o2) {
		return Double.compare(o2.getScore(), o1.getScore());
	}
	
}

class ScheduleOPT{
	
	private HashMap<Integer,ScheduledContent[]> _timeLine;
	private PriorityQueue<PotentialInsertion> _potentialInsertions;
	private Comparator<PotentialInsertion> _insertionComparator;
	private int _areaID;
	private double _weight;
	private static final int MAXDEPTH = 3;
	private int numOfTakenBlocks;
	private int veryEnd;
	
	public ScheduleOPT(double _w, int _id){
		_timeLine = new HashMap<Integer,ScheduledContent[]>();
		_insertionComparator = new InsertionComparator();
		_potentialInsertions = new PriorityQueue<PotentialInsertion>(10,_insertionComparator);
		this._weight = _w;
		this._areaID = _id;
		this.numOfTakenBlocks = 0;
		this.veryEnd = 0;
	}
	
	public void InsertContent(ScheduledContent content){
		for(int time=content.begin; time<=content.end; time++){
			if(_timeLine.get(time)==null){
				_timeLine.put(time, new ScheduledContent[3]);
			}
			_timeLine.get(time)[content.depth] = content;
		}
		this.numOfTakenBlocks += (content.end-content.begin+1);
		this.veryEnd = Integer.max(this.veryEnd, content.end);
	}
	
	void GetAllPotentialInsertion(Insertion content){
		int size = this.veryEnd;
		double utilization = this.getUtilization();
		_potentialInsertions.clear();
		for(int depth=0; depth<MAXDEPTH; depth++){
			VacantSpace space = new VacantSpace();
			int begin = 0;
			int end = 0;
			while(true){
				// skip the non-vacant positions
				while((begin<=size&&_timeLine.containsKey(begin)&&_timeLine.get(begin)[depth]!=null)
						||CheckRepeatPosition(begin,content.id)){
					begin++;
				}
				if(begin>size){
					space.setValues(_areaID, _weight, size+1, size+1+content.adLength, depth, size, utilization);
					_potentialInsertions.add(new PotentialInsertion(content,space));
					break;
				}
				end = begin+1;
				while(end<=size&&(!_timeLine.containsKey(end)||_timeLine.get(end)[depth]==null)
						&&!CheckRepeatPosition(end,content.id)){
					end++;
				}
				if(end>size){
					space.setValues(_areaID, _weight, begin, begin+content.adLength, depth, size, utilization);
					_potentialInsertions.add(new PotentialInsertion(content,space));
					break;
				}
				else{
					space.setValues(_areaID, _weight, begin, end-1, depth, size, utilization);
					_potentialInsertions.add(new PotentialInsertion(content,space));
				}
				begin=end;
			}
		}
		System.out.println("here");
	}
	
	public void printArea(){
		//System.out.println("@");
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
	
	public PotentialInsertion getTheOptimizedInsertion(Insertion content){
		this.GetAllPotentialInsertion(content);
		return this._potentialInsertions.peek();
	}
	
	boolean CheckRepeatPosition(int time,int id){
		if(_timeLine.containsKey(time)){
			for(int depth=0; depth<MAXDEPTH; depth++)
				if(_timeLine.get(time)[depth]!=null&&_timeLine.get(time)[depth].id==id)
					return true;
		}
		return false;
	}
	
	double getUtilization(){
		double utilization = 0.0;
		double size = this.veryEnd*3;
		double taken = this.numOfTakenBlocks;
		utilization = taken/size;
		if(utilization<0.1)
			utilization = 0.1;
		utilization *=2;
		return utilization;
	}
}