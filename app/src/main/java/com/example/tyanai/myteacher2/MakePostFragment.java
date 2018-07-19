package com.example.tyanai.myteacher2;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;



public class MakePostFragment extends Fragment {
    public static final String TAG = "MakePostFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_makepost,container,false);





        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*


        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar
                .newTab()
                .setText("PR")
                .setTabListener(
                        new MainTabListener<ConfirmProfileFragment>(
                                getActivity(),
                                "f1",
                                ConfirmProfileFragment.class
                        )
                ));
        actionBar.addTab(actionBar
                .newTab()
                .setText("募集")
                .setTabListener(
                        new MainTabListener<ContractFragment>(
                                this,
                                "f2",
                                ContractFragment.class
                        )
                ));



    }
    public static class MainTabListener<T extends Fragment> implements ActionBar.TabListener {
        private Fragment fragment;
        private final Activity activity;
        private final String tag;
        private final Class<T> cls;

        public MainTabListener(
                Activity activity, String tag, Class<T> cls){
            this.activity = activity;
            this.tag = tag;
            this.cls = cls;

        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if(fragment == null){
                fragment = Fragment.instantiate(activity, cls.getName());
                ft.add(android.R.id.content, fragment, tag);
            }
            else{
                ft.attach(fragment);
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if(fragment != null){
                ft.detach(fragment);
            }
        }

*/
}

}