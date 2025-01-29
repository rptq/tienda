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
public class Employee extends Person implements Logable{

int employeeId = 123;
String password = "test";

    @Override
    public boolean login(int employeeId, String password) {
        System.out.println("Introduce el usuario");
        
        int user = employeeId;
        
        if (user == 123){
            if (password.equals("test")){
                System.out.println("Login correcto");
                return true;
            }
        }
    return false;
    }
}
