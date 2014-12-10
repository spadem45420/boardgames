package model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MemberBean;
import model.StoreMemberBean;
import model.StoreMemberDAO;

public class StoreMemberJdbc implements StoreMemberDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=boardgames";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "123456";
	
	
	private static final String SELECT_BY_ID = "select * from StoreMember where storeUsername=?";
	@Override
	public StoreMemberBean select(String storeUsername) {
		StoreMemberBean result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setString(1,storeUsername);
			rset = stmt.executeQuery();
			
			if(rset.next()){
				result = new StoreMemberBean();
				result.setStoreMemberId(rset.getInt("storeMemberId"));
				result.setStoreUsername(rset.getString("storeUsername"));
				result.setStoreJoinDate(rset.getDate("storeJoinDate"));
				result.setStorePhone(rset.getString("storePhone"));
				result.setImgFileName(rset.getString("imgFileName"));
				result.setStoreEmail(rset.getString("storeEmail"));
				result.setStoreWebsite(rset.getString("storeWebsite"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	
	private static final String SELECT_ALL= "select * from StoreMember";
	@Override
	public List<StoreMemberBean> select() {
		List<StoreMemberBean> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(SELECT_ALL);
			rset = stmt.executeQuery();
			
			result = new ArrayList<StoreMemberBean>();
			while(rset.next()){
				StoreMemberBean bean = new StoreMemberBean();
				bean.setStoreMemberId(rset.getInt("storeMemberId"));
				bean.setStoreUsername(rset.getString("storeUsername"));
				bean.setStoreJoinDate(rset.getDate("storeJoinDate"));
				bean.setStorePhone(rset.getString("storePhone"));
				bean.setImgFileName(rset.getString("imgFileName"));
				bean.setStoreEmail(rset.getString("storeEmail"));
				bean.setStoreWebsite(rset.getString("storeWebsite"));
				
				result.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	private static final String INSERT = 
			"insert into StoreMember (storeUsername,storePswd,storeJoinDate,storePhone,imgFileName,storeImage,"
			+ "storeEmail,storeWebsite) values (?,?,?,?,?,?,?,?)";
	@Override
	public StoreMemberBean insert(StoreMemberBean bean, InputStream is,
			long size) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StoreMemberBean result = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(INSERT);

			result = new StoreMemberBean();
			stmt.setString(1,bean.getStoreUsername());
			stmt.setBytes(2,bean.getStorePswd());
			java.util.Date storeJoinDate = bean.getStoreJoinDate();
			if(storeJoinDate!=null) {
				long temp = storeJoinDate.getTime();
				stmt.setDate(3, new java.sql.Date(temp));
			} else {
				stmt.setDate(3, null);
			}
			stmt.setString(4,bean.getStorePhone());
			stmt.setString(5,bean.getImgFileName());
			stmt.setBinaryStream(6,is,size);
			stmt.setString(7,bean.getStoreEmail());
			stmt.setString(8,bean.getStoreWebsite());
			
			int i = stmt.executeUpdate();
			if(i==1){
				System.out.println("Insert Successful!");
				return bean;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	private static final String UPDATE = 
			"update StoreMember set storePswd=?,storeJoinDate=?,storePhone=?,imgFileName=?,storeImage=?,"
			+ "storeEmail=?,storeWebsite=? where storeUsername=?";
	@Override
	public StoreMemberBean update(StoreMemberBean bean, InputStream is,
			long size) {
		Connection conn = null;
		PreparedStatement stmt = null;
		StoreMemberBean result = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(UPDATE);

			stmt.setBytes(1,bean.getStorePswd());
			java.util.Date storeJoinDate = bean.getStoreJoinDate();
			if(storeJoinDate!=null) {
				long temp = storeJoinDate.getTime();
				stmt.setDate(2, new java.sql.Date(temp));
			} else {
				stmt.setDate(2, null);
			}
			stmt.setString(3,bean.getStorePhone());
			stmt.setString(4,bean.getImgFileName());
			stmt.setBinaryStream(5,is,size);
			stmt.setString(6,bean.getStoreEmail());
			stmt.setString(7,bean.getStoreWebsite());
			stmt.setString(8,bean.getStoreUsername());
			
			int i = stmt.executeUpdate();
			if(i==1){
				System.out.println("Update Successful!");
				return bean;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	
	private static final String DELETE = "delete from StoreMember where storeUsername=?";
	@Override
	public boolean delete(String storeUsername) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(DELETE);
			stmt.setString(1, storeUsername);
			int i = stmt.executeUpdate();
			
			if(i==1){
				System.out.println("Delete Successful!");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		StoreMemberJdbc dao = new StoreMemberJdbc();
		
		//select by id
//		System.out.println(dao.select("Allcognus"));
		
		//select
//		List<StoreMemberBean> list = dao.select();
//		System.out.println(list);
		
		//insert
//		StoreMemberBean bean = new StoreMemberBean();
//		File f = null;
//		FileInputStream fis = null;
//			try {
//				f = new File("img/java_duke.jpg");
//				fis = new FileInputStream(f);
//				long length = f.length();		
//			bean.setStoreUsername("Bob3");
//			bean.setStoreJoinDate(new java.util.Date());
//			bean.setStorePhone("0913456789");
//			bean.setStoreEmail("Bob@gmail.com");
//			bean.setImgFileName("Bob.jpg");
//			bean.setStoreWebsite(null);
//			dao.insert(bean,fis,length);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		//update
//		StoreMemberBean bean = new StoreMemberBean();
//		File f = null;
//		FileInputStream fis = null;
//			try {
//				f = new File("img/java_duke.jpg");
//				fis = new FileInputStream(f);
//				long length = f.length();		
//			bean.setStoreUsername("Bob3");
//			bean.setStoreJoinDate(new java.util.Date());
//			bean.setStorePhone("0913456780");
//			bean.setStoreEmail("Bob@gmail.com");
//			bean.setImgFileName("Bob.jpg");
//			bean.setStoreWebsite(null);
//			System.out.println(dao.update(bean,fis,length));
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		//delete
//		System.out.println(dao.delete("Bob3"));
	}

}
