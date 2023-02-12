/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.FoodieGo.database;

/**
 *
 * @author inder
 */
import java.sql.*;
public class DBLoader 
{
    public static ResultSet executeSQL_Query(String query) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver loaded successfully");
        
           
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/foodie_go","root","@mahabharat1@");
        System.out.println("Connection built");            
           
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        System.out.println("Statement Created");
            
            
            
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Resultset created");
        return rs;
        
    }
    
}
