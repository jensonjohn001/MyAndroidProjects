package com.xyz.bill_master;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PrintEG extends AppCompatActivity {

        private static final String LOG_TAG = "Error";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_print_eg);
        }



        /**
         * Prints the contents on the screen to a PDF file,
         * which i then saved in Documents/PDF
         * @param view The clicked view
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void printPDF(View view) throws IOException {
            if(isExternalStorageWritable()) {
                String filename = getFileName();
                File file = new File(getAlbumStorageDir("PDF"), filename);

                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    createPDF(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        /**
         * Checks if external storage is available for read and write
         * @return boolean
         */
        private boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        /**
         * Returns a name for the file that will be created
         * @return String
         */
        private String getFileName() {
            //TODO: 06/10/2015
            return "file2" + ".pdf";
        }

        /**
         * Creates a PDF document and writes it to external storage using the
         * received FileOutputStream object
         * @param outputStream a FileOutputStream object
         * @throws IOException
         */
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void createPDF(FileOutputStream outputStream) throws IOException {
            PrintedPdfDocument document = new PrintedPdfDocument(this,
                    getPrintAttributes());

            // start a page
            PdfDocument.Page page = document.startPage(1);

            // draw something on the page
            View content = getContentView();
            content.draw(page.getCanvas());

            // finish the page
            document.finishPage(page);
            //. . .
            // add more pages
            //. . .
            // write the document content
            document.writeTo(outputStream);

            //close the document
            document.close();
        }


        private View getContentView() {
            return findViewById(R.id.relativeLayout);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private PrintAttributes getPrintAttributes() {
            PrintAttributes.Builder builder = new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(new PrintAttributes.Resolution("res1","Resolution",50,50)).setMinMargins(new PrintAttributes.Margins(5, 5, 5, 5));
            PrintAttributes printAttributes = builder.build();
            return printAttributes;
        }


        private File getAlbumStorageDir(String albumName) {
            // Get the directory for the user's public pictures directory.
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), albumName);
            if (!file.exists()) {
                file.mkdir();
                Log.e(LOG_TAG, "Directory not created");
            }
            return file;
        }

    public void load_other_activity(View view) {
        Intent i=new Intent(getApplicationContext(),Billing.class);
        startActivity(i);
    }

    public void load_preview(View view) {
        Intent i=new Intent(getApplicationContext(),PrintPreview.class);
        startActivity(i);
        }
}
