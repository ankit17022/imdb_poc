package com.example.imdb_poc.data;

import java.util.*;


public class ImdbPayload{
    private double ratings;
    private long number_of_votes;
    private Set<Member> cast_members;
    private Set<Member> crew_members;

    public ImdbPayload() {
        cast_members = new HashSet<>();
        crew_members = new HashSet<>();
    }

    public ImdbPayload(double ratings, long number_of_votes, Set<Member> cast_members, Set<Member> crew_members) {
        this.ratings = ratings;
        this.number_of_votes = number_of_votes;
        this.cast_members = cast_members;
        this.crew_members = crew_members;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public long getNumber_of_votes() {
        return number_of_votes;
    }

    public void setNumber_of_votes(long number_of_votes) {
        this.number_of_votes = number_of_votes;
    }

    public Set<Member> getCast_members() {
        return cast_members;
    }

    public void setCast_members(Set<Member> cast_members) {
        this.cast_members = cast_members;
    }

    public Set<Member> getCrew_members() {
        return crew_members;
    }

    public void setCrew_members(Set<Member> crew_members) {
        this.crew_members = crew_members;
    }

    public void addCastMember(Member member){
        this.cast_members.add(member);
    }

    public void addCrewMember(Member member){
        this.crew_members.add(member);
    }

    @Override
    public String toString() {
        return "ImdbPayload{" +
                "ratings=" + ratings +
                ", number_of_votes=" + number_of_votes +
                ", cast_members=" + cast_members +
                ", crew_members=" + crew_members +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImdbPayload that = (ImdbPayload) o;
        return Double.compare(that.ratings, ratings) == 0 && number_of_votes == that.number_of_votes && Objects.equals(cast_members, that.cast_members) && Objects.equals(crew_members, that.crew_members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratings, number_of_votes, cast_members, crew_members);
    }
}
