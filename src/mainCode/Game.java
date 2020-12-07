package mainCode;

import java.awt.*;

import javax.swing.*;

import java.util.Hashtable;
import java.util.HashMap;

import inherentException.InvalidRangeValueException;


public class Game extends AutoSelfRemovingJPanel implements Runnable{
	
	private HashMap<String, Component> mapOfComponents = new HashMap<String, Component>();
	
	public Component add(String key, Component dataLabel){
		mapOfComponents.put(key, dataLabel);
		return super.add(dataLabel);
	}
	
	
	private final Dimension GameScreenSize = new Dimension(500, 600);
	
	public Game(ScreenFrame screen, Container displayBoard, ScreenController scrControllerLoca){
		super(displayBoard, scrControllerLoca);
		initialize(screen);	
	}
	
	private void initialize(ScreenFrame screen){
		this.setSize(GameScreenSize);
		screen.setSize(GameScreenSize);
		
		initializeBackground();
		initializeSubPanel();
		
		initializeThreads();
		
		super.initializeComponentAttributeBefore();
	}
	
	private void initializeBackground(){
		initializeShortDistanceBackground();
		initializeFarDistanceBackground();
	}
	
	private final String bgShortDistanceKey = "Universe";
	
	private void initializeShortDistanceBackground(){
		final ImageIcon imgUniverse = new ImageIcon("gamebackground.jpg");
		createBackground(bgShortDistanceKey, imgUniverse, JLayeredPane.DEFAULT_LAYER);
	}
	
	private final String bgFarDistanceKey = "BigStars";
	
	private void initializeFarDistanceBackground(){
		final ImageIcon imgBigStars = new ImageIcon("gamebackground2.png");
		createBackground(bgFarDistanceKey, imgBigStars, JLayeredPane.PALETTE_LAYER);
	}
	
	private void createBackground(String backgroundKey, ImageIcon backgroundImage, int layerLevel){
		JLabel background = new JLabel(backgroundImage);
		background.setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		background.setLocation(0, 0);
		this.add(backgroundKey, background);
		this.setLayer(background, layerLevel);
	}
	
	private void initializeSubPanel(){
		initializeDistanceToGround();
	}
	
	private final String panelDistanceKey = "Distance To Ground";
	
	private void initializeDistanceToGround(){
		final int maxPositionForDistance = 5;
		ImageNumber panelDistance = createDistanceToGround(maxPositionForDistance);
		
		setDistanceToGroundMeasurement(panelDistance);
		setDistanceToGroundInLayer(panelDistance, JLayeredPane.MODAL_LAYER);
	}
	
	private ImageNumber createDistanceToGround(int maxPositionForDistance){
		ImageNumber newDistancePanel = new ImageNumber(maxPositionForDistance);
		newDistancePanel.setLocation(this.getSize().width - newDistancePanel.getSize().width, 0);
		
		return newDistancePanel;
	}
	
	private void setDistanceToGroundMeasurement(ImageNumber panelDistance){
		ImageIcon[] measurements = new ImageIcon[2];
		measurements[0] = new ImageIcon("characterk.png");
		measurements[1] = new ImageIcon("characterm.png");
		panelDistance.setMeasurement(measurements);
	}
	
	private void setDistanceToGroundInLayer(ImageNumber panelDistance, int layerLevel){
		this.add(panelDistanceKey, panelDistance);
		this.setLayer(panelDistance, layerLevel);
	}
	
	private void initializeThreads(){
		Thread controllerForMoveBackground = new Thread(this);
		controllerForMoveBackground.start();
	}
	
	private final int distanceStartingToGround = 36000;
	private ImageNumber panelDistance;
	private JLabel backgroundShortDistance;
	private JLabel backgroundFarDistance;
	
	@Override
	public void run() {
		final long NextFrameTerm_Short = 30;				//millisecond
		long previousFrameTime_Short = System.currentTimeMillis();
		
		final long NextFrameTerm_Far = 50;
		long previousFrameTime_Far = previousFrameTime_Short;
		
		Integer distanceToGround = new Integer(distanceStartingToGround);
		
		setBackgroundComponent();
		
		int coordinateShortDistanceBackground = 0;
		int coordinateFarDistanceBackground = 400;
		
		while(!super.getAllTaskFinished()){
			boolean isTimeToChangingFrameForShortDistanceBackground = 
					System.currentTimeMillis() > previousFrameTime_Short + NextFrameTerm_Short;
					
			if(isTimeToChangingFrameForShortDistanceBackground){
				backgroundShortDistance.setLocation(0, coordinateShortDistanceBackground--);
				resetDistancePanel(distanceToGround, coordinateShortDistanceBackground);
				
				previousFrameTime_Short = System.currentTimeMillis();
			}
			
			boolean isTimeToChangingFrameForFarDistanceBackground =
					System.currentTimeMillis() > previousFrameTime_Far + NextFrameTerm_Far;
					
			if(isTimeToChangingFrameForFarDistanceBackground){
				backgroundFarDistance.setLocation(0, coordinateFarDistanceBackground--);
				
				previousFrameTime_Far = System.currentTimeMillis();
			}
		}
	}
	
