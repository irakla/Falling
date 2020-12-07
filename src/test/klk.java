package test;

public class klk {
	static int lefted = 0;
	public static void main(String[] args) {
		long storedtime = System.currentTimeMillis();
		while(true){
			if(System.currentTimeMillis() < storedtime + 3)
				continue;
			
			storedtime = System.currentTimeMillis();
			klk i = new klk();
			i = null;
			lefted++;
			if(lefted % 100 == 0)
				System.out.println(lefted);
			System.gc();
		}
	}
	
	protected void finalize() throws Throwable{
		super.finalize();
		System.out.println("deleted : " + this);
		lefted = 0;
	}
}
