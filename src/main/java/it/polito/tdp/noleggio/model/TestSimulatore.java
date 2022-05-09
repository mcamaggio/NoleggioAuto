package it.polito.tdp.noleggio.model;

public class TestSimulatore {

	public static void main(String[] args) {
		Simulatore sim = new Simulatore(14);
		
		sim.caricaEventi();
		sim.run();
		
		System.out.println("Clienti: " +sim.getnClientiTot()+ " di cui "
				+sim.getnClientiInsoddisfatti()+ " insoddisfatti\n");

	}

}
