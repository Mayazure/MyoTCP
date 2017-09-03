package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Duration;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.Server;
import util.FileOperator;
import util.MyoTool;
import util.TimeParser;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton testButton;
	private JButton startButton;
	private JButton sendButton;
	private JButton closeButton;
	private JButton submitButton;
	private JButton sigButton;
	private JButton noticeButton;
	private JButton pingButton;
	private JTextArea console;

	private JLabel ipField;
	private JLabel testField;
	private JLabel countLabel;
	private JLabel waitLabel;

	private JTextField commandField;
	//	private DefaultListModel<String> listModel;
	//	private JList<String> list;

	private Server server;
	private boolean serverIsOn = false;
	private boolean isTest = false;
	private boolean isWaiting = false;
	private boolean endSignal = false;
	private FileOperator fileOperator = FileOperator.getFileOperator();
	private int countTotal = 0;
	private int countTest = 0;
	private int countExp = 0;

	private long tempTimestamp = 0;
	private long pingTimestamp = 0;

	public MainWindow(){

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
		this.setSize(800,640);

		//Main Panel
		JPanel mainPanel = new JPanel();
		BoxLayout mainPanelLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
		mainPanel.setLayout(mainPanelLayout);
		this.setContentPane(mainPanel);

		//Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		BoxLayout controlPanelLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		controlPanel.setLayout(controlPanelLayout);

		console = new JTextArea();
		console.setEditable(false);
		//		console.setForeground(Color.white);
		//		console.setBackground(Color.black);
		Font font = new Font("Default",Font.PLAIN,16);
		console.setFont(font);
		JScrollPane consolePanel = new JScrollPane(console);
		controlPanel.add(consolePanel);

		Box infoBox = Box.createHorizontalBox();
		ipField = new JLabel("000.000.000.000");
		//		ipField.setOpaque(true);
		ipField.setMinimumSize(new Dimension(100,50));
		//		ipField.setMaximumSize(new Dimension(2000, 50));


		testField = new JLabel("       TEST OFF");	
		//		testField.setOpaque(true);
		waitLabel = new JLabel("       WAIT OFF");

		countLabel = new JLabel("       Total: " + countTotal + " | Exp: " + countExp + " | Test: " + countTest);

		infoBox.add(ipField);
		infoBox.add(testField);
		infoBox.add(waitLabel);
		infoBox.add(countLabel);
		infoBox.add(Box.createHorizontalGlue());


		controlPanel.add(infoBox);

		commandField = new JTextField();
		commandField.setMaximumSize(new Dimension(2000,50));
		controlPanel.add(commandField);

		JPanel buttonPanel = new JPanel();
		BoxLayout buttonPaneLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		buttonPanel.setLayout(buttonPaneLayout);
		testButton = new JButton("Test");
		startButton = new JButton("Start");
		sendButton = new JButton("Send");
		closeButton = new JButton("Close");
		submitButton = new JButton("Submit");
		sigButton = new JButton("SIG");
		noticeButton = new JButton("Notice");
		pingButton = new JButton("Ping");
		buttonPanel.add(testButton);
		buttonPanel.add(startButton);
		buttonPanel.add(sigButton);
		buttonPanel.add(sendButton);
//		buttonPanel.add(noticeButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(pingButton);
		buttonPanel.add(closeButton);
		controlPanel.add(buttonPanel);

		mainPanel.add(controlPanel);

		//Info Panel
		//		JPanel infoPanel = new JPanel();
		//		BoxLayout listPanelLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		//		infoPanel.setLayout(listPanelLayout);
		//		infoPanel.setMaximumSize(new Dimension(100,2000));

		//		JLabel label = new JLabel("Connecting Clients");
		//		infoPanel.add(label);

		//		listModel = new DefaultListModel<String>();
		//		listModel.addElement("000.000.000.000");
		//		list = new JList<String>(listModel);
		//		JScrollPane listPanel = new JScrollPane(list);		
		//		infoPanel.add(listPanel);

		//		mainPanel.add(infoPanel);

		//Events
		
		commandField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					if(serverIsOn&&(!isWaiting)){
						isWaiting = true;
						waitLabel.setForeground(Color.RED);
						waitLabel.setText("       WAIT  ON");

						tempTimestamp = System.currentTimeMillis();
						server.send(ipField.getText(), commandField.getText());
					}
					else{
						updateConsole("Waiting for response.");
						server.send(ipField.getText(), "NOTI");
					}
				}
			}
		});
		
		testButton.addActionListener(new ActionListener() {

			//			Font pfont = testButton.getFont();
			//			int size = pfont.getSize();

			@Override
			public void actionPerformed(ActionEvent e) {

				if(isTest){
					//					testButton.setFont(pfont);
					testButton.setBackground(Color.BLACK);
					testButton.setForeground(Color.BLACK);
					testField.setForeground(Color.BLACK);
					testField.setText("       TEST OFF");
				}
				else{

					//					Font font = new Font("Default",Font.BOLD,size);
					//					testButton.setFont(font);
					testButton.setForeground(Color.RED);
					testButton.setBackground(Color.RED);
					testField.setForeground(Color.RED);
					testField.setText("       TEST  ON");
				}
				isTest = !isTest;
			}
		});

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!serverIsOn){
					String startInfo = "Experiment start."; 
					updateConsole(startInfo);
					recordToFile(startInfo);
					serverIsOn = true;
					Thread thread = new Thread(server);
					thread.start();
				}
				else{
					updateConsole("Server already on.");
				}
			}
		});

		sigButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				commandField.setText("SIG");
			}
		});

		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(serverIsOn&&(!isWaiting)){
					
					isWaiting = true;
					waitLabel.setForeground(Color.RED);
					waitLabel.setText("       WAIT  ON");

					tempTimestamp = System.currentTimeMillis();
					server.send(ipField.getText(), commandField.getText());
				}
				else{
					updateConsole("Waiting for response.");
					server.send(ipField.getText(), "NOTI");
				}
				//debug
				//TimeParser.getCurrentHourTime();
			}
		});
		
		noticeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isWaiting){
					server.send(ipField.getText(), "NOTI");
				}
				else{
					updateConsole("No waiting response.");
				}
			}
		});
		
		pingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ping();
			}
		});

		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(serverIsOn){
					server.send(ipField.getText(), "SUB");
				}
			}
		});

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				server.closeAll();
			}
		});

		//		list.addListSelectionListener(new ListSelectionListener() {
		//
		//			@Override
		//			public void valueChanged(ListSelectionEvent e) {
		//				ipField.setText(list.getSelectedValue());
		//
		//			}
		//		});


		//MainWindow
		//		this.setMinimumSize(new Dimension(960,640));
		//		this.setMaximumSize(new Dimension(960,640));
		this.setMinimumSize(new Dimension(800,640));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Non-UI parts
		server = new Server(this);
	}

	public void updateConsole(String ip, String data){
		String date = MyoTool.getDate();
		this.console.append(date);
		if(isTest){
			this.console.append("TEST");
		}
		this.console.append("> ");
		this.console.append(ip+": ");
		String preData = "$T1=" + tempTimestamp;
		this.console.append(preData);
		this.console.append(data);
		this.console.append("\r\n");
		console.setCaretPosition(console.getDocument().getLength());
		//		System.out.println(preData+data);

		if(isTest){
			countTest++;
			recordToFile("TEST:" + preData + data);
		}
		else{
			countExp++;
			recordToFile(preData + data);
		}
		countTotal = countTest+countExp;
		updateCount();
		
		isWaiting = false;
		waitLabel.setForeground(Color.BLACK);
		waitLabel.setText("       WAIT OFF");
	}

	public void updateConsole(String data){
		String date = MyoTool.getDate();
		this.console.append(date);
		this.console.append("> ");
		this.console.append(data);
		this.console.append("\r\n");
		console.setCaretPosition(console.getDocument().getLength());
	}

	public void updateIP(String IP){
		this.ipField.setText(IP);
		//		this.listModel.addElement(IP);
	}
	//
	//	public void deleteIP(String IP){
	//		this.listModel.removeElement(IP);
	//	}

	private void recordToFile(String data){
		fileOperator.saveToFile(data);
	}

	private void updateCount(){
		String count = "       Total: " + countTotal + " | Exp: " + countExp + " | Test: " + countTest;
		this.countLabel.setText(count);
	}

	public void endSignal(){
		endSignal = true;
		String closeInfo = "Total:" + countTotal + ";Exp:" + countExp + ";Test:" + countTest+";";
		updateConsole(closeInfo);
		recordToFile(closeInfo);
	}
	
	private void ping(){
		server.send(ipField.getText(), "PING");
		pingTimestamp = System.currentTimeMillis();
	}
	
	public void onPing(){
		long timestamp = System.currentTimeMillis() - pingTimestamp;
		double duration = timestamp/1000.0;
		String pingRes = "Ping response in "+duration+" seconds";
		updateConsole(pingRes);
	}

}