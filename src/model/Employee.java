/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Scanner;

/**
 *
 * @author RobertTerolLacasta
 */
public class Employee extends Person implements Logable {

    private int employeeId = EMPLOYEE_ID;
    private String password = PASSWORD;

    public final static int EMPLOYEE_ID = 123;
    public final static String PASSWORD = "test";

    @Override
    public boolean login(int id, String psswd) {
        return (id == employeeId && psswd.equals(password));
    }
}
