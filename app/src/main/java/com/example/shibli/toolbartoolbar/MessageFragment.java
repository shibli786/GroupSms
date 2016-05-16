package com.example.shibli.toolbartoolbar;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shibli.databse.DatabaseScema;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ListView lv;
    static View view;
    MessageListAdapter messageListAdapter;
    BroadcastReceiver broadcastReceiver;
    DatabaseScema scema;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String recipient = "";
    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
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
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.message_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getContext(), Setting.class));

            return true;
        }
        if (id == R.id.message) {
            startActivity(new Intent(getContext(), MessageActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void refreshAdapter() {

        onResume();

    }

    @Override
    public void onResume() {
        super.onResume();
        messageListAdapter = new MessageListAdapter(getActivity(), R.layout.message_list);
        lv = (ListView) view.findViewById(R.id.message_list);
        scema = new DatabaseScema(getActivity());
        ArrayList<MessageListDataProvider> al = scema.getListMessages();
        Toast.makeText(getActivity(), "message list " + al.size(), Toast.LENGTH_LONG).show();

        lv.setAdapter(messageListAdapter);
        for (MessageListDataProvider messageListDataProvider : al) {
            messageListAdapter.add(messageListDataProvider);
        }


        this.broadcastReceiver = new BroadcastReceiver() {
            @Override


            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "message Received", Toast.LENGTH_LONG).show();

                SMSInfo sms = ParseSMS.readSMSFromBroadCastReceiver(context, intent);
                recipient = scema.getContactNameByNumber(sms.phNo);
                MessageDataProvider messageDataProvider= new MessageDataProvider(sms.SMS,true,sms.time);

                MessageListDataProvider messageListDataProvider = new MessageListDataProvider(recipient, sms.time, sms.SMS);

                boolean f = false;
                String name = "";


                if (scema.searchRecipient(messageListDataProvider)) {
                    scema.updateMessageList(messageListDataProvider);
                    name = scema.getContactNameByNumber(sms.phNo);
                    for (int i = 0; i < messageListAdapter.getCount(); i++) {
                        if (name.equals(messageListAdapter.getItem(i).getRecipient())) {
                            messageListAdapter.remove(messageListAdapter.getItem(i));
                            messageListAdapter.insert(messageListDataProvider, 0);
                            messageListAdapter.notifyDataSetChanged();
                            break;
                        }
                    }


                } else {
                    scema.insertMessageList(messageListDataProvider);
                    name = sms.phNo;
                    messageListAdapter.add(messageListDataProvider);

                }
                scema.insertMessage(messageDataProvider,name);

            }


        };
        getActivity().getApplicationContext().registerReceiver(this.broadcastReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MessageActivity.class);
                TextView tv = (TextView) view.findViewById(R.id.last_recipient);

                String name = tv.getText().toString();

                i.putExtra("groupname", name);
                startActivity(i);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final CharSequence[] items = {
                        "View", "Delete message", "Archive message  ", "Email message", "Mark as Read"
                };

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
                                if (scema.deleteListMessage(messageListAdapter.getItem(position).getRecipient())) {
                                    messageListAdapter.remove(messageListAdapter.getItem(position));
                                    messageListAdapter.notifyDataSetChanged();
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

    @Override
    public void onPause() {
        super.onPause();
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
