package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GuiManager extends JFrame {

	private static final long serialVersionUID = -1446237294057835141L;

	private GameOfLife game;
	
	private JPanel optionsPanel;
	private GamePanel gamePanel;
	
	private JButton startButton;
	private JButton stopButton;
	private JButton blackCellsButton;
	private JButton colorCellsButton;
	private JButton structuresButton;
	private JButton stepButton;
	private JButton clearAllButton;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem[] fileMenuItems;
	private JSlider gameSpeedSlider;
	
	Timer timer = new Timer(true); // <-- Super important true
	private boolean running = false;
	private int gameSpeed = 2;
	static private int[] gameSpeedIntervals = {30, 10, 3, 1};
	
	// === Main Constructor === //
	
	GuiManager(GameOfLife g) {
		super("Game of Life");
		
		game = g;
		
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		GridLayout grid = new GridLayout(20, 1);
		grid.setHgap(5);
		grid.setVgap(5);
		optionsPanel = new JPanel(grid);
		gamePanel = new GamePanel();
		
		gamePanel.setBackground(Color.white);
		
		this.add(optionsPanel, BorderLayout.EAST);
		this.add(gamePanel, BorderLayout.CENTER);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			int lastRun = 0;
			@Override
			public void run() {
				if (running) {
					++lastRun;
					if (lastRun > gameSpeedIntervals[gameSpeed-1]) {
						lastRun = 0;
						CellsHolder.updateNeigboursCount();
						CellsHolder.updateAliveStatus();
					}
				}
				gamePanel.repaint();
			}
		}, 1000, 25);
		
		initOptionsPanel();
		initMenuBar();
		
	}
	
	GamePanel getGamePanel () {
		return gamePanel;
	};
	
	JMenuItem [] getMenuItem () {
		return fileMenuItems;
	}
	
	JButton getStructuresButton () {
		return structuresButton;
	}
	
	JButton getStartButton () {
		return startButton;
	}
	
	JMenuBar getMenubar () {
		return menuBar;
	}
	
	
	private void initMenuBar() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenuItems = new JMenuItem[5];
		
		fileMenuItems[0] = new JMenuItem("Save as...");
		fileMenuItems[1] = new JMenuItem("Open file...");
		fileMenuItems[2] = new JMenuItem("Make quick backup");
		fileMenuItems[3] = new JMenuItem("Load quick backup");
		fileMenuItems[4] = new JMenuItem("Exit");
		
		
		fileMenuItems[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./Saves"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter ("Game of Life Files", "txt", "gof"));
				int returnVal = fileChooser.showDialog(GuiManager.this.getContentPane(), "Save");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					game.getGameMgr().saveToFile(file);
				}
				else if (returnVal == JFileChooser.ERROR_OPTION) {
					System.out.println("Failed opening a file");
				}
			}
		});
		
		fileMenuItems[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./Saves"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter ("Game of Life Files", "txt", "gof"));
				int returnVal = fileChooser.showDialog(GuiManager.this.getContentPane(), "Open");
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					game.getGameMgr().loadFromFile(file);
				}
				else if (returnVal == JFileChooser.ERROR_OPTION) {
					System.out.println("Failed opening a file");
				}
			}
		});
		
		fileMenuItems[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getGameMgr().makeQuickBackup();
			}
		});
		
		fileMenuItems[3].addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getGameMgr().loadQuickBackup();
			}
		});
		
		fileMenuItems[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiManager.this.dispose();	
			}
		});
		
		for (int i = 0; i != 5; ++i) {
			fileMenu.add(fileMenuItems[i]);
		}
		
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}
	
	
	
	private void initOptionsPanel() {
		gameSpeedSlider = new JSlider(JSlider.HORIZONTAL, 1, 4, 2);
		gameSpeedSlider.setMajorTickSpacing(1);
		gameSpeedSlider.setPaintLabels(true);
		gameSpeedSlider.setPaintLabels(true);
		gameSpeedSlider.setPaintTrack(true);
		
		gameSpeedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				gameSpeed = gameSpeedSlider.getValue();
			}
		});
		
		
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		blackCellsButton = new JButton("Black");
		colorCellsButton = new JButton("Color");
		structuresButton = new JButton("Structures");
		stepButton = new JButton("Next step");
		clearAllButton = new JButton("Clear all");
		
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CellsHolder.clearAll();
			}
		});
		
		stepButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				CellsHolder.updateNeigboursCount();
				CellsHolder.updateAliveStatus();
				gamePanel.repaint();
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				running = true;
				// This is tragedy - temporary solution that we will never remove
				timer = new Timer(true);
				
				clearAllButton.setEnabled(false);
				structuresButton.setEnabled(false);
				stepButton.setEnabled(false);
				fileMenu.setEnabled(false);
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearAllButton.setEnabled(true);
				stepButton.setEnabled(true);
				structuresButton.setEnabled(true);
				fileMenu.setEnabled(true);
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				running = false;
			}
		});
		stopButton.setEnabled(false);
		
		structuresButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean status = StructuresDialog.showDialog(GuiManager.this, game);
				if (!status) {
					System.out.println("No structure selected");
				}
				else {
					game.getGameMgr().addStructure(StructuresDialog.getValue());
				}
			}
		});
		
		blackCellsButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setColorCells(false);
				colorCellsButton.setEnabled(true);
				blackCellsButton.setEnabled(false);
				gamePanel.repaint();
			}
		});
		blackCellsButton.setEnabled(false);
		
		colorCellsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gamePanel.setColorCells(true);
				colorCellsButton.setEnabled(false);
				blackCellsButton.setEnabled(true);
				gamePanel.repaint();
			}
		});
		
		GridLayout g = new GridLayout(1, 2);
		g.setHgap(5);
		g.setVgap(5);
		
		JPanel p1 = new JPanel(g);
		JPanel p2 = new JPanel(g);
		JLabel manual = new JLabel ("Manual:");
		JLabel manual1 = new JLabel ("R - rotate structure");
		JLabel manual2 = new JLabel ("T - Flip structure");
		p1.add(startButton);
		p1.add(stopButton);
		p2.add(blackCellsButton);
		p2.add(colorCellsButton);
		
		optionsPanel.add(p1);
		optionsPanel.add(new JLabel("Set game speed"));
		optionsPanel.add(gameSpeedSlider);
		optionsPanel.add(new JLabel("Choose color of cells"));
		optionsPanel.add(p2);
		optionsPanel.add(structuresButton);
		optionsPanel.add(stepButton);
		optionsPanel.add(clearAllButton);
		
		optionsPanel.add(manual);
		optionsPanel.add(manual1);
		optionsPanel.add(manual2);
		
		

		
	}
	
}