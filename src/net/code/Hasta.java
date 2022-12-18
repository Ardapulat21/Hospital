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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hasta extends Ortak {
	 
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";	
	public Hasta() {			
		JFrame f=new JFrame("Hasta");
		
		JLabel ll=new JLabel("Hasta Kayıt");		
		ll.setBounds(160,10, 100, 25);
		
		JLabel l=new JLabel("Hastanın adı:");		
		l.setBounds(70,50, 100, 25);
		
		
		JLabel l2=new JLabel("Hastanın soyadı:");
		l2.setBounds(70, 80, 100, 25);
		
		
		JLabel l3=new JLabel("Şikayet:");
		l3.setBounds(70, 110, 100, 25);
		
		JTextField t=new JTextField();
		t.setBounds(200, 50, 100, 25);
		t.setVisible(true);
		
		JTextField t2=new JTextField();
		t2.setBounds(200,80,100,25);
		t2.setVisible(true);
		
		JTextArea tx=new JTextArea();
		
		tx.setBounds(200, 110, 100, 50);
		
		JLabel l4=new JLabel("Doktorlar:");
		l4.setBounds(70, 170, 100, 25);
		
		String personel="";
		DefaultListModel<String> list1 = new DefaultListModel<>(); 
		try {			
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			String sql="SELECT * FROM PERSONEL";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);			
			while(result.next()) {				
				if(result.getString("gorevi").equals("DOKTOR")) 										
					list1.addElement(result.getString("ADSOYAD"));															
			}			
			connection.close();
		}catch(SQLException x) {
			System.out.println(x);
		}
		
		
		JList<String> list=new JList(list1);				
		JScrollPane scrollpane=new JScrollPane();
		scrollpane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		scrollpane.setBounds(200, 170, 110, 100);	
				
		
		JButton b=new JButton("Randevu");
		b.setBounds(150, 300, 110, 30);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection=DriverManager.getConnection(jdbcUrl,username,password);		
					String sql="INSERT INTO PATIENT VALUES(?,?,?,?,?);";
					java.sql.PreparedStatement statement=connection.prepareStatement(sql);
					statement.setInt(1, getCounter("patient"));
					statement.setString(2, t.getText()+" "+t2.getText());
					statement.setString(3, tx.getText());
					statement.setString(4,"06.12.2020");
					statement.setString(5, list.getSelectedValue());
					int rows=statement.executeUpdate();
					if(rows>0) {
						JOptionPane.showMessageDialog(null,"Randevu oluşturuldu!","DB",1);
					}
					t.setText("");
					t2.setText("");
					tx.setText("");
					list.setSelectedIndex(0);
				}catch(SQLException x) {
					System.out.println(x);
				}
			}
			
		});
		
		f.add(ll);
		f.add(b);
		f.add(scrollpane);
		f.add(t);
		f.add(t2);
		f.add(l);
		f.add(l2);
		f.add(l3);
		f.add(l4);
		f.add(tx);
		
		
		f.setLayout(null);
		f.setVisible(true);
		f.setSize(400,400);
		
		
		
	}
}
