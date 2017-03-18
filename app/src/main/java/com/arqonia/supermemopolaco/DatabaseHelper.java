package com.arqonia.supermemopolaco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileLock;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Dictionary.db";
    public static final String TABLE_NAME = "dictionary";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "ESPANOL";
    public static final String COL_2 = "POLSKI";
    public static final String COL_3 = "LEVEL";
    public static final String COL_4 = "POINTS";
    public static final String COL_5 = "POINTS2";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public String getEspanolPoints(String polski) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from dictionary where polski='"+polski+"'",null);
        String str;
        res.moveToFirst();
        str = res.getString(4);
        return str;
    }

    public String getPolskiPoints(String espanol) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from dictionary where espanol='"+espanol+"'",null);
        String str;
        res.moveToFirst();
        str = res.getString(5);
        return str;
    }

    public ArrayList<String> getIdSet(int value, int points) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from dictionary where Level <= "+value+" and Points = "+points+"",null);
        int count = res.getCount();
        ArrayList<String> arrlist = new ArrayList<String>(count);
        try {
           while (res.moveToNext()) {
               arrlist.add(res.getString(0));
           }
        } finally {
            res.close();
        }

        if(arrlist.size() == 0) {
            arrlist.add("0");
        }
        return arrlist;
    }

    public String getEspanolToPolskiWord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String str;
        if(id>0) {
            Cursor res = db.rawQuery("select * from dictionary where ID=" + id + "", null);
            res.moveToNext();
            str = res.getString(1);
        } else {
            str = "0";
        }
        return str;
    }

    public String getPolskiToEspanolWord2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String str;
        if(id>0) {
            Cursor res = db.rawQuery("select * from dictionary where ID=" + id + "", null);
            res.moveToNext();
            str = res.getString(2);
        } else {
            str = "0";
        }
        return str;
    }

    public String getPolskiToEspanolWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from dictionary where ESPANOL='"+word+"'",null);
        String str;
        res.moveToFirst();
        str = res.getString(2);
        return str;
    }

    public String getEspanolToPolskiWord2(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from dictionary where POLSKI='"+word+"'",null);
        String str;
        res.moveToFirst();
        str = res.getString(1);
        return str;
    }

    public String getPolskiNumer() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select count(1) from dictionary where points2=4",null);
        String str;
        res.moveToFirst();
        str = res.getString(0);
        return str;
    }

    public String getEspanolNumero() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count(*) from dictionary where points = 4",null);
        String str;
        res.moveToFirst();
        str = res.getString(0);
        return str;
    }

    public boolean updateEspanolData(String polski,String points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,points);
        db.update(TABLE_NAME, contentValues, "polski = ?",new String[] { polski });
        return true;
    }

    public boolean updatePolskiData(String espanol,String points2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,points2);
        db.update(TABLE_NAME, contentValues, "espanol = ?",new String[] { espanol });
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER,ESPANOL TEXT,POLSKI TEXT,LEVEL INTEGER,POINTS INTEGER,POINTS2 INTEGER)");
        db.beginTransaction();
        db.execSQL("insert into " + TABLE_NAME +" values" +
                "('1','la gente','ludzie','1','0','0'),\n" +
                "('2','el cuerpo','ciało','1','0','0'),\n" +
                "('3','el cuello','szyja','1','0','0'),\n" +
                "('4','el pecho','pierś','2','0','0'),\n" +
                "('5','el abdomen','brzuch','2','0','0'),\n" +
                "('6','la pierna','noga','1','0','0'),\n" +
                "('7','la cabeza','głowa','1','0','0'),\n" +
                "('8','el brazo','ramię','2','0','0'),\n" +
                "('9','el codo','łokieć','2','0','0'),\n" +
                "('10','el antebrazo','przedramię','2','0','0'),\n" +
                "('11','el muslo','udo','2','0','0'),\n" +
                "('12','la rodilla','kostka','3','0','0'),\n" +
                "('13','el hombro','bark','3','0','0'),\n" +
                "('14','la mano','ręka','1','0','0'),\n" +
                "('15','la nalga','pośladek','2','0','0'),\n" +
                "('16','la espalda','plecy','1','0','0'),\n" +
                "('17','el pulgar','kciuk','1','0','0'),\n" +
                "('18','el talón','pięta','2','0','0'),\n" +
                "('19','la cara','twarz','1','0','0'),\n" +
                "('20','el pelo','włosy','1','0','0'),\n" +
                "('21','la piel','skóra','2','0','0'),\n" +
                "('22','la frente','czoło','1','0','0'),\n" +
                "('23','la ceja','brew','2','0','0'),\n" +
                "('24','el ojo','oko','1','0','0'),\n" +
                "('25','la nariz','nos','1','0','0'),\n" +
                "('26','la oreja','ucho','1','0','0'),\n" +
                "('27','la mejilla','policzek','3','0','0'),\n" +
                "('28','la boca','usta','1','0','0'),\n" +
                "('29','la arruga','zmarszczka','3','0','0'),\n" +
                "('30','el indice','palec wskazujący','2','0','0'),\n" +
                "('31','el puño','pięść','2','0','0'),\n" +
                "('32','el pie','stopa','1','0','0'),\n" +
                "('33','el músculo','mięsień','1','0','0'),\n" +
                "('34','el biceps','biceps','2','0','0'),\n" +
                "('35','el abdominal','mięsień brzucha','2','0','0'),\n" +
                "('36','el triceps','triceps','3','0','0'),\n" +
                "('37','el esqueleto','szkielet','3','0','0'),\n" +
                "('38','la costilla','żebro','3','0','0'),\n" +
                "('39','el cráneo','czaszka','3','0','0'),\n" +
                "('40','la espina dorsal','kręgosłup','2','0','0'),\n" +
                "('41','el órgano interno','narząd wewnętrzny','2','0','0'),\n" +
                "('42','el cerebro','mózg','1','0','0'),\n" +
                "('43','el higado','wątroba','2','0','0'),\n" +
                "('44','el corazón','serce','1','0','0'),\n" +
                "('45','el estómago','żołądek','1','0','0'),\n" +
                "('46','la lengua','język','1','0','0'),\n" +
                "('47','la garganta','gardło','2','0','0'),\n" +
                "('48','la familia','rodzina','1','0','0'),\n" +
                "('49','la abuela','babcia','2','0','0'),\n" +
                "('50','el abuelo','dziadek','2','0','0'),\n" +
                "('51','el tío','wujek','2','0','0'),\n" +
                "('52','la tía','ciotka','2','0','0'),\n" +
                "('53','el padre','ojciec','1','0','0'),\n" +
                "('54','la madre','matka','1','0','0'),\n" +
                "('55','el primo','kuzyn','3','0','0'),\n" +
                "('56','el hermano','brat','1','0','0'),\n" +
                "('57','la hermana','siostra','1','0','0'),\n" +
                "('58','la mujer','kobieta','1','0','0'),\n" +
                "('59','el hijo','syn','1','0','0'),\n" +
                "('60','la hija','córka','1','0','0'),\n" +
                "('61','el nieto','wnuk','2','0','0'),\n" +
                "('62','la nieta','wnuczka','2','0','0'),\n" +
                "('63','el marido','mąż','1','0','0'),\n" +
                "('64','el niño','dziecko','1','0','0'),\n" +
                "('65','la genración','pokolenie','2','0','0'),\n" +
                "('66','los padres','rodzice','2','0','0'),\n" +
                "('67','los abueleros','dziadkowie','3','0','0'),\n" +
                "('68','los parientes','krewni','3','0','0'),\n" +
                "('69','la madrasta','macocha','3','0','0'),\n" +
                "('70','los gemelos','bliźnięta','4','0','0'),\n" +
                "('71','la sugra','teściowa','3','0','0'),\n" +
                "('72','el suegro','teść','3','0','0'),\n" +
                "('73','el bebé','niemowlę','2','0','0'),\n" +
                "('74','el cuñado','szwagier','2','0','0'),\n" +
                "('75','el chico','chłopiec','1','0','0'),\n" +
                "('76','la chica','dziewczynka','1','0','0'),\n" +
                "('77','la señorita','panna','1','0','0'),\n" +
                "('78','la señora','pani','1','0','0'),\n" +
                "('79','el señor','pan','1','0','0'),\n" +
                "('80','la adolescente','nastolatka','3','0','0'),\n" +
                "('81','el adulto','dorosły','3','0','0'),\n" +
                "('82','el hombre','mężczyzna','1','0','0'),\n" +
                "('83','el relación','związek','2','0','0'),\n" +
                "('84','el jefe','szef','2','0','0'),\n" +
                "('85','la secretaria personal','asystentka','3','0','0'),\n" +
                "('86','el empleado','pracownik','2','0','0'),\n" +
                "('87','la empresaria','pracodawca','2','0','0'),\n" +
                "('88','la oficina','biuro','2','0','0'),\n" +
                "('89','el amigo','przyjaciel','1','0','0'),\n" +
                "('90','la prometida','narzeczona','3','0','0'),\n" +
                "('91','el prometido','narzeczony','3','0','0'),\n" +
                "('92','la emoción','emocja','1','0','0'),\n" +
                "('93','contento','szczęśliwy','1','0','0'),\n" +
                "('94','triste','smutny','2','0','0'),\n" +
                "('95','entusiasmado','podekscytowany','2','0','0'),\n" +
                "('96','abburido','znudzony','2','0','0'),\n" +
                "('97','sorprendido','zaskoczony','2','0','0'),\n" +
                "('98','asustado','przestraszony','2','0','0'),\n" +
                "('99','enfadado','zły','1','0','0'),\n" +
                "('100','confuso','zdezorientowany','2','0','0'),\n" +
                "('101','preocupado','zmartwiony','2','0','0'),\n" +
                "('102','nervioso','nerwowy','1','0','0'),\n" +
                "('103','orgulloso','dumny','2','0','0'),\n" +
                "('104','seguro de sí mismo','pewny siebie','3','0','0'),\n" +
                "('105','avergonzado','zakłopotany','3','0','0'),\n" +
                "('106','tímido','nieśmiały','2','0','0'),\n" +
                "('107','llorar','płakać','1','0','0'),\n" +
                "('108','desmayarse','zemdleć','2','0','0'),\n" +
                "('109','rreír','śmiać się','1','0','0'),\n" +
                "('110','horrorizado','wstrząśnięty','2','0','0'),\n" +
                "('111','gritar','krzyczeć','3','0','0'),\n" +
                "('112','bostezar','ziewać','4','0','0'),\n" +
                "('113','nacer','urodzić się','3','0','0'),\n" +
                "('114','hacer amigos','zaprzyjaźnić się','2','0','0'),\n" +
                "('115','conseguir un trabajo','dostać pracę','4','0','0'),\n" +
                "('116','enamorarse','zakochać się','3','0','0'),\n" +
                "('117','la boda','ślub','1','0','0'),\n" +
                "('118','el divorcio','rozwód','1','0','0'),\n" +
                "('119','el funeral','pogrzeb','1','0','0'),\n" +
                "('120','casarse','wziąć ślub','2','0','0'),\n" +
                "('121','el bautizo','chrzest','3','0','0'),\n" +
                "('122','el aniversrio','rocznica','2','0','0'),\n" +
                "('123','emigrar','wyemigrować','2','0','0'),\n" +
                "('124','morir','umrzeć','2','0','0'),\n" +
                "('125','hacer testamento','spisać testament','2','0','0'),\n" +
                "('126','fiesta','święto','1','0','0'),\n" +
                "('127','el cumpleaños','urodziny','1','0','0'),\n" +
                "('128','el regalo','prezent','1','0','0'),\n" +
                "('129','la navidad','boże narodzenie','1','0','0'),\n" +
                "('130','el año nuevo','nowy rok','1','0','0'),\n" +
                "('131','el carnaval','karnawał','1','0','0'),\n" +
                "('132','la semana santa','wielki tydzień','2','0','0'),\n" +
                "('133','el aspecto','wyglad','2','0','0'),\n" +
                "('134','las manoplas','rękawiczki jednopalczaste','5','0','0'),\n" +
                "('135','el pijama','piżama','3','0','0'),\n" +
                "('136','la bufanda','szalik','2','0','0'),\n" +
                "('137','el logotipo','logo','4','0','0'),\n" +
                "('138','las zapatillas de deporte','buty sportowe','5','0','0'),\n" +
                "('139','el chándal','dres','5','0','0'),\n" +
                "('140','las zapatillas','kapcie','4','0','0'),\n" +
                "('141','el caballero','dżentelmen','3','0','0'),\n" +
                "('142','la corbata','krawat','3','0','0'),\n" +
                "('143','la chaqueta','marynarka','3','0','0'),\n" +
                "('144','el bolsillo','kieszeń','4','0','0'),\n" +
                "('145','los pantalones','spodnie','2','0','0'),\n" +
                "('146','el traje de chaqueta','garnitur','3','0','0'),\n" +
                "('147','el jersey','sweter','2','0','0'),\n" +
                "('148','largo','długi','1','0','0'),\n" +
                "('149','corto','krótki','1','0','0'),\n" +
                "('150','la camisa','koszula','1','0','0'),\n" +
                "('151','los vaqueros','dżinsy','1','0','0'),\n" +
                "('152','la camiseta de tirantes','podkoszulek','5','0','0'),\n" +
                "('153','los calcetines','skarpetki','3','0','0'),\n" +
                "('154','la falda','spódnica','2','0','0'),\n" +
                "('155','el vestido','sukienka','2','0','0'),\n" +
                "('156','la blusa','bluzka','2','0','0'),\n" +
                "('157','el sujetador','biustonosz','5','0','0'),\n" +
                "('158','la gorra','czapka','3','0','0'),\n" +
                "('159','el sombrerro','kapelusz','2','0','0'),\n" +
                "('160','el cinturón','pasek','2','0','0'),\n" +
                "('161','el paraguas','parasol','2','0','0'),\n" +
                "('162','los guantes','rękawiczki','4','0','0'),\n" +
                "('163','el reloj','zegarek','2','0','0'),\n" +
                "('164','la cartera','portfel','2','0','0'),\n" +
                "('165','el monedero','portmonetka','3','0','0'),\n" +
                "('166','la mochila','plecak','2','0','0'),\n" +
                "('167','los zapatos','buty','1','0','0'),\n" +
                "('168','la sandalia','sandał','3','0','0'),\n" +
                "('169','el pelo','włosy','2','0','0'),\n" +
                "('170','la peluquera','fryzjer','3','0','0'),\n" +
                "('171','las tijeras','nożyczki','3','0','0'),\n" +
                "('172','cortar','ciąć','3','0','0'),\n" +
                "('173','la cliente','klientka','2','0','0'),\n" +
                "('174','el gel','żel','5','0','0'),\n" +
                "('175','rizado','kręcone','3','0','0'),\n" +
                "('176','lacio','proste','3','0','0'),\n" +
                "('177','rubio','blond','3','0','0'),\n" +
                "('178','castaño','ciemny brąz','3','0','0'),\n" +
                "('179','pelirrojo','rudy','3','0','0'),\n" +
                "('180','negro','czarny','1','0','0'),\n" +
                "('181','gris','siwy','3','0','0'),\n" +
                "('182','blanco','biały','1','0','0'),\n" +
                "('183','el barbero','golarz','4','0','0'),\n" +
                "('184','la caspa','łupież','6','0','0'),\n" +
                "('185','graso','tłusty','4','0','0'),\n" +
                "('186','normal','normalny','2','0','0'),\n" +
                "('187','seco','suchy','2','0','0'),\n" +
                "('188','el maquillaje','makijaż','4','0','0'),\n" +
                "('189','moreno','ciemny','2','0','0'),\n" +
                "('190','claro','jasny','2','0','0'),\n" +
                "('191','el tatuaje','tatuaż','4','0','0'),\n" +
                "('192','la salud','zdrowie','2','0','0'),\n" +
                "('193','la enfermedad','choroba','3','0','0'),\n" +
                "('194','la friebre','gorączka','3','0','0'),\n" +
                "('195','el dolor de cabeza','ból głowy','4','0','0'),\n" +
                "('196','la tos','kaszel','4','0','0'),\n" +
                "('197','el estornudo','kichnięcie','4','0','0'),\n" +
                "('198','el resfriado','przeziębienie','4','0','0'),\n" +
                "('199','la gripe','grypa','4','0','0'),\n" +
                "('200','la asma','astma','4','0','0'),\n" +
                "('201','la náusea','mdłości','4','0','0'),\n" +
                "('202','la alergia','alergia','4','0','0'),\n" +
                "('203','la diabetes','cukrzyca','4','0','0'),\n" +
                "('204','el virus','wirus','3','0','0'),\n" +
                "('205','el dolor de estómago','ból żołądka','4','0','0'),\n" +
                "('206','la epilepsia','padaczka','5','0','0'),\n" +
                "('207','el médico','lekarz','2','0','0'),\n" +
                "('208','la receta','recepta','3','0','0'),\n" +
                "('209','la paciente','pacjentka','4','0','0'),\n" +
                "('210','la efermera','pielęgniarka','4','0','0'),\n" +
                "('211','la báscula','waga','4','0','0'),\n" +
                "('212','la cita','wizyta','5','0','0'),\n" +
                "('213','la sala de espera','poczekalnia','6','0','0'),\n" +
                "('214','la consulta','gabinet','6','0','0'),\n" +
                "('215','la inoculación','szczepienie','6','0','0'),\n" +
                "('216','el termómetro','termometr','4','0','0'),\n" +
                "('217','la fractura','złamanie','6','0','0'),\n" +
                "('218','la urgenica','nagły wypadek','6','0','0'),\n" +
                "('219','la herida','rana','4','0','0'),\n" +
                "('220','la hermorragia','krwotok','5','0','0'),\n" +
                "('221','la conmoción','wstrząs mózgu','5','0','0'),\n" +
                "('222','el aacidente','wypadek','5','0','0'),\n" +
                "('223','la tirita','plaster','5','0','0'),\n" +
                "('224','la venda','bandaż','6','0','0'),\n" +
                "('225','la reanimación','reanimacja','5','0','0'),\n" +
                "('226','el shock','wstrząs','5','0','0'),\n" +
                "('227','inconsciente','nieprzytomny','5','0','0'),\n" +
                "('228','el pulso','tętno','5','0','0'),\n" +
                "('229','la respiración','oddychanie','4','0','0'),\n" +
                "('230','estéril','sterylny','5','0','0'),\n" +
                "('231','el hospital','szpital','1','0','0'),\n" +
                "('232','la anestesista','anestezjolog','5','0','0'),\n" +
                "('233','la inyección','zastrzyk','5','0','0'),\n" +
                "('234','la radiografía','rendgen','5','0','0'),\n" +
                "('235','la ecografía','USG','6','0','0'),\n" +
                "('236','la operación','operacja','4','0','0'),\n" +
                "('237','ingresado','przyjęty','6','0','0'),\n" +
                "('238','dado de alta','wypisany','6','0','0'),\n" +
                "('239','la clínica','klinika','6','0','0'),\n" +
                "('240','la cardiologia','kardiologia','6','0','0'),\n" +
                "('241','la ortopedia','ortopedia','6','0','0'),\n" +
                "('242','la ginecología','ginekologia','6','0','0'),\n" +
                "('243','la fisioterapia','fizjoterapia','6','0','0'),\n" +
                "('244','la dermatología','dermatologia','6','0','0'),\n" +
                "('245','la pediatría','pediatria','6','0','0'),\n" +
                "('246','la radiología','radiologia','6','0','0'),\n" +
                "('247','la cirugía','chirurgia','6','0','0'),\n" +
                "('248','la maternidad','oddział położniczy','6','0','0'),\n" +
                "('249','la psiquiatría','psychiatria','6','0','0'),\n" +
                "('250','la oftalmología','okulistyka','5','0','0'),\n" +
                "('251','la neurología','neurologia','5','0','0'),\n" +
                "('252','la oncología','onkologia','5','0','0'),\n" +
                "('253','la urología','urologia','5','0','0'),\n" +
                "('254','el volante','skierowanie','6','0','0'),\n" +
                "('255','el análysis','badanie','4','0','0'),\n" +
                "('256','el resultado','wynik','2','0','0'),\n" +
                "('257','el dentista','dentysta','2','0','0'),\n" +
                "('258','el diente','ząb','4','0','0'),\n" +
                "('259','la revisión','przegląd','4','0','0'),\n" +
                "('260','el nervio','nerw','2','0','0'),\n" +
                "('261','la corona','korona','2','0','0'),\n" +
                "('262','la extracción','usunięcie','5','0','0'),\n" +
                "('263','el cepillo de dientes','szczoteczka','4','0','0'),\n" +
                "('264','el examen de ojos','badanie wzroku','4','0','0'),\n" +
                "('265','el óptico','optyk','3','0','0'),\n" +
                "('266','las gafas','okulary','2','0','0'),\n" +
                "('267','las gafas de sol','okulary przeciwsłoneczne','2','0','0'),\n" +
                "('268','el astigmatismo','astygmatyzm','6','0','0'),\n" +
                "('269','la lágrima','łza','5','0','0'),\n" +
                "('270','la vista','wzrok','4','0','0'),\n" +
                "('271','el embrazo','ciąża','6','0','0'),\n" +
                "('272','el nacimiento','narodziny','5','0','0'),\n" +
                "('273','el parto','poród','5','0','0'),\n" +
                "('274','el masaje','masaż','4','0','0'),\n" +
                "('275','el yoga','joga','4','0','0'),\n" +
                "('276','la meditación','medytacja','6','0','0'),\n" +
                "('277','el terapeuta','terapeuta','4','0','0'),\n" +
                "('278','la psicoterapia','psychoterapia','6','0','0'),\n" +
                "('279','la casa','dom','1','0','0'),\n" +
                "('280','el tejado','dach','3','0','0'),\n" +
                "('281','la chimenea','komin','2','0','0'),\n" +
                "('282','la ventana','okno','1','0','0'),\n" +
                "('283','el garaje','garaż','1','0','0'),\n" +
                "('284','el sótano','piwnica','4','0','0'),\n" +
                "('285','el patio','podwórze','2','0','0'),\n" +
                "('286','el piso','podłoga','2','0','0'),\n" +
                "('287','el propietario','właściciel','3','0','0'),\n" +
                "('288','el buzón','skrzynka na listy','4','0','0'),\n" +
                "('289','el alquiler','czynsz','4','0','0'),\n" +
                "('290','la habitación','pokój','2','0','0'),\n" +
                "('291','la puerta','drzwi','1','0','0'),\n" +
                "('292','el suelo','podłoga','3','0','0'),\n" +
                "('293','el recibidor','przedpokój','3','0','0'),\n" +
                "('294','la escalera','schody','2','0','0'),\n" +
                "('295','el edificio','blok mieszkalny','2','0','0'),\n" +
                "('296','el timbre','dzwonek','2','0','0'),\n" +
                "('297','la cadena','łańcuch','4','0','0'),\n" +
                "('298','la calle','klucz','2','0','0'),\n" +
                "('299','el ascensor','winda','1','0','0'),\n" +
                "('300','el radiador','kaloryfer','1','0','0'),\n" +
                "('301','la estufa','grzejnik','2','0','0'),\n" +
                "('302','el ventilador','wentylator','2','0','0'),\n" +
                "('303','la bombilla','żarówka','1','0','0'),\n" +
                "('304','el echufe macho','wtyczka','4','0','0'),\n" +
                "('305','los cables','przewody','3','0','0'),\n" +
                "('306','la corriente alterna','prąd zmienny','3','0','0'),\n" +
                "('307','la corriente continua','prąd stały','3','0','0'),\n" +
                "('308','el generador','generator','3','0','0'),\n" +
                "('309','el voltaje','napięcie','4','0','0'),\n" +
                "('310','la corriente eléctrica','prąd elektryczny','4','0','0'),\n" +
                "('311','el echufe hembra','gniazdko','4','0','0'),\n" +
                "('312','el interruptor','włącznik','4','0','0'),\n" +
                "('313','el grifo','kran','4','0','0'),\n" +
                "('314','el tanque','zbiornik','4','0','0'),\n" +
                "('315','la cisterna','cysterna','4','0','0'),\n" +
                "('316','el pedal','pedał','3','0','0'),\n" +
                "('317','el cubo de basura','kosz na śmieci','3','0','0'),\n" +
                "('318','el cuarto de estar','salon','3','0','0'),\n" +
                "('319','la lámpara','lampa','2','0','0'),\n" +
                "('320','el cuadro','obraz','2','0','0'),\n" +
                "('321','el jarrón','wazon','4','0','0'),\n" +
                "('322','la mesa de café','stolik kawowy','3','0','0'),\n" +
                "('323','el sillón','fotel','2','0','0'),\n" +
                "('324','la moqueta','dywan','3','0','0'),\n" +
                "('325','la cortina','zasłona','3','0','0'),\n" +
                "('326','el visillo','firanka','3','0','0'),\n" +
                "('327','el despacho','gabinet','3','0','0'),\n" +
                "('328','el comedor','jadalnia','1','0','0'),\n" +
                "('329','la vela','świeca','3','0','0'),\n" +
                "('330','la mesa','stół','1','0','0'),\n" +
                "('331','la servilleta','serweta','1','0','0'),\n" +
                "('332','la silla','krzesło','1','0','0'),\n" +
                "('333','el espejo','lustro','1','0','0'),\n" +
                "('334','el desayuno','śniadanie','1','0','0'),\n" +
                "('335','la cena','kolacja','1','0','0'),\n" +
                "('336','comer','jeść','1','0','0'),\n" +
                "('337','lleno','najedzony','3','0','0'),\n" +
                "('338','el invitado','gość','4','0','0'),\n" +
                "('339','el anfitrión','gospodarz','4','0','0'),\n" +
                "('340','la anfitriona','gospodyni','4','0','0'),\n" +
                "('341','hambriento','głodny','5','0','0'),\n" +
                "('342','la comida','posiłek','1','0','0'),\n" +
                "('343','la ración','porcja','2','0','0'),\n" +
                "('344','servir','podawać','2','0','0'),\n" +
                "('345','la taza','kubek','2','0','0'),\n" +
                "('346','el plato','talerz','1','0','0'),\n" +
                "('347','la tetera','czajnik herbaciany','4','0','0'),\n" +
                "('348','la jarra','dzbanek','4','0','0'),\n" +
                "('349','el vaso','szklanka','2','0','0'),\n" +
                "('350','el tenedor','widelec','1','0','0'),\n" +
                "('351','la cuchara','łyżka','1','0','0'),\n" +
                "('352','el cuchillo','nóż','1','0','0'),\n" +
                "('353','la copa','kieliszek','1','0','0'),\n" +
                "('354','los cubiertos','sztućce','2','0','0'),\n" +
                "('355','la cocina','kuchnia','1','0','0'),\n" +
                "('356','la isla','wyspa','1','0','0'),\n" +
                "('357','el cajón','szuflada','4','0','0'),\n" +
                "('358','el microondas','mikrofalówka','3','0','0'),\n" +
                "('359','el hervidor','czanik','2','0','0'),\n" +
                "('360','el frigorifico','lodówka','1','0','0'),\n" +
                "('361','congelar','zamrozić','2','0','0'),\n" +
                "('362','cocinar','gotowanie','1','0','0'),\n" +
                "('363','pelar','obierać','4','0','0'),\n" +
                "('364','cortar','kroić','3','0','0'),\n" +
                "('365','remover','mieszać','2','0','0'),\n" +
                "('366','hervir','gotować','2','0','0'),\n" +
                "('367','freir','smażyć','2','0','0'),\n" +
                "('368','cocer','gotować','2','0','0'),\n" +
                "('369','asar','piec','5','0','0'),\n" +
                "('370','la sartén','patelnia','4','0','0'),\n" +
                "('371','el dormitorio','sypialnia','1','0','0'),\n" +
                "('372','la almohada','poduszka','2','0','0'),\n" +
                "('373','la cómoda','komoda','2','0','0'),\n" +
                "('374','la cama','łóżko','1','0','0'),\n" +
                "('375','la sábana','prześcieradło','2','0','0'),\n" +
                "('376','la manta','koc','2','0','0'),\n" +
                "('377','levantarse','wstać','5','0','0'),\n" +
                "('378','dormirse','iść spać','5','0','0'),\n" +
                "('379','hacer la cama','zrobić łóżko','5','0','0'),\n" +
                "('380','individual','pojedynczy','2','0','0'),\n" +
                "('381','el baño','łazienka','1','0','0'),\n" +
                "('382','la bañera','wanna','2','0','0'),\n" +
                "('383','el lavabo','umywalka','3','0','0'),\n" +
                "('384','el armario de las medicinas','apteczka','5','0','0'),\n" +
                "('385','la cortina de ducha','zasłona prysznicoa','6','0','0'),\n" +
                "('386','la pasta de dientes','pasta do zębów','3','0','0'),\n" +
                "('387','el cepillo de dientes','szczoteczka do zębów','3','0','0'),\n" +
                "('388','el jabón','mydło','2','0','0'),\n" +
                "('389','las toallas','ręczniki','3','0','0'),\n" +
                "('390','el orinal','nocnik','5','0','0'),\n" +
                "('391','la cuna','łóżeczko dziecięce','6','0','0'),\n" +
                "('392','secar','suszyć','5','0','0'),\n" +
                "('393','la lavadora','pralka','2','0','0'),\n" +
                "('394','la plancha','żelazko','4','0','0'),\n" +
                "('395','la ropa','ubranie','2','0','0'),\n" +
                "('396','planchar','prasować','4','0','0'),\n" +
                "('397','centrifugar','wirować','6','0','0'),\n" +
                "('398','el cubo','wiaderko','5','0','0'),\n" +
                "('399','limpiar','czyścić','2','0','0'),\n" +
                "('400','fregar','myć','4','0','0'),\n" +
                "('401','barrer','zamiatać','6','0','0'),\n" +
                "('402','el martillo','młotek','3','0','0'),\n" +
                "('403','la batería','akumulator','2','0','0'),\n" +
                "('404','el taller','warsztat','5','0','0'),\n" +
                "('405','la madera de pino','drewno sosnowe','6','0','0'),\n" +
                "('406','pintar','malować','4','0','0'),\n" +
                "('407','mate','matowy','6','0','0'),\n" +
                "('408','las botas de goma','kalosze','6','0','0'),\n" +
                "('409','el jardín','ogród','3','0','0'),\n" +
                "('410','el cortacésped','kosiarka','6','0','0'),\n" +
                "('411','cortar el césped','kosić','6','0','0'),\n" +
                "('412','el césped','trawnik','5','0','0'),\n" +
                "('413','el jardinero','ogrodnik','4','0','0'),\n" +
                "('414','cavar','kopać','6','0','0'),\n" +
                "('415','regar','podleać','5','0','0'),\n" +
                "('416','cultivar','uprawiać','6','0','0'),\n" +
                "('417','coger','zbierać','6','0','0'),\n" +
                "('418','los servicios','usługi','3','0','0'),\n" +
                "('419','la ambulancia','karetka','3','0','0'),\n" +
                "('420','la estación de policía',' posterunek policji','3','0','0'),\n" +
                "('421','el uniforme','mundur','3','0','0'),\n" +
                "('422','las luces','światła','4','0','0'),\n" +
                "('423','el arresto','areszt','5','0','0'),\n" +
                "('424','la policía','policja','2','0','0'),\n" +
                "('425','el robo','kradzież','3','0','0'),\n" +
                "('426','el cargo','oskarżenie','5','0','0'),\n" +
                "('427','la denuncia','skarga','6','0','0'),\n" +
                "('428','el crimen','przestępstwo','4','0','0'),\n" +
                "('429','la huella dactilar','odcisk palca','6','0','0'),\n" +
                "('430','el comisario','inspektor','6','0','0'),\n" +
                "('431','el investigación','śledztwo','6','0','0'),\n" +
                "('432','el sospechoso','podejrzany','6','0','0'),\n" +
                "('433','el bombero','strażak','5','0','0'),\n" +
                "('434','el casco','kask','4','0','0'),\n" +
                "('435','el extintor','gaśnica','4','0','0'),\n" +
                "('436','el detector de humo','czujnik dymu','5','0','0'),\n" +
                "('437','el banco','bank','1','0','0'),\n" +
                "('438','la ventanilla','okienko','1','0','0'),\n" +
                "('439','el cajero','kasjer','2','0','0'),\n" +
                "('440','el billete','bilet','1','0','0'),\n" +
                "('441','la tarjeta de crédito','karta kredytowa','1','0','0'),\n" +
                "('442','el dinero','pieniądze','1','0','0'),\n" +
                "('443','el cajero automático','bankomat','4','0','0'),\n" +
                "('444','los ahorros','oszczędności','4','0','0'),\n" +
                "('445','el credito hipotecario','kredyt hipoteczny','6','0','0'),\n" +
                "('446','ingresar','wpłacać','6','0','0'),\n" +
                "('447','el pin','pin','6','0','0'),\n" +
                "('448','la cambio','kantor','2','0','0'),\n" +
                "('449','las finanzas','finanse','2','0','0'),\n" +
                "('450','las acciones','akcje','4','0','0'),\n" +
                "('451','el cliente','klient','4','0','0'),\n" +
                "('452','correo','poczta','1','0','0'),\n" +
                "('453','el sello','znaczek','2','0','0'),\n" +
                "('454','el cartero','listonosz','2','0','0'),\n" +
                "('455','el sobre','koperta','3','0','0'),\n" +
                "('456','la carta','list','3','0','0'),\n" +
                "('457','la firm','podpis','2','0','0'),\n" +
                "('458','el paquete','paczka','2','0','0'),\n" +
                "('459','el mensajero','kurier','6','0','0'),\n" +
                "('460','comunicando','zajęty','4','0','0'),\n" +
                "('461','contestar','odebrać telefon','4','0','0'),\n" +
                "('462','apagado','rozłączony','6','0','0'),\n" +
                "('463','el hotel','hotel','1','0','0'),\n" +
                "('464','la recepcionista','recepcjonistka','1','0','0'),\n" +
                "('465','el huésped','gość','5','0','0'),\n" +
                "('466','el botones','bagażowy','5','0','0'),\n" +
                "('467','el servicio de limpieza','serwis sprzątający','5','0','0'),\n" +
                "('468','el gimnasio','siłownia','4','0','0'),\n" +
                "('469','la piscina','basen','3','0','0'),\n" +
                "('470','el restaurante','restauracja','2','0','0'),\n" +
                "('471','las compras','zakupy','4','0','0'),\n" +
                "('472','el centro comercial','centrum handlowe','3','0','0'),\n" +
                "('473','la planta','piętro','3','0','0'),\n" +
                "('474','la tienda','sklep','1','0','0'),\n" +
                "('475','la planta baja','parter','2','0','0'),\n" +
                "('476','la escalera mecánica',' ruchome schody','3','0','0'),\n" +
                "('477','la sección infantil','dział dziecięcy','6','0','0'),\n" +
                "('478','el dependiente','sprzedawca','2','0','0'),\n" +
                "('479','los productos de belleza','kosmetyki','6','0','0'),\n" +
                "('480','la lancería','bielizna','6','0','0'),\n" +
                "('481','la perfumería','perfumeria','5','0','0'),\n" +
                "('482','el supermercado','suprmarket','2','0','0'),\n" +
                "('483','el pacillo','przejście','2','0','0'),\n" +
                "('484','el carro','wóz','2','0','0'),\n" +
                "('485','la pandería','pieczywo','4','0','0'),\n" +
                "('486','los cereales','płatki śniadaniowe','5','0','0'),\n" +
                "('487','la verdura','warzywa','2','0','0'),\n" +
                "('488','la fruta','owoce','2','0','0'),\n" +
                "('489','la carne','mięso','2','0','0'),\n" +
                "('490','las bebidas','napoje','4','0','0'),\n" +
                "('491','la farmacia','apteka','2','0','0'),\n" +
                "('492','los desodorantes','dezodoranty','4','0','0'),\n" +
                "('493','el farmacéutico','aptekarz','4','0','0'),\n" +
                "('494','la crema protectora',' krem z filtrem','3','0','0'),\n" +
                "('495','el cápsula','kapsuła','4','0','0'),\n" +
                "('496','la píldora','tabletka','4','0','0'),\n" +
                "('497','la crema','krem','4','0','0'),\n" +
                "('498','el hierro','żelazo','5','0','0'),\n" +
                "('499','el calcio','wapń','5','0','0');");
        db.setTransactionSuccessful();
        db.endTransaction();
