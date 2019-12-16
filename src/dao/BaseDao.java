package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * 数据库连接与关闭工具类。
 * 
 * @author 北大青鸟
 */
public class BaseDao {
	// 定义一个Connection
	protected Connection conn;

	/**
	 * 获取数据库连接对象。
	 */
	public Connection getConnection() {
		// 初始化上下文
		Context ctx = null;
		try {
			//获取数据源
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/smbms");
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接。
	 * 
	 * @param conn
	 *            数据库连接
	 * @param stmt
	 *            Statement对象
	 * @param rs
	 *            结果集
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		// 若结果集对象不为空，则关闭
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 若Statement对象不为空，则关闭
		try {
			if (stmt != null && !stmt.isClosed())
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 若数据库连接对象不为空，则关闭
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 增删改方法
	public int executeUpdate(String sql, Object[] obj) {
		// 定义一个int类型的变量记录影响行数
		int result = 0;
		// 获取数据库连接
		conn = this.getConnection();
		System.out.println(conn == null);
		PreparedStatement pstmt = null;
		try {
			// 开始执行sql语句
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				// 插入占位符对应的数据
				pstmt.setObject(i + 1, obj[i]);
			}
			// 接收返回的影响行数结果
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接数据库
			closeAll(conn, pstmt, null);
		}
		// 返回影响结果
		return result;
	}

	// 查询方法
	public ResultSet executeQuery(String sql, Object[] obj) {
		// 获取数据库连接
		conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 开始执行sql语句
			pstmt = conn.prepareStatement(sql);
			// 向对应的占位符插入对应的数据
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pstmt.setObject(i + 1, obj[i]);
				}
			}
			// 接收查询返回的结果
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
