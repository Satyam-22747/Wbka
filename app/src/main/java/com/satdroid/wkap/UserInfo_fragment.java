package com.satdroid.wkap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfo_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfo_fragment extends Fragment {

    private TextView name,email, phone, add,web, comp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserInfo_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfo_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfo_fragment newInstance(String param1, String param2) {
        UserInfo_fragment fragment = new UserInfo_fragment();
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
        View view=inflater.inflate(R.layout.fragment_user_info_fragment, container, false);
        name = view.findViewById(R.id.uName);
        email = view.findViewById(R.id.uEmail);
        phone =view. findViewById(R.id.uPhone);
        add = view.findViewById(R.id.uAdd);
        web = view.findViewById(R.id.uWeb);
        comp = view.findViewById(R.id.uComp);

        Bundle bundle = getArguments();
        UsersModal message = (UsersModal)bundle.getSerializable("UserInfo");
//        Toast.makeText(getContext(), "Message:"+message.getName(), Toast.LENGTH_SHORT).show();

        name.setText("Name:"+message.getName());
        email.setText("Email:"+message.getEmail());
        phone.setText("Phone:"+message.getPhone());

        add.setText("Address:"+
                "\nstreet:"+message.getAddress().getStreet()+
                "\nsuite:"+message.getAddress().getSuite()+
                "\ncity:"+message.getAddress().getCity()+
                "\nzipcode:"+message.getAddress().getZipcode()+
                "\nGeo:"+
                "\nlat:"+message.getAddress().getGeo().getLat()+
                "\nlng:"+message.getAddress().getGeo().getLng());

        comp.setText("Company:"+
                "\nname:"+message.getCompany().getName()+
                "\ncatchPhrase:"+message.getCompany().getCatchPhrase()+
                "\nbs:"+message.getCompany().getBs());

        web.setText(message.getWebsite());

        return view;
    }
}