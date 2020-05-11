package com.example.introfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText enterText;
    private EditText enterThought;
    private Button saveButton;
    private TextView titleTextview;
    //    private TextView thoughtTextview;
    private Button showDetails;
    private Button updateButton;
    private Button deleteButton;

    public static final String KEY_TITLE = "title";
    public static final String KEY_THOUGHT = "thought";

    //Create connection to firestore
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.collection("Journal")
            .document("First thought");
    private CollectionReference collectionReference = db.collection("Journal");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterText = findViewById(R.id.editText);
        enterThought = findViewById(R.id.editThought);
        saveButton = findViewById(R.id.saveButton);
//        thoughtTextview = findViewById(R.id.rec_thought);
        titleTextview = findViewById(R.id.rec_title);
        showDetails = findViewById(R.id.showDetailButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(this);
        showDetails.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveButton:
                addThought();
                break;
            case R.id.showDetailButton:
                getThought();
                break;
            case R.id.updateButton:
                update();
                break;
            case R.id.deleteButton:
                delete();
                break;
        }
    }

    private void addThought() {
        String title = enterText.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();

        Journal journal = new Journal(title,thought);

        collectionReference.add(journal)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT)
                                .show();
                    }
                })


                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Saved Failed " + e.toString(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void showDetails() {
        getThought();
//        documentReference
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            Journal journal = documentSnapshot.toObject(Journal.class);
//                            if (journal != null) {
//                                titleTextview.setText(journal.getTitle());
//                                thoughtTextview.setText(journal.getThought());
//                            }
//
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Failed ", Toast.LENGTH_LONG)
//                                    .show();
//                        }
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Failed " + e.toString(), Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
    }

    private void saveDetails() {

        addThought();
//        String title = enterText.getText().toString().trim();
//        String thought = enterThought.getText().toString().trim();
//
//        Journal journal = new Journal();
//        journal.setTitle(title);
//        journal.setThought(thought);
//        Map<String, Object> data = new HashMap<>();
//
//        data.put(KEY_TITLE, title);
//        data.put(KEY_THOUGHT, thought);

//        documentReference
//                .set(journal)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), "Saved Failed " + e.toString(), Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                });
    }

    private void update() {
        String title = enterText.getText().toString().trim();
//        String thought = enterThought.getText().toString().trim();
        Map<String, Object> data = new HashMap<>();


        data.put(KEY_TITLE, title);
//        data.put(KEY_THOUGHT, thought);

        collectionReference.
                 document().update(data).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT)
                                .show();

                    }
                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Saved Failed " + e.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
        });

//        documentReference.update(data).addOnSuccessListener(
//                new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT)
//                                .show();
//
//                    }
//                }
//        ).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Saved Failed " + e.toString(), Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
    }

    private void delete() {

        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
//                        StringBuilder stringBuilder = new StringBuilder();
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                            Journal journal = snapshots.toObject(Journal.class);
                            data += "Title :" + journal.getTitle() + "\n" +
                                    "Journal :" + journal.getThought() + "\n\n";


                        }
                        //delete all
                        collectionReference.document().update(data,FieldValue.delete());


                    }
                });

        //DELETE A FIELD
//        documentReference.update(KEY_THOUGHT, FieldValue.delete()).addOnSuccessListener(
//                new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT)
//                                .show();
//
//                    }
//                }
//        ).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Failed" + e.toString(), Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
    }

    private void getThought() {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
//                        StringBuilder stringBuilder = new StringBuilder();
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                            Journal journal = snapshots.toObject(Journal.class);
                            data += "Title :" + journal.getTitle() + "\n" +
                                    "Journal :" + journal.getThought() + "\n\n";


                        }
                        titleTextview.setText(data);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Saved Failed " + e.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "Failed " + e.toString(), Toast.LENGTH_SHORT)
                            .show();

                }
                String data = "";
                if (queryDocumentSnapshots != null) {

                    StringBuilder stringBuilder = new StringBuilder();
                    for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {

                        Journal journal = snapshots.toObject(Journal.class);
                        data += "Title :" + journal.getTitle() + "\n" +
                                "Journal :" + journal.getThought() + "\n\n";
                    }
                }
                titleTextview.setText(data);
            }
        });
    }
}

//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Toast.makeText(getApplicationContext(), "Failed " + e.toString(), Toast.LENGTH_SHORT)
//                            .show();
//
//                } String data="";
//                if (documentSnapshot != null && documentSnapshot.exists()) {
//                    Journal journal = documentSnapshot.toObject(Journal.class);
//                    if (journal != null) {
//
//                        data = "Title :" + journal.getTitle() + "\n" +
//                                "Journal :"  + journal.getThought();
//                        titleTextview.setText(data);
////                        titleTextview.setText(journal.getTitle());
////                        thoughtTextview.setText(journal.getThought());
//                    }
//
//                } else {
//
//                    titleTextview.setText(data);
////                    titleTextview.setText("");
////                    thoughtTextview.setText("");
//
//                }
//
//            }
//        });




