package control;

import model.dao.*;
import model.entity.*;
import view.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class Controller 
{
    // DAO
    private ChefDAO chefDAO;
    private CorsoDAO corsoDAO;
    private SessioneDAO sessioneDAO;
    private CategoriaDAO categoriaDAO;
    private FrequenzaSessioniDAO frequenzaSessioniDAO;
    private RicettaDAO ricettaDAO;
    private RealizzazioneRicettaDAO realizzazioneRicettaDAO;
    
    private Chef chefAutenticato; // chef attualmente loggato
    private Corso corsoSelezionato; // per tenere traccia del corso selezionato in VisualizzaCorsiPage
    
    
    public Controller() 
    {
        this.chefDAO = new ChefDAO();
        this.corsoDAO = new CorsoDAO();
        this.sessioneDAO = new SessioneDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.frequenzaSessioniDAO = new FrequenzaSessioniDAO();
        this.ricettaDAO = new RicettaDAO();
        this.realizzazioneRicettaDAO = new RealizzazioneRicettaDAO();
        this.chefAutenticato = null;
        this.corsoSelezionato = null;
    }
    
    

    // METODI PER LoginPage
    // Metodi pubblici
    public boolean autenticaChef(String username, String password) 
    {
        Chef chef = chefDAO.getByUsernameAndPassword(username, password);
        if (chef != null) 
        {
            this.chefAutenticato = chef;
            JOptionPane.showMessageDialog(null, "Login effettuato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            setVisibleMainPage();
            return true;
        }
        JOptionPane.showMessageDialog(null, "Username o Password errati", "Errore di autenticazione", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    
    // METODI PER MainPage
    // Metodi pubblici
    public String getNomeChefAutenticato() 
	{
		if (chefAutenticato != null)
		return chefAutenticato.getNome() + " " + chefAutenticato.getCognome();
		return "";
	}
    
    public JPanel creaNuovoCorsoPage() 
	{
		return new NuovoCorsoPage(this);
	}
    
    public JPanel creaVisualizzaCorsiPage() 
	{
		return new VisualizzaCorsiPage(this);
	}
    
    public JPanel creaVisualizzaReportPage() 
	{
		return new VisualizzaReportPage(this);
	}
    
    public void logout() 
    {
        this.chefAutenticato = null;
    }
    
    public void setVisibleLoginPage() 
	{
		new LoginPage(this).setVisible(true);
	}
    
    // Metodi privati
    private void setVisibleMainPage()
    {
    	new MainPage(this).setVisible(true);
    }
    
    
    // METODI PER NuovoCorsoPage
    // Metodi pubblici
    public void caricaCategorieInComboBox(JComboBox<String> comboBox)
    {
        comboBox.removeAllItems();
        List<Categoria> categorie = getAllCategorie();
        
        for (Categoria categoria : categorie) comboBox.addItem(categoria.getDescrizione());
    }
    
    public void caricaFrequenzeInComboBox(JComboBox<String> comboBox)
    {
        comboBox.removeAllItems();
        List<FrequenzaSessioni> frequenze = getAllFrequenze();
        
        for (FrequenzaSessioni frequenza : frequenze) comboBox.addItem(frequenza.getDescrizione());
    }
    
    public boolean creaCorsoByIndex(LocalDate dataInizio, int numeroSessioni, int frequenzaIndex, int categoriaIndex)
    {
        FrequenzaSessioni frequenza = getFrequenzaByIndex(frequenzaIndex);
        Categoria categoria = getCategoriaByIndex(categoriaIndex);
        
        if (frequenza == null || categoria == null) return false;
        return creaCorso(dataInizio, numeroSessioni, frequenza, categoria);
    }
    
    public boolean creaSessioniPerUltimoCorso(LocalDate dataInizio, int frequenzaIndex, boolean[] modalitaInPresenza)
    {
        Corso corso = getUltimoCorsoCreato();
        if (corso == null) return false;
        
        int giorniIntervallo = calcolaGiorniIntervalloByIndex(frequenzaIndex);
        LocalDate dataSessione = dataInizio;
        int idCorso = getIdCorso(corso);
        
        for (int i = 0; i < modalitaInPresenza.length; i++) 
        {
            boolean inPresenza = modalitaInPresenza[i];
            int numeroSessione = i + 1;
            String urlMeeting = inPresenza ? null : "https://meet.uninafoodlab/demo.com/" + idCorso + "/" + numeroSessione;
            
            boolean aggiunta = aggiungiSessione(corso, inPresenza, dataSessione, numeroSessione, urlMeeting);
            if (!aggiunta) return false;
            
            dataSessione = dataSessione.plusDays(giorniIntervallo);
        }
        
        return true;
    }
    
    // Metodi privati
    private boolean creaCorso(LocalDate dataInizio, int numeroSessioni, FrequenzaSessioni frequenza, Categoria categoria) 
    {
        if (chefAutenticato == null) return false;
        
        Corso nuovoCorso = new Corso(dataInizio, numeroSessioni, frequenza, categoria, chefAutenticato, new ArrayList<>());
        return corsoDAO.addCorso(nuovoCorso);
    }
    
    private Corso getUltimoCorsoCreato()
    {
        List<Corso> corsi = getCorsiChefAutenticato();
        if (!corsi.isEmpty()) return corsi.get(corsi.size() - 1);
        return null;
    }
    
    private int getIdCorso(Corso corso)
    {
        return corso != null ? corso.getId() : -1;
    }
    
    private int calcolaGiorniIntervalloByIndex(int frequenzaIndex)
    {
        FrequenzaSessioni frequenza = getFrequenzaByIndex(frequenzaIndex);
        return calcolaGiorniIntervallo(frequenza);
    }
    
    private int calcolaGiorniIntervallo(FrequenzaSessioni frequenza)
    {
        if (frequenza == null) return 7; // improbabile, default a settimanale
        
        String desc = frequenza.getDescrizione().toLowerCase();
        
        if (desc.contains("giornaliero")) return 1;
        if (desc.contains("ogni due giorni")) return 2;
        if (desc.contains("settimanale")) return 7;
        if (desc.contains("ogni 2 settimane")) return 14;
        if (desc.contains("mensile")) return 30;
        
        return 7;
    }
    
    private boolean aggiungiSessione(Corso corso, boolean inPresenza, LocalDate data, int numeroSessione, String urlMeeting) 
    {
        Sessione nuovaSessione = new Sessione(inPresenza, data, numeroSessione, urlMeeting, corso);
        return sessioneDAO.addSessione(nuovaSessione);
    }
    
    
    // METODI PER VisualizzaCorsiPage
    // Metodi pubblici
    public void caricaCategorieConFiltroInComboBox(JComboBox<String> comboBox)
    {
        comboBox.removeAllItems();
        comboBox.addItem("Tutte le categorie");
        List<Categoria> categorie = getAllCategorie();
        
        for (Categoria categoria : categorie) comboBox.addItem(categoria.getDescrizione());
    }
    
    public void caricaCorsiInTabella(DefaultTableModel tableModel, int indiceCategoria) 
    {
        tableModel.setRowCount(0);
        List<Corso> corsi;
        
        if (indiceCategoria == 0) corsi = getCorsiChefAutenticato(); // tutte le categorie
        else 
        {
            // sottrai 1 perché il primo elemento è "Tutte le categorie"
            Categoria categoria = getCategoriaByIndice(indiceCategoria - 1);
            if (categoria == null) corsi = getCorsiChefAutenticato();
            else corsi = getCorsiChefAutenticatoPerCategoria(categoria);
        }
        
        for (Corso corso : corsi) 
        {
            Object[] riga = 
            {
                corso.getId(),
                corso.getCategoria().getDescrizione(),
                corso.getDataInizio(),
                corso.getNumeroSessioni(),
                corso.getFrequenzaSessioni().getDescrizione()
            };
            tableModel.addRow(riga);
        }
    }
    
    public void mostraPaginaDettagliCorso(JPanel parent, int idCorso) 
	{
		corsoSelezionato = getCorsoById(idCorso);
        if (corsoSelezionato != null) 
        {
            DettagliCorsoPage pagina = new DettagliCorsoPage(SwingUtilities.getWindowAncestor(parent), this);
            pagina.setVisible(true);
        }
	}
    
    // Metodi privati
    private Categoria getCategoriaByIndice(int indice)
	{
		List<Categoria> categorie = getAllCategorie();
		if (indice >= 0 && indice < categorie.size()) return categorie.get(indice);
		else return null;
	}
    
    private List<Corso> getCorsiChefAutenticatoPerCategoria(Categoria categoria) 
    {
        if (chefAutenticato == null) return new ArrayList<>();
        return corsoDAO.getByChefAndCategoria(chefAutenticato, categoria);
    }
    
    
    // METODI PER DettagliCorsoPage 
    // Metodi pubblici
    public void caricaSessioniInDettagliCorsoPage(DefaultTableModel sessioniTableModel) 
    {
        sessioniTableModel.setRowCount(0);
        List<Sessione> sessioni = getSessioniCorso(corsoSelezionato);
        
        for (Sessione sessione : sessioni) 
        {
            String modalita = sessione.isInPresenza() ? "In Presenza" : "Online";
            String url = sessione.getUrlMeeting() == null ? "N/A" : sessione.getUrlMeeting();
            
            List<Ricetta> ricette = getRicetteAssociateASessione(sessione);
            String numRicetteString = ricette.size() + " ricetta/e";
            
            Object[] riga = 
            {
                sessione.getNumeroSessione(),
                sessione.getData(),
                modalita,
                url,
                numRicetteString
            };
            sessioniTableModel.addRow(riga);
        }
    }
    
    public int getIdCorsoSelezionato() 
	{
		if (corsoSelezionato != null) return corsoSelezionato.getId();
		return -1;
	}
    
    public String getCategoriaCorsoSelezionato()
    {
    	if (corsoSelezionato != null) return corsoSelezionato.getCategoria().getDescrizione();
		return "";
    }
    
    public LocalDate getDataInizioCorsoSelezionato()
	{
		if (corsoSelezionato != null) return corsoSelezionato.getDataInizio();
		return null;
	}

	public int getNumeroSessioniCorsoSelezionato()
	{
		if (corsoSelezionato != null) return corsoSelezionato.getNumeroSessioni();
		return -1;
	}
	
	public String getFrequenzaSessioniCorsoSelezionato()
	{
		if (corsoSelezionato != null) return corsoSelezionato.getFrequenzaSessioni().getDescrizione();
		return "";
	}
    
    public void apriAssociaRicetteDialog(DettagliCorsoPage paginaDettagliCorso, int selectedRow) 
	{
	    List<Sessione> sessioni = getSessioniCorso(corsoSelezionato);
	    Sessione sessioneSelezionata = sessioni.get(selectedRow);
	    
	    if (sessioneSelezionata.isInPresenza())
	    {
		    int sessioneId = sessioneSelezionata.getId();
		    AssociaRicettePage dialog = new AssociaRicettePage(SwingUtilities.getWindowAncestor(paginaDettagliCorso), this, sessioneId);
		    dialog.setVisible(true);
	    }
	    else JOptionPane.showMessageDialog(paginaDettagliCorso, "Non è possibile associare ricette a sessioni online.", "Operazione non consentita", JOptionPane.WARNING_MESSAGE);
	}
    
    // Metodi privati
    private List<Sessione> getSessioniCorso(Corso corso) 
    {
        return sessioneDAO.getByCorso(corso);
    }
    
    
    // METODI PER AssociaRicettePage
    // Metodi pubblici
    public String getInfoSessioneById(int sessioneId)
    {
        Sessione sessione = getSessioneById(sessioneId);
        return getInfoSessione(sessione);
    }
    
    public List<String> getNomiRicetteDisponibiliPerSessione(int sessioneId)
    {
        Sessione sessione = getSessioneById(sessioneId);
        if (sessione == null) return new ArrayList<>();
        
        List<Ricetta> disponibili = getRicetteDisponibiliPerSessione(sessione);
        List<String> nomi = new ArrayList<>();
        
        for (Ricetta r : disponibili) nomi.add(r.getNome());
        return nomi;
    }
    
    public List<String> getNomiRicetteAssociateASessione(int sessioneId)
    {
        Sessione sessione = getSessioneById(sessioneId);
        if (sessione == null) return new ArrayList<>();
        
        List<Ricetta> associate = getRicetteAssociateASessione(sessione);
        List<String> nomi = new ArrayList<>();
        
        for (Ricetta r : associate) nomi.add(r.getNome());
        return nomi;
    }
    
    public boolean associaRicetteASessioneByNomi(int sessioneId, List<String> nomiRicette)
    {
        Sessione sessione = getSessioneById(sessioneId);
        if (sessione == null) return false;
        
        List<Ricetta> tutteRicette = getAllRicette();
        boolean tutteAssociate = true;
        
        for (String nome : nomiRicette) 
        {
            Ricetta ricettaDaAssociare = null;
            for (Ricetta r : tutteRicette) 
            {
                if (r.getNome().equals(nome)) 
                {
                    ricettaDaAssociare = r;
                    break;
                }
            }
            if (ricettaDaAssociare != null) 
            {
                if (!associaRicettaASessione(sessione, ricettaDaAssociare)) tutteAssociate = false;
            }
        }
        
        return tutteAssociate;
    }
    
    public boolean associaRicettaASessioneByNome(int sessioneId, String nomeRicetta)
    {
        Sessione sessione = getSessioneById(sessioneId);
        if (sessione == null) return false;
        
        List<Ricetta> tutteRicette = getAllRicette();
        Ricetta ricettaDaAssociare = null;
        
        for (Ricetta r : tutteRicette) 
        {
            if (r.getNome().equals(nomeRicetta)) 
            {
                ricettaDaAssociare = r;
                break;
            }
        }
        
        if (ricettaDaAssociare != null) return associaRicettaASessione(sessione, ricettaDaAssociare);
        
        return false;
    }
    
    public boolean rimuoviRicettaDaSessioneByNome(int sessioneId, String nomeRicetta)
    {
        Sessione sessione = getSessioneById(sessioneId);
        if (sessione == null) return false;
        
        List<Ricetta> tutteRicette = getAllRicette();
        Ricetta ricettaDaRimuovere = null;
        
        for (Ricetta r : tutteRicette) 
        {
            if (r.getNome().equals(nomeRicetta)) 
            {
                ricettaDaRimuovere = r;
                break;
            }
        }
        
        if (ricettaDaRimuovere != null) {
            return rimuoviRicettaDaSessione(sessione, ricettaDaRimuovere);
        }
        
        return false;
    }
    
    // Metodi privati
    private Sessione getSessioneById(int id)
    {
        return sessioneDAO.getById(id);
    }
    
    private String getInfoSessione(Sessione sessione)
    {
        if (sessione == null) return "";
        return "Sessione N° " + sessione.getNumeroSessione() + " - Data: " + sessione.getData();
    }
    
    private List<Ricetta> getRicetteDisponibiliPerSessione(Sessione sessione)
    {
        List<Ricetta> tutteRicette = getAllRicette();
        List<Ricetta> ricetteAssociate = getRicetteAssociateASessione(sessione);
        List<Ricetta> disponibili = new ArrayList<>();
        
        for (Ricetta r : tutteRicette) 
        {
            boolean trovata = false;
            for (Ricetta ra : ricetteAssociate) 
            {
                if (r.getId() == ra.getId()) 
                {
                    trovata = true;
                    break;
                }
            }
            if (!trovata) disponibili.add(r);
        }
        return disponibili;
    }
    
    private boolean associaRicettaASessione(Sessione sessione, Ricetta ricetta) 
    {
        return realizzazioneRicettaDAO.associaRicettaASessione(sessione, ricetta);
    }
    
    private boolean rimuoviRicettaDaSessione(Sessione sessione, Ricetta ricetta) 
	{
		return realizzazioneRicettaDAO.rimuoviRicettaDaSessione(sessione, ricetta);
	}
    
    
    // METODI PER VisualizzaReportPage
    // Metodi pubblici
    public Object[] generaDatiReportMensile(int mese, int anno) 
    {
        if (chefAutenticato == null) return null;
        
        // Recupera tutti i corsi dello chef autenticato
        List<Corso> tuttiCorsi = getCorsiChefAutenticato();
        List<Corso> corsiDelMese = new ArrayList<>();
        
        // Filtra i corsi che iniziano nel mese/anno selezionato
        for (Corso corso : tuttiCorsi) 
        {
            LocalDate dataInizio = corso.getDataInizio();
            if (dataInizio.getYear() == anno && dataInizio.getMonthValue() == mese) corsiDelMese.add(corso);
        }
        if (corsiDelMese.isEmpty()) return new Object[]{0, 0, 0, 0, 0, 0};
        
        int numCorsi = corsiDelMese.size();
        int numSessioniOnline = 0;
        int numSessioniPratiche = 0;
        int totaleRicette = 0;
        int maxRicette = 0;
        int minRicette = Integer.MAX_VALUE;
        
        // Analizza le sessioni di ogni corso
        for (Corso corso : corsiDelMese) 
        {
            List<Sessione> sessioni = getSessioniPerCorsoMeseEdAnno(corso, mese, anno);
            
            for (Sessione sessione : sessioni) 
            {
                if (sessione.isInPresenza()) 
                {
                    numSessioniPratiche++;
                    
                    // Conta le ricette realizzate in questa sessione pratica
                    List<Ricetta> ricette = getRicetteAssociateASessione(sessione);
                    int numRicette = ricette.size();
                    
                    totaleRicette += numRicette;
                    
                    if (numRicette > maxRicette) maxRicette = numRicette;
                    if (numRicette < minRicette) minRicette = numRicette;
                }
                else numSessioniOnline++;
            }
        }
        
        float mediaRicette = 0;
        if (numSessioniPratiche > 0) mediaRicette = (float) (totaleRicette) / numSessioniPratiche;
        
        // Se non ci sono sessioni pratiche, resetta i valori min/max
        if (numSessioniPratiche == 0) 
        {
            minRicette = 0;
            maxRicette = 0;
        }

        return new Object[]{numCorsi, numSessioniOnline, numSessioniPratiche, mediaRicette, maxRicette, minRicette};
    }
    
    // Metodi privati per VisualizzaReportPage
    public List<Sessione> getSessioniPerCorsoMeseEdAnno(Corso corso, int mese, int anno) 
	{
		List<Sessione> sessioniCorso = getSessioniCorso(corso);
		List<Sessione> sessioniFiltrate = new ArrayList<>();
		
		for (Sessione sessione : sessioniCorso) 
		{
			LocalDate data = sessione.getData();
			if (data.getYear() == anno && data.getMonthValue() == mese) sessioniFiltrate.add(sessione);
		}
		
		return sessioniFiltrate;
	}
    
    
    // METODI PRIVATI CONDIVISI - GESTIONE ENTITÀ 
    // Gestione Categorie
    private List<Categoria> getAllCategorie() 
    {
        return categoriaDAO.getAll();
    }
    
    private Categoria getCategoriaByIndex(int index)
    {
        List<Categoria> categorie = getAllCategorie();
        if (index >= 0 && index < categorie.size()) return categorie.get(index);
        return null;
    }
    
    // Gestione Frequenze
    private List<FrequenzaSessioni> getAllFrequenze() 
    {
        return frequenzaSessioniDAO.getAll();
    }
    
    private FrequenzaSessioni getFrequenzaByIndex(int index)
    {
        List<FrequenzaSessioni> frequenze = getAllFrequenze();
        if (index >= 0 && index < frequenze.size()) return frequenze.get(index);
        return null;
    }
    
    // Gestione Corsi
    private Corso getCorsoById(int id) 
	{
		return corsoDAO.getById(id);
	}
    
    private List<Corso> getCorsiChefAutenticato() 
    {
        if (chefAutenticato == null) return new ArrayList<>();
        return corsoDAO.getByChef(chefAutenticato);
    }
    
    // Gestione Ricette
    private List<Ricetta> getAllRicette() 
    {
        return ricettaDAO.getAll();
    }
    
    private List<Ricetta> getRicetteAssociateASessione(Sessione sessione)
    {
        return ricettaDAO.getBySessione(sessione);
    }
}
