package logica;

public class Cliente extends Thread {
	private Buffer buffer;
	private int id;
	private int numMensajes;
	
	public Cliente(Buffer pBuffer, int pId, int pNumMensajes) {
		buffer = pBuffer;
		id = pId;
		numMensajes = pNumMensajes;
	}
	
	public void run() {
		for (int i = 0; i < numMensajes; i++) {
			int n = (int) (Math.random()*10);
			Mensaje m = new Mensaje(id, n);
			System.out.println("Mensaje cliente " + id+": " + n);
			while(!buffer.encolar(m)) {	
				yield();
			};
			int r = buffer.desencolarRespuesta().getRespuesta();
			System.out.println("Respuesta cliente " + id+": " + r);
		}
	}
}
