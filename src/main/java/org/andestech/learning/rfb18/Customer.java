package org.andestech.learning.rfb18;

import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class Customer {

    public String id = "";
    public String name = "";
    public String sname = "";
    public double salary;
    public int age;
    public int creditRating;


    public Customer(String name, String sname, double salary, int age, int creditRating) {
        this.name = name;
        this.sname = sname;
        this.salary = salary;
        this.age = age;
        this.creditRating = creditRating;
        this.id = id;
    }

    public String setId() throws ClassNotFoundException, SQLException {
        int idCount = 0;

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","dbpass");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);

        String query = "select * from public.data";
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            idCount = rs.getInt("customerid");
        }

        id = String.format("%04d", idCount);
        idCount++;
        String updQuery = "UPDATE public.data SET customerid = "+idCount;
        st.executeUpdate(updQuery);
        String log = "ID клиента - "+id;

        rs.close();
        st.close();
        conn.close();
        return id;
    }
}
