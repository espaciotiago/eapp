package Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Section;
import ModelManager.Area;
import ModelManager.Item;

public class DummyDataManager {

    private DaoSession daoSession;

    public DummyDataManager(DaoSession daoSession){
        this.daoSession = daoSession;
    }

    /**
     *
     */
    public boolean verifyDataExistence(){
        List sections = daoSession.getSectionDao().loadAll();
        List areas = daoSession.getAreaDao().loadAll();
        List items = daoSession.getItemDao().loadAll();

        if(!sections.isEmpty() && !areas.isEmpty() && !items.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     */
    public List<Long> fillDummyData(){
        // Delete the registres
        daoSession.getSectionDao().deleteAll();
        daoSession.getAreaDao().deleteAll();
        daoSession.getItemDao().deleteAll();
        // Insert Dummy data
        List<Long> list = createSectionsAndAreas();
        list.addAll(createItems());
        return list;
    }

    /**
     *
     */
    private List<Long> createItems(){
        List<Long> listItems= new ArrayList<>();
        int i = 0;
        //---------------------------------------------------------------------------
        i++;
        Item item1 = new Item();
        item1.setReference("dummydata"+i);
        item1.setName("Uniforme");
        item1.setDescription("Uniforme");
        item1.setImagePath("img"+i);
        item1.setStock(100);
        item1.setQtyDefault(1);
        item1.setQtyInSpecificTime(1);
        item1.setEachInDays(180);
        item1.setCreationDate(new Date());
        Long id1 = daoSession.getItemDao().insert(item1);
        listItems.add(id1);
        //---------------------------------------------------------------------------
        i++;
        Item item2 = new Item();
        item2.setReference("dummydata"+i);
        item2.setName("Gafas de seguridad lente claro");
        item2.setDescription("Gafas de seguridad lente claro");
        item2.setImagePath("img"+i);
        item2.setStock(100);
        item2.setQtyDefault(1);
        item2.setQtyInSpecificTime(1);
        item2.setEachInDays(180);
        item2.setCreationDate(new Date());
        Long id2 = daoSession.getItemDao().insert(item2);
        listItems.add(id2);
        //---------------------------------------------------------------------------
        i++;
        Item item3 = new Item();
        item3.setReference("dummydata"+i);
        item3.setName("Gafas de seguridad lente oscuro");
        item3.setDescription("Gafas de seguridad lente oscuro");
        item3.setImagePath("img"+i);
        item3.setStock(100);
        item3.setQtyDefault(1);
        item3.setQtyInSpecificTime(1);
        item3.setEachInDays(180);
        item3.setCreationDate(new Date());
        Long id3 = daoSession.getItemDao().insert(item3);
        listItems.add(id3);
        //---------------------------------------------------------------------------
        i++;
        Item item4 = new Item();
        item4.setReference("dummydata"+i);
        item4.setName("Protector auditivo silicona");
        item4.setDescription("Protector auditivo silicona");
        item4.setImagePath("img"+i);
        item4.setStock(100);
        item4.setQtyDefault(1);
        item4.setQtyInSpecificTime(9999);
        item4.setEachInDays(1);
        item4.setCreationDate(new Date());
        Long id4 = daoSession.getItemDao().insert(item4);
        listItems.add(id4);
        //---------------------------------------------------------------------------
        i++;
        Item item5 = new Item();
        item5.setReference("dummydata"+i);
        item5.setName("Protector auditivo tipo copa");
        item5.setDescription("Protector auditivo tipo copa");
        item5.setImagePath("img"+i);
        item5.setStock(100);
        item5.setQtyDefault(1);
        item5.setQtyInSpecificTime(1);
        item5.setEachInDays(365);
        item5.setCreationDate(new Date());
        Long id5 = daoSession.getItemDao().insert(item5);
        listItems.add(id5);
        //---------------------------------------------------------------------------
        i++;
        Item item6 = new Item();
        item6.setReference("dummydata"+i);
        item6.setName("Botas de seguridad dielectrica con puntera");
        item6.setDescription("Botas de seguridad dielectrica con puntera");
        item6.setImagePath("img"+i);
        item6.setStock(100);
        item6.setQtyDefault(1);
        item6.setQtyInSpecificTime(1);
        item6.setEachInDays(180);
        item6.setCreationDate(new Date());
        Long id6 = daoSession.getItemDao().insert(item6);
        listItems.add(id6);
        //---------------------------------------------------------------------------
        i++;
        Item item7 = new Item();
        item7.setReference("dummydata"+i);
        item7.setName("Protección respiratoria");
        item7.setDescription("Protección respiratoria");
        item7.setImagePath("img"+i);
        item7.setStock(100);
        item7.setQtyDefault(1);
        item7.setQtyInSpecificTime(1);
        item7.setEachInDays(365);
        item7.setCreationDate(new Date());
        Long id7 = daoSession.getItemDao().insert(item7);
        listItems.add(id7);
        //---------------------------------------------------------------------------
        i++;
        Item item8 = new Item();
        item8.setReference("dummydata"+i);
        item8.setName("Protección respiratoria");
        item8.setDescription("Protección respiratoria");
        item8.setImagePath("img"+i);
        item8.setStock(100);
        item8.setQtyDefault(1);
        item8.setQtyInSpecificTime(1);
        item8.setEachInDays(365);
        item8.setCreationDate(new Date());
        Long id8 = daoSession.getItemDao().insert(item8);
        listItems.add(id8);
        //---------------------------------------------------------------------------
        i++;
        Item item9 = new Item();
        item9.setReference("dummydata"+i);
        item9.setName("Guante G40 Latex");
        item9.setDescription("Guante G40 Latex");
        item9.setImagePath("img"+i);
        item9.setStock(100);
        item9.setQtyDefault(1);
        item9.setQtyInSpecificTime(1);
        item9.setEachInDays(180);
        item9.setCreationDate(new Date());
        Long id9 = daoSession.getItemDao().insert(item9);
        listItems.add(id9);
        //---------------------------------------------------------------------------
        i++;
        Item item10 = new Item();
        item10.setReference("dummydata"+i);
        item10.setName("Guantes G80 Nitrilo 18");
        item10.setDescription("");
        item10.setImagePath("img"+i);
        item10.setStock(100);
        item10.setQtyDefault(1);
        item10.setQtyInSpecificTime(999);
        item10.setEachInDays(1);
        item10.setCreationDate(new Date());
        Long id10 = daoSession.getItemDao().insert(item10);
        listItems.add(id10);
        //---------------------------------------------------------------------------
        i++;
        Item item11 = new Item();
        item11.setReference("dummydata"+i);
        item11.setName("Guantes G10 Nitrilo Flex");
        item11.setDescription("");
        item11.setImagePath("img"+i);
        item11.setStock(100);
        item11.setQtyDefault(1);
        item11.setQtyInSpecificTime(999);
        item11.setEachInDays(1);
        item11.setCreationDate(new Date());
        Long id11 = daoSession.getItemDao().insert(item11);
        listItems.add(id11);
        //---------------------------------------------------------------------------
        i++;
        Item item12 = new Item();
        item12.setReference("dummydata"+i);
        item12.setName("Guantes G60 Nitrilo Nivel 5");
        item12.setDescription("Protector auditivo tipo copa");
        item12.setImagePath("img"+i);
        item12.setStock(100);
        item12.setQtyDefault(1);
        item12.setQtyInSpecificTime(1);
        item12.setEachInDays(365);
        item12.setCreationDate(new Date());
        Long id12 = daoSession.getItemDao().insert(item12);
        listItems.add(id12);
        //---------------------------------------------------------------------------
        i++;
        Item item13 = new Item();
        item13.setReference("dummydata"+i);
        item13.setName("Protección corporal");
        item13.setDescription("");
        item13.setImagePath("img"+i);
        item13.setStock(100);
        item13.setQtyDefault(1);
        item13.setQtyInSpecificTime(1);
        item13.setEachInDays(365);
        item13.setCreationDate(new Date());
        Long id13 = daoSession.getItemDao().insert(item13);
        listItems.add(id13);
        //---------------------------------------------------------------------------
        i++;
        Item item14 = new Item();
        item14.setReference("dummydata"+i);
        item14.setName("Guantes G40 Nitrilo");
        item14.setDescription("");
        item14.setImagePath("img"+i);
        item14.setStock(100);
        item14.setQtyDefault(1);
        item14.setQtyInSpecificTime(1);
        item14.setEachInDays(180);
        item14.setCreationDate(new Date());
        Long id14 = daoSession.getItemDao().insert(item14);
        listItems.add(id14);
        //---------------------------------------------------------------------------
        i++;
        Item item15 = new Item();
        item15.setReference("dummydata"+i);
        item15.setName("Guantes G60 Nivel 3");
        item15.setDescription("");
        item15.setImagePath("img"+i);
        item15.setStock(100);
        item15.setQtyDefault(1);
        item15.setQtyInSpecificTime(1);
        item15.setEachInDays(180);
        item15.setCreationDate(new Date());
        Long id15 = daoSession.getItemDao().insert(item15);
        listItems.add(id15);
        //---------------------------------------------------------------------------
        i++;
        Item item16 = new Item();
        item16.setReference("dummydata"+i);
        item16.setName("Careta facial v90 the shield");
        item16.setDescription("");
        item16.setImagePath("img"+i);
        item16.setStock(100);
        item16.setQtyDefault(1);
        item16.setQtyInSpecificTime(1);
        item16.setEachInDays(1);
        item16.setCreationDate(new Date());
        Long id16 = daoSession.getItemDao().insert(item16);
        listItems.add(id16);
        //---------------------------------------------------------------------------
        i++;
        Item item17 = new Item();
        item17.setReference("dummydata"+i);
        item17.setName("Mangas Kevlar");
        item17.setDescription("");
        item17.setImagePath("img"+i);
        item17.setStock(100);
        item17.setQtyDefault(1);
        item17.setQtyInSpecificTime(1);
        item17.setEachInDays(180);
        item17.setCreationDate(new Date());
        Long id17 = daoSession.getItemDao().insert(item17);
        listItems.add(id17);
        //---------------------------------------------------------------------------
        i++;
        Item item18 = new Item();
        item18.setReference("dummydata"+i);
        item18.setName("Protector facial");
        item18.setDescription("");
        item18.setImagePath("img"+i);
        item18.setStock(100);
        item18.setQtyDefault(1);
        item18.setQtyInSpecificTime(1);
        item18.setEachInDays(180);
        item18.setCreationDate(new Date());
        Long id18 = daoSession.getItemDao().insert(item18);
        listItems.add(id18);
        //---------------------------------------------------------------------------
        i++;
        Item item19 = new Item();
        item19.setReference("dummydata"+i);
        item19.setName("Guante G40 Poliuretano");
        item19.setDescription("");
        item19.setImagePath("img"+i);
        item19.setStock(100);
        item19.setQtyDefault(1);
        item19.setQtyInSpecificTime(1);
        item19.setEachInDays(180);
        item19.setCreationDate(new Date());
        Long id19 = daoSession.getItemDao().insert(item19);
        listItems.add(id19);
        //---------------------------------------------------------------------------
        i++;
        Item item20 = new Item();
        item20.setReference("dummydata"+i);
        item20.setName("Protección corporal");
        item20.setDescription("");
        item20.setImagePath("img"+i);
        item20.setStock(100);
        item20.setQtyDefault(1);
        item20.setQtyInSpecificTime(1);
        item20.setEachInDays(180);
        item20.setCreationDate(new Date());
        Long id20 = daoSession.getItemDao().insert(item20);
        listItems.add(id20);
        //---------------------------------------------------------------------------
        i++;
        Item item21 = new Item();
        item21.setReference("dummydata"+i);
        item21.setName("Botas en caucho con puntera");
        item21.setDescription("");
        item21.setImagePath("img"+i);
        item21.setStock(100);
        item21.setQtyDefault(1);
        item21.setQtyInSpecificTime(1);
        item21.setEachInDays(365);
        item21.setCreationDate(new Date());
        Long id21 = daoSession.getItemDao().insert(item21);
        listItems.add(id21);
        //---------------------------------------------------------------------------
        i++;
        Item item22 = new Item();
        item22.setReference("dummydata"+i);
        item22.setName("Protección respiratoria");
        item22.setDescription("");
        item22.setImagePath("img"+i);
        item22.setStock(100);
        item22.setQtyDefault(1);
        item22.setQtyInSpecificTime(999);
        item22.setEachInDays(1);
        item22.setCreationDate(new Date());
        Long id22 = daoSession.getItemDao().insert(item22);
        listItems.add(id22);
        //---------------------------------------------------------------------------
        i++;
        Item item23 = new Item();
        item23.setReference("dummydata"+i);
        item23.setName("Guante G40 grasas y aceites");
        item23.setDescription("");
        item23.setImagePath("img"+i);
        item23.setStock(100);
        item23.setQtyDefault(1);
        item23.setQtyInSpecificTime(1);
        item23.setEachInDays(180);
        item23.setCreationDate(new Date());
        Long id23 = daoSession.getItemDao().insert(item23);
        listItems.add(id23);
        //---------------------------------------------------------------------------
        i++;
        Item item24 = new Item();
        item24.setReference("dummydata"+i);
        item24.setName("Guantes en carnaza largo");
        item24.setDescription("");
        item24.setImagePath("img"+i);
        item24.setStock(100);
        item24.setQtyDefault(1);
        item24.setQtyInSpecificTime(1);
        item24.setEachInDays(180);
        item24.setCreationDate(new Date());
        Long id24 = daoSession.getItemDao().insert(item24);
        listItems.add(id24);
        //---------------------------------------------------------------------------
        i++;
        Item item25 = new Item();
        item25.setReference("dummydata"+i);
        item25.setName("Manga en carnaza");
        item25.setDescription("");
        item25.setImagePath("img"+i);
        item25.setStock(100);
        item25.setQtyDefault(1);
        item25.setQtyInSpecificTime(1);
        item25.setEachInDays(180);
        item25.setCreationDate(new Date());
        Long id25 = daoSession.getItemDao().insert(item25);
        listItems.add(id25);
        //---------------------------------------------------------------------------
        i++;
        Item item26 = new Item();
        item26.setReference("dummydata"+i);
        item26.setName("Peto en carnaza");
        item26.setDescription("");
        item26.setImagePath("img"+i);
        item26.setStock(100);
        item26.setQtyDefault(1);
        item26.setQtyInSpecificTime(1);
        item26.setEachInDays(180);
        item26.setCreationDate(new Date());
        Long id26 = daoSession.getItemDao().insert(item26);
        listItems.add(id26);
        //---------------------------------------------------------------------------
        i++;
        Item item27 = new Item();
        item27.setReference("dummydata"+i);
        item27.setName("Prótección ignifuga");
        item27.setDescription("");
        item27.setImagePath("img"+i);
        item27.setStock(100);
        item27.setQtyDefault(1);
        item27.setQtyInSpecificTime(1);
        item27.setEachInDays(180);
        item27.setCreationDate(new Date());
        Long id27 = daoSession.getItemDao().insert(item27);
        listItems.add(id27);
        //---------------------------------------------------------------------------
        i++;
        Item item28 = new Item();
        item28.setReference("dummydata"+i);
        item28.setName("Traje encapsulado");
        item28.setDescription("");
        item28.setImagePath("img"+i);
        item28.setStock(100);
        item28.setQtyDefault(1);
        item28.setQtyInSpecificTime(1);
        item28.setEachInDays(365);
        item28.setCreationDate(new Date());
        Long id28 = daoSession.getItemDao().insert(item28);
        listItems.add(id28);
        //---------------------------------------------------------------------------
        i++;
        Item item29 = new Item();
        item29.setReference("dummydata"+i);
        item29.setName("Guantes dielectricos");
        item29.setDescription("");
        item29.setImagePath("img"+i);
        item29.setStock(100);
        item29.setQtyDefault(1);
        item29.setQtyInSpecificTime(1);
        item29.setEachInDays(180);
        item29.setCreationDate(new Date());
        Long id29 = daoSession.getItemDao().insert(item29);
        listItems.add(id29);


        return listItems;
    }
    /**
     *
     */
    private List<Long> createSectionsAndAreas(){
        List<Long> listSections = new ArrayList<>();
        // Spears --------------------------------------------------------------------
        Section spears = new Section();
        spears.setName("Spears");
        Long spearsId = daoSession.insert(spears);
        listSections.add(spearsId);

        Area convertidora = new Area();
        convertidora.setName("Convertidora");
        Long idConvertidora = daoSession.insert(convertidora);
        spears.setAreas(Constants.appendIdToString("",idConvertidora));

        Area quimicos = new Area();
        quimicos.setName("Quimicos");
        Long idQuimicos = daoSession.insert(quimicos);
        spears.setAreas(Constants.appendIdToString(spears.getAreas(),idQuimicos));

        Area pfm = new Area();
        pfm.setName("PFM");
        Long idPfm = daoSession.insert(pfm);
        spears.setAreas(Constants.appendIdToString(spears.getAreas(),idPfm));

        Area zonaGris = new Area();
        zonaGris.setName("Zona gris");
        Long idZonaGris = daoSession.insert(zonaGris);
        spears.setAreas(Constants.appendIdToString(spears.getAreas(),idZonaGris));

        Area okura = new Area();
        okura.setName("Okura");
        Long idOkura = daoSession.insert(okura);
        spears.setAreas(Constants.appendIdToString(spears.getAreas(),idOkura));

        daoSession.getSectionDao().update(spears);

        // WWTP --------------------------------------------------------------------
        Section wwtp = new Section();
        wwtp.setName("WWTP");
        Long wwtpId = daoSession.insert(wwtp);
        listSections.add(wwtpId);

        Area wwtpArea = new Area();
        wwtpArea.setName("WWTP");
        Long idWwtp = daoSession.insert(wwtpArea);
        wwtp.setAreas(Constants.appendIdToString("",idWwtp));

        daoSession.getSectionDao().update(wwtp);

        // Wipes --------------------------------------------------------------------
        Section wipes = new Section();
        wipes.setName("Wipes");
        Long wipesId = daoSession.insert(wipes);
        listSections.add(wipesId);

        Area convertidoraWipes = new Area();
        convertidoraWipes.setName("Convertidora");
        Long idConvertidoraWipes = daoSession.insert(convertidoraWipes);
        wipes.setAreas(Constants.appendIdToString("",idConvertidoraWipes));

        Area quimicosWipes = new Area();
        quimicosWipes.setName("Quimicos");
        Long idQuimicosWipes = daoSession.insert(quimicosWipes);
        wipes.setAreas(Constants.appendIdToString(spears.getAreas(),idQuimicosWipes));

        Area pfmWipes = new Area();
        pfmWipes.setName("PFM");
        Long idPfmWipes = daoSession.insert(pfmWipes);
        wipes.setAreas(Constants.appendIdToString(spears.getAreas(),idPfmWipes));

        Area zonaGrisWipes = new Area();
        zonaGrisWipes.setName("Zona gris");
        Long idZonaGrisWipes = daoSession.insert(zonaGrisWipes);
        wipes.setAreas(Constants.appendIdToString(spears.getAreas(),idZonaGrisWipes));

        daoSession.getSectionDao().update(wipes);

        // TM --------------------------------------------------------------------
        Section tm = new Section();
        tm.setName("TM");
        Long tmId = daoSession.insert(tm);
        listSections.add(tmId);

        Area maquina = new Area();
        maquina.setName("Maquina");
        Long idMaquina = daoSession.insert(maquina);
        tm.setAreas(Constants.appendIdToString("",idMaquina));

        Area shaftPuller = new Area();
        shaftPuller.setName("Zona gris");
        Long idShaftPuller = daoSession.insert(shaftPuller);
        tm.setAreas(Constants.appendIdToString(spears.getAreas(),idShaftPuller));

        daoSession.getSectionDao().update(tm);

        // COFORM --------------------------------------------------------------------
        Section coform = new Section();
        coform.setName("Coform");
        Long coformId = daoSession.insert(coform);
        listSections.add(coformId);

        Area formacion = new Area();
        formacion.setName("Formación");
        Long idFormacion = daoSession.insert(formacion);
        coform.setAreas(Constants.appendIdToString("",idFormacion));

        Area desenrrolladores = new Area();
        desenrrolladores.setName("Desenrrolladores");
        Long idDesenrrolladores = daoSession.insert(desenrrolladores);
        coform.setAreas(Constants.appendIdToString(spears.getAreas(),idDesenrrolladores));

        Area tcp = new Area();
        tcp.setName("TCP");
        Long idTcp = daoSession.insert(tcp);
        coform.setAreas(Constants.appendIdToString(spears.getAreas(),idTcp));

        daoSession.getSectionDao().update(coform);

        // CONVERSION --------------------------------------------------------------------
        Section conversion = new Section();
        conversion.setName("Conversion");
        Long conversionId = daoSession.insert(conversion);
        listSections.add(conversionId);

        Area convertidoraConv = new Area();
        convertidoraConv.setName("Convertidora");
        Long idConvertidoraConv = daoSession.insert(convertidoraConv);
        conversion.setAreas(Constants.appendIdToString("",idConvertidoraConv));

        Area empacadora = new Area();
        empacadora.setName("Empacadora");
        Long idEmpacadora = daoSession.insert(empacadora);
        conversion.setAreas(Constants.appendIdToString(spears.getAreas(),idEmpacadora));

        daoSession.getSectionDao().update(conversion);

        // RF --------------------------------------------------------------------
        Section rf = new Section();
        rf.setName("RF");
        Long rfId = daoSession.insert(rf);
        listSections.add(rfId);

        Area pulper = new Area();
        pulper.setName("Pulper");
        Long idPulper = daoSession.insert(pulper);
        rf.setAreas(Constants.appendIdToString("",idPulper));

        Area desalambrado = new Area();
        desalambrado.setName("Desalambrado");
        Long idDesalambrado = daoSession.insert(desalambrado);
        rf.setAreas(Constants.appendIdToString(spears.getAreas(),idDesalambrado));

        Area montacarga = new Area();
        montacarga.setName("Montacarga");
        Long idMontacarga = daoSession.insert(montacarga);
        rf.setAreas(Constants.appendIdToString(spears.getAreas(),idMontacarga));

        Area laboratorio = new Area();
        laboratorio.setName("Laboratorio");
        Long idLaboratorio = daoSession.insert(laboratorio);
        rf.setAreas(Constants.appendIdToString(spears.getAreas(),idLaboratorio));

        daoSession.getSectionDao().update(rf);

        // Mantanimiento --------------------------------------------------------------------
        Section mantenimiento = new Section();
        mantenimiento.setName("Mantenimiento");
        Long mantanimientoId = daoSession.insert(mantenimiento);
        listSections.add(mantanimientoId);

        Area electrico = new Area();
        electrico.setName("Electrico");
        Long idElectrico = daoSession.insert(electrico);
        mantenimiento.setAreas(Constants.appendIdToString("",idElectrico));

        Area mecanico = new Area();
        mecanico.setName("Mecánico");
        Long idMecanico = daoSession.insert(mecanico);
        mantenimiento.setAreas(Constants.appendIdToString(mantenimiento.getAreas(),idMecanico));

        daoSession.getSectionDao().update(mantenimiento);

        return listSections;
    }
}
