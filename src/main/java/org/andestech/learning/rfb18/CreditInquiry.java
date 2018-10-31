package org.andestech.learning.rfb18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

//этот класс должен обрабатывать заявки на кредит
public class CreditInquiry {




    private static String getCurrentTimeStamp() { DateFormat dateFormat = DateFormat.getDateInstance();
        Date today = new Date(); return dateFormat.format(today.getTime()); }

    public CreditAccount createEnquiry() throws CreditInquiryException, SQLException, ClassNotFoundException, CreditTreatmentException {
// Процедура сбора данных и обработка заявки:
        Scanner sc1 = new Scanner(System.in);
//        System.out.println("Введите имя клиента");
//        String _name = sc1.next();
//        System.out.println("Введите фамилию клиента");
//        String _sname = sc1.next();
//        System.out.println("Введите запрплату клиента");

        Customer cus1 = new Customer("Иван","Иванов",30000, 56,1);
        cus1.setId();
        System.out.println("Клиенту присвоен ID - "+cus1.id);
        System.out.println("Введите суму кредита");
        double _issuedSum = sc1.nextDouble();
        System.out.println("Введите рассрочку в месяцах ");
        int _period = sc1.nextInt();
        double _toPay = _issuedSum * (1+(0.1*(_period/12)));
        double _monthlyPay = _toPay/_period;
        System.out.println("Обработка заявки:");
        System.out.println("Сумма к выдаче: " + _issuedSum + ", сумма к выплате: " + ", ежемесячный платеж: " + _monthlyPay + ", сроком на " + _period +" месяцев.");

        if (cus1.age<18)
        {
            //System.out.println("В выдаче кредита отказано: клиент несовершеннолетний");
            throw new CreditInquiryException("В выдаче кредита отказано: клиент несовершеннолетний");
        }
        else if (cus1.age>99)
        {
            //System.out.println("В выдаче кредита отказано");
            throw new CreditInquiryException("В выдаче кредита отказано");
        }
        else if (cus1.creditRating<0)
        {
            //System.out.println("В выдаче кредита отказано: отрицательная кредитная история");
            throw new CreditInquiryException("В выдаче кредита отказано: отрицательная кредитная история");

        }
        else if (_monthlyPay>cus1.salary-15000) {
            //System.out.println("В выдаче кредита отказано: ежемесячный платеж не должен превышать " + (cus1.salary-15000));
            throw new CreditInquiryException("В выдаче кредита отказано: ежемесячный платеж не должен превышать " + (cus1.salary-15000));
        }
        else {
            System.out.println("Заявка на кредит одобрена");
            System.out.println("Сумма к выдаче: " + _issuedSum + ", сумма к выплате: "+ _toPay + ", ежемесячный платеж: " + _monthlyPay + ", сроком на " + _period +" месяцев.");


            CreditAccount ca1 = new CreditAccount(cus1.id, _toPay, _monthlyPay, _period);
            ca1.setAccNum();
            System.out.println("Номер счета:" + ca1.accNum);

            //коннектимся к БД
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/postgres";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","dbpass");
            props.setProperty("ssl","false");
            Connection conn = DriverManager.getConnection(url, props);
            //Записываем счет в БД:
            Statement st = conn.createStatement();
            String insAcc = "INSERT INTO public.accounts(\n" +
                    "            \"fullSum\", \"monthlyPay\", period, \"accnum\", \"customerID\",\"balance\")\n" +
                    "    VALUES ("+ca1.toPay+","+ca1.monthlyPay+","+ca1.period+","+ca1.accNum+","+cus1.id+","+ca1.toPay+");\n";
            st.executeUpdate(insAcc);
            //пишем логи в БД
            CreditHistory log1 = new CreditHistory(ca1.accNum,"Открыт кредит для клиента "+ cus1.name+" "+cus1.sname+" на сумму "+ca1.toPay+" руб.");
            log1.logs.add(log1);
            log1.writeLogs();
            //ca1.makePayment();
            //ca1.closeCredit();

            st.close();
            conn.close();
            return ca1;
        }




    }



}
