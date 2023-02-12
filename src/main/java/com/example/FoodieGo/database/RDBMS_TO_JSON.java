package com.example.FoodieGo.database;

import java.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class RDBMS_TO_JSON 
{
    public static String generateJSON(String SQLStatement)
    {        
        String JSONans = "" ;
        try
        {
            ResultSet rs  = DBLoader.executeSQL_Query(SQLStatement);
            System.out.println("ResultSet Created");
//            while(rs.next())
//            {
//                System.out.println(rs.getString("emp_name"));
//            }
            
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int n = rsmd.getColumnCount();
            System.out.println(n);
            
            JSONObject ansobject = new JSONObject();

            JSONArray jsonArray = new JSONArray();               
            
            while(rs.next())
            {
                JSONObject singlerow = new JSONObject();
                for(int j = 1 ; j <= n ; j++)
                {
                    String clname = rsmd.getColumnLabel(j);                         
                    singlerow.put(clname, rs.getString(clname));
                }
                jsonArray.add(singlerow);
                        
            }
            ansobject.put("ans", jsonArray);
            
            JSONans = ansobject.toJSONString();
            System.out.println(JSONans);
            
        }
        catch(Exception e)
        {
        } 
        return JSONans;
        
    }
    public static void main(String[] args) 
    {
        System.out.println("ans: "+generateJSON("select * from emp"));
    }       
}
