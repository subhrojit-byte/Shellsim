package com.shellintel.shellsim.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Company {

    private Long id;
    private String name;
    private String country;
    private LocalDate incorporationDate;
    private Integer employeeCount;
    private BigDecimal declaredRevenue;
    private String industry;
    private Boolean isShellTruth;
    private Integer riskScore;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(LocalDate incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getDeclaredRevenue() {
        return declaredRevenue;
    }

    public void setDeclaredRevenue(BigDecimal declaredRevenue) {
        this.declaredRevenue = declaredRevenue;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Boolean getIsShellTruth() {
        return isShellTruth;
    }

    public void setIsShellTruth(Boolean shellTruth) {
        isShellTruth = shellTruth;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", incorporationDate=" + incorporationDate +
                ", employeeCount=" + employeeCount +
                ", declaredRevenue=" + declaredRevenue +
                ", industry='" + industry + '\'' +
                ", isShellTruth=" + isShellTruth +
                ", riskScore=" + riskScore +
                ", createdAt=" + createdAt +
                '}';
    }
}

