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
    public final static double BALANCE = 50.0;

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    @Override
    public boolean pay(int id, double balance, double total){
    
    balance = balance-total;
    setBalance(balance);
    if (balance>0){
    return true;
    }
    else{
    return false;
    }
    }
    
    
}
