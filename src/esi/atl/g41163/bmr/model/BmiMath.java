/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package esi.atl.g41163.bmr.model;

/**
 * Provides public methods for calculating BMI and Calories Expenses
 * @author Adrian Torres
 * @version 1.0
 * @since 2017-03-14
 */
public class BmiMath
{
    /**
     * Calculates the BMI of a person based on size, weight, age and gender
     * @param s Size of the person (cm)
     * @param w Weight of the person (kg)
     * @param a Age of the person (years)
     * @param g Gender (true = male, false = female)
     * @return A double representing the BMI of this person
     */
    public static double getBmi(double s, double w, double a, boolean g)
    {
        double bmi;
        
        if (g)
        {
            // Male
            bmi = 13.7 * w + 5 * s - 6.8 * a + 66;
        }
        else
        {
            // Female
            bmi = 9.6 * w + 1.8 * s - 4.7 * a + 655;
        }
        
        return bmi;
    }
    
    /**
     * Calculates the Calories Expenses of a person based on their BMI and their
     * lifestyle
     * @param bmi BMI of the person
     * @param m Lifestyle multiplier of the person
     * @return A double representing the calorie expenses of a person
     */
    public static double getCal(double bmi, double m)
    {
        return bmi * m;
    }
}
