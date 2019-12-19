package com.example.phoc;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import static android.media.ExifInterface.TAG_EXPOSURE_TIME;
import static android.media.ExifInterface.TAG_FLASH;
import static android.media.ExifInterface.TAG_ISO_SPEED_RATINGS;

import com.bumptech.glide.Glide;
import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.example.phoc.DatabaseConnection.MyOnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Upload extends AppCompatActivity implements View.OnClickListener{

    Button upload;
    ImageView upload_image;
    TextView titleNameText;
    Uri selectedImageUri;
    String exifJson;
    ExifInterface exif;
    String titleName;
    EditText contentText;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://phoc-50746.appspot.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //intent로 갤러리에서 선택한 사진URI 받기
        Intent intent = getIntent(); /*데이터 수신*/
        String imageUriString = intent.getStringExtra("imageUriString");
        titleName =  intent.getStringExtra("titleName");
        selectedImageUri = Uri.parse(imageUriString);
        Log.e("전시화면에서 스트링Uri",imageUriString);
        Log.e("전시화면에서 Uri",selectedImageUri.toString());
        initLayout();
    }

    private void initLayout()
    {
        setContentView(R.layout.activity_upload);

        upload = (Button)findViewById(R.id.uploadBtn);
        upload.setOnClickListener(this);

        titleNameText = (TextView)findViewById(R.id.themeText);
        titleNameText.setText("#"+titleName);

        contentText = findViewById(R.id.infoText);

        //URI로 화면에 사진 뿌리기
        upload_image = (ImageView) findViewById(R.id.upload_image);
        upload_image.setImageURI(selectedImageUri);

        //exif값을 JSON포맷으로 String변수에 저장
        makeExif();



//        Bitmap bm = Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//        imgView.setImageBitmap(bm);
    }

    private void makeExif() {
        //사진에서 exif값 추출
        try {
            exif = new ExifInterface(getPath(selectedImageUri));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exif Error!", Toast.LENGTH_LONG).show();
        }

        exifJson = "";
        String flash;
        String iso;
        String exposure;

        //플래시 설정값
        flash = exif.getAttribute(TAG_FLASH);
        //ISO 설정값
        iso = exif.getAttribute(TAG_ISO_SPEED_RATINGS);
        //exposure time(셔터스피드) 설정값
        double temp = exif.getAttributeDouble(TAG_EXPOSURE_TIME, 0) * 1000000000l;
        exposure = String.valueOf((long) temp);


        exifJson = "{\"TAG_EXPOSURE_TIME\":\"" + exposure + "\",\"TAG_ISO_SPEED_RATINGS\":\"" + iso + "\",\"TAG_FLASH\":\"" + flash + "\"}";
        Log.e("exifJson출력결과", exifJson);

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    @Override
    public void onClick(View v) {
        if(v == upload){
            Log.d("upload", selectedImageUri.toString());
            //DatabaseQueryClass.Post.createPost(exifJson, contentText.getText(), )
            /*
            final String cameraSettingJson,
            final String content,
            final String imgUrl,
            final String theme)

             */
            FirebaseStorage storage = FirebaseStorage.getInstance("gs://phoc-50746.appspot.com");
            final StorageReference storageRef = storage.getReference();
            StorageReference riverRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());
            UploadTask uploadTask = riverRef.putFile(selectedImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("uploadd", e.toString());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("uploadd", taskSnapshot.getMetadata().toString());
                    StorageReference imageRef = storageRef.child("images/" + selectedImageUri.getLastPathSegment());

                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("uploadd", uri.toString());
                            DatabaseQueryClass.Post.createPost(exifJson, contentText.getText().toString(), uri.toString(), titleName, new MyOnSuccessListener() {
                                @Override
                                public void onSuccess() {
                                    goToMain();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                }
            });

        }

    }
    private void goToMain(){
        startActivity(new Intent(this, main.class));
        finish();
    }
}
