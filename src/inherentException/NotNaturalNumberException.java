package inherentException;

public class NotNaturalNumberException extends Exception{
	private int targetNumber;
	private String locaDescription;
	
	public NotNaturalNumberException(int targetNumber, String description){
		super();
		this.targetNumber = targetNumber;
		this.locaDescription = description;
	}
	
	@Override
	public String getMessage(){
		return locaDescription + "���� �ڿ������� �ƴ� " + targetNumber + "�Էµ�";
	}
}
