/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package esi.atl.g41163.bmr.model;

/**
 * Enum representing the different Lifestyles for the BMR Application
 * @author Adrian Torres
 * @version 1.1
 * @since 2017-02-28
 */
public enum Lifestyles
{
    SEDENTARY("Sedentary", 1.2),
    L_ACTIVE("Little active", 1.375),
    ACTIVE("Active", 1.55),
    V_ACTIVE("Very active", 1.725),
    E_ACTIVE("Extremely active", 1.9);
    
    private final String value;
    private final double multiplier;
    
    private Lifestyles(String value, double multiplier)
    {
        this.value = value;
        this.multiplier = multiplier;
    }
    
    /**
     * Gets the human-readable representation of this item
     * @return A string representing the item in human-readable form
     */
    public String getValue()
    {
        return this.value;
    }
    

    @Override
    public String toString()
    {
        return this.getValue();
    }
    
    /**
     * Gets the double corresponding to this item
     * @return A double representing the multiplier associated with the lifestyle
     */
    public double getMult()
    {
        return this.multiplier;
    }
}
