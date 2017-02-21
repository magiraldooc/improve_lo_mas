/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent.model;

import java.util.ArrayList;

/**
 *
 * @author magir
 */
public class SearchLO {
    
    private String searchString;
    
    private Integer searchLOId;
    
    private LOList loList;

    public SearchLO() {
        this.loList = new LOList();
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public LOList getLoList() {
        return loList;
    }

    public void setLoList(LOList loList) {
        this.loList = loList;
    }

    public Integer getSearchLOId() {
        return searchLOId;
    }

    public void setSearchLOId(Integer searchLOId) {
        this.searchLOId = searchLOId;
    }
}
