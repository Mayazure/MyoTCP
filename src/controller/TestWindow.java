package controller;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

public class TestWindow extends JFrame{

	
	public TestWindow() {
		
		Box mainBox = Box.createHorizontalBox();
		this.setContentPane(mainBox);
		
		JButton button = new JButton("button");
		
		mainBox.add(button);
		
		this.setMinimumSize(new Dimension(960,640));
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {
		TestWindow tw = new TestWindow();
		
	}
}
