package mainCode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Logo extends AutoSelfRemovingJPanel implements ActionListener, KeyListener, MouseListener{
	private final int FadePeriodTime = 25;						//millisecond
	private Timer fadeBeacon = new Timer(FadePeriodTime, this);
	//Don't use multiple Timer at the same time
	
	public Logo(ScreenFrame screen, Container displayBoard, ScreenController scrControllerLoca){
		super(displayBoard, scrControllerLoca);
		initialize(screen);
		this.requestFocusInWindow();
		
		fadingStart();
	}
	
	private void initialize(ScreenFrame screen){
		panelBasisInitialize(screen);
		eventListenerInitialize();
		super.initializeComponentAttributeBefore();
	}
		
	private void panelBasisInitialize(ScreenFrame screen){
		imageInitialize();
		panelInitialize(screen);
	}
	
	private void panelInitialize(ScreenFrame screen){
		this.setLocation(0, 0);
		this.setSize(screen.getFrameSize());
		this.setOpaque(false);
	}
	
	private final ImageIcon LogoImage = new ImageIcon("Logo.jpg");
	
	private void imageInitialize(){
		JLabel logoImage = new JLabel(LogoImage);
		logoImage.setSize(LogoImage.getIconWidth(), LogoImage.getIconHeight());
		logoImage.setLocation(0, 0);
		
		this.add(logoImage);
	}
	
	private void eventListenerInitialize(){
		this.addKeyListener(this);
		this.addMouseListener(this);
	}
	
	private int startInitialDelay = 1000;			//millisecond
	
	private void fadingStart(){
		fadeBeacon.setInitialDelay(startInitialDelay);
		fadeBeacon.start();
	}
	

	@Override
	public void actionPerformed(ActionEvent e){
		opacityControl();
		
		fadingCompleteAction();
		(super.getBoardLoca()).repaint();
	}
	
	private boolean fadein = true;
	
	private void opacityControl(){
		if(fadein)
			moreDarkly();
		else
			moreBrightly();
	}
	
	private final float FadeInPeriodSpeed = 0.02f;
	private final float FadeOutPeriodSpeed = -0.04f;
	
	private final float Dark = 0;
	private final float Bright = 1;
	private float opacity = 0;
	
	private void moreDarkly(){
		opacity += FadeInPeriodSpeed;
	}
	
	private void moreBrightly(){
		opacity += FadeOutPeriodSpeed;
	}
	
	private void fadingCompleteAction(){
		if(fadein && opacity > Bright)
			fadingChange();
		
		if(!fadein && opacity < Dark)
			fadingComplete();
	}
	
	private int termInitialDelay = 3000;			//millisecond
	
	private void fadingChange(){
		opacity = Bright;
		fadeBeacon.stop();
		fadein = false;
		fadeBeacon.setInitialDelay(termInitialDelay);
		
		fadeBeacon.restart();
	}
	
	private void fadingComplete(){
		classAttributeCompletedSetting();
		externalCompleteSetting();
		
		frameVisualControl();
	}
	
	private void classAttributeCompletedSetting(){
		setOpacityDark();
		fadeBeaconClear();
	}
	
	private void setOpacityDark(){
		opacity = Dark;
		super.getBoardLoca().repaint();
	}
	
	private void fadeBeaconClear(){
		fadeBeacon.stop();
		fadeBeacon = null;
	}
	
	private void frameVisualControl(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setVisible(false);
	}
	
	private void externalCompleteSetting(){
		super.setAllTaskFinishedSetting();
	}
	
	
	@Override
    public void paintComponent(Graphics g) {
		paintOpacity(g);
    }
	
	private void paintOpacity(Graphics g){
		((Graphics2D) g).setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC, opacity));
		g.fillRect(0,0,getWidth(),getHeight());
	}
	
	
	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		
		case KeyEvent.VK_ESCAPE:
			logoSkip();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getButton()){
		
		case MouseEvent.BUTTON1:
			logoSkip();
		}
	}
	
	private final float LittleDark = Math.abs(FadeOutPeriodSpeed) * 15;
	
	private void logoSkip(){
		boolean stateMoreLittleDark = this.opacity < LittleDark;
		final boolean DonotSkipCondition = 
				stateMoreLittleDark && !this.fadein;
		
		if(DonotSkipCondition)
			return;
		
		fadeBeaconResetting();
		attributeResetting();
	}
	
	private void fadeBeaconResetting(){
		fadeBeacon.setInitialDelay(0);
		fadeBeacon.restart();
	}
	
	private void attributeResetting(){
		this.fadein = false;
		this.opacity = LittleDark;
	}
	
	
	@Override	public void mouseEntered(MouseEvent arg0) {}
	@Override	public void mouseExited(MouseEvent arg0) {}
	@Override	public void mousePressed(MouseEvent arg0) {}
	@Override	public void mouseReleased(MouseEvent arg0) {}
	@Override	public void keyReleased(KeyEvent arg0) {}
	@Override	public void keyTyped(KeyEvent arg0) {}
}