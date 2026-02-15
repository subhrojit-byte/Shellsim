package com.shellintel.shellsim.repository;


import com.shellintel.shellsim.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert single transaction
    public void save(Transaction tx) {
        String sql = """
                INSERT INTO core.transaction 
                (company_id, amount, transaction_type, transaction_date)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                tx.getCompanyId(),
                tx.getAmount(),
                tx.getTransactionType(),
                tx.getTransactionDate()
        );
    }

    // Batch insert (recommended for simulation)
    public void saveAll(List<Transaction> transactions) {

        String sql = """
                INSERT INTO core.transaction 
                (company_id, amount, transaction_type, transaction_date)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.batchUpdate(sql, transactions, transactions.size(),
                (ps, tx) -> {
                    ps.setLong(1, tx.getCompanyId());
                    ps.setBigDecimal(2, tx.getAmount());
                    ps.setString(3, tx.getTransactionType());
                    ps.setObject(4, tx.getTransactionDate());
                });
    }

    // Fetch by company
    public List<Transaction> findByCompanyId(Long companyId) {
        String sql = "SELECT * FROM core.transaction WHERE company_id = ?";
        return jdbcTemplate.query(sql, new TransactionRowMapper(), companyId);
    }

    public List<Transaction> findAll() {return jdbcTemplate.query("SELECT * FROM core.transaction", new TransactionRowMapper());
    }



    // RowMapper
    private static class TransactionRowMapper implements RowMapper<Transaction> {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction tx = new Transaction();
            tx.setId(rs.getLong("id"));
            tx.setCompanyId(rs.getLong("company_id"));
            tx.setAmount(rs.getBigDecimal("amount"));
            tx.setTransactionType(rs.getString("transaction_type"));
            tx.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
            return tx;
        }
    }
}

