package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
private List <Esame>partenza;
private Set<Esame>soluzioneMigliore;
private double mediaSoluzione;
	public  Model(){
		EsameDAO dao=new EsameDAO();
		this.partenza=dao.getTuttiEsami();
	}
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame>parziale =new HashSet<Esame>();
		soluzioneMigliore=new HashSet<Esame>();
		mediaSoluzione=0;
		cerca2(parziale,0,numeroCrediti);
		return soluzioneMigliore;	
	}

	
	private void cerca(Set<Esame> parziale, int L, int m) {
		//casi terminali 
		
		int crediti =sommaCrediti(parziale);
		if(crediti>m)
			return;
		if(crediti==m) {
			double media=calcolaMedia(parziale);
			if(media>mediaSoluzione) {
				soluzioneMigliore=new HashSet<>(parziale);
				mediaSoluzione=media;
			}
			return;	
		}
		//sicuramente crediti <m
		 //raggiungiamo la fine 
		 //1 L=N non ci sono più esami da aggiungere
		if(L==partenza.size())
			return;
		
		//generale i sotto problemi
		for(Esame e:partenza) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale,L+1,m);
				parziale.remove(e);
			}
			
		}
		
		
	}
	
	
	public void cerca2(Set<Esame> parziale, int L, int m) {
		//I CASI TERMINALI SONO GLI STESSI
		//casi terminali 
		
				int crediti =sommaCrediti(parziale);
				if(crediti>m)
					return;
				if(crediti==m) {
					double media=calcolaMedia(parziale);
					if(media>mediaSoluzione) {
						soluzioneMigliore=new HashSet<>(parziale);
						mediaSoluzione=media;
					}
					return;	
				}
				//sicuramente crediti <m
				 //raggiungiamo la fine 
				 //1 L=N non ci sono più esami da aggiungere
				if(L==partenza.size())
					return;
		 //GENERAZIONE SOTTOPROBLEMA
				parziale.add(partenza.get(L));
				cerca2(parziale,L+1,m);
				parziale.remove(partenza.get(L));
				cerca2(parziale,L+1,m);
	}
	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
