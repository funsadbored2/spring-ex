package com.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.emaillist.vo.EmaillistVo;

@Repository
public class EmaillistDao {

	public List<EmaillistVo> getList() {

		Connection conn = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<EmaillistVo> list = new ArrayList<EmaillistVo>();

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			System.out.println("접속되었습니다.");

			// 3. SQL문 준비 / 바인딩 / 실행

			String query = "SELECT NO, " + "FIRST_NAME, " + "LAST_NAME, " + "EMAIL " + "FROM EMAILLIST " + "ORDER BY NO DESC";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 4.결과처리

			while (rs.next()) {

				EmaillistVo vo = new EmaillistVo();

				vo.setNo(rs.getInt("NO"));
				vo.setFirstName(rs.getString("FIRST_NAME"));
				vo.setLastName(rs.getString("LAST_NAME"));
				vo.setEmail(rs.getString("EMAIL"));

				list.add(vo);
			}

		} catch (ClassNotFoundException e) {

			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {

			System.out.println("error:" + e);

		} finally {

			// 5. 자원정리

			try {

				if (pstmt != null) {

					pstmt.close();

				}

				if (conn != null) {

					conn.close();

				}

			} catch (SQLException e) {

				System.out.println("error:" + e);

			}

		}
		
		return list;
	}

	public int insert(EmaillistVo emaillistVo) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			System.out.println("접속되었습니다.");
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO EMAILLIST VALUES(SEQ_EMAILLIST.NEXTVAL,?,?,?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, emaillistVo.getLastName());
			pstmt.setString(2, emaillistVo.getFirstName());
			pstmt.setString(3, emaillistVo.getEmail());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 처리");
		} catch (ClassNotFoundException e) {
			System.out.println("error:드라이브 로딩 실패" + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error: " + e);
			}
			// 5. 자원정리

		}

		return count;

	}
}
