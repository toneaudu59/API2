/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacie.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import myconnection.DBConnection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pharmacie.metier.Medecin;
import pharmacie.metier.Patient;
import pharmacie.metier.Prescription;

/**
 *
 * @author tonea
 */
public class PrescriptionDAOTest {
    static Connection dbConnect;
    public PrescriptionDAOTest() {
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
     * Test of create method, of class PrescriptionDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription obj = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        Prescription expResult=obj;
        PrescriptionDAO instance = new PrescriptionDAO();
        instance.setConnection(dbConnect);
        Prescription result=instance.create(obj);
        assertEquals("Dates différentes",result.getDate(),expResult.getDate());
        assertEquals("Id médecin différents",result.getIdmed(),expResult.getIdmed());
        assertEquals("Id patient différents",result.getIdpat(),expResult.getIdpat());
        instance.delete(result);
        p.delete(pat);
        m.delete(med);
    }

    /**
     * Test of read method, of class PrescriptionDAO.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription obj = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO instance = new PrescriptionDAO();
        Prescription expResult=instance.create(obj);
        instance.setConnection(dbConnect);
        Prescription result=instance.read(expResult.getId());
        assertEquals("Dates différentes",result.getDate(),expResult.getDate());
        assertEquals("Id médecin différents",result.getIdmed(),expResult.getIdmed());
        assertEquals("Id patient différents",result.getIdpat(),expResult.getIdpat());
        try {
            instance.read(0);
            fail("exception d'id inconnu non générée");
        } catch (SQLException e) {}
        instance.delete(result);
        p.delete(pat);
        m.delete(med);
    }

    /**
     * Test of update method, of class PrescriptionDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin med2=new Medecin(0,"MA","NomTest2","PrenomTest2","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        med2=m.create(med2);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription obj = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO instance = new PrescriptionDAO();
        obj=instance.create(obj);
        obj.setIdmed(med2.getId());
        Prescription expResult=obj;
        instance.setConnection(dbConnect);
        Prescription result=instance.update(obj);
        assertEquals("Dates différentes",result.getDate(),expResult.getDate());
        assertEquals("Id médecin différents",result.getIdmed(),expResult.getIdmed());
        assertEquals("Id patient différents",result.getIdpat(),expResult.getIdpat());
        instance.delete(result);
        p.delete(pat);
        m.delete(med);
        m.delete(med2);
    }

    /**
     * Test of delete method, of class PrescriptionDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        Medecin med2=new Medecin(0,"MA","NomTest2","PrenomTest2","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        med2=m.create(med2);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription obj = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO instance = new PrescriptionDAO();
        obj=instance.create(obj);
        obj.setIdmed(med2.getId());
        Prescription expResult=obj;
        instance.setConnection(dbConnect);
        Prescription result=instance.update(obj);
        instance.delete(result);
        p.delete(pat);
        m.delete(med);
        m.delete(med2);
        try{
            instance.read(result.getId());
            fail("exception de record introuvable non générée");
        }catch(Exception e){}
        
    }

    /**
     * Test of rech method, of class PrescriptionDAO.
     */
 /*   @Test
    public void testRech() throws Exception {
        System.out.println("rech");
        int id = 0;
        PrescriptionDAO instance = new PrescriptionDAO();
        String expResult = "";
        String result = instance.rech(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rechp method, of class PrescriptionDAO.
     */
 /*   @Test
    public void testRechp() throws Exception {
        System.out.println("rechp");
        int id = 0;
        String ch = "";
        PrescriptionDAO instance = new PrescriptionDAO();
        List<String> expResult = null;
        List<String> result = instance.rechp(id, ch);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rechm method, of class PrescriptionDAO.
     */
 /*   @Test
    public void testRechm() throws Exception {
        System.out.println("rechm");
        String ch = "";
        int id = 0;
        PrescriptionDAO instance = new PrescriptionDAO();
        List<String> expResult = null;
        List<String> result = instance.rechm(ch, id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rechmed method, of class PrescriptionDAO.
     */
 /*   @Test
    public void testRechmed() throws Exception {
        System.out.println("rechmed");
        int id = 0;
        PrescriptionDAO instance = new PrescriptionDAO();
        List<Integer> expResult = null;
        List<Integer> result = instance.rechmed(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
