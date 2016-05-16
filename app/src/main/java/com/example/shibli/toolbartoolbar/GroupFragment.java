package com.example.shibli.toolbartoolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private OnFragmentInteractionListener mListener;
    private String groupName = "";
    private ListView lv;
    private View view;
    private GroupAdapter adapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), Setting.class));

        }
        if (id == R.id.addgroup) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Title");

// Set up the input
            final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    groupName = input.getText().toString();
                    if (groupName.equals("")) {
                        Toast.makeText(getContext(), "Enter Gropu Name", Toast.LENGTH_LONG).show();


                    } else {
                        DatabaseScema scema = new DatabaseScema(getActivity());

                     //  adapter.add(new Group(groupName));
                       //insert into database;
                        if(scema.searchGroup(groupName)){
                            Toast.makeText(getActivity(),"Group Already Exists",Toast.LENGTH_LONG).show();

                        }
                       else{
                        scema.insertGroupTable(groupName);
                        Group g=scema.getGroup(groupName);


                        adapter.add(g);}

                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_group, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    DatabaseScema scema;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter= new GroupAdapter(getActivity(),R.layout.single_row_contact);

        lv = (ListView) view.findViewById(R.id.group_listView);
        lv.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);

        lv.setAdapter(adapter);
         scema= new DatabaseScema(getActivity());
        TreeSet<Group> ts=scema.getAllGroup();
        for (Group groupName:ts){
            adapter.add(groupName);
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i= new Intent(getActivity(),MessageActivity.class);
TextView tv= (TextView) view.findViewById(R.id.contact_name);


               String groupName=tv.getText().toString();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //lv.setAdapter(new GroupAdapter(getActivity(),R.layout.single_row_contact));
                i.putExtra("groupname",groupName);

                startActivity(i);
            }
        });



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = {
                        "View Group", "Delete Group", "Rename group", "Add Contacts","Rename group"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0: {
                                Intent i= new Intent(getActivity(),GroupmembersActivity.class);
                                i.putExtra("name",adapter.getItem(position).getGroupName());
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                if (scema.deleteGroup(adapter.getItem(position).getGroupName())) {
                                    adapter.remove(adapter.getItem(position));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "Deleted ", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), "Failed,try Again ", Toast.LENGTH_LONG).show();


                                }

                                break;
                            }
                            case 2: {
                            }
                            case 4: {
                            }
                            default: {
                            }
                        }


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;
            }
        });


    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
