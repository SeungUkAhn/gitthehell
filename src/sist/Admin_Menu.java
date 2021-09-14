package sist;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin_Menu extends JFrame {

	//전역 변수 설정
	Connection con = null; 
	PreparedStatement pstmt = null;
	ResultSet rs = null; //DB 연결 객체
	String sql = null;
	
	
	JPanel container = null;				//버튼 컨테이너
	DefaultTableModel model = null;
	JTable table = null;					//메뉴 테이블
	JButton btn1, btn2, btn3, btn4; //전체내역, 추가, 수정, 삭제 버튼
	
	
	public Admin_Menu() {
			
		setTitle("메뉴 관리");
		
		//컨테이너 생성
		container = new JPanel();
				
		String[] header = {"메뉴ID", "메뉴명", "가격", "설명"};
		
		model = new DefaultTableModel(header, 0);
		
	    table = new JTable(model);
		
		JScrollPane jsp = new JScrollPane(
				table, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
		);
		
		//컴포넌트 생성 - 버튼
		btn1 = new JButton("전체내역");
		btn2 = new JButton("추가");
		btn3 = new JButton("삭제");
		btn4 = new JButton("수정");
		
		
		//컨테이너에 버튼 추가
		container.add(btn1); container.add(btn2);
		container.add(btn3); container.add(btn4);
		
		//프레임에 테이블, 컨테이너 추가
		add(jsp, BorderLayout.NORTH);
		add(container, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 500);
		setVisible(true);
		
		//버튼 이벤트 처리
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				connect();
				model.setRowCount(0);
				select();
			}
		});
		
		
		
		
	}
	
	void connect() {
		 
		 String driver = "oracle.jdbc.driver.OracleDriver";
		 String url = "jdbc:oracle:thin:@localhost:1521:xe";
		 String user = "web";
		 String password = "1234";
		
		 try {
			
			//드라이버 로드
			Class.forName(driver);
			
			//DB 접속
			con = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	 } //connect() 끝
	
	
	void select() { //버튼1 - 전체메뉴조회
		  
		  try {
			
			  //쿼리 작성
			  sql = "select * from A_MENU order by MENU_ID";
			  
			  //쿼리 전송
			  pstmt = con.prepareStatement(sql);
			  
			  //쿼리 실행
			  rs = pstmt.executeQuery();
			  
			  while(rs.next()) {
				 
				  Object[] data = {rs.getInt(1), rs.getString(2), 
						  		   rs.getInt(3), rs.getString(4)};
				 
				  //저장된 한 개의 레코드를 model에 추가
				  model.addRow(data);
			 }
			  
			  //연결 종료
			  rs.close(); pstmt.close(); con.close();
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	  } //select() ends
	
	public static void main(String[] args) {

		new Admin_Menu();
	}

}
