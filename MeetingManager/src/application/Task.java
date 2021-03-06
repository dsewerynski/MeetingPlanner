
public class Task {
	
	private String description;
	private String priority;
	private Task next;
	
	public Task(String description, String priority)
	{
		this.description = description;
		this.priority = priority;
	}
	

	/**
	 * Method to get the description of a task
	 * 
	 * @return description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Method to set the description of a task
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Method to get the priority of a task
	 * 
	 * @return priority
	 */
	public String getPriority()
	{
		return priority;
	}
	
	/**
	 * Method to set the priority of a task
	 * 
	 * @param priority
	 */
	public void setPriority(String priority)
	{
		this.priority = priority;
	}
	
	/**
     * Method to get the next task in the list after this one
     * 
     * @return next
     */
    public Task getNext()
    {
    	return next;
    }

    /**
     * Method to set the next task in the list after this one
     * 
     * @param next 
     */
    public void setNext(Task task)
    {
    	next = task;
    }

}
