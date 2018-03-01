package logica;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Servidor extends Thread{

	private Buffer buffer;
	
	public Servidor(Buffer pBuffer) {
		buffer = pBuffer;
	}
	
	@Override
	public void run() {
		while (buffer.hayMensajes()) {
			Mensaje m = buffer.desencolar();
			m.setRespuesta(m.getConsulta() + 1);
			buffer.encolar(m);
		}
	}
	
	public static void main(String[] args) {
		Buffer buffer =  new Buffer(20);
		
		ArrayList info;
		info = cargarInfo("./data/data.properties");
		int numClientes = (int) info.get(0);
		int[] numMensajesClientes = (int[]) info.get(1);
		int numServidores = (int) info.get(2);
		
		Cliente[] clientes = new Cliente[numClientes];
		Servidor[] servidores = new Servidor[numServidores];
		
		for (int i = 0; i < numClientes; i++) {
			clientes[i] = new Cliente(buffer, i + 1, numMensajesClientes[i]);
			clientes[i].start();
		}
		
		for (int i = 0; i < numServidores; i++) {
			servidores[i] = new Servidor(buffer);
			servidores[i].start();
		}
	}

	private static ArrayList cargarInfo( String pRutaArchivo ) 
    {
		ArrayList info = new ArrayList();
        Properties datos = new Properties( );
        try
        {
            FileInputStream in = new FileInputStream( pRutaArchivo );
            datos.load( in );
            in.close( );
            String strNumClientes = datos.getProperty( "clientes" );
            info.add(Integer.parseInt( strNumClientes ));
            
            int[] msnClientes = new int[(int) info.get(0)];
            for (int i = 0; i < msnClientes.length; i++) {
            	String strNumMensajes = datos.getProperty( "cliente" + (i + 1) );
                msnClientes[i] = Integer.parseInt( strNumMensajes );
            }

            String strServidores = datos.getProperty( "servidores" );
            info.add(msnClientes);
            info.add(Integer.parseInt( strServidores ));
        }
        catch( Exception e ) {}
        return info;
    }
}
