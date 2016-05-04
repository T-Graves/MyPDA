package com.gravestristan.mypda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * This class is the main notes menu. It displays all notes that the user currently has.
 * Created by Tristan on 3/1/2016.
 */
public class NotesMenuFragment extends Fragment implements AppStatics{

    private ArrayList<NoteObjects> mNoteObjects;
    private RecyclerView mNoteList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * The onCreate method for the NotesMenuFragment. It makes sure this fragment has the options
     * menu.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * The onCreateView method for the NotesMenuFragment. It inflates the view and strings up all
     * view items.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_menu, container, false);

        mNoteList = (RecyclerView) view.findViewById(R.id.notes_recycler_view);
        mNoteList.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mNoteList.setLayoutManager(mLayoutManager);
        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
        mNoteObjects = dbHandler.getAllNoteItems();
        dbHandler.close();
        mAdapter = new NotesRecyclerViewAdapter(mNoteObjects);
        mNoteList.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        mNoteList.addItemDecoration(itemDecoration);

        ((NotesRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
                             NotesRecyclerViewAdapter.NoteClickListener() {
                                 /**
                                  * The onItemClick method for the NotesRecyclerViewAdapter.
                                  * @param position The position of the item clicked.
                                  * @param v The view being passed in.
                                  */
                                 @Override
                                 public void onItemClick(int position, View v) {
                                     SingleNoteFragment currentNote = new SingleNoteFragment();
                                     Bundle args = new Bundle();
                                     args.putString("currentNoteUUID", mNoteObjects.get(position).getNoteId().toString());

                                     currentNote.setArguments(args);
                                     swapFragmentHandler(currentNote);
                                 }
                             });

        ((NotesRecyclerViewAdapter) mAdapter).setOnItemLongClickListener(new NotesRecyclerViewAdapter.NoteLongClickListener() {
            /**
             * The onItemLongClick method for the NotesRecyclerViewAdapter.
             * @param position The position of the item clicked.
             * @param v The view being passed in.
             */
            @Override
            public void onItemLongClick(int position, View v) {
                final int itemPosition = position;
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Delete Note");
                builder.setMessage("Delete This Note?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    /**
                     * The onClick method for the yes option of the alert dialog.
                     * @param dialog The dialog interface being passed in.
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PDADBHandler dbHandler = new PDADBHandler(getContext(), null, null, DATABASE_VERSION);
                        dbHandler.deleteNoteFromNoteTable(mNoteObjects.get(itemPosition));
                        dbHandler.close();
                        mNoteObjects.remove(itemPosition);
                        ((NotesRecyclerViewAdapter) mAdapter).deleteitem(itemPosition);
                        noteDeletedSnackBar(view);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    /**
                     * The onClick method for the no option of the alert dialog.
                     * @param dialog The dialog interface being passed in.
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        return view;
    }

    /**
     * This method displays a SnackBar message when an item is deleted.
     * @param view
     */
    public void noteDeletedSnackBar(View view){
        Snackbar snackbar = Snackbar
                .make(view, "Note Deleted", Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    /**
     * This method sets the new note menu item to be visible.
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_new_note).setVisible(true);
    }

    /**
     * This method handles what to do when a menu item is clicked.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.action_new_note:
                NotesCreationFragment createNewNote = new NotesCreationFragment();
                swapFragmentHandler(createNewNote);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This method is used to swap fragments. It is out in its own method to clean up the code a
     * little bit.
     * @param newFragment The fragment to swap to.
     */
    private void swapFragmentHandler(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
