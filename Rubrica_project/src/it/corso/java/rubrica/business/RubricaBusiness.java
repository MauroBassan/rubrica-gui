package it.corso.java.rubrica.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.MysqlDataSource;

import it.corso.java.rubrica.model.Contatto;

public class RubricaBusiness {

	private Connection conn;
	// la creo static e poi sarà un singleton!!
	private static RubricaBusiness rb;
	
	public static RubricaBusiness getInstance() {
		if(rb == null) {
			rb = new RubricaBusiness();
		}
		return rb;	
	}
	
	
	// creo metodo x connettermi al db
	private Connection getConnection() throws SQLException {
		//  controllo che l'stanza non sia null, altrimenti la facciamo noi
		if(conn == null) {
			// se l'istanza è null, creo l'istanza come segue:
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("localhost");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("BMWfj055yx");
			dataSource.setDatabaseName("rubrica_java");
			dataSource.setServerTimezone("UTC");
		
			// a questo punto avendo impostato i paramentri x connetterci, ci connettiamo:
			conn = dataSource.getConnection();
			
		}
		return conn;
	}
	
	public List<Contatto> ricercaContatti() throws SQLException {
		String query = "SELECT  id, nome, cognome, telefono FROM contatti";
		
		/* per eseguire la query dobbiamo creare un oggetto
		 * di tipo Prepared statement che serve a passare in input  a Mysql la query x poter effetturare
		 */
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		 
		List<Contatto> contatti = new ArrayList<Contatto>();
		while(rs.next()) {
			Contatto c = new Contatto();
			c.setId(rs.getInt(1));
			c.setNome(rs.getString(2));
			c.setCognome(rs.getString(3));
			c.setTelefono(rs.getString(4));
			
			contatti.add(c);
			System.out.println("------------------------------");
		}
		return contatti;	
	}
	
	public int aggiungiContatto(Contatto c) throws SQLException {
		
		String sql = "INSERT INTO contatti(nome, cognome, telefono) VALUES(?, ?, ?)";
		PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, c.getNome());
		ps.setString(2, c.getCognome());
		ps.setString(3, c.getTelefono());
		
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		return rs.getInt(1);
	}
	
	
}
