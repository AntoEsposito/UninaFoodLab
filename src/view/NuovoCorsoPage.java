package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import control.Controller;
import view.color.AppColor;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.border.TitledBorder;


public class NuovoCorsoPage extends JPanel 
{
    private static final long serialVersionUID = 1L;
	private Controller controller;
	
    private JPanel formPanel;
    private JPanel sessioniPanel;
    private JPanel sessioniContainer;
    
    private JLabel titleLabel;
    private JLabel categoriaLabel;
    private JLabel dataInizioLabel;
    private JLabel numeroSessioniLabel;
    private JLabel frequenzaLabel;
    private JLabel logoLabel;
    
    private JComboBox<String> categoriaCombo;
    private JComboBox<String> frequenzaCombo;
    
    private JTextField dataInizioField;
    private JSpinner numeroSessioniSpinner;
    
    private JScrollPane scrollPane;
    
    private JButton creaCorsoButton;
    
    
    public NuovoCorsoPage(Controller controller) 
    {
        this.controller = controller;
        
        setBackground(AppColor.BACKGROUND_GREY);
        setBorder(new EmptyBorder(5, 20, 20, 20));
        setLayout(new BorderLayout(0, 0));
        
        initComponents();
    }
    
    
    private void initComponents() 
    {
        // panel principale con titolo e form
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(AppColor.BACKGROUND_GREY);
        formPanel.setBorder(new EmptyBorder(0, 10, 5, 10));
        add(formPanel, BorderLayout.NORTH);
        
        // label titolo
        titleLabel = new JLabel("CREA NUOVO CORSO\r\n");
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColor.FOREGROUND_WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        formPanel.add(titleLabel);
        
        // Panel tutti i campi creazione corso
        JPanel campiPanel = new JPanel(new GridLayout(4, 2, 15, 10));
        campiPanel.setBackground(AppColor.BACKGROUND_GREY);
        campiPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        campiPanel.setMaximumSize(new Dimension(600, 200));
        formPanel.add(campiPanel);
        
        // label categoria
        categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        categoriaLabel.setForeground(AppColor.FOREGROUND_WHITE);
        campiPanel.add(categoriaLabel);
        
        // combo box categoria
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        categoriaCombo.setBackground(AppColor.BUTTON_BLUE);
        categoriaCombo.setForeground(AppColor.FOREGROUND_WHITE);
        categoriaCombo.setFocusable(false);
        categoriaCombo.setPreferredSize(new Dimension(200, 30));
        controller.caricaCategorieInComboBox(categoriaCombo);
        campiPanel.add(categoriaCombo);
        
        // data Inizio
        dataInizioLabel = new JLabel("Data Inizio (gg/mm/aaaa):");
        dataInizioLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dataInizioLabel.setForeground(AppColor.FOREGROUND_WHITE);
        campiPanel.add(dataInizioLabel);
        
        // text field data (calendario era troppo difficile)
        dataInizioField = new JTextField(15);
        dataInizioField.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dataInizioField.setBackground(AppColor.BUTTON_BLUE);
        dataInizioField.setForeground(AppColor.FOREGROUND_WHITE);
        dataInizioField.setCaretColor(AppColor.FOREGROUND_WHITE);
        dataInizioField.setPreferredSize(new Dimension(200, 30));
        dataInizioField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        campiPanel.add(dataInizioField);
        
        // label numero Sessioni
        numeroSessioniLabel = new JLabel("Numero Sessioni:");
        numeroSessioniLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        numeroSessioniLabel.setForeground(AppColor.FOREGROUND_WHITE);
        campiPanel.add(numeroSessioniLabel);
        
        // spinner numero sessioni
        numeroSessioniSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 20, 1));
        numeroSessioniSpinner.setFont(new Font("Segoe UI", Font.BOLD, 16));
        numeroSessioniSpinner.setPreferredSize(new Dimension(200, 30));
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) numeroSessioniSpinner.getEditor();
        editor.getComponent(0).setBackground(AppColor.BUTTON_BLUE);
        editor.getComponent(0).setForeground(AppColor.FOREGROUND_WHITE);
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        numeroSessioniSpinner.addChangeListener(e -> aggiornaSessioniPanel());
        campiPanel.add(numeroSessioniSpinner);
        
        // label frequenza Sessioni
        frequenzaLabel = new JLabel("Frequenza Sessioni:");
        frequenzaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        frequenzaLabel.setForeground(AppColor.FOREGROUND_WHITE);
        campiPanel.add(frequenzaLabel);
        
        frequenzaCombo = new JComboBox<>();
        frequenzaCombo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        frequenzaCombo.setBackground(AppColor.BUTTON_BLUE);
        frequenzaCombo.setForeground(AppColor.FOREGROUND_WHITE);
        frequenzaCombo.setFocusable(false);
        frequenzaCombo.setPreferredSize(new Dimension(200, 30));
        controller.caricaFrequenzeInComboBox(frequenzaCombo);
        campiPanel.add(frequenzaCombo);
       
        
        // panel per le sessioni
        sessioniContainer = new JPanel(new BorderLayout());
        sessioniContainer.setBorder(new TitledBorder(null, "Sessioni del Corso", 0, 0, new Font("Segoe UI", Font.BOLD, 14), AppColor.FOREGROUND_WHITE));
        sessioniContainer.setBackground(AppColor.BACKGROUND_GREY);
        add(sessioniContainer, BorderLayout.CENTER);

        // panel interno per sessioni
        sessioniPanel = new JPanel();
        sessioniPanel.setBackground(AppColor.BACKGROUND_GREY);
        sessioniPanel.setLayout(new BoxLayout(sessioniPanel, BoxLayout.Y_AXIS));
        
        // scroll pane per sessioni
        scrollPane = new JScrollPane(sessioniPanel);
        scrollPane.setBackground(AppColor.BACKGROUND_GREY);
        scrollPane.getViewport().setBackground(AppColor.BACKGROUND_GREY);
        scrollPane.setBorder(null);
        sessioniContainer.add(scrollPane, BorderLayout.CENTER);
        
        
        // pulsante crea corso
        creaCorsoButton = new JButton("Crea Corso");
        creaCorsoButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        creaCorsoButton.setBackground(AppColor.BUTTON_BLUE);
        creaCorsoButton.setForeground(AppColor.FOREGROUND_WHITE);
        creaCorsoButton.setFocusPainted(false);
        creaCorsoButton.setBorderPainted(false);
        creaCorsoButton.setContentAreaFilled(true);
        creaCorsoButton.setOpaque(true);
        creaCorsoButton.addActionListener(e -> creaCorso());
        add(creaCorsoButton, BorderLayout.SOUTH);
        
        logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(NuovoCorsoPage.class.getResource("/images/UninaFoodLabLogo.png")));
        add(logoLabel, BorderLayout.EAST);
        
        // Inizializza con 4 sessioni
        aggiornaSessioniPanel();
    }
    
    // aggiorna il panel delle sessioni in base al numero selezionato nello spinner don dei label creati dinamicamente
    private void aggiornaSessioniPanel() 
    {
        sessioniPanel.removeAll();
        int numeroSessioni = (Integer) numeroSessioniSpinner.getValue();
        
        for (int i = 1; i <= numeroSessioni; i++) 
        {
        	// panel singola sessione da inserire nal panel principale
            JPanel sessionePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            sessionePanel.setBorder(new LineBorder(AppColor.FOREGROUND_WHITE));
            sessionePanel.setBackground(AppColor.BACKGROUND_GREY);
            sessioniPanel.add(sessionePanel);
            
            JLabel sessioneLabel = new JLabel("Sessione " + i + ":");
            sessioneLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            sessioneLabel.setForeground(AppColor.FOREGROUND_WHITE);
            sessionePanel.add(sessioneLabel);
            
            JRadioButton onlineRadio = new JRadioButton("Online", true);
            JRadioButton presenzaRadio = new JRadioButton("In Presenza");
            
            onlineRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            onlineRadio.setBackground(AppColor.BACKGROUND_GREY);
            onlineRadio.setForeground(AppColor.FOREGROUND_WHITE);
            sessionePanel.add(onlineRadio);
            
            presenzaRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            presenzaRadio.setBackground(AppColor.BACKGROUND_GREY);
            presenzaRadio.setForeground(AppColor.FOREGROUND_WHITE);
            sessionePanel.add(presenzaRadio);
            
            ButtonGroup group = new ButtonGroup(); // per selezione mutua esclusione
            group.add(onlineRadio);
            group.add(presenzaRadio);
            
            sessionePanel.putClientProperty("presenzaRadio", presenzaRadio);      
        }
		sessioniPanel.revalidate();
		sessioniPanel.repaint();
    }

    private void creaCorso() 
    {
        // validazione campi
        String dataText = dataInizioField.getText().trim();
        if (dataText.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Inserisci una data di inizio", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate dataInizio;
        try 
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataInizio = LocalDate.parse(dataText, formatter);
        } 
        catch (DateTimeParseException e) 
        {
            JOptionPane.showMessageDialog(this, "Formato data non valido. Usa il formato gg/mm/aaaa", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int categoriaIndex = categoriaCombo.getSelectedIndex();
        int numeroSessioni = (Integer) numeroSessioniSpinner.getValue();
        int frequenzaIndex = frequenzaCombo.getSelectedIndex();
        
        // crea il corso
        boolean successo = controller.creaCorsoByIndex(dataInizio, numeroSessioni, frequenzaIndex, categoriaIndex);
        
        if (successo) 
        {
            // Prepara array modalità sessioni
            boolean[] modalitaInPresenza = new boolean[numeroSessioni];
            Component[] componenti = sessioniPanel.getComponents();
            
            for (int i = 0; i < componenti.length && i < numeroSessioni; i++) 
            {
                if (componenti[i] instanceof JPanel) 
                {
                    JPanel sessionePanel = (JPanel) componenti[i];
                    JRadioButton presenzaRadio = (JRadioButton) sessionePanel.getClientProperty("presenzaRadio");
                    modalitaInPresenza[i] = presenzaRadio != null && presenzaRadio.isSelected();
                }
            }
            
            // Crea le sessioni
            boolean sessioniCreate = controller.creaSessioniPerUltimoCorso(dataInizio, frequenzaIndex, modalitaInPresenza);
            
            if (sessioniCreate) 
            {
                JOptionPane.showMessageDialog(this, "Corso creato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } 
            else JOptionPane.showMessageDialog(this, "Corso creato ma errore nella creazione delle sessioni", "Attenzione", JOptionPane.WARNING_MESSAGE);
        } 
        else JOptionPane.showMessageDialog(this, "Errore nella creazione del corso", "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    
    private void resetForm() 
    {
        dataInizioField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        numeroSessioniSpinner.setValue(4);
        if (categoriaCombo.getItemCount() > 0) categoriaCombo.setSelectedIndex(0);
        if (frequenzaCombo.getItemCount() > 0) frequenzaCombo.setSelectedIndex(0);
        aggiornaSessioniPanel();
    }
}