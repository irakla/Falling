package mainCode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AutoSelfRemovingJPanel extends JLayeredPane implements FinishedNoticer{
	private boolean allTaskFinished = false;
	private final Container boardLoca;
	
	private ScreenController screenController;
	
	protected Container getBoardLoca(){
		return boardLoca;
	}
	
	protected ScreenController getScreenController(){
		return screenController;
	}
	
	protected boolean getAllTaskFinished(){
		return allTaskFinished;
	}
	
	public AutoSelfRemovingJPanel(Container displayBoard, ScreenController scrControllerLoca){
		super();
		this.boardLoca = displayBoard;
		
		this.screenController = scrControllerLoca;
	}
	
	protected void initializeComponentAttributeBefore(){
		reserveToScreen();
	}
	//must do this method before component initializing
	
	private void reserveToScreen(){
		screenController.addTask(this);
	}
	
	protected void setAllTaskFinishedSetting(){
		allTaskFinished = true;
		doFinishingTask();
	}
	
	@Override
	public void doFinishingTask() {
		if(allTaskFinished){
			showNextFromScreenTaskList();
			removeSelfFromBoard();
		}
	}
	
	private void showNextFromScreenTaskList(){
		screenController.nextTaskShow();
	}
	
	private void removeSelfFromBoard(){
		boardLoca.remove(this);
	}
	
	
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		System.out.println("deleted : " + this);
	}
}