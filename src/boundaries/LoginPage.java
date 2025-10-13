package boundaries;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

import boundaries.colors.AppColor;
import control.Controller;


public class LoginPage extends JFrame 
{
    private static final long serialVersionUID = 1L;
	private Controller controller;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel formPanel;
	
	private JLabel titleLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private JButton loginButton;
    
    
    public LoginPage(Controller controller) 
    {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(LoginPage.class.getResource("/images/UninaFoodLabSmallLogo.png")));
        this.controller = controller;
        
    	setResizable(false);
        setTitle("Chef Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        
        initComponents(); // per non appesantire il costruttore
    }
    
    
    private void initComponents() 
    {
    	// pannello principale
        mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(AppColor.BACKGROUND_GREY);
        mainPanel.setBorder(new EmptyBorder(30, 60, 30, 60));
        getContentPane().add(mainPanel);
        
        
        // pannello superiore con titolo e sottotitolo
        topPanel = new JPanel();
        topPanel.setBackground(AppColor.BACKGROUND_GREY);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new BorderLayout(0, 0));
        
        // label titolo
        titleLabel = new JLabel();
        titleLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/images/UninaFoodLabLogo.png")));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(AppColor.FOREGROUND_WHITE);
        topPanel.add(titleLabel, BorderLayout.NORTH);
        
        
        // pannello centrale per il form di login
        formPanel = new JPanel();
        formPanel.setBackground(AppColor.BACKGROUND_GREY);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // label username
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(AppColor.FOREGROUND_WHITE);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(usernameLabel);
        
        // field username
        usernameField = new JTextField();
        usernameField.setForeground(Color.BLACK);
        usernameField.setBackground(AppColor.FOREGROUND_WHITE);
        usernameField.setBorder(null);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(usernameField);
        
        // label password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordLabel);
        
        // field password
        passwordField = new JPasswordField();
        passwordField.setForeground(Color.BLACK);
        passwordField.setBackground(AppColor.FOREGROUND_WHITE);
        passwordField.setBorder(null);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordField);
        
        
        // pulsante login
        loginButton = new JButton("Accedi");
        loginButton.setForeground(AppColor.FOREGROUND_WHITE);
        loginButton.setBackground(AppColor.BUTTON_BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(true);
        loginButton.setOpaque(true);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(e -> login());
        mainPanel.add(loginButton, BorderLayout.SOUTH);
        
        // premere invio sul campo username per spostarsi al campo password
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());
        
        // premere invio sul campo password per effettuare il login
        passwordField.addActionListener(e -> login());
    }
    
    
    private void login() 
    {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword()); // per qualche motivo toString() non funziona
		        
        if (username.isEmpty() || password.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Inserisci Username e Password", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean autenticato = controller.autenticaChef(username, password);
        if (autenticato) dispose(); // chiude la finestra di login
        else passwordField.setText(""); // resetta la password
    }
}