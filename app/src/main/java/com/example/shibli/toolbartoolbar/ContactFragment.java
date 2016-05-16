package com.example.shibli.toolbartoolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    DatabaseScema scema;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lv;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String m_text = "";
    private String number = "";

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }
   static  ArrayList<ContactDetail> al;
    public  ArrayList<ContactDetail> retunAllContact(){
        return al;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (ListView) view.findViewById(R.id.contact_listView);
         scema = new DatabaseScema(getContext());
        al = scema.getAllContacts();

        final ContactAdapter adapter = new ContactAdapter(getActivity(), R.layout.single_row_contact);
        lv.setAdapter(adapter);

        // Toast.makeText(getContext(),al.size(),Toast.LENGTH_LONG).show();
        Log.i("Tag", "onActivityCreated: " + al.size());
int k=0;

        for (ContactDetail detail : al) {
           // k++;
            adapter.add(detail);

            ///remove this condition after testing
           // if(k>=16)break;
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv= (TextView) view.findViewById(R.id.contact_name);
                String name=tv.getText().toString();
                Intent i= new Intent(getActivity(),MessageActivity.class);
                i.putExtra("groupname",name);
                startActivity(i);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = {
                        "View Contact", "Delete Contact", "Call ",};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        switch (item) {
                            case 0: {
                                break;
                            }
                            case 1: {

                                if (scema.deleteContact(adapter.getItem(position).getName())) {
                                    Toast.makeText(getActivity(), "Deleted ", Toast.LENGTH_LONG).show();
                                    adapter.remove(adapter.getItem(position));
                                    adapter.notifyDataSetChanged();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable state = lv.onSaveInstanceState();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.adduser) {
            Toast.makeText(getContext(), " add user", Toast.LENGTH_LONG).show();

            LinearLayout layout = new LinearLayout(getActivity());
            layout.setOrientation(LinearLayout.VERTICAL);


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Contact");

// Set up the input
            final EditText name = new EditText(getActivity());
            name.setInputType(InputType.TYPE_CLASS_TEXT);
            name.setHint("Name");

            final EditText no = new EditText(getActivity());
            no.setInputType(InputType.TYPE_CLASS_PHONE);
            no.setHint("Number");

            layout.addView(name);
            layout.addView(no);

// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            builder.setView(layout);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_text = name.getText().toString();
                    number = no.getText().toString();
                    if(m_text.equals("")||number.equals("")){
                        Toast.makeText(getContext(),"Name or Number Can't be Empty",Toast.LENGTH_LONG).show();

                    }else{
                        //add it in database

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
        return true;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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

    @Override
    public void onResume() {
        super.onResume();


    }
}
