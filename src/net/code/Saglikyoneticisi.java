package net.code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Saglikyoneticisi {
	DefaultListModel<String> l = new DefaultListModel<>();  
	DefaultListModel<String> ll = new DefaultListModel<>();  
	DefaultListModel<String> lll = new DefaultListModel<>();  
	JList<String> l1,l2,l3;
	
	void func() {
		String adsoyad,gorevi,maas;
		l.removeAllElements();
		ll.removeAllElements();
		lll.removeAllElements();
		try {
			Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
			String sql="SELECT * FROM PERSONEL";
			java.sql.Statement statement=connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()) {
				adsoyad=result.getString("adsoyad");
				gorevi=result.getString("gorevi");
				maas=String.valueOf(result.getInt("maas"));
				System.out.println(maas);
				l.addElement(adsoyad);
				ll.addElement(gorevi);
				lll.addElement(maas);				
			}						
			connection.close();
		}catch(SQLException x) {
			System.out.println(x);
		}
		l1=new JList<>(l);
		l2=new JList<>(ll);
		l3=new JList<>(lll);
	}
	static String jdbcUrl="jdbc:postgresql://localhost:5432/Hospital";
	static String username="postgres";
	static String password="admin";
	public Saglikyoneticisi() {
		JFrame f =new JFrame("Sağlık yöneticisi");	
		JLabel lb1=new JLabel("Ad soyad:");
		JLabel lb2=new JLabel("Görevi:");
		JLabel lb3=new JLabel("Maaş:");
		
		JButton b=new JButton("Personel Cikar");
		b.setBounds(10,150,150,30);
		
		JButton b2=new JButton("Personel Ekle");
		b2.setBounds(220,150,150,30);
		
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				personelEkle p=new personelEkle();								
			}			
		});				
		func();				
		
		l1.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				l2.setSelectedIndex(l1.getSelectedIndex());
				l3.setSelectedIndex(l1.getSelectedIndex());												
			}			
		});
		
		l2.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				l1.setSelectedIndex(l1.getSelectedIndex());
				l3.setSelectedIndex(l1.getSelectedIndex());												
			}			
		});
		
		l3.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				l2.setSelectedIndex(l1.getSelectedIndex());
				l1.setSelectedIndex(l1.getSelectedIndex());												
			}			
		});
		
		JScrollPane s= new JScrollPane();
	    s.setViewportView(l1);
	    l1.setLayoutOrientation(JList.VERTICAL);
	    
	    JScrollPane s2 = new JScrollPane();
	    s2.setViewportView(l2);
	    l2.setLayoutOrientation(JList.VERTICAL);
	    
	    JScrollPane s3= new JScrollPane();
	    s3.setViewportView(l3);
	    l3.setLayoutOrientation(JList.VERTICAL);
	    
	    b.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection=DriverManager.getConnection(jdbcUrl,username,password);
					String sql="DELETE FROM PERSONEL WHERE adsoyad=?";
					java.sql.PreparedStatement statement=connection.prepareStatement(sql);					
					statement.setString(1, l1.getSelectedValue());					
					statement.executeUpdate();														
					connection.close();
				}catch(SQLException x) {
					System.out.println(x);
				}
				func();				
			}			
		});
	    	    
		lb1.setBounds(35, 13, 80, 30);
		lb2.setBounds(180, 13, 80, 30);
		lb3.setBounds(310, 13, 80, 30);
		s.setBounds(0,40,130,100);
		s2.setBounds(137,40,137,100);
		s3.setBounds(280,40,100,100);
		f.add(b);
		f.add(b2);
		f.add(lb1);
		f.add(lb2);
		f.add(lb3);
		f.add(s);
		f.add(s2);
		f.add(s3);
		f.setLayout(null);
		f.setVisible(true);
		f.setSize(400,400);
		
	}
}
