package inherentException;

public class InvalidRangeValueException extends Exception{
	private String targetValue;
	private String description;
	private String validCondition;
	
	public InvalidRangeValueException(String targetValue, String description, String validCondition){
		super();
		this.targetValue = targetValue;
		this.description = description;
		this.validCondition = validCondition;
	}
	
	@Override
	public String getMessage(){
		return description + "�� " + targetValue + "�� Ÿ������ ���� ��. �մ��� ���� : " + validCondition;
	}
}
