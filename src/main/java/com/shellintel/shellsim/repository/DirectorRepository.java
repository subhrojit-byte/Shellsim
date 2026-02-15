package com.shellintel.shellsim.repository;

import com.shellintel.shellsim.model.Director;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class DirectorRepository {

    private final JdbcTemplate jdbcTemplate;

    public DirectorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save director and return generated ID
    public Long save(Director director) {

        String sql = """
                INSERT INTO core.director (name, nationality)
                VALUES (?, ?)
                RETURNING id
                """;

        return jdbcTemplate.queryForObject(sql, Long.class,
                director.getName(),
                director.getNationality()
        );
    }

    // Link company and director
    public void linkCompanyDirector(Long companyId, Long directorId) {

        String sql = """
                INSERT INTO core.company_director (company_id, director_id)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(sql, companyId, directorId);
    }

    public List<Director> findAll() {
        return jdbcTemplate.query("SELECT * FROM core.director", new DirectorRowMapper());
    }

    public List<Director> findByCompanyId(Long companyId) {

        String sql = """
                SELECT d.id, d.name, d.nationality
                FROM core.director d
                JOIN core.company_director cd
                  ON d.id = cd.director_id
                WHERE cd.company_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                new Object[]{companyId},
                (rs, rowNum) -> {
                    Director director = new Director();
                    director.setId(rs.getLong("id"));
                    director.setName(rs.getString("name"));
                    director.setNationality(rs.getString("nationality"));
                    return director;
                }
        );
    }

    private static class DirectorRowMapper implements RowMapper<Director> {
        @Override
        public Director mapRow(ResultSet rs, int rowNum) throws SQLException {
            Director d = new Director();
            d.setId(rs.getLong("id"));
            d.setName(rs.getString("name"));
            d.setNationality(rs.getString("nationality"));
            return d;
        }
    }
}
