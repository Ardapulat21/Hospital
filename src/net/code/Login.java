package net.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login {
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";

	static String getDoktor(String id,String pass) {
		try {			
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			String sql="SELECT * FROM PERSONEL";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);			
			while(result.next()) {				
				if(id.equals(result.getString("kad")) && pass.equals(result.getString("sifre")) && result.getString("gorevi").equals("DOKTOR")) {					
					return result.getString("ADSOYAD");																				
				}											
			}			
			connection.close();
		}catch(SQLException x) {
			System.out.println(x);
		}
		return null;
	}
	
	static boolean isInto(String jdbc,String username,String password,String t1,String t2) {
		try {
			Connection connection=DriverManager.getConnection(jdbc,username,password);
			String sql="SELECT * FROM ACCOUNT";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);			
			while(result.next()) {
				String id =result.getString("id");
				String pass=result.getString("password");				
				if(id.equals(t1) && pass.equals(t2)) {
					System.out.println("Registiration has found!");
					return true;
				}
			}							
			connection.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Login(){								
				
		
		JLabel l3=new JLabel("Personel");
		l3.setVisible(true);
		l3.setBounds(190, 25, 100, 20);
		
		JFrame frame=new JFrame("Login");
		JTextField t1;
		JPasswordField t2;
		JCheckBox c=new JCheckBox("Şifreyi göster!");
		c.setBounds(150,150,140,20);
		
		t1=new JTextField();
		t1.setBounds(160, 60, 120, 30);
		
		t2=new JPasswordField();
		t2.setBounds(160, 100, 120, 30);
				
		JLabel l=new JLabel("Kullanıcı adı:");
		l.setBounds(60, 60, 80, 30);
		l.setVisible(true);
		JLabel l2=new JLabel("Şifre");
		l2.setBounds(60, 100, 80, 30);
		l2.setVisible(true);
		
		JButton button=new JButton("Giriş");			
		button.setBounds(150, 180, 130, 30);			
		button.setVisible(true);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {			
					Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
					String sql="SELECT * FROM PERSONEL";
					java.sql.Statement statement=connection.createStatement();
					ResultSet result=statement.executeQuery(sql);	
					boolean s=false;
					while(result.next()) {				
						if(t1.getText().equals(result.getString("kad")) &&t2.getText().equals(result.getString("sifre"))) {
							if(result.getString("gorevi").equals("DOKTOR")) {
								Doktor d=new Doktor(getDoktor(t1.getText(), t2.getText()));	
								s=true;
							}
							else if(result.getString("gorevi").equals("SAGLIK YONETICISI")) {
								Saglikyoneticisi sy=new Saglikyoneticisi();
								s=true;
							}
						}											
					}
					if(!s)						
						JOptionPane.showMessageDialog(null,"Geçersiz kullanıcı adı veya şifre","DB",1);	
					
					connection.close();
				}catch(SQLException x) {
					System.out.println(x);
				}
				
				
				/*if(isInto(jdbcUrl,username,password,t1.getText(),t2.getText())) {
					JOptionPane.showMessageDialog(null,"Login has been accomplished!","DB",1);
					//AccountInfo a=new AccountInfo(t1.getText(),t2.getText());
					//frame.setVisible(false);
					
				}else {
					JOptionPane.showMessageDialog(null,"invalid id or password","DB",1);	
				}	*/			
			}				
		});
		JButton button2=new JButton("Back");			
		button2.setBounds(150, 220, 130, 30);			
		button2.setVisible(true);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {																	
				frame.setVisible(false);
			}				
		});
		
		c.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(c.isSelected())	{
					t2.setEchoChar((char)0);
				}else {
					t2.setEchoChar('*');
				}				
			}   			
		});
				
		
		frame.add(l3);
		frame.add(c);
		frame.add(t1);
		frame.add(t2);
		frame.add(l);
		frame.add(l2);			
		frame.add(button);
		frame.add(button2);
		frame.setLayout(null);
		frame.setSize(400,400);
		frame.setVisible(true);
			/*
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			System.out.println("Connected!");
			String sql="SELECT * FROM ACCOUNT";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()) {
				String id =result.getString("id");
				String pass=result.getString("password");
				System.out.println("id:"+id+" password:"+pass);			
			}								
			connection.close();
			*/
									

	}
}
