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
		return description + "의 " + targetValue + "는 타당하지 않은 값. 합당한 조건 : " + validCondition;
	}
}
