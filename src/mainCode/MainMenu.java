package mainCode;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MainMenu extends AutoSelfRemovingJPanel{	
	
	private final ScreenFrame screenLoca;
	
	public MainMenu(ScreenFrame screen, Container displayBoard, ScreenController scrControllerLoca) {
		super(displayBoard, scrControllerLoca);
		this.screenLoca = screen;
		initialize(screen);
		
		super.initializeComponentAttributeBefore();
	}
	
	private void initialize(ScreenFrame screen){
		wallpaperInitialize();
		titleInitialize();
		buttonInitialize();
		panelInitialize(screen);
	}
	
	private ImageIcon mainImage = new ImageIcon("Menu.jpg");
	
	private void wallpaperInitialize(){
		JLabel wallpaper = new JLabel(mainImage);
		wallpaper.setSize(mainImage.getIconWidth(), mainImage.getIconHeight());
		wallpaper.setLocation(0, 0);
		
		this.add(wallpaper);
		this.setLayer(wallpaper, JLayeredPane.DEFAULT_LAYER);
	}
	
	private ImageIcon titleImage = new ImageIcon("Title.png");
	private Point locationTitle = new Point(25, 105);
	
	private void titleInitialize(){
		JLabel title = new JLabel(titleImage);
		title.setSize(titleImage.getIconWidth(), titleImage.getIconHeight());
		title.setLocation(locationTitle);
		
		this.add(title);
		this.setLayer(title, JLayeredPane.POPUP_LAYER);
	}
	
	private void buttonInitialize(){
		buttonInitialize_Start();
		buttonInitialize_exit();
	}

	private final MainMenu MainMenuPanelLoca = this;
	private ImageIcon buttonImage_Start = new ImageIcon("Button_START.jpg");
	private Point locationButtonStart = new Point(450, 260);
	private Dimension sizeButtonStart = new Dimension(100, 40);
	
	private void buttonInitialize_Start(){
		ImageButton buttonStart = new ImageButton(buttonImage_Start);
		buttonStart.setSize(sizeButtonStart);
		buttonStart.setLocation(locationButtonStart);
		
		buttonStart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainMenuPanelLoca.setAllTaskFinishedSetting();
				Game g = new Game(screenLoca,
						MainMenuPanelLoca.getBoardLoca(),
						MainMenuPanelLoca.getScreenController());
			}
		});
		
		this.add(buttonStart);
		this.setLayer(buttonStart, JLayeredPane.MODAL_LAYER);
	}	
	
	private ImageIcon buttonImage_Exit = new ImageIcon("Button_Exit.jpg");
	private Point locationButtonExit = new Point(450, 320);
	private Dimension sizeButtonExit = new Dimension(100, 40);
	
	private void buttonInitialize_exit(){
		ImageButton buttonExit = new ImageButton(buttonImage_Exit);
		buttonExit.setSize(sizeButtonExit);
		buttonExit.setLocation(locationButtonExit);
		
		buttonExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		this.add(buttonExit);
		this.setLayer(buttonExit, JLayeredPane.MODAL_LAYER);
	}
	
	private class ImageButton extends JButton implements MouseListener, ActionListener{		
		ImageButton(ImageIcon contextImage){
			super(contextImage);
			initialize();
			
			repaint();
		}
		
		private void initialize(){
			this.setBorderPainted(false);
			this.setContentAreaFilled(false);
			this.addMouseListener(this);
		}
		
		
		private final float OpacityNotFocused = 0.2f;
		private float opacityNow = OpacityNotFocused;
		private int movingPointXYForClick = 2;
		
		@Override	
		public void mouseEntered(MouseEvent arg0) {
			opacityNow = 0f;
			repaint();
		}
		
		@Override	
		public void mouseExited(MouseEvent arg0) {
			opacityNow = OpacityNotFocused;
			repaint();
		}
		
		@Override	
		public void mousePressed(MouseEvent arg0) {
			relocateForClick();
		}
		
		@Override	
		public void mouseReleased(MouseEvent arg0) {
			relocateForNotClicked();
		}
		
		private void relocateForClick(){
			this.setLocation(this.getLocation().x + movingPointXYForClick, 
					this.getLocation().y + movingPointXYForClick);
		}
		
		private void relocateForNotClicked(){
			this.setLocation(this.getLocation().x - movingPointXYForClick, 
					this.getLocation().y - movingPointXYForClick);
		}
		
		private final String Clicked = "Clicked";
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			ActionEvent clickedAE = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, Clicked);
			actionPerformed(clickedAE);
		}
		
		@Override	public void actionPerformed(ActionEvent arg0) {}
		
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			
			g.setColor(new Color(0f, 0f, 0f, opacityNow));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private void panelInitialize(ScreenFrame screen){
		this.setSize(screen.getFrameSize());
		this.setLocation(0, 0);
		this.requestFocusInWindow();
	}
}
