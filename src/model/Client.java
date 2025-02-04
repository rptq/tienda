/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author RobertTerolLacasta
 */
public class Client extends Person implements Payeable {
    
    private int memberid = MEMBER_ID;
    private double balance = BALANCE;
    
    public final static int MEMBER_ID = 456;
    public final static double BALANCE = 50.00;
    
    @Override
    public boolean Payeable(){
    
    
    }
    
    
}
