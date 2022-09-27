package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BbsDAO() {//DB�젒�냽遺�
		try {
			String dbURL="jdbc:mysql://localhost/kimdhh.cafe24";//移댄럹24�뿉�꽌 留뚮뱾�뿀�뜕 MySQL ID(15媛�)
			String dbID="kimdhh";//洹몃븣 �꽕�젙�뻽�뜕 ID
			String dbPassword = " dohui2628@";//洹몃븣�꽕�젙�뻽�뜕PW
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {//�쁽�옱�떆媛�
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
	}
	
	public int getNext() {//�쁽�옱�떆媛�
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
	}
	
	public int write(String bbsTitle, String userID,String bbsContent) {//�떎�젣濡� �뜲�씠�꽣踰좎씠�뒪�뿉 �뜲�씠�꽣 �궫�엯�븷 �닔 �엳�뒗 �븿�닔
		String SQL = "INSERT INTO BBS VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6,1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
	}
	
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID<? AND bbsAvailable =1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs>list= new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()-(pageNumber -1)*10);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
	}
	
	public boolean nextPage(int pageNumber) {//�럹�씠吏뺤쿂由�
		String SQL = "SELECT * FROM BBS WHERE bbsID<? AND bbsAvailable =1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext()-(pageNumber -1)*10);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
	}
		public Bbs getBbs(int bbsID) {
			String SQL = "SELECT * FROM BBS WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
		}
		
		public int update(int bbsID, String bbsTitle, String bbsContent) {
			String SQL = "UPDATE BBS SET bbsTitle=?, bbsContent =? WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, bbsTitle);
				pstmt.setString(2, bbsContent);
				pstmt.setInt(3, bbsID);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
		}
		
		public int delete(int bbsID) {
			String SQL = "UPDATE BBS SET bbsAvailabe=0 WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;//�뜲�씠�꽣踰좎씠�뒪�삤瑜�
		}
}

