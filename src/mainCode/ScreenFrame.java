package mainCode;

//import independentFunction.Logo;

import inherentException.NotNaturalNumberException;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ScreenFrame extends JFrame{
	
	//Independent Constant Attribute
	static final String ProgramName = new String("FreeFalling");
	static final String IconPath = new String("ico.jpg");
	
	//Dependent Attribute
	private Dimension frameSize = new Dimension(640, 480);
	private final Point CenterLocation = new Point(
			Toolkit.getDefaultToolkit().getScreenSize().width / 2 - frameSize.width / 2,
			Toolkit.getDefaultToolkit().getScreenSize().height / 2 - frameSize.height / 2);
	
	private Container displayBoard/* = this.getContentPane()*/;
	private ScreenController screenController/* = new ScreenController(displayBoard)*/;
	
	//Getter
	public Dimension getFrameSize(){
		return frameSize;
	}
	
	public Point getCenterLocation(){
		return CenterLocation;
	}
	
	//Setter
	private void setFrameSize(int width, int height){
		Dimension parameterFrameSize = new Dimension(width, height);
		
		checkAndSetSize(parameterFrameSize);
	}
	
	private void setFrameSize(Dimension parameterFrameSize){
		checkAndSetSize(parameterFrameSize);
	}
	
	private void checkAndSetSize(Dimension frameSize){
		try{
			sizeSetting(frameSize);
		}
		catch(inherentException.NotNaturalNumberException e){
			e.printStackTrace();
		}
	}
	
	private void sizeSetting(Dimension newFrameSize) throws inherentException.NotNaturalNumberException{
		checkSize(newFrameSize);
		
		frameSize.setSize(newFrameSize);
		this.setSize(frameSize);
	}
	
	private void checkSize(Dimension newFrameSize) throws NotNaturalNumberException{
		if(newFrameSize.width <= 0)
			throw new inherentException.NotNaturalNumberException(newFrameSize.width, "SizeSetting Method width Parameter");
		else if(newFrameSize.height <= 0)
			throw new inherentException.NotNaturalNumberException(newFrameSize.height, "SizeSetting Method height Parameter");
	}
	
	
	ScreenFrame(){
		frameAttributeInitiallize();
		eventInitiallize();
		
		showLogo();
		showMainMenu();
	}
	
	void frameAttributeInitiallize(){
		initializeWindow();
		initializeContainer();
		initializeScrController();
	}
	
	private void initializeWindow(){
		this.setVisible(true);
		this.setTitle(ProgramName);
		initialFrameSizing();	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(IconPath));
		this.setLocation(CenterLocation);
		this.setResizable(false);
		this.setLayout(null);
	}
	
	private void initialFrameSizing(){
		final int FrameBarSize_TopAndBottom = this.getInsets().top + this.getInsets().bottom;
		final int FrameBarSize_LeftAndRight = this.getInsets().left + this.getInsets().right;
		
		setFrameSize(frameSize.width + FrameBarSize_LeftAndRight - 10, frameSize.height + FrameBarSize_TopAndBottom - 20);
	}
	
	private void initializeContainer(){
		displayBoard = this.getContentPane();
		displayBoard.setLocation(0, 0);
		displayBoard.setSize(frameSize);
		displayBoard.requestFocusInWindow();
	}
	
	private void initializeScrController(){
		screenController = new ScreenController(displayBoard);
	}
	
	private void eventInitiallize(){
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	
	private void showLogo(){
		Logo logo = new Logo(this, displayBoard, screenController);
	}
	
	public void showMainMenu(){
		MainMenu menu= new MainMenu(this, displayBoard, screenController);
		
	}
}