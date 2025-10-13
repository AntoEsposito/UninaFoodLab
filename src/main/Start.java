package main;

import boundaries.LoginPage;
import control.Controller;

public class Start 
{
    public static void main(String[] args) 
    {   
        LoginPage loginPage = new LoginPage(new Controller());
        loginPage.setVisible(true);
    }
}