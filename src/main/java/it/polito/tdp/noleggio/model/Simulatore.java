package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;

import it.polito.tdp.noleggio.model.Event.EventType;

public class Simulatore {
	
	// Parametri di ingresso 
	private int NC;
	private Duration T_IN = Duration.ofMinutes(10);
	private Duration T_TRAVEL = Duration.ofHours(1); // 1, 2, o 3 volte tanto
	
	// Valori calcolati in uscita
	private int nClientiTot;
	private int nClientiInsoddisfatti;
	
	// Stato del mondo
	private int autoDisonibili;
	
	// Coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulatore(int NC) {
		this.NC = NC;
		this.queue = new PriorityQueue<>();
		this.autoDisonibili = NC;
	}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	public void caricaEventi() {
		LocalTime ora = LocalTime.of(8, 0);
		while(ora.isBefore(LocalTime.of(16, 0))) {
			this.queue.add(new Event(ora, EventType.NUOVO_CLIENTE));
			ora = ora.plus(T_IN);
		}
	}
	

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		case NUOVO_CLIENTE:
			this.nClientiTot++;
			if(this.autoDisonibili>0) {
				this.autoDisonibili--;
				int ore = (int)(Math.random()*3)+1;
				LocalTime oraRientro = e.getTime().plus(Duration.ofHours(ore * T_TRAVEL.toHours()));
				this.queue.add(new Event(oraRientro, EventType.AUTO_RESTITUITA));
			} else {
				this.nClientiInsoddisfatti++;
			}
			break;
		case AUTO_RESTITUITA:
			this.autoDisonibili++;
			break;
		}
		
	}

	public int getnClientiTot() {
		return nClientiTot;
	}

	public int getnClientiInsoddisfatti() {
		return nClientiInsoddisfatti;
	}

	public void setNC(int nC) {
		NC = nC;
	}

	public void setT_INC(Duration t_INC) {
		T_IN = t_INC;
	}

	public void setT_TRAVEL(Duration t_TRAVEL) {
		T_TRAVEL = t_TRAVEL;
	}
	
	
	
}
