package com.peregudova.multinote.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.peregudova.multinote.R;
import com.peregudova.multinote.requests.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NotesViewHolder>{
    private final LayoutInflater inflater;
    ArrayList<Note> notes;
    private Context context;
    private static RecyclerViewClickListener itemListener;

    RVAdapter(Context context, Map<String, Note> notes, RecyclerViewClickListener itemListener){
        this.notes = new ArrayList<>();
        this.notes.addAll(notes.values());
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        RVAdapter.itemListener = itemListener;

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.card, parent, false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RVAdapter.NotesViewHolder holder, int position) {

        holder.owner.setText(notes.get(position).getOwner());
        if(notes.get(position).getText().length()>50) {
            holder.text.setText(notes.get(position).getText().substring(0, 50));
        } else{
            holder.text.setText(notes.get(position).getText());
        }
        holder.time.setText(notes.get(position).getLast_modified());
        holder.id.setText(notes.get(position).getId());
    }

    public Note getNoteByPosition(int position){
        return this.notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull @NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public static class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView text;
        TextView owner;
        TextView time;
        TextView id;
        NotesViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            text = itemView.findViewById(R.id.note_text);
            owner = itemView.findViewById(R.id.note_owner);
            time = itemView.findViewById(R.id.note_time);
            itemView.setOnClickListener(this);
            id = itemView.findViewById(R.id.id_note);
        }

        @Override
        public void onClick(View view) {
            itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
        }
    }
}
