package com.supersmashcoders.backtobackhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.supersmashcoders.backtobackhackathon.models.ProductModel;
import com.supersmashcoders.backtobackhackathon.proxy.ImageProxy;

import java.util.List;


public class ImageCarouselActivity extends FragmentActivity {
    public static final String ARG_PRODUCTS = "com.bcsupersmashcoders.PRODUCTS";

    ImagesPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_carousel);

        List<ProductModel> products = (List<ProductModel>) getIntent().getSerializableExtra(ARG_PRODUCTS);
        mDemoCollectionPagerAdapter = new ImagesPagerAdapter(getSupportFragmentManager(), products);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class ImagesPagerAdapter extends FragmentStatePagerAdapter {
        List<ProductModel> products;

        public ImagesPagerAdapter(FragmentManager fm, List<ProductModel> products) {
            super(fm);
            this.products = products;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ImageFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ImageFragment.ARG_OBJECT, i + 1);
            args.putString(ImageFragment.ARG_IMAGE_URL, products.get(i).getPhotoUrl());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return products.get(position).getName();
        }
    }

    public static class ImageFragment extends Fragment {
        public static final String ARG_OBJECT = "object";
        public static final String ARG_IMAGE_URL = "image_url";
        private ImageProxy imageProxy;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            imageProxy = new ImageProxy();
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.image_item, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));

            String url =  "http://www.backcountry.com" + args.getString(ARG_IMAGE_URL);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
            imageProxy.getURLImage(getActivity(), url, imageView);
            return rootView;
        }
    }
}
