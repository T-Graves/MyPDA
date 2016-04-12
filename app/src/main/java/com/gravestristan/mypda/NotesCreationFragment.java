package com.gravestristan.mypda;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

    EditText mNoteContent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_creation, container, false);

        mNoteContent = (EditText) view.findViewById(R.id.note_content_field);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save_note).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.action_save_note:
                NoteObjects noteObject = new NoteObjects();
                PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);

                noteObject.setNoteContents(mNoteContent.getText().toString());

                dbHandler.addNote(noteObject);
                dbHandler.close();

                getFragmentManager().popBackStack();
                Snackbar snackBar = Snackbar
                        .make(getView(), "Note Created", Snackbar.LENGTH_SHORT);
                snackBar.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
