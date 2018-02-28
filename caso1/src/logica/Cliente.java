package logica;

public class Cliente extends Thread {
	private Buffer buffer;
	private int id;
	
	public Cliente(Buffer pBuffer, int id) {
		buffer = pBuffer;
		this.id = id;
	}
	
	public void run() {
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
