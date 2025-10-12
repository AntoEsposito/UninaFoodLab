package main;

import controller.Controller;
import boundaries.LoginPage;

public class Start 
{
    public static void main(String[] args) 
    {   
        LoginPage loginPage = new LoginPage(new Controller());
        loginPage.setVisible(true);
    }
}