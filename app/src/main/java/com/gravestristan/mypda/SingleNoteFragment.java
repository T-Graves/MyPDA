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

import java.util.UUID;

/**
 * Created by Tristan on 3/27/2016.
 */
public class SingleNoteFragment extends Fragment implements AppStatics{

    EditText mNoteTitle;
    EditText mNoteContent;

    Button mUpdateButton;

    private UUID mCurrentUUID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_creation, container, false);

        mCurrentUUID = UUID.fromString(getArguments().getString("currentNoteUUID"));
        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
        NoteObjects currentNote = dbHandler.getNote(mCurrentUUID);
        dbHandler.close();

        mNoteTitle = (EditText) view.findViewById(R.id.note_title_field);
        mNoteTitle.setText(currentNote.getNoteTitle());
        mNoteContent = (EditText) view.findViewById(R.id.note_content_field);
        mNoteContent.setText(currentNote.getNoteContents());

        mUpdateButton = (Button) view.findViewById(R.id.create_or_update_button);
        mUpdateButton.setText("Update");

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteObjects noteObject = new NoteObjects();
                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

                noteObject.setNoteId(mCurrentUUID);
                noteObject.setNoteTitle(mNoteTitle.getText().toString());
                noteObject.setNoteContents(mNoteContent.getText().toString());

                dbHandler.updateNote(noteObject);

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
