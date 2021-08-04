package com.example.imdb_poc.model;

import com.example.imdb_poc.converter.ImdbPayloadJpaConverterJson;
import com.example.imdb_poc.data.ImdbPayload;

import javax.persistence.*;

@Entity
@Table(name = "imdb_mapping")
public class ImdbMapping {

    @Id
    private int id;
    private String imdb_title_id;
    private int cms_content_id;
    private int processed;
    private double score;

    @Column(name = "payload")
    @Convert(converter = ImdbPayloadJpaConverterJson.class)
    private ImdbPayload payload;

    public ImdbMapping() {
    }

    public ImdbMapping(int id, String imdb_title_id, int cms_content_id, int processed, double score, ImdbPayload payload) {
        this.id = id;
        this.imdb_title_id = imdb_title_id;
        this.cms_content_id = cms_content_id;
        this.processed = processed;
        this.score = score;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_title_id() {
        return imdb_title_id;
    }

    public void setImdb_title_id(String imdb_title_id) {
        this.imdb_title_id = imdb_title_id;
    }

    public int getCms_content_id() {
        return cms_content_id;
    }

    public void setCms_content_id(int cms_content_id) {
        this.cms_content_id = cms_content_id;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ImdbPayload getPayload() {
        return payload;
    }

    public void setPayload(ImdbPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "ImdbMapping{" +
                "id=" + id +
                ", imdb_title_id='" + imdb_title_id + '\'' +
                ", cms_content_id='" + cms_content_id + '\'' +
                ", processed='" + processed + '\'' +
                ", score=" + score +
                ", payload=" + payload +
                '}';
    }
}
