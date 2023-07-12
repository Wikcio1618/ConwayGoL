package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

// Author Wikcio
public class StructuresDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	static GameOfLife game;
	
	private static StructuresDialog dialog;
	private static String value = "";
	private static boolean selectionStatus = false;
	private JList<String> list;
	private static DefaultListModel<String> listModel;
	
	private static int types = 4;
	private static int maxMembers = 20;
	private static String[][] structuresLib = new String[types][maxMembers];
	private JCheckBox[] boxes;

	
	
	public static boolean showDialog(Component comp, GameOfLife g) {
		
		if (dialog == null)
			dialog = new StructuresDialog(JOptionPane.getFrameForComponent(comp), g);
		
		dialog.setVisible(true);
		return selectionStatus;
	} 

	
	public StructuresDialog(Frame owner, GameOfLife g) {
		super(owner, "Game of Life - structures", true);
		this.setSize(480, 480);
		game = g;
		// Adding bottom panel for buttons
		JPanel bottomPanel = new JPanel();
		JPanel titlesPanel = new JPanel();
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		// Adding list on the left
				listModel = new DefaultListModel<String>();

				//Oscillators - type 0
				addStructure (0, "Pond");
				addStructure (0, "Pulsar");
				addStructure (0, "I-Column");
				addStructure (0, "Gabriel's p138");
				//Long living - type 1
				addStructure (1, "The R-pentomino");
				addStructure (1, "Diehard");
				addStructure (1, "Acorn");
				//Spaceships - type 2
				addStructure (2, "Glider");
				addStructure (2, "Light-weight spaceship");
				addStructure (2, "Middle-weight spaceship");
				addStructure (2, "Heavy-weight spaceship");
				addStructure (2, "Glider synthesis");
				//Guns - type 3
				addStructure (3, "Simkin glider gun");
				addStructure (3, "Gosper glider gun");		
		
		/* ^^^ If you add something here, remember to go to Structure Class ^^^^^^^^^
		 *		and create a structure with its vectors 						 	*/
				
				
		//Adding upper panel with checkboxes
		this.add(titlesPanel, BorderLayout.NORTH);
		boxes = new JCheckBox[types + 1];
		ButtonGroup checkBoxGroup = new ButtonGroup();
		
		for (int i = 0; i < types + 1; i++) {
			boxes[i] = new JCheckBox("", true);
			checkBoxGroup.add(boxes[i]);
			titlesPanel.add(boxes[i]);
		}

		boxes[0].setText("ALL");
		boxes[1].setText("Oscillators");
		boxes[2].setText("Metuselahs");
		boxes[3].setText("Spaceships");
		boxes[4].setText("Guns");
				
		boxes[0].addItemListener(new ItemListener () {
			@Override
			public void itemStateChanged (ItemEvent e) {
				listModel.removeAllElements();
				for (int t = 0; t < types; t++)
					for (int m = 0; m < maxMembers; m++)
						if (structuresLib[t][m] != null) {
							listModel.addElement (structuresLib[t][m]);
						}
						else 
							break;
			}
		});
		boxes[1].addItemListener(new ItemListener () {
			@Override
			public void itemStateChanged (ItemEvent e) {
				listModel.removeAllElements();
				for (int m = 0; m < maxMembers; m++)
					if (structuresLib[0][m] != null) {
						listModel.addElement (structuresLib[0][m]);
					}
					else 
						break;
			}
		});
		boxes[2].addItemListener(new ItemListener () {
			@Override
			public void itemStateChanged (ItemEvent e) {
				listModel.removeAllElements();
				for (int m = 0; m < maxMembers; m++)
					if (structuresLib[1][m] != null) {
						listModel.addElement (structuresLib[1][m]);
					}
					else 
						break;
			}
		});
		boxes[3].addItemListener(new ItemListener () {
			@Override
			public void itemStateChanged (ItemEvent e) {
				listModel.removeAllElements();
				for (int m = 0; m < maxMembers; m++)
					if (structuresLib[2][m] != null) {
						listModel.addElement (structuresLib[2][m]);
					}
					else 
						break;
			}
		});
		boxes[4].addItemListener(new ItemListener () {
			@Override
			public void itemStateChanged (ItemEvent e) {
				listModel.removeAllElements();
				for (int m = 0; m < maxMembers; m++)
					if (structuresLib[3][m] != null) {
						listModel.addElement (structuresLib[3][m]);
					}
					else 
						break;
			}
		});
		
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(list);
		this.add(listScrollPane);
		
		
		// Creating and adding the buttons
		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedValue() != null) {
					setSelectionStatus(true);
					setValue(list.getSelectedValue());
					setVisible(false);
				}
			}
		});
		bottomPanel.add(selectButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		bottomPanel.add(cancelButton);
		
		// Setting the default
		setSelectionStatus(false);
	}
	
	/*TODO - Should take care of (dis)enabling buttons but gets in a way of Load File menu option.
	 *Can be fixed by making Load File always enabled but informing the user 
	 *either on terminal or in frame notification 
	 *when there is no backup file yet
	 *See: GameManager -> public void makeQuickBackup()*/
	static void setSelectionStatus(boolean b) {
		selectionStatus = b;
		if (selectionStatus) {
			game.getGuiMgr().getStartButton().setEnabled(false);
			for (int i = 0; i < 4; i ++)
				game.getGuiMgr().getMenuItem()[i].setEnabled(false);
			game.getGuiMgr().getStructuresButton().setEnabled(false);
		}
		else if (!selectionStatus) {
			game.getGuiMgr().getStartButton().setEnabled(true);
			for (int i = 0; i < 4; i ++)
				game.getGuiMgr().getMenuItem()[i].setEnabled(true);
			game.getGuiMgr().getStructuresButton().setEnabled(true);
		}
	}


	private void setValue(String newVal) {
		value = newVal;
	}
	
	
	public static String getValue() {
		return value;
	}

	public String getChosenOption() {
		return value;
	}


	public static boolean getStatus() {
		return selectionStatus;
	}

	private static void addStructure (int type, String name) {
			for (int j = 0; j != maxMembers; ++j) 
				if (structuresLib[type][j] == null) {
					structuresLib[type][j] = new String (name);
					listModel.addElement(structuresLib[type][j]);
					break;
				}
	}
}