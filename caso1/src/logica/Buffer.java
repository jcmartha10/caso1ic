package logica;

import java.util.ArrayList;

public class Buffer {

	private ArrayList<Mensaje> mensajes;
	private ArrayList<Mensaje> respuestas;
	private int tamanio;
	private Object lleno;
	private Object vacio;
	
	public Buffer(int pTamanio) {
		tamanio = pTamanio;
		mensajes = new ArrayList<Mensaje>();
		lleno = new Object();
		vacio = new Object();
	}
	
	public boolean encolar(Mensaje m) {
		synchronized (mensajes) {
			if(mensajes.size() == tamanio) {
					return false;
			}
		}
		
		synchronized(this){mensajes.add(m);}
		synchronized(vacio) {vacio.notify();}
		synchronized(m) {
			try {
				m.wait();
			} catch (InterruptedException e) {}
		}
		
		return true;
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
	
	public void encolarRespuesta(Mensaje m) {
		synchronized(respuestas) {
			if(respuestas.size() == tamanio) {
				try {
					lleno.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			synchronized(this){respuestas.add(m);}
			synchronized(m) {m.notify();}{
			}
		}
	}
	
	public Mensaje desencolarRespuesta() {
		synchronized(respuestas){
			return respuestas.remove(0);
		}
	}

	public boolean hayMensajes() {
		return mensajes.size() != 0;
	}
}
