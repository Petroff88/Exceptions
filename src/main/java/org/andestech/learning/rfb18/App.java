package org.andestech.learning.rfb18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;


/**
 * TODO:
 *
 * Java07-Course-materials
 * Итоговое задание. Срок выполнения - к 17.09.18.
 *
 * Вашему подразделению поручено разработать программный стек для обработки заявок по кредитам и
 * сопровождению выданных кредитов. Архитектор проекта предложил следующий шаблон для выполнения задачи:
 *
 * Заявители должы описываться классом Customer, в котором должны быть следующие поля (как минимум):
 * id, name, sname, salary, age, creditRating.
 *
 * Заявки на кредит должны сопровождаться и обрабатываться классом CreditInquiry.
 *
 * На основе данных клиента - salary, age, creditRating, а также суммы запрашиваемого кредита и срока погашения,
 * принимается решение.
 * При отклонении заявки на кредит должно генерироваться исключение CreditInquiryException.
 *
 * Для получивших кредит заявителей должен создаваться экземпляр класса CreditAccount,
 * который должен описывать все данные кредита и операции по нему.
 * Если Вы не знаете, как написать реализацию какого-либо метода, сделайте класс абстрактным.
 *
 * Добавьте стек исключений, обслуживающий операции по кредитам.
 * Например, при превышении лимита по кредиту, должно генерироваться исключение CreditOverdraftException.
 *
 * Вся история операций по выданным кредитам и заявкам на кредит должна
 * обслуживаться в типе CreditHistory. Этот класс должен уметь сохранять историю во внутренней коллекции,
 * выгружать историю на диск или БД, загружать историю с диска или БД обратно в коллекцию.
 * При необходимости, генерировать исключения.
 *
 * Установите проект в локальный репозиторий maven.
 * Протестируйте проект на основе отдельного приложения (нового проекта maven).
 *
 *
 *
 */

public class App 
{

    public static void main( String[] args ) throws CreditInquiryException, ClassNotFoundException, SQLException, CreditTreatmentException, OperationException {

        int num = 0;
        do {

        Scanner sc1 = new Scanner(System.in);
        System.out.println("Введите номер, соответствующий операции:");
        System.out.println("1 - заведение заяки на кредит");
        System.out.println("2 - внесение ежемесячного платежа");
        System.out.println("3 - закрытие кредитного счета");
        System.out.println("4 - вывести логи на печать");

        //Вариант без исключения
//        num = sc1.nextInt();
//        switch (num) {
//            case 1:
//                CreditAccount test = new CreditAccount("", 0, 0, 0);
//                CreditInquiry ce1 = new CreditInquiry();
//                test = ce1.createEnquiry();
//                break;
//            case 2:
//                CreditAccount test1 = new CreditAccount("", 0, 0, 0);
//                test1.makePayment();
//                break;
//            case 3:
//                CreditAccount test2 = new CreditAccount("", 0, 0, 0);
//                test2.closeCredit();
//                break;
//            case 4:
//                CreditHistory testhistory = new CreditHistory("", "");
//                testhistory.readLogs();
//                break;
//        }


        //Вариант с исключением

        try {

        num = sc1.nextInt();
            if (num>4|num<1){throw new OperationException ("Некорректно введен номер операции, введите число от 1 до 4");}
            } catch (OperationException e){
            //e.printStackTrace();
            //System.out.println(e);

                }
        finally {
            switch (num) {
            case 1:
            CreditAccount test = new CreditAccount("", 0, 0, 0);
            CreditInquiry ce1 = new CreditInquiry();
            test = ce1.createEnquiry();
            break;
            case 2:
                CreditAccount test1 = new CreditAccount("", 0, 0, 0);
                test1.makePayment();
                break;
            case 3:
                CreditAccount test2 = new CreditAccount("", 0, 0, 0);
                test2.closeCredit();
                break;
            case 4:
                CreditHistory testhistory = new CreditHistory("", "");
                testhistory.readLogs();
                break;
                    }
        }

    }
        while (num<1| num>4);



        //test = ce1.createEnquiry();
        //test.makePayment();
        //test.closeCredit();


    }
}
