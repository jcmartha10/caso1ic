package logica;

import java.util.ArrayList;

public class Buffer {

	private ArrayList<Mensaje> mensajes;
	private int tamanio;
	private Object lleno;
	private Object vacio;
	
	public Buffer(int pTamanio) {
		tamanio = pTamanio;
		mensajes = new ArrayList<Mensaje>();
		lleno = new Object();
		vacio = new Object();
	}
	
	public void encolar(Mensaje m) {
		synchronized (lleno) {
			while (mensajes.size() == tamanio) {
				try {
					lleno.wait();
				} catch (InterruptedException e) {}
			}
		}
		
		synchronized(this){mensajes.add(m);}
		synchronized(vacio) {vacio.notify();}
	}
	
	public Mensaje desencolar() {
		synchronized (vacio) {
			while (mensajes.size() == 0) {
				try {
					vacio.wait();
				} catch (InterruptedException e) {}
			}
		}
		
		Mensaje temp;
		synchronized(this){temp = mensajes.remove(0);}
		synchronized(lleno) {lleno.notify();}
		return temp;
	}
}
