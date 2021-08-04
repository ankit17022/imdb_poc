package com.example.imdb_poc.data;

import java.util.ArrayList;
import java.util.List;

public class ImdbPayload{
    private double ratings;
    private long number_of_votes;
    private List<Member> cast_members = new ArrayList<>();
    private List<Member> crew_members = new ArrayList<>();

    public ImdbPayload() {
    }

    public ImdbPayload(double ratings, long number_of_votes, List<Member> cast_members, List<Member> crew_members) {
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

    public List<Member> getCast_members() {
        return cast_members;
    }

    public void setCast_members(List<Member> cast_members) {
        this.cast_members = cast_members;
    }

    public List<Member> getCrew_members() {
        return crew_members;
    }

    public void setCrew_members(List<Member> crew_members) {
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
}
