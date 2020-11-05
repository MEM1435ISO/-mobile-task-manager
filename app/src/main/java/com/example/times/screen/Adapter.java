package com.example.times.screen;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.times.App;
import com.example.times.R;
import com.example.times.model.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private SortedList<Note> sortedList;

    public Adapter() {// сортировка задач

        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {// сравниватель
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {// менятель
                notifyItemRangeChanged(position, count);

            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {// когда содержиое одинаково
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }


    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteText, tagText, time;
        CheckBox completed;
        View delete;

        Note note;
        boolean silentUpdate;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            ///
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            //itemView.setBackgroundColor(color);

            noteText = itemView.findViewById(R.id.note_text);
            tagText = itemView.findViewById(R.id.tags);
            tagText.setTextColor(color);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteDetailsActivity.start((Activity) itemView.getContext(), note);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getNoteDao().delete(note);
                }
            });

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!silentUpdate) {
                        note.done = isChecked;
                        App.getInstance().getNoteDao().update(note); // Save
                    }
                    updateStrokeOut();
                }
            });
            //
            DateFormat dateFormat = new SimpleDateFormat("dd MMM H:mm");
            Date date = new Date();
            String str = dateFormat.format(date);
            time = itemView.findViewById(R.id.time);
            time.setText(str);//
            //
        }

        public void bind(Note note) {
            this.note = note;
            noteText.setText(note.text);
            tagText.setText(note.tag);
            updateStrokeOut();
            silentUpdate = true;
            completed.setChecked(note.done);
            silentUpdate = false;

        }

        private void updateStrokeOut() {// если выполнена задача вычеркивает
            if (note.done) {
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                completed.setTextColor(Color.parseColor("#1c9600"));
                completed.setText("выполнено");
            } else {
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                completed.setTextColor(Color.parseColor("#b50000"));
                completed.setText("выполнить");
            }
        }
    }

}