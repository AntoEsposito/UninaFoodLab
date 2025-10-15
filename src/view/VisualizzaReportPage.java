package view;

import javax.swing.*;
import javax.swing.border.*;

import control.Controller;
import view.color.AppColor;

import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.*;
import java.time.YearMonth;


public class VisualizzaReportPage extends JPanel 
{
    private static final long serialVersionUID = 1L;
    private Controller controller;
    
    private JPanel topPanel;
    private JPanel reportPanel;
    private JPanel chartPanel;
    
    private JLabel titleLabel;
    private JLabel meseAnnoLabel;
    private JLabel numCorsiLabel;
    private JLabel numSessioniOnlineLabel;
    private JLabel numSessioniPraticheLabel;
    private JLabel mediaRicetteLabel;
    private JLabel maxRicetteLabel;
    private JLabel minRicetteLabel;
    
    private JComboBox<String> meseComboBox;
    private JComboBox<String> annoComboBox;
    
    private JButton generaReportButton;
    
    
    public VisualizzaReportPage(Controller controller) 
    {
        this.controller = controller;
        
        setForeground(AppColor.FOREGROUND_WHITE);
        setBackground(AppColor.BACKGROUND_GREY);
        setLayout(new BorderLayout(10, 10));
        
        initComponents();
    }
    
    
    private void initComponents() 
    {
        // panel superiore con titolo e selettori
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(AppColor.BACKGROUND_GREY);
        add(topPanel, BorderLayout.NORTH);
        
        // titolo
        titleLabel = new JLabel("REPORT MENSILE CORSI E RICETTE");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(AppColor.FOREGROUND_WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        
        
        // panel per selettori mese e anno
        JPanel selettoriPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        selettoriPanel.setBackground(AppColor.BACKGROUND_GREY);
        topPanel.add(selettoriPanel);
        
        // label per selezione mese e anno
        meseAnnoLabel = new JLabel("Seleziona periodo:");
        meseAnnoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meseAnnoLabel.setForeground(AppColor.FOREGROUND_WHITE);
        selettoriPanel.add(meseAnnoLabel);
        
        // combobox per selezione mese
        meseComboBox = new JComboBox<>(new String[]{
            "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
            "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
        });
        meseComboBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meseComboBox.setBackground(AppColor.BUTTON_BLUE);
        meseComboBox.setForeground(AppColor.FOREGROUND_WHITE);
        meseComboBox.setFocusable(false);
        meseComboBox.setSelectedIndex(YearMonth.now().getMonthValue() - 1);
        selettoriPanel.add(meseComboBox);
        
        // combobox per selezione anno
        annoComboBox = new JComboBox<>();
        int annoCorrente = YearMonth.now().getYear();
        for (int i = annoCorrente - 5; i <= annoCorrente; i++) annoComboBox.addItem(String.valueOf(i)); // ultimi 5 anni
        annoComboBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        annoComboBox.setBackground(AppColor.BUTTON_BLUE);
        annoComboBox.setForeground(AppColor.FOREGROUND_WHITE);
        annoComboBox.setFocusable(false);
        annoComboBox.setSelectedItem(String.valueOf(annoCorrente));
        selettoriPanel.add(annoComboBox);
        
        
        // bottone per generare il report
        generaReportButton = new JButton("Genera Report");
        generaReportButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        generaReportButton.setBackground(AppColor.BUTTON_BLUE);
        generaReportButton.setForeground(AppColor.FOREGROUND_WHITE);
        generaReportButton.setFocusPainted(false);
        generaReportButton.setBorderPainted(false);
        generaReportButton.setContentAreaFilled(true);
        generaReportButton.setOpaque(true);
        generaReportButton.addActionListener(e -> generaReport());
        selettoriPanel.add(generaReportButton);
        
        
        // panel centrale per i dati del report
        reportPanel = new JPanel();
        reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));
        reportPanel.setBackground(AppColor.BACKGROUND_GREY);
        reportPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        add(reportPanel, BorderLayout.SOUTH);
        
