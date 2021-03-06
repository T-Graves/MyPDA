package com.gravestristan.mypda;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is the RecyclerView adapter for the notes menu.
 * Created by Tristan on 3/26/2016.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteItemHolder> implements AppStatics {

    private ArrayList<NoteObjects> mNoteObjectsArray;
    private static NoteClickListener noteClickListener;
    private static NoteLongClickListener noteLongClickListener;

    /**
     *
     */
    public static class NoteItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView noteContent;

        /**
         *
         * @param itemView
         */
        public NoteItemHolder(View itemView){
            super(itemView);

            noteContent = (TextView) itemView.findViewById(R.id.note_content);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        /**
         *
         * @param v
         */
        @Override
        public void onClick(View v){
            noteClickListener.onItemClick(getAdapterPosition(), v);
        }

        /**
         *
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v){
            noteLongClickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }

    }

    /**
     *
     * @param noteClickListener
     */
    public void setOnItemClickListener(NoteClickListener noteClickListener){
        this.noteClickListener = noteClickListener;
    }

    /**
     *
     * @param noteLongClickListener
     */
    public void setOnItemLongClickListener(NoteLongClickListener noteLongClickListener){
        this.noteLongClickListener = noteLongClickListener;
    }

    /**
     *
     * @param dataSet
     */
    public NotesRecyclerViewAdapter(ArrayList<NoteObjects> dataSet) {
        mNoteObjectsArray = dataSet;
    }

    /**
     *
     * @param index
     */
    public void deleteitem(int index) {
        notifyItemRemoved(index);
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public NoteItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card_view, parent, false);

        NoteItemHolder noteItemHolder = new NoteItemHolder(view);
        return noteItemHolder;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(NoteItemHolder holder, int position){
        holder.noteContent.setText(mNoteObjectsArray.get(position).getNoteContents());
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount(){
        return mNoteObjectsArray.size();
    }


    /**
     *
     */
    public interface NoteClickListener {
        public void onItemClick(int position, View v);
    }

    /**
     *
     */
    public interface NoteLongClickListener {
        public void onItemLongClick(int position, View v);
    }

}
