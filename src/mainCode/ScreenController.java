package mainCode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.LinkedList;

public class ScreenController{
	public static final String Finished = "Finished";
	
	private boolean displaying = false;
	
	private LinkedList<AutoSelfRemovingJPanel> nextShowingList = new LinkedList<AutoSelfRemovingJPanel>();
	
	private Container screenContainer;
	
	public ScreenController(Container boardLoca){
		this.screenContainer = boardLoca;
	}
	
	public void addTask(AutoSelfRemovingJPanel newTask){
		nextShowingList.addLast(newTask);
		if(!displaying)
			nextTaskShow();
		
		displaying = true;
	}
	
	public void nextTaskShow(){		
		if(nextShowingList.isEmpty()){
			displaying = false;
			return;
		}
		
		AutoSelfRemovingJPanel outputTask = nextShowingList.getFirst();
		screenContainer.add(outputTask);
		
		nextShowingList.removeFirst();
	}
}
