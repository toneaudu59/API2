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
import static pharmacie.DAO.PrescriptionDAOTest.dbConnect;
import pharmacie.metier.Info;
import pharmacie.metier.Medecin;
import pharmacie.metier.Medicament;
import pharmacie.metier.Patient;
import pharmacie.metier.Prescription;

/**
 *
 * @author tonea
 */
public class InfoDAOTest {
    static Connection dbConnect;
    public InfoDAOTest() {
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
     * Test of create method, of class InfoDAO.
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
        Prescription pre = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO pr = new PrescriptionDAO();
        pr.setConnection(dbConnect);
        pre=pr.create(pre);
        Medicament medoc = new Medicament(0, "NomTest", "DescTest", "StockTest");
        MedicamentDAO md=new MedicamentDAO();
        md.setConnection(dbConnect);
        medoc=md.create(medoc);
        Info obj=new Info(0,10,"UniteTest",medoc.getId(),pre.getId());
        InfoDAO instance=new InfoDAO();
        instance.setConnection(dbConnect);
        Info expResult=obj;
        Info result=instance.create(obj);
        assertEquals("Quantités différentes",result.getQuantite(),expResult.getQuantite());
        assertEquals("Unités différentes",result.getUnite(),expResult.getUnite());
        assertEquals("Id medicament différents",result.getIdmedoc(),expResult.getIdmedoc());
        assertEquals("Id prescripton différents",result.getIdpres(),expResult.getIdpres());
        instance.delete(result);
        md.delete(medoc);
        pr.delete(pre);
        p.delete(pat);
        m.delete(med);
    }

    /**
     * Test of read method, of class InfoDAO.
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
        Prescription pre = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO pr = new PrescriptionDAO();
        pr.setConnection(dbConnect);
        pre=pr.create(pre);
        Medicament medoc = new Medicament(0, "NomTest", "DescTest", "StockTest");
        MedicamentDAO md=new MedicamentDAO();
        md.setConnection(dbConnect);
        medoc=md.create(medoc);
        Info obj=new Info(0,10,"UniteTest",medoc.getId(),pre.getId());
        InfoDAO instance=new InfoDAO();
        instance.setConnection(dbConnect);
        Info expResult=instance.create(obj);
        Info result=instance.read(expResult.getId());
        assertEquals("Quantités différentes",result.getQuantite(),expResult.getQuantite());
        assertEquals("Unités différentes",result.getUnite(),expResult.getUnite());
        assertEquals("Id medicament différents",result.getIdmedoc(),expResult.getIdmedoc());
        assertEquals("Id prescripton différents",result.getIdpres(),expResult.getIdpres());
        instance.delete(result);
        md.delete(medoc);
        pr.delete(pre);
        p.delete(pat);
        m.delete(med);
        try{
            instance.read(0);
            fail("exception d'id inconnu non générée");
        }
        catch(SQLException e){}
    }

    /**
     * Test of update method, of class InfoDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription pre = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO pr = new PrescriptionDAO();
        pr.setConnection(dbConnect);
        pre=pr.create(pre);
        Medicament medoc = new Medicament(0, "NomTest", "DescTest", "StockTest");
        Medicament medoc2 = new Medicament(0, "NomTest2", "DescTest2", "StockTest2");
        MedicamentDAO md=new MedicamentDAO();
        md.setConnection(dbConnect);
        medoc=md.create(medoc);
        medoc2=md.create(medoc2);
        Info obj=new Info(0,10,"UniteTest",medoc.getId(),pre.getId());
        InfoDAO instance=new InfoDAO();
        instance.setConnection(dbConnect);
        obj=instance.create(obj);
        obj.setIdmedoc(medoc2.getId());
        Info expResult=obj;
        Info result=instance.update(obj);
        assertEquals("Quantités différentes",result.getQuantite(),expResult.getQuantite());
        assertEquals("Unités différentes",result.getUnite(),expResult.getUnite());
        assertEquals("Id medicament différents",result.getIdmedoc(),expResult.getIdmedoc());
        assertEquals("Id prescripton différents",result.getIdpres(),expResult.getIdpres());
        instance.delete(result);
        md.delete(medoc);
        md.delete(medoc2);
        pr.delete(pre);
        p.delete(pat);
        m.delete(med);
    }

    /**
     * Test of delete method, of class InfoDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO m=new MedecinDAO();
        m.setConnection(dbConnect);
        med=m.create(med);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO p=new PatientDAO();
        p.setConnection(dbConnect);
        pat=p.create(pat);
        Prescription pre = new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO pr = new PrescriptionDAO();
        pr.setConnection(dbConnect);
        pre=pr.create(pre);
        Medicament medoc = new Medicament(0, "NomTest", "DescTest", "StockTest");
        MedicamentDAO md=new MedicamentDAO();
        md.setConnection(dbConnect);
        medoc=md.create(medoc);
        Info obj=new Info(0,10,"UniteTest",medoc.getId(),pre.getId());
        InfoDAO instance=new InfoDAO();
        instance.setConnection(dbConnect);
        Info expResult=obj;
        Info result=instance.create(obj);
        assertEquals("Quantités différentes",result.getQuantite(),expResult.getQuantite());
        assertEquals("Unités différentes",result.getUnite(),expResult.getUnite());
        assertEquals("Id medicament différents",result.getIdmedoc(),expResult.getIdmedoc());
        assertEquals("Id prescripton différents",result.getIdpres(),expResult.getIdpres());
        instance.delete(result);
        md.delete(medoc);
        pr.delete(pre);
        p.delete(pat);
        m.delete(med);
        try {
            instance.read(obj.getId());
            fail("exception de record introuvable non générée");
        }
        catch(SQLException e){}
    }

    /**
     * Test of rech method, of class InfoDAO.
     */
    /*@Test
    public void testRech_int() throws Exception {
        System.out.println("rech");
        int id = 0;
        InfoDAO instance = new InfoDAO();
        List<Info> expResult = null;
        List<Info> result = instance.rech(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rech method, of class InfoDAO.
     */
    /*@Test
    public void testRech_String() throws Exception {
        System.out.println("rech");
        String id = "";
        InfoDAO instance = new InfoDAO();
        List<Info> expResult = null;
        List<Info> result = instance.rech(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
