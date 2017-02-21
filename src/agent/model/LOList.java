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
public class LOList {
    
    private ArrayList<LOM> LOList;

    public LOList() {
        LOList = new ArrayList<>();
    }

    public ArrayList<LOM> getLOList() {
        return LOList;
    }

    public void setLOList(ArrayList<LOM> LOList) {
        this.LOList = LOList;
    }
    
    public void addLO(LOM lom){
        this.LOList.add(lom);
    }

    @Override
    public String toString() {
        return "LOList{" + "LOList=" + LOList.toString() + '}';
    }
    
}
