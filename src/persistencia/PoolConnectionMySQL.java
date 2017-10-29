package persistencia;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;


public class PoolConnectionMySQL
{
	private Vector <Connection> connections = new Vector<Connection>();
	protected String setDriver;
	protected String classpath;
	protected String jdbc;
	protected String servidor;
	protected String usuario;
	protected String password;
	protected int cantCon;
	private static PoolConnectionMySQL pool;
	private PoolConnectionMySQL()
	{
		getConfiguration();
		for (int i= 0; i< cantCon;i++)
			connections.add(connect());
	}
	
	public static PoolConnectionMySQL getPoolConnection()
	{
		if (pool== null)
			pool =new PoolConnectionMySQL();
		return pool;
	}
	
	private Connection connect()
	{
		try
		{
//			Class.forName("com.mysql.jdbc.Driver");
			Class.forName(classpath);
			
			String dbConnectString = jdbc + servidor;
			Connection con = DriverManager.getConnection (dbConnectString, usuario, password);
            
            return con;
		}
		catch (SQLException e)
		{
			System.out.println("Mensaje Error: " + e.getMessage());
			System.out.println("Stack Trace: " + e.getStackTrace());
			return null;
		}
		catch (Exception ex)
		{
			System.out.println("Mensaje Error: " + ex.getMessage());
			System.out.println("Stack Trace: " + ex.getStackTrace());
			return null;
		}
	}
	
	public void getConfiguration()
	{
		String configuracion = "ConfigBD.txt";
	    Properties propiedades;
	 
	    // Carga del fichero de propiedades 
	    try 
	    {
	       FileInputStream f = new FileInputStream(configuracion);	 
	       propiedades = new Properties();
	       propiedades.load(f);
	       f.close();
	 
       // Leo los valores de configuracion
	       setDriver=propiedades.getProperty("setdriver").toLowerCase();
	       classpath = propiedades.getProperty(setDriver+"classpath"); 
	       jdbc = propiedades.getProperty(setDriver+"jdbc"); 
	       servidor = propiedades.getProperty(setDriver+"servidor");
	       usuario = propiedades.getProperty(setDriver+"usuario");
	       password = propiedades.getProperty(setDriver+"password");
	       cantCon = Integer.parseInt(propiedades.getProperty("conexiones"));
	     } 
	    catch (Exception e) 
	     {
				System.out.println("Mensaje Error: " + e.getMessage());
				System.out.println("Stack Trace: " + e.getStackTrace());
	     }
	}
	
	public void closeConnections()
	{
		for (int i=0; i<connections.size();i++)
		{
			try
			{
				connections.elementAt(i).close();
			}
			catch(Exception e)
			{
				System.out.println("Mensaje Error: " + e.getMessage());
				System.out.println("Stack Trace: " + e.getStackTrace());
			}
		}
	}
	
	public Connection getConnection()
	{
		Connection c = null;
		if (connections.size()>0)
			c = connections.remove(0);
		else
		{
			c = connect();
			System.out.println("Se ha creado una nueva conexion fuera de los parametros de configuracion");
		}
		return c;
	}
	
	public void realeaseConnection(Connection c)
	{
		connections.add(c);
	}
	
	public void testConnection()
	{
		try {
			int numUsuarios=0;
			Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
			
			Statement s = con.createStatement();
			String sentencia = "select 1 valor from dual";
			s.executeQuery(sentencia);
			ResultSet rs = s.getResultSet();
			
			while (rs.next())
			{
				numUsuarios = Integer.parseInt(rs.getString("valor"));
			}
			if (numUsuarios==1)
				System.out.println("Prueba exitosa");
			PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
		}
	}
	
}
