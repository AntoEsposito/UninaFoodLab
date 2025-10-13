package boundaries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import boundaries.colors.AppColor;
import control.Controller;

import java.awt.*;
import javax.swing.border.TitledBorder;

public class DettagliCorsoPage extends JDialog 
{
    private static final long serialVersionUID = 1L;
	private Controller controller;
	
    private DefaultTableModel sessioniTableModel;
    
    private JTable sessioniTable;
    private JButton associaRicetteButton;
    
    
    public DettagliCorsoPage(Window owner, Controller controller) 
    {
        super(owner, "Dettagli Corso", ModalityType.APPLICATION_MODAL);
        this.controller = controller;

        setSize(800, 600);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(AppColor.BACKGROUND_GREY);
        
        initComponents();
        controller.caricaSessioniInDettagliCorsoPage(sessioniTableModel);
    }
    
    
    private void initComponents() 
    {
        getContentPane().setLayout(new BorderLayout(10, 10));
        
        // Panel con informazioni corso
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 5));
        infoPanel.setBackground(AppColor.BACKGROUND_GREY);
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        
        // labels informazioni corso
        infoPanel.add(creaLabelBianca("  ID Corso: " + String.valueOf(controller.getIdCorsoSelezionato())));
        infoPanel.add(creaLabelBianca("  Categoria: " + controller.getCategoriaCorsoSelezionato()));
        infoPanel.add(creaLabelBianca("  Data Inizio: " + controller.getDataInizioCorsoSelezionato().toString()));
        infoPanel.add(creaLabelBianca("  Numero Sessioni: " + String.valueOf(controller.getNumeroSessioniCorsoSelezionato())));
        infoPanel.add(creaLabelBianca("  Frequenza Sessioni: " + controller.getFrequenzaSessioniCorsoSelezionato()));
        
        
        // panel con tabella sessioni
        JPanel sessioniPanel = new JPanel(new BorderLayout());
        sessioniPanel.setBorder(new TitledBorder(null, "Sessioni del Corso", TitledBorder.LEADING, TitledBorder.TOP, null, AppColor.FOREGROUND_WHITE));
        sessioniPanel.setBackground(AppColor.BACKGROUND_GREY);
        getContentPane().add(sessioniPanel, BorderLayout.CENTER);
        
        // tabella sessioni
        String[] colonne = {"Numero Sessione", "Data Sessione", "Tipo Sessione", "URL", "Ricette"};
        sessioniTableModel = new DefaultTableModel(colonne, 0) // classe anonima per rendere non editabile la tabella
        {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        sessioniTable = new JTable(sessioniTableModel);
        sessioniTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sessioniTable.setBackground(AppColor.BACKGROUND_GREY);
        sessioniTable.setForeground(AppColor.FOREGROUND_WHITE);
        sessioniTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        sessioniTable.getTableHeader().setBackground(AppColor.BACKGROUND_GREY);
        sessioniTable.getTableHeader().setForeground(AppColor.FOREGROUND_WHITE);
        sessioniTable.setSelectionBackground(AppColor.BUTTON_BLUE);
        sessioniTable.setSelectionForeground(AppColor.FOREGROUND_WHITE);
        
        // scroll dove inserire la tabella delle sessioni
        JScrollPane scrollPane = new JScrollPane(sessioniTable);
        scrollPane.setBackground(AppColor.BACKGROUND_GREY);
        scrollPane.getViewport().setBackground(AppColor.BACKGROUND_GREY);
        sessioniPanel.add(scrollPane, BorderLayout.CENTER);
      
        
        // Panel pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(AppColor.BACKGROUND_GREY);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        // bottone associa ricette
        associaRicetteButton = new JButton("Visualizza Dettagli e Associa Ricette");
        associaRicetteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        associaRicetteButton.setBackground(AppColor.BUTTON_BLUE);
        associaRicetteButton.setForeground(AppColor.FOREGROUND_WHITE);
        associaRicetteButton.setFocusPainted(false);
        associaRicetteButton.setBorderPainted(false);
        associaRicetteButton.setContentAreaFilled(true);
        associaRicetteButton.setOpaque(true);
        associaRicetteButton.addActionListener(e -> associaRicette());
        buttonPanel.add(associaRicetteButton);
        
        // bottone chiudi
        JButton chiudiButton = new JButton("Chiudi");
        chiudiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chiudiButton.setBackground(AppColor.BUTTON_BLUE);
        chiudiButton.setForeground(AppColor.FOREGROUND_WHITE);
        chiudiButton.setFocusPainted(false);
        chiudiButton.setBorderPainted(false);
        chiudiButton.setContentAreaFilled(true);
        chiudiButton.setOpaque(true);
        chiudiButton.addActionListener(e -> dispose());
        buttonPanel.add(chiudiButton);
    }
    
    
    private JLabel creaLabelBianca(String testo) 
	{
		JLabel label = new JLabel(testo);
		label.setForeground(AppColor.FOREGROUND_WHITE);
		label.setFont(new Font("Segoe UI", Font.BOLD, 14));
		return label;
	}
    
    private void associaRicette() 
    {
        int selectedRow = sessioniTable.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Seleziona una sessione dalla tabella", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.apriAssociaRicetteDialog(this, selectedRow);
        controller.caricaSessioniInDettagliCorsoPage(sessioniTableModel); // aggiorna la tabella dopo l'associazione
    }
}
