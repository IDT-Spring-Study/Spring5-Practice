package com.study.kdy.chapter08.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

public class MemberDao {

	private final JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?",
				(rs, rowNum) -> {
					Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
				}, email);

		return results.isEmpty() ? null : results.get(0);
	}

	public Member insert(Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			// 파라미터로 전달받은 Connection을 이용해서 PreparedStatement 생성
			PreparedStatement pstmt = con.prepareStatement(
					"insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) " +
							"values (?, ?, ?, ?)",
					new String[] { "ID" });
			// 인덱스 파라미터 값 설정
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4,
					Timestamp.valueOf(member.getRegisterDateTime()));
			// 생성한 PreparedStatement 객체 리턴
			return pstmt;
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());

		return member;
	}

	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
				member.getName(), member.getPassword(), member.getEmail());
	}

	public List<Member> selectAll() {
		return jdbcTemplate.query("select * from MEMBER",
				(ResultSet rs, int rowNum) -> {
					Member member = new Member(
							rs.getString("EMAIL"),
							rs.getString("PASSWORD"),
							rs.getString("NAME"),
							rs.getTimestamp("REGDATE").toLocalDateTime());
					member.setId(rs.getLong("ID"));
					return member;
				});
	}

	public void delete(Long id) {
		jdbcTemplate.update("DELETE FROM MEMBER WHERE ID = ?", id);
	}

	public int count() {
		return jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
	}

}
