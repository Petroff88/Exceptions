package org.andestech.learning.rfb18;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class CreditHistory {

private String acc = "";
private String summary = "";


    public CreditHistory(String acc, String summary) {
        this.setAcc(acc);
        this.setSummary(summary);
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    ArrayList<CreditHistory>logs = new ArrayList<CreditHistory>();

    public void writeLogs () throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","dbpass");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);
        //Записываем логи в БД:
        Statement st = conn.createStatement();
        String insLog = "INSERT INTO public.\"Credit_history\" (account_number, operation_description) VALUES ('"+ getAcc() +"','"+ getSummary() +"')";
        //System.out.println(insLog);
        for(int i = 0; i < logs.size(); i++)
        {st.executeUpdate(insLog);}
        st.close();
        conn.close();
    }

    public void readLogs()throws ClassNotFoundException, SQLException{
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","dbpass");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);
        Statement st = conn.createStatement();
        String selectLog = "SELECT account_number, operation_description FROM public.\"Credit_history\"";
        ResultSet rs = st.executeQuery(selectLog);
        System.out.println(selectLog);
        while (rs.next()){
        logs.add(new CreditHistory(rs.getString("account_number"),rs.getString("operation_description")));
        }
        for(int i=0;i<logs.size();i++)
        System.out.println(logs.get(i).getAcc()+". "+logs.get(i).getSummary());

        rs.close();
        st.close();
        conn.close();
    }



}
