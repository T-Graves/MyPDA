package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Tristan on 3/27/2016.
 */
public class NotesCreationFragment extends Fragment implements AppStatics {

    EditText mNoteTitle;
    EditText mNoteContent;

    Button mCreateButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_creation, container, false);

        mNoteTitle = (EditText) view.findViewById(R.id.note_title_field);
        mNoteContent = (EditText) view.findViewById(R.id.note_content_field);

        mCreateButton = (Button) view.findViewById(R.id.create_or_update_button);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NoteObjects noteObject = new NoteObjects();
                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

                noteObject.setNoteTitle(mNoteTitle.getText().toString());
                noteObject.setNoteContents(mNoteContent.getText().toString());

                dbHandler.addNote(noteObject);
                dbHandler.close();

                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
