package persistencia;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import jdk.nashorn.internal.ir.CaseNode;


public class PoolConnectionMySQL_NoUsar
{
	private Vector <Connection> connections = new Vector<Connection>();
	protected String setDriver;
	protected String classpath;
	protected String jdbc;
	protected String servidor;
	protected String usuario;
	protected String password;
	protected int cantCon;
	private static PoolConnectionMySQL_NoUsar pool;
	private PoolConnectionMySQL_NoUsar()
	{
		getConfiguration();
		for (int i= 0; i< cantCon;i++)
			connections.add(connect());
	}
	
	public static PoolConnectionMySQL_NoUsar getPoolConnection()
	{
		if (pool== null)
			pool =new PoolConnectionMySQL_NoUsar();
		return pool;
	}
	
	private Connection connect()
	{
		try
		{
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
		String configuracion = "No usar, el PoolConnection se setea en el txt para SQLServer o MySQL";
	    Properties propiedades;
	 
	    // Carga del fichero de propiedades 
	    try 
	    {
	       FileInputStream f = new FileInputStream(configuracion);	 
	       propiedades = new Properties();
	       propiedades.load(f);
	       f.close();
	 
       // Leo los valores de configuracion
	       setDriver=propiedades.getProperty("setdriver").toUpperCase();
	       setDriver=propiedades.getProperty("setdriver").toUpperCase();
	       if (setDriver.toString().compareTo("SS") == 0) {
	    	   classpath = propiedades.getProperty("ssclasspath"); 
		       jdbc = propiedades.getProperty("ssjdbc"); 
		       servidor = propiedades.getProperty("ssservidor");
		       usuario = propiedades.getProperty("ssusuario");
		       password = propiedades.getProperty("sspassword");
	       } else if (setDriver.toString().compareTo("MY") == 0) {
	    	   classpath = propiedades.getProperty("myclasspath"); 
		       jdbc = propiedades.getProperty("myjdbc"); 
		       servidor = propiedades.getProperty("myservidor");
		       usuario = propiedades.getProperty("myusuario");
		       password = propiedades.getProperty("mypassword");
	       }
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
			Connection con = PoolConnection.getPoolConnection().getConnection();
			
			Statement s = con.createStatement();
			String sentencia = "select count(*) usuarios from sys.sysusers where sid is not null and islogin=1";
			s.executeQuery(sentencia);
			ResultSet rs = s.getResultSet();
			
			while (rs.next())
			{
				numUsuarios = Integer.parseInt(rs.getString("usuarios"));
			}
			if (numUsuarios>0)
				System.out.println("Existen usuarios");
			else
				System.out.println("No se encontraron usuarios");
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
		}
	}
	
}
