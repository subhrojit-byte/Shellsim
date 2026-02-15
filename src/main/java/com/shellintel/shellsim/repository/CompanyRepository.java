package com.shellintel.shellsim.repository;

import com.shellintel.shellsim.model.Company;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save and return generated ID
    public Long save(Company company) {

        String sql = """
                INSERT INTO core.company
                ( company_name, country, incorporation_date, employee_count,
                 declared_revenue, industry, is_shell_truth, risk_score)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING company_id
                """;

        return jdbcTemplate.queryForObject(sql, Long.class,
                company.getName(),
                company.getCountry(),
                company.getIncorporationDate(),
                company.getEmployeeCount(),
                company.getDeclaredRevenue(),
                company.getIndustry(),
                company.getIsShellTruth(),
                company.getRiskScore()
          );
    }
    public void updateRiskScore(Long companyId, int score) {
        jdbcTemplate.update("""
            UPDATE core.company
            SET risk_score = ?
            WHERE company_id = ?
        """, score, companyId);
    }

    public List<Company> findAll() {
        return jdbcTemplate.query("SELECT * FROM core.company", new CompanyRowMapper());
    }

    public Integer countAll() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM core.company",
                Integer.class
        );
    }

    public Integer countShell() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM core.company WHERE is_shell_truth = true",
                Integer.class
        );
    }

    public Integer countLegit() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM core.company WHERE is_shell_truth = false",
                Integer.class
        );
    }

    public List<Company> findTopHighRisk() {
        return jdbcTemplate.query(
                "SELECT * FROM core.company ORDER BY risk_score DESC LIMIT 10",
                new CompanyRowMapper()
        );
    }

    private static class CompanyRowMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

            Company company = new Company();

            company.setId(rs.getLong("company_id"));
            company.setName(rs.getString("company_name"));
            company.setCountry(rs.getString("country"));
            company.setIncorporationDate(
                    rs.getDate("incorporation_date") != null
                            ? rs.getDate("incorporation_date").toLocalDate()
                            : null
            );
            company.setEmployeeCount(rs.getInt("employee_count"));
            company.setDeclaredRevenue(rs.getBigDecimal("declared_revenue"));
            company.setIndustry(rs.getString("industry"));
            company.setIsShellTruth(rs.getBoolean("is_shell_truth"));
            company.setRiskScore(rs.getInt("risk_score"));
            company.setCreatedAt(
                    rs.getTimestamp("created_at") != null
                            ? rs.getTimestamp("created_at").toLocalDateTime()
                            : null
            );

            return company;
        }
    }

}

