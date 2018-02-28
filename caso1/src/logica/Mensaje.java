package logica;

public class Mensaje {
	private int idCliente;
	private int consulta;
	private int respuesta;
	
	public Mensaje(int id, int consulta) {
		this.idCliente = id;
		this.consulta = consulta;
		this.respuesta = -1;
	}
	public int getConsulta() {
		return consulta;
	}
	public void setConsulta(int consulta) {
		this.consulta = consulta;
	}
	public int getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(int respuesta) {
		this.respuesta = respuesta;
	}
	
	
}
