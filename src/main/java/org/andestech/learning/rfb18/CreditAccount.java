package org.andestech.learning.rfb18;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

//кредитные счета
public class CreditAccount {


    String accNum;
    String ownerId = "";//проверить, откуда он приходит
    double toPay;
    double monthlyPay;
    int period;

    ArrayList accounts = new ArrayList<CreditAccount>();

    public CreditAccount(String ownerId, double toPay, double monthlyPay, int period) {
        this.accNum = accNum;
        this.ownerId = ownerId;
        this.toPay = toPay;
        this.monthlyPay = monthlyPay;
        this.period = period;
    }


    public String setAccNum () throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        String url = "jdbc:postgresql://localhost/postgres";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","dbpass");
        props.setProperty("ssl","false");
        Connection conn = DriverManager.getConnection(url, props);

        String query = "select * from public.data";
        Statement st = conn.createStatement();

        int accNumCount = 0;
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            accNumCount = rs.getInt("accountnum");
        }

        String s = String.format("%07d", accNumCount);
        accNum = "4081781012000"+s;
        accNumCount++;
        System.out.println(accNumCount);
        String updQuery = "UPDATE public.data SET accountnum = "+accNumCount;
        st.executeUpdate(updQuery);
        String log = "Создан счет № "+accNum;

        rs.close();
        st.close();
        conn.close();
        return accNum;
        }

        public void closeCredit() throws SQLException, ClassNotFoundException, CreditTreatmentException {

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите номер счета для закрытия");
            String tempAccnum = sc.next();
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","dbpass");
            props.setProperty("ssl","false");
            Connection conn = DriverManager.getConnection(url, props);

            Statement st = conn.createStatement();
            String selectAccNum = "SELECT balance FROM public.accounts WHERE accNum = '"+tempAccnum+"'";
            int balance = 0;
            ResultSet rs = st.executeQuery(selectAccNum);
            if (rs.next()){
                balance = rs.getInt("balance");
            }
            if (balance>0) {
                //прикрутить сюда exception
                //System.out.println("Невозможно закрыть кредит. Кредит погашен не полностью");
                throw new CreditTreatmentException("Невозможно закрыть кредит. Кредит погашен не полностью");
            }
            else if (balance<0){

                //System.out.println("Невозможно закрыть кредит. Переплата по кредиту, необходимо вернуть средства клиенту");
                throw new CreditTreatmentException("Невозможно закрыть кредит. Переплата по кредиту, необходимо вернуть средства клиенту");
            }
            else {
            String deleteAcc = "DELETE FROM public.accounts WHERE accNum ='"+tempAccnum+"'";
            //логируем закрытие в БД
            CreditHistory log1 = new CreditHistory(tempAccnum,"Счет "+tempAccnum+" закрыт");
            log1.logs.add(log1);
            log1.writeLogs();

            System.out.println("Кредит закрыт");}
            rs.close();
            st.close();
            conn.close();

        }

        public void makePayment () throws ClassNotFoundException, SQLException {

            Scanner sc = new Scanner(System.in);
            System.out.println("Введите номер счета, по которому необходимо внести ежемесячный платеж.");
            String tempAccnum = sc.next();
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","dbpass");
            props.setProperty("ssl","false");
            Connection conn = DriverManager.getConnection(url, props);

            Statement st = conn.createStatement();
            String selectBalance = "SELECT \"balance\",\"monthlyPay\" FROM public.accounts WHERE \"accnum\" = '"+tempAccnum+"'";
            int balance = 0;
            int payment = 0;
            //System.out.println(selectBalance);
            ResultSet rs = st.executeQuery(selectBalance);
            if (rs.next()){
                balance = rs.getInt("balance");
                payment = rs.getInt("monthlyPay");
            }
            balance = balance-payment;
            String updBal = "UPDATE public.accounts SET balance="+balance+" WHERE accNum = '"+tempAccnum+"'";
            st.executeUpdate(updBal);
            //логируем оплату в БД
            CreditHistory log1 = new CreditHistory(tempAccnum,"По счету "+tempAccnum+" внесен платеж в размере "+payment+" руб. Остаток задолженности "+balance+" руб.");
            log1.logs.add(log1);
            log1.writeLogs();

            System.out.println("Платеж внесен, остаток задолженности по кредиту составляет"+balance+"руб.");


            rs.close();
            st.close();
            conn.close();
        }





//    public void addAcc(){
//        accounts.add(1,new CreditAccount());
//
//    }


}
