package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import control.Controller;
import view.color.AppColor;

import java.awt.*;
import java.util.List;


public class AssociaRicettePage extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
    private DettagliCorsoPage parentPage;
    
    private JPanel infoPanel;
    private JPanel disponibiliPanel;
    private JPanel buttonsPanel;
    private JPanel associatePanel;
    private JPanel bottomPanel;
    
    private JLabel infoLabel;
    
    private JList<String> ricetteDisponibiliList;
    private JList<String> ricetteAssociateList;
    
    private DefaultListModel<String> ricetteDisponibiliModel;
    private DefaultListModel<String> ricetteAssociateModel;
    
    private JScrollPane scrollDisponibili;
    private JScrollPane scrollAssociate;
    
    private JButton aggiungiButton;
    private JButton rimuoviButton;
    private JButton chiudiButton;
    
    
    public AssociaRicettePage(Window owner, Controller controller) 
    {
        super(owner, "Associa Ricette alla Sessione", ModalityType.APPLICATION_MODAL);
        this.controller = controller;
        this.parentPage = (DettagliCorsoPage) owner;
        
        setSize(800, 570);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(AppColor.BACKGROUND_GREY);
        
        initComponents();
        caricaRicette();
    }
    
    
    private void initComponents() 
    {
        getContentPane().setLayout(new BorderLayout(10, 10));
        
        // Info sessione
        infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(AppColor.BACKGROUND_GREY);
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(infoPanel, BorderLayout.NORTH);
        
        // label info sessione
        infoLabel = new JLabel(controller.getInfoSessioneSelezionata());
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        infoLabel.setForeground(AppColor.FOREGROUND_WHITE);
        infoPanel.add(infoLabel);
        
        
        // panel per lista ricette disponibili
        disponibiliPanel = new JPanel(new BorderLayout(5, 5));
        disponibiliPanel.setBackground(AppColor.BACKGROUND_GREY);
        disponibiliPanel.setBorder(new TitledBorder(null, "Ricette Disponibili", 0, 0, new Font("Segoe UI", Font.BOLD, 14), AppColor.FOREGROUND_WHITE));
        disponibiliPanel.setBorder(new CompoundBorder(new EmptyBorder(0, 10, 0, 0), disponibiliPanel.getBorder()));
        disponibiliPanel.setPreferredSize(new Dimension(300, 0));
        add(disponibiliPanel, BorderLayout.WEST);
        
        // lista ricette disponibili
        ricetteDisponibiliModel = new DefaultListModel<>();
        ricetteDisponibiliList = new JList<>(ricetteDisponibiliModel);
        ricetteDisponibiliList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ricetteDisponibiliList.setBackground(AppColor.BACKGROUND_GREY);
        ricetteDisponibiliList.setForeground(AppColor.FOREGROUND_WHITE);
        ricetteDisponibiliList.setSelectionBackground(AppColor.BUTTON_BLUE);
        ricetteDisponibiliList.setSelectionForeground(AppColor.FOREGROUND_WHITE);
        
        // scroll pane per lista ricette disponibili
        scrollDisponibili = new JScrollPane(ricetteDisponibiliList);
        scrollDisponibili.setBackground(AppColor.BACKGROUND_GREY);
        scrollDisponibili.getViewport().setBackground(AppColor.BACKGROUND_GREY);
        disponibiliPanel.add(scrollDisponibili, BorderLayout.CENTER);
        
        
        // panel pulsanti centrali
        buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(120, 0, 0, 0));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(AppColor.BACKGROUND_GREY);
        add(buttonsPanel, BorderLayout.CENTER);
        
        // pulsante aggiungi ricetta
        aggiungiButton = new JButton("→");
        buttonsPanel.add(aggiungiButton);
        aggiungiButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        aggiungiButton.setBackground(AppColor.BUTTON_BLUE);
        aggiungiButton.setForeground(AppColor.FOREGROUND_WHITE);
        aggiungiButton.setFocusPainted(false);
        aggiungiButton.setContentAreaFilled(true);
        aggiungiButton.setOpaque(true);
        aggiungiButton.setToolTipText("Aggiungi ricetta selezionata");
        aggiungiButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aggiungiButton.addActionListener(e -> aggiungiRicetta());
        
        // pulsante rimuovi ricetta
        rimuoviButton = new JButton("←");
        rimuoviButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        rimuoviButton.setBackground(AppColor.BUTTON_BLUE);
        rimuoviButton.setForeground(AppColor.FOREGROUND_WHITE);
        rimuoviButton.setFocusPainted(false);
        rimuoviButton.setContentAreaFilled(true);
        rimuoviButton.setOpaque(true);
        rimuoviButton.setToolTipText("Rimuovi ricetta selezionata");
        rimuoviButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rimuoviButton.addActionListener(e -> rimuoviRicetta());
        buttonsPanel.add(rimuoviButton);
        
        
        // panel lista ricette associate
        associatePanel = new JPanel(new BorderLayout(5, 5));
        associatePanel.setBackground(AppColor.BACKGROUND_GREY);
        associatePanel.setBorder(new TitledBorder(null, "Ricette Associate", 0, 0, new Font("Segoe UI", Font.BOLD, 14), AppColor.FOREGROUND_WHITE));
        associatePanel.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 10), associatePanel.getBorder()));
        associatePanel.setPreferredSize(new Dimension(300, 0));
        add(associatePanel, BorderLayout.EAST);
        
        // lista ricette associate
        ricetteAssociateModel = new DefaultListModel<>();
        ricetteAssociateList = new JList<>(ricetteAssociateModel);
        ricetteAssociateList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ricetteAssociateList.setBackground(AppColor.BACKGROUND_GREY);
        ricetteAssociateList.setForeground(AppColor.FOREGROUND_WHITE);
        ricetteAssociateList.setSelectionBackground(AppColor.BUTTON_BLUE);
        ricetteAssociateList.setSelectionForeground(AppColor.FOREGROUND_WHITE);
        
        // scroll pane per lista ricette associate
        scrollAssociate = new JScrollPane(ricetteAssociateList);
        scrollAssociate.setBackground(AppColor.BACKGROUND_GREY);
        scrollAssociate.getViewport().setBackground(AppColor.BACKGROUND_GREY);
        associatePanel.add(scrollAssociate, BorderLayout.CENTER);
        
        
        // panel inferiore con pulsante chiudi
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(AppColor.BACKGROUND_GREY);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // bottone chiudi
        chiudiButton = new JButton("Chiudi");
        chiudiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chiudiButton.setBackground(AppColor.BUTTON_BLUE);
        chiudiButton.setForeground(AppColor.FOREGROUND_WHITE);
        chiudiButton.setFocusPainted(false);
        chiudiButton.setBorderPainted(false);
        chiudiButton.setContentAreaFilled(true);
        chiudiButton.setOpaque(true);
        chiudiButton.addActionListener(e -> dispose());
        bottomPanel.add(chiudiButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    
    private void caricaRicette() 
    {
        // Carica ricette disponibili e associate tramite controller usando l'ID della sessione
        List<String> ricetteDisponibili = controller.getNomiRicetteDisponibiliPerSessioneSelezionata();
        List<String> ricetteAssociate = controller.getNomiRicetteAssociateASessioneSelezionata();
        
        // Popola le liste
        ricetteDisponibiliModel.clear();
        ricetteAssociateModel.clear();
        
        for (String nome : ricetteDisponibili) ricetteDisponibiliModel.addElement(nome);
        for (String nome : ricetteAssociate) ricetteAssociateModel.addElement(nome);
    }
     
    private void aggiungiRicetta() 
    {
        String selezionata = ricetteDisponibiliList.getSelectedValue();
        
        if (selezionata == null) 
        {
            JOptionPane.showMessageDialog(this, "Seleziona una ricetta da aggiungere", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean aggiunta = controller.associaRicettaASessioneSelezionataByNome(selezionata);
        if (aggiunta) 
        {
            // Sposta la ricetta dalla lista disponibili a quella associate
            ricetteDisponibiliModel.removeElement(selezionata);
            ricetteAssociateModel.addElement(selezionata);
            // Aggiorna la tabella nella pagina dettagli corso
            parentPage.aggiornaTabella();
        } else JOptionPane.showMessageDialog(this, "Errore nell'associazione della ricetta", "Errore", JOptionPane.ERROR_MESSAGE);
    }
    
    private void rimuoviRicetta() 
    {
        String selezionata = ricetteAssociateList.getSelectedValue();
        
        if (selezionata == null) 
        {
            JOptionPane.showMessageDialog(this, "Seleziona una ricetta da rimuovere", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean rimossa = controller.rimuoviRicettaDaSessioneByNome(selezionata);
        if (rimossa) 
        {
            // Sposta la ricetta dalla lista associate a quella disponibili
            ricetteAssociateModel.removeElement(selezionata);
            ricetteDisponibiliModel.addElement(selezionata);
            // Aggiorna la tabella nella pagina dettagli corso
            parentPage.aggiornaTabella();
        } else JOptionPane.showMessageDialog(this, "Errore nella rimozione della ricetta", "Errore", JOptionPane.ERROR_MESSAGE);
    }
}