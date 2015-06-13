package com.supersmashcoders.backtobackhackathon;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.supersmashcoders.backtobackhackathon.adapters.EventAdapter;
import com.supersmashcoders.backtobackhackathon.dummy.DummyContent;
import com.supersmashcoders.backtobackhackathon.enums.EventRequestType;
import com.supersmashcoders.backtobackhackathon.models.EventModel;
import com.supersmashcoders.backtobackhackathon.proxy.EventProxy;
import com.supersmashcoders.backtobackhackathon.proxy.RequestListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnEventsFragmentInteractionListener}
 * interface.
 */
public class EventsFragment extends Fragment implements AbsListView.OnItemClickListener {
    private static final String EVENT_REQUEST_TYPE = "com.supersmashcoders.EventRequestType";
    private static final String EVENT_LIST = "com.supersmashcoders.EventList";

    private EventRequestType mEventRequestType;
    private List<EventModel> mEvents;
    EventProxy mEventProxy;

    private OnEventsFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static EventsFragment newInstance(String eventRequestType) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_REQUEST_TYPE, eventRequestType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventProxy = new EventProxy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        if (getArguments() != null) {
            mEventRequestType = EventRequestType.valueOf(getArguments().getString(EVENT_REQUEST_TYPE));
            mEvents = (List<EventModel>) getArguments().getSerializable(EVENT_LIST);
            if (mEvents == null) {
                mEvents = new ArrayList<>();
                // TODO: Temporary adapter while content is loaded. Replace with spinner
                mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
                mEventProxy.getEvents(getActivity(), new RequestListener<List<EventModel>>() {
                    @Override
                    public void onComplete(List<EventModel> eventsList) {
                        mEvents = eventsList;
                        EventAdapter jsonAdapter = new EventAdapter(getActivity(), R.layout.list_item_event, eventsList);
                        mListView.setAdapter(jsonAdapter);
                    }

                    @Override
                    public void onError() {
                        Log.e("API CALL", "Error filling list");
                    }
                });
            } else {
                mAdapter = new EventAdapter(getActivity(), R.layout.list_item_event, mEvents);
            }
        } else if(savedInstanceState != null) {
            mEventRequestType = EventRequestType.valueOf(savedInstanceState.getString(EVENT_REQUEST_TYPE));
            mEvents = (List<EventModel>) savedInstanceState.getSerializable(EVENT_LIST);
            mAdapter = new EventAdapter(getActivity(), R.layout.list_item_event, mEvents);
        }

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putString(EVENT_REQUEST_TYPE, mEventRequestType.name());
        getArguments().putSerializable(EVENT_LIST, new ArrayList<>(mEvents));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEventsFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEventsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onEventSelected(id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EVENT_REQUEST_TYPE, mEventRequestType.name());
        outState.putSerializable(EVENT_LIST, new ArrayList<>(mEvents));
        super.onSaveInstanceState(outState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnEventsFragmentInteractionListener {
        public void onEventSelected(long eventId);
    }

}
