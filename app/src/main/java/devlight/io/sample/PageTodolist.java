package devlight.io.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class PageTodolist extends Fragment implements ListAdapter.InnerItemOnclickListener {

    private List<String> list = new ArrayList<String>();
    private ListView todoList;
    private ListAdapter listAdapter;
    private FloatingActionButton addbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_todolist,container,false);
        for(int i=0;i<50;i++){
            list.add("todolist"+i);
        }

        todoList = (ListView) view.findViewById(R.id.todolist);
        addbutton=view.findViewById(R.id.addbutton);
        listAdapter = new ListAdapter(getActivity(),R.layout.item_todolist,list,todoList);
        listAdapter.setOnInnerItemOnClickListener(this);
        todoList.setAdapter(listAdapter);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), addtask.class));

            }
        });


        return view;
    }


    @Override
    public void itemClick(View v) {
        ImageButton btn =  getView().findViewById(v.getId());
        int position = (int) v.getTag();
        list.remove(position);
        listAdapter.notifyDataSetChanged();

    }
}

