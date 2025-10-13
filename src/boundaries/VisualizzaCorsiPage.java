package boundaries;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import boundaries.colors.AppColor;
import control.Controller;

import java.awt.*;


public class VisualizzaCorsiPage extends JPanel 
{
    private static final long serialVersionUID = 1L;
	private Controller controller;
	
    private JComboBox<String> selettoreFiltroCategoria;
    
    private DefaultTableModel tableModel;
    
    private JScrollPane scrollPane;
    
    private JTable corsiTable;
    
    private JPanel visualizzaButtonPanel;
    private JPanel filtriPanel;
    
    private JLabel categoriaLabel;
    
    private JButton visualizzaButton;
    private JButton applicaFiltroButton;
    
    
    public VisualizzaCorsiPage(Controller controller) 
    {
        this.controller = controller;
        
        setForeground(AppColor.FOREGROUND_WHITE);
        setBackground(AppColor.BACKGROUND_GREY);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
        controller.caricaCorsiInTabella(tableModel, 0);    
    }
    
    
    private void initComponents() 
    {
        // panel superiore con i filtri
        filtriPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtriPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        filtriPanel.setBackground(AppColor.BACKGROUND_GREY);
        add(filtriPanel, BorderLayout.NORTH);
        
        // laber categoria
        categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        categoriaLabel.setForeground(AppColor.FOREGROUND_WHITE);
        filtriPanel.add(categoriaLabel);
        
        // selettore categoria da filtrare
        selettoreFiltroCategoria = new JComboBox<>();
        selettoreFiltroCategoria.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selettoreFiltroCategoria.setBackground(AppColor.BUTTON_BLUE);
        selettoreFiltroCategoria.setForeground(AppColor.FOREGROUND_WHITE);
        selettoreFiltroCategoria.setFocusable(false);
        controller.caricaCategorieConFiltroInComboBox(selettoreFiltroCategoria);
        filtriPanel.add(selettoreFiltroCategoria);
        
        // bottone per applicare il filtro
        applicaFiltroButton = new JButton("Applica Filtro");
        applicaFiltroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        applicaFiltroButton.setBackground(AppColor.BUTTON_BLUE);
        applicaFiltroButton.setForeground(AppColor.FOREGROUND_WHITE);
        applicaFiltroButton.setFocusPainted(false);
        applicaFiltroButton.setBorderPainted(false);
        applicaFiltroButton.setContentAreaFilled(true);
        applicaFiltroButton.setOpaque(true);
        applicaFiltroButton.addActionListener(e -> applicaFiltro());
        filtriPanel.add(applicaFiltroButton);
        
        
        // tabella dei corsi
        String[] colonne = {"ID Unico", "Categoria", "Data Inizio", "N. Sessioni", "Frequenza"};
        tableModel = new DefaultTableModel(colonne, 0) // classe anonima per rendere la tabella non editabile
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        
        corsiTable = new JTable(tableModel);
        corsiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        corsiTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        corsiTable.setBackground(AppColor.BACKGROUND_GREY);
        corsiTable.setForeground(AppColor.FOREGROUND_WHITE);
        corsiTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        corsiTable.getTableHeader().setBackground(AppColor.BACKGROUND_GREY);
        corsiTable.getTableHeader().setForeground(AppColor.FOREGROUND_WHITE);
        corsiTable.setSelectionBackground(AppColor.BUTTON_BLUE);
        corsiTable.setSelectionForeground(AppColor.FOREGROUND_WHITE);

        // scroll dove inserire la tabella dei corsi
        scrollPane = new JScrollPane(corsiTable);
        scrollPane.setBackground(AppColor.BACKGROUND_GREY);
        scrollPane.getViewport().setBackground(AppColor.BACKGROUND_GREY);
        add(scrollPane, BorderLayout.CENTER);
        
        
        // panel per il bottone gestione corso
        visualizzaButtonPanel = new JPanel();
        visualizzaButtonPanel.setBackground(AppColor.BACKGROUND_GREY);
        add(visualizzaButtonPanel, BorderLayout.SOUTH);
        
        // bottone per andare nella pagina di visualizzazione dettagli corso
        visualizzaButton = new JButton("Visualizza Dettagli e Associa Ricette");
        visualizzaButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        visualizzaButton.setBackground(AppColor.BUTTON_BLUE);
        visualizzaButton.setForeground(AppColor.FOREGROUND_WHITE);
        visualizzaButton.setFocusPainted(false);
        visualizzaButton.setBorderPainted(false);
        visualizzaButton.setContentAreaFilled(true);
        visualizzaButton.setOpaque(true);
        visualizzaButton.addActionListener(e -> visualizzaDettagliCorso());
        visualizzaButtonPanel.add(visualizzaButton);
    }
    
    
    private void applicaFiltro() 
    {
        int index = selettoreFiltroCategoria.getSelectedIndex();
        controller.caricaCorsiInTabella(tableModel, index);
    }
     
    private void visualizzaDettagliCorso() 
    {
        int selectedRow = corsiTable.getSelectedRow();
        
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Seleziona un corso dalla tabella", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idCorso = (Integer) tableModel.getValueAt(selectedRow, 0);
        controller.mostraPaginaDettagliCorso(this, idCorso);
    }
}