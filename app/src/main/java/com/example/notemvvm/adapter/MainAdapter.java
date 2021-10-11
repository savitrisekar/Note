package com.example.notemvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemvvm.databinding.ItemNoteBinding;
import com.example.notemvvm.model.Note;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<Note> noteList;
    private HandleNoteClick clickListener;
    private ItemNoteBinding binding;

    public MainAdapter(Context context, HandleNoteClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.binding.tvTitleNote.setText(this.noteList.get(position).title);
        holder.binding.tvDscNote.setText(this.noteList.get(position).description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.editClick(noteList.get(position));
            }
        });

        holder.binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.removeClick(noteList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (noteList == null || noteList.size() == 0) {
            return 0;
        } else {
            return noteList.size();
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding binding;

        public MainViewHolder(@NonNull ItemNoteBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }

    public interface HandleNoteClick {
        void removeClick(Note note);

        void editClick(Note note);
    }
}
