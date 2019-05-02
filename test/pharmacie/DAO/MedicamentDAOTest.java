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
import pharmacie.metier.Info;
import pharmacie.metier.Medecin;
import pharmacie.metier.Medicament;
import pharmacie.metier.Patient;
import pharmacie.metier.Prescription;
import pharmacie.metier.Vue_somme_medicament_prescrit;

/**
 *
 * @author tonea
 */
public class MedicamentDAOTest {

    static Connection dbConnect;

    public MedicamentDAOTest() {
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
     * Test of create method, of class MedicamentDAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Medicament obj = new Medicament(0, "NomTest", "DescTest", "CodeTest");
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        Medicament expResult = obj;
        Medicament result = instance.create(obj);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Descriptions différentes", expResult.getDescription(), result.getDescription());
        assertEquals("Codes différents", expResult.getCode(), result.getCode());
        //int id = result.getId();
        //obj = new Medicament(0, "NomTest", "DescTest", "StockTest");
        instance.delete(result);
    }

    /**
     * Test of read method, of class MedicamentDAO.
     */
    @Test
    public void testRead_int() throws Exception {
        System.out.println("read");
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        Medicament obj = new Medicament(0, "NomTest", "DescTest", "CodeTest");
        Medicament expResult = instance.create(obj);
        int id = expResult.getId();
        Medicament result = instance.read(id);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Descriptions différentes", expResult.getDescription(), result.getDescription());
        assertEquals("Codes différents", expResult.getCode(), result.getCode());
        try {
            result = instance.read(0);
            fail("exception d'id inconnu non générée");
        } catch (SQLException e) {}
        instance.delete(result);
    }

    /**
     * Test of read method, of class MedicamentDAO.
     */
    @Test
    public void testRead_String() throws Exception {
        System.out.println("read");
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        Medicament obj = new Medicament(0, "NomTest", "DescTest", "CodeTest");
        Medicament expResult = instance.create(obj);
        String nom = expResult.getNom();
        Medicament result = instance.read(nom);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Descriptions différentes", expResult.getDescription(), result.getDescription());
        assertEquals("Codes différents", expResult.getCode(), result.getCode());
        instance.delete(result);
    }

    /**
     * Test of update method, of class MedicamentDAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Medicament obj = new Medicament(0, "NomTest", "DescTest", "CodeTest");
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        obj = instance.create(obj);
        obj.setNom("NomTest2");
        obj.setDescription("DescTest2");
        obj.setCode("CodeTest");
        Medicament expResult=obj;
        Medicament result = instance.update(obj);
        assertEquals("Noms différents", expResult.getNom(), result.getNom());
        assertEquals("Descriptions différentes", expResult.getDescription(), result.getDescription());
        assertEquals("Codes différents", expResult.getCode(), result.getCode());
       // obj = new Medicament(0, "NomTest", "DescTest", "StockTest");
        instance.delete(result);
    }

    /**
     * Test of delete method, of class MedicamentDAO.
     */
    @Test
    public void testDelete() throws Exception {
         System.out.println("delete");
        Medicament obj = new Medicament(0, "NomTest", "DescTest", "CodeTest");
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        obj = instance.create(obj);
        instance.delete(obj);
        try {
            instance.read(obj.getId());
            fail("exception de record introuvable non générée");//Suppression réussie
        }
        catch(SQLException e){}
        instance.create(obj);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO patd=new PatientDAO();
        patd.setConnection(dbConnect);
        pat=patd.create(pat);
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO medd=new MedecinDAO();
        medd.setConnection(dbConnect);
        med=medd.create(med);
        Prescription pr=new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO prd= new PrescriptionDAO();
        prd.setConnection(dbConnect);
        pr=prd.create(pr);
        Info inf=new Info(0,10,"UniteTest",obj.getId(),pr.getId());
        InfoDAO infd=new InfoDAO();
        infd.setConnection(dbConnect);
        inf=infd.create(inf);
        try{
            instance.delete(obj);
            fail("exception de record de parent clé étrangère");
        }catch(Exception e){}
        infd.delete(inf);
        prd.delete(pr);
        medd.delete(med);
        patd.delete(pat);
        instance.delete(obj);
    }

    /**
     * Test of rech method, of class MedicamentDAO.
     */
   @Test
    public void testRech() throws Exception {
        System.out.println("rechNom");
        Medicament obj1 = new Medicament(0, "NomTest", "DescTest", "CodeTest");;
        Medicament obj2 = new Medicament(0, "NomTest2", "DescTest", "CodeTest2");;
        String descrech = "DescTest";
        MedicamentDAO instance = new MedicamentDAO();
        instance.setConnection(dbConnect);
        obj1=instance.create(obj1);
        obj2=instance.create(obj2);
        Patient pat=new Patient(0,"NomTest","PrenomTest","0000000000");
        PatientDAO patd=new PatientDAO();
        patd.setConnection(dbConnect);
        pat=patd.create(pat);
        Medecin med=new Medecin(0,"MT","NomTest","PrenomTest","0000000000");
        MedecinDAO medd=new MedecinDAO();
        medd.setConnection(dbConnect);
        med=medd.create(med);
        Prescription pr1=new Prescription(0,LocalDate.now(),med.getId(),pat.getId());
        PrescriptionDAO prd= new PrescriptionDAO();
        prd.setConnection(dbConnect);
        pr1=prd.create(pr1);
        Info inf1=new Info(0,10,"UniteTest",obj1.getId(),pr1.getId());
        Info inf2=new Info(0,20,"UniteTest2",obj2.getId(),pr1.getId());
        InfoDAO infd=new InfoDAO();
        infd.setConnection(dbConnect);
        inf1=infd.create(inf1);
        inf2=infd.create(inf2);
        List<Vue_somme_medicament_prescrit> result = instance.rech(descrech);
        boolean ok1=false,ok2=false;
        for(int i=0;i<result.size();i++){
            if(obj1.getNom().equalsIgnoreCase(result.get(i).getNom()) && obj1.getId()==result.get(i).getId() && obj1.getDescription().equalsIgnoreCase(result.get(i).getDescription())){
                        ok1=true;
            }
            if(obj2.getNom().equalsIgnoreCase(result.get(i).getNom()) && obj2.getId()==result.get(i).getId() && obj2.getDescription().equalsIgnoreCase(result.get(i).getDescription())){
                        ok2=true;
            }
        }
        if(!ok1) fail("record introuvable"+obj1);
        if(!ok2) fail("record introuvable"+obj2);
        infd.delete(inf1);
        infd.delete(inf2);
        prd.delete(pr1);
        medd.delete(med);
        patd.delete(pat);
        instance.delete(obj1);
        instance.delete(obj2);
    }

}
