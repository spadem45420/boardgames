package model.dao;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.MemberBean;
import model.MemberDAO;

@SuppressWarnings("unused")
public class MemberDAOJdbc implements MemberDAO {
	private static final String URL = "jdbc:sqlserver://localhost:1433;database=boardgames";
	private static final String USERNAME = "sa";
	private static final String PASSWORD = "123456";
	
	
	private static final String SELECT_BY_ID = "select * from Member where username=?";
	@Override
	public MemberBean select(String username) {
		MemberBean result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(SELECT_BY_ID);
			stmt.setString(1,username);
			rset = stmt.executeQuery();
			
			if(rset.next()){
				result = new MemberBean();
				result.setMemberId(rset.getInt("memberId"));
				result.setUsername(rset.getString("username"));
				result.setPswd(rset.getBytes("pswd"));
				result.setEmail(rset.getString("email"));
				result.setLastname(rset.getString("lastname"));
				result.setFirstname(rset.getString("firstname"));
				result.setGender(rset.getString("gender"));
				result.setNickname(rset.getString("nickname"));
				result.setBirthday(rset.getDate("birthday"));
				result.setIdCard(rset.getString("idCard"));
				result.setJoinDate(rset.getDate("joinDate"));
				result.setPhone(rset.getString("phone"));
				result.setMemberAddress(rset.getString("memberAddress"));
				result.setImgFileName(rset.getString("imgFileName"));
				result.setMemberImage(rset.getBytes("memberImage"));
				result.setGroupBan(rset.getBoolean("isGroupBan"));
				result.setCommentBan(rset.getBoolean("isCommentBan"));
				result.setNotBanTime(rset.getDate("notBanTime"));
				result.setBanTime(rset.getDate("banTime"));
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
	
	private static final String SELECT_ALL = "select * from Member";
	@Override
	public List<MemberBean> select() {
		List<MemberBean> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(SELECT_ALL);
			rset = stmt.executeQuery();
			
			result = new ArrayList<MemberBean>();
			while(rset.next()){
				MemberBean bean = new MemberBean();
				bean.setMemberId(rset.getInt("memberId"));
				bean.setUsername(rset.getString("username"));
				bean.setPswd(rset.getBytes("pswd"));
				bean.setEmail(rset.getString("email"));
				bean.setLastname(rset.getString("lastname"));
				bean.setFirstname(rset.getString("firstname"));
				bean.setGender(rset.getString("gender"));
				bean.setNickname(rset.getString("nickname"));
				bean.setBirthday(rset.getDate("birthday"));
				bean.setIdCard(rset.getString("idCard"));
				bean.setJoinDate(rset.getDate("joinDate"));
				bean.setPhone(rset.getString("phone"));
				bean.setMemberAddress(rset.getString("memberAddress"));
				bean.setImgFileName(rset.getString("imgFileName"));
				bean.setMemberImage(rset.getBytes("memberImage"));
				bean.setGroupBan(rset.getBoolean("isGroupBan"));
				bean.setCommentBan(rset.getBoolean("isCommentBan"));
				bean.setNotBanTime(rset.getDate("notBanTime"));
				bean.setBanTime(rset.getDate("banTime"));
				
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
			"insert into Member (username,pswd,email,lastname,firstname,gender,"
			+ "nickname,birthday,idCard,joinDate,phone,memberAddress,imgFileName,"
			+ "memberImage,isGroupBan,isCommentBan,notBanTime,banTime) values "
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	@Override
	public MemberBean insert(MemberBean bean,InputStream is,long size) {
		Connection conn = null;
		PreparedStatement stmt = null;
		MemberBean result = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(INSERT);

			result = new MemberBean();
			stmt.setString(1,bean.getUsername());
			stmt.setBytes(2,bean.getPswd());
			stmt.setString(3,bean.getEmail());
			stmt.setString(4,bean.getLastname());
			stmt.setString(5,bean.getFirstname());
			stmt.setString(6,bean.getGender());
			stmt.setString(7,bean.getNickname());
			
			java.util.Date birthday = bean.getBirthday();
			if(birthday!=null) {
				long temp = birthday.getTime();
				stmt.setDate(8, new java.sql.Date(temp));
			} else {
				stmt.setDate(8, null);
			}
			
			stmt.setString(9,bean.getIdCard());
			
			java.util.Date joinDate = bean.getJoinDate();
			if(joinDate!=null) {
				long temp = joinDate.getTime();
				stmt.setDate(10, new java.sql.Date(temp));
			} else {
				stmt.setDate(10, null);
			}
			stmt.setString(11,bean.getPhone());
			stmt.setString(12,bean.getMemberAddress());
			stmt.setString(13,bean.getImgFileName());
			stmt.setBinaryStream(14, is, size);
			stmt.setBoolean(15,false);
			stmt.setBoolean(16,false);
			stmt.setDate(17,null);
			stmt.setDate(18,null);
			
			int i = stmt.executeUpdate();
			if(i==1){
				System.out.println("Insert Successful!");
				return bean;
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
		
		return result;
	}
	
	
	private static final String UPDATE = 
			"update Member set pswd=?,email=?,lastname=?,firstname=?,gender=?,"
			+ "nickname=?,birthday=?,idCard=?,joinDate=?,phone=?,memberAddress=?,imgFileName=?,memberImage=?,"
			+ "isGroupBan=?,isCommentBan=?,notBanTime=?,banTime=? where username=?";
	@Override
	public MemberBean update(MemberBean bean,InputStream is,long size) {
		Connection conn = null;
		PreparedStatement stmt = null;
		MemberBean result = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(UPDATE);
			
			stmt.setBytes(1,bean.getPswd());
			stmt.setString(2,bean.getEmail());
			stmt.setString(3,bean.getLastname());
			stmt.setString(4,bean.getFirstname());
			stmt.setString(5,bean.getGender());
			stmt.setString(6,bean.getNickname());
			
			java.util.Date birthday = bean.getBirthday();
			if(birthday!=null) {
				long temp = birthday.getTime();
				stmt.setDate(7, new java.sql.Date(temp));
			} else {
				stmt.setDate(7, null);
			}
			
			stmt.setString(8,bean.getIdCard());
			
			java.util.Date joinDate = bean.getJoinDate();
			if(joinDate!=null) {
				long temp = joinDate.getTime();
				stmt.setDate(9, new java.sql.Date(temp));
			} else {
				stmt.setDate(9, null);
			}
			stmt.setString(10,bean.getPhone());
			stmt.setString(11,bean.getMemberAddress());
			stmt.setString(12,bean.getImgFileName());
			stmt.setBinaryStream(13, is, size);
			stmt.setBoolean(14,false);
			stmt.setBoolean(15,false);
			stmt.setDate(16,null);
			stmt.setDate(17,null);
			stmt.setString(18, bean.getUsername());
			
			int i = stmt.executeUpdate();
			if(i==1){
				System.out.println("Update Successful!");
				return bean;
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
		
		return result;
	}

	private static final String DELETE = "delete from Member where username=?";
	@Override
	public boolean delete(String username) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			stmt = conn.prepareStatement(DELETE);
			stmt.setString(1, username);
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
		MemberDAOJdbc dao = new MemberDAOJdbc();
		
		//select all
		List<MemberBean> beans = dao.select();
		System.out.println(beans);
		
		
		
		//select by id
//		System.out.println(dao.select("Mary123"));
		
		
		
		//insert
		MemberBean bean = new MemberBean();
		File f = null;
		FileInputStream fis = null;
		long length = 0;
			
			try {
				f = new File("img/java_duke.jpg");
				fis = new FileInputStream(f);
				length = f.length();		
			bean.setUsername("Bob4");
			bean.setPswd("Bob".getBytes());
			bean.setEmail("Bob@gmail.com");
			bean.setLastname("鮑勃");
			bean.setFirstname("張");
			bean.setGender("man");
			bean.setNickname("Bob");
			bean.setBirthday(new java.util.Date());
			bean.setIdCard("B123456987");
			bean.setJoinDate(new java.util.Date());
			bean.setPhone("0988456789");
			bean.setMemberAddress("新北市新莊區思源路370巷");
			bean.setImgFileName("java_duke.jpg");
			dao.insert(bean,fis,length);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		
		
		//update
//		MemberBean bean = new MemberBean();
//		File f = null;
//		FileInputStream fis = null;
//		try {
//			f = new File("img/java_duke.jpg");
//			fis = new FileInputStream(f);
//			long length = f.length();
//			
//			bean.setUsername("Bob1123");
//			bean.setPswd("Bob".getBytes());
//			bean.setEmail("Bob@gmail.com");
//			bean.setLastname("鮑勃");
//			bean.setFirstname("林");
//			bean.setGender("man");
//			bean.setNickname("Bob");
//			bean.setBirthday(new java.util.Date());
//			bean.setIdCard("B123456987");
//			bean.setJoinDate(new java.util.Date());
//			bean.setPhone("0988456789");
//			bean.setMemberAddress("新北市新莊區思源路370巷");
//			bean.setImgFileName("java_duke2.jpg");
////			bean.setGroupBan(false);
////			bean.setCommentBan(false);
////			bean.setNotBanTime(null);
////			bean.setBanTime(null);
//			
////			System.out.println(dao.update(bean,fis,length));
//			
//			bean = dao.select("Bob1123");
//			System.out.println(bean);
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//delete
//		dao.delete("Bob123");
		
		
		
		//取出圖片
//		try {
//			File f = new File("img/java_duke_out.jpg");
//			Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
//			PreparedStatement stmt;
//			stmt = conn.prepareStatement("select * from Member where username= ?");
//			stmt.setString(1, "Bob123");
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				BufferedOutputStream bos = new BufferedOutputStream(
//						new FileOutputStream(f));
//				Blob b = rs.getBlob("memberImage");
//				byte[] data = b.getBytes(1, (int)b.length());
//				bos.write(data, 0, (int)b.length());
//				bos.close();
//				System.out.println("File output is successful!");
//			}
//			else{
//				System.out.println("Error username!");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
}
