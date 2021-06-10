/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralph.model;

import java.math.BigDecimal;

/**
 *
 * @author rhughes
 */
public class HighLowPoint {
    private long unixTime;
    private String isoTime;
    private BigDecimal height;
    private String type; // High or Low

    /**
     * @return the unixTime
     */
    public long getUnixTime() {
        return unixTime;
    }

    /**
     * @param unixTime the unixTime to set
     */
    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    /**
     * @return the isoTime
     */
    public String getIsoTime() {
        return isoTime;
    }

    /**
     * @param isoTime the isoTime to set
     */
    public void setIsoTime(String isoTime) {
        this.isoTime = isoTime;
    }

    /**
     * @return the height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        if (type.equalsIgnoreCase("low") || type.equalsIgnoreCase("high")) {
            this.type = type;
        } else {
            System.out.println("Garbage in, garbage out");
            System.exit(-1);
        }
    }
}
