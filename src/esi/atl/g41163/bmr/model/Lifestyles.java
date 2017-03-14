/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package esi.atl.g41163.bmr.model;

/**
 * Enum representing the different Lifestyles for the BMR Application
 * @author Adrian Torres
 * @version 1.0
 * @since 2017-02-28
 */
public enum Lifestyles
{
    SEDENTARY("Sedentary"),
    L_ACTIVE("Little active"),
    ACTIVE("Active"),
    V_ACTIVE("Very active"),
    E_ACTIVE("Extremely active");
    
    private final String value;
    
    private Lifestyles(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return this.value;
    }
}