	private void setBackgroundComponent(){
		backgroundShortDistance = (JLabel) mapOfComponents.get(bgShortDistanceKey);
		backgroundFarDistance = (JLabel) mapOfComponents.get(bgFarDistanceKey);
		panelDistance = (ImageNumber) mapOfComponents.get(panelDistanceKey);
	}
	
	private void resetDistancePanel(Integer distanceToGround, int coordinateShortDistanceBackground){
		distanceToGround = calculateDistanceToGround(coordinateShortDistanceBackground);
		
		try {
			panelDistance.setNumber(distanceToGround);
		} catch (InvalidRangeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Integer calculateDistanceToGround(int coordinateShortDistanceBackground){
		return new Integer(
				(backgroundShortDistance.getIcon().getIconHeight()
				+ coordinateShortDistanceBackground) * 9);
	}
	
	private class ImageNumber extends JPanel{
		private final ImageIcon NumberImage[] = new ImageIcon[10];
		private JLabel eachPositionSpace[];
		private int eachPositionNumber[];
		
		private JLabel measurementSpace[] = null;
		
		ImageNumber(int maxPositionNumber){
			super();
			this.setOpaque(false);
			this.setLayout(null);
			
			eachPositionSpace = new JLabel[maxPositionNumber];
			eachPositionNumber = new int[maxPositionNumber];
			
			for(int i = 0; i < NumberImage.length; i++)
				NumberImage[i] = new ImageIcon("number" + i + ".png");
			
			this.setSize(NumberImage[0].getIconWidth() * maxPositionNumber
					, NumberImage[0].getIconHeight());
			
			for(int i = 0; i < eachPositionSpace.length; i++){
				eachPositionSpace[i] = new JLabel(NumberImage[0]);
				eachPositionSpace[i].setSize(NumberImage[0].getIconWidth(), NumberImage[0].getIconHeight());
				eachPositionSpace[i].setLocation(NumberImage[0].getIconWidth() * i , 0);
				this.add(eachPositionSpace[i]);
			}
		}
		
		public void setNumber(int displayNumber) throws InvalidRangeValueException{
			if(displayNumber >= (int)Math.pow(10, eachPositionNumber.length))
				throw new InvalidRangeValueException("ImageNumber", Integer.toString(displayNumber), "< " + Integer.toString((int)Math.pow(10, eachPositionNumber.length)));
			else if(displayNumber < 0)
				throw new InvalidRangeValueException("ImageNumber", Integer.toString(displayNumber), "> " + 0);
			
			
			for(int nowPosition = 0; nowPosition < eachPositionNumber.length; nowPosition++){
				int nowPositionExchangedTen = (int)Math.pow(10,  eachPositionNumber.length - 1 - nowPosition);
				int nowNumber = displayNumber / nowPositionExchangedTen;
				
				eachPositionNumber[nowPosition] = nowNumber;
				displayNumber -= nowNumber * nowPositionExchangedTen;
			}
			setNumberImage();
		}
		
		private void setNumberImage(){
			boolean maxPositionIsZero = true;
			for(int nowPosition = 0; nowPosition < eachPositionNumber.length; nowPosition++){
				if(eachPositionNumber[nowPosition] == 0 && maxPositionIsZero){
					eachPositionSpace[nowPosition].setIcon(null);
					continue;
				}
				eachPositionSpace[nowPosition].setIcon(NumberImage[eachPositionNumber[nowPosition]]);
				eachPositionSpace[nowPosition].setLocation(NumberImage[0].getIconWidth() * nowPosition , 0);
				maxPositionIsZero = false;
			}
		}
		
		public void setMeasurement(ImageIcon[] imageMeasurement){
			measurementSpace = new JLabel[imageMeasurement.length];
			
			int widthNumbers = 0;
			for(int nowPosition = 0; nowPosition < eachPositionSpace.length; nowPosition++){
				widthNumbers += eachPositionSpace[nowPosition].getIcon().getIconWidth();
			}
			
			int widthMeasurement = 0;
			for(int nowCharLoca = 0; nowCharLoca < imageMeasurement.length; nowCharLoca++){
				measurementSpace[nowCharLoca] = new JLabel(imageMeasurement[nowCharLoca]);
				measurementSpace[nowCharLoca].setSize(imageMeasurement[nowCharLoca].getIconWidth(), imageMeasurement[nowCharLoca].getIconHeight());
				measurementSpace[nowCharLoca].setLocation(widthNumbers + widthMeasurement, 0);
				widthMeasurement += imageMeasurement[nowCharLoca].getIconWidth();
				this.add(measurementSpace[nowCharLoca]);
			}
			
			this.setSize(widthNumbers + widthMeasurement, NumberImage[0].getIconHeight());
			this.setLocation(this.getLocation().x - widthMeasurement - 10, this.getLocation().y);
			System.out.println(this.getLocation().x);
		}
	}
}
