package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Tristan on 3/27/2016.
 */
public class SingleNoteFragment extends Fragment implements AppStatics{

    EditText mNoteContent;

    private UUID mCurrentUUID;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_creation, container, false);

        mCurrentUUID = UUID.fromString(getArguments().getString("currentNoteUUID"));
        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
        NoteObjects currentNote = dbHandler.getNote(mCurrentUUID);
        dbHandler.close();

        mNoteContent = (EditText) view.findViewById(R.id.note_content_field);
        mNoteContent.setText(currentNote.getNoteContents());

        return view;
    }

    /**
     *
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save_note).setVisible(true);
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveNote();
                return true;
            case R.id.action_save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     */
    private void saveNote(){
        NoteObjects noteObject = new NoteObjects();
        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

        noteObject.setNoteId(mCurrentUUID);
        noteObject.setNoteContents(mNoteContent.getText().toString());

        dbHandler.updateNote(noteObject);

        dbHandler.close();
        getFragmentManager().popBackStack();
    }
}
