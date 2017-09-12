package com.konggit.appnot.data;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.konggit.appnot.Results;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class ExternalStorage implements Data {

    Context context;

    public ExternalStorage(Context context) {
        this.context = context;
    }

    @Override
    public Map<Date, Results> getData(String saveName) {
        return null;
    }

    @Override
    public void setData(Map<Date, Results> data, String saveName) {

        //read write permissions needed till android 4.4
        if (isExternalStorageWritable()) {

            isExternalStorageReadable();
            //If you are handling files that are not intended for other apps to use (such as graphic textures
            // or sound effects used by only your app), you should use a private storage directory on
            // the external storage by calling getExternalFilesDir()
            File file = new File(Environment.getExternalStorageDirectory(), saveName);

        } else {

            Log.i("ExternalStorage", "Not writable");

        }

        //create new dir
        //String newFolder = "/myFolder2";
        //String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        //File myNewFolder = new File(extStorageDirectory + newFolder);
        //myNewFolder.mkdir();

        //?
        //File files_folder = getFilesDir();
        //File files_child = new File(files_folder, "files_child");
        //files_child.mkdirs();
        //File created_folder = getDir("custom", MODE_PRIVATE);
        //File f1_child = new File(created_folder, "custom_child");
        //f1_child.mkdirs();

    }

    // Checks if external storage is available for read and write
    private boolean isExternalStorageWritable() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            return true;

        }

        return false;
    }

    private boolean isExternalStorageReadable() {

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {

                Log.i("ExternalStorage", "Only readable");

                return true;
            }

            Log.i("ExternalStorage", "Readable");

            return true;
        }

        Log.i("ExternalStorage", "Not readable");

        return false;
    }


    public String getSDCardDirectory(){
        String SdcardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        String dir = SdcardPath.substring(SdcardPath.lastIndexOf('/') + 1);
        System.out.println(dir);
        String[] trimmed = SdcardPath.split(dir);
        String sdcardPath = trimmed[0];
        System.out.println(sdcardPath);
        return sdcardPath;
    }
    //I recommend you to use Environment.DIRECTORY_DCIM, because of most people never move their
    // directory of DCIM (photo) path from sdcard to Internal Storage. Usually, always be in sdcard.


}


/*
//save image to external storage(sd card)
//http://android-er.blogspot.com/2010/07/save-file-to-sd-card.html
String image_URL=
 "http://chart.apis.google.com/chart?chs=200x200&cht=qr&chl=http%3A%2F%2Fandroid-er.blogspot.com%2F";

String extStorageDirectory;

Bitmap bm;

  //Called when the activity is first created.
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Button buttonSave = (Button)findViewById(R.id.save);

    ImageView bmImage = (ImageView)findViewById(R.id.image);
    BitmapFactory.Options bmOptions;
    bmOptions = new BitmapFactory.Options();
    bmOptions.inSampleSize = 1;
    bm = LoadImage(image_URL, bmOptions);
    bmImage.setImageBitmap(bm);

    extStorageDirectory = Environment.getExternalStorageDirectory().toString();

    buttonSave.setText("Save to " + extStorageDirectory + "/qr.PNG");
    buttonSave.setOnClickListener(buttonSaveOnClickListener);
}

    private Bitmap LoadImage(String URL, BitmapFactory.Options options)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (IOException e1) {
        }
        return bitmap;
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException{
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try{
            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
        }
        return inputStream;
    }

    Button.OnClickListener buttonSaveOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            OutputStream outStream = null;
            File file = new File(extStorageDirectory, "er.PNG");
            try {
                outStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();

                Toast.makeText(AndroidWebImage.this, "Saved", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AndroidWebImage.this, e.toString(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AndroidWebImage.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    };

}
*/