package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.FileOperator;
import util.MyoTool;

public class StampWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton stampstartButton;
	private JButton stampendButton;
	private JButton closeButton;

	private JButton labExp;
	private JButton labBse;
	private JButton labFree;
	private JButton labHigh;
	private JButton labMotor;
	private JButton labMission;

	private JTextArea console;
	private JTextField commandField;


	public StampWindow(){

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImageIcon imageIcon = new ImageIcon(getClass().getResource("/image/icon.png"));  
		this.setIconImage(imageIcon.getImage()); 
		this.setTitle("MyoDriving Tool");
		this.setSize(960,640);

		//Main Panel
		JPanel mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(mainPanelLayout);
		this.setContentPane(mainPanel);

		//Control Panel
		JPanel controlPanel = new JPanel();
		BoxLayout controlPanelLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(controlPanelLayout);

		console = new JTextArea();
		console.setEditable(false);
		console.setForeground(Color.white);
		console.setBackground(Color.black);
		Font font = new Font("Default",Font.PLAIN,16);
		console.setFont(font);
		JScrollPane consolePanel = new JScrollPane(console);
		controlPanel.add(consolePanel);

		//		ipField = new JTextField();
		//		ipField.setEditable(false);
		//		ipField.setMaximumSize(new Dimension(2000, 50));
		//		controlPanel.add(ipField);

		labExp = new JButton("Exp");
		labBse = new JButton("Bse");
		labFree = new JButton("Free");
		labHigh = new JButton("High");
		labMotor = new JButton("Motor");
		labMission = new JButton("Mission");

		JPanel labelPanel = new JPanel();
		BoxLayout labelPaneLayout = new BoxLayout(labelPanel, BoxLayout.X_AXIS);
		labelPanel.setLayout(labelPaneLayout);
		labelPanel.add(labExp);
		labelPanel.add(labBse);
		labelPanel.add(labFree);
		labelPanel.add(labHigh);
		labelPanel.add(labMotor);
		labelPanel.add(labMission);
		controlPanel.add(labelPanel);

		commandField = new JTextField();
		commandField.setMaximumSize(new Dimension(2000,50));
		controlPanel.add(commandField);

		JPanel buttonPanel = new JPanel();
		BoxLayout buttonPaneLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		buttonPanel.setLayout(buttonPaneLayout);
		stampstartButton = new JButton("Stamp Start");
		stampendButton = new JButton("Stamp End");
		closeButton = new JButton("Close");
		buttonPanel.add(stampstartButton);
		buttonPanel.add(stampendButton);
		buttonPanel.add(closeButton);
		controlPanel.add(buttonPanel);

		mainPanel.add(controlPanel);

		//Events
		/*stampstartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		stampendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});*/

		ActionListener actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String source = e.getActionCommand();
				if(source.equals("Stamp Start")){
					console.append("start [");
					console.append(commandField.getText().toString());
					console.append("] "+System.currentTimeMillis());
					console.append("\r\n");
				}
				else if(source.equals("Stamp End")){
					console.append("end [");
					console.append(commandField.getText().toString());
					console.append("] "+System.currentTimeMillis());
					console.append("\r\n");
				}


			}
		};

		ActionListener labelListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String source = e.getActionCommand();

				if(source.equals("Exp")){
					commandField.setText("Exp");
				}
				else if(source.equals("Bse")){
					commandField.setText("Bse");
				}
				else if(source.equals("Free")){
					commandField.setText("Free");
				}
				else if(source.equals("High")){
					commandField.setText("High");
				}
				else if(source.equals("Motor")){
					commandField.setText("Motor");
				}
				else if(source.equals("Mission")){
					commandField.setText("Mission");
				}
//				else if(source.equals("")){
//					commandField.setText("");
//				}
			}
		};
		
		stampstartButton.addActionListener(actionListener);
		stampendButton.addActionListener(actionListener);

		labExp.addActionListener(labelListener);
		labBse.addActionListener(labelListener);
		labFree.addActionListener(labelListener);
		labHigh.addActionListener(labelListener);
		labMotor.addActionListener(labelListener);
		labMission.addActionListener(labelListener);

		//MainWindow
		//		this.setMinimumSize(new Dimension(960,640));
		//		this.setMaximumSize(new Dimension(960,640));
		this.setMinimumSize(new Dimension(960,640));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void updateConsole(String data){
		String date = MyoTool.getDate();
		this.console.append(date);
		this.console.append("> ");
		this.console.append(data);
		this.console.append("\r\n");
		console.setCaretPosition(console.getDocument().getLength());
	}

	public void recordToFile(String data){
//		FileOperator.saveToFile(data);
	}

}