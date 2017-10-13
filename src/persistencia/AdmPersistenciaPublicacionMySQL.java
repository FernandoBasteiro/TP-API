package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import controlador.AdmUsuarios;
import modelo.CompraInmediata;
import modelo.Oferta;
import modelo.Publicacion;
import modelo.Subasta;

public class AdmPersistenciaPublicacionMySQL {
	static private AdmPersistenciaPublicacionMySQL instancia;
	
	static public AdmPersistenciaPublicacionMySQL getInstancia(){
		if (instancia == null) {
			instancia = new AdmPersistenciaPublicacionMySQL();
		}
		return instancia;
	}
	
	private AdmPersistenciaPublicacionMySQL() {
		
	}
	
	public ArrayList<Publicacion> buscarPublicacionesProducto(String nombreDeProducto) {
		ArrayList<Publicacion> publicaciones=new ArrayList<Publicacion>();
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		// Inserto los datos de la publicacion
		try {
			PreparedStatement ps = con.prepareStatement("select nroPublicacion,tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock,fechaHasta,ultimaOferta from publicaciones where nombreDeProducto=?");
			ps.setString(1, nombreDeProducto);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				ArrayList<String> imagenes = new ArrayList<String>();
				PreparedStatement psImagenes = con.prepareStatement("select imagenURL from imagenesPublicaciones where nroPublicacion=?");
				psImagenes.setInt(1, rs.getInt("nroPublicacion"));
				ResultSet rsImagenes=psImagenes.executeQuery();
				while(rsImagenes.next()) {
					imagenes.add(rsImagenes.getString("imagenURL"));
				}
				
				if(rs.getString("tipoPublicacion").equals("Compra Inmediata")) {
					CompraInmediata ci = new CompraInmediata(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getInt("stock"),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							AdmUsuarios.getInstancia().buscarUsuarioRegular(rs.getString("nombreDeUsuarioVendedor")));
					publicaciones.add(ci);
				} else if (rs.getString("tipoPublicacion").equals("Subasta")) {
					Oferta oferta = AdmPersistenciaOfertasMySQL.getInstancia().getMayorOferta(rs.getInt("nroPublicacion"));
					Subasta s = new Subasta(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getTimestamp("fechaHasta").toLocalDateTime(),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							oferta,
							AdmUsuarios.getInstancia().buscarUsuarioRegular(rs.getString("nombreDeUsuarioVendedor")));
					publicaciones.add(s);
				}
			}
//			System.out.println("ID generado: " + rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
		PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		return publicaciones;
	}
	
	public Publicacion buscarPublicacion(int nroPublicacion) {
		Publicacion publicacion=null;
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		// Inserto los datos de la publicacion
		try {
			PreparedStatement ps = con.prepareStatement("select nroPublicacion,tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock,fechaHasta,ultimaOferta from publicaciones where nroPublicacion=?");
			ps.setInt(1, nroPublicacion);
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				ArrayList<String> imagenes = new ArrayList<String>();
				PreparedStatement psImagenes = con.prepareStatement("select imagenURL from imagenesPublicaciones where nroPublicacion=?");
				psImagenes.setInt(1, rs.getInt("nroPublicacion"));
				ResultSet rsImagenes=psImagenes.executeQuery();
				while(rsImagenes.next()) {
					imagenes.add(rsImagenes.getString("imagenURL"));
				}
				
				if(rs.getString("tipoPublicacion").equals("Compra Inmediata")) {
					CompraInmediata ci = new CompraInmediata(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getInt("stock"),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							AdmUsuarios.getInstancia().buscarUsuarioRegular(rs.getString("nombreDeUsuarioVendedor")));
					publicacion=ci;
				} else if (rs.getString("tipoPublicacion").equals("Subasta")) {
					Oferta oferta = AdmPersistenciaOfertasMySQL.getInstancia().getMayorOferta(rs.getInt("nroPublicacion"));
					Subasta s = new Subasta(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getTimestamp("fechaHasta").toLocalDateTime(),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							oferta,
							AdmUsuarios.getInstancia().buscarUsuarioRegular(rs.getString("nombreDeUsuarioVendedor")));
					publicacion=s;
				}
			}
//			System.out.println("ID generado: " + rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
		PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		return publicacion;
	}
	
