package com.example.brandonrodriguez.appinventory_1_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PDFGenerator extends AppCompatActivity {

    String NOMBRE_DIRECTORIO = "MisPDFs";
    String NOMBRE_DOCUMENTO = "MiPDF2.pdf";

    private Font T1 = new Font(Font.TIMES_ROMAN,20,Font.BOLD);
    private Font T2 = new Font(Font.TIMES_ROMAN,16,Font.NORMAL);
    private Font T3 = new Font(Font.TIMES_ROMAN,12,Font.BOLD);
    private Font TXT = new Font(Font.TIMES_ROMAN,10,Font.BOLD);


    //private Font titulos = new Font(Font.FontFamily.)

    EditText etTexto;
    Button btnGenerar;
    TextView DateOne;
    Spinner spinner1,spinner2,spinner3;
    // Queda pendiente los radio button
    TextView HoraGen,FechaAtencion,HoraAtencion,NomReciveRep,HorarioTentativo,Folio,Solicitante,FechaDeInicio,Departamento, Sitio;
    EditText InHoraGen,inFechaAtencion,inHoraAtencion,inNomReciveRep,inHorarioTentativo,inFolio,inSolicitante,inFechaDeInicio,inDepar;

    TextView BreveDescri,Observaciones, Mantenimiento, TipoDeMant,MaterialUtilizado,ActiReal;
    EditText inBreveDescri,inObservaciones,inActReali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfgenerator);

        spinner1 = (Spinner)findViewById(R.id.menuSitios);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PDFGenerator.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lista_departamentos));
        spinner1.setAdapter(adapter);

        spinner2 = (Spinner)findViewById(R.id.manteniUrgencia);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(PDFGenerator.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.lista_urgencia_mant));
        spinner2.setAdapter(adapter2);

        spinner3 = (Spinner)findViewById(R.id.tipoDeMant);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(PDFGenerator.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tipo_de_mant));
        spinner3.setAdapter(adapter3);

        ActiReal = findViewById(R.id.ActividadesRealizadas);
        inActReali = findViewById(R.id.inActReali);
        MaterialUtilizado = findViewById(R.id.MaterialUtilizado);
        TipoDeMant = findViewById(R.id.TipoDeMant);
        Mantenimiento = findViewById(R.id.MANTENIMIENTO);
        Observaciones = findViewById(R.id.Observaciones);
        inObservaciones = findViewById(R.id.inObservaciones);
        BreveDescri = findViewById(R.id.BreveDescrip);
        inBreveDescri = findViewById(R.id.inBreveDescrip);
        Sitio = findViewById(R.id.Sitio);
        //Pendiente radio grup de sitio
        etTexto = findViewById(R.id.etTexto);
        btnGenerar = findViewById(R.id.btnGenerar);
        DateOne = findViewById(R.id.DateOne);
        HoraGen = findViewById(R.id.HoraGen);
        InHoraGen = findViewById(R.id.InHoraGen);
        FechaAtencion  = findViewById(R.id.FechaAtencion);
        inFechaAtencion = findViewById(R.id.InFechaAtencion);
        NomReciveRep = findViewById(R.id.NomReciveRep);
        inNomReciveRep = findViewById(R.id.inRecibeRep);
        HorarioTentativo = findViewById(R.id.HorarioTentativo);
        inHorarioTentativo = findViewById(R.id.inHorarioTentativo);
        Folio = findViewById(R.id.Folio);
        inFolio = findViewById(R.id.inFolio);
        Solicitante = findViewById(R.id.Solicitante);
        inSolicitante = findViewById(R.id.inSolicitante);
        FechaDeInicio = findViewById(R.id.FechaDeInicio);
        inFechaDeInicio = findViewById(R.id.inFechaDeInicio);
        Departamento = findViewById(R.id.Departamento);
        inDepar = findViewById(R.id.inDepartamento);
        HoraAtencion = findViewById(R.id.HoraAtencion);
        inHoraAtencion = findViewById(R.id.inHoraAtencion);

        // Permisos
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    1000);
        }

        // Genera el documento
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearPDF();
                Toast.makeText(PDFGenerator.this, "SE CREO EL PDF", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void crearPDF() {
        Document documento = new Document();

        try {
            File file = crearFichero(NOMBRE_DOCUMENTO);
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsolutePath());

            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPDF);

            documento.open();

     // Encabezado
            documento.add(new Paragraph("               Instituto Politécnico Nacional \n",T1));
            documento.add(new Paragraph("               UNIDAD PROFESIONAL INTERDISCIPLINARIA EN \n",T2));
            documento.add(new Paragraph("               INGENIERÍA Y TECNOLOGÍAS AVANZADAS \n",T2));
            documento.add(new Paragraph("               DEPARTAMENTO DE RECURSOSMATERIALES Y SERVICIOS \n\n",T2));
            documento.add(new Paragraph("                        ORDEN DE TRABAJO \n\n",T3));

            //Primer cuadricula

          //  documento.add(new Paragraph( DateOne.getText().toString() + etTexto.getText().toString()+"\n\n"));

            // Insertamos una tabla
            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell(DateOne.getText().toString() + etTexto.getText().toString());
            tabla.addCell(HoraGen.getText().toString() + InHoraGen.getText().toString());
            tabla.addCell(FechaAtencion.getText().toString() + inFechaAtencion.getText().toString());
            tabla.addCell(HoraAtencion.getText().toString() + inHoraAtencion.getText().toString());

            PdfPTable tabla2 = new PdfPTable(2);
            tabla2.addCell(NomReciveRep.getText().toString());
            tabla2.addCell(inNomReciveRep.getText().toString());

            PdfPTable tabla3 = new PdfPTable(4);
            tabla3.addCell(new Phrase(HorarioTentativo.getText().toString(),TXT));
            tabla3.addCell(new Phrase(inHorarioTentativo.getText().toString(),TXT));
            tabla3.addCell(new Phrase(Folio.getText().toString(),TXT));
            tabla3.addCell(new Phrase(inFolio.getText().toString(),TXT));

            PdfPTable tabla4 = new PdfPTable(1);
            tabla4.addCell("USUARIO");
            //tabla4.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla5 = new PdfPTable(2);
            tabla5.addCell(new Phrase(Solicitante.getText().toString() + inSolicitante.getText().toString(),TXT));
            tabla5.addCell(new Phrase(FechaDeInicio.getText().toString() + inFechaDeInicio.getText().toString(),TXT));
            PdfPTable tabla6 = new PdfPTable(1);
            tabla6.addCell(new Phrase("Departamento: " + inDepar.getText().toString(), TXT));
            PdfPTable tabla7 = new PdfPTable(1);
            tabla7.addCell("Sitio: ");
            PdfPTable tabla8 = new PdfPTable(1);
            tabla8.addCell("");

            PdfPTable tabla9 = new PdfPTable(1);
            tabla9.addCell(new Phrase(BreveDescri.getText().toString(),TXT));
           // tabla9.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla9a = new PdfPTable(1);
            tabla9a.addCell(new Phrase(inBreveDescri.getText().toString(),TXT));

            PdfPTable tabla10 = new PdfPTable(1);
            tabla10.addCell(new Phrase(Observaciones.getText().toString(),TXT));
           // tabla10.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla11 = new PdfPTable(1);
            tabla11.addCell(new Phrase(inObservaciones.getText().toString(),TXT));

            PdfPTable tabla12 = new PdfPTable(1);
            tabla12.addCell("");

            // hAY QUE MODIFICAR AUI POR LOS RADIO BUTON
            PdfPTable tabla13 = new PdfPTable(1);
            tabla13.addCell("MANTENIMIENTO");
            //tabla13.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla14 = new PdfPTable(1);
            tabla14.addCell("( PENDIENTE )");
            PdfPTable tabla15 = new PdfPTable(1);
            tabla15.addCell("");

            PdfPTable tabla16 = new PdfPTable(1);
            tabla16.addCell("MATERIAL UTILIZADO");
          //  tabla16.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla17 = new PdfPTable(3);
            tabla17.addCell("Cantidad");tabla17.addCell("UNIDAD");tabla17.addCell("NOMBRE");
            PdfPTable tabla18 = new PdfPTable(1);
            tabla18.addCell("");

            PdfPTable tabla19 = new PdfPTable(1);
            tabla19.addCell("ACTIVIDADES DESARROLLADAS");
            //tabla19.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla20 = new PdfPTable(1);
            tabla20.addCell("( PENDIENTE )");

            documento.add(tabla);
            documento.add(tabla2);
            documento.add(tabla3);
            documento.add(tabla4);
            documento.add(tabla5);
            documento.add(tabla6);
            documento.add(tabla7);
            documento.add(tabla8);
            documento.add(tabla9);
            documento.add(tabla9a);
            documento.add(tabla10);
            documento.add(tabla11);
            documento.add(tabla12);
            documento.add(tabla13);
            documento.add(tabla14);
            documento.add(tabla15);
            documento.add(tabla16);
            documento.add(tabla17);
            documento.add(tabla18);
            documento.add(tabla19);
            documento.add(tabla20);

        } catch(DocumentException e) {
        } catch(IOException e) {
        } finally {
            documento.close();
        }
    }

    public File crearFichero(String nombreFichero) {
        File ruta = getRuta();

        File fichero = null;
        if(ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }

        return fichero;
    }

    public File getRuta() {
        File ruta = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOMBRE_DIRECTORIO);

            if(ruta != null) {
                if(!ruta.mkdirs()) {
                    if(!ruta.exists()) {
                        return null;
                    }
                }
            }

        }
        return ruta;
    }
}
