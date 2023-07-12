package gof;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class StructuresDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	static GameOfLife game;
	
	private static StructuresDialog dialog;
	private static String value = "";
	private static boolean selectionStatus = false;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	
	public static boolean showDialog(Component comp, GameOfLife g) {
		
		dialog = new StructuresDialog(JOptionPane.getFrameForComponent(comp), g);
		
		dialog.setVisible(true);
		return selectionStatus;
	} 

	
	public StructuresDialog(Frame owner, GameOfLife g) {
		super(owner, "Game of Life - structures", true);
		this.setSize(400, 200);
		game = g;
		// Adding bottom panel for buttons
		JPanel bottomPanel = new JPanel();
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		
		// Adding list on the left
		listModel = new DefaultListModel<String>();
		listModel.addElement("Pond");
		listModel.addElement("Glider");
		listModel.addElement("Glider Spawner");
		
		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScrollPane = new JScrollPane(list);
		this.add(listScrollPane);
		
		
		
		// Creating and adding the buttons
		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSelectionStatus(true);
				setValue(list.getSelectedValue());
				setVisible(false);
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

}