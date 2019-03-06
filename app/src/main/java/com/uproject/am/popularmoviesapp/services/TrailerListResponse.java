package com.uproject.am.popularmoviesapp.services;

import com.uproject.am.popularmoviesapp.entities.Trailer;

import java.util.List;



public class TrailerListResponse{
    private String id;

    private List<Trailer> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