        // labels per i dati del report
        numCorsiLabel = creaLabelBianca("Numero totale di corsi tenuti: -");
        numSessioniOnlineLabel = creaLabelBianca("Numero di sessioni online: -");
        numSessioniPraticheLabel = creaLabelBianca("Numero di sessioni pratiche: -");
        mediaRicetteLabel = creaLabelBianca("Numero medio di ricette realizzate (solo sessioni pratiche): -");
        maxRicetteLabel = creaLabelBianca("Numero massimo di ricette realizzate: -");
        minRicetteLabel = creaLabelBianca("Numero minimo di ricette realizzate: -");
        reportPanel.add(numCorsiLabel);
        reportPanel.add(numSessioniOnlineLabel);
        reportPanel.add(numSessioniPraticheLabel);
        reportPanel.add(mediaRicetteLabel);
        reportPanel.add(maxRicetteLabel);
        reportPanel.add(minRicetteLabel);
        
        
        // panel per il grafico (inizialmente vuoto)
        chartPanel = new JPanel();
        chartPanel.setBackground(AppColor.BACKGROUND_GREY);
        chartPanel.setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
    }
    
    
    private JLabel creaLabelBianca(String text) 
    {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(AppColor.FOREGROUND_WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private void resetLabels()
    {
        numCorsiLabel.setText("Numero totale di corsi tenuti: -");
        numSessioniOnlineLabel.setText("Numero di sessioni online: -");
        numSessioniPraticheLabel.setText("Numero di sessioni pratiche: -");
        mediaRicetteLabel.setText("Numero medio di ricette realizzate: -");
        maxRicetteLabel.setText("Numero massimo di ricette realizzate: -");
        minRicetteLabel.setText("Numero minimo di ricette realizzate: -");
        chartPanel.removeAll();
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private void generaReport() 
    {
        int meseSelezionato = meseComboBox.getSelectedIndex() + 1;
        int annoSelezionato = Integer.parseInt((String) annoComboBox.getSelectedItem());
        
        // Recupera i dati dal controller
        Object[] datiReport = controller.generaDatiReportMensile(meseSelezionato, annoSelezionato);
        
        if (datiReport == null || ((int)datiReport[0]) == 0)
        {
            JOptionPane.showMessageDialog(this, "Nessun corso trovato per il periodo selezionato", "Informazione", JOptionPane.INFORMATION_MESSAGE);
            
            resetLabels();
            return;
        }
        
        int numCorsi = (int) datiReport[0];
        int numSessioniOnline = (int) datiReport[1];
        int numSessioniPratiche = (int) datiReport[2];
        double mediaRicette = (float) datiReport[3];
        int maxRicette = (int) datiReport[4];
        int minRicette = (int) datiReport[5];
        
        // Aggiorna le labels
        numCorsiLabel.setText("Numero totale di corsi tenuti: " + numCorsi);
        numSessioniOnlineLabel.setText("Numero di sessioni online: " + numSessioniOnline);
        numSessioniPraticheLabel.setText("Numero di sessioni pratiche: " + numSessioniPratiche);
        
        if (numSessioniPratiche > 0) 
        {
            mediaRicetteLabel.setText(String.format("Numero medio di ricette realizzate: %.2f", mediaRicette));
            maxRicetteLabel.setText("Numero massimo di ricette realizzate: " + maxRicette);
            minRicetteLabel.setText("Numero minimo di ricette realizzate: " + minRicette);
        } 
        else 
        {
            mediaRicetteLabel.setText("Numero medio di ricette realizzate: -");
            maxRicetteLabel.setText("Numero massimo di ricette realizzate: -");
            minRicetteLabel.setText("Numero minimo di ricette realizzate: -");
        }
        
        // Genera il grafico
        generaGrafico(numCorsi, numSessioniOnline, numSessioniPratiche, mediaRicette, maxRicette, minRicette);
    }
    
    
    private void generaGrafico(int numCorsi, int numSessioniOnline, int numSessioniPratiche, double mediaRicette, int maxRicette, int minRicette) 
    {
        // Crea il dataset per il grafico a barre
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        dataset.addValue(numCorsi, "Dati", "Corsi Totali");
        dataset.addValue(numSessioniOnline, "Dati", "Sessioni Online");
        dataset.addValue(numSessioniPratiche, "Dati", "Sessioni Pratiche");
        
        if (numSessioniPratiche > 0) 
        {
            dataset.addValue(mediaRicette, "Dati", "Media Ricette/Sessione");
            dataset.addValue(maxRicette, "Dati", "Max Ricette/Sessione");
            dataset.addValue(minRicette, "Dati", "Min Ricette/Sessione");
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Statistiche Attività del Periodo",
            "",  // Nessuna etichetta sull'asse X, le etichette delle barre sono auto-descrittive
            "Quantità",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,   // Tooltips
            false
        );
        
        // personalizza l'aspetto del grafico
        chart.setBackgroundPaint(AppColor.BACKGROUND_GREY);
        chart.getPlot().setBackgroundPaint(AppColor.BACKGROUND_LIGHT_GREY);
        
        // personalizza il titolo
        chart.getTitle().setPaint(AppColor.FOREGROUND_WHITE);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        // personalizza gli assi
        CategoryPlot plot = chart.getCategoryPlot();
        
        // asse X (categorie)
        plot.getDomainAxis().setTickLabelPaint(AppColor.FOREGROUND_WHITE);
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14));
      
        // asse Y (valori)
        plot.getRangeAxis().setLabelPaint(AppColor.FOREGROUND_WHITE);
        plot.getRangeAxis().setTickLabelPaint(AppColor.FOREGROUND_WHITE);
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        plot.getRenderer().setSeriesPaint(0, AppColor.BUTTON_BLUE); // Blu per tutte le barre
        
        
        // crea il panel del grafico
        ChartPanel graficoPanel = new ChartPanel(chart);
        graficoPanel.setBackground(AppColor.BACKGROUND_GREY);
        
        // aggiorna il panel del grafico
        chartPanel.removeAll();
        chartPanel.add(graficoPanel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
}