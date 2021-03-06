package application;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;



public class Company {
	/**
	 *A binary tree of all the employees in the company
	 */
	private static TreeMap<Integer,Employee> employees = new TreeMap<Integer,Employee>();
	private Long searchStart;
	private Long searchEnd;
	private Float searchTime;
	
	
	/**
	 * this search  method will accept a string of employee id's and find the times where non of the members of staff are in meetings
	 * @param ids as the array of the employees ids that the algorithm will run for
	 * @param startTime as the the time that the algorithm will start from
	 * @param endTime as the time that the algorithm will stop to search for free times
	 */
	
	/**
	 * Method to preform a search of available time for the user to pick when to add meetings to
	 * @param ids
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public LinkedList<Meeting> search(int[] ids, Date startTime, Date endTime) {
		searchStart = System.nanoTime();
	 Employee[] listOfEmployee= new Employee[ids.length]; 
	LinkedList<Meeting> totalMeetings = new LinkedList<Meeting>();
	for(int i = 0; i < ids.length; i++) {	
		listOfEmployee[i] = new Employee();
		 listOfEmployee[i] = employees.get(ids[i]);
		 totalMeetings.addAll(listOfEmployee[i].getMeetings(startTime,endTime));
		}
	totalMeetings = this.mergeMeetings(totalMeetings);
	
	return this.getTimesBetween(startTime, endTime, totalMeetings, new LinkedList<Meeting>());
	
	}
	
	/**
	 * 
	 * @param employees
	 * @param startTime
	 * @param endTime
	 * @param index
	 * @param totalMeetings
	 * @return
	 */
	public LinkedList<Meeting> compareMeetings(Employee[] employees,Date startTime, Date endTime, int index, LinkedList<Meeting> totalMeetings) {
		if(index < employees.length ) {
			LinkedList<Meeting> employeeMeetings= employees[index].getDiary().getMeetings();
			ListIterator<Meeting> it = employeeMeetings.listIterator();
			
				while(it.hasNext()){
						Meeting meetingToAdd = (Meeting) it.next();
							
						totalMeetings.add(meetingToAdd);
						
			    	}
			return compareMeetings(employees,startTime, endTime, ++index,totalMeetings);
		}
		return totalMeetings; 
	}
	
	
	/**
	 * 
	 * @param totalMeetings
	 * @return
	 */
	private LinkedList<Meeting> mergeMeetings(LinkedList<Meeting> totalMeetings) {
		Collections.sort(totalMeetings, new Comparator<Meeting>() {
		    public int compare(Meeting meeting1,  Meeting meeting2) {
		        return meeting1.getStartTime().compareTo(meeting2.getStartTime());
		    }}
	);
	
	

		for (int i = 0; i< totalMeetings.size(); i++) {
			Meeting meeting1 = totalMeetings.get(i);
			Meeting meeting2 = totalMeetings.get(i+1);
			if(meeting1.getEndTime().compareTo(meeting2.getStartTime())> 0 && meeting1.getEndTime().compareTo(meeting2.getEndTime()) < 0) {
				if(meeting1.getEndTime().compareTo(meeting2.getEndTime()) < 0)
				totalMeetings.get(i).setEndTime(meeting2.getEndTime());
				totalMeetings.remove(i+1);
				i--;
				
			}else {
					totalMeetings.remove(i+1);
				}
			}
		
		
		return totalMeetings;
		
	}
	
	/**
	 * A method to add an employee to the binary tree
	 * @param id as the id of the employee to add
	 * @param name as the name of the employee to add
	 * @param jobTitle as the title of the position in the workplace of the employee
	 */
	public static void addEmployee(int id,String forename,String surname, String jobTitle) {
		employees.put(id, new Employee(id,forename,surname,jobTitle));
	}
	
	/**
	 * method to delete an employee from the binary tree
	 * @param id as the id of the employee you want to remove
	 */
	public boolean deleteEmployee(int id) {
		try {
			employees.remove(id);
			return true;
		}catch(NullPointerException e) {
			System.out.println("This employee doesn't exist");
			return false;
		}
		
	}
	
	/**
	 * method to select a certain employee from the binary tree so that methods can be run on them
	 * eg. edit
	 * @param id as the id of the employee that is to be edited
	 */
	public static Employee selectEmployee(int id) {
		try {
			return employees.get(id);
		}catch(NullPointerException e) {
			System.out.println("This employee doesn't exist");
			return new Employee();
		}
	}
	
	/**
	 * Method to find the times between the unavailable times employees
	 * @param startTime as the start time entered by the user
	 * @param endTimeas the end time entered by the user
	 * @param totalMeetings as the linked list of meetings that the emplpoyees have
	 * @param timesBetween
	 * @return
	 */
	public LinkedList<Meeting> getTimesBetween(Date startTime,Date endTime, LinkedList<Meeting> totalMeetings, LinkedList<Meeting> timesBetween) {
	
		timesBetween.add(new Meeting(startTime,totalMeetings.getFirst().getStartTime(),""));
		for(int i =0; i < totalMeetings.size()-1; i++) {
			timesBetween.add(new Meeting(totalMeetings.get(i).getEndTime(),totalMeetings.get(i+1).getStartTime(),""));	
		}
		timesBetween.add(new Meeting(totalMeetings.getLast().getEndTime(),endTime,""));
		searchEnd = System.nanoTime();
		searchTime = (float)(searchEnd-searchStart)/1000000;
		System.out.println(searchTime/1000 + " s");
		return timesBetween;
	}

	public static TreeMap<Integer, Employee> getEmployees() {
		return employees;
	}

}