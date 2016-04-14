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
 * This class is used to create a new note.
 * Created by Tristan on 3/27/2016.
 */
public class NotesCreationFragment extends Fragment implements AppStatics {

    EditText mNoteContent;

    /**
     * The onCreate method for NotesCreationFragment. It makes sure this fragment has the options
     * menu.
     * @param savedInstanceState The bundle being passed in.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * The onCreateView method for the NotesCreationFragment. It inflates the view for this
     * fragment and strings up the Edit text field used in this fragment.
     * @param inflater The layout inflater being passed in.
     * @param container The view group being passed in.
     * @param savedInstanceState The bundle being passed in.
     * @return Returns the inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_creation, container, false);

        mNoteContent = (EditText) view.findViewById(R.id.note_content_field);

        return view;
    }

    /**
     * This method sets the save note menu item to be visible when this fragment is being displayed.
     * @param menu The menu being passed in.
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_save_note).setVisible(true);
    }

    /**
     * This method handles what to do when a menu item is clicked.
     * @param item
     * @return
     */
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
