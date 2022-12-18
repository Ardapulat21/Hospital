package net.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

// HASTA ve PERSONELEKLE sınıfları Ortak sınıfını extends etti!

public class Menu {
	public static void main(String[] args) {
		
		JFrame f=new JFrame("Menu");
		
		JButton button=new JButton("Personel");
		button.setBounds(130,150,130,30);
		button.setVisible(true);		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login l=new Login();
				
			}			
		});
		
		JButton button2=new JButton("Hasta Kayıt");
		button2.setBounds(130,220,130,30);
		button2.setVisible(true);
		
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Hasta h=new Hasta();					
			}			
		});
	
		f.add(button2);
		f.add(button);
		f.setLayout(null);
		f.setSize(400,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
