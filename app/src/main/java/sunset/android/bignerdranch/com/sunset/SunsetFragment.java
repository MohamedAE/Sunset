package sunset.android.bignerdranch.com.sunset;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SunsetFragment extends Fragment {

	//References to the elements of SunsetFragment
	private View mSceneView;
	private View mSunView;
	private View mSkyView;

	public static SunsetFragment newInstance() {
		return new SunsetFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sunset, container, false);

		mSceneView = view;
		mSunView = view.findViewById(R.id.sun);
		mSkyView = view.findViewById(R.id.sky);

		mSceneView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimation();
			}
		});

		return view;
	}

	/*Rect - position and size of a view relative to its parent*/
	private void startAnimation() {
		//Get the top of this view in pixels
		float sunYStart = mSunView.getTop();
		//Get the height of this view in pixels
		float sunYEnd = mSkyView.getHeight();

		/*Create an ObjectAnimator that can animate between the given float values
		* offFloat(...) PARAMS
		* - target object
		* - property name
		* - float values...*/
		ObjectAnimator heightAnimator = ObjectAnimator
				.ofFloat(mSunView, "y", sunYStart, sunYEnd)
				.setDuration(3000);

		heightAnimator.start();
	}

}