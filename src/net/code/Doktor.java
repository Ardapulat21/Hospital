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

public class Doktor {
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";
		
	//public Doktor(String dad) {	
		
	public Doktor(String dad) {
		JFrame f =new JFrame("Doktor");		
		JLabel l=new JLabel("Hastalarım:");
		l.setBounds(50, 20, 100, 25);
		
		JLabel l2=new JLabel("Reçete:");
		l2.setBounds(200, 20, 100, 25);
		
		JTextArea tx=new JTextArea();
		tx.setBounds(160,40, 120, 100);
		
		JButton b2=new JButton("Reçete");
		b2.setBounds(160, 150, 120, 20);	
		
		JButton b=new JButton("Randevu iptal");
		b.setBounds(20, 150, 120, 20);
			
		DefaultListModel<String> list1 = new DefaultListModel<>();
		try {
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			String sql="SELECT * FROM PATIENT";	
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()) {
				if(result.getString("doktor").equals(dad)) {
					list1.addElement(result.getString("adsoyad"));
				}
			}			
			connection.close();
		}catch(SQLException x) {
			System.out.println(x);
		}
		JList<String> list=new JList(list1);				
		JScrollPane scrollpane=new JScrollPane();
		scrollpane.setViewportView(list);
		list.setLayoutOrientation(JList.VERTICAL);
		scrollpane.setBounds(20, 40, 120, 100);	
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection =DriverManager.getConnection(jdbcUrl,username,password);
					String sql="DELETE FROM PATIENT WHERE ADSOYAD=?";
					java.sql.PreparedStatement statement=connection.prepareStatement(sql);
					statement.setString(1, list.getSelectedValue());
					statement.executeUpdate();
					list1.removeElementAt(list1.indexOf(list.getSelectedValue()));
					JOptionPane.showMessageDialog(null,"Randevu iptal edildi!","DB",1);	
				}catch(SQLException x) {
					System.out.println(x);
				}			
			}			
		});
		
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection =DriverManager.getConnection(jdbcUrl,username,password);
					String sql="INSERT INTO MUAYENE VALUES(?,?)";
					java.sql.PreparedStatement statement=connection.prepareStatement(sql);
					statement.setString(1, list.getSelectedValue());
					statement.setString(2, tx.getText());
					statement.executeUpdate();		
					sql="DELETE FROM PATIENT WHERE ADSOYAD=?";
					java.sql.PreparedStatement st=connection.prepareStatement(sql);
					st.setString(1, list.getSelectedValue());
					st.executeUpdate();
					list1.removeElementAt(list1.indexOf(list.getSelectedValue()));
					tx.setText("");
				}catch(SQLException x) {
					System.out.println(x);												
			}}
		});
		
		f.add(b2);
		f.add(l2);
		f.add(tx);
		f.add(b);
		f.add(l);
		f.add(scrollpane);
		f.setLayout(null);
		f.setVisible(true);
		f.setSize(400,400);
		
	}
}