/*
        db.execSQL("Begin; insert into " + TABLE_NAME +" values" +
                "('500','el magnesio','magnez','6','0','0')," +
                "('501','la insulina','insulina','6','0','0'),\n" +
                "('502','la fecha de caducidad','data ważności','5','0','0'),\n" +
                "('503','la diarrea','biegunka','5','0','0'),\n" +
                "('504','el medicamento','lek','5','0','0'),\n" +
                "('505','la floristería','kwiaciarnia','5','0','0'),\n" +
                "('506','la rosa','róża','2','0','0'),\n" +
                "('507','el tulipán','tulipan','2','0','0'),\n" +
                "('508','los cigarillos','papierosy','4','0','0'),\n" +
                "('509','la papelera','kosz na śmieci','4','0','0'),\n" +
                "('510','el helado','lody','2','0','0'),\n" +
                "('511','la revista czasopismo','3','0','0'),\n" +
                "('512','el periódico','gazeta','3','0','0'),\n" +
                "('513','el machero','zapalniczka','5','0','0'),\n" +
                "('514','el puro','cygaro','5','0','0'),\n" +
                "('515','la pastelería','cukiernia','3','0','0'),\n" +
                "('516','el chicle','guma do żucia','5','0','0'),\n" +
                "('517','la zapatería','obuwniczy','2','0','0'),\n" +
                "('518','la agencia de viajes','biuro podróży','2','0','0'),\n" +
                "('519','la peluquería','zakład fryzjerski','3','0','0'),\n" +
                "('520','el mercado','rynek','2','0','0'),\n" +
                "('521','la lavendería','pralnia','2','0','0'),\n" +
                "('522','los alimentos','żywność','3','0','0'),\n" +
                "('523','el peso','waga','3','0','0'),\n" +
                "('524','el cordo','wieprzowina','5','0','0'),\n" +
                "('525','la vaca','wołowina','3','0','0'),\n" +
                "('526','el jamón','szynka','3','0','0'),\n" +
                "('527','la carne picada','mięso mielone','5','0','0'),\n" +
                "('528','las aves','drób','5','0','0'),\n" +
                "('529','la carne magra','chude mięso','5','0','0'),\n" +
                "('530','el pescado','ryba','5','0','0'),\n" +
                "('531','el hielo','lód','5','0','0'),\n" +
                "('532','congelado','zamrożony','5','0','0'),\n" +
                "('533','fresco','świeży','4','0','0'),\n" +
                "('534','la cola','ogon','4','0','0'),\n" +
                "('535','la col','kapusta','6','0','0'),\n" +
                "('536','el pimiento','papryka','3','0','0'),\n" +
                "('537','el pepino','ogórek','5','0','0'),\n" +
                "('538','el tomate','pomidor','3','0','0'),\n" +
                "('539','la patata','ziemniak','3','0','0'),\n" +
                "('540','la remolacha','burak','5','0','0'),\n" +
                "('541','la zanahoria','marchew','5','0','0'),\n" +
                "('542','la cebolla','cebula','3','0','0'),\n" +
                "('543','crudo','surowy','4','0','0'),\n" +
                "('544','picante','ostry','3','0','0'),\n" +
                "('545','dulce','słodki','3','0','0'),\n" +
                "('546','amargo','gorzki','4','0','0'),\n" +
                "('547','el ajo','czosnek','5','0','0'),\n" +
                "('548','la naranja','pomarańcza','3','0','0'),\n" +
                "('549','el melocotón','brzoskwinia','4','0','0'),\n" +
                "('550','la nectarina','nektrynka','5','0','0'),\n" +
                "('551','el pomelo','grejpfrut','5','0','0'),\n" +
                "('552','la cereza','czereśnia','5','0','0'),\n" +
                "('553','la lima','limonka','3','0','0'),\n" +
                "('554','el limón','cytryna','3','0','0'),\n" +
                "('555','la manzana','jablko','3','0','0'),\n" +
                "('556','la fresa','truskawka','4','0','0'),\n" +
                "('557','la sandía','arbuz','5','0','0'),\n" +
                "('558','la arándano','jagoda','5','0','0'),\n" +
                "('559','la piña','ananas','6','0','0'),\n" +
                "('560','el plátano','banan','3','0','0'),\n" +
                "('561','el piñón','orzeszek','5','0','0'),\n" +
                "('562','el coco','kokos','5','0','0'),\n" +
                "('563','duro','twardy','3','0','0'),\n" +
                "('564','blando','miękki','3','0','0'),\n" +
                "('565','salado','słony','3','0','0'),\n" +
                "('566','el arroz','ryż','1','0','0'),\n" +
                "('567','la vainilla','wanilia','3','0','0'),\n" +
                "('568','el pimentón','papryka','2','0','0'),\n" +
                "('569','las hierbas','zioła','5','0','0'),\n" +
                "('570','las especias','przyprawy','5','0','0'),\n" +
                "('571','el aceite de oliva','oliwa z oliwek','6','0','0'),\n" +
                "('572','el tarro','słoik','6','0','0'),\n" +
                "('573','la miel','miód','6','0','0'),\n" +
                "('574','la salsa','sos','3','0','0'),\n" +
                "('575','la mostaza','musztarda','3','0','0'),\n" +
                "('576','la mayonesa','majonez','3','0','0'),\n" +
                "('577','la mantequilla','masło','3','0','0'),\n" +
                "('578','la mantequilla de cacahuetes','masło orzechowe','6','0','0'),\n" +
                "('579','el queso','ser','5','0','0'),\n" +
                "('580','la leche','mleko','3','0','0'),\n" +
                "('581','la margarina','margaryna','4','0','0'),\n" +
                "('582','la nata','śmietana','3','0','0'),\n" +
                "('583','el yogurt','jogurt','3','0','0'),\n" +
                "('584','los huevos','jaja','2','0','0'),\n" +
                "('585','sin grasa','beztłuszczowy','6','0','0'),\n" +
                "('586','la harina','mąka','5','0','0'),\n" +
                "('587','hornear','piec','6','0','0'),\n" +
                "('588','el pan','chleb','2','0','0'),\n" +
                "('589','la tarta','tort','5','0','0'),\n" +
                "('590','el agua','woda','1','0','0'),\n" +
                "('591','el té','herbata','1','0','0'),\n" +
                "('592','el café','kawa','1','0','0'),\n" +
                "('593','el zumo','sok','1','0','0'),\n" +
                "('594','la cerveza','piwo','1','0','0'),\n" +
                "('595','el vino','wino','1','0','0'),\n" +
                "('596','el vino tinto','wino czerwone','2','0','0'),\n" +
                "('597','el champán','szampan','5','0','0'),\n" +
                "('598','la café','kawiarnia','1','0','0'),\n" +
                "('599','la espuma','piana','5','0','0'),\n" +
                "('600','la ensalada','sałatka','6','0','0'),\n" +
                "('601','el bar','bar','3','0','0'),\n" +
                "('602','el cubito de hielo','kostka lodu','6','0','0'),\n" +
                "('603','el cóctel','koktajl','6','0','0'),\n" +
                "('604','un chupito','shot','6','0','0'),\n" +
                "('605','las patatas fritas','frytki','2','0','0'),\n" +
                "('606','las aceitunas','oliwki','4','0','0'),\n" +
                "('607','el camarero','kelner','2','0','0'),\n" +
                "('608','la botella','butelka','2','0','0'),\n" +
                "('609','el chef','szef kuchni','2','0','0'),\n" +
                "('610','el plato principal','danie główne','5','0','0'),\n" +
                "('611','pagar','płacić','5','0','0'),\n" +
                "('612','la sopa','zupa','5','0','0'),\n" +
                "('613','los aperitivos','przystaki','6','0','0'),\n" +
                "('614','los cacahuetes','orzeszki ziemne','6','0','0'),\n" +
                "('615','el postre','deser','6','0','0'),\n" +
                "('616','la pizza','pizza','4','0','0'),\n" +
                "('617','la entrega a domicilio','dostawa do domu','6','0','0'),\n" +
                "('618','la hamburguesa','hamburger','4','0','0'),\n" +
                "('619','para comer en el local','na miejscu','6','0','0'),\n" +
                "('620','el menú','menu','4','0','0'),\n" +
                "('621','para llevar','na wynos','4','0','0'),\n" +
                "('622','el pollo','kurczak','4','0','0'),\n" +
                "('623','el bocadillo','kanapka','3','0','0'),\n" +
                "('624','la salchicha','kiełbaska','4','0','0'),\n" +
                "('625','el huevo frito','jajo smażone','6','0','0'),\n" +
                "('626','el bacón','bekon','6','0','0'),\n" +
                "('627','las crepes','naleśniki','6','0','0'),\n" +
                "('628','los gofres','gofry','6','0','0'),\n" +
                "('629','la comida principal','obiad','5','0','0'),\n" +
                "('630','el guiso','gulasz','6','0','0'),\n" +
                "('631','la empanada','pieróg','5','0','0'),\n" +
                "('632','el pincho','szaszłyk','6','0','0'),\n" +
                "('633','las albóndigas','klopsiki','6','0','0'),\n" +
                "('634','la tortilla','omlet','6','0','0'),\n" +
                "('635','la pasta','makaron','4','0','0'),\n" +
                "('636','el estudio','nauka','4','0','0'),\n" +
                "('637','el colegio','szkoła','2','0','0'),\n" +
                "('638','la pizarra','tablica','3','0','0'),\n" +
                "('639','el aula','klasa','3','0','0'),\n" +
                "('640','leer','czytac','3','0','0'),\n" +
                "('641','escribir','pisać','3','0','0'),\n" +
                "('642','dibujar','rysować','3','0','0'),\n" +
                "('643','aprender','uczyć się','3','0','0'),\n" +
                "('644','discutir','dyskutować','3','0','0'),\n" +
                "('645','el alumno','ławka','4','0','0'),\n" +
                "('646','el arte','sztuka','4','0','0'),\n" +
                "('647','la biología','biologia','4','0','0'),\n" +
                "('648','la guímica','chemia','4','0','0'),\n" +
                "('649','la geografía','geografia','4','0','0'),\n" +
                "('650','la historia','historia','4','0','0'),\n" +
                "('651','los idiomas','jęzki','4','0','0'),\n" +
                "('652','la literatura','literatura','4','0','0'),\n" +
                "('653','las matemáticas','matematyka','4','0','0'),\n" +
                "('654','la música','muzyka','3','0','0'),\n" +
                "('655','las ciencias','przedmioty ścisłe','4','0','0'),\n" +
                "('656','la fisica','fizyka','4','0','0'),\n" +
                "('657','el proyector','rzutnik','5','0','0'),\n" +
                "('658','el cuaderno','zeszyt','4','0','0'),\n" +
                "('659','el lápiz','ołówek','3','0','0'),\n" +
                "('660','la pregunta','pytanie','3','0','0'),\n" +
                "('661','la goma','guma','3','0','0'),\n" +
                "('662','la respuesta','odpowiedź','3','0','0'),\n" +
                "('663','el bolígrafo','długopis','3','0','0'),\n" +
                "('664','el diccionario','słownik','3','0','0'),\n" +
                "('665','el examen','egzamin','3','0','0'),\n" +
                "('666','el director','dyrektor szkoły','3','0','0'),\n" +
                "('667','la clase','lekcja','3','0','0'),\n" +
                "('668','el laboratorio','laboratorium','4','0','0'),\n" +
                "('669','el experimento','doświadczenie','4','0','0'),\n" +
                "('670','el microscopio','mikroskop','4','0','0'),\n" +
                "('671','el ocular','okular','2','0','0'),\n" +
                "('672','las gafas protectoras','okulary ochronne','2','0','0'),\n" +
                "('673','el imán','magnes','6','0','0'),\n" +
                "('674','el campus','kampus','6','0','0'),\n" +
                "('675','el aparcamiento','parking','2','0','0'),\n" +
                "('676','el colegio mayor','akademik','6','0','0'),\n" +
                "('677','la biblioteca','biblioteka','3','0','0'),\n" +
                "('678','el estudiante','student','3','0','0'),\n" +
                "('679','la graduada','absolwent','5','0','0'),\n" +
                "('680','el diploma','dyplom','4','0','0'),\n" +
                "('681','el doctorado','doktorat','4','0','0'),\n" +
                "('682','el máster','magister','4','0','0'),\n" +
                "('683','el trabajo','praca','1','0','0'),\n" +
                "('684','la oficina','biuro','1','0','0'),\n" +
                "('685','el monitor','monitor','4','0','0'),\n" +
                "('686','el ordenador','komputer','3','0','0'),\n" +
                "('687','la impresora','drukarka','3','0','0'),\n" +
                "('688','fotocopiar','kopiować','4','0','0'),\n" +
                "('689','ampliar','powiększać','4','0','0'),\n" +
                "('690','imprimir','drukować','4','0','0'),\n" +
                "('691','reducir','zmniejszać','4','0','0'),\n" +
                "('692','asistir','być obecnym','4','0','0'),\n" +
                "('693','la presentación','prezentacja','3','0','0'),\n" +
                "('694','los apuntes','notatki','5','0','0'),\n" +
                "('695','la empresa','firma','3','0','0'),\n" +
                "('696','el sueldo','pensja','4','0','0'),\n" +
                "('697','el teclado','klawiatura','4','0','0'),\n" +
                "('698','la memoria','pamięć','2','0','0'),\n" +
                "('699','el altavoz','głośnik','4','0','0'),\n" +
                "('700','el disco duro','dysk twardy','4','0','0'),\n" +
                "('701','el sistem','system','4','0','0'),\n" +
                "('702','el software','oprogramowanie','3','0','0'),\n" +
                "('703','el servidor','serwer','4','0','0'),\n" +
                "('704','la red','sieć','3','0','0'),\n" +
                "('705','el programa','program','2','0','0'),\n" +
                "('706','la applicación','aplikacja','3','0','0'),\n" +
                "('707','la barra del menú','pasek menu','6','0','0'),\n" +
                "('708','la barra de acceso','pasek narzędzi','6','0','0'),\n" +
                "('709','el fondo','tapeta','6','0','0'),\n" +
                "('710','el icono','ikona','6','0','0'),\n" +
                "('711','el fichero','plik','6','0','0'),\n" +
                "('712','la carpeta','katalog','6','0','0'),\n" +
                "('713','la papelera','kosz','5','0','0'),\n" +
                "('714','el sitio web','strona internetowa','5','0','0'),\n" +
                "('715','el navegador','przeglądarka','5','0','0'),\n" +
                "('716','conectar','łączyć','5','0','0'),\n" +
                "('717','bajar','pobierać','5','0','0'),\n" +
                "('718','instalar','instalować','5','0','0'),\n" +
                "('719','recibir','odbierać','5','0','0'),\n" +
                "('720','guardar','zapisać','5','0','0'),\n" +
                "('721','buscar','szukać','4','0','0'),\n" +
                "('722','enviar','wysyłać','4','0','0'),\n" +
                "('723','el estudio de televisión','studio telewizyjne','4','0','0'),\n" +
                "('724','la cámara','kamera','5','0','0'),\n" +
                "('725','el presentador','prezenter','5','0','0'),\n" +
                "('726','emitir','emitować','5','0','0'),\n" +
                "('727','el canal','kanał','4','0','0'),\n" +
                "('728','el documental','program dokumentalny','6','0','0'),\n" +
                "('729','el concurso','konkurs','6','0','0'),\n" +
                "('730','en directo','na żywo','5','0','0'),\n" +
                "('731','las noticias','wiadomości','6','0','0'),\n" +
                "('732','la prensa','prasa','6','0','0'),\n" +
                "('733','la telenovela','telenowela','6','0','0'),\n" +
                "('734','los actores','aktorzy','6','0','0'),\n" +
                "('735','el reporter','reporter','6','0','0'),\n" +
                "('736','el entrevistador','dziennkarz','6','0','0'),\n" +
                "('737','la radio','radio','5','0','0'),\n" +
                "('738','el micrófono','mikrofon','5','0','0'),\n" +
                "('739','la emisión','program','6','0','0'),\n" +
                "('740','la frecuencia','częstotliwość','6','0','0'),\n" +
                "('741','el derecho','prawo','4','0','0'),\n" +
                "('742','el abogado','prawnik','4','0','0'),\n" +
                "('743','el juez','sędzia','4','0','0'),\n" +
                "('744','la defensa','obrona','5','0','0'),\n" +
                "('745','el ocusado','oskarżony','5','0','0'),\n" +
                "('746','la apelación','apelacja','5','0','0'),\n" +
                "('747','absuelto','uniewinniony','5','0','0'),\n" +
                "('748','el juicio','rozprawa sądowa','5','0','0'),\n" +
                "('749','el cargo','oskarżenie','5','0','0'),\n" +
                "('750','el prueba','dowód','6','0','0'),\n" +
                "('751','culpable','winny','6','0','0'),\n" +
                "('752','inocente','niewinny','6','0','0'),\n" +
                "('753','el sospechoso','podejrzany','6','0','0'),\n" +
                "('754','la celda','cela','5','0','0'),\n" +
                "('755','la declaración','zeznanie','5','0','0'),\n" +
                "('756','la cárcel','więzienie','5','0','0'),\n" +
                "('757','el granjero','rolnik','6','0','0'),\n" +
                "('758','el granero','stodoła','6','0','0'),\n" +
                "('759','la cerca','ogrodzenie','6','0','0'),\n" +
                "('760','el campo','pole','5','0','0'),\n" +
                "('761','el tractor','traktor','6','0','0'),\n" +
                "('762','el maíz','kukurydza','4','0','0'),\n" +
                "('763','el algodón','bawełna','4','0','0'),\n" +
                "('764','la gallina','kura','5','0','0'),\n" +
                "('765','el gallo','kogut','5','0','0'),\n" +
                "('766','el pavo','indyk','5','0','0'),\n" +
                "('767','el cerdo','świnia','5','0','0'),\n" +
                "('768','la oveja','owca','5','0','0'),\n" +
                "('769','el toro','byk','4','0','0'),\n" +
                "('770','el pato','kaczka','4','0','0'),\n" +
                "('771','la burro','osiol','5','0','0'),\n" +
                "('772','el caballo','koń','3','0','0'),\n" +
                "('773','el establo','stajnia','5','0','0'),\n" +
                "('774','la construcción','budowa','3','0','0'),\n" +
                "('775','la pared','ściana','4','0','0'),\n" +
                "('776','el ladrillo','cegła','5','0','0'),\n" +
                "('777','la madera','drewno','5','0','0'),\n" +
                "('778','la teja','dachówka','5','0','0'),\n" +
                "('779','la maquinaria','maszyna','5','0','0'),\n" +
                "('780','el esfalto','asfalt','5','0','0'),\n" +
                "('781','la profesión','zawód','5','0','0'),\n" +
                "('782','el carpintero','stolarz','6','0','0'),\n" +
                "('783','el electricista','elektryk','6','0','0'),\n" +
                "('784','el fontanero','hydraulik','6','0','0'),\n" +
                "('785','el agente inmobiliario','agent nieruchomości','6','0','0'),\n" +
                "('786','la veterinaria','weterynarz','6','0','0'),\n" +
                "('787','el soldado','żołnierz','4','0','0'),\n" +
                "('788','el marino','marynarz','4','0','0'),\n" +
                "('789','el pescador','rybak','5','0','0'),\n" +
                "('790','el contable','księgowy','5','0','0'),\n" +
                "('791','el arquitecto','architekt','5','0','0'),\n" +
                "('792','el profesor','nauczyciel','4','0','0'),\n" +
                "('793','el bibliotecario','bibliotekarz','4','0','0'),\n" +
                "('794','el cientifico','naukowiec','4','0','0'),\n" +
                "('795','el taxista','taksówkarz','4','0','0'),\n" +
                "('796','el piloto','pilot','4','0','0'),\n" +
                "('797','la azafata','stewardesa','5','0','0'),\n" +
                "('798','el conductor de autobús','kierowca autobusu','4','0','0'),\n" +
                "('799','el músico','muzyk','4','0','0'),\n" +
                "('800','la cantante','piosenkarka','4','0','0'),\n" +
                "('801','la actriz','aktorka','4','0','0'),\n" +
                "('802','la camarera','kelnerka','4','0','0'),\n" +
                "('803','el barman','barman','4','0','0'),\n" +
                "('804','el deportista','sportowiec','4','0','0'),\n" +
                "('805','el escultor','rzeźbiarz','6','0','0'),\n" +
                "('806','el pintor','malarz','6','0','0'),\n" +
                "('807','la periodista','dziennikarka','5','0','0'),\n" +
                "('808','el fotógrafo','fotograf','4','0','0'),\n" +
                "('809','la redactora','redaktorka','4','0','0'),\n" +
                "('810','el diseñador','projektant','4','0','0'),\n" +
                "('811','el sastre','krawiec','5','0','0'),\n" +
                "('812','adelante','naprzód','4','0','0'),\n" +
                "('813','acerca de','o czymś','4','0','0'),\n" +
                "('814','la contraseña','hasło','4','0','0'),\n" +
                "('815','el arroyo','strumień','5','0','0'),\n" +
                "('816','la fecha','data','2','0','0'),\n" +
                "('817','el cambio','zmiana','2','0','0'),\n" +
                "('818','a la derecha','w prawo','2','0','0'),\n" +
                "('819','a la izquierdo','w lewo','2','0','0'),\n" +
                "('820','el desarollo','rozwój','2','0','0'),\n" +
                "('821','demasiado','zbyt','4','0','0'),\n" +
                "('822','cualquier','dowolny','2','0','0'),\n" +
                "('823','el lobo','wilk','6','0','0'),\n" +
                "('824','tuyo','twój','2','0','0'),\n" +
                "('825','la madrugada','wczesny poranek','4','0','0'),\n" +
                "('826','el lugar','miejsce','4','0','0'),\n" +
                "('827','el valle','dolina','4','0','0'),\n" +
                "('828','situado','usytuowany','4','0','0'),\n" +
                "('829','el extremo','koniec','4','0','0'),\n" +
                "('830','nor-oriental','północny wschód','5','0','0'),\n" +
                "('831','el recorrido','podróż','5','0','0'),\n" +
                "('832','ver','zobaczyć','2','0','0'),\n" +
                "('833','la droga','lek','1','0','0'),\n" +
                "('834','el origen','pochodzenie','4','0','0'),\n" +
                "('835','falla','usterka','5','0','0'),\n" +
                "('836','la corteza','skorupa','5','0','0'),\n" +
                "('837','terrestre','ziemska','5','0','0'),\n" +
                "('838','el erosión','erozja','5','0','0'),\n" +
                "('839','llegar','dostać','4','0','0'),\n" +
                "('840','ambas','zarówno','5','0','0'),\n" +
                "('841','la ladera','stok','5','0','0'),\n" +
                "('842','hasta','w górę','3','0','0'),\n" +
                "('843','el abrigo','płaszcz','4','0','0'),\n" +
                "('844','el bolso','torebka','4','0','0'),\n" +
                "('845','la maleta','walizka','4','0','0'),\n" +
                "('846','guapa','ładna','4','0','0'),\n" +
                "('847','delante','przed','3','0','0'),\n" +
                "('848','sobre','na','3','0','0'),\n" +
                "('849','el cuaderno','notatnik','4','0','0'),\n" +
                "('850','debajo','poniżej','3','0','0'),\n" +
                "('851','dentro','wewnątrz','3','0','0'),\n" +
                "('852','detras','za','3','0','0'),\n" +
                "('853','el armario','szafka','4','0','0'),\n" +
                "('854','fuera','na zewnątrz','3','0','0'),\n" +
                "('855','encima','powyżej','3','0','0'),\n" +
                "('856','usos','użycie','2','0','0'),\n" +
                "('857','el cementerio','cmentarz','','0','0'),\n" +
                "('858','la gasolinera','stacja benzynowa5','0','0'),\n" +
                "('859','limpio','czysty','4','0','0'),\n" +
                "('860','cudrada','kwadratowa','4','0','0'),\n" +
                "('861','rico','bogaty','4','0','0'),\n" +
                "('862','ocupada','zajęta','4','0','0'),\n" +
                "('863','verdadero','prawdziwy','4','0','0'),\n" +
                "('864','falso','fałszywy','4','0','0'),\n" +
                "('865','enfrente','naprzeciwko','4','0','0'),\n" +
                "('866','cerca','blisko','4','0','0'),\n" +
                "('867','estanco','wodoszczelny','4','0','0'),\n" +
                "('868','al lado','obok','4','0','0'),\n" +
                "('869','el sueño','marzenie','4','0','0'),\n" +
                "('870','el sobrino','siostrzeniec','4','0','0'),\n" +
                "('871','casado','żonaty','4','0','0'),\n" +
                "('872','soltero','kawaler','4','0','0'),\n" +
                "('873','el pasillo','korytarz','2','0','0'),\n" +
                "('874','la luz','światło','2','0','0'),\n" +
                "('875','el vórtcie','wir','5','0','0'),\n" +
                "('876','comunicado','skomunikowany','5','0','0'),\n" +
                "('877','la terraza','taras','4','0','0'),\n" +
                "('878','el sillón','fotel','4','0','0'),\n" +
                "('879','calvo','łysy','2','0','0'),\n" +
                "('880','fuerte','silny','2','0','0'),\n" +
                "('881','libre','wolny','2','0','0'),\n" +
                "('882','mojado','mokry','4','0','0'),\n" +
                "('883','vacío','pusty','1','0','0'),\n" +
                "('884','suscio','brudny','1','0','0'),\n" +
                "('885','debil','słaby','4','0','0'),\n" +
                "('886','amable','miły','2','0','0'),\n" +
                "('887','grosero','niemiły','2','0','0'),\n" +
                "('888','divertido','interesujący','2','0','0'),\n" +
                "('889','barato','tani','1','0','0'),\n" +
                "('890','caro','drogi','1','0','0'),\n" +
                "('891','moderno','nowoczesny','4','0','0'),\n" +
                "('892','antiguo','staroświecki','4','0','0'),\n" +
                "('893','feo','brzydki','1','0','0'),\n" +
                "('894','bonito','ładny','1','0','0'),\n" +
                "('895','estrecho','wąski','1','0','0'),\n" +
                "('896','ancho','szeroki','1','0','0'),\n" +
                "('897','tirar','rzucać','2','0','0'),\n" +
                "('898','escuchar','słuchać','2','0','0'),\n" +
                "('899','mirar','patrzeć','2','0','0'),\n" +
                "('900','abrir','otwierać','1','0','0'),\n" +
                "('901','hablar','mówić','1','0','0'),\n" +
                "('902','meter en','umieścić w','4','0','0'),\n" +
                "('903','completar','ukończyć','4','0','0'),\n" +
                "('904','comprender','zrozumieć','4','0','0'),\n" +
                "('905','preguntar','zapytać','2','0','0'),\n" +
                "('906','el cartel','plakat','5','0','0'),\n" +
                "('907','la tema','motyw','5','0','0'),\n" +
                "('908','lavar','myć','3','0','0'),\n" +
                "('909','querer','chcieć','3','0','0'),\n" +
                "('910','jugar','grać','2','0','0'),\n" +
                "('911','poder','móc','2','0','0'),\n" +
                "('912','comer','jeść','2','0','0'),\n" +
                "('913','pedir','prosić','2','0','0'),\n" +
                "('914','vivir','żyć','2','0','0'),\n" +
                "('915','hacer','robić','2','0','0'),\n" +
                "('916','el barrio','sąsiedztwo','6','0','0'),\n" +
                "('917','a donde','dokąd','4','0','0'),\n" +
                "('918','de donde','skąd','4','0','0'),\n" +
                "('919','raro','rzadko','4','0','0'),\n" +
                "('920','siempre','zawsze','4','0','0'),\n" +
                "('921','a veces','od czasu do czasu','4','0','0'),\n" +
                "('922','a menudo','często','4','0','0'),\n" +
                "('923','fumar','palić','4','0','0'),\n" +
                "('924','el camello','wielbłąd','5','0','0'),\n" +
                "('925','el paseo','spacer','5','0','0'),\n" +
                "('926','el borrador','gąbka','5','0','0'),\n" +
                "('927','la hoja','kartka','6','0','0'),\n" +
                "('928','la agenda','kalendarzyk','6','0','0'),\n" +
                "('929','el ejercicio','ćwiczenie','6','0','0'),\n" +
                "('930','aquel','tamten','5','0','0'),\n" +
                "('931','allí/allá','tam','5','0','0'),\n" +
                "('932','aqui','tutaj','5','0','0'),\n" +
                "('933','cuero','skórzany','5','0','0'),\n" +
                "('934','la mentira','kłamstwo','6','0','0'),\n" +
                "('935','bailar','tańczyć','4','0','0'),\n" +
                "('936','bajar','schodzić','4','0','0'),\n" +
                "('937','cantar','śpiewać','4','0','0'),\n" +
                "('938','comprar','kupować','4','0','0'),\n" +
                "('939','dejar','zostawić','4','0','0'),\n" +
                "('940','entrar','wchodzić','4','0','0'),\n" +
                "('941','estudiar','uczyć się','4','0','0'),\n" +
                "('942','llamarse','nazywać się','4','0','0'),\n" +
                "('943','nadar','pływać','4','0','0'),\n" +
                "('944','preparar','przygotować','4','0','0'),\n" +
                "('945','quedar','umawiać się','4','0','0'),\n" +
                "('946','quejarse','narzekać','4','0','0'),\n" +
                "('947','tocar','grać na instrumencie','4','0','0'),\n" +
                "('948','tomar','brać','4','0','0'),\n" +
                "('949','visitar','odwiedzać','4','0','0'),\n" +
                "('950','beber','pić','4','0','0'),\n" +
                "('951','correr','biegać','4','0','0'),\n" +
                "('952','vender','sprzedawać','4','0','0'),\n" +
                "('953','asistir','uczestniczyć','4','0','0'),\n" +
                "('954','oir','słyszeć','4','0','0'),\n" +
                "('955','preferir','woleć','4','0','0'),\n" +
                "('956','producir','produkować','4','0','0'),\n" +
                "('957','reír','śmiać się','4','0','0'),\n" +
                "('958','repetir','powtarzać','4','0','0'),\n" +
                "('959','salir','wychodzić','4','0','0'),\n" +
                "('960','seguir','iść dalej','4','0','0'),\n" +
                "('961','sentir','czuć','4','0','0'),\n" +
                "('962','servir','służyć','4','0','0'),\n" +
                "('963','traducir','tłumaczyć','4','0','0'),\n" +
                "('964','venir','przychodzić','4','0','0'),\n" +
                "('965','ver','widzieć','4','0','0'),\n" +
                "('966','volver','wracać','4','0','0'),\n" +
                "('967','conducir','prowadzić','4','0','0'),\n" +
                "('968','corregir','poprawiac','4','0','0'),\n" +
                "('969','despedir','żegnać','4','0','0'),\n" +
                "('970','dormir','spać','4','0','0'),\n" +
                "('971','herir','ranić','4','0','0'),\n" +
                "('972','ir','iść','4','0','0'),\n" +
                "('973','huir','uciekać','5','0','0'),\n" +
                "('974','mentir','kłamać','5','0','0'),\n" +
                "('975','morir','umierać','4','0','0'),\n" +
                "('976','haber','znajdować się','5','0','0'),\n" +
                "('977','llover','padać','4','0','0'),\n" +
                "('978','peder','zgubić','4','0','0'),\n" +
                "('979','saber','wiedzieć','4','0','0'),\n" +
                "('980','tener','mieć','4','0','0'),\n" +
                "('981','traer','przynosić','5','0','0'),\n" +
                "('982','pensar','myśleć','4','0','0'),\n" +
                "('983','atender','uczestniczyć','4','0','0'),\n" +
                "('984','nevar','padać','5','0','0'),\n" +
                "('985','conocer','znać','5','0','0'),\n" +
                "('986','convencer','przekonywać','5','0','0'),\n" +
                "('987','defender','bronić','5','0','0'),\n" +
                "('988','devolver','zawracać','5','0','0'),\n" +
                "('989','doler','boleć','4','0','0'),\n" +
                "('990','entender','rozumieć','4','0','0'),\n" +
                "('991','subir','wchodzić','4','0','0'),\n" +
                "('992','contar','liczyć','4','0','0'),\n" +
                "('993','costar','kosztować','4','0','0'),\n" +
                "('994','dar','dawać','4','0','0'),\n" +
                "('995','despertar','budzić się','4','0','0'),\n" +
                "('996','encontar','znaleźć','5','0','0'),\n" +
                "('997','el transporte','transport','4','0','0'),\n" +
                "('998','la carretera','droga','3','0','0'); Commit");

        db.execSQL("Begin; insert into " + TABLE_NAME +" values" +
                "('999','la autopista','autostrada','2','0','0'),\n" +
                "('1000','el mapa','mapa','2','0','0'),\n" +
                "('1001','el tráfico','ruch uliczny','3','0','0'),\n" +
                "('1002','el camión','samochód ciężarowy','3','0','0'),\n" +
                "('1003','el peligro','niebezpieczeństwo','6','0','0'),\n" +
                "('1004','la','glorieta','rondo','5','0','0'),\n" +
                "('1005','el desvío','objezd','6','0','0'),\n" +
                "('1006','aparcar','parkować','3','0','0'),\n" +
                "('1007','adelantar','wyprzedzać','4','0','0'),\n" +
                "('1008','las obras','roboty drogowe','6','0','0'),\n" +
                "('1009','el paso de peatones','przejśdie dla pieszych','5','0','0'),\n" +
                "('1010','el autocar','autokar','2','0','0'),\n" +
                "('1011','la ruta','trasa','3','0','0'),\n" +
                "('1012','el tranvía','tramwaj','2','0','0'),\n" +
                "('1013','la parada','przystanek','3','0','0'),\n" +
                "('1014','la tarifa','opłata za przejazd','4','0','0'),\n" +
                "('1015','el horario','rozkład jazdy','4','0','0'),\n" +
                "('1016','el coche','samochód','1','0','0'),\n" +
                "('1017','la rueda','koło','5','0','0'),\n" +
                "('1018','el neumático','opona','6','0','0'),\n" +
                "('1019','la limusina','limuzyna','5','0','0'),\n" +
                "('1020','la gasolina','benzyna','4','0','0'),\n" +
                "('1021','el aire acondicionado','klimatyzacja','4','0','0'),\n" +
                "('1022','el automático','automatyczny','4','0','0'),\n" +
                "('1023','el freno','hamulec','5','0','0'),\n" +
                "('1024','el volante','kierownica','5','0','0'),\n" +
                "('1025','el techo','dach','5','0','0'),\n" +
                "('1026','la avería','awaria','3','0','0'),\n" +
                "('1027','la motocicleta','motocykl','3','0','0'),\n" +
                "('1028','el faro','reflektor','4','0','0'),\n" +
                "('1029','el motor','silnik','4','0','0'),\n" +
                "('1030','el tipo','typ','4','0','0'),\n" +
                "('1031','el acelerador','gaz','4','0','0'),\n" +
                "('1032','la bicicleta','rower','1','0','0'),\n" +
                "('1033','el tren','pociąg','1','0','0'),\n" +
                "('1034','el andén','peron','2','0','0'),\n" +
                "('1035','la lacomotora','lokomotywa','3','0','0'),\n" +
                "('1036','el metro','metro','2','0','0'),\n" +
                "('1037','el vagón','wagon','2','0','0'),\n" +
                "('1038','la asiento','miejsce','3','0','0'),\n" +
                "('1039','la hora punta','godzina szczytu','4','0','0'),\n" +
                "('1040','el plano','plan','4','0','0'),\n" +
                "('1041','el revisor','konduktor','5','0','0'),\n" +
                "('1042','la barrera','bramka','5','0','0'),\n" +
                "('1043','el avión','samolot','2','0','0'),\n" +
                "('1044','la salida','wyjście','2','0','0'),\n" +
                "('1045','el ala','skrzydło','5','0','0'),\n" +
                "('1046','la cabina','kabina','3','0','0'),\n" +
                "('1047','la fila','rząd','4','0','0'),\n" +
                "('1048','la salida de emergencia','wyjście awaryjne','5','0','0'),\n" +
                "('1049','la bandeja','stolik','5','0','0'),\n" +
                "('1050','la avioneta','awionetka','6','0','0'),\n" +
                "('1051','el helicópterp','helikopter','6','0','0'),\n" +
                "('1052','el misil','pocisk','6','0','0'),\n" +
                "('1053','el caza','myśliwiec','6','0','0'),\n" +
                "('1054','el aeropuerto','lotnisko','3','0','0'),\n" +
                "('1055','reservar un vuelo','zarezerwować lot','5','0','0'),\n" +
                "('1056','la conexión','połączenie','5','0','0'),\n" +
                "('1057','la torre','wieża','4','0','0'),\n" +
                "('1058','la pista','pas startowy','5','0','0'),\n" +
                "('1059','la aduana','odprawa celna','4','0','0'),\n" +
                "('1060','el vuelo','lot','4','0','0'),\n" +
                "('1061','nacional','krajowy','5','0','0'),\n" +
                "('1062','las vacaciones','wakacje','4','0','0'),\n" +
                "('1063','el inmigración','kontrola paszportowa','5','0','0'),\n" +
                "('1064','internacional','międzynarodowy','5','0','0'),\n" +
                "('1065','la seguridad','bezpieczeństwo','5','0','0'),\n" +
                "('1066','la tarjeta de embarque','karta pokładowa','5','0','0'),\n" +
                "('1067','el control de pasaportes','kontrola paszportowa','5','0','0'),\n" +
                "('1068','el equipaje','bagaż','3','0','0'),\n" +
                "('1069','las salidas','odloty','3','0','0'),\n" +
                "('1070','las llegadas','przyloty','3','0','0'),\n" +
                "('1071','la parada de taxis','postój taksówek','3','0','0'),\n" +
                "('1072','la recogida de equipajes','odbiór bagażu','3','0','0'),\n" +
                "('1073','el barco','statek','4','0','0'),\n" +
                "('1074','la cubierta','pokład','4','0','0'),\n" +
                "('1075','el radar','radar','3','0','0'),\n" +
                "('1076','el ancla','kotwica','6','0','0'),\n" +
                "('1077','el capitán','kapitan','5','0','0'),\n" +
                "('1078','el puerto','port','4','0','0'),\n" +
                "('1079','la lancha motora','łódź motorowa','5','0','0'),\n" +
                "('1080','el catamarán','katamaran','6','0','0'),\n" +
                "('1081','el barco de vela','żaglówka','6','0','0'),\n" +
                "('1082','el submarino','łódź podwodna','6','0','0'),\n" +
                "('1083','el yate','jacht','6','0','0'),\n" +
                "('1084','el ferry','prom','5','0','0'),\n" +
                "('1085','el petrolero','tankowiec','6','0','0'),\n" +
                "('1086','el almacén','magazyn','6','0','0'),\n" +
                "('1087','el contenedor','kontener','6','0','0'),\n" +
                "('1088','embarcar','wchodzić na pokład','6','0','0'),\n" +
                "('1089','atrcar','przybić do portu','6','0','0'),\n" +
                "('1090','desembarcar','wysiadać','6','0','0'),\n" +
                "('1091','zarpar','wypłynąć w morze','6','0','0'),\n" +
                "('1092','el faro','latarnia morska','6','0','0'),\n" +
                "('1093','el deporte','sport','2','0','0'),\n" +
                "('1094','el fútbol americano','futbol amerykański','4','0','0'),\n" +
                "('1095','el jugador','gracz','3','0','0'),\n" +
                "('1096','el rugby','rugby','3','0','0'),\n" +
                "('1097','coger','złapać','3','0','0'),\n" +
                "('1098','el balón','piłka','2','0','0'),\n" +
                "('1099','la bota','but','3','0','0'),\n" +
                "('1100','coger','złapać','3','0','0'); Commit;");

        db.execSQL("Begin; insert into " + TABLE_NAME +" values" +
                "('1101','pasar','podawać','3','0','0'),\n" +
                "('1102','el fútbol','piłka nożna','2','0','0'),\n" +
                "('1103','el trio libre','rzut wolny','4','0','0'),\n" +
                "('1104','placar','blokować','4','0','0'),\n" +
                "('1105','chutar','kopać','4','0','0'),\n" +
                "('1106','el portero','bramkarz','3','0','0'),\n" +
                "('1107','el hockey','hokej','4','0','0'),\n" +
                "('1108','el equipo','drużyna','3','0','0'),\n" +
                "('1109','el ataque','atak','3','0','0'),\n" +
                "('1110','la defensa','obrona','3','0','0'),\n" +
                "('1111','la punctuación','wynik','4','0','0'),\n" +
                "('1112','el jugador de baloncesto','koszykarz','5','0','0'),\n" +
                "('1113','saltar','skakać','4','0','0'),\n" +
                "('1114','lanzar','rzucać','4','0','0'),\n" +
                "('1115','la canasta','kosz','5','0','0'),\n" +
                "('1116','el béisbol','bejsbol','5','0','0'),\n" +
                "('1117','la pelota','piłka','5','0','0'),\n" +
                "('1118','el servicio','serw','5','0','0'),\n" +
                "('1119','el partido','mecz','3','0','0'),\n" +
                "('1120','el bádminton','badminton','5','0','0'),\n" +
                "('1121','el campeonato','turniej','4','0','0'),\n" +
                "('1122','el golf','golf','4','0','0'),\n" +
                "('1123','el atletismo','lekkoatletyka','4','0','0'),\n" +
                "('1124','el campo','boisko','3','0','0'),\n" +
                "('1125','la carrera','wyścig','4','0','0'),\n" +
                "('1126','el tiempo','czas','2','0','0'),\n" +
                "('1127','el récord','rekord','4','0','0'),\n" +
                "('1128','batir un récord','pobić rekord','4','0','0'),\n" +
                "('1129','la maratón','maraton','2','0','0'),\n" +
                "('1130','la línea de meta','meta','4','0','0'),\n" +
                "('1131','la gimnasia','gimnastyka','4','0','0'),\n" +
                "('1132','la barra fija','drążek','4','0','0'),\n" +
                "('1133','el guante','rękawica','5','0','0'),\n" +
                "('1134','la lucha libre','zapasy','5','0','0'),\n" +
                "('1135','el boxeo','boks','4','0','0'),\n" +
                "('1136','el ring','ring','4','0','0'),\n" +
                "('1137','el asalto','runda','4','0','0'),\n" +
                "('1138','la defensa personal','samoobrona','4','0','0'),\n" +
                "('1139','la natación','pływanie','4','0','0'),\n" +
                "('1140','el trampolín','trampolina','4','0','0'),\n" +
                "('1141','el tubo','rurka','5','0','0'),\n" +
                "('1142','el socorrista','ratownik','6','0','0'),\n" +
                "('1143','la mariposa','motylek','4','0','0'),\n" +
                "('1144','la vela','żeglarstwo','6','0','0'),\n" +
                "('1145','el kayak','kajak','5','0','0'),\n" +
                "('1146','la tabla','deska','6','0','0'),\n" +
                "('1147','el surfing','surfing','5','0','0'),\n" +
                "('1148','el surfista','surfer','5','0','0'),\n" +
                "('1149','el tripulación','załoga','4','0','0'),\n" +
                "('1150','el timón ster','5','0','0'),\n" +
                "('1151','el viento','wiatr','3','0','0'),\n" +
                "('1152','la ola','fala','3','0','0'),\n" +
                "('1153','la equitación','jazda konna','6','0','0'),\n" +
                "('1154','el salto','skok','6','0','0'),\n" +
                "('1155','la cuadra','stajnia','6','0','0'),\n" +
                "('1156','el galope','galop','6','0','0'),\n" +
                "('1157','la pesca','wędkarstwo','5','0','0'),\n" +
                "('1158','recoger','wciągać','5','0','0'),\n" +
                "('1159','el esquí','narciarstwo','5','0','0'),\n" +
                "('1160','la esquiadora','narciarka','5','0','0'),\n" +
                "('1161','el bobsleigh','bobslej','5','0','0'),\n" +
                "('1162','el curling','curling','5','0','0'),\n" +
                "('1163','la escalada','wspinaczka skalna','5','0','0'),\n" +
                "('1164','el rally','rajd samochodowy','5','0','0'),\n" +
                "('1165','el automovilismo','wyścig samochodowy','5','0','0'),\n" +
                "('1166','el motociclismo','wyścig motocyklowy','5','0','0'),\n" +
                "('1167','la diana','tarcza','5','0','0'),\n" +
                "('1168','el arco','łuk','4','0','0'),\n" +
                "('1169','los bolos','kręgle','5','0','0'),\n" +
                "('1170','el billar','bilard','5','0','0'),\n" +
                "('1171','la pesa','hantla','5','0','0'),\n" +
                "('1172','el aeróbic','aerobik','5','0','0'),\n" +
                "('1173','el footing','jogging','5','0','0'),\n" +
                "('1174','entrenarse','trenować','6','0','0'),\n" +
                "('1175','laventar','podciagać','6','0','0'),\n" +
                "('1176','el ocio','czas wolny','5','0','0'),\n" +
                "('1177','el teatro','teatr','4','0','0'),\n" +
                "('1178','el decorado','dekoracja','5','0','0'),\n" +
                "('1179','el escenario','scena','5','0','0'),\n" +
                "('1180','el actor','aktor','2','0','0'),\n" +
                "('1181','el director','reżyser','2','0','0'),\n" +
                "('1182','el público','publiczność','2','0','0'),\n" +
                "('1183','el productor','producent','5','0','0'),\n" +
                "('1184','el guión','scenariusz','5','0','0'),\n" +
                "('1185','el programa','program','4','0','0'),\n" +
                "('1186','el concierto','koncert','4','0','0'),\n" +
                "('1187','el póster','plakat','4','0','0'),\n" +
                "('1188','el musical','musical','5','0','0'),\n" +
                "('1189','el ballet','balet','5','0','0'),\n" +
                "('1190','las palomitas','popcorn','5','0','0'),\n" +
                "('1191','la ópera','opera','4','0','0'),\n" +
                "('1192','el cine','kino','3','0','0'),\n" +
                "('1193','la comedia','komedia','3','0','0'),\n" +
                "('1194','la película de suspense','thriller','4','0','0'),\n" +
                "('1195','la película de terror','horror','4','0','0'),\n" +
                "('1196','la película del oeste','western','4','0','0'),\n" +
                "('1197','el triángulo','trójkąt','4','0','0'),\n" +
                "('1198','la trompeta','trąbka','4','0','0'),\n" +
                "('1199','la pausa','pauza','2','0','0'),\n" +
                "('1200','asia','azja','1','0','0'),\n" +
                "('1201','la batería','perkusja','5','0','0'),\n" +
                "('1202','el micrófono','mikrofon','4','0','0'),\n" +
                "('1203','el guitarrista','gitarzysta','4','0','0'),\n" +
                "('1204','el teclado','keybord','6','0','0'),\n" +
                "('1205','la guitarra','gitara','2','0','0'),\n" +
                "('1206','la contrabajo','gitara basowa','3','0','0'),\n" +
                "('1207','el tambor','bęben','4','0','0'),\n" +
                "('1208','la melodía','melodia','4','0','0'),\n" +
                "('1209','la canción','piosenka','3','0','0'),\n" +
                "('1210','el ritmo','rytm','4','0','0'),\n" +
                "('1211','la letra','tekst','3','0','0'),\n" +
                "('1212','abierto','otwarty','2','0','0'),\n" +
                "('1213','cerrado','zamkniety','2','0','0'),\n" +
                "('1214','la guía del viajero','przewodnik','3','0','0'),\n" +
                "('1215','el precio de entradam','opłata za wstęp','4','0','0'),\n" +
                "('1216','la pila','bateria','4','0','0'),\n" +
                "('1217','la cámara de vídeo','kamera','3','0','0'),\n" +
                "('1218','la cámara de fotos','aparat','3','0','0'),\n" +
                "('1219','la galeria de arte','galeria sztuki','4','0','0'),\n" +
                "('1220','el monumento','pomnik','4','0','0'),\n" +
                "('1221','el museo','muzeum','2','0','0'),\n" +
                "('1222','el casino','kasyno','3','0','0'),\n" +
                "('1223','el parque nacional','4','0','0'),\n" +
                "('1224','el sendero ścieżka','4','0','0'),\n" +
                "('1225','la hierba','trawa','4','0','0'),\n" +
                "('1226','el zoo','zoo','4','0','0'),\n" +
                "('1227','el ciclismo','jazda na rowerze','5','0','0'),\n" +
                "('1228','la playa','plaża','2','0','0'),\n" +
                "('1229','la sombrilla','parasolka','3','0','0'),\n" +
                "('1230','la arena','piasek','3','0','0'),\n" +
                "('1231','el mar','morze','1','0','0'),\n" +
                "('1232','el bikini','bikini','4','0','0'),\n" +
                "('1233','la hamaca','leżak','4','0','0'),\n" +
                "('1234','la concha','muszla','6','0','0'),\n" +
                "('1235','el castillo','zamek','4','0','0'),\n" +
                "('1236','el camping','kemping','5','0','0'),\n" +
                "('1237','el carbón','węgiel','3','0','0'),\n" +
                "('1238','la oguera','ognisko','5','0','0'),\n" +
                "('1239','el termo','termos','5','0','0'),\n" +
                "('1240','la tienda','namiot','3','0','0'),\n" +
                "('1241','la linterna','latarka','3','0','0'),\n" +
                "('1242','el saco de dormir','spiwór','4','0','0'),\n" +
                "('1243','el barbacoa','grill','6','0','0'),\n" +
                "('1244','los auriculares','słuchawki','6','0','0'),\n" +
                "('1245','la pantalla','ekran','6','0','0'),\n" +
                "('1246','el mando a distancia','pilot','6','0','0'),\n" +
                "('1247','la televisión por cable','telewizja kablowa','5','0','0'),\n" +
                "('1248','el programa','program','4','0','0'),\n" +
                "('1249','encender','włączyć','4','0','0'),\n" +
                "('1250','apagar','wyłączyć','4','0','0'),\n" +
                "('1251','el tripode','statyw','5','0','0'),\n" +
                "('1252','el apaisado','pejzaż','5','0','0'),\n" +
                "('1253','el juego','gra','4','0','0'),\n" +
                "('1254','la reina','królowa','4','0','0'),\n" +
                "('1255','el rey','król','4','0','0'),\n" +
                "('1256','el ajedrez','szachy','5','0','0'),\n" +
                "('1257','el turno','ruch','4','0','0'),\n" +
                "('1258','ganar','wygrywać','4','0','0'),\n" +
                "('1259','el ganador','zwycięzca','4','0','0'),\n" +
                "('1260','perder','przegrywać','4','0','0'),\n" +
                "('1261','el perdedor','przegrany','4','0','0'),\n" +
                "('1262','la partida','gra','3','0','0'),\n" +
                "('1263','el póquer','poker','3','0','0'),\n" +
                "('1264','la pintura','malarstwo','4','0','0'),\n" +
                "('1265','rojo','czerwony','1','0','0'),\n" +
                "('1266','azul','niebieski','1','0','0'),\n" +
                "('1267','amarillo','żółty','1','0','0'),\n" +
                "('1268','verde','zielony','1','0','0'),\n" +
                "('1269','naranja','pomarańczowy','1','0','0'),\n" +
                "('1270','morado','fioletowy','1','0','0'),\n" +
                "('1271','europa','europa','1','0','0'),\n" +
                "('1272','africa','afryka','1','0','0'),\n" +
                "('1273','gris','szary','1','0','0'),\n" +
                "('1274','marrón','brązowy','1','0','0'),\n" +
                "('1275','el dibujo','rysunek','3','0','0'),\n" +
                "('1276','coser','szyć','4','0','0'),\n" +
                "('1277','la seda','jedwab','5','0','0'),\n" +
                "('1278','la moda','moda','3','0','0'),\n" +
                "('1279','el medio ambiente','środowisko','5','0','0'),\n" +
                "('1280','el espacio','kosmos','4','0','0'),\n" +
                "('1281','mercurio','merkury','4','0','0'),\n" +
                "('1282','marte','mars','4','0','0'),\n" +
                "('1283','júpiter','jowisz','4','0','0'),\n" +
                "('1284','urano','uran','4','0','0'),\n" +
                "('1285','venus','wenus','4','0','0'),\n" +
                "('1286','tierra','ziemia','4','0','0'),\n" +
                "('1287','saturno','saturn','4','0','0'),\n" +
                "('1288','neptuno','neptun','4','0','0'),\n" +
                "('1289','plutón','pluton','4','0','0'),\n" +
                "('1290','la estrella','gwiazda','4','0','0'),\n" +
                "('1291','la galaxia','galaktyka','4','0','0'),\n" +
                "('1292','la nebulosa','mgławica','4','0','0'),\n" +
                "('1293','el asteroide','asteroida','4','0','0'),\n" +
                "('1294','el cometa','kometa','4','0','0'),\n" +
                "('1295','el eclipse','zaćmienie','5','0','0'),\n" +
                "('1296','el planeta','planeta','4','0','0'),\n" +
                "('1297','el universo','wszechświat','4','0','0'),\n" +
                "('1298','la órbita','orbita','4','0','0'),\n" +
                "('1299','la gravedad','grwitacja','4','0','0'),\n" +
                "('1300','el meteorito','meteoryt','4','0','0'),\n" +
                "('1301','el agujero negro','czarna dziura','5','0','0'),\n" +
                "('1302','la luna llena','pełnia księżyca','3','0','0'),\n" +
                "('1303','la exploracón espacial','badania kosmosu','5','0','0'),\n" +
                "('1304','la estación espacial','stacja kosmiczna','5','0','0'),\n" +
                "('1305','el transportador espacial','prom kosmiczny','5','0','0'),\n" +
                "('1306','la constelación','gwiazdozbiór','3','0','0'),\n" +
                "('1307','el satélite','satelita','3','0','0'),\n" +
                "('1308','el telescopio','teleskop','4','0','0'),\n" +
                "('1309','los prismáticos','lornetka','4','0','0'),\n" +
                "('1310','el polo','biegun','3','0','0'),\n" +
                "('1311','el continente','kontynent','3','0','0'),\n" +
                "('1312','el océano','ocean','3','0','0'),\n" +
                "('1313','la tierra firme','ląd','3','0','0'),\n" +
                "('1314','la península','półwysep','4','0','0'),\n" +
                "('1315','la isla',' wyspa','3','0','0'),\n" +
                "('1316','el núcleo interno','jądro wewnętrzne','5','0','0'),\n" +
                "('1317','el núcleo externo','jądro zewnętrzne','5','0','0'),\n" +
                "('1318','la atmósfera','atmosfera','4','0','0'),\n" +
                "('1319','el trópico','zwrotnik','4','0','0'),\n" +
                "('1320','el ecuador','równik','4','0','0'),\n" +
                "('1321','el hemisferio','półkula','4','0','0'),\n" +
                "('1322','la longitud','długość geograficzna','5','0','0'),\n" +
                "('1323','la latitud','szerokość geograficzna','5','0','0'),\n" +
                "('1324','la lava','lawa','4','0','0'),\n" +
                "('1325','el magma','magma','4','0','0'),\n" +
                "('1326','el volcán','wulkan','3','0','0'),\n" +
                "('1327','el terremoto','trzęsienie ziemi','3','0','0'),\n" +
                "('1328','entrar en erupción','wybuchąć','4','0','0'),\n" +
                "('1329','la placa','płyta','4','0','0'),\n" +
                "('1330','el temblor','wstrząs','4','0','0'),\n" +
                "('1331','la zona','strefa','4','0','0'),\n" +
                "('1332','el peisaje','pejzaż','3','0','0'),\n" +
                "('1333','la montaña','góra','3','0','0'),\n" +
                "('1334','la orilla','brzeg','4','0','0'),\n" +
                "('1335','el río','rzeka','2','0','0'),\n" +
                "('1336','las rocas','skały','2','0','0'),\n" +
                "('1337','la colina','wzgórze','3','0','0'),\n" +
                "('1338','el glaciar','lodowiec','3','0','0'),\n" +
                "('1339','el valle','dolina','3','0','0'),\n" +
                "('1340','la cueva','jaskina','3','0','0'),\n" +
                "('1341','el desierto','pustynia','3','0','0'),\n" +
                "('1342','el bosque','las','2','0','0'),\n" +
                "('1343','el pantano','bagno','5','0','0'),\n" +
                "('1344','el prado','łąka','5','0','0'),\n" +
                "('1345','la cascada','wodospad','2','0','0'),\n" +
                "('1346','el lago','jezioro','2','0','0'),\n" +
                "('1347','la costa','wybrzeże','2','0','0'),\n" +
                "('1348','la aurora','zorza','4','0','0'),\n" +
                "('1349','el agua nieve','śnieg z deszczem','4','0','0'),\n" +
                "('1350','el granizo','grad','4','0','0'),\n" +
                "('1351','el truento','grzmot','4','0','0'),\n" +
                "('1352','la nube','chmura','4','0','0'),\n" +
                "('1353','la lluvia','deszcz','4','0','0'),\n" +
                "('1354','la tormenta','burza','4','0','0'),\n" +
                "('1355','la niebla','mgła','4','0','0'),\n" +
                "('1356','el arco iris','tęcza','5','0','0'),\n" +
                "('1357','la nieve','śnieg','4','0','0'),\n" +
                "('1358','la helada','mróz','4','0','0'),\n" +
                "('1359','la huracán','huragan','4','0','0'),\n" +
                "('1360','el tornado','tornado','4','0','0'),\n" +
                "('1361','el monzón','monsun','5','0','0'),\n" +
                "('1362','el mármol','marmur','5','0','0'),\n" +
                "('1363','los minerales','minerały','5','0','0'),\n" +
                "('1364','los animales','zwierzęta','5','0','0'),\n" +
                "('1365','el cornejo','królik','4','0','0'),\n" +
                "('1366','el hámster','chomik','4','0','0'),\n" +
                "('1367','el ratón','mysz','3','0','0'),\n" +
                "('1368','la rata','szczur','3','0','0'),\n" +
                "('1369','el zorro','lis','3','0','0'),\n" +
                "('1370','el murciélago','nietoperz','5','0','0'),\n" +
                "('1371','el lobo','wilk','3','0','0'),\n" +
                "('1372','el perro','pies','2','0','0'),\n" +
                "('1373','el delfín','delfin','5','0','0'),\n" +
                "('1374','la foca','foka','5','0','0'),\n" +
                "('1375','el gato','kot','2','0','0'),\n" +
                "('1376','el león','lew','2','0','0'),\n" +
                "('1377','marino','morski','5','0','0'),\n" +
                "('1378','la morsa','mors','5','0','0'),\n" +
                "('1379','la ballena','wieloryb','5','0','0'),\n" +
                "('1380','el delfín','delfin','5','0','0'),\n" +
                "('1381','el ciervo','jeleń','5','0','0'),\n" +
                "('1382','la cebra','zebra','3','0','0'),\n" +
                "('1383','la jirafa','żyrafa','3','0','0'),\n" +
                "('1384','el camello','wielbłąd','5','0','0'),\n" +
                "('1385','el hipopótamo','hipopotam','5','0','0'),\n" +
                "('1386','el elfante','słoń','3','0','0'),\n" +
                "('1387','el tigre','tygrys','2','0','0'),\n" +
                "('1388','el mono','małpa','2','0','0'),\n" +
                "('1389','la gorila','goryl','3','0','0'),\n" +
                "('1390','el canguro','kangur','5','0','0'),\n" +
                "('1391','el oso','niedźwiedź','5','0','0'),\n" +
                "('1392','el koala','koala','5','0','0'),\n" +
                "('1393','la paloma','gołąb','2','0','0'),\n" +
                "('1394','el colibrí','koliber','5','0','0'),\n" +
                "('1395','el halcón','sokół','5','0','0'),\n" +
                "('1396','el pelícano','pelikan','5','0','0'),\n" +
                "('1397','el flamenco','flaming','5','0','0'),\n" +
                "('1398','la gaviota','mewa','5','0','0'),\n" +
                "('1399','la cigüeña','bocian','5','0','0'),\n" +
                "('1400','la águila','orzeł','2','0','0'),\n" +
                "('1401','el pingüino','pingwin','5','0','0'),\n" +
                "('1402','el cocodrilo','krokodyl','3','0','0'),\n" +
                "('1403','el loro','papuga','3','0','0'),\n" +
                "('1404','la tortuga','żółw','3','0','0'),\n" +
                "('1405','la rana','żaba','5','0','0'),\n" +
                "('1406','los peces','ryby','3','0','0'),\n" +
                "('1407','el tiburón','rekin','3','0','0'),\n" +
                "('1408','la hormiga','mrówka','5','0','0'),\n" +
                "('1409','la avispa','osa','5','0','0'),\n" +
                "('1410','la abeja','pszczoła','5','0','0'),\n" +
                "('1411','el escorpión','skrpion','3','0','0'),\n" +
                "('1412','la araña','pająk','3','0','0'),\n" +
                "('1413','la mosca','mucha','3','0','0'),\n" +
                "('1414','el mosquito','komar','3','0','0'),\n" +
                "('1415','el caracol','ślimak','3','0','0'),\n" +
                "('1416','la langosta','homar','5','0','0'),\n" +
                "('1417','las plantas','rośliny','3','0','0'),\n" +
                "('1418','el árbol','drzewo','3','0','0'),\n" +
                "('1419','la ciudad','miasto','1','0','0'),\n" +
                "('1420','la easquina','róg ulicy','3','0','0'),\n" +
                "('1421','el cruce','skrzyzowanie','3','0','0'),\n" +
                "('1422','la acera','chodnik','3','0','0'),\n" +
                "('1423','la avenida','aleja','3','0','0'),\n" +
                "('1424','la plaza','plac','2','0','0'),\n" +
                "('1425','la fábrica','fabryka','4','0','0'),\n" +
                "('1426','la iglesia','kościół','1','0','0'),\n" +
                "('1427','la universidad','uniwersytet','1','0','0'),\n" +
                "('1428','el ayuntamiento','ratusz','2','0','0'),\n" +
                "('1429','el rascacielos','drapacz chmur','3','0','0'),\n" +
                "('1430','el pueblo','wioska','1','0','0'),\n" +
                "('1431','el cúpula','kopuła','5','0','0'),\n" +
                "('1432','el templo','świątynia','3','0','0'),\n" +
                "('1433','el puente','most','2','0','0'),\n" +
                "('1434','la presa','zapora','4','0','0'),\n" +
                "('1435','la columna','filar','4','0','0'),\n" +
                "('1436','la catedral','katedra','1','0','0'),\n" +
                "('1437','renacimiento','renesans','4','0','0'),\n" +
                "('1438','gótico','gotyk','4','0','0'),\n" +
                "('1439','el estilo modernista','secesja','4','0','0'),\n" +
                "('1440','los estilos','style','4','0','0'),\n" +
                "('1441','barroco','barok','4','0','0'),\n" +
                "('1442','rococó','rokoko','4','0','0'),\n" +
                "('1443','neoclásico','neoklasyczny','4','0','0'),\n" +
                "('1444','los datos','informacje','2','0','0'),\n" +
                "('1445','la medianoche','północ','2','0','0'),\n" +
                "('1446','el amanecer','wschód słońca','3','0','0'),\n" +
                "('1447','el alba','świt','3','0','0'),\n" +
                "('1448','la puesta el sol','zachód słońca','3','0','0'),\n" +
                "('1449','la mediodía','południe','3','0','0'),\n" +
                "('1450','la mañana','poranek','3','0','0'),\n" +
                "('1451','la noche','noc','1','0','0'),\n" +
                "('1452','la película de ciencia ficcion','5','0','0'),\n" +
                "('1453','los instrumentos','instrumenty','3','0','0'),\n" +
                "('1454','la orquesta','orkiestra','3','0','0'),\n" +
                "('1455','el violin','skrzypce','5','0','0'),\n" +
                "('1456','la nota','nuta','5','0','0'),\n" +
                "('1457','la sinfonía','symfonia','5','0','0'),\n" +
                "('1458','extraño','dziwny','3','0','0'),\n" +
                "('1459','la flauta','flet','5','0','0'),\n" +
                "('1460','el saxofón','saksofon','6','0','0'),\n" +
                "('1461','el anochecer','zmierzch','3','0','0'),\n" +
                "('1462','la tarde','popołudnie','3','0','0'),\n" +
                "('1463','el minuto','minuta','1','0','0'),\n" +
                "('1464','el segundo','sekunda','1','0','0'),\n" +
                "('1465','ahora','teraz','2','0','0'),\n" +
                "('1466','más tarde','3','0','0'),\n" +
                "('1467','media hora','pół godziny','3','0','0'),\n" +
                "('1468','el calendario','kalendarz','4','0','0'),\n" +
                "('1469','el mes','miesiąc','2','0','0'),\n" +
                "('1470','la semana','tydzień','2','0','0'),\n" +
                "('1471','el año','rok','1','0','0'),\n" +
                "('1472','el día','dzień','1','0','0'),\n" +
                "('1473','enero','styczeń','1','0','0'),\n" +
                "('1474','febrero','luty','1','0','0'),\n" +
                "('1475','marzo','marzec','1','0','0'),\n" +
                "('1476','abril','kwiecień','1','0','0'),\n" +
                "('1477','mayo','maj','1','0','0'),\n" +
                "('1478','junio','czerwiec','1','0','0'),\n" +
                "('1479','julio','lipiec','1','0','0'),\n" +
                "('1480','agosto','sierpień','1','0','0'),\n" +
                "('1481','septiembre','wrzesień','1','0','0'),\n" +
                "('1482','octubre','październik','1','0','0'),\n" +
                "('1483','noviembre','listopad','1','0','0'),\n" +
                "('1484','diciembre','grudzień','1','0','0'),\n" +
                "('1485','domingo','niedziela','1','0','0'),\n" +
                "('1486','sábado','sobota','1','0','0'),\n" +
                "('1487','viernes','piątek','1','0','0'),\n" +
                "('1488','jueves','czwartek','1','0','0'),\n" +
                "('1489','miércoles','środa','1','0','0'),\n" +
                "('1490','martes','wtorek','1','0','0'),\n" +
                "('1491','lunes','poniedziałek','1','0','0'),\n" +
                "('1492','la primavera','wiosna','1','0','0'),\n" +
                "('1493','el verano','lato','1','0','0'),\n" +
                "('1494','el otoño','jesień','1','0','0'),\n" +
                "('1495','el invierno','zima','1','0','0'),\n" +
                "('1496','el siglo','wiek','3','0','0'),\n" +
                "('1497','la década','dekada','3','0','0'),\n" +
                "('1498','el milenio','tysiąclecie','3','0','0'),\n" +
                "('1499','esta semana','w tym tygodniu','3','0','0'),\n" +
                "('1500','la semana pasada','w zeszłym tygodniu','3','0','0'),\n" +
                "('1501','la semana que viene','w przyszłym tygodniu','3','0','0'),\n" +
                "('1502','antes de ayer','przedwczoraj','3','0','0'),\n" +
                "('1503','pasado mañana','pojutrze','3','0','0'),\n" +
                "('1504','semanalmente','cotygodniowy','3','0','0'),\n" +
                "('1505','mensual','comiesięczny','3','0','0'),\n" +
                "('1506','anual','roczny','3','0','0'),\n" +
                "('1507','el festivo','święto','3','0','0'),\n" +
                "('1508','la hora','godzina','2','0','0'),\n" +
                "('1509','ayer','wczoraj','2','0','0'),\n" +
                "('1510','hoy','dzisiaj','2','0','0'),\n" +
                "('1511','el fin de semana','koniec tygodnia','2','0','0'),\n" +
                "('1512','los números','liczby','1','0','0'),\n" +
                "('1513','cero','zero','1','0','0'),\n" +
                "('1514','uno','jeden','1','0','0'),\n" +
                "('1515','dos','dwa','1','0','0'),\n" +
                "('1516','tres','trzy','1','0','0'),\n" +
                "('1517','cuatro','cztery','1','0','0'),\n" +
                "('1518','cinco','pięc','1','0','0'),\n" +
                "('1519','seis','sześć','1','0','0'),\n" +
                "('1520','siete','siedem','1','0','0'),\n" +
                "('1521','ocho','osiem','1','0','0'),\n" +
                "('1522','nueve','dziewięć','1','0','0'),\n" +
                "('1523','diez','dziesięć','1','0','0'),\n" +
                "('1524','once','jedenaście','1','0','0'),\n" +
                "('1525','doce','dwanaście','1','0','0'),\n" +
                "('1526','trece','trzynascie','1','0','0'),\n" +
                "('1527','catorce','czternaście','1','0','0'),\n" +
                "('1528','quince','piętnaście','1','0','0'),\n" +
                "('1529','dieciséis','szesnaście','1','0','0'),\n" +
                "('1530','diecisiete','siedemnaście','1','0','0'),\n" +
                "('1531','dieciocho','osiemnaście','1','0','0'),\n" +
                "('1532','diecinueve','dziewiętnaście','1','0','0'),\n" +
                "('1533','veinte','dwadzieścia','1','0','0'),\n" +
                "('1534','veintiuno','dwadzieścia jeden','1','0','0'),\n" +
                "('1535','veintidós','dwadzieścia dwa','1','0','0'),\n" +
                "('1536','treinta','trzydzieści','1','0','0'),\n" +
                "('1537','cuarenta','czterdzieści','1','0','0'),\n" +
                "('1538','cincuenta','pięcdziesiąt','1','0','0'),\n" +
                "('1539','sesenta','sześćdziesiąt','1','0','0'),\n" +
                "('1540','setenta','siedemdziesiąt','1','0','0'),\n" +
                "('1541','ochenta','osiemdziesiąt','1','0','0'),\n" +
                "('1542','noventa','dziewięćdziesiąt','1','0','0'),\n" +
                "('1543','cien','sto','1','0','0'),\n" +
                "('1544','ciento diez','sto dziesięć','2','0','0'),\n" +
                "('1545','doscientos','dwieście','2','0','0'),\n" +
                "('1546','trescientos','','trzysta','2','0','0'),\n" +
                "('1547','cuatrocientos','czterysta','2','0','0'),\n" +
                "('1548','quinientos','pięćset','2','0','0'),\n" +
                "('1549','seiscientos','sześćset','2','0','0'),\n" +
                "('1550','setecientos','siedemset','2','0','0'),\n" +
                "('1551','ochocientos','osiemset','2','0','0'),\n" +
                "('1552','novecientos','','dziewięćset','2','0','0'),\n" +
                "('1553','mil','tysiąc','3','0','0'),\n" +
                "('1554','el kilogramo','kilogram','3','0','0'),\n" +
                "('1555','el kilómetro','kilometr','3','0','0'),\n" +
                "('1556','la milla','mila','4','0','0'),\n" +
                "('1557','la yard','jard','4','0','0'),\n" +
                "('1558','el metro','metr','2','0','0'),\n" +
                "('1559','medir','mierzyć','4','0','0'),\n" +
                "('1560','pesar','ważyć','4','0','0'),\n" +
                "('1561','la bolsa','worek','5','0','0'),\n" +
                "('1562','el volumen','objętość','4','0','0'),\n" +
                "('1563','el mapamundi','mapa świata','3','0','0'),\n" +
                "('1564','el mundo','świat','1','0','0'),\n" +
                "('1565','el mar báltico','morze bałtyckie','2','0','0'),\n" +
                "('1566','el mar del norte','morze północne','2','0','0'),\n" +
                "('1567','el mar caribe','morze karaibskie','2','0','0'),\n" +
                "('1568','el amazonas','amazonia','2','0','0'),\n" +
                "('1569','el océano pacífico','ocean spokojny','2','0','0'),\n" +
                "('1570','los andes','andy','3','0','0'),\n" +
                "('1571','el norte','północ','2','0','0'),\n" +
                "('1572','el este','wschód','2','0','0'),\n" +
                "('1573','el oeste','zachód','2','0','0'),\n" +
                "('1574','el sur','południe','2','0','0'),\n" +
                "('1575','el océano atlántico','ocean atlantycki','3','0','0'),\n" +
                "('1576','el mar rojo','morze czerwone','3','0','0'),\n" +
                "('1577','canadá','kanada','2','0','0'),\n" +
                "('1578','estados unidos','stany zjednoczone','1','0','0'),\n" +
                "('1579','méxico','meksyk','1','0','0'),\n" +
                "('1580','América del norte','ameryka północna','1','0','0'),\n" +
                "('1581','América del sur','ameryka południowa','1','0','0'),\n" +
                "('1582','el país','kraj','1','0','0'),\n" +
                "('1583','el estado','państwo','3','0','0'),\n" +
                "('1584','la nación','naród','2','0','0'),\n" +
                "('1585','la provincia','prowincja','3','0','0'),\n" +
                "('1586','la colonia','kolonia','3','0','0'),\n" +
                "('1587','el territorio','terytorium','2','0','0'),\n" +
                "('1588','el principado','księstwo','3','0','0'),\n" +
                "('1589','reino unido','zjednoczone królestwo','2','0','0'),\n" +
                "('1590','inglaterra','anglia','2','0','0'),\n" +
                "('1591','suecia','szwecja','2','0','0'),\n" +
                "('1592','república checa','czechy','2','0','0'),\n" +
                "('1593','suiza','szwajcaria','2','0','0'),\n" +
                "('1594','europa','europa','1','0','0'),\n" +
                "('1595','africa','afryka','1','0','0'); Commit;");*/
    }
}