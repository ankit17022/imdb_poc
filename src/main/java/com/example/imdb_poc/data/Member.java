package com.example.imdb_poc.data;

import java.util.Objects;

public class Member {
    private String member_name;
    private String member_id;
    private String role;
    private String category;
    private String job;

    public Member() {
    }

    public Member(String member_name, String member_id, String role, String category, String job) {
        this.member_name = member_name;
        this.member_id = member_id;
        this.role = role;
        this.category = category;
        this.job = job;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Member{" +
                "member_name='" + member_name + '\'' +
                ", member_id='" + member_id + '\'' +
                ", role='" + role + '\'' +
                ", category='" + category + '\'' +
                ", job='" + job + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(member_name, member.member_name) && Objects.equals(member_id, member.member_id) && Objects.equals(role, member.role) && Objects.equals(category, member.category) && Objects.equals(job, member.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member_name, member_id, role, category, job);
    }
}
