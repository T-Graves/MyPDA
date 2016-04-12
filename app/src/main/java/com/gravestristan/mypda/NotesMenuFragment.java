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
 * Created by Tristan on 3/1/2016.
 */
public class NotesMenuFragment extends Fragment implements AppStatics{

    private ArrayList<NoteObjects> mNoteObjects;
    private RecyclerView mNoteList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
            @Override
            public void onItemLongClick(int position, View v) {
                //Log.d(TAG, "Note clicked long");
                final int itemPosition = position;
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle("Delete Note");
                builder.setMessage("Delete This Note?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

    public void noteDeletedSnackBar(View view){
        Snackbar snackbar = Snackbar
                .make(view, "Note Deleted", Snackbar.LENGTH_SHORT);

        snackbar.show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_new_note).setVisible(true);
    }

    /**
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     *
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
     *
     * @param newFragment
     */
    private void swapFragmentHandler(Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
