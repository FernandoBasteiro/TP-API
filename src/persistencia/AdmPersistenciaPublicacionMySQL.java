package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import controlador.CompraInmediataView;
import controlador.PublicacionView;
import controlador.SubastaView;
import modelo.CompraInmediata;
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
		return null;
	}
	
	public Publicacion buscarPublicacion(int nroPublicacion) {
		return null;
	}
	
	public ArrayList<PublicacionView> buscarPublicacionesUsuario(String nombreDeUsuario) {
		ArrayList<PublicacionView> publicaciones=new ArrayList<PublicacionView>();
		Connection con = PoolConnectionMySQL.getPoolConnection().getConnection();
		// Inserto los datos de la publicacion
		try {
			PreparedStatement ps = con.prepareStatement("select nroPublicacion,tipoPublicacion,nombreDeProducto,descripcion,fechaPublicacion,precioPublicado,estadoPublicacion,nombreDeUsuarioVendedor,stock,fechaHasta,ultimaOferta from publicaciones where nombreDeUsuarioVendedor=?");
			ps.setString(1, nombreDeUsuario);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				if(rs.getString("tipoPublicacion").equals("Compra Inmediata")) {
					PublicacionView ci= new CompraInmediataView(rs.getString("tipoPublicacion"),
															rs.getString("nombreDeProducto"),
															rs.getString("descripcion"),
															rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
															new ArrayList<String>(),
															rs.getFloat("precioPublicado"),
															rs.getString("estadoPublicacion"),
															rs.getInt("nroPublicacion"),
															rs.getInt("stock"));
					try {
						PreparedStatement psImagenes = con.prepareStatement("select imagenURL from imagenesPublicaciones where nroPublicacion=?");
						psImagenes.setInt(1, rs.getInt("nroPublicacion"));
						ResultSet rsImagenes=psImagenes.executeQuery();
						while(rsImagenes.next()) {
							if(rsImagenes.getString("imagenURL")!="")
								ci.getImagenes().add(rsImagenes.getString("imagenURL"));
						}
					} catch (Exception e) {
						System.out.println("Error Query: " + e.getMessage());
						return null;
					}
					publicaciones.add(ci);
				} else if (rs.getString("tipoPublicacion").equals("Subasta")) {
					PublicacionView s= new SubastaView(rs.getString("tipoPublicacion"),
													rs.getString("nombreDeProducto"),
													rs.getString("descripcion"),
													rs.getTimestamp("fechaPublicacion").toLocalDateTime(),
											new ArrayList<String>(),
											rs.getFloat("precioPublicado"),
											rs.getString("estadoPublicacion"),
											rs.getInt("nroPublicacion"),
											rs.getTimestamp("fechaHasta").toLocalDateTime());
					try {
						PreparedStatement psImagenes = con.prepareStatement("select imagenURL from imagenesPublicaciones where nroPublicacion=?");
						psImagenes.setInt(1, rs.getInt("nroPublicacion"));
						ResultSet rsImagenes=psImagenes.executeQuery();
						while(rsImagenes.next()) {
							if(rsImagenes.getString("imagenURL")!="")
								s.getImagenes().add(rsImagenes.getString("imagenURL"));
						}
					} catch (Exception e) {
						System.out.println("Error Query: " + e.getMessage());
						return null;
					}
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
