package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import control.Controller;
import view.color.AppColor;

import java.awt.*;


public class MainPage extends JFrame 
{
    private static final long serialVersionUID = 1L;
	private Controller controller;
	
    private JTabbedPane tabbedPane;
    
    private JPanel topPanel;
    
    private JLabel welcomeLabel;
    
    private JButton logoutButton;
    
    
    public MainPage(Controller controller) 
    {
        this.controller = controller;
        
        getContentPane().setBackground(AppColor.BACKGROUND_GREY);
        setIconImage(Toolkit.getDefaultToolkit().getImage(MainPage.class.getResource("/images/UninaFoodLabSmallLogo.png")));
        setTitle("UninaFoodLab - Chef Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    
    private void initComponents() 
    {
        // panel superiore
        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(AppColor.BACKGROUND_GREY);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // label con nome chef autenticato
        welcomeLabel = new JLabel(controller.getNomeChefAutenticato() + " - AREA CHEF");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(AppColor.FOREGROUND_WHITE);
        topPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // bottone di logout
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutButton.setBackground(AppColor.LOGOUT_RED);
        logoutButton.setForeground(AppColor.FOREGROUND_WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(true);
        logoutButton.setOpaque(true);
        logoutButton.addActionListener(e -> logout());
        topPanel.add(logoutButton, BorderLayout.EAST);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        
        // tabbed pane con le diverse funzionalità
        UIManager.put("TabbedPane.selected", AppColor.BUTTON_BLUE); // Colore sfondo tab selezionata
        UIManager.put("TabbedPane.foreground", AppColor.FOREGROUND_WHITE); // Colore testo tab
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 16));
        tabbedPane.setBackground(AppColor.BACKGROUND_GREY);
        tabbedPane.setForeground(AppColor.FOREGROUND_WHITE);


        
        // tab per creare nuovo corso;
        tabbedPane.addTab("Nuovo Corso", controller.creaNuovoCorsoPage());
        
        // tab per visualizzare corsi
        tabbedPane.addTab("Visualizza Corsi", controller.creaVisualizzaCorsiPage());
        
        // tab per visualizzare report mensile
        tabbedPane.addTab("Report Mensile", controller.creaVisualizzaReportPage());
        
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }
    
    
    private void logout() 
    {
        int scelta = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler uscire?", "Conferma logout", JOptionPane.YES_NO_OPTION);
        if (scelta == JOptionPane.YES_OPTION) 
        {
            controller.logout();
            controller.setVisibleLoginPage();
            dispose();
        }
    }
}