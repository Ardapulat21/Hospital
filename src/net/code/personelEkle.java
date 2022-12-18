package net.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class personelEkle extends Ortak{	
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";
	
	public personelEkle() {
		
		JFrame f =new JFrame("Personel ekle");		
		JLabel l=new JLabel("Personel ad-soyad:");
		l.setBounds(20, 20, 120, 20);		
		JTextField t=new JTextField();
		t.setBounds(154, 22, 100, 20);
		
		JLabel l2=new JLabel("Personel kullanıcı adı:");
		l2.setBounds(20, 50, 140, 20);		
		JTextField t2=new JTextField();
		t2.setBounds(154, 52, 100, 20);
		
		JLabel l3=new JLabel("Personel şifre:");
		l3.setBounds(20, 80, 120, 20);		
		JTextField t3=new JTextField();
		t3.setBounds(154,82, 100, 20);
		
		JLabel l4=new JLabel("Personel görev:");
		l4.setBounds(20, 110, 120, 20);	
		DefaultListModel<String> ll = new DefaultListModel<>(); 
		
		ll.addElement("DOKTOR");
		ll.addElement("SAGLIK YONETICISI");
		ll.addElement("TEMIZLIK PERSONELI");
		
		JList<String> list=new JList<>(ll);
		list.setBounds(154, 110, 160, 60);
		
		JLabel l5=new JLabel("Adres:");
		l5.setBounds(20, 180, 120, 20);
		
		JLabel l6=new JLabel("Telefon:");
		l6.setBounds(20, 210, 120, 20);
		
		JTextField t5=new JTextField();
		t5.setBounds(154, 212, 120, 20);
		
		JTextField t4=new JTextField();
		t4.setBounds(154, 180, 120, 20);
		
		JButton b=new JButton("Personel Ekle");
		b.setBounds(140, 300, 130, 30);
		b.addActionListener(new ActionListener() {
		String sql="";
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection=DriverManager.getConnection(jdbcUrl,username,password);		
					sql="INSERT INTO PERSONEL VALUES(?,?,?,?,?,?);";
					java.sql.PreparedStatement statement=connection.prepareStatement(sql);
					statement.setInt(1, getCounter("personel")+1);
					statement.setString(2, t2.getText());
					statement.setString(3, t3.getText());
					statement.setString(4, t.getText());
					statement.setString(5, list.getSelectedValue());
					switch(list.getSelectedValue()) {
					case "DOKTOR":
						statement.setInt(6,10000);
						break;
					case "SAGLIK YONETICISI":
						statement.setInt(6,15000);
						break;
					case "TEMIZLIK PERSONELI":
						statement.setInt(6,4000);
						break;
					}					
					statement.executeUpdate();
					sql="INSERT INTO PERSONELBILGI VALUES(?,?,?,?);";
					statement=connection.prepareStatement(sql);
					statement.setInt(1, getCounter("personelbilgi"));
					statement.setString(2,t.getText());
					statement.setString(3, t4.getText());
					statement.setString(4, t5.getText());
					statement.executeUpdate();
					
				}catch(SQLException x) {
					System.out.println(x);
				}
			
				t.setText("");
				t2.setText("");
				t3.setText("");
				t4.setText("");			
				list.clearSelection();
			}						
		});
		f.add(l6);
		f.add(t5);
		f.add(t4);
		f.add(l5);
		f.add(l4);
		f.add(b);
		f.add(list);
		f.add(t);
		f.add(l);
		f.add(t2);
		f.add(l2);
		f.add(t3);
		f.add(l3);
		
		f.setLayout(null);
		f.setVisible(true);
		f.setSize(400,400);	
	}
}
