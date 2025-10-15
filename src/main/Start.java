package main;

import control.Controller;
import view.LoginPage;

public class Start 
{
    public static void main(String[] args) 
    {   
        LoginPage loginPage = new LoginPage(new Controller());
        loginPage.setVisible(true);
    }
}