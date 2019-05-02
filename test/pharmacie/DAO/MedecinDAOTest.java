/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacie.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import myconnection.DBConnection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pharmacie.metier.Medecin;

/**
 *
 * @author tonea
 */
public class MedecinDAOTest {
    static Connection dbConnect;
    public MedecinDAOTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.out.println("connection invalide");
            System.exit(1);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        DBConnection.closeConnection();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class MedecinDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        Medecin expResult = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin result = instance.create(obj);
        assertEquals("Matricules différents", expResult.getMatricule(), result.getMatricule());
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        
        instance.delete(result);
    }

    /**
     * Test of read method, of class MedecinDAO.
     */
    @Test
    public void testRead_int() throws Exception {
        System.out.println("read");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin expResult = instance.create(obj);
        int id = expResult.getId();
        Medecin result = instance.read(id);
        assertEquals("Matricules différents", expResult.getMatricule(), result.getMatricule());
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        try {
            result = instance.read(0);
            fail("exception d'id inconnu non générée");
        } catch (SQLException e) {}
        instance.delete(result);
    }

    /**
     * Test of read method, of class MedecinDAO.
     */
    @Test
    public void testRead_Medecin() throws Exception {
        System.out.println("read");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin expResult = instance.create(obj);
        Medecin result = instance.read(obj);
        assertEquals("Matricules différents", expResult.getMatricule(), result.getMatricule());
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        instance.delete(result);
    }

    /**
     * Test of read method, of class MedecinDAO.
     */
    @Test
    public void testRead_String() throws Exception {
        System.out.println("read");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin expResult = instance.create(obj);
        String matricule = expResult.getMatricule();
        Medecin result = instance.read(matricule);
        assertEquals("Matricules différents", expResult.getMatricule(), result.getMatricule());
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        instance.delete(result);
    }

    /**
     * Test of update method, of class MedecinDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        obj = instance.create(obj);
        obj.setMatricule("MA");
        obj.setNom("NomTest2");
        obj.setPrenom("PrenomTest2");
        obj.setTel("1111111111");
        Medecin expResult=obj;
        Medecin result = instance.update(obj);
        assertEquals("Matricules différents", expResult.getMatricule(), result.getMatricule());
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        instance.delete(result);
    }

    /**
     * Test of delete method, of class MedecinDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Medecin obj = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        obj=instance.create(obj);
        instance.delete(obj);
        try {
            instance.read(obj.getId());
            fail("exception de record introuvable non générée");//Suppression réussie
        }
        catch(SQLException e){}
    }

    /**
     * Test of rech method, of class MedecinDAO.
     */
    @Test
    public void testRech() throws Exception {
        System.out.println("rech");
        String nomrech = "NomTest";
        Medecin obj1 = new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin obj2 = new Medecin(0,"MA","NomTest","PrenomTest2","1111111111");
        MedecinDAO instance = new MedecinDAO();
        instance.setConnection(dbConnect);
        obj1=instance.create(obj1);
        obj2=instance.create(obj2);
        List<Medecin> result = instance.rech(nomrech);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
        boolean ok1=false,ok2=false;
        for(int i=0;i<result.size();i++){
            if(result.get(i).equals(obj1)) ok1=true;
            if(result.get(i).equals(obj2)) ok2=true;
        }
        if(!ok1) fail("Objet introuvable");
        if(!ok2) fail("Objet introuvable");
        instance.delete(obj1);
        instance.delete(obj2);
    }
    
}
