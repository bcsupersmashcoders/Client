package com.supersmashcoders.backtobackhackathon;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.supersmashcoders.backtobackhackathon.converters.DateConverter;
import com.supersmashcoders.backtobackhackathon.global.UserHandler;
import com.supersmashcoders.backtobackhackathon.models.EventModel;
import com.supersmashcoders.backtobackhackathon.models.UserModel;
import com.supersmashcoders.backtobackhackathon.proxy.EventProxy;
import com.supersmashcoders.backtobackhackathon.proxy.RequestListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {
    private static final String EVENT_ID = "com.supersmashcoders.EVENT_ID";

    private View mRootView;
    private Button mAttendButton;
    private long mEventId;
    private EventProxy mEventProxy;
    private boolean isAttending;
    private EventModel mEvent;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventFragment.
     */
    public static EventFragment newInstance(long eventId) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putLong(EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventId = getArguments().getLong(EVENT_ID);
        }
        mEventProxy = new EventProxy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_event, container, false);

        MapFragment mMapFragment = MapFragment.newInstance();
        getChildFragmentManager().beginTransaction()
                .add(R.id.map_container, mMapFragment)
                .commit();

        mAttendButton = (Button) mRootView.findViewById(R.id.button_attend);
        mAttendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendEvent();
            }
        });

        Button mImagesButton = (Button) mRootView.findViewById(R.id.button_images);
        mImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageCarouselActivity.class);
                intent.putExtra(ImageCarouselActivity.ARG_PRODUCTS, new ArrayList<>(mEvent.getProducts()));
                startActivity(intent);
            }
        });

        return mRootView;
    }

    public void checkIfAttending() {
        for (UserModel user : mEvent.getAttendants()) {
            if (user.getUsername().equals(UserHandler.getUsername())) {
                isAttending = true;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        mEventProxy.get(getActivity(), mEventId, new RequestListener<EventModel>() {
            @Override
            public void onComplete(EventModel event) {
                TextView name = (TextView) mRootView.findViewById(R.id.name);
                TextView description = (TextView) mRootView.findViewById(R.id.description);
                TextView startDate = (TextView) mRootView.findViewById(R.id.start_date);
                TextView endDate = (TextView) mRootView.findViewById(R.id.end_date);
                TextView tags = (TextView) mRootView.findViewById(R.id.tag);

                mEvent = event;
                name.setText(mEvent.getName());
                description.setText(mEvent.getDescription());
                startDate.setText(DateConverter.toString(mEvent.getStartDate(), DateConverter.DateFormat.DATE_FORMAT));
                endDate.setText(DateConverter.toString(mEvent.getEndDate(), DateConverter.DateFormat.DATE_FORMAT));
                tags.setText(mEvent.getTag().getDisplayName());
            }

            @Override
            public void onError() {
            }
        });
    }

    public void attendEvent() {
        if(isAttending) {
            mEventProxy.removeAttendance(getActivity(), mEventId, new RequestListener<EventModel>() {
                @Override
                public void onComplete(EventModel object) {
                    Toast.makeText(getActivity(), "Attending Event!", Toast.LENGTH_SHORT);
                }

                @Override
                public void onError() {
                }
            });
            isAttending = false;
        } else {
            mEventProxy.attend(getActivity(), mEventId, new RequestListener<EventModel>() {
                @Override
                public void onComplete(EventModel object) {
                    Toast.makeText(getActivity(), "Event Abandoned!", Toast.LENGTH_SHORT);
                }

                @Override
                public void onError() {
                }
            });
            isAttending = true;
        }
        updateButton();
    }

    private void updateButton() {
        if (isAttending) {
            mAttendButton.setText("Stop Attending");
        } else {
            mAttendButton.setText("Attend");
        }
    }
}
