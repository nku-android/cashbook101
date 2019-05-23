package devlight.io.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class PageTodolist extends Fragment {

    private List<String> list = new ArrayList<String>();
    private ListView todoList;
    private ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_todolist,container,false);
        for(int i=0;i<50;i++){
            list.add("todolist"+i);
        }

        ListAdapter listAdapter = new ListAdapter(getActivity(),R.layout.item_todolist,list);
        todoList = (ListView) view.findViewById(R.id.todolist);
        todoList.setAdapter(listAdapter);
        return view;
    }


//    @Override
//    protected void onCreate(final Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_horizontal_ntb);
//
//        for(int i=0;i<50;i++){
//            list.add("todolist"+i);
//        }
//
//        ListAdapter listAdapter = new ListAdapter(PageTodolist.this,R.layout.item_list,list);
//        todoList = (ListView) findViewById(R.id.todolist);
//        todoList.setAdapter(listAdapter);
//
//
//    }

}

