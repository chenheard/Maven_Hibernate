package com.yiibai.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SALARY_GRADE")
public class SalaryGrade {
    private Integer grade;
    private Float lowSalary;
    private Float highSalary;

    public SalaryGrade() {
    }

    public SalaryGrade(Integer grade, Float lowSalary, Float highSalary) {
        this.grade = grade;
        this.lowSalary = lowSalary;
        this.highSalary = highSalary;
    }

    @Id
    @Column(name = "GRADE")
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Column(name = "LOW_SALARY", nullable = false)
    public Float getLowSalary() {
        return lowSalary;
    }

    public void setLowSalary(Float lowSalary) {
        this.lowSalary = lowSalary;
    }

    @Column(name = "HIGH_SALARY", nullable = false)
    public Float getHighSalary() {
        return highSalary;
    }

    public void setHighSalary(Float highSalary) {
        this.highSalary = highSalary;
    }
}
