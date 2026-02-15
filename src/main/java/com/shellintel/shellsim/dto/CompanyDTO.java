package com.shellintel.shellsim.dto;


public class CompanyDTO {

    private Long id;
    private String name;
    private String country;
    private int riskScore;

    public CompanyDTO() {}

    public CompanyDTO(Long id, String name, String country, int riskScore) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.riskScore = riskScore;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getRiskScore() { return riskScore; }
    public void setRiskScore(int riskScore) { this.riskScore = riskScore; }
}