	public ArrayList<Publicacion> buscarPublicacionesUsuario(String nombreDeUsuario) {
		ArrayList<Publicacion> publicaciones=new ArrayList<Publicacion>();
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		// Inserto los datos de la publicacion
		try {
			PreparedStatement ps = con.prepareStatement("select nroPublicacion,tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock,fechaHasta,ultimaOferta from publicaciones where nombreDeUsuarioVendedor=?");
			ps.setString(1, nombreDeUsuario);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				ArrayList<String> imagenes = new ArrayList<String>();
				PreparedStatement psImagenes = con.prepareStatement("select imagenURL from imagenesPublicaciones where nroPublicacion=?");
				psImagenes.setInt(1, rs.getInt("nroPublicacion"));
				ResultSet rsImagenes=psImagenes.executeQuery();
				while(rsImagenes.next()) {
					imagenes.add(rsImagenes.getString("imagenURL"));
				}
				
				if(rs.getString("tipoPublicacion").equals("Compra Inmediata")) {
					CompraInmediata ci = new CompraInmediata(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getInt("stock"),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							AdmUsuarios.getInstancia().buscarUsuarioRegular(nombreDeUsuario));
					publicaciones.add(ci);
				} else if (rs.getString("tipoPublicacion").equals("Subasta")) {
					Oferta oferta = AdmPersistenciaOfertasMySQL.getInstancia().getMayorOferta(rs.getInt("nroPublicacion"));
					Subasta s = new Subasta(rs.getString("nombreDeProducto"),
							rs.getString("descripcion"),
							imagenes,
							rs.getFloat("precioPublicado"),
							rs.getTimestamp("fechaHasta").toLocalDateTime(),
							rs.getInt("nroPublicacion"),
							rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
							oferta,
							AdmUsuarios.getInstancia().buscarUsuarioRegular(nombreDeUsuario));
					publicaciones.add(s);
				}
			}
//			System.out.println("ID generado: " + rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return null;
		}
		PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		return publicaciones;
	}
	
	public int updatePublicacion(int nroPublicacion) {
		return 0;
	}
	
	public int insertPublicacion(Subasta s) {
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		int nroPublicacion=0;
		// Inserto los datos de la publicacion
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO publicaciones (tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,fechaHasta) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, s.getPublicacionView().getTipoPublicacion());
			ps.setString(2, s.getPublicacionView().getNombreProducto());
			ps.setString(3, s.getPublicacionView().getDescripcion());
			ps.setTimestamp(4, Timestamp.valueOf(s.getPublicacionView().getFechaPublicacion()));
			ps.setFloat(5, s.getPublicacionView().getPrecioActual());
			ps.setString(6, s.getPublicacionView().getEstadoPublicacion());
			ps.setString(7, s.getVendedor().getNombreDeUsuario());
			ps.setTimestamp(8, Timestamp.valueOf(s.getPublicacionView().getFechaHasta()));
			ps.executeUpdate();
			
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			nroPublicacion=rs.getInt(1);
//			System.out.println("ID generado: " + rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
		
		//inserto los datos de la lista de imagenes
		try {
			for (String imagenTexto : s.getPublicacionView().getImagenes()) {
				PreparedStatement ps = con.prepareStatement("INSERT INTO imagenesPublicaciones (nroPublicacion,imagenURL) VALUES (?,?)");
				ps.setInt(1, nroPublicacion);
				ps.setString(2, imagenTexto);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			this.deletePublicacion(con, nroPublicacion);
			return -1;
		}
		PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		return nroPublicacion;
	}
	
	public int insertPublicacion(CompraInmediata ci) {
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		int nroPublicacion=0;
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO publicaciones (tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ci.getPublicacionView().getTipoPublicacion());
			ps.setString(2, ci.getPublicacionView().getNombreProducto());
			ps.setString(3, ci.getPublicacionView().getDescripcion());
			ps.setTimestamp(4, Timestamp.valueOf(ci.getPublicacionView().getFechaPublicacion()));
			ps.setFloat(5, ci.getPublicacionView().getPrecioActual());
			ps.setString(6, ci.getPublicacionView().getEstadoPublicacion());
			ps.setString(7, ci.getVendedor().getNombreDeUsuario());
			ps.setInt(8, ci.getPublicacionView().getStock());
			ps.executeUpdate();
			
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			nroPublicacion=rs.getInt(1);
//			System.out.println("ID generado: " + rs.getInt(1));
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			return -1;
		}
		
		//inserto los datos de la lista de imagenes
		try {
			for (String imagenTexto : ci.getPublicacionView().getImagenes()) {
				if(imagenTexto.length()>0) {
					PreparedStatement ps = con.prepareStatement("INSERT INTO imagenesPublicaciones (nroPublicacion,imagenURL) VALUES (?,?)");
					ps.setInt(1, nroPublicacion);
					ps.setString(2, imagenTexto);
					ps.executeUpdate();
				}
			}
			
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
			this.deletePublicacion(con, nroPublicacion);
			return -1;
		}
		
		PoolConnectionMySQL.getPoolConnection().realeaseConnection(con);
		return nroPublicacion;
	}
	
	private void deletePublicacion(Connection con,int nroPublicacion) {
		try {
			PreparedStatement ps = con.prepareStatement("delete from imagenesPublicaciones where nroPublicacion=?");
			ps.setInt(1, nroPublicacion);
			ps.execute();
		} catch (Exception e) {
			System.out.println("Error Query: " + e.getMessage());
		}
	}
}
