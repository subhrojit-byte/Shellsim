package com.shellintel.shellsim.model;

public class CompanyDirector {
    private Long companyId;
    private Long directorId;
    private String role;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CompanyDirector{" +
                "companyId=" + companyId +
                ", directorId=" + directorId +
                ", role='" + role + '\'' +
                '}';
    }
}
