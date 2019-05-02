/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacie.DAO;

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
import static pharmacie.DAO.MedicamentDAOTest.dbConnect;
import pharmacie.metier.Info;
import pharmacie.metier.Medecin;
import pharmacie.metier.Medicament;
import pharmacie.metier.Patient;
import pharmacie.metier.Prescription;

/**
 *
 * @author tonea
 */
public class PatientDAOTest {
    
    public PatientDAOTest() {
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
     * Test of create method, of class PatientDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Patient obj = new Patient(0,"NomTestC","PrenomTest","0000000000");
        PatientDAO instance = new PatientDAO();
        instance.setConnection(dbConnect);
        Patient expResult = new Patient(0,"NomTestC","PrenomTest","0000000000");
        Patient result = instance.create(obj);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        try{
            Patient result2 = instance.create(obj);
            fail("exception de doublon non déclenchée");
            instance.delete(result2);
        }
        catch(SQLException e){}
        
        //int id = result.getId();
        //obj = new Medicament(0, "NomTest", "DescTest", "StockTest");
        instance.delete(result);
    }

    /**
     * Test of read method, of class PatientDAO.
     */
    @Test
    public void testRead_int() throws Exception {
        System.out.println("read");
        PatientDAO instance = new PatientDAO();
        Patient obj= new Patient(0,"NomTestR","PrenomTest","0000000000");
        Patient expResult = instance.create(obj);
        int id = expResult.getId();
        Patient result = instance.read(id);
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
     * Test of read method, of class PatientDAO.
     */
    @Test
    public void testRead_Patient() throws Exception {
        System.out.println("read");
        PatientDAO instance = new PatientDAO();
        Patient obj= new Patient(0,"NomTestRP","PrenomTest","0000000000");
        Patient expResult = instance.create(obj);
        Patient result = instance.read(obj);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        instance.delete(result);
    }

    /**
     * Test of update method, of class PatientDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Patient obj = new Patient(0,"NomTestU","PrenomTest","0000000000");
        PatientDAO instance = new PatientDAO();
        instance.setConnection(dbConnect);
        obj = instance.create(obj);
        obj.setNom("NomTest2");
        obj.setPrenom("PrenomTest2");
        obj.setTel("11111111111");
        Patient expResult=obj;
        Patient result = instance.update(obj);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Prenoms différents", expResult.getPrenom(), result.getPrenom());
        assertEquals("Téléphones différents", expResult.getTel(), result.getTel());
        obj = new Patient(0,"NomTest","PrenomTest","0000000000");
        Patient mod=instance.create(obj);
        obj=mod;
        obj.setNom("NomTest2");
        obj.setPrenom("PrenomTest2");
        obj.setTel("11111111111");
        try{
            instance.update(obj);
            fail("Exception de triplet nom-prenom-tel non déclenchée");
            instance.delete(obj);
        }catch(Exception e){instance.delete(mod);}
       // obj = new Medicament(0, "NomTest", "DescTest", "StockTest");
        
        instance.delete(result);
    }

    /**
     * Test of delete method, of class PatientDAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Patient obj = new Patient(0,"NomTestD1","PrenomTest","0000000000");
        PatientDAO instance = new PatientDAO();
        instance.setConnection(dbConnect);
        obj=instance.create(obj);
        instance.delete(obj);
        try {
            instance.read(obj.getId());
            fail("exception de record introuvable non générée");
        }catch(Exception e){}
        /*obj = new Patient(0,"NomTestD2","PrenomTestD","0000000001");
        obj=instance.create(obj);
        Medicament medoc=new Medicament(0, "NomTest", "DescTest", "StockTest");
        MedicamentDAO mdao=new MedicamentDAO();
        mdao.setConnection(dbConnect);
        medoc=mdao.create(medoc);
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO medd=new MedecinDAO();
        medd.setConnection(dbConnect);
        med=medd.create(med);
        Prescription pr=new Prescription(0,LocalDate.now(),med.getId(),obj.getId());
        PrescriptionDAO prd= new PrescriptionDAO();
        prd.setConnection(dbConnect);
        pr=prd.create(pr);
        Info inf=new Info(0,10,"UniteTest",medoc.getId(),pr.getId());
        InfoDAO infd=new InfoDAO();
        infd.setConnection(dbConnect);
        inf=infd.create(inf);
        try{
            instance.delete(obj);
            System.out.println(obj);
            fail("exception de record de parent clé étrangère non détecté");
        }catch(Exception e){}
        infd.delete(inf);
        prd.delete(pr);
        medd.delete(med);
        mdao.delete(medoc);
        instance.delete(obj);*/
    }

    /**
     * Test of rech method, of class PatientDAO.
     */
  /*  @Test
    public void testRech() throws Exception {
        System.out.println("rech");
        Patient obj1 = new Patient(0,"NomTest","PrenomTest","0000000000");
        Patient obj2 = new Patient(0,"NomTest","PrenomTest2","1111111111");
        String nomrech = "NomTest";
        PatientDAO instance=new PatientDAO();
        instance.setConnection(dbConnect);
        obj1=instance.create(obj1);
        obj2=instance.create(obj2);
        
      
        List<Patient> result = instance.rech(nomrech);
        if(result.indexOf(obj1)<0) fail("record introuvable "+obj1);
        if(result.indexOf(obj2)<0) fail("record introuvable"+obj2);
        instance.delete(obj1);
        instance.delete(obj2);
    }*/
    
}
